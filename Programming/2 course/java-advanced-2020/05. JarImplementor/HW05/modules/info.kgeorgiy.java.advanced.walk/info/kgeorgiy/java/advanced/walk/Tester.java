package info.kgeorgiy.java.advanced.walk;

import info.kgeorgiy.java.advanced.base.BaseTester;

/**
 * Test runner
 * for <a href="https://www.kgeorgiy.info/courses/java-advanced/homeworks.html#homework-walk">Walk</a> homework
 * if <a href="https://www.kgeorgiy.info/courses/java-advanced/">Java Advanced</a> course.
 *
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public class Tester extends BaseTester {
    public static void main(final String... args) {
        new Tester()
                .add("Walk", WalkTest.class)
                .add("RecursiveWalk", RecursiveWalkTest.class)
                .run(args);
    }
}
