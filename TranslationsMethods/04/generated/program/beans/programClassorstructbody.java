package program.beans;

import program.*;
import java.util.ArrayList;
import java.util.List;

public class programClassorstructbody extends programNode {

    private final List<programNode> children = new ArrayList<>();

    public programClassorstructbody(programLexer lexer, programNode parent) throws program.exception.ParseException {
        programToken token = lexer.getCurrentToken();
        try {
            switch (token.getType()) {

				case Auto, LongDouble, LongLong, Bool, String, Void, Double, Vector, Int:
					children.add(new programClassorstructpart(lexer, this));
					depth = parent.depth;
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