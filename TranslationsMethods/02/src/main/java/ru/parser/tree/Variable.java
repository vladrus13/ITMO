package ru.parser.tree;

import guru.nidi.graphviz.model.MutableNode;
import ru.parser.exception.ParseException;
import ru.parser.exception.UnexpectedTokenException;
import ru.parser.terminal.Terminal;
import ru.parser.terminal.Token;
import ru.parser.terminal.Tokenizer;

/**
 * Variable class for {@link ru.parser.Parser}
 */
public class Variable extends Node {
    /**
     * Type of variable
     */
    private final Name type;
    /**
     * Count of reference
     */
    private final int countReference;
    /**
     * Name of variable
     */
    private final Name name;

    /**
     * Constructor of class
     * @param tokenizer {@link Tokenizer}
     * @throws ParseException if we have wrong character of token on parsing
     */
    public Variable(Tokenizer tokenizer) throws ParseException {
        Token currentToken;
        try {
            type = new Name(tokenizer);
        } catch (UnexpectedTokenException e) {
            throw new UnexpectedTokenException("At parsing variable", e);
        }
        int count = 0;
        currentToken = tokenizer.getCurrentToken();
        while (currentToken.getTerminal() == Terminal.REFERENCE) {
            count++;
            try {
                currentToken = tokenizer.nextToken();
            } catch (ParseException e) {
                throw new ParseException(
                        String.format("At parsing variable with type %s", type.toString()), e);
            }
        }
        countReference = count;
        if (currentToken.getTerminal() == Terminal.COMMA || currentToken.getTerminal() == Terminal.CLOSE_BRACKET) {
            name = new Name();
            return;
        }
        try {
            name = new Name(tokenizer);
        } catch (UnexpectedTokenException e) {
            throw new UnexpectedTokenException(
                    String.format("At parsing variable with type %s and %d references", type.toString(), countReference), e);
        }
    }

    @Override
    public StringBuilder toStringBuilder() {
        if (name.isNull()) {
            return type.toStringBuilder().append("*".repeat(countReference));
        }
        return type.toStringBuilder().append(' ').append("*".repeat(countReference)).append(name.toStringBuilder());
    }

    @Override
    public MutableNode getNode() {
        return guru.nidi.graphviz.model.Factory.mutNode("V:" + toString())
                .addLink(name.getNode())
                .addLink(type.getNode())
                .addLink(guru.nidi.graphviz.model.Factory.node("*".repeat(countReference)));
    }
}
