package expression;

public class Count extends UnaryOperationA {
    public Count(TripleExpression a) {
        super(a);
    }

    protected int apply(int x) {
        return Integer.bitCount(x);
    }
}
