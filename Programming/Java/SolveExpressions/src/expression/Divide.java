package expression;

public class Divide extends Operation implements TripleExpression {
    public Divide(TripleExpression f, TripleExpression s) {
        super(f, s);
    }

    public int apply(int x, int y) {
        return x / y;
    }

}
