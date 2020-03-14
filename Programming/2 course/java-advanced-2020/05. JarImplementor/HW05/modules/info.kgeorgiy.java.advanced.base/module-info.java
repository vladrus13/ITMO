/**
 * Base test classes for <a href="https://www.kgeorgiy.info/courses/java-advanced/">Java Advanced</a> course.
 *
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
module info.kgeorgiy.java.advanced.base {
    requires transitive junit;

    exports info.kgeorgiy.java.advanced.base;
    opens info.kgeorgiy.java.advanced.base to junit;
}