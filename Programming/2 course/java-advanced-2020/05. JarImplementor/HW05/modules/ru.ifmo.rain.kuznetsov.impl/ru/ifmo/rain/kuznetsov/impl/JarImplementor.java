package ru.ifmo.rain.kuznetsov.impl;

import info.kgeorgiy.java.advanced.implementor.ImplerException;
import info.kgeorgiy.java.advanced.implementor.JarImpler;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;
import java.util.function.Predicate;
import java.util.jar.Attributes;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;

/**
 * Implementation class for {@link info.kgeorgiy.java.advanced.implementor.JarImpler} interface.
 *
 * @author Vladislav Kuznetsov
 */
public class JarImplementor implements JarImpler {

    /** Create new object
     *
     */
    public JarImplementor() {}

    /**
     * Class for uniq {@link java.lang.reflect.Method}.
     */
    private static class UniqMethod {
        /**
         * Wrapped method.
         */
        private final Method method;

        /**
         * return {@link Method}
         * @return method
         */
        public Method getMethod() {
            return method;
        }

        /**
         * Constructor for {@link UniqMethod}.
         *
         * @param method to wrap
         */
        UniqMethod(Method method) {
            this.method = method;
        }

        /**
         * Compare methods. Method are equal, if their names, parameters and return types are equal.
         *
         * @param object other object to compare
         * @return true, if equals, else false
         */
        @Override
        public boolean equals(Object object) {
            if (object == null) {
                return false;
            }
            if (object instanceof UniqMethod) {
                UniqMethod another = (UniqMethod) object;
                if (method.getName().equals(another.getMethod().getName())) {
                    return true;
                }
                if (method.getReturnType().equals(another.getMethod().getReturnType())) {
                    return true;
                }
                if (Arrays.equals(method.getParameterTypes(), another.getMethod().getParameterTypes())) {
                    return true;
                }
                if (method.hashCode() != another.getMethod().hashCode()) {
                    return true;
                }
                return false;
            }
            return false;
        }

        /**
         * Get hashcode of this {@link UniqMethod}.
         *
         * @return hashcode
         */
        @Override
        public int hashCode() {
            return (Arrays.hashCode(method.getParameterTypes()) + method.getReturnType().hashCode()) + method.getName().hashCode();
        }
    }

    /**
     * Get package name. Example: java.lang for {@link java.lang.String}.
     *
     * @param token class to get package name
     * @return package name
     */
    private String getPackageName(Class<?> token) {
        if (token.getPackage() == null) {
            return "";
        } else {
            return token.getPackage().getName();
        }
    }

    /**
     * Get path to token, with implementation class.
     *
     * @param path  path to parent class
     * @param token class whose path we get
     * @param end   line whose we write after all (type file)
     * @return path to file
     */
    private Path getPath(Path path, Class<?> token, String end) {
        return path.resolve(getPackageName(token).replace('.', File.separatorChar)).resolve(token.getSimpleName() + "Impl." + end);
    }

    // SOME GETTERS FOR GENERATORS

    /**
     * Get array of private constructors.
     *
     * @param token class whose we get constuctors
     * @return array of private constructors
     */
    private Constructor<?>[] getConstructors(Class<?> token) {
        return Arrays.stream(token.getDeclaredConstructors()).filter(constructor -> !Modifier.isPrivate(constructor.getModifiers())).toArray(Constructor[]::new);
    }

    /**
     * Return Unicode format of string
     * @param s input string
     * @return Unicode string
     */
    private String getUnicode(String s) {
        StringBuilder stringBuilder = new StringBuilder();
        for (char ch : s.toCharArray()) {
            stringBuilder.append(ch >= 128 ? String.format("\\u%04X", (int) ch) : ch);
        }
        return stringBuilder.toString();
    }

    /**
     * Get default value of token.
     *
     * @param token class whose we get default value
     * @return default value of token
     */
    private String getValue(Class<?> token) {
        if (token.equals(boolean.class)) {
            return "false";
        }
        if (token.equals(void.class)) {
            return "";
        }
        if (token.isPrimitive()) {
            return "0";
        }
        return "null";
    }

    // / SOME GETTERS FOR GENERATORS

    // GENERATORS FOR CODE

    /**
     * Generate space.
     *
     * @return " "
     */
    private String generateSpace() {
        return " ";
    }

    /**
     * Generate some tabs.
     *
     * @param times , how much times we repeat tab
     * @return some tabs
     */
    private String generateTab(int times) {
        return "    ".repeat(times);
    }

    /**
     * Generate line separator.
     *
     * @return line separator
     */
    private String generateLineSeparator() {
        return System.lineSeparator();
    }

    /**
     * Generate return type for {@link Executable}
     *
     * @param executable {@link Method} or {@link Constructor}
     * @return return type
     */
    private String generateReturn(Executable executable) {
        if (executable instanceof Method) {
            Method method = (Method) executable;
            return method.getReturnType().getCanonicalName() + generateSpace();
        }
        return "";
    }

    /**
     * Generate class name.
     *
     * @param token class whose we generate name
     * @return class name
     */
    private String generateClassName(Class<?> token) {
        return token.getSimpleName() + "Impl";
    }

    /**
     * Generate right name for {@link Executable}. If this is {@link Method}, return type of return and name of method,
     * else return name of class.
     *
     * @param executable {@link Method} or {@link Constructor}
     * @return right name.
     */
    private String generateName(Executable executable) {
        if (executable instanceof Method) {
            Method method = (Method) executable;
            return method.getName();
        }
        return generateClassName(((Constructor<?>) executable).getDeclaringClass());
    }

    /**
     * Generate full name for parameter.
     *
     * @param parameter  parameter whose we get
     * @param isShowType is show type of parameter
     * @return full name
     */
    private String generateParametreExucutable(Parameter parameter, boolean isShowType) {
        return (isShowType ? parameter.getType().getCanonicalName() + generateSpace() : "") + parameter.getName();
    }

    /**
     * Generate full names for {@link Executable}.
     *
     * @param executable executable whose we get
     * @param isShowType is show types of parameters
     * @return parameters with brackets
     */
    private String generateParametresExucutable(Executable executable, boolean isShowType) {
        return Arrays.stream(executable.getParameters()).map(parameter -> generateParametreExucutable(parameter, isShowType)).
                collect(Collectors.joining(", ", "(", ")"));
    }

    /**
     * Generate exceptions for {@link Executable}.
     *
     * @param executable executable whose we get
     * @return right line with list of exceptions.
     */
    private String generateException(Executable executable) {
        StringBuilder returned = new StringBuilder();
        Class<?>[] exceptions = executable.getExceptionTypes();
        if (exceptions.length > 0) {
            returned.append(" throws ");
        }
        returned.append(Arrays.stream(exceptions).map(Class::getCanonicalName).collect(Collectors.joining(", ")));
        return returned.toString();
    }

    /**
     * Generate body of {@link Executable}.
     *
     * @param executable executable whose we get
     * @return line with correct body
     */
    private String generateMain(Executable executable) {
        if (executable instanceof Method) {
            return "return " + getValue(((Method) executable).getReturnType()) + ";";
        } else {
            return "super" + generateParametresExucutable(executable, false) + ";";
        }
    }

    /**
     * Generate FULL line of {@link Executable}, ready to compile.
     *
     * @param executable whose we get ({@link Constructor} or {@link Method})
     * @return code of such {@link Executable}
     */
    private String generateExucutable(Executable executable) {
        StringBuilder returned = new StringBuilder();
        returned.append(generateTab(1));
        int mod = executable.getModifiers() & ~Modifier.ABSTRACT & ~Modifier.NATIVE & ~Modifier.TRANSIENT;
        returned.append(Modifier.toString(mod)).
                append(mod > 0 ? generateSpace() : "").
                append(generateReturn(executable)).
                append(generateName(executable)).
                append(generateParametresExucutable(executable, true)).
                append(generateException(executable)).
                append(generateSpace()).
                append("{").
                append(generateLineSeparator()).
                append(generateTab(2)).
                append(generateMain(executable)).
                append(generateLineSeparator()).
                append(generateTab(1)).
                append("}").
                append(generateLineSeparator());
        return returned.toString();
    }

    /**
     * Generate package line for class.
     *
     * @param token whose we get package
     * @return line look like (package info.kgeorgiy.java.advanced.implementor.JarImpler + line separator * 2)
     */
    private String generatePackage(Class<?> token) {
        if (!getPackageName(token).equals("")) {
            return "package " + getPackageName(token) + ";" + generateLineSeparator() + generateLineSeparator();
        }
        return generateLineSeparator();
    }

    /**
     * Generate up of code of class.
     *
     * @param token class whose we get up
     * @return correct up of class
     * @throws ImplerException if class is private
     */
    private String generateClassUp(Class<?> token) throws ImplerException {
        if (Modifier.isPrivate(token.getModifiers())) {
            throw new ImplerException("Private token-class");
        }
        return generatePackage(token) + "public class " + generateClassName(token) + " " + (token.isInterface() ? "implements " : "extends ")
                + token.getCanonicalName() + " {" + generateLineSeparator();
    }

    /**
     * Generate constructors for class and write it to writer.
     *
     * @param token  class whose we generate constructors
     * @param writer writer class
     * @throws IOException     if error during writing
     * @throws ImplerException if class doesn't have not private constructors
     */
    private void generateConstructors(Class<?> token, Writer writer) throws IOException, ImplerException {
        Constructor<?>[] constructors = getConstructors(token);
        if (constructors.length == 0) {
            throw new ImplerException("You may not implement void");
        }
        for (Constructor<?> constructor : constructors) {
            writer.write(getUnicode(generateExucutable(constructor)));
        }
    }

    /**
     * Filter array of {@link Method} to Set of {@link UniqMethod}, filer - is uniq and abstract.
     *
     * @param methods array of {@link Method}.
     * @param set     set where we put methods.
     * @param predicate predicate for filter methods
     */
    private void addMethods(Method[] methods, Set<UniqMethod> set, Predicate<Method> predicate) {
        Arrays.stream(methods).
                filter(predicate).
                map(UniqMethod::new).
                collect(Collectors.toCollection(() -> set));
    }


    /**
     * Write abstract methods
     * @param token class
     * @param writer those write
     * @throws IOException if problesm with writer
     */
    private void generateAbstractMethods(Class<?> token, Writer writer) throws IOException {
        Set<UniqMethod> methods = new HashSet<>();
        Set<UniqMethod> finalMethods = new HashSet<>();
        addMethods(token.getMethods(), methods, method -> Modifier.isAbstract(method.getModifiers()));
        addMethods(token.getDeclaredMethods(), finalMethods, method -> Modifier.isFinal(method.getModifiers()));
        while (token != null) {
            addMethods(token.getDeclaredMethods(), methods, method -> Modifier.isAbstract(method.getModifiers()));
            token = token.getSuperclass();
        }
        methods.removeAll(finalMethods);
        for (UniqMethod method : methods) {
            writer.write(generateExucutable(method.getMethod()));
        }
    }

    // / GENERATORS FOR CODE

    // UTILS

    /**
     * Create parent directories to {@link Path}
     *
     * @param root - path
     * @throws ImplerException if we can't create
     */
    public void createDirectories(Path root) throws ImplerException {
        if (root.getParent() != null) {
            try {
                Files.createDirectories(root.getParent());
            } catch (IOException e) {
                throw new ImplerException("Can't create directories file", e);
            }
        }
    }

    // / UTILS

    /**
     * @throws ImplerException if we can't generate class:
     *                         1) Arguments (or one) are null
     *                         2) Class is primitive or array or final class
     *                         3) Class isn't an interface and contain only private constructors
     *                         4) Problem with writer.
     */
    @Override
    public void implement(Class<?> token, Path root) throws ImplerException {
        if (token == null || root == null) {
            throw new ImplerException("Token or class is null");
        }
        if (token.isPrimitive() || token.isArray() || Modifier.isFinal(token.getModifiers()) || token == Enum.class) {
            throw new ImplerException("Incorrect class");
        }
        root = getPath(root, token, "java");
        createDirectories(root);
        try {
            Writer writer = Files.newBufferedWriter(root);
            writer.write(getUnicode(generateClassUp(token)));
            if (!token.isInterface()) {
                generateConstructors(token, writer);
            }
            generateAbstractMethods(token, writer);
            writer.write(getUnicode("}" + generateLineSeparator()));
            writer.close();
        } catch (IOException e) {
            throw new ImplerException("Can't write to java file", e);
        }
    }

    /**
     * @throws ImplerException if we can't generate class:
     *                         1) Arguments (or one) are null
     *                         2) Class is primitive or array or final class
     *                         3) Class isn't an interface and contain only private constructors
     *                         4) Problem with writer.
     */
    @Override
    public void implementJar(Class<?> aClass, Path path) throws ImplerException {
        if (aClass == null || path == null) {
            throw new ImplerException("Token or class is null");
        }
        createDirectories(path);
        Path templateDir;
        try {
            templateDir = Files.createTempDirectory(path.toAbsolutePath().getParent(), "template");
        } catch (IOException e) {
            throw new ImplerException("Can't create template directory", e);
        }
        try {
            implement(aClass, templateDir);
            JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
            String[] args = new String[]{
                    "-classpath",
                     "info.kgeorgiy.java.advanced.base.jar" + File.pathSeparator +
                            templateDir.toString() + File.pathSeparator + System.getProperty("java.class.path"),
                    getPath(templateDir, aClass, "java").toString()
            };
            if (compiler.run(null, null, null, args) != 0) {
                throw new ImplerException("Can't complile files");
            }
            Manifest manifest = new Manifest();
            Attributes attributes = manifest.getMainAttributes();
            attributes.put(Attributes.Name.MANIFEST_VERSION, "1.0");
            ZipEntry zipEntry;
            String localPath = Paths.get("").resolve(aClass.getPackageName().replace('.', File.separatorChar)).resolve(aClass.getSimpleName() + "Impl.class").toString();
            try (JarOutputStream writerJar = new JarOutputStream(Files.newOutputStream(path), manifest)) {
                zipEntry = new ZipEntry(localPath);
                writerJar.putNextEntry(zipEntry);
                Files.copy(getPath(templateDir, aClass, "class"), writerJar);
            } catch (IOException e) {
                throw new ImplerException("Can't write to JAR", e);
            }
        } finally {
            try {
                Files.walkFileTree(templateDir, new SimpleFileVisitor<>() {
                    @Override
                    public FileVisitResult visitFile(Path file, BasicFileAttributes attributes) throws IOException {
                        Files.delete(file);
                        return FileVisitResult.CONTINUE;
                    }

                    @Override
                    public FileVisitResult postVisitDirectory(Path file, IOException e) throws IOException {
                        Files.delete(file);
                        return FileVisitResult.CONTINUE;
                    }
                });
            } catch (IOException e) {
                // ignore: can't delete directory
                System.out.println("Can't delete dir" + e.getMessage());
            }
        }
    }

    /**
     * Special function for debug. Save .jar file to "/home/vladkuznetsov/Vl/Projects/Java/java-advanced-2020/05. JarImplementor/HW05/test/".
     *
     * @param args args[0] - name of generate class.
     */
    public static void main(String[] args) {
        JarImplementor implementor = new JarImplementor();
        if (args.length < 1 || args[0] == null) {
            System.out.println("Wrong args");
            System.exit(0);
        }
        try {
            implementor.implementJar(Class.forName(args[0]), Path.of("/home/vladkuznetsov/Vl/Projects/Java/java-advanced-2020/05. JarImplementor/HW05/test/test.jar"));
        } catch (ImplerException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
