package calculator.exception;

/**
 * Class exception for unsupported characters
 */
public class UnsupportedCharacterException extends calculator.exception.ParseException {

    /**
     * Constructor for class
     * @param message message
     */
    public UnsupportedCharacterException(String message) {
        super(message);
    }

}