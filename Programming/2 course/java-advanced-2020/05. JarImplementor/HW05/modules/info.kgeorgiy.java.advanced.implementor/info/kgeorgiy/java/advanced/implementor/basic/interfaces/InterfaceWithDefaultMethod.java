package info.kgeorgiy.java.advanced.implementor.basic.interfaces;

/**
 * Basic interface basic for easy version
 * of <a href="https://www.kgeorgiy.info/courses/java-advanced/homeworks.html#homework-implementor">Implementor</a> homework
 * for <a href="https://www.kgeorgiy.info/courses/java-advanced/">Java Advanced</a> course.
 *
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public interface InterfaceWithDefaultMethod {
    int hello();
    default void defaultMethod() {
        System.out.println("defaultMethod");
    }
}
