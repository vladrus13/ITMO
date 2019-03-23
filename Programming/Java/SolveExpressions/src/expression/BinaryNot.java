package expression;

public class BinaryNot extends UnaryOperationA {

    public BinaryNot(TripleExpression s) {
        super(s);
    }

    protected int apply(int x) {
        return ~x;
    }
}
