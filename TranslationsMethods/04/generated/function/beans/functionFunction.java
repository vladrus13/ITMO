package function.beans;

import function.*;
import java.util.ArrayList;
import java.util.List;

public class functionFunction extends functionNode {

    private final List<functionNode> children = new ArrayList<>();

    public functionFunction(functionLexer lexer, functionNode parent) throws function.exception.ParseException {
        functionToken token = lexer.getCurrentToken();
        try {
            switch (token.getType()) {
				case Name:
					children.add(new functionVariable(lexer, this));
					children.add(new functionTerminal(functionEnum.OpenBracket, lexer));
					children.add(new functionVariables(lexer, this));
					children.add(new functionTerminal(functionEnum.CloseBracket, lexer));
					children.add(new functionTerminal(functionEnum.Semicolon, lexer));
					string = children.get(0).string + "(" + children.get(2).string + ");";
					break;
                default:
					throw new function.exception.UnexpectedTokenException("Unexpected token exception. Actual: " + token.getType());
            }
        } catch (Exception exception) {
            throw new function.exception.ParseException("Get exception while parse function", exception);
        }
    }
}