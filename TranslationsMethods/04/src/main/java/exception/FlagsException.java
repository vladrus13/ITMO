package exception;

public class FlagsException extends Exception {
    public FlagsException(String message) {
        super(message);
    }

    public FlagsException(String message, Throwable cause) {
        super(message, cause);
    }
}
