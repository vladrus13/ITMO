package expression.exceptions;

public class ValueException extends Exception{
    public ValueException(String s) {
        super("\nERROR\nValueException\n" + s + '\n');
    }
}
