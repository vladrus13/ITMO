package expression;

import expression.exceptions.DivideByZeroException;
import expression.exceptions.OverflowException;
import expression.exceptions.ParsingException;

public class CheckedLog extends CheckedOperation implements TripleExpression {
    public CheckedLog(TripleExpression a, TripleExpression b) {
        super(a, b);
    }

    public void check(int x, int y) throws OverflowException {
        if (x <= 0) {
            throw new OverflowException("x <= 0, so y is mega big");
        }
        if (y <= 1) {
            throw new OverflowException("Incorrect y");
        }
    }

    public int apply(int y, int x) throws OverflowException {
        check(x, y);
        int l = 0, r = 31, m;
        while (1 < r - l) {
            m = (l + r) / 2;
            try {
                int fm = new CheckedPower(new CheckedConst(y), new CheckedConst(m)).evaluate(0, 228, 364);
                if (fm <= x) {
                    l = m;
                } else {
                    r = m;
                }
            } catch (Exception e) {
                r = m;
            }
        }
        try {
            int fm = new CheckedPower(new CheckedConst(y), new CheckedConst(r)).evaluate(0, 228, 364);
            if (fm <= x) {
                return r;
            } else {
                return l;
            }
        } catch (Exception e) {
            return l;
        }
    }
}
