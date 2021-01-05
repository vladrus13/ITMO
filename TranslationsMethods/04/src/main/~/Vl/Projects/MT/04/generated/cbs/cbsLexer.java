package %s;

/**
 * Tokenizer class
 */
public class %sLexer {
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
    private Token currentToken;

    /**
     * Constructor for class
     * @param text text
     */
    public %sLexer(String text) {
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
    public Token getCurrentToken() {
        return currentToken;
    }

    /**
     * Go to next token
     * @return next token
     * @throws ParseException if we found problem with parsing (more than one token can be, unsupported character)
     */
    public Token nextToken() throws ParseException {
        skipSpaces();
        if (text.length() == currentChar) {
            currentChar++;
            return currentToken = new Token(Terminal.END);
        }
        if (text.length() < currentChar) {
            throw new ParseException("Get a token after end of data");
        }
        Terminal token = null;
        for (%sEnum terminal : %sEnum.values()) {
            if (terminal.isFirst(currentChar())) {
                if (token != null) {
                    throw new ParseException(String.format("Can get more than one variant of start token on data:\n\t> %s <\n\tVariants: %s %s",
                            getProblem(), token.name(), terminal.name()));
                }
                token = terminal;
            }
        }
        if (token == null) {
            throw new UnsupportedCharacterException(String.format("Can't get character '%c' at > %s <", currentChar, getProblem()));
        }
        int startChar = currentChar;
        currentChar++;
        while (currentChar < text.length() && token.isContinue(text.charAt(currentChar))) {
            currentChar++;
        }
        if (token.isContainData()) {
            return currentToken = new Token(token, text.substring(startChar, currentChar));
        } else {
            return currentToken = new Token(token);
        }
    }
}