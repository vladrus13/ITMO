package expression;

import expression.exceptions.OverflowException;

public class CheckedPower extends CheckedOperation implements TripleExpression{
    public CheckedPower(TripleExpression a, TripleExpression b) {
        super(a, b);
    }

    public void check(int x, int y) throws OverflowException{
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

    public int apply(int x, int y) throws OverflowException {
        if (y < 0) {
            throw new OverflowException("negative power: " + String.valueOf(x) + " ^ " + String.valueOf(y));
        }
        if (y == 0) {
            return 1;
        }
        if (y == 1) {
            return x;
        }
        if (y % 2 == 0) {
            check(x, x);
            return apply(x * x, y / 2);
        } else {
            check(x, x);
            int a = apply(x * x, y / 2);
            check(a, x);
            return a * x;
        }
    }
}
