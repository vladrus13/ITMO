import java.util.Arrays;
import java.util.Base64;

public class Main {
    static void s(byte[] a1, int a2) {
        int v5 = a1.length;
        int i = 0;
        while (i < v5 - a2) {
            byte v2 = a1[i];
            a1[i] = a1[i - 1 + a2];
            a1[i - 1 + a2] = v2;
            i += a2;
        }
    }

    public static void supadupaultimateWrite(byte[] a) {
        System.out.println(new String(a));
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Give me a string");
            return;
        }
        if (args[0].length() <= 128) {
            byte[] dest = args[0].getBytes();
            supadupaultimateWrite(dest);
            Main.s(dest, 2);
            supadupaultimateWrite(dest);
            Main.s(dest, 3);
            supadupaultimateWrite(dest);
            Main.s(dest, 4);
            supadupaultimateWrite(dest);
            Main.s(dest, 5);
            supadupaultimateWrite(dest);
            Main.s(dest, 6);
            supadupaultimateWrite(dest);
            Main.s(dest, 7);
            supadupaultimateWrite(dest);
            Main.s(dest, 8);
            supadupaultimateWrite(dest);
            Main.s(dest, 9);
            supadupaultimateWrite(dest);
            Main.s(dest, 10);
            System.out.println(new String(dest));
            return;
        }
        System.out.println("String is too long");
    }
}
