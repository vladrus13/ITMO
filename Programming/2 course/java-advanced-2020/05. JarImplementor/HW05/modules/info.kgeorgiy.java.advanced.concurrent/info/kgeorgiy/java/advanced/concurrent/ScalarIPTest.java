package info.kgeorgiy.java.advanced.concurrent;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.util.Collections;
import java.util.stream.Stream;

/**
 * Basic tests for easy version
 * of <a href="https://www.kgeorgiy.info/courses/java-advanced/homeworks.html#homework-implementor">Implementor</a> homework
 * for <a href="https://www.kgeorgiy.info/courses/java-advanced/">Java Advanced</a> course.
 *
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ScalarIPTest<P extends ScalarIP> extends BaseIPTest<P> {
    @Test
    public void test01_maximum() throws InterruptedException {
        test(Collections::max, ScalarIP::maximum, COMPARATORS);
    }

    @Test
    public void test03_all() throws InterruptedException {
        testS(Stream::allMatch, ScalarIP::all, PREDICATES);
    }
}
