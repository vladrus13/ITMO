/**
 * Tests for <a href="https://www.kgeorgiy.info/courses/java-advanced/homeworks.html#homework-implementor">Implementor</a> homework
 * of <a href="https://www.kgeorgiy.info/courses/java-advanced/">Java Advanced</a> course.
 *
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
module info.kgeorgiy.java.advanced.implementor {
    requires transitive info.kgeorgiy.java.advanced.base;

    requires java.management;
    requires java.management.rmi;
    requires java.compiler;
    requires java.sql;
    requires java.sql.rowset;
    requires java.desktop;

    exports info.kgeorgiy.java.advanced.implementor;

    opens info.kgeorgiy.java.advanced.implementor.basic.interfaces;
    opens info.kgeorgiy.java.advanced.implementor.basic.interfaces.standard;
    opens info.kgeorgiy.java.advanced.implementor.basic.classes;
    opens info.kgeorgiy.java.advanced.implementor.basic.classes.standard;

    opens info.kgeorgiy.java.advanced.implementor.full.interfaces;
    opens info.kgeorgiy.java.advanced.implementor.full.interfaces.standard;
    opens info.kgeorgiy.java.advanced.implementor.full.classes;
    opens info.kgeorgiy.java.advanced.implementor.full.classes.standard;
    opens info.kgeorgiy.java.advanced.implementor.full.lang;

    opens info.kgeorgiy.java.advanced.implementor to junit;
}
