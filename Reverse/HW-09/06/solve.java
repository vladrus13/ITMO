import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Base64;
import java.util.Scanner;

public class Main {

    public static byte[] getSliceOfArray(byte[] arr, int startIndex, int endIndex) {
        return Arrays.copyOfRange(arr, startIndex, endIndex);
    }

    public static boolean check(String name, String serial) {
        byte[] nameb = name.getBytes(StandardCharsets.UTF_8);
        if (nameb.length != 12) {
            return false;
        }
        int[] valid = {ByteBuffer.wrap(getSliceOfArray(nameb, 0, 4)).getInt(), ByteBuffer.wrap(getSliceOfArray(nameb, 4, 8)).getInt(), ByteBuffer.wrap(getSliceOfArray(nameb, 8, 12)).getInt()};
        valid[0] = valid[0] ^ valid[1];
        valid[1] = valid[1] ^ valid[0];
        valid[0] = valid[0] ^ valid[1];
        valid[1] = valid[1] ^ valid[2];
        valid[2] = valid[2] ^ valid[1];
        valid[1] = valid[1] ^ valid[2];
        String x = Integer.toHexString(valid[0]) + '-' + Integer.toHexString(valid[1]) + '-' + Integer.toHexString(valid[2]);
        System.out.println(x);
        return serial.equals(x);
    }
    //54465f32-30323021-53506243
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("What is your name?");
        String name = scanner.nextLine();
        System.out.println("... and your serial?");
        if (check(name, scanner.nextLine())) {
            System.out.println("Your serial is valid!");
        } else {
            System.out.println("Your serial is invalid...");
        }
    }
}
