import java.io.*;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Launcher {

    public static class Pair implements Comparable<Pair> {
        public final short[] indexes;
        public final int result;

        public Pair(short[] indexes, int result) {
            this.indexes = indexes;
            this.result = result;
        }

        @Override
        public int compareTo(Pair pair) {
            return Integer.compare(this.result, pair.result);
        }
    }

    public static short n;
    public static PrintWriter printWriter;
    public static int best;
    public static short[][] g;
    public static ThreadLocalRandom randomizer = ThreadLocalRandom.current();
    public static short COUNT_OF_STEPS;
    public static final short COUNT_OF_BEANS = 2000;
    public static final short COUNT_OF_STARTS = 200;

    public static int calculate(final short[] indexes) {
        int result = 0;
        for (int i = 1; i < n; i++) {
            result += g[indexes[i - 1]][indexes[i]];
        }
        result += g[indexes[n - 1]][indexes[0]];
        return result;
    }

    public static short[] shuffle() {
        short[] start = new short[n];
        for (short i = 0; i < n; i++) {
            start[i] = i;
        }
        for (int i = start.length - 1; i > 0; i--) {
            int index = randomizer.nextInt(i + 1);
            short temp = start[i];
            start[i] = start[index];
            start[index] = temp;
        }
        return start;
    }

    public static void generator() {
        TreeSet<Pair> queue = new TreeSet<>();
        for (int i = 0; i < COUNT_OF_STARTS; i++) {
            short[] random = shuffle();
            queue.add(new Pair(random, calculate(random)));
        }
        while (!queue.isEmpty()) {
            Pair currentPair = queue.pollFirst();
            if (currentPair == null){
                throw new IllegalStateException("Can't be");
            }
            short[] result = currentPair.indexes;
            if (currentPair.result < best) {
                for (int i = 0; i < n; i++) {
                    printWriter.print(result[i] + 1);
                    printWriter.print(" ");
                }
                return;
            }
            for (short swap1 = 0; swap1 < n; swap1++) {
                short swap2 = (short) randomizer.nextInt(n);
                if (swap1 == swap2) continue;
                short[] another = Arrays.copyOf(result, result.length);
                short temp = another[swap1];
                another[swap1] = another[swap2];
                another[swap2] = temp;
                queue.add(new Pair(another, calculate(another)));
            }
            while (queue.size() > COUNT_OF_BEANS) {
                queue.pollLast();
            }
        }
    }

    public static void main(final String[] args) throws FileNotFoundException {
        FastReader fastReader = new FastReader("tsp.in");
        printWriter = new PrintWriter("tsp.out");
        n = fastReader.nextShort();
        COUNT_OF_STEPS = (short) Math.ceil(1.5 * n);
        best = (int) Math.floor(fastReader.nextInt() * 1.05);
        g = new short[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < i; j++) {
                g[i][j] = fastReader.nextShort();
                g[j][i] = g[i][j];
            }
        }
        generator();
        printWriter.close();
    }

    static class FastReader {
        BufferedReader br;
        StringTokenizer st;

        public FastReader() {
            br = new BufferedReader(new InputStreamReader(System.in));
        }

        public FastReader(String s) throws FileNotFoundException {
            br = new BufferedReader(new FileReader(s));
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

        short nextShort() {
            return Short.parseShort(next());
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