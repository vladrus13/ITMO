package ru.parser;

import ru.parser.exception.ParseException;
import ru.parser.terminal.Tokenizer;
import ru.parser.tree.Function;
import ru.parser.tree.Node;

// Modification: add support for variables without names

/**
 * Main class for parsing methods
 */
public class Parser {
    /**
     * Return parsed node
     * @param s parsed string
     * @return {@link Node}-node
     * @throws ParseException if we have parsing problem like unsupported character, unexpected token, etc.
     */
    public static Node parse(String s) throws ParseException {
        return new Function(new Tokenizer(s));
    }
}
