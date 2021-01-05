package program.beans;

import program.*;
import java.util.ArrayList;
import java.util.List;

public class programRClass extends programNode {

    private final List<programNode> children = new ArrayList<>();

    public programRClass(programLexer lexer, programNode parent) throws program.exception.ParseException {
        programToken token = lexer.getCurrentToken();
        try {
            switch (token.getType()) {
				case Class:
					children.add(new programTerminal(programEnum.Class, lexer));
					children.add(new programTerminal(programEnum.Word, lexer));
					children.add(new programTerminal(programEnum.OpenFigureBracket, lexer));
					children.add(new programClassorstructbody(lexer, this));
					children.add(new programTerminal(programEnum.CloseFigureBracket, lexer));
					depth = parent.depth + 1;
					s = "class " + ((programTerminal)children.get(1)).getData() + "{\n\t" + children.get(3).s.replace("\n", "\n\t") + "\n}";
					break;
                default:
					throw new program.exception.UnexpectedTokenException("Unexpected token exception. Actual: " + token.getType());
            }
        } catch (Exception exception) {
            throw new program.exception.ParseException("Get exception while parse program", exception);
        }
    }
}