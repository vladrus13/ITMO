import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Solve {

    public static ArrayList<Integer> getConjunctive(ArrayList<Integer> a) {
        ArrayList<Integer> answer = new ArrayList<>();
        for (int i = 0; i < a.size(); i++) {
            answer.add(a.get(i) == 0 ? -1 : 1);
        }
        return answer;
    }

    public static ArrayList<Integer> getBinary(int a, int size, int coff) {
        ArrayList<Integer> answer = new ArrayList<>();
        while (size > 0) {
            answer.add(a % 2 == 0 ? -1 * coff : coff);
            a /= 2;
            size--;
        }
        return answer;
    }

    // haha, discrete mathematics POWER!!!
    public static ArrayList<ArrayList<Integer>> buildPerfectConjunctiveNormalForm(ArrayList<Integer> f) {
        int size = (int) Math.log(f.size()) + 1;
        ArrayList<ArrayList<Integer>> answer = new ArrayList<>();
        for (int i = 0; i < f.size(); i++) {
            if (f.get(i) == 1) {
                answer.add(getBinary(i, size, 1));
            }
        }
        return answer;
    }

    public static ArrayList<ArrayList<Integer>> buildPerfectDisjunctiveForm(ArrayList<Integer> f) {
        int size = (int) Math.log(f.size()) + 1;
        ArrayList<ArrayList<Integer>> answer = new ArrayList<>();
        for (int i = 0; i < f.size(); i++) {
            if (f.get(i) == 0) {
                answer.add(getBinary(i, size, -1));
            }
        }
        return answer;
    }

    static long one(long x) {
        long res = 0;
        while (x > 0) {
            ++res;
            x = x & (x - 1);
        }
        return res;
    }

    public static void main(String[] args) {
        FastReader fastReader = new FastReader();
        PrintWriter printWriter = new PrintWriter(System.out);
        long m = fastReader.nextInt();
        printWriter.println(String.format("2\n%d 1", 1 << m));
        for (long i = 0; i < 1 << m; i++) {
            for (long j = 0; j < m; j++) {
                if ((i & (1 << j)) != 0) {
                    printWriter.print("1.0 ");
                } else {
                    printWriter.print("-1234567890000.0 ");
                }
            }
            printWriter.println(0.5 - one(i));
        }
        for (long i = 0; i < 1 << m; i++) {
            long x = fastReader.nextInt();
            printWriter.print(x + ".0 ");
        }
        printWriter.println(-0.5);
        printWriter.close();
    }

    static class FastReader {
        BufferedReader br;
        StringTokenizer st;

        public FastReader() {
            br = new BufferedReader(new InputStreamReader(System.in));
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
