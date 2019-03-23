package expression;

import expression.exceptions.OverflowException;

public class CheckedMultiply extends CheckedOperation implements TripleExpression {
    public CheckedMultiply(TripleExpression f, TripleExpression s) {
        super(f, s);
    }

    public void check(int x, int y) throws OverflowException {
        if (x > 0 && y > 0 && Integer.MAX_VALUE / x < y) {
            throw new OverflowException("overflow " + String.valueOf(x) + " * " + String.valueOf(y));
        }
        if (x > 0 && y < 0 && Integer.MIN_VALUE / x > y) {
            throw new OverflowException("overflow " + String.valueOf(x) + " * " + String.valueOf(y));
        }
        if (x < 0 && y > 0 && Integer.MIN_VALUE / y > x) {
            throw new OverflowException("overflow " + String.valueOf(x) + " * " + String.valueOf(y));
        }
        if (x < 0 && y < 0 && Integer.MAX_VALUE / x > y) {
            throw new OverflowException("overflow " + String.valueOf(x) + " * " + String.valueOf(y));
        }
    }

    public int apply(int x, int y) throws OverflowException{
        check(x, y);
        return x * y;
    }
}
