/**
 * Tests for <a href="https://www.kgeorgiy.info/courses/java-advanced/homeworks.html#homework-arrayset">ArraySet</a> homework
 * of <a href="https://www.kgeorgiy.info/courses/java-advanced/">Java Advanced</a> course.
 *
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
module info.kgeorgiy.java.advanced.arrayset {
    requires transitive quickcheck;

    requires transitive info.kgeorgiy.java.advanced.base;

    exports info.kgeorgiy.java.advanced.arrayset;

    opens info.kgeorgiy.java.advanced.arrayset to junit;
}
