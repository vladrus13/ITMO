package function.beans;

import function.*;
import java.util.ArrayList;
import java.util.List;

public class functionT0 extends functionNode {

    private final List<functionNode> children = new ArrayList<>();

    public functionT0(functionLexer lexer, functionNode parent) throws function.exception.ParseException {
        functionToken token = lexer.getCurrentToken();
        try {
            switch (token.getType()) {
				case Comma:
					children.add(new functionTerminal(functionEnum.Comma, lexer));
					children.add(new functionVariables(lexer, this));
					string = ", " + children.get(1).string;
					break;

                default:
					string = "";

            }
        } catch (Exception exception) {
            throw new function.exception.ParseException("Get exception while parse function", exception);
        }
    }
}