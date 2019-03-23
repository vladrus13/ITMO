package expression;

public class Or extends Operation {
    public Or(TripleExpression a, TripleExpression b) {
        super(a, b);
    }

    protected int apply(int x, int y) {
        return x | y;
    }
}
