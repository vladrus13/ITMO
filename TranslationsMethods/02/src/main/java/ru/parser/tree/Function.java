package ru.parser.tree;

import guru.nidi.graphviz.model.Graph;
import guru.nidi.graphviz.model.MutableNode;
import ru.parser.exception.ParseException;
import ru.parser.exception.UnexpectedTokenException;
import ru.parser.terminal.Terminal;
import ru.parser.terminal.Token;
import ru.parser.terminal.Tokenizer;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Function class for {@link ru.parser.Parser}
 */
public class Function extends Node {

    /**
     * Type of function
     */
    private final Name typeFunction;
    /**
     * Count references
     */
    private final int countReference;
    /**
     * Function name
     */
    private final Name functionName;
    /**
     * Variables
     */
    private final List<Variable> variables;

    /**
     * Constructor for class
     * @param tokenizer {@link Tokenizer}
     * @throws ParseException if we have wrong character of token on parsing
     */
    public Function(Tokenizer tokenizer) throws ParseException {
        tokenizer.nextToken();
        try {
            typeFunction = new Name(tokenizer);
        } catch (UnexpectedTokenException e) {
            throw new UnexpectedTokenException("At parsing function", e);
        }
        int count = 0;
        while (tokenizer.getCurrentToken().getTerminal() == Terminal.REFERENCE) {
            count++;
            try {
                tokenizer.nextToken();
            } catch (ParseException e) {
                throw new ParseException(
                        String.format("At parsing function with type %s", typeFunction.toString()), e);
            }
        }
        countReference = count;
        try {
            functionName = new Name(tokenizer);
        } catch (UnexpectedTokenException e) {
            throw new UnexpectedTokenException(String.format("At parsing function with type %s", typeFunction.toString()), e);
        }
        Token currentToken = tokenizer.getCurrentToken();
        if (currentToken.getTerminal() != Terminal.OPEN_BRACKET) {
            throw new UnexpectedTokenException(String.format("Unexpected token. Excepted: %s. Actual: %s",
                    Terminal.OPEN_BRACKET, currentToken.getTerminal()));
        }
        try {
            tokenizer.nextToken();
        } catch (ParseException e) {
            throw new ParseException(String.format("At parsing function with name %s",
                    functionName.toString()), e);
        }
        variables = new ArrayList<>();
        while (tokenizer.getCurrentToken().getTerminal() != Terminal.CLOSE_BRACKET) {
            try {
                variables.add(new Variable(tokenizer));
            } catch (ParseException e) {
                throw new ParseException(String.format("At parsing function with type %s", typeFunction.toString()), e);
            }
            if (tokenizer.getCurrentToken().getTerminal() == Terminal.COMMA) {
                try {
                    tokenizer.nextToken();
                } catch (ParseException e) {
                    throw new ParseException(String.format("At parsing function with type %s", typeFunction.toString()), e);
                }
            }
        }
        try {
            currentToken = tokenizer.nextToken();
        } catch (ParseException e) {
            throw new ParseException(String.format("At parsing function with type %s and variables %s",
                    typeFunction.toString(), variables.stream().map(Node::toString).collect(Collectors.joining(", "))), e);
        }
        if (currentToken.getTerminal() != Terminal.SEMICOLON) {
            throw new UnexpectedTokenException(String.format("Unexpected token. Excepted: %s. Actual: %s",
                    Terminal.SEMICOLON, currentToken.getTerminal()));
        }
        try {
            tokenizer.nextToken();
        } catch (ParseException e) {
            throw new ParseException(String.format("At parsing function with type %s and variables %s",
                    typeFunction.toString(), variables.stream().map(Node::toString).collect(Collectors.joining(", "))), e);
        }
    }

    @Override
    public StringBuilder toStringBuilder() {
        StringBuilder answer = typeFunction.toStringBuilder().append("*".repeat(countReference));
        answer.append(' ').append(functionName.toStringBuilder()).append('(');
        // TODO effective stream (maybe)
        for (int i = 0; i < variables.size(); i++) {
            answer.append(variables.get(i).toStringBuilder());
            if (i + 1 != variables.size()) answer.append(", ");
        }
        answer.append(");");
        return answer;
    }

    @Override
    public MutableNode getNode() {
        MutableNode g = guru.nidi.graphviz.model.Factory.mutNode("F:" + toString())
                .addLink(functionName.getNode())
                .addLink(typeFunction.getNode())
                .addLink(guru.nidi.graphviz.model.Factory.node("*".repeat(countReference)));
        for (Variable variable : variables) {
            g.addLink(variable.getNode());
        }
        return g;
    }
}
