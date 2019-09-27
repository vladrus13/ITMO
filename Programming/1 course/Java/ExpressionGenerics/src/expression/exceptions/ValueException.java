package expression.exceptions;

public class ValueException extends ParsingException {
    public ValueException(String s) {
        super("\nERROR\nValueException\n" + s + '\n');
    }
}
