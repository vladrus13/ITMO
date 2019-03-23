package expression;

import expression.exceptions.DivideByZeroException;
import expression.exceptions.OverflowException;
import expression.exceptions.ParsingException;

public abstract class UnaryOperationA implements TripleExpression{
    private TripleExpression Alone;

    protected UnaryOperationA(TripleExpression s) {
        Alone = s;
    }

    protected abstract int apply(int x) throws OverflowException;

    public int evaluate(int x, int y, int z) throws OverflowException, DivideByZeroException, ParsingException {
        return apply(Alone.evaluate(x, y, z));
    }
}
