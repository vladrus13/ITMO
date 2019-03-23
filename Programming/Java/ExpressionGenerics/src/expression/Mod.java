package expression;

import expression.exceptions.ParsingException;
import expression.operations.Operation;

public class Mod<T> extends BinaryOperationA<T> {

    public Mod(TripleExpression first, TripleExpression second, Operation<T> operation) {
        super(first, second, operation);
    }

    protected T solve(T x, T y) throws ParsingException {
        return operation.mod(x, y);
    }
}
