package program.exception;

/**
 * Class-parent for all exception
 */
public class ParseException extends Exception {

    /**
     * Constructor for class
     * @param message message
     */
    public ParseException(String message) {
        super(message);
    }

    /**
     * Constructor for class
     * @param message message
     * @param cause cause
     */
    public ParseException(String message, Throwable cause) {
        super(message, cause);
    }
}