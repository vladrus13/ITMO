package expression;

import expression.exceptions.OverflowException;

public class CheckedSqrt extends UnaryOperationA {
    public CheckedSqrt(TripleExpression f) {
        super(f);
    }

    protected void check(int x) throws OverflowException {
        if (x < 0) {
            throw new OverflowException("Want you a complex number?");
        }
    }

    protected int apply(int x) throws OverflowException {
        check(x);
        int l = 0, r = (int) 1e5, m, fm;
        while (1 < r - l) {
            m = (l + r) / 2;
            try {
                fm = new CheckedMultiply(new CheckedConst(m), new CheckedConst(m)).evaluate(423434, 228, 364);
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
            fm = new CheckedMultiply(new CheckedConst(r), new CheckedConst(r)).evaluate(423434, 228, 364);
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
