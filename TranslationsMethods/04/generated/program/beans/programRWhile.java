package program.beans;

import program.*;
import java.util.ArrayList;
import java.util.List;

public class programRWhile extends programNode {

    private final List<programNode> children = new ArrayList<>();

    public programRWhile(programLexer lexer, programNode parent) throws program.exception.ParseException {
        programToken token = lexer.getCurrentToken();
        try {
            switch (token.getType()) {
				case While:
					children.add(new programTerminal(programEnum.While, lexer));
					children.add(new programTerminal(programEnum.OpenBracket, lexer));
					children.add(new programExpression(lexer, this));
					children.add(new programTerminal(programEnum.CloseBracket, lexer));
					children.add(new programTerminal(programEnum.OpenFigureBracket, lexer));
					children.add(new programBody(lexer, this));
					children.add(new programTerminal(programEnum.CloseFigureBracket, lexer));
					depth = parent.depth + 1;
					s = "while (" + children.get(2).s + ") {\n\t" + children.get(5).s.replace("\n", "\n\t") + "\n}";
					break;
                default:
					throw new program.exception.UnexpectedTokenException("Unexpected token exception. Actual: " + token.getType());
            }
        } catch (Exception exception) {
            throw new program.exception.ParseException("Get exception while parse program", exception);
        }
    }
}