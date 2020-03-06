package prolog.prtest;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public class PrologException extends RuntimeException {
    public PrologException(final String message) {
        super(message);
    }

    public PrologException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
