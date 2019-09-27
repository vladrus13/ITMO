package expression;

import expression.exceptions.ParsingException;
import expression.operations.Operation;

public class Negate<T> extends UnaryOperationA<T> {

    public Negate(TripleExpression first, Operation<T> operation) {
        super(first, operation);
    }

    protected T solve(T x) throws ParsingException {
        return operation.not(x);
    }
}
