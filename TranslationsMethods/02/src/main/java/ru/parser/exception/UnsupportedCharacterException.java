package ru.parser.exception;

/**
 * Class exception for unsupported characters
 */
public class UnsupportedCharacterException extends ParseException {

    /**
     * Constructor for class
     * @param message message
     */
    public UnsupportedCharacterException(String message) {
        super(message);
    }

}
