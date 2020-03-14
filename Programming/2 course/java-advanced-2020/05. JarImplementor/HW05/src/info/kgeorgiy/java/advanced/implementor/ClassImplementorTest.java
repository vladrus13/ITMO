package info.kgeorgiy.java.advanced.implementor;

import info.kgeorgiy.java.advanced.implementor.basic.classes.AbstractClassWithInterface;
import info.kgeorgiy.java.advanced.implementor.basic.classes.standard.*;
import info.kgeorgiy.java.advanced.implementor.full.classes.ClassWithPackagePrivateConstructor;
import info.kgeorgiy.java.advanced.implementor.full.classes.standard.*;
import org.junit.Test;

import javax.annotation.processing.Completions;
import java.util.Formatter;

/**
 * Full tests for hard version
 * of <a href="https://www.kgeorgiy.info/courses/java-advanced/homeworks.html#homework-implementor">Implementor</a> homework
 * for <a href="https://www.kgeorgiy.info/courses/java-advanced/">Java Advanced</a> course.
 *
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public class ClassImplementorTest extends InterfaceImplementorTest {
    @Test
    public void test11_defaultConstructorClasses() {
        test(false, AbstractClassWithInterface.class, BMPImageWriteParam.class, RelationNotFoundException.class);
    }

    @Test
    public void test12_noDefaultConstructorClasses() {
        test(false, IIOException.class, ImmutableDescriptor.class, LdapReferralException.class, ClassLogger.class);
    }

    @Test
    public void test13_ambiguousConstructorClasses() {
        test(false, IIOImage.class);
    }

    @Test
    public void test14_utilityClasses() {
        test(true, Completions.class);
    }

    @Test
    public void test15_finalClasses() {
        test(true, Integer.class, String.class);
    }

    @Test
    public void test16_standardNonClasses() {
        test(true, void.class, String[].class, int[].class, String.class, boolean.class);
    }

    @Test
    public void test17_constructorThrows() {
        test(false, FileCacheImageInputStream.class);
    }

    @Test
    public void test18_nonPublicAbstractMethod() {
        test(false, RMIServerImpl.class, RMIIIOPServerImpl.class);
    }

    @Test
    public void test19_enum() {
        test(true, Enum.class, Formatter.BigDecimalLayoutForm.class);
    }

    @Test
    public void test20_packagePrivateConstructor() {
        test(false, ClassWithPackagePrivateConstructor.class);
    }
}
