package ru.ifmo.rain.kuznetsov.impl;

import info.kgeorgiy.java.advanced.implementor.Impler;
import info.kgeorgiy.java.advanced.implementor.ImplerException;

import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Implementor implements Impler {

    private static class UniqMethod {
        private final Method method;

        public Method getMethod() {
            return method;
        }

        UniqMethod(Method method) {
            this.method = method;
        }

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

        @Override
        public int hashCode() {
            return (Arrays.hashCode(method.getParameterTypes()) + 11 * method.getReturnType().hashCode()) + method.getName().hashCode();
        }
    }

    private String getPackageName(Class<?> token) {
        if (token.getPackage() == null) {
            return "";
        } else {
            return token.getPackage().getName();
        }
    }

    private Path getPath(Path path, Class<?> token) {
        return path.resolve(getPackageName(token).replace('.', File.separatorChar)).resolve(token.getSimpleName() + "Impl.java");
    }

    // SOME GETTERS FOR GENERATORS

    private Constructor<?>[] getConstructors(Class<?> token) {
        return Arrays.stream(token.getDeclaredConstructors()).filter(constructor -> !Modifier.isPrivate(constructor.getModifiers())).toArray(Constructor[]::new);
    }

    // / SOME GETTERS FOR GENERATORS

    // GENERATORS FOR CODE

    private String generateSpace() {
        return " ";
    }

    private String generateTab(int times) {
        return "    ".repeat(times);
    }

    private String generateLineSeparator() {
        return System.lineSeparator();
    }

    private String generateReturn(Executable executable) {
        if (executable instanceof Method) {
            Method method = (Method) executable;
            return method.getReturnType().getCanonicalName() + generateSpace();
        }
        return "";
    }

    private String generateClassName(Class<?> token) {
        return token.getSimpleName() + "Impl";
    }

    private String generateName(Executable executable) {
        if (executable instanceof Method) {
            Method method = (Method) executable;
            return method.getName();
        }
        return generateClassName(((Constructor<?>) executable).getDeclaringClass());
    }

    private String generateParametreExucutable(Parameter parameter, boolean isShowType) {
        return (isShowType ? parameter.getType().getCanonicalName() + generateSpace() : "") + parameter.getName();
    }

    private String generateParametresExucutable(Executable executable, boolean isShowType) {
        return Arrays.stream(executable.getParameters()).map(parameter -> generateParametreExucutable(parameter, isShowType)).
                collect(Collectors.joining(", ", "(", ")"));
    }

    private String generateException(Executable executable) {
        StringBuilder returned = new StringBuilder();
        Class<?>[] exceptions = executable.getExceptionTypes();
        if (exceptions.length > 0) {
            returned.append(" throws ");
        }
        returned.append(Arrays.stream(exceptions).map(Class::getCanonicalName).collect(Collectors.joining(", ")));
        return returned.toString();
    }

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

    private String generateMain(Executable executable) {
        if (executable instanceof Method) {
            return "return " + getValue(((Method) executable).getReturnType()) + ";";
        } else {
            return "super" + generateParametresExucutable(executable, false) + ";";
        }
    }

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

    private String generatePackage(Class<?> token) {
        if (!getPackageName(token).equals("")) {
            return "package " + getPackageName(token) + ";" + generateLineSeparator() + generateLineSeparator();
        }
        return generateLineSeparator();
    }

    private String generateClassUp(Class<?> token) throws ImplerException {
        if (Modifier.isPrivate(token.getModifiers())) {
            throw new ImplerException("Private token-class");
        }
        return generatePackage(token) + "public class " + generateClassName(token) + " " + (token.isInterface() ? "implements " : "extends ")
                + token.getCanonicalName() + " {" + generateLineSeparator();
    }

    private void generateConstructors(Class<?> token, Writer writer) throws IOException, ImplerException {
        Constructor<?>[] constructors = getConstructors(token);
        if (constructors.length == 0) {
            throw new ImplerException("You may not implement void");
        }
        for (Constructor<?> constructor : constructors) {
            writer.write(generateExucutable(constructor));
        }
    }

    private void addMethods(Method[] methods, Set<UniqMethod> set, Predicate<Method> predicate) {
        Arrays.stream(methods).
                filter(predicate).
                map(UniqMethod::new).
                collect(Collectors.toCollection(() -> set));
    }

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

    @Override
    public void implement(Class<?> token, Path root) throws ImplerException {
        if (token == null || root == null) {
            throw new ImplerException("Token or class is null");
        }
        if (token.isPrimitive() || token.isArray() || Modifier.isFinal(token.getModifiers()) || token == Enum.class) {
            throw new ImplerException("Incorrect class");
        }
        root = getPath(root, token);
        if (root.getParent() != null) {
            try {
                Files.createDirectories(root.getParent());
            } catch (IOException e) {
                throw new ImplerException("Can't create directories for java file", e);
            }
        }
        try {
            Writer writer = Files.newBufferedWriter(root);
            writer.write(generateClassUp(token));
            if (!token.isInterface()) {
                generateConstructors(token, writer);
            }
            generateAbstractMethods(token, writer);
            writer.write("}" + generateLineSeparator());
            writer.close();
        } catch (IOException e) {
            throw new ImplerException("Can't write to java file", e);
        }
    }

    public static void main(String[] args) {
        Implementor implementor = new Implementor();
        try {
            implementor.implement(Enum.class, Path.of("/home/vladkuznetsov/Vl/Projects/Java/java-advanced-2020/04. Implementor/HW04/test"));
        } catch (ImplerException e) {
            e.printStackTrace();
        }
    }
}
