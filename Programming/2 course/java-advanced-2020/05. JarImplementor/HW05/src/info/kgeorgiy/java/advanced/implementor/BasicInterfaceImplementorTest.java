package info.kgeorgiy.java.advanced.implementor;

import info.kgeorgiy.java.advanced.implementor.basic.interfaces.InterfaceWithDefaultMethod;
import info.kgeorgiy.java.advanced.implementor.basic.interfaces.InterfaceWithStaticMethod;
import info.kgeorgiy.java.advanced.implementor.basic.interfaces.standard.Accessible;
import info.kgeorgiy.java.advanced.implementor.basic.interfaces.standard.Descriptor;
import info.kgeorgiy.java.advanced.implementor.basic.interfaces.standard.RandomAccess;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestWatcher;

import java.io.IOException;
import java.nio.file.Paths;

/**
 * Basic tests for easy version
 * of <a href="https://www.kgeorgiy.info/courses/java-advanced/homeworks.html#homework-implementor">Implementor</a> homework
 * for <a href="https://www.kgeorgiy.info/courses/java-advanced/">Java Advanced</a> course.
 *
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public class BasicInterfaceImplementorTest extends BaseImplementorTest {
    private String methodName;
    @Rule
    public TestWatcher watcher = watcher(description -> methodName = description.getMethodName());

    @Test
    public void test01_constructor() {
        assertConstructor(Impler.class);
    }

    @Test
    public void test02_methodlessInterfaces() throws IOException {
        test(false, RandomAccess.class);
    }

    @Test
    public void test03_standardInterfaces() throws IOException {
        test(false, Accessible.class);
    }

    @Test
    public void test04_extendedInterfaces() throws IOException {
        test(false, Descriptor.class);
    }

    @Test
    public void test05_standardNonInterfaces() throws IOException {
        test(true, void.class, String.class);
    }

    @Test
    public void test06_java8Interfaces() throws IOException {
        test(false, InterfaceWithStaticMethod.class, InterfaceWithDefaultMethod.class);
    }

    protected void test(final boolean shouldFail, final Class<?>... classes) {
        test(Paths.get(methodName), shouldFail, classes);
    }
}
