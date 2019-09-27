package expression.operations;

import expression.exceptions.ParsingException;

public interface Operation<T> {
    T parseNumber(String number) throws ParsingException;

    T add(T x, T y) throws ParsingException;

    T subtract(T x, T y) throws ParsingException;

    T multiply(T x, T y) throws ParsingException;

    T divide(T x, T y) throws ParsingException;

    T not(T x) throws ParsingException;

    T square(T x) throws ParsingException;

    T abs(T x) throws ParsingException;

    T mod(T x, T y) throws ParsingException;
}
