package expression;

public abstract class Operation implements TripleExpression {
    public TripleExpression first, second;

    public Operation(TripleExpression f, TripleExpression s) {
        first = f;
        second = s;
    }

    protected abstract int apply(int x, int y);

    public int evaluate(int x, int y, int z) {
        return apply(first.evaluate(x, y, z), second.evaluate(x, y, z));
    }
}
