package base;

import java.util.Locale;
import java.util.Objects;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public class Asserts {
    static {
        Locale.setDefault(Locale.US);
    }

    public static void assertEquals(final String message, final Object expected, final Object actual) {
        assertTrue(String.format("%s:%n     expected `%s`,%n       actual `%s`", message, expected, actual), Objects.equals(expected, actual));
    }

    public static void assertTrue(final String message, final boolean value) {
        if (!value) {
            throw new AssertionError(message);
        }
    }

    public static void assertEquals(final String message, final int expected, final int actual) {
        assertTrue(String.format("%s: Expected %d, found %d", message, expected, actual), actual == expected);
    }

    public static void assertEquals(final String message, final double precision, final double expected, final double actual) {
        assertTrue(
                String.format("%s: Expected %.12f, found %.12f", message, expected, actual),
                Math.abs(actual - expected) < precision ||
                        Math.abs(actual - expected) < precision * Math.abs(actual) ||
                        (Double.isNaN(actual) || Double.isInfinite(actual)) &&
                                (Double.isNaN(expected) || Double.isInfinite(expected))
        );
    }

    public static void checkAssert(final Class<?> c) {
        if (!c.desiredAssertionStatus()) {
            throw new AssertionError("You should enable assertions by running 'java -ea " + c.getName() + "'");
        }
    }
}
