package program.beans;

import program.programEnum;
import program.programLexer;
import program.programToken;

public class programTerminal extends programNode {
    private final programEnum token;
    private final String data;

    public programTerminal(programEnum token, String data) {
        this.token = token;
        this.data = data;
    }

    public programTerminal(programEnum expected, programLexer lexer) throws program.exception.ParseException {
        programToken realToken = lexer.getCurrentToken();
        if (realToken.getType() != expected) {
            throw new program.exception.UnexpectedTokenException("Unexprected token. Expected: " + expected.name() + ". Actual: " + realToken.getType());
        }
        token = realToken.getType();
        data = realToken.getData();
        lexer.nextToken();
    }

    public programEnum getToken() {
        return token;
    }

    public String getData() {
        return data;
    }
}