package program.beans;

import program.*;
import java.util.ArrayList;
import java.util.List;

public class programBlock extends programNode {

    private final List<programNode> children = new ArrayList<>();

    public programBlock(programLexer lexer, programNode parent) throws program.exception.ParseException {
        programToken token = lexer.getCurrentToken();
        try {
            switch (token.getType()) {
				case Auto, LongDouble, LongLong, Bool, String, Void, Double, Vector, Int:
					children.add(new programFunction(lexer, this));
					s = children.get(0).s;
					break;
				case Class:
					children.add(new programRClass(lexer, this));
					s = children.get(0).s;
					break;
				case Struct:
					children.add(new programStruct(lexer, this));
					s = children.get(0).s;
					break;
				case Using:
					children.add(new programUsing(lexer, this));
					s = children.get(0).s;
					break;
                default:
					throw new program.exception.UnexpectedTokenException("Unexpected token exception. Actual: " + token.getType());
            }
        } catch (Exception exception) {
            throw new program.exception.ParseException("Get exception while parse program", exception);
        }
    }
}