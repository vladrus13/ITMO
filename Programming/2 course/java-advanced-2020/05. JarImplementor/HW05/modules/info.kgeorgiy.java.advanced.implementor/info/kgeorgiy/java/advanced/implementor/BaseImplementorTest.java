package info.kgeorgiy.java.advanced.implementor;

import info.kgeorgiy.java.advanced.base.BaseTest;
import org.junit.Assert;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.lang.reflect.Modifier;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public class BaseImplementorTest extends BaseTest {
    private static final SimpleFileVisitor<Path> DELETE_VISITOR = new SimpleFileVisitor<>() {
        @Override
        public FileVisitResult visitFile(final Path file, final BasicFileAttributes attrs) throws IOException {
            Files.delete(file);
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult postVisitDirectory(final Path dir, final IOException exc) throws IOException {
            Files.delete(dir);
            return FileVisitResult.CONTINUE;
        }
    };

    protected static void check(final URLClassLoader loader, final Class<?> token) {
        final String name = getImplName(token);
        try {
            System.err.println("Loading class " + name);
            final Class<?> impl = loader.loadClass(name);

            if (token.isInterface()) {
                Assert.assertTrue(name + " should implement " + token, Arrays.asList(impl.getInterfaces()).contains(token));
            } else {
                Assert.assertEquals(name + " should extend " + token, token, impl.getSuperclass());
            }
            Assert.assertFalse(name + " should not be abstract", Modifier.isAbstract(impl.getModifiers()));
            Assert.assertFalse(name + " should not be interface", Modifier.isInterface(impl.getModifiers()));
        } catch (final IllegalAccessError e) {
            System.err.println("\t\t" + e.getMessage());
            // Ok
        } catch (final ClassNotFoundException e) {
            throw new AssertionError("Error loading class " + name, e);
        }
    }

    private static String getImplName(final Class<?> token) {
        return token.getPackageName() + "." + token.getSimpleName() + "Impl";
    }

    public static void clean(final Path root) throws IOException {
        if (Files.exists(root)) {
            Files.walkFileTree(root, DELETE_VISITOR);
        }
    }

    public static Path getFile(final Path root, final Class<?> clazz) {
        return root.resolve(getImplName(clazz).replace(".", File.separator) + ".java").toAbsolutePath();
    }

    private void implement(final boolean shouldFail, final Path root, final Class<?>... classes) {
        Impler implementor;
        try {
            implementor = createCUT();
        } catch (final Exception e) {
            e.printStackTrace();
            Assert.fail("Instantiation error");
            implementor = null;
        }
        for (final Class<?> clazz : classes) {
            try {
                implement(root, implementor, clazz);

                Assert.assertFalse("You may not implement " + clazz, shouldFail);
            } catch (final ImplerException e) {
                if (shouldFail) {
                    return;
                }
                throw new AssertionError("Error implementing " + clazz, e);
            } catch (final Throwable e) {
                throw new AssertionError("Error implementing " + clazz, e);
            }
            final Path file = getFile(root, clazz);
            Assert.assertTrue("Error implementing clazz: File '" + file + "' not found", Files.exists(file));
        }
    }

    protected void implement(final Path root, final Impler implementor, final Class<?> clazz) throws ImplerException {
        implementor.implement(clazz, root);
    }

    public static void check(final Path root, final Class<?>... classes) {
        final URLClassLoader loader = getClassLoader(root);
        for (final Class<?> token : classes) {
            check(loader, token);
        }
    }

    public static void compileFiles(final Path root, final List<String> files) {
        final JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        Assert.assertNotNull("Could not find java compiler, include tools.jar to classpath", compiler);
        final List<String> args = new ArrayList<>();
        args.addAll(files);
        args.add("-cp");
        args.add(root + File.pathSeparator + getClassPath());
        final int exitCode = compiler.run(null, null, null, args.toArray(String[]::new));
        Assert.assertEquals("Compiler exit code", 0, exitCode);
    }

    private static String getClassPath() {
        try {
            return Path.of(BaseImplementorTest.class.getProtectionDomain().getCodeSource().getLocation().toURI()).toString();
        } catch (final URISyntaxException e) {
            throw new AssertionError(e);
        }
    }

    public static void compile(final Path root, final Class<?>... classes) {
        final List<String> files = new ArrayList<>();
        for (final Class<?> token : classes) {
            files.add(getFile(root, token).toString());
        }
        compileFiles(root, files);
    }

    public static URLClassLoader getClassLoader(final Path root) {
        try {
            return new URLClassLoader(new URL[]{root.toUri().toURL()});
        } catch (final MalformedURLException e) {
            throw new AssertionError(e);
        }
    }

    public void test(final Path root, final boolean shouldFail, final Class<?>[] classes) {
        try {
            try {
                implement(shouldFail, root, classes);
                if (!shouldFail) {
                    compile(root, classes);
                    check(root, classes);
                }
            } finally {
                clean(root);
            }
        } catch (final IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public static void assertConstructor(final Class<?>... ifaces) {
        final Class<?> token = loadClass();
        for (final Class<?> iface : ifaces) {
            Assert.assertTrue(token.getName() + " should implement " + iface.getName() + " interface", iface.isAssignableFrom(token));
        }
        checkConstructor("public default constructor", token);
    }
}
