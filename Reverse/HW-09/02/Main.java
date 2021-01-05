import java.math.BigInteger;

public class Main {

    public static void keke(String[] args) {
        BigInteger v1 = new BigInteger(args[0]);
        BigInteger v2 = new BigInteger(args[1]);
        BigInteger v3 = new BigInteger(args[2]);
        BigInteger v4 = BigInteger.ZERO;
        while (v4.compareTo(v1) < 0) {
            v4 = v4.add(BigInteger.ONE);
        }
        int v5 = (int) v2.longValue();
        long v6 = v2.longValue();
        int v8 = 0;
        while (v5 >= v8) {
            v6 = v6 * v6;
            v8++;
        }
        BigInteger v8_1 = BigInteger.valueOf(5).multiply(v4);
        BigInteger v9 = BigInteger.valueOf(4).multiply(v2);
        BigInteger v10 = BigInteger.valueOf(4).multiply(v3);
        if (!v9.add(v8_1).add(v10).equals(BigInteger.valueOf(11))) {
            // System.out.println("nope");
            return;
        }
        v8_1 = BigInteger.valueOf(4).multiply(v4);
        v9 = BigInteger.valueOf(5).multiply(v2);
        v10 = BigInteger.valueOf(4).multiply(v3);
        if (!v9.add(v8_1).add(v10).equals(BigInteger.valueOf(8))) {
            // System.out.println("nope");
            return;
        }
        v8_1 = BigInteger.valueOf(4).multiply(v4);
        v9 = BigInteger.valueOf(4).multiply(v2);
        v10 = BigInteger.valueOf(5).multiply(v3);
        if (!v9.add(v8_1).add(v10).equals(BigInteger.valueOf(7))) {
            // System.out.println("nope");
            return;
        }
        BigInteger v11 = v2.subtract(BigInteger.ONE);
        BigInteger v12 = v3.multiply(v4).multiply(v11).multiply(new BigInteger("cafebabedeadbeef", 16));
        System.out.printf("spbctf{%s}%n", v12);
    }

    public static void main(String[] args) {
        for (int i = -10; i < 10; i++) {
            for (int j = -10; j < 10; j++) {
                for (int k = -10; k < 10; k++) {
                    keke(new String[]{String.valueOf(i), String.valueOf(j), String.valueOf(k)});
                }
            }
        }
    }
}