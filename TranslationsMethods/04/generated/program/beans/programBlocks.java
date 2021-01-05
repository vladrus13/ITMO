package program.beans;

import program.*;
import java.util.ArrayList;
import java.util.List;

public class programBlocks extends programNode {

    private final List<programNode> children = new ArrayList<>();

    public programBlocks(programLexer lexer, programNode parent) throws program.exception.ParseException {
        programToken token = lexer.getCurrentToken();
        try {
            switch (token.getType()) {
				case Using, Auto, LongDouble, Class, LongLong, Bool, String, Void, Double, Vector, Struct, Int:
					children.add(new programBlock(lexer, this));
					children.add(new programBlocks(lexer, this));
					s = children.get(0).s + "\n\n" + children.get(1).s;
					break;

                default:
					s = "";

            }
        } catch (Exception exception) {
            throw new program.exception.ParseException("Get exception while parse program", exception);
        }
    }
}