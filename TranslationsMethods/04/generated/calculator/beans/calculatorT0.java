package calculator.beans;

import calculator.*;
import java.util.ArrayList;
import java.util.List;

public class calculatorT0 extends calculatorNode {

    public calculatorT0(calculatorLexer lexer, calculatorNode parent) throws calculator.exception.ParseException {
        calculatorToken token = lexer.getCurrentToken();
        try {
            switch (token.getType()) {
				case Plus:
					children.add(new calculatorTerminal(calculatorEnum.Plus, lexer));
					children.add(new calculatorAdded(lexer, this));
					children.add(new calculatorT2(lexer, this));
					val = children.get(2).val + children.get(1).val;
					break;
                default:
					throw new calculator.exception.UnexpectedTokenException("Unexpected token exception. Actual: " + token.getType());
            }
        } catch (Exception exception) {
            throw new calculator.exception.ParseException("Get exception while parse calculator", exception);
        }
    }
}