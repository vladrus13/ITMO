package expression.exceptions;

public class MissingOperatorException extends ParsingException{
    public MissingOperatorException(String c) {
        super(c);
    }
}
