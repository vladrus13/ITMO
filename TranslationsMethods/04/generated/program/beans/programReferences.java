package program.beans;

import program.*;
import java.util.ArrayList;
import java.util.List;

public class programReferences extends programNode {

    private final List<programNode> children = new ArrayList<>();

    public programReferences(programLexer lexer, programNode parent) throws program.exception.ParseException {
        programToken token = lexer.getCurrentToken();
        try {
            switch (token.getType()) {
				case Multiply:
					children.add(new programTerminal(programEnum.Multiply, lexer));
					children.add(new programReferences(lexer, this));
					s = "*" + children.get(1).s;
					break;

                default:
					s = "";

            }
        } catch (Exception exception) {
            throw new program.exception.ParseException("Get exception while parse program", exception);
        }
    }
}