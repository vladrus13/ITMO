package program.beans;

import program.*;
import java.util.ArrayList;
import java.util.List;

public class programArgument extends programNode {

    private final List<programNode> children = new ArrayList<>();

    public programArgument(programLexer lexer, programNode parent) throws program.exception.ParseException {
        programToken token = lexer.getCurrentToken();
        try {
            switch (token.getType()) {
				case Auto, LongDouble, LongLong, Bool, String, Void, Double, Vector, Int:
					children.add(new programType(lexer, this));
					children.add(new programReferences(lexer, this));
					children.add(new programTerminal(programEnum.Word, lexer));
					s = children.get(0).s + children.get(1).s + " " + ((programTerminal)children.get(2)).getData();
					break;
                default:
					throw new program.exception.UnexpectedTokenException("Unexpected token exception. Actual: " + token.getType());
            }
        } catch (Exception exception) {
            throw new program.exception.ParseException("Get exception while parse program", exception);
        }
    }
}