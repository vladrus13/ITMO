package function.beans;

import function.*;
import java.util.ArrayList;
import java.util.List;

public class functionVariables extends functionNode {

    private final List<functionNode> children = new ArrayList<>();

    public functionVariables(functionLexer lexer, functionNode parent) throws function.exception.ParseException {
        functionToken token = lexer.getCurrentToken();
        try {
            switch (token.getType()) {
				case Name:
					children.add(new functionVariable(lexer, this));
					children.add(new functionT0(lexer, this));
					string = children.get(0).string + children.get(1).string;
					break;

                default:
					string = "";

            }
        } catch (Exception exception) {
            throw new function.exception.ParseException("Get exception while parse function", exception);
        }
    }
}