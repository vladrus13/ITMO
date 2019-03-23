package expression;

import expression.exceptions.OverflowException;

public class CheckedNegate extends UnaryOperationA {

    public CheckedNegate(TripleExpression a) {
        super(a);
    }

    protected void check(int x) throws OverflowException{
        if (x == Integer.MIN_VALUE) {
            throw new OverflowException("Negate - overflow");
        }
    }

    protected int apply(int x) throws OverflowException {
        check(x);
        return -x;
    }
}
