package clojure.cljtest.parsing;

import jstest.ArithmeticTests;

import java.util.Random;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public class ClojureVariablesParsingTest extends ClojureObjectParsingTest {
    private static final Random RANDOM = new Random(4370528470872345L);
    public ClojureVariablesParsingTest(final boolean hard) {
        super(new VariablesTests(RANDOM), hard);
    }

    public static void main(final String... args) {
        new ClojureVariablesParsingTest(mode(args, ClojureVariablesParsingTest.class)).run();
    }

    static class VariablesTests extends jstest.VariablesTests {
        private final Random random;

        public VariablesTests(final Random random) {
            this.random = random;
            binary("+", (a, b) -> a + b);
            binary("-", (a, b) -> a - b);
            binary("*", (a, b) -> a * b);
            binary("/", (a, b) -> a / b);
            unary("negate", a -> -a);
            for (int i = 0; i < 10; i++) {
                tests.addAll(new ArithmeticTests(var('x', 0), var('y', 1), var('z', 2)).tests);
            }
        }

        private AbstractExpression var(final char first, final int i) {
            final StringBuilder sb = new StringBuilder();
            sb.append(random.nextBoolean() ? first : Character.toUpperCase(first));
            while (random.nextBoolean()) {
                sb.append("xyzXYZ".charAt(random.nextInt(6)));
            }
            return variable(sb.toString(), i);
        }
    }
}
