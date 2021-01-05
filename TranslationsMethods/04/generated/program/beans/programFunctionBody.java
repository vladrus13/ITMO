package program.beans;

import program.*;
import java.util.ArrayList;
import java.util.List;

public class programFunctionBody extends programNode {

    private final List<programNode> children = new ArrayList<>();

    public programFunctionBody(programLexer lexer, programNode parent) throws program.exception.ParseException {
        programToken token = lexer.getCurrentToken();
        try {
            switch (token.getType()) {
				case OpenFigureBracket:
					children.add(new programTerminal(programEnum.OpenFigureBracket, lexer));
					children.add(new programBody(lexer, this));
					children.add(new programTerminal(programEnum.CloseFigureBracket, lexer));
					depth = parent.depth;
					s = " {\n\t" + children.get(1).s.replace("\n", "\n\t") + "\n}";
					break;
				case Semicolon:
					children.add(new programTerminal(programEnum.Semicolon, lexer));
					s = ";";
					break;
                default:
					throw new program.exception.UnexpectedTokenException("Unexpected token exception. Actual: " + token.getType());
            }
        } catch (Exception exception) {
            throw new program.exception.ParseException("Get exception while parse program", exception);
        }
    }
}