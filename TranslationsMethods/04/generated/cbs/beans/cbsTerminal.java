package cbs.beans;

import cbs.cbsEnum;
import cbs.cbsLexer;
import cbs.cbsToken;

public class cbsTerminal extends cbsNode {
    private final cbsEnum token;
    private final String data;

    public cbsTerminal(cbsEnum token, String data) {
        this.token = token;
        this.data = data;
    }

    public cbsTerminal(cbsEnum expected, cbsLexer lexer) throws cbs.exception.ParseException {
        cbsToken realToken = lexer.getCurrentToken();
        if (realToken.getType() != expected) {
            throw new cbs.exception.UnexpectedTokenException("Unexprected token. Expected: " + expected.name() + ". Actual: " + realToken.getType());
        }
        token = realToken.getType();
        data = realToken.getData();
        lexer.nextToken();
    }

    public cbsEnum getToken() {
        return token;
    }

    public String getData() {
        return data;
    }
}