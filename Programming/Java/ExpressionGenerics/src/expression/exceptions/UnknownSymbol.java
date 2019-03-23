package expression.exceptions;

public class UnknownSymbol extends ParsingException {
    public UnknownSymbol(String s) {
        super(s);
    }
}
