package function.beans;

import function.*;
import java.util.ArrayList;
import java.util.List;

public class functionReferences extends functionNode {

    private final List<functionNode> children = new ArrayList<>();

    public functionReferences(functionLexer lexer, functionNode parent) throws function.exception.ParseException {
        functionToken token = lexer.getCurrentToken();
        try {
            switch (token.getType()) {
				case Reference:
					children.add(new functionTerminal(functionEnum.Reference, lexer));
					children.add(new functionReferences(lexer, this));
					string = "*" + children.get(1).string;
					break;

                default:
					string = "";

            }
        } catch (Exception exception) {
            throw new function.exception.ParseException("Get exception while parse function", exception);
        }
    }
}