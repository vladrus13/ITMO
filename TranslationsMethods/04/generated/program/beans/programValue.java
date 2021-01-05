package program.beans;

import program.*;
import java.util.ArrayList;
import java.util.List;

public class programValue extends programNode {

    private final List<programNode> children = new ArrayList<>();

    public programValue(programLexer lexer, programNode parent) throws program.exception.ParseException {
        programToken token = lexer.getCurrentToken();
        try {
            switch (token.getType()) {
				case Quotes:
					children.add(new programAnyinquotes(lexer, this));
					s = children.get(0).s;
					break;
				case Digits, True, False:
					children.add(new programNumber(lexer, this));
					s = children.get(0).s;
					break;
				case Word:
					children.add(new programTerminal(programEnum.Word, lexer));
					s = ((programTerminal)children.get(0)).getData();
					break;
                default:
					throw new program.exception.UnexpectedTokenException("Unexpected token exception. Actual: " + token.getType());
            }
        } catch (Exception exception) {
            throw new program.exception.ParseException("Get exception while parse program", exception);
        }
    }
}