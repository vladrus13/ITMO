package expression;

public class Subtract extends Operation implements TripleExpression {
    public Subtract(TripleExpression f, TripleExpression s) {
        super(f, s);
    }

    public int apply(int x, int y) {
        return x - y;
    }
}
