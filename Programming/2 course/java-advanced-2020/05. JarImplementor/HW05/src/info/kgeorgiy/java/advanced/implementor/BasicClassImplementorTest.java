package info.kgeorgiy.java.advanced.implementor;

import info.kgeorgiy.java.advanced.implementor.basic.classes.AbstractClassWithInterface;
import info.kgeorgiy.java.advanced.implementor.basic.classes.standard.IIOException;
import info.kgeorgiy.java.advanced.implementor.basic.classes.standard.IIOImage;
import info.kgeorgiy.java.advanced.implementor.basic.classes.standard.RMIServerImpl;
import info.kgeorgiy.java.advanced.implementor.basic.classes.standard.RelationNotFoundException;
import org.junit.Test;

/**
 * Basic tests for hard version
 * of <a href="https://www.kgeorgiy.info/courses/java-advanced/homeworks.html#homework-implementor">Implementor</a> homework
 * for <a href="https://www.kgeorgiy.info/courses/java-advanced/">Java Advanced</a> course.
 *
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public class BasicClassImplementorTest extends BasicInterfaceImplementorTest {
    @Test
    public void test07_defaultConstructorClasses() {
        test(false, RelationNotFoundException.class, AbstractClassWithInterface.class);
    }

    @Test
    public void test08_noDefaultConstructorClasses() {
        test(false, IIOException.class);
    }

    @Test
    public void test09_ambiguousConstructorClasses() {
        test(false, IIOImage.class);
    }

    @Test
    public void test14_nonPublicAbstractMethod() {
        test(false, RMIServerImpl.class);
    }

    @Test
    public void test15_enum() {
        test(true, Enum.class);
    }
}
