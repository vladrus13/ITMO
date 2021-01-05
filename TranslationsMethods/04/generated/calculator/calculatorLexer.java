package calculator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Tokenizer class
 */
public class calculatorLexer {
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
    private calculatorToken currentToken;

    /**
     * Constructor for class
     * @param text text
     */
    public calculatorLexer(String text) {
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
    public calculatorToken getCurrentToken() {
        return currentToken;
    }

    /**
     * Go to next token
     * @return next token
     * @throws ParseException if we found problem with parsing (more than one token can be, unsupported character)
     */
    public void nextToken() throws calculator.exception.ParseException {
        skipSpaces();
        if (text.length() == currentChar) {
            currentChar++;
            currentToken = new calculatorToken(calculatorEnum.End, null);
            return;
        }
        if (text.length() < currentChar) {
            throw new calculator.exception.ParseException("Get a token after end of data");
        }
        calculatorEnum token = null;
        Matcher matcher = null;
        for (calculatorEnum terminal : calculatorEnum.values()) {
            matcher = terminal.mather(text.substring(currentChar));
            if (matcher.matches()) {
                token = terminal;
                break;
            }
        }
        if (token == null) {
            throw new calculator.exception.UnsupportedCharacterException("Can't get character '"+ text.substring(currentChar, currentChar + 1) + "' at > " + getProblem() + " <");
        }
        int start = currentChar;
        currentChar = text.length();
        String matching = token.getMatching();
        matching = matching.substring(0, matching.length() - 2);
        Pattern matcher1 = Pattern.compile(matching);
        while (!matcher1.matcher(text.substring(start, currentChar)).matches()) {
            currentChar--;
        }
        if (token.isSkipable()) {
            nextToken();
            return;
        }
        currentToken = new calculatorToken(token, text.substring(start, currentChar));
    }
}