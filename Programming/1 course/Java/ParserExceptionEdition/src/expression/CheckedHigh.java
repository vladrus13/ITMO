package expression;

import expression.exceptions.OverflowException;

public class CheckedHigh extends UnaryOperationA{
    public CheckedHigh(TripleExpression f) {
        super(f);
    }

    protected int apply(int x) throws OverflowException {
        return Integer.highestOneBit(x);
    }
}
