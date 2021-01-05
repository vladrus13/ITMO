import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Base64;
import java.util.Objects;
import java.util.StringTokenizer;

public class Solve {

    private static byte[] take(FastReader fastReader) {
        return toByte(fastReader.nextLine());
    }

    private static void trying(PrintWriter printWriter, byte[] text, byte[] Vl) {
        printWriter.println("NO");
        printWriter.println(toString(text));
        printWriter.println(toString(Vl));
        printWriter.flush();
    }

    private static void answer(PrintWriter printWriter, String answer) {
        printWriter.println("YES");
        printWriter.println(answer);
        printWriter.flush();
    }

    public static void main(String[] args) throws IOException {
        FastReader fastReader = new FastReader();
        PrintWriter printWriter = new PrintWriter(System.out);
        if (args.length == 1 && args[0] != null) {
            String[] command = new String[]{"java", "-jar", String.format("Interact-task8-%s.jar", args[0])};
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
        byte[] messageReal = take(fastReader);
        byte[] VI = take(fastReader);
        VI[2] ^= 's' ^ 14;
        trying(printWriter, messageReal, VI);
        String verdict = fastReader.nextLine();
        if (!Objects.equals(verdict, "Ok")) {
            answer(printWriter, "No");
            return;
        }
        for (int i = 3; i < 16; i++) {
            VI[i] ^= 0x3;
        }
        trying(printWriter, messageReal, VI);
        verdict = fastReader.nextLine();
        if (!Objects.equals(verdict, "Ok")) {
            answer(printWriter, "N/A");
        } else {
            answer(printWriter, "Yes");
        }
    }

    public static String toString(byte[] args) {
        return Base64.getEncoder().encodeToString(args);
    }

    public static byte[] toByte(String args) {
        return Base64.getDecoder().decode(args);
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
