package expression;

public class Const implements TripleExpression {
    private double value;
    public Const(double x) {
        value = x;
    }

    // can i use class Number?

    public int evaluate(int x, int y, int z) {
        return (int) value;
    }
}
