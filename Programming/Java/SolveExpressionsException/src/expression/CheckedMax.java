package expression;

public class CheckedMax  extends CheckedOperation implements TripleExpression {
    public CheckedMax(TripleExpression f, TripleExpression s) {
        super(f, s);
    }

    public void check(int x, int y) {
        // hmmm
    }

    public int apply(int x, int y) {
        check(x, y);
        return (x > y ? x : y);
    }

}
