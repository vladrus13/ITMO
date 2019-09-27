package expression.generic;

/**
 * Abs, square, mod over unchecked int, float, byte test.
 *
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public class GenericAsmUfbTest extends GenericUfbTest {
    public GenericAsmUfbTest() {
        GenericAsmTest.addMas(this);
        ufb(
                "1 + 5 mod 3",
                (x, y, z) -> 1 + 5 % 3,
                (x, y, z) -> 1 + 5 % 3.0f,
                (x, y, z) -> b(1 + 5 % 3)
        );
        ufb(
                "x + y mod (z + 1)",
                (x, y, z) -> x + y % (z + 1),
                (x, y, z) -> x + y % (z + 1.0f),
                (x, y, z) -> b(x + y % (z + 1))
        );
        ufb(
                "abs -5",
                (x, y, z) -> 5,
                (x, y, z) -> 5.0f,
                (x, y, z) -> b(5)
        );
        ufb(
                "abs (x - y) / z",
                (x, y, z) -> Math.abs(x - y) / z,
                (x, y, z) -> Math.abs(x - (float) y) / z,
                (x, y, z) -> b(Math.abs(x - y) / z)
        );
        ufb(
                "square -5",
                (x, y, z) -> 25,
                (x, y, z) -> 25.0f,
                (x, y, z) -> b(25)
        );
        ufb(
                "square x - y / z",
                (x, y, z) -> x * x - y / z,
                (x, y, z) -> x * (float) x - y / (float) z,
                (x, y, z) -> b(x * x - y / z)
        );
    }

    public static void main(final String[] args) {
        new GenericAsmUfbTest().run();
    }
}
