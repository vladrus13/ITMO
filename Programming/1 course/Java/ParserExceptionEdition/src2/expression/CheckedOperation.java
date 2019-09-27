package expression;

import expression.exceptions.DivideByZeroException;
import expression.exceptions.OverflowException;
import expression.exceptions.ParsingException;

public abstract class CheckedOperation implements TripleExpression {
    public TripleExpression first, second;

    public CheckedOperation(TripleExpression f, TripleExpression s) {
        first = f;
        second = s;
    }

    protected abstract int apply(int x, int y) throws OverflowException, DivideByZeroException, ParsingException;

    protected abstract void check(int x, int y) throws OverflowException, DivideByZeroException;

    public int evaluate(int x, int y, int z) throws OverflowException, DivideByZeroException, ParsingException {
        return apply(first.evaluate(x, y, z), second.evaluate(x, y, z));
    }
}
