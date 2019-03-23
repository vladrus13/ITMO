package expression.generic;

import java.math.BigInteger;

/**
 * Generic abs, square, mod test.
 *
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public class GenericAsmTest extends GenericTest {
    public GenericAsmTest() {
        addMas(this);
    }

    static void addMas(final GenericTest test) {
        test.allConst("abs -5", 5);
        test.all(
                "abs (x - y) / z",
                (x, y, z) -> check(Math.abs(check(x - y))) / z,
                (x, y, z) -> Math.abs(x - y) / (double) z,
                (x, y, z) -> bi(Math.abs(x - y) / z)
        );

        test.allConst("square -5", 25);
        test.all(
                "square x - y / z",
                (x, y, z) -> mul(x, x) - y / z,
                (x, y, z) -> x * (double) x - y / (double) z,
                (x, y, z) -> bi(x).multiply(bi(x)).subtract(bi(y).divide(bi(z)))
        );

        test.allConst("1 + 5 mod 3", 1 + 5 % 3);
        test.all(
                "x + y mod (z + 1)",
                (x, y, z) -> x + y % (z + 1L),
                (x, y, z) -> x + y % (z + 1.0),
                (x, y, z) -> bi(x).add(bi(y).mod(bi(z).add(BigInteger.ONE)))
        );
    }

    public static void main(final String[] args) {
        new GenericAsmTest().run();
    }
}
