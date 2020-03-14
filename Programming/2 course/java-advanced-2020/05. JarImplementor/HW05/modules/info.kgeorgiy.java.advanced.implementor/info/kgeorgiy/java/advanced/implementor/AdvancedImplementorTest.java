package info.kgeorgiy.java.advanced.implementor;

import info.kgeorgiy.java.advanced.implementor.full.classes.Overridden;
import info.kgeorgiy.java.advanced.implementor.full.interfaces.Interfaces;
import info.kgeorgiy.java.advanced.implementor.full.interfaces.Proxies;
import org.junit.Test;

/**
 * Full tests for advanced version
 * of <a href="https://www.kgeorgiy.info/courses/java-advanced/homeworks.html#homework-implementor">Implementor</a> homework
 * for <a href="https://www.kgeorgiy.info/courses/java-advanced/">Java Advanced</a> course.
 *
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public class AdvancedImplementorTest extends ClassImplementorTest {
    @Test
    public void test41_duplicateClasses() {
        test(false, Proxies.class);
    }

    @Test
    public void test42_nestedInterfaces() {
        test(false, Interfaces.OK);
        test(true, Interfaces.FAILED);
    }

    @Test
    public void test43_overridden() {
        test(false, Overridden.OK);
        test(true, Overridden.FAILED);
    }
}
