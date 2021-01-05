package program.beans;

import program.*;
import java.util.ArrayList;
import java.util.List;

public class programHeader extends programNode {

    private final List<programNode> children = new ArrayList<>();

    public programHeader(programLexer lexer, programNode parent) throws program.exception.ParseException {
        programToken token = lexer.getCurrentToken();
        try {
            switch (token.getType()) {
				case Include:
					children.add(new programTerminal(programEnum.Include, lexer));
					children.add(new programLeftInclude(lexer, this));
					s = "#include " + children.get(1).s;
					break;
                default:
					throw new program.exception.UnexpectedTokenException("Unexpected token exception. Actual: " + token.getType());
            }
        } catch (Exception exception) {
            throw new program.exception.ParseException("Get exception while parse program", exception);
        }
    }
}