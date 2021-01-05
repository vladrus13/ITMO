import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;

public class Main {

    private static BufferedWriter bf;

    static {
        try {
            bf = Files.newBufferedWriter(Path.of("Hello.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void rec(String s, int D, int R, int A) throws IOException {
        if (D == 0 && R == 0){
            a(new String[]{s});
        } else {
            if (D > 0) rec(s + "D", D - 1, R, A);
            if (R > 0) rec(s + "R", D, R - 1, A);
            if (A > 0) rec(s + "A", D, R, A - 1);
        }
    }

    public static void main(String[] a) throws IOException {
        // rec("DDDDDDRR", 0, 17, 5);
        a(new String[]{"DDDDDDRRUUURRDRRRRRDDLLLDDRRRRRRRRRRRUURR"});
        bf.close();
    }

    public static void a(String[] objecte) throws IOException {
        byte[] bytes = objecte[0].getBytes();
        byte[] decode = Base64.getDecoder().decode("6enp6enp6enp6enp6enp6enp6enp6eml6urq6unq6erp6urq6urq6urq6enp6unq6enp6unq6erp6enq6enp6unp6erp6erq6urq6urq6urq6urp6unp6enq6erq6unp6enp6unq6enp6unp6enp6unq6erq6urq6unp6urq6erq6unp6erp6unp6enp6erp6enp6enp6enp6enq6urp6urq6urq6urq6urq6erqsunp6unp6erp6unp6enp6enp6enq6enp6erq6unq6erq6urq6urq6urq6urp6enp6enp6enp6enp6enp6enp6enp6ek=");
        for (int i = 0; i < decode.length; i++) {
            decode[i] = (byte) ((byte) (decode[i] ^ -54));
        }
        for (int i = 0; i < decode.length; i++) {
            if (i % 22 == 0) {
                bf.write("\n");
            }
            bf.write(" " + (char) decode[i]);
        }
        byte[] decode2 = Base64.getDecoder().decode("h4SWl4CSvbIOBxLhtau1qZmnq5WODwgBq5WoorSpr6KZr7WZowcVv7s=");
        int general2 = 1;
        int general1 = 1;
        for (int i4 = 0; i4 < bytes.length; i4++) {
            if (bytes[i4] == 68) {
                int i5 = 0;
                while (i5 < 34) {
                    i5++;
                    decode2[i4] = (byte) ((byte) (decode2[i4] ^ (i5 * 239)));
                }
                general1++;
            } else if (bytes[i4] == 82) {
                int i6 = 0;
                while (i6 < 34) {
                    i6++;
                    decode2[i4] = (byte) ((byte) (decode2[i4] ^ (i6 * 97)));
                }
                general2++;
            } else if (bytes[i4] == 76) {
                int i7 = 0;
                while (i7 < 34) {
                    i7++;
                    decode2[i4] = (byte) ((byte) (decode2[i4] ^ (i7 * 65)));
                }
                general2--;
            } else if (bytes[i4] == 85) {
                int i8 = 0;
                while (i8 < 34) {
                    i8++;
                    decode2[i4] = (byte) ((byte) (decode2[i4] ^ (i8 * 65)));
                }
                general1--;
            } else {
                int i9 = 0;
                while (i9 < 34) {
                    i9++;
                    decode2[i4] = (byte) ((byte) (decode2[i4] ^ (i9 * 65)));
                }
            }
            int i10 = (general1 * 22) + general2;
            if (decode[i10] == 32 || decode[i10] == 120 || decode[i10] == 111) {
                int i11 = 0;
                while (i11 < 34) {
                    i11++;
                    decode2[i4] = (byte) ((byte) (decode2[i4] ^ (i11 * 67)));
                }
            } else {
                int i12 = 0;
                while (i12 < 41) {
                    int i13 = i12 + 1;
                    decode2[i12] = (byte) ((byte) (decode2[i12] ^ (i13 * 69)));
                    i12 = i13;
                }
            }
        }
        if (general1 * 22 + general2 == 174) {
            // if (new String(decode2).startsWith("spbctf{")) {
                bf.write(new String(decode2) + " " + objecte[0]);
                bf.write("\n");
            // }
        } else {
            System.out.println(general1 + " " + general2);
        }
    }
}
