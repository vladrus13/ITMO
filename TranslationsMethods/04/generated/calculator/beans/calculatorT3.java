package calculator.beans;

import calculator.*;
import java.util.ArrayList;
import java.util.List;

public class calculatorT3 extends calculatorNode {

    public calculatorT3(calculatorLexer lexer, calculatorNode parent) throws calculator.exception.ParseException {
        calculatorToken token = lexer.getCurrentToken();
        try {
            switch (token.getType()) {
				case Multiply:
					children.add(new calculatorT1Multiplied(lexer, this));
					val = children.get(0).val;
					break;
				case Divide:
					children.add(new calculatorT1Divided(lexer, this));
					val = children.get(0).val;
					break;

                default:
					val = 1;

            }
        } catch (Exception exception) {
            throw new calculator.exception.ParseException("Get exception while parse calculator", exception);
        }
    }
}