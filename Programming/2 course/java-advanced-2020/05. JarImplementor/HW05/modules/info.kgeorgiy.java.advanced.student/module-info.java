/**
 * Tests for <a href="https://www.kgeorgiy.info/courses/java-advanced/homeworks.html#homework-student">Student</a> homework
 * of <a href="https://www.kgeorgiy.info/courses/java-advanced/">Java Advanced</a> course.
 *
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
module info.kgeorgiy.java.advanced.student {
    requires transitive info.kgeorgiy.java.advanced.base;

    exports info.kgeorgiy.java.advanced.student;

    opens info.kgeorgiy.java.advanced.student to junit;
}
