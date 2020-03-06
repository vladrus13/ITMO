package clojure.cljtest.multi;

import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public class MultiMedAvgTests extends MultiTests {
    private static final Random RANDOM = new Random(343243543059325L);

    public MultiMedAvgTests(final boolean testMulti) {
        super(testMulti);
        any("med", testMulti ? -1 : 3, args -> args.stream().sorted().skip(args.size() / 2).findFirst().orElseThrow());
        any("avg", testMulti ? -1 : 5, args -> args.stream().mapToDouble(a -> a).sum() / args.size());
        tests(
                f("med", vx, vy, vz),
                f("avg", vx, vy, vz, c(7), f("*", vy, vz)),
                f("med", vx, f("-", vy, vz), c(7))
        );
        if (testMulti) {
            tests(
                    f("med", vx),
                    f("med", vx, vy),
                    f("med", vx, vy, vz),
                    f("med", vx, vy, vz, c(3), c(5)),
                    f("med", f("-", vx, vy)),
                    f("med", f("+", vx, vy)),
                    f("med", vz, f("/", vx, vy)),
                    f("med", vz, f("med", vx, vy)),
                    f("avg", vx),
                    f("avg", vx, vy),
                    f("avg", vx, vy, vz),
                    f("avg", vx, vy, vz, c(3), c(5)),
                    f("avg", vz, f("avg", vx, vy))
            );
            final Supplier<AbstractExpression> generator = () -> random(vx, vy, vz, c(RANDOM.nextInt(10)));
            for (int i = 1; i < 10; i++) {
                final AbstractExpression[] args = Stream.generate(generator).limit(i).toArray(AbstractExpression[]::new);
                tests(
                        f("med", args),
                        f("avg", args)
                );
            }
        }
    }

    @SafeVarargs
    private static <T> T random(final T... values) {
        return values[RANDOM.nextInt(values.length)];
    }
}
