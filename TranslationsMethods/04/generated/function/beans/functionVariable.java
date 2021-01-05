package function.beans;

import function.*;
import java.util.ArrayList;
import java.util.List;

public class functionVariable extends functionNode {

    private final List<functionNode> children = new ArrayList<>();

    public functionVariable(functionLexer lexer, functionNode parent) throws function.exception.ParseException {
        functionToken token = lexer.getCurrentToken();
        try {
            switch (token.getType()) {
				case Name:
					children.add(new functionTerminal(functionEnum.Name, lexer));
					children.add(new functionReferences(lexer, this));
					children.add(new functionTerminal(functionEnum.Name, lexer));
					string = ((functionTerminal)children.get(0)).getData()
        + children.get(1).string + " "
        + ((functionTerminal)children.get(2)).getData();
					break;
                default:
					throw new function.exception.UnexpectedTokenException("Unexpected token exception. Actual: " + token.getType());
            }
        } catch (Exception exception) {
            throw new function.exception.ParseException("Get exception while parse function", exception);
        }
    }
}