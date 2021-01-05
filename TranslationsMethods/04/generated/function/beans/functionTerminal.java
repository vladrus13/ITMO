package function.beans;

import function.functionEnum;
import function.functionLexer;
import function.functionToken;

public class functionTerminal extends functionNode {
    private final functionEnum token;
    private final String data;

    public functionTerminal(functionEnum token, String data) {
        this.token = token;
        this.data = data;
    }

    public functionTerminal(functionEnum expected, functionLexer lexer) throws function.exception.ParseException {
        functionToken realToken = lexer.getCurrentToken();
        if (realToken.getType() != expected) {
            throw new function.exception.UnexpectedTokenException("Unexprected token. Expected: " + expected.name() + ". Actual: " + realToken.getType());
        }
        token = realToken.getType();
        data = realToken.getData();
        lexer.nextToken();
    }

    public functionEnum getToken() {
        return token;
    }

    public String getData() {
        return data;
    }
}