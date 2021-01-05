package program.beans;

import program.*;
import java.util.ArrayList;
import java.util.List;

public class programNumber extends programNode {

    private final List<programNode> children = new ArrayList<>();

    public programNumber(programLexer lexer, programNode parent) throws program.exception.ParseException {
        programToken token = lexer.getCurrentToken();
        try {
            switch (token.getType()) {
				case Digits:
					children.add(new programTerminal(programEnum.Digits, lexer));
					s = ((programTerminal)children.get(0)).getData();
					break;
				case True, False:
					children.add(new programBoolean(lexer, this));
					s = children.get(0).s;
					break;
                default:
					throw new program.exception.UnexpectedTokenException("Unexpected token exception. Actual: " + token.getType());
            }
        } catch (Exception exception) {
            throw new program.exception.ParseException("Get exception while parse program", exception);
        }
    }
}