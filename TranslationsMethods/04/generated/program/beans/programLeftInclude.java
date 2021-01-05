package program.beans;

import program.*;
import java.util.ArrayList;
import java.util.List;

public class programLeftInclude extends programNode {

    private final List<programNode> children = new ArrayList<>();

    public programLeftInclude(programLexer lexer, programNode parent) throws program.exception.ParseException {
        programToken token = lexer.getCurrentToken();
        try {
            switch (token.getType()) {
				case Less:
					children.add(new programTerminal(programEnum.Less, lexer));
					children.add(new programTerminal(programEnum.Word, lexer));
					children.add(new programTerminal(programEnum.Greater, lexer));
					s = "<" + ((programTerminal)children.get(1)).getData() + ((programTerminal)children.get(2)).getData();
					break;
				case Quotes:
					children.add(new programTerminal(programEnum.Quotes, lexer));
					children.add(new programTerminal(programEnum.Word, lexer));
					children.add(new programTerminal(programEnum.Quotes, lexer));
					s = "\"" + ((programTerminal)children.get(1)).getData() + "\"";
					break;
                default:
					throw new program.exception.UnexpectedTokenException("Unexpected token exception. Actual: " + token.getType());
            }
        } catch (Exception exception) {
            throw new program.exception.ParseException("Get exception while parse program", exception);
        }
    }
}