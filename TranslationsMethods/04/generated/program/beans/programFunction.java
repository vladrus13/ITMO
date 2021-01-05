package program.beans;

import program.*;
import java.util.ArrayList;
import java.util.List;

public class programFunction extends programNode {

    private final List<programNode> children = new ArrayList<>();

    public programFunction(programLexer lexer, programNode parent) throws program.exception.ParseException {
        programToken token = lexer.getCurrentToken();
        try {
            switch (token.getType()) {
				case Auto, LongDouble, LongLong, Bool, String, Void, Double, Vector, Int:
					children.add(new programType(lexer, this));
					children.add(new programTerminal(programEnum.Word, lexer));
					children.add(new programTerminal(programEnum.OpenBracket, lexer));
					children.add(new programArguments(lexer, this));
					children.add(new programTerminal(programEnum.CloseBracket, lexer));
					children.add(new programFunctionBody(lexer, this));
					depth = parent.depth + 1;
					s = children.get(0).s + " " + ((programTerminal)children.get(1)).getData() + "(" + children.get(3).s + ")" + children.get(5).s;
					break;
                default:
					throw new program.exception.UnexpectedTokenException("Unexpected token exception. Actual: " + token.getType());
            }
        } catch (Exception exception) {
            throw new program.exception.ParseException("Get exception while parse program", exception);
        }
    }
}