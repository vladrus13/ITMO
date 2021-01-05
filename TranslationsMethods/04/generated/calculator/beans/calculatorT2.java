package calculator.beans;

import calculator.*;
import java.util.ArrayList;
import java.util.List;

public class calculatorT2 extends calculatorNode {

    public calculatorT2(calculatorLexer lexer, calculatorNode parent) throws calculator.exception.ParseException {
        calculatorToken token = lexer.getCurrentToken();
        try {
            switch (token.getType()) {
				case Plus:
					children.add(new calculatorT0(lexer, this));
					val = children.get(0).val;
					break;

                default:
					val = 0;

            }
        } catch (Exception exception) {
            throw new calculator.exception.ParseException("Get exception while parse calculator", exception);
        }
    }
}