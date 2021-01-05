package program.beans;

import program.*;
import java.util.ArrayList;
import java.util.List;

public class programPartInstruction extends programNode {

    private final List<programNode> children = new ArrayList<>();

    public programPartInstruction(programLexer lexer, programNode parent) throws program.exception.ParseException {
        programToken token = lexer.getCurrentToken();
        try {
            switch (token.getType()) {
				case Delete:
					children.add(new programTerminal(programEnum.Delete, lexer));
					s = "delete";
					break;
				case Continue:
					children.add(new programTerminal(programEnum.Continue, lexer));
					s = "continue";
					break;
				case Break:
					children.add(new programTerminal(programEnum.Break, lexer));
					s = "break";
					break;
				case Return:
					children.add(new programTerminal(programEnum.Return, lexer));
					children.add(new programExpression(lexer, this));
					s = "return " + children.get(1).s;
					break;
                default:
					throw new program.exception.UnexpectedTokenException("Unexpected token exception. Actual: " + token.getType());
            }
        } catch (Exception exception) {
            throw new program.exception.ParseException("Get exception while parse program", exception);
        }
    }
}