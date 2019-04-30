package clojure.cljtest.multi;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public class MultiSinhCoshTests extends MultiTests {
    public MultiSinhCoshTests(final boolean testMulti) {
        super(testMulti);
        unary("sinh", Math::sinh);
        unary("cosh", Math::cosh);
        tests(
                f("sinh", f("-", vx, vy)),
                f("cosh", f("+", vx, vy)),
                f("cosh", f("/", f("sinh", vz), f("+", vx, vy))),
                f("+", f("cosh", f("sinh", f("+", vx, c(10)))), f("*", vz, f("*", vy, f("cosh", c(0)))))
        );
    }
}
