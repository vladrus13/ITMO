package expression;

public class CheckedConst implements TripleExpression {
    private double value;
    public CheckedConst(double x) {
        value = x;
    }

    // can i use class Number?

    public int evaluate(int x, int y, int z) {
        return (int) value;
    }
}
