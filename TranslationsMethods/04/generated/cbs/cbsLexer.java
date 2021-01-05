package cbs;

import java.util.regex.Matcher;

/**
 * Tokenizer class
 */
public class cbsLexer {
    /**
     * Current text
     */
    private final String text;
    /**
     * Position of current char
     */
    private int currentChar;
    /**
     * Current token
     */
    private cbsToken currentToken;

    /**
     * Constructor for class
     * @param text text
     */
    public cbsLexer(String text) {
        this.text = text;
        currentChar = 0;
    }

    /**
     * Skip spaces on line from current character
     */
    private void skipSpaces() {
        while (text.length() > currentChar && Character.isWhitespace(text.charAt(currentChar))) {
            currentChar++;
        }
    }

    /**
     * Get current char
     * @return current character
     */
    private char currentChar() {
        return text.charAt(currentChar);
    }

    /**
     * Get cause of problem on parsing
     * @return cause
     */
    private String getProblem() {
        return text.substring(Math.max(0, currentChar - 3), Math.min(text.length(), currentChar + 4));
    }

    /**
     * Getter for current token
     * @return current token
     */
    public cbsToken getCurrentToken() {
        return currentToken;
    }

    /**
     * Go to next token
     * @return next token
     * @throws ParseException if we found problem with parsing (more than one token can be, unsupported character)
     */
    public void nextToken() throws cbs.exception.ParseException {
        skipSpaces();
        if (text.length() == currentChar) {
            currentChar++;
            currentToken = new cbsToken(cbsEnum.End, null);
            return;
        }
        if (text.length() < currentChar) {
            throw new cbs.exception.ParseException("Get a token after end of data");
        }
        cbsEnum token = null;
        Matcher matcher = null;
        for (cbsEnum terminal : cbsEnum.values()) {
            matcher = terminal.mather(text.substring(currentChar));
            if (matcher.matches()) {
                token = terminal;
                break;
            }
        }
        if (token == null) {
            throw new cbs.exception.UnsupportedCharacterException("Can't get character '"+ text.substring(currentChar, currentChar + 1) + "' at > " + getProblem() + " <");
        }
        int start = currentChar;
        currentChar = text.length();
        while (token.mather(text.substring(start, currentChar)).matches()) {
            currentChar--;
        }
        currentChar++;
        currentToken = new cbsToken(token, text.substring(start, currentChar));
    }
}