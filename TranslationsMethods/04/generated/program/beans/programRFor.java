package program.beans;

import program.*;
import java.util.ArrayList;
import java.util.List;

public class programRFor extends programNode {

    private final List<programNode> children = new ArrayList<>();

    public programRFor(programLexer lexer, programNode parent) throws program.exception.ParseException {
        programToken token = lexer.getCurrentToken();
        try {
            switch (token.getType()) {
				case For:
					children.add(new programTerminal(programEnum.For, lexer));
					children.add(new programTerminal(programEnum.OpenBracket, lexer));
					children.add(new programField(lexer, this));
					children.add(new programMaybeexpression(lexer, this));
					children.add(new programExpression(lexer, this));
					children.add(new programTerminal(programEnum.CloseBracket, lexer));
					children.add(new programTerminal(programEnum.OpenFigureBracket, lexer));
					children.add(new programBody(lexer, this));
					children.add(new programTerminal(programEnum.CloseFigureBracket, lexer));
					depth = parent.depth + 1;
					s = "for (" + children.get(2).s + " " + children.get(3).s + " " + children.get(4).s + ") {\n\t" + children.get(7).s.replace("\n", "\n\t") + "\n}";
					break;
                default:
					throw new program.exception.UnexpectedTokenException("Unexpected token exception. Actual: " + token.getType());
            }
        } catch (Exception exception) {
            throw new program.exception.ParseException("Get exception while parse program", exception);
        }
    }
}