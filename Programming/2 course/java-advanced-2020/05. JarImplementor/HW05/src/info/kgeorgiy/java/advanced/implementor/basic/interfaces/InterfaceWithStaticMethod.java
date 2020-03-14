package info.kgeorgiy.java.advanced.implementor.basic.interfaces;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public interface InterfaceWithStaticMethod {
    int hello();
    static void staticMethod() {
        System.out.println("staticMethod");
    }
}
