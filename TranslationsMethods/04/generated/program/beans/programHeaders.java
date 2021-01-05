package program.beans;

import program.*;
import java.util.ArrayList;
import java.util.List;

public class programHeaders extends programNode {

    private final List<programNode> children = new ArrayList<>();

    public programHeaders(programLexer lexer, programNode parent) throws program.exception.ParseException {
        programToken token = lexer.getCurrentToken();
        try {
            switch (token.getType()) {
				case Include:
					children.add(new programHeader(lexer, this));
					children.add(new programHeaders(lexer, this));
					s = children.get(0).s + "\n" + children.get(1).s;
					break;

                default:
					s = "";

            }
        } catch (Exception exception) {
            throw new program.exception.ParseException("Get exception while parse program", exception);
        }
    }
}