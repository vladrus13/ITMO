package ru.parser.terminal;

/**
 * Token class for {@link Tokenizer}
 */
public class Token {
    /**
     * {@link Terminal}
     */
    private final Terminal terminal;
    /**
     * Data of class
     */
    private final String data;

    /**
     * Constructor of class
     * @param terminal {@link Terminal}
     * @param data data
     */
    public Token(Terminal terminal, String data) {
        this.terminal = terminal;
        this.data = data;
    }

    /**
     * Constructor of class without data
     * @param terminal {@link Terminal}
     */
    public Token(Terminal terminal) {
        this.terminal = terminal;
        this.data = null;
    }

    /**
     * Getter for terminal
     * @return {@link Terminal}
     */
    public Terminal getTerminal() {
        return terminal;
    }

    /**
     * Getter for data
     * @return data
     */
    public String getData() {
        return data;
    }
}
