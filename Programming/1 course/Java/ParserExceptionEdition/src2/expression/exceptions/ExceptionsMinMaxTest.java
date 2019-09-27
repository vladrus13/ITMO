package expression.exceptions;

import java.util.Arrays;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public class ExceptionsMinMaxTest extends ExceptionsAbsSqrtTest {
    protected ExceptionsMinMaxTest() {
        levels.add(0, list(
                op("min", Math::min),
                op("max", Math::max)
        ));

        tests.addAll(Arrays.asList(
                op("6 - 10 min-3", (x, y, z) -> -4),
                op("6 - 10 min-5", (x, y, z) -> -5),
                op("2 min 3", (x, y, z) -> 2),
                op("4 min 3 min 2", (x, y, z) -> 2),
                op("20 min 3 * 3", (x, y, z) -> 9),
                op("x min (y * z)", (x, y, z) -> Math.min(x, y * z)),
                op("2 min x + 1", (x, y, z) -> Math.min(2, x + 1)),
                op("-1 min (3 min x)", (x, y, z) -> Math.min(-1, x)),
                op("8 max 2", (x, y, z) -> 8),
                op("x max y", (x, y, z) -> Math.max(x, y)),
                op("5max y", (x, y, z) -> Math.max(5, y))
        ));
        parsingTest.addAll(Arrays.asList(
                op("max", "max"),
                op("max1", "max1"),
                op("max 1", "max 1"),
                op("1 max", "1 max"),
                op("5max5", "5max5"),
                op("1 mxx 1", "1 mxx 1"),
                op("1 * max 2", "1 * max 2"),
                op("1 max * 3", "1 max * 3")
        ));
    }


    public static void main(final String[] args) {
        new ExceptionsMinMaxTest().run();
    }
}