package expression;

import expression.exceptions.ParsingException;
import expression.operations.Operation;

public class Square<T> extends UnaryOperationA<T> {

    public Square(TripleExpression first, Operation<T> operation) {
        super(first, operation);
    }

    protected T solve(T x) throws ParsingException {
        return operation.square(x);
    }
}
