package expression;

import expression.exceptions.ParsingException;
import expression.operations.Operation;

public class Multiply<T> extends BinaryOperationA<T> {

    public Multiply(TripleExpression first, TripleExpression second, Operation<T> operation) {
        super(first, second, operation);
    }

    protected T solve(T x, T y) throws ParsingException {
        return operation.multiply(x, y);
    }
}
