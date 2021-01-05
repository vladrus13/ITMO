package program.beans;

import program.*;
import java.util.ArrayList;
import java.util.List;

public class programInstruction extends programNode {

    private final List<programNode> children = new ArrayList<>();

    public programInstruction(programLexer lexer, programNode parent) throws program.exception.ParseException {
        programToken token = lexer.getCurrentToken();
        try {
            switch (token.getType()) {
				case Delete, Break, Return, Continue:
					children.add(new programPartInstruction(lexer, this));
					children.add(new programTerminal(programEnum.Semicolon, lexer));
					depth = parent.depth;
					s = children.get(0).s + ";";
					break;
                default:
					throw new program.exception.UnexpectedTokenException("Unexpected token exception. Actual: " + token.getType());
            }
        } catch (Exception exception) {
            throw new program.exception.ParseException("Get exception while parse program", exception);
        }
    }
}