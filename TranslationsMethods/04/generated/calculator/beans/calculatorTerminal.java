package calculator.beans;

import calculator.calculatorEnum;
import calculator.calculatorLexer;
import calculator.calculatorToken;

public class calculatorTerminal extends calculatorNode {
    private final calculatorEnum token;
    private final String data;

    public calculatorTerminal(calculatorEnum token, String data) {
        this.token = token;
        this.data = data;
    }

    public calculatorTerminal(calculatorEnum expected, calculatorLexer lexer) throws calculator.exception.ParseException {
        calculatorToken realToken = lexer.getCurrentToken();
        if (realToken.getType() != expected) {
            throw new calculator.exception.UnexpectedTokenException("Unexprected token. Expected: " + expected.name() + ". Actual: " + realToken.getType());
        }
        token = realToken.getType();
        data = realToken.getData();
        lexer.nextToken();
    }

    public calculatorEnum getToken() {
        return token;
    }

    public String getData() {
        return data;
    }
}