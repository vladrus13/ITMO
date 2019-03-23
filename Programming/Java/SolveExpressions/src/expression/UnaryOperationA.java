package expression;

public abstract class UnaryOperationA implements TripleExpression{
    private TripleExpression Alone;

    protected UnaryOperationA(TripleExpression s) {
        Alone = s;
    }

    protected abstract int apply(int x);

    public int evaluate(int x, int y, int z) {
        return apply(Alone.evaluate(x, y, z));
    }
}
