package cbs.exception;

/**
 * Class exception for unsupported characters
 */
public class UnsupportedCharacterException extends cbs.exception.ParseException {

    /**
     * Constructor for class
     * @param message message
     */
    public UnsupportedCharacterException(String message) {
        super(message);
    }

}