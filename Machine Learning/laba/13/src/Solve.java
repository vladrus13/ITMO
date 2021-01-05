import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.StringTokenizer;
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
    }

    private static final Function<ArrayList<Integer>, Long> sum = element -> element.stream().mapToLong(Integer::longValue).sum();

    private static final Function<ArrayList<Integer>, Double> average = element -> ((double) sum.apply(element)) / element.size();

    private static final Function<ArrayList<Integer>, Double> dispersion = array -> {
        double avg = average.apply(array);
        return array.stream().mapToDouble(element -> Math.pow(element - avg, 2)).sum();
    };

    private static final Function<Pair<ArrayList<Integer>, ArrayList<Integer>>, Double> cov = pair -> {
        double fAvg = average.apply(pair.first);
        double sAvg = average.apply(pair.second);
        return IntStream.range(0, pair.first.size())
                .mapToObj(i -> new Pair<>(pair.first.get(i), pair.second.get(i)))
                .mapToDouble(element -> (element.first - fAvg) * (element.second - sAvg))
                .sum();
    };

    private static final Function<Pair<ArrayList<Integer>, ArrayList<Integer>>, Double> pirson = pair -> {
        double fd = dispersion.apply(pair.first);
        double sd = dispersion.apply(pair.second);
        if (Math.abs(fd) < 1e-5 || Math.abs(sd) < 1e-5) {
            return 0.0;
        }
        return cov.apply(pair) / Math.sqrt(fd * sd);
    };

    private static final Function<FastReader, Pair<Integer, Integer>> readPair =
            fastReader -> new Pair<>(fastReader.nextInt(), fastReader.nextInt());

    public static final Function<Pair<Integer, FastReader>, Pair<ArrayList<Integer>, ArrayList<Integer>>> readArrayList = pair -> {
        ArrayList<Integer> x = new ArrayList<>();
        ArrayList<Integer> y = new ArrayList<>();
        IntStream.range(0, pair.first).mapToObj(i -> readPair.apply(pair.second)).forEach(obj -> {
            x.add(obj.first);
            y.add(obj.second);
        });
        return new Pair<>(x, y);
    };

    public static final Function<ArrayList<Integer>, ArrayList<Integer>> rang = array -> {
        if (array.size() == 0) {
            return new ArrayList<>();
        }
        ArrayList<Pair<Integer, Integer>> temp = new ArrayList<>();
        IntStream.range(0, array.size()).forEach(i -> temp.add(new Pair<>(array.get(i), i)));
        temp.sort(Comparator.comparingInt(o -> o.first));
        ArrayList<Integer> returned = new ArrayList<>();
        IntStream.range(0, array.size()).forEach(i -> returned.add(0));
        int count = 0;
        for (int i = 1; i < array.size(); i++) {
            count += (!temp.get(i).first.equals(temp.get(i - 1).first)) ? 1 : 0;
            returned.set(temp.get(i).second, count);
        }
        return returned;
    };

    public static final Function<Pair<ArrayList<Integer>, ArrayList<Integer>>, Double> spirman = pair -> {
        if (pair.first.size() == 0 || pair.first.size() == 1) {
            return 0.0;
        }
        ArrayList<Integer> fr = rang.apply(pair.first);
        ArrayList<Integer> sr = rang.apply(pair.second);
        Double sumSq = IntStream.range(0, pair.first.size()).mapToDouble(i -> Math.pow(fr.get(i) - sr.get(i), 2.0)).sum();
        return 1 - 6 * sumSq / (Math.pow(pair.first.size(), 3) - pair.first.size());
    };

    public static final Function<FastReader, Pair<ArrayList<Integer>, ArrayList<Integer>>> readAll = fastReader -> {
        int n = fastReader.nextInt();
        return readArrayList.apply(new Pair<>(n, fastReader));
    };

    public static void main(String[] args) {
        FastReader fastReader = new FastReader();
        PrintWriter printWriter = new PrintWriter(System.out);
        printWriter.printf("%.10f",
                spirman.apply(readAll.apply(fastReader)));
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
