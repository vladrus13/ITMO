package clojure.cljtest.multi;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public class MultiSquareSqrtTests extends MultiTests {
    public MultiSquareSqrtTests(final boolean testMulti) {
        super(testMulti);
        unary("square", a -> a * a);
        unary("sqrt", a -> Math.sqrt(Math.abs(a)));
        tests(
                f("square", f("-", vx, vy)),
                f("sqrt", f("+", vx, vy)),
                f("sqrt", f("/", f("square", vz), f("+", vx, vy))),
                f("+", vx, f("sqrt", f("square", c(2)))),
                f("+", vx, f("sqrt", f("-", vy, f("/", f("square", c(3)), c(4))))),
                f("+", f("square", f("-", vx, c(3))), f("*", vz, f("*", vy, f("sqrt", c(-1)))))
        );
    }
}
