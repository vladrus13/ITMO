import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.StringTokenizer;
import java.util.stream.IntStream;

public class MerkleTree {

    public static byte[] getSha256(byte[] value) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(value);
            return md.digest();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static byte[] headHash;

    public static int firstNull(byte[][][] a) {
        for (int i = 0; i < a.length; i++) {
            if (a[i] == null) return i;
        }
        return 3;
    }

    public static void writeTry(PrintWriter printWriter, String repeater) {
        printWriter.println(repeater.repeat(256));
        printWriter.flush();
    }

    public static boolean verifyKey(ArrayList<byte[]> neighbourhoods, int position, byte[] verified) {
        verified = getSha256(getParent(verified));
        for (byte[] neighbourhood : neighbourhoods) {
            verified = getSha256(position % 2 == 0 ? getParent(verified, neighbourhood) : getParent(neighbourhood, verified));
            position /= 2;
        }
        return Arrays.equals(verified, headHash);
    }

    public static ArrayList<byte[]> read8(FastReader fastReader) {
        ArrayList<byte[]> answer = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            answer.add(Base64.getDecoder().decode(fastReader.nextLine()));
        }
        return answer;
    }

    private static byte[] dominate(byte[] a, byte[] b, byte[] c) {
        if (Arrays.equals(a, b) || Arrays.equals(a, c)) {
            return a;
        }
        if (Arrays.equals(b, c)) {
            return b;
        }
        throw new IllegalArgumentException("Ban dominators");
    }

    private static byte[][] getAnswer(byte[][][][] ready, byte[] tryCode) {
        byte[][] answer = new byte[256][];
        for (int i = 0; i < tryCode.length; i++) {
            answer[i] = dominate(ready[tryCode[i]][0][i], ready[tryCode[i]][1][i], ready[tryCode[i]][2][i]);
        }
        return answer;
    }

    private static byte[][] convert(byte[] a) {
        byte[][] answer = new byte[256][32];
        for (int i = 0; i < 256; i++) {
            for (int j = 0; j < 32; j++) {
                answer[i][j] = a[i * 32 + j];
            }
        }
        return answer;
    }

    private static String toString(byte[][] a) {
        byte[] b = new byte[a.length * a[0].length];
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[i].length; j++) {
                b[i * a[i].length + j] = a[i][j];
            }
        }
        return toString(b);
    }

    public static void main(String[] args) throws IOException {
        FastReader fastReader = new FastReader();
        PrintWriter printWriter = new PrintWriter(System.out);
        if (args.length == 1 && args[0] != null && args[0].equals("interact_check")) {
            String[] command = new String[]{"java", "-jar", "Interact-task7.jar"};
            ProcessBuilder builder = new ProcessBuilder(command);
            Process process = builder.start();
            if (!process.isAlive()) {
                System.out.println("Compilation error");
                fastReader = new FastReader(new BufferedReader(new InputStreamReader(process.getInputStream())));
                String x;
                while ((x = fastReader.nextLine()) != null) {
                    System.out.println(x);
                }
                return;
            }
            fastReader = new FastReader(new BufferedReader(new InputStreamReader(process.getInputStream())));
            printWriter = new PrintWriter(process.getOutputStream());
        }
        headHash = Base64.getDecoder().decode(fastReader.nextLine());
        boolean isAccepted = false;
        // first - node, second - number document {0|1}, third - tries {0|1|2}
        ArrayList<byte[][][][]> secrets = new ArrayList<>();
        IntStream.range(0, 256).forEach(x -> {
            byte[][][][] a = new byte[2][][][];
            a[0] = new byte[3][][];
            a[1] = new byte[3][][];
            secrets.add(a);
        });
        int counter = 0;
        while (!isAccepted) {
            counter++;
            int node = fastReader.nextInt();
            if (args.length == 1 && args[0] != null && args[0].equals("interact_check")) {
                System.out.println(counter);
            }
            int firstNull0 = firstNull(secrets.get(node)[0]);
            int firstNull1 = firstNull(secrets.get(node)[1]);
            int current = 0;
            if (firstNull0 == 3) {
                // try document 11..11
                current = 1;
                writeTry(printWriter, "1");
                secrets.get(node)[1][firstNull1] = convert(Base64.getDecoder().decode(fastReader.nextLine()));
                if (firstNull1 == 2 && Arrays.equals(secrets.get(node)[1][0], secrets.get(node)[1][1])) {
                    secrets.get(node)[1][2] = secrets.get(node)[1][0];
                }
            } else {
                // try document 00..00
                writeTry(printWriter, "0");
                secrets.get(node)[0][firstNull0] = convert(Base64.getDecoder().decode(fastReader.nextLine()));
                if (firstNull0 == 2 && Arrays.equals(secrets.get(node)[0][0], secrets.get(node)[0][1])) {
                    secrets.get(node)[0][2] = secrets.get(node)[0][0];
                }
            }
            byte[] publicKey = Base64.getDecoder().decode(fastReader.nextLine());
            ArrayList<byte[]> prove = read8(fastReader);
            boolean verifyKey = verifyKey(prove, node, publicKey);
            boolean verifyHash = verifyHash(current, secrets.get(node)[current][current == 0 ? firstNull0 : firstNull1], publicKey);
            boolean isTrue = verifyHash && verifyKey;
            if (verifyHash) {
                if (current == 1) {
                    secrets.get(node)[1][0] = secrets.get(node)[1][firstNull1];
                    secrets.get(node)[1][1] = secrets.get(node)[1][firstNull1];
                    secrets.get(node)[1][2] = secrets.get(node)[1][firstNull1];
                } else {
                    secrets.get(node)[0][0] = secrets.get(node)[0][firstNull0];
                    secrets.get(node)[0][1] = secrets.get(node)[0][firstNull0];
                    secrets.get(node)[0][2] = secrets.get(node)[0][firstNull0];
                }
            } else {
                if (current == 1) {
                    secrets.get(node)[1][firstNull1] = null;
                } else {
                    secrets.get(node)[0][firstNull0] = null;
                }
            }
            byte[] tryCode = toChar(fastReader.nextLine());
            firstNull0 = firstNull(secrets.get(node)[0]);
            firstNull1 = firstNull(secrets.get(node)[1]);
            if (firstNull1 == 3 && firstNull0 == 3) {
                // ready to answer
                printWriter.println(isTrue ? "YES" : "NO");
                printWriter.println("YES");
                byte[][] answer = getAnswer(secrets.get(node), tryCode);
                printWriter.println(toString(answer));
                printWriter.flush();
                isAccepted = true;
            } else {
                printWriter.println((isTrue ? "YES" : "NO") + "\nNO");
                printWriter.flush();
            }
        }
        printWriter.close();
    }

    private static boolean verifyHash(int extend, byte[][] bytes, byte[] publicKey) {
        byte[][][] y = new byte[2][256][32];
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 256; j++) {
                for (int k = 0; k < 32; k++) {
                    y[i][j][k] = publicKey[i * 32 * 256 + j * 32 + k];
                }
            }
        }
        for (int i = 0; i < 256; i++) {
            if (!Arrays.equals(getSha256(bytes[i]), y[extend][i])) {
                return false;
            }
        }
        return true;
    }

    private static byte[] toChar(String decode) {
        byte[] answer = new byte[decode.length()];
        for (int i = 0; i < decode.length(); i++) {
            answer[i] = (byte) (decode.charAt(i) == '0' ? 0 : 1);
        }
        return answer;
    }

    private static byte[] getParent(byte[] left, byte[] right) {
        if (left == null && right == null) {
            return null;
        }
        int size = 2 + (left == null ? 0 : left.length) + (right == null ? 0 : right.length);
        byte[] result = new byte[size];
        int currentPosition = 0;
        result[currentPosition] = 0x00000001;
        currentPosition++;
        if (left != null) {
            for (byte b : left) {
                result[currentPosition] = b;
                currentPosition++;
            }
        }
        result[currentPosition] = 0x00000002;
        currentPosition++;
        if (right != null) {
            for (byte b : right) {
                result[currentPosition] = b;
                currentPosition++;
            }
        }
        return result;
    }

    private static byte[] getParent(byte[] child) {
        if (child.length == 0) return null;
        byte[] result = new byte[child.length + 1];
        result[0] = 0x00000000;
        int i = 1;
        for (byte b : child) {
            result[i] = b;
            i++;
        }
        return result;
    }

    private static String toString(byte[] array) {
        if (array == null) {
            return "null";
        } else {
            return Base64.getEncoder().encodeToString(array);
        }
    }

    static class FastReader {
        BufferedReader br;
        StringTokenizer st;

        public FastReader() {
            br = new BufferedReader(new InputStreamReader(System.in));
        }

        public FastReader(BufferedReader br) {
            this.br = br;
        }

        String next() {
            while (st == null || !st.hasMoreElements()) {
                try {
                    st = new StringTokenizer(br.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return st.nextToken();
        }

        int nextInt() {
            return Integer.parseInt(next());
        }

        long nextLong() {
            return Long.parseLong(next());
        }

        double nextDouble() {
            return Double.parseDouble(next());
        }

        String nextLine() {
            String str = "";
            try {
                str = br.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return str;
        }
    }
}