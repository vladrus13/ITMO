package program.beans;

import program.*;
import java.util.ArrayList;
import java.util.List;

public class programSingleassignoperator extends programNode {

    private final List<programNode> children = new ArrayList<>();

    public programSingleassignoperator(programLexer lexer, programNode parent) throws program.exception.ParseException {
        programToken token = lexer.getCurrentToken();
        try {
            switch (token.getType()) {
				case PlusPlus:
					children.add(new programTerminal(programEnum.PlusPlus, lexer));
					s = "++";
					break;
				case MinusMinus:
					children.add(new programTerminal(programEnum.MinusMinus, lexer));
					s = "--";
					break;
                default:
					throw new program.exception.UnexpectedTokenException("Unexpected token exception. Actual: " + token.getType());
            }
        } catch (Exception exception) {
            throw new program.exception.ParseException("Get exception while parse program", exception);
        }
    }
}