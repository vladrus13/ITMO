import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.*;
import java.util.concurrent.atomic.DoubleAccumulator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;
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

    public static final Function<Integer, ArrayList<Double>> generate =
            count -> IntStream.range(0, count).mapToDouble(i -> 0.0).boxed().collect(Collectors.toCollection(ArrayList::new));

    public static final Function<FastReader, Double> solve = fastReader -> {
        int kx = fastReader.nextInt(), ky = fastReader.nextInt(), n = fastReader.nextInt();
        ArrayList<Pair<Integer, Integer>> array = readArrayList.apply(new Pair<>(n, fastReader));
        array.forEach(pair -> {
            pair.first--;
            pair.second--;
        });
        ArrayList<Double> lineX = generate.apply(kx);
        ArrayList<Double> lineY = generate.apply(ky);
        Map<Pair<Integer, Integer>, Integer> table = new HashMap<>();
        array.forEach(pair -> {
            lineX.set(pair.first, lineX.get(pair.first) + (1D / n));
            lineY.set(pair.second, lineY.get(pair.second) + (1D / n));
            table.merge(pair, 1, Integer::sum);
        });
        return table.entrySet().stream().mapToDouble(pair -> {
            Pair<Integer, Integer> coordinates = pair.getKey();
            Double e = lineX.get(coordinates.first) * lineY.get(coordinates.second) * n;
            return Math.pow(pair.getValue() - e, 2) / e - e;
        }).sum() + n;
    };

    public static final Function<ArrayList<Pair<Integer, Integer>>, ArrayList<Pair<Integer, Integer>>> copy = array -> {
        ArrayList<Pair<Integer, Integer>> copy = new ArrayList<>();
        array.stream().map(pair -> new Pair<>(pair.first, pair.second)).forEach(copy::add);
        return copy;
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
