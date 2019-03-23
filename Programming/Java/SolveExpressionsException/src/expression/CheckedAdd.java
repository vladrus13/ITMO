package expression;

import expression.exceptions.OverflowException;

public class CheckedAdd extends CheckedOperation implements TripleExpression {

    public CheckedAdd(TripleExpression f, TripleExpression s) {
        super(f, s);
    }

    public void check(int x, int y) throws OverflowException {
        if (x > 0 && Integer.MAX_VALUE - x < y || x < 0 && Integer.MIN_VALUE - x > y) {
            throw new OverflowException("overflow\n" + String.valueOf(x) + " + " + String.valueOf(y));
        }
    }

    public int apply(int x, int y) throws OverflowException{
        check(x, y);
        return x + y;
    }

    public double solveIt(double x, double y) {
        return x + y;
    }
}
