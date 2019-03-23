package expression;

public class Xor extends Operation {
    public Xor(TripleExpression a, TripleExpression b) {
        super(a, b);
    }

    protected int apply(int x, int y) {
        return x ^ y;
    }
}
