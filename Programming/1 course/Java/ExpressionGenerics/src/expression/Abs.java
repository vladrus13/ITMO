package expression;

import expression.exceptions.ParsingException;
import expression.operations.Operation;

public class Abs<T> extends UnaryOperationA<T> {

    public Abs(TripleExpression first, Operation<T> operation) {
        super(first, operation);
    }

    protected T solve(T x) throws ParsingException {
        return operation.abs(x);
    }
}
