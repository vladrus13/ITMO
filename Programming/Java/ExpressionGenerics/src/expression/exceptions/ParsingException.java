package expression.exceptions;

public class ParsingException extends Exception {
    public ParsingException(String s) {
        super("\nERROR\nParsing error\n" + s + '\n');
    }
}
