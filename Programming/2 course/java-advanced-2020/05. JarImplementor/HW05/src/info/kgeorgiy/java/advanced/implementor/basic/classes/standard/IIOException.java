package info.kgeorgiy.java.advanced.implementor.basic.classes.standard;

import java.io.IOException;

/**
 * Copy of {@link javax.imageio.IIOException}
 */
public class IIOException extends IOException {
    private static final long serialVersionUID = -3216210718638985251L;

    /**
     * Constructs an {@code IIOException} with a given message
     * {@code String}.  No underlying cause is set;
     * {@code getCause} will return {@code null}.
     *
     * @param message the error message.
     *
     * @see #getMessage
     */
    public IIOException(String message) {
        super(message);
    }

    /**
     * Constructs an {@code IIOException} with a given message
     * {@code String} and a {@code Throwable} that was its
     * underlying cause.
     *
     * @param message the error message.
     * @param cause the {@code Throwable} ({@code Error} or
     * {@code Exception}) that caused this exception to occur.
     *
     * @see #getCause
     * @see #getMessage
     */
    public IIOException(String message, Throwable cause) {
        super(message);
        initCause(cause);
    }
}
