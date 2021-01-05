package program.beans;

import program.*;
import java.util.ArrayList;
import java.util.List;

public class programArguments extends programNode {

    private final List<programNode> children = new ArrayList<>();

    public programArguments(programLexer lexer, programNode parent) throws program.exception.ParseException {
        programToken token = lexer.getCurrentToken();
        try {
            switch (token.getType()) {
				case Auto, LongDouble, LongLong, Bool, String, Void, Double, Vector, Int:
					children.add(new programArgument(lexer, this));
					children.add(new programArguments(lexer, this));
					s = children.get(0).s + children.get(1).s;
					break;

                default:
					s = "";

            }
        } catch (Exception exception) {
            throw new program.exception.ParseException("Get exception while parse program", exception);
        }
    }
}