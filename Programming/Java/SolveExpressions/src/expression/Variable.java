
package expression;

public class Variable implements TripleExpression {
    private String s;

    public Variable(String cava) {
        s = cava;
    }

    public int evaluate(int x, int y, int z) {
        if (s.equals("x")) {
            return x;
        }
        if (s.equals("y")) {
            return y;
        }
        if (s.equals("z")) {
            return z;
        }
        return 0;
    }
}
