package program.beans;

import program.*;
import java.util.ArrayList;
import java.util.List;

public class programBoolean extends programNode {

    private final List<programNode> children = new ArrayList<>();

    public programBoolean(programLexer lexer, programNode parent) throws program.exception.ParseException {
        programToken token = lexer.getCurrentToken();
        try {
            switch (token.getType()) {
				case True:
					children.add(new programTerminal(programEnum.True, lexer));
					s = "true";
					break;
				case False:
					children.add(new programTerminal(programEnum.False, lexer));
					s = "false";
					break;
                default:
					throw new program.exception.UnexpectedTokenException("Unexpected token exception. Actual: " + token.getType());
            }
        } catch (Exception exception) {
            throw new program.exception.ParseException("Get exception while parse program", exception);
        }
    }
}