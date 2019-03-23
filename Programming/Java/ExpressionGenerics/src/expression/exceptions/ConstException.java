package expression.exceptions;

public class ConstException extends ValueException {
    public ConstException(String s) {
        super("VERY large value " + s);
    }

}
