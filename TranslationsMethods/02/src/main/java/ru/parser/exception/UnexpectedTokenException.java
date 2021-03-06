package ru.parser.exception;

/**
 * Class for unexpected token
 */
public class UnexpectedTokenException extends ParseException {

    /**
     * Constructor for class
     * @param message message
     */
    public UnexpectedTokenException(String message) {
        super(message);
    }

    /**
     * Constructor for class
     * @param message message
     * @param cause cause
     */
    public UnexpectedTokenException(String message, Throwable cause) {
        super(message, cause);
    }
}
