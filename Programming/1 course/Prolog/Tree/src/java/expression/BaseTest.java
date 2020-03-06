package expression;

import base.Asserts;
import base.TestCounter;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public abstract strictfp class BaseTest extends Asserts {
    public final Random random = new Random(7240958270458L);

    protected final TestCounter counter = new TestCounter();

    protected BaseTest() {
        Locale.setDefault(Locale.US);

        checkAssert(getClass());
    }

    public static String repeat(final String s, final int n) {
        return Stream.generate(() -> s).limit(n).collect(Collectors.joining());
    }

    public <T> T random(final List<T> variants) {
        return variants.get(random.nextInt(variants.size()));
    }

    public int randomInt(final int n) {
        return random.nextInt(n);
    }

    public void run() {
        System.out.println("=== Testing " + getClass().getSimpleName());
        test();
        counter.printStatus(getClass());
    }

    protected abstract void test();

    @SafeVarargs
    public static <T> List<T> list(final T... items) {
        return new ArrayList<>(Arrays.asList(items));
    }

    public static void addRange(final List<Integer> values, final int d, final int c) {
        for (int i = -d; i <= d; i++) {
            values.add(c + i);
        }
    }

    public static final class Op<T> {
        public final String name;
        public final T f;

        private Op(final String name, final T f) {
            this.name = name;
            this.f = f;
        }
    }

    public static <T> Op<T> op(final String name, final T f) {
        return new Op<>(name, f);
    }
}
