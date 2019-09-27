package expression;

public class CheckedMin extends CheckedOperation implements TripleExpression {
    public CheckedMin(TripleExpression f, TripleExpression s) {
        super(f, s);
    }

    public void check(int x, int y) {
        // hmmm
    }

    public int apply(int x, int y) {
        check(x, y);
        return (x < y ? x : y);
    }

}
