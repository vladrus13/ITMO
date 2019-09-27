package expression.generic;

/**
 * Generic unchecked int, float, byte test.
 *
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public class GenericUfbTest extends GenericTest {
    public GenericUfbTest() {
        ufb(
                "10",
                (x, y, z) -> 10,
                (x, y, z) -> 10.0f,
                (x, y, z) -> b(10)
        );
        ufb(
                "x",
                (x, y, z) -> x,
                (x, y, z) -> (float) x,
                (x, y, z) -> b(x)
        );
        ufb(
                "y + 2",
                (x, y, z) -> y + 2,
                (x, y, z) -> y + 2.0f,
                (x, y, z) -> b(y + 2)
        );
        ufb(
                "z / 2",
                (x, y, z) -> z / 2,
                (x, y, z) -> z / 2.0f,
                (x, y, z) -> b(z / 2)
        );
        ufb(
                "y / z",
                (x, y, z) -> y / z,
                (x, y, z) -> y / (float) z,
                (x, y, z) -> b(y / z)
        );
        ufb(
                "100 * x * y * 100 + z",
                (x, y, z) -> i(100 * x * y * 100 + z),
                (x, y, z) -> 100.0f * x * y * 100 + z,
                (x, y, z) -> b(100 * x * y * 100 + z)
        );
        ufb(
                "x * y + (z - 1) / 10",
                (x, y, z) -> x * y + (z - 1) / 10,
                (x, y, z) -> x * (float) y + (z - 1) / 10.0f,
                (x, y, z) -> b(x * y + (z - 1) / 10)
        );
    }

    protected static byte b(final int x) {
        return (byte) x;
    }

    protected void ufb(final String expression, final F<Integer> fu, final F<Float> ff, final F<Byte> fb) {
        test(expression, "u", fu);
        test(expression, "f", ff);
        test(expression, "b", (x, y, z) -> fb.apply((byte) x, (byte) y, (byte) z));
    }

    public static void main(final String[] args) {
        new GenericUfbTest().run();
    }
}
