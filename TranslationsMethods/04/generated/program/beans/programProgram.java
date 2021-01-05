package program.beans;

import program.*;
import java.util.ArrayList;
import java.util.List;

public class programProgram extends programNode {

    private final List<programNode> children = new ArrayList<>();

    public programProgram(programLexer lexer, programNode parent) throws program.exception.ParseException {
        programToken token = lexer.getCurrentToken();
        try {
            switch (token.getType()) {
				case Include:
					children.add(new programHeaders(lexer, this));
					children.add(new programBlocks(lexer, this));
					depth = 0;
					s = children.get(0).s + "\n" + children.get(1).s;
					break;
                default:
					throw new program.exception.UnexpectedTokenException("Unexpected token exception. Actual: " + token.getType());
            }
        } catch (Exception exception) {
            throw new program.exception.ParseException("Get exception while parse program", exception);
        }
    }
}