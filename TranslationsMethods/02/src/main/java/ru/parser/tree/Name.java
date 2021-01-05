package ru.parser.tree;

import guru.nidi.graphviz.model.MutableNode;
import ru.parser.exception.ParseException;
import ru.parser.exception.UnexpectedTokenException;
import ru.parser.terminal.Terminal;
import ru.parser.terminal.Token;
import ru.parser.terminal.Tokenizer;

/**
 * Name function for {@link ru.parser.Parser}
 */
public class Name extends Node {

    /**
     * Name
     */
    private final String name;

    /**
     * Constructor for class
     * @param tokenizer {@link Tokenizer}
     * @throws ParseException if we have wrong character of token on parsing
     */
    public Name(Tokenizer tokenizer) throws ParseException {
        Token token = tokenizer.getCurrentToken();
        if (token.getTerminal() != Terminal.NAME) {
            throw new UnexpectedTokenException(String.format("Unexpected token. Expected: %s. Actual: %s", Terminal.NAME.name(), token.getTerminal().name()));
        }
        name = token.getData();
        try {
            tokenizer.nextToken();
        } catch (ParseException e) {
            throw new ParseException(String.format("At name{%s}", name), e);
        }
    }

    public Name() {
        name = null;
    }

    public boolean isNull() {
        return name == null;
    }

    @Override
    public StringBuilder toStringBuilder() {
        return new StringBuilder(name);
    }

    @Override
    public MutableNode getNode() {
        return guru.nidi.graphviz.model.Factory.mutNode("N:" + toString());
    }
}
