package cbs.beans;

import cbs.*;
import java.util.ArrayList;
import java.util.List;

public class cbsS extends cbsNode {

    private final List<cbsNode> children = new ArrayList<>();

    public cbsS(cbsLexer lexer) throws cbs.exception.ParseException {
        cbsToken token = lexer.getCurrentToken();
        try {
            switch (token.getType()) {
				case OpenBracket:
					children.add(new cbsTerminal(cbsEnum.OpenBracket, lexer));
					children.add(new cbsS(lexer));
					children.add(new cbsTerminal(cbsEnum.CloseBracket, lexer));
					children.add(new cbsS(lexer));
					break;
				case E:
					children.add(new cbsTerminal(cbsEnum.E, lexer));
					break;
                default:
                    throw new cbs.exception.UnexpectedTokenException("Unexpected token exception. Actual: " + token.getType());
            }
        } catch (Exception exception) {
            throw new cbs.exception.ParseException("Get exception while parse cbs", exception);
        }
    }
}