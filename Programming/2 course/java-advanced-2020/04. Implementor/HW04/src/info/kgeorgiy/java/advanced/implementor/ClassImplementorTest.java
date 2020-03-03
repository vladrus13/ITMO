package info.kgeorgiy.java.advanced.implementor;

import info.kgeorgiy.java.advanced.implementor.basic.classes.AbstractClassWithInterface;
import info.kgeorgiy.java.advanced.implementor.basic.classes.standard.IIOException;
import info.kgeorgiy.java.advanced.implementor.basic.classes.standard.IIOImage;
import info.kgeorgiy.java.advanced.implementor.basic.classes.standard.RMIServerImpl;
import info.kgeorgiy.java.advanced.implementor.basic.classes.standard.RelationNotFoundException;
import org.junit.Test;

import java.io.IOException;

/**
 * Basic tests for hard version
 * of <a href="https://www.kgeorgiy.info/courses/java-advanced/homeworks.html#homework-implementor">Implementor</a> homework
 * for <a href="https://www.kgeorgiy.info/courses/java-advanced/">Java Advanced</a> course.
 *
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public class ClassImplementorTest extends InterfaceImplementorTest {
    @Test
    public void test07_defaultConstructorClasses() throws IOException {
        test(false, RelationNotFoundException.class, AbstractClassWithInterface.class);
    }

    @Test
    public void test08_noDefaultConstructorClasses() throws IOException {
        test(false, IIOException.class);
    }

    @Test
    public void test09_ambiguousConstructorClasses() throws IOException {
        test(false, IIOImage.class);
    }

    @Test
    public void test14_nonPublicAbstractMethod() throws IOException {
        test(false, RMIServerImpl.class);
    }

    @Test
    public void test15_enum() throws IOException {
        test(true, Enum.class);
    }
}
