package program.beans;

import program.*;
import java.util.ArrayList;
import java.util.List;

public class programField extends programNode {

    private final List<programNode> children = new ArrayList<>();

    public programField(programLexer lexer, programNode parent) throws program.exception.ParseException {
        programToken token = lexer.getCurrentToken();
        try {
            switch (token.getType()) {
				case Auto, LongDouble, LongLong, Bool, String, Void, Double, Vector, Int:
					children.add(new programType(lexer, this));
					children.add(new programExpression(lexer, this));
					children.add(new programTerminal(programEnum.Semicolon, lexer));
					s = children.get(0).s + " " + children.get(1).s + ";";
					break;
                default:
					throw new program.exception.UnexpectedTokenException("Unexpected token exception. Actual: " + token.getType());
            }
        } catch (Exception exception) {
            throw new program.exception.ParseException("Get exception while parse program", exception);
        }
    }
}