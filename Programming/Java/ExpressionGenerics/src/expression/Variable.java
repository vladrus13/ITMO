
package expression;

import expression.exceptions.ParsingException;

public class Variable<T> implements TripleExpression<T> {
    private String s;

    public Variable(String var) {
        s = var;
    }

    public T evaluate(T x, T y, T z) throws ParsingException {
        if (s.equals("x")) {
            return x;
        }
        if (s.equals("y")) {
            return y;
        }
        if (s.equals("z")) {
            return z;
        }
        throw new ParsingException("Unknown Symbol " + s);
    }
}
