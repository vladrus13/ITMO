package clojure.cljtest.multi;

import jstest.ArithmeticTests;
import jstest.VariablesTests;

import java.util.function.DoubleBinaryOperator;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public class MultiTests extends VariablesTests {
    public MultiTests(final boolean testMulti) {
        arith(testMulti, "+", 0, Double::sum);
        arith(testMulti, "-", 0, (a, b) -> a - b);
        arith(testMulti, "*", 1, (a, b) -> a * b);
        binary("/", (a, b) -> a / b);
        tests.addAll(new ArithmeticTests().tests);
    }

    private void arith(final boolean testMulti, final String name, final int zero, final DoubleBinaryOperator op) {
        if (testMulti) {
            any(name, -1, vars -> {
                switch (vars.size()) {
                    case 0:
                        return (double) zero;
                    case 1:
                        return op.applyAsDouble((double) zero, vars.get(0));
                    default:
                        return vars.subList(1, vars.size()).stream().mapToDouble(a -> a).reduce(vars.get(0), op);
                }
            });
        } else {
            binary(name, op);
        }
    }
}
