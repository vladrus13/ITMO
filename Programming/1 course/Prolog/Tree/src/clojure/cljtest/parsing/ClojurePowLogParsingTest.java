package clojure.cljtest.parsing;

import jstest.ArithmeticTests;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public class ClojurePowLogParsingTest extends ClojureObjectParsingTest {
    static {
        PARSED.rename("**", "Pow");
        PARSED.rename("//", "Log");
    }

    public ClojurePowLogParsingTest(final boolean hard) {
        super(new PowLogTests(), hard);
        priorities.put("**", -30);
        priorities.put("//", -30);
    }

    public static void main(final String... args) {
        new ClojurePowLogParsingTest(mode(args, ClojurePowLogParsingTest.class)).run();
    }

    public static class PowLogTests extends ArithmeticTests {{
        binary("**", Math::pow);
        binary("//", (a, b) -> Math.log(Math.abs(b)) / Math.log(Math.abs(a)));
        tests(
                f("**", vx, vy),
                f("//", vx, vy),
                f("**", vx, f("-", vy, vz)),
                f(
                        "**",
                        c(2),
                        f("+", c(1), f("*", c(2), f("-", vy, vz)))
                ),
                f(
                        "//",
                        f("+", c(2), f("*", c(4), f("-", vx, vz))),
                        f("+", c(1), f("*", c(2), f("-", vy, vz)))
                )
        );
    }}
}
