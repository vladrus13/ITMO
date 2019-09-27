package expression.parser;

import java.util.Arrays;

/**
 * @author Georgiy Korneev
 */
public class ParserNotCountTest extends ParserBitwiseTest {
    protected ParserNotCountTest() {
        unary.add(op("~", a -> ~a));
        unary.add(op("count", ParserNotCountTest::count));

        tests.addAll(Arrays.asList(
                op("~-5", (x, y, z) -> 4),
                op("~(x - y)", (x, y, z) -> ~(x - y)),
                op("x-~-y", (x, y, z) -> x - ~(-y)),
                op("~-x", (x, y, z) -> ~(-x)),
                op("~(x+y)", (x, y, z) -> ~(x + y)),
                op("count 5", (x, y, z) -> 2),
                op("count -5", (x, y, z) -> 31),
                op("count (x - y)", (x, y, z) -> count(x - y)),
                op("x -count y", (x, y, z) -> x - count(y)),
                op("count -x", (x, y, z) -> count(-x)),
                op("count(x+y)", (x, y, z) -> count(x + y))
        ));
    }

    public static long count(final long a) {
        return Integer.bitCount((int) a);
    }

    public static void main(final String[] args) {
        new ParserNotCountTest().run();
    }
}
