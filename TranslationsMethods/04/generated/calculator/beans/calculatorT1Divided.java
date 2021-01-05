package calculator.beans;

import calculator.*;
import java.util.ArrayList;
import java.util.List;

public class calculatorT1Divided extends calculatorNode {

    public calculatorT1Divided(calculatorLexer lexer, calculatorNode parent) throws calculator.exception.ParseException {
        calculatorToken token = lexer.getCurrentToken();
        try {
            switch (token.getType()) {
				case Divide:
					children.add(new calculatorTerminal(calculatorEnum.Divide, lexer));
					children.add(new calculatorMultipliedordivided(lexer, this));
					children.add(new calculatorT3(lexer, this));
					val = children.get(1).val / children.get(2).val;
					break;
                default:
					throw new calculator.exception.UnexpectedTokenException("Unexpected token exception. Actual: " + token.getType());
            }
        } catch (Exception exception) {
            throw new calculator.exception.ParseException("Get exception while parse calculator", exception);
        }
    }
}