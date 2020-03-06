package clojure.cljtest.parsing;

import jstest.ArithmeticTests;

import java.util.function.DoubleBinaryOperator;
import java.util.function.LongBinaryOperator;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public class ClojureBitwiseParsingTest extends ClojureObjectParsingTest {
    static {
        PARSED.rename("&", "And");
        PARSED.rename("|", "Or");
        PARSED.rename("^", "Xor");
    }

    public ClojureBitwiseParsingTest(final boolean hard) {
        super(new BitwiseTests(), hard);
        priorities.put("&", 5);
        priorities.put("|", 3);
        priorities.put("^", 1);
    }

    public static void main(final String... args) {
        new ClojureBitwiseParsingTest(mode(args, ClojureBitwiseParsingTest.class)).run();
    }

    static DoubleBinaryOperator logic(final LongBinaryOperator op) {
        return (a, b) -> Double.longBitsToDouble(op.applyAsLong(Double.doubleToLongBits(a), Double.doubleToLongBits(b)));
    }

    public static class BitwiseTests extends ArithmeticTests {{
        binary("&", logic((a, b) -> a & b));
        binary("|", logic((a, b) -> a | b));
        binary("^", logic((a, b) -> a ^ b));
        tests(
                f("&", vx, vy),
                f("|", vx, vy),
                f("^", vx, vy),
                f("&", vx, f("-", vy, vz)),
                f("|", vx, f("-", vy, vz)),
                f("^", vx, f("-", vy, vz))
        );
    }}
}
