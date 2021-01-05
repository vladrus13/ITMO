import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.*;
import java.util.function.Function;
import java.util.stream.IntStream;

public class Solve {

    public static class Pair<F, S> {
        F first;
        S second;

        public Pair(F first, S second) {
            this.first = first;
            this.second = second;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Pair<?, ?> pair = (Pair<?, ?>) o;
            return first.equals(pair.first) &&
                    second.equals(pair.second);
        }

        @Override
        public int hashCode() {
            return Objects.hash(first, second);
        }
    }

    private static final Function<FastReader, Pair<Integer, Integer>> readPair =
            fastReader -> new Pair<>(fastReader.nextInt(), fastReader.nextInt());

    public static final Function<Pair<Integer, FastReader>, ArrayList<Pair<Integer, Integer>>> readArrayList = pair -> {
        ArrayList<Pair<Integer, Integer>> returned = new ArrayList<>();
        IntStream.range(0, pair.first).mapToObj(i -> readPair.apply(pair.second)).forEach(returned::add);
        return returned;
    };

    public static final Function<FastReader, Double> solve = fastReader -> {
        int kx = fastReader.nextInt(), ky = fastReader.nextInt(), n = fastReader.nextInt();
        ArrayList<Pair<Integer, Integer>> array = readArrayList.apply(new Pair<>(n, fastReader));
        Map<Integer, Double> x = new HashMap<>();
        Map<Pair<Integer, Integer>, Double> xy = new HashMap<>();
        array.forEach(pair -> {
            x.merge(pair.first, 1.0 / n, Double::sum);
            xy.merge(pair, 1.0 / n, Double::sum);
        });
        return xy.entrySet().stream()
                .mapToDouble(container -> -container.getValue() * (Math.log(container.getValue()) - Math.log(x.get(container.getKey().first))))
                .sum();
    };

    public static void main(String[] args) {
        FastReader fastReader = new FastReader();
        PrintWriter printWriter = new PrintWriter(System.out);
        printWriter.printf("%.10f", solve.apply(fastReader));
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
