package calculator.exception;

/**
 * Class for unexpected token
 */
public class UnexpectedTokenException extends calculator.exception.ParseException {

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