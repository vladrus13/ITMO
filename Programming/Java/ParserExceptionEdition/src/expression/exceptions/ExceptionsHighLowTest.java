package expression.exceptions;

import java.util.Arrays;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public class ExceptionsHighLowTest extends ExceptionsTest {
    protected ExceptionsHighLowTest() {
        unary.add(op("high", ExceptionsHighLowTest::high));
        unary.add(op("low", ExceptionsHighLowTest::low));

        tests.addAll(Arrays.asList(
                op("high -4", (x, y, z) -> Integer.MIN_VALUE),
                op("high-5", (x, y, z) -> Integer.MIN_VALUE),
                op("low 4", (x, y, z) -> 4),
                op("low 18", (x, y, z) -> 2),
                op("low x * y * z", (x, y, z) -> low(x) * y * z),
                op("low(x * y * z)", (x, y, z) -> low(x * y * z)),
                op("high(x + y + z)", (x, y, z) -> high(x + y + z))
        ));
        addParsingTests(
                "hello",
                "   high    ",
                "high  ()",
                "high(    )",
                " high (1, 2)",
                "high(  *  )",
                "high(  \\  )",
                "abb 1",
                "    abb 1    ",
                "please, do not display too much context",
                "high *",
                "highx",
                "highx 10",
                "lоw 4"
        );
    }

    private static long high(final long v) {
        return Integer.highestOneBit((int) v);
    }

    private static long low(final long v) {
        return Integer.lowestOneBit((int) v);
    }

    public static void main(final String[] args) {
        new ExceptionsHighLowTest().run();
    }
}
