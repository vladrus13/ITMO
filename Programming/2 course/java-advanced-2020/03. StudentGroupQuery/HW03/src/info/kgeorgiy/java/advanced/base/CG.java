package info.kgeorgiy.java.advanced.base;

/**
 * Certificate generator.
 *
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
@FunctionalInterface
public interface CG {
    void certify(final Class<?> token, final String salt);
}
