package program.beans;

import program.*;
import java.util.ArrayList;
import java.util.List;

public class programMaybesingleassignoperator extends programNode {

    private final List<programNode> children = new ArrayList<>();

    public programMaybesingleassignoperator(programLexer lexer, programNode parent) throws program.exception.ParseException {
        programToken token = lexer.getCurrentToken();
        try {
            switch (token.getType()) {
				case MinusMinus, PlusPlus:
					children.add(new programSingleassignoperator(lexer, this));
					s = children.get(0).s;
					break;

                default:
					s = "";

            }
        } catch (Exception exception) {
            throw new program.exception.ParseException("Get exception while parse program", exception);
        }
    }
}