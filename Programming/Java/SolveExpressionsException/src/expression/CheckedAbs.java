package expression;

import expression.exceptions.OverflowException;

public class CheckedAbs extends UnaryOperationA {
    public CheckedAbs(TripleExpression a) {
        super(a);
    }

    protected void check(int x) throws OverflowException {
        if (x == Integer.MIN_VALUE) {
            throw new OverflowException("Negate = MIN_VALUE - overflow");
        }
    }

    protected int apply(int x) throws OverflowException {
        check(x);
        return (x < 0 ? -x : x);
    }
}
