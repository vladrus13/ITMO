package calculator.beans;

import calculator.*;
import java.util.ArrayList;
import java.util.List;

public class calculatorMultipliedordivided extends calculatorNode {

    public calculatorMultipliedordivided(calculatorLexer lexer, calculatorNode parent) throws calculator.exception.ParseException {
        calculatorToken token = lexer.getCurrentToken();
        try {
            switch (token.getType()) {
				case Number:
					children.add(new calculatorTerminal(calculatorEnum.Number, lexer));
					children.add(new calculatorT3(lexer, this));
					int temp = Integer.parseInt(((calculatorTerminal)children.get(0)).getData());
					if (children.get(1).children.size() != 0)
                    if (children.get(1).children.get(0) instanceof calculatorT1Multiplied)
                        val = temp * children.get(1).val;
                    else if (children.get(1).val == 0)
                        throw new ArithmeticException("Divide by zero!");
                        else val = temp / children.get(1).val;
                    else val = temp;
					break;
				case OpenBracket:
					children.add(new calculatorTerminal(calculatorEnum.OpenBracket, lexer));
					children.add(new calculatorAdded(lexer, this));
					children.add(new calculatorTerminal(calculatorEnum.CloseBracket, lexer));
					val = children.get(1).val;
					break;
                default:
					throw new calculator.exception.UnexpectedTokenException("Unexpected token exception. Actual: " + token.getType());
            }
        } catch (Exception exception) {
            throw new calculator.exception.ParseException("Get exception while parse calculator", exception);
        }
    }
}