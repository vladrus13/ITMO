package program.exception;

/**
 * Class exception for unsupported characters
 */
public class UnsupportedCharacterException extends program.exception.ParseException {

    /**
     * Constructor for class
     * @param message message
     */
    public UnsupportedCharacterException(String message) {
        super(message);
    }

}