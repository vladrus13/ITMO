package expression.exceptions;

public class ValueException extends Exception{
    public ValueException(String s) {
        super("\nAm I a joke to you?\nValueException\n" + s);
    }
}
