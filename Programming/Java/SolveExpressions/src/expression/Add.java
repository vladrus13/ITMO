package expression;

public class Add extends Operation implements TripleExpression {

    public Add(TripleExpression f, TripleExpression s) {
        super(f, s);
    }

    public int apply(int x, int y) {
        return x + y;
    }

    public double solveIt(double x, double y) {
        return x + y;
    }
}
