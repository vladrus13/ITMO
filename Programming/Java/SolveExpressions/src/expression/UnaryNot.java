package expression;

public class UnaryNot extends UnaryOperationA {

    public UnaryNot(TripleExpression a) {
        super(a);
    }

    protected int apply(int x) {
        return -x;
    }
}
