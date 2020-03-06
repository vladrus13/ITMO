package clojure.cljtest.multi;

import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public class MultiMinMaxTests extends MultiTests {
    private static final Random RANDOM = new Random(343243543059325L);

    public MultiMinMaxTests(final boolean testMulti) {
        super(testMulti);
        final int arity = testMulti ? -1 : 2;
        any("min", arity, xs -> xs.stream().mapToDouble(a -> a).min().getAsDouble());
        any("max", arity, xs -> xs.stream().mapToDouble(a -> a).max().getAsDouble());
        tests(
                f("min", vx, vy),
                f("max", vx, vy),
                f("min", vx, c(3)),
                f("max", vx, c(3)),
                f("max", vx, f("min", vy, vz)),
                f("min", vx, f("max", vy, vz)),
                f("/", vz, f("max", vx, vy)),
                f("+", f("min", f("+", vx, c(10)), f("*", vz, f("*", vy, c(0)))), c(2))
        );
        if (testMulti) {
            final Supplier<AbstractExpression> generator = () -> random(vx, vy, vz, c(RANDOM.nextInt(10)));
            for (int i = 1; i < 10; i++) {
                final AbstractExpression[] args = Stream.generate(generator).limit(i).toArray(AbstractExpression[]::new);
                tests(
                        f("min", args),
                        f("max", args)
                );
            }
        }
    }

    @SafeVarargs
    private static <T> T random(final T... values) {
        return values[RANDOM.nextInt(values.length)];
    }
}
