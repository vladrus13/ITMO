package expression;

import expression.exceptions.ParsingException;
import expression.operations.Operation;

public abstract class BinaryOperationA<T> implements TripleExpression<T> {
    private final TripleExpression<T> first;
    private final TripleExpression<T> second;
    protected Operation<T> operation;

    public BinaryOperationA(TripleExpression<T> first, TripleExpression<T> second, Operation<T> operation) {
        this.first = first;
        this.second = second;
        this.operation = operation;
    }

    protected abstract T solve(T x, T y) throws ParsingException;

    @Override
    public T evaluate(T x, T y, T z) throws ParsingException {
        return solve(first.evaluate(x, y, z), second.evaluate(x, y, z));
    }
}
