package expression;

import expression.exceptions.OverflowException;

public class CheckedSubtract extends CheckedOperation implements TripleExpression {
    public CheckedSubtract(TripleExpression f, TripleExpression s) {
        super(f, s);
    }

    public void check(int x, int y) throws OverflowException {
        if (x >= 0 && y < 0 && x - Integer.MAX_VALUE > y || x <= 0 && y > 0 && Integer.MIN_VALUE - x > -y) {
            throw new OverflowException("overflow\n" + String.valueOf(x) + " - " + String.valueOf(y));
        }
    }

    public int apply(int x, int y) throws OverflowException{
        check(x, y);
        return x - y;
    }
}
