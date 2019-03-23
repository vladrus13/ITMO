package expression;

import expression.exceptions.ParsingException;
import expression.operations.Operation;

public abstract class UnaryOperationA<T> implements TripleExpression<T> {
    private final TripleExpression<T> first;
    protected Operation<T> operation;

    public UnaryOperationA(TripleExpression<T> first, Operation<T> operation) {
        this.first = first;
        this.operation = operation;
    }

    protected abstract T solve(T x) throws ParsingException;

    @Override
    public T evaluate(T x, T y, T z) throws ParsingException {
        return solve(first.evaluate(x, y, z));
    }
}
