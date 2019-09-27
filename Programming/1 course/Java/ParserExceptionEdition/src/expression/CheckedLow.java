package expression;

import expression.exceptions.OverflowException;

public class CheckedLow extends UnaryOperationA {
    public CheckedLow(TripleExpression f) {
        super(f);
    }

    protected int apply(int x) throws OverflowException {
        return Integer.lowestOneBit(x);
    }

}
