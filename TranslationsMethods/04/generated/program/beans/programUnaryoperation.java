package program.beans;

import program.*;
import java.util.ArrayList;
import java.util.List;

public class programUnaryoperation extends programNode {

    private final List<programNode> children = new ArrayList<>();

    public programUnaryoperation(programLexer lexer, programNode parent) throws program.exception.ParseException {
        programToken token = lexer.getCurrentToken();
        try {
            switch (token.getType()) {
				case Not:
					children.add(new programTerminal(programEnum.Not, lexer));
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