package expression;

public class And extends Operation{
    public And(TripleExpression a, TripleExpression b) {
        super(a, b);
    }

    protected int apply(int x, int y) {
        return x & y;
    }
}
