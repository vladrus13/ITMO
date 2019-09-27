package expression;

import expression.exceptions.ParsingException;
import expression.operations.Operation;

public class Add<T> extends BinaryOperationA<T> {

    public Add(TripleExpression first, TripleExpression second, Operation<T> operation) {
        super(first, second, operation);
    }

    protected T solve(T x, T y) throws ParsingException {
        return operation.add(x, y);
    }
}
