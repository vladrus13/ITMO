package expression;

import expression.exceptions.DivideByZeroException;
import expression.exceptions.OverflowException;

public class CheckedDivide extends CheckedOperation implements TripleExpression {
    public CheckedDivide(TripleExpression f, TripleExpression s) {
        super(f, s);
    }

    public void check(int x, int y) throws OverflowException, DivideByZeroException {
        if (y == 0) {
            throw new DivideByZeroException("Divide by zero");
        }
        if (x == Integer.MIN_VALUE && y == -1) {
            throw new OverflowException("overflow\n" + String.valueOf(x) + " / " + String.valueOf(y));
        }
    }
    public int apply(int x, int y) throws OverflowException, DivideByZeroException {
        check(x, y);
        return x / y;
    }

}
