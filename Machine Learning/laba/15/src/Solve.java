import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.*;
import java.util.concurrent.atomic.DoubleAccumulator;
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
    }

    private static final Function<FastReader, Pair<Integer, Integer>> readPair =
            fastReader -> new Pair<>(fastReader.nextInt(), fastReader.nextInt());

    public static final Function<Pair<Integer, FastReader>, ArrayList<Pair<Integer, Integer>>> readArrayList = pair -> {
        ArrayList<Pair<Integer, Integer>> returned = new ArrayList<>();
        IntStream.range(0, pair.first).mapToObj(i -> readPair.apply(pair.second)).forEach(returned::add);
        return returned;
    };

    public static final Function<ArrayList<Pair<Integer, Integer>>, Double> dispersion =
            array -> array.stream().mapToDouble(pair -> Math.pow(pair.second, 2) / array.size()).sum();

    public static final Function<Integer, ArrayList<Pair<Double, Double>>> generate =
            count -> IntStream.range(0, count).mapToObj(i -> new Pair<>(0.0, 0.0)).collect(Collectors.toCollection(ArrayList::new));

    public static final Function<Pair<Integer, ArrayList<Pair<Integer, Integer>>>, ArrayList<Pair<Double, Double>>> expect = complication -> {
        int k = complication.first;
        ArrayList<Pair<Integer, Integer>> array = complication.second;
        ArrayList<Pair<Double, Double>> answer = generate.apply(k);
        array.forEach(pair -> {
            Pair<Double, Double> h = answer.get(pair.first - 1);
            h.first += ((double) pair.second) / array.size();
            h.second += 1.0 / array.size();
        });
        return answer;
    };

    public static final Function<Pair<Integer, ArrayList<Pair<Integer, Integer>>>, Double> conditional =
            array -> expect.apply(array).stream().mapToDouble(pair -> pair.second == 0 ? 0 : Math.pow(pair.first, 2) / pair.second).sum();

    public static final Function<FastReader, Double> solve = fastReader -> {
        int k = fastReader.nextInt();
        int n = fastReader.nextInt();
        ArrayList<Pair<Integer, Integer>> array = readArrayList.apply(new Pair<>(n, fastReader));
        return dispersion.apply(array) - conditional.apply(new Pair<>(k, array));
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
