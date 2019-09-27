package expression;

public class Multiply extends Operation implements TripleExpression {
    public Multiply(TripleExpression f, TripleExpression s) {
        super(f, s);
    }

    public int apply(int x, int y) {
        return x * y;
    }

}
