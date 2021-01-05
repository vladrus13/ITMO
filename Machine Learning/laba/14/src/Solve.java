import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.*;
import java.util.function.Function;
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



    public static final Function<FastReader, ArrayList<Pair<Integer, Integer>>> readAll = fastReader -> {
        fastReader.nextInt();
        int n = fastReader.nextInt();
        return readArrayList.apply(new Pair<>(n, fastReader));
    };

    public static final Function<ArrayList<Pair<Integer, Integer>>, Long> getInCategorize = array -> {
        Map<Integer, ArrayList<Integer>> classes = new HashMap<>();
        array.forEach(pair -> {
            classes.putIfAbsent(pair.second, new ArrayList<>());
            classes.get(pair.second).add(pair.first);
        });
        for (Map.Entry<Integer, ArrayList<Integer>> entry : classes.entrySet()) {
            Collections.sort(entry.getValue());
        }
        Long returned = 0L;
        for (Map.Entry<Integer, ArrayList<Integer>> entry : classes.entrySet()) {
            ArrayList<Integer> list = entry.getValue();
            Long suffix = list.stream().mapToLong(Integer::longValue).sum();
            Long prefix = 0L;
            for (int i = 0; i < list.size(); i++) {
                prefix += (long) list.get(i);
                suffix -= (long) list.get(i);
                returned += ((long) list.get(i)) * ((long) (i + 1)) - prefix;
                returned += suffix - ((long) list.get(i)) * ((long) (list.size() - i - 1));
            }
        }
        return returned;
    };

    public static final Function<ArrayList<Pair<Integer, Integer>>, ArrayList<Pair<Integer, Integer>>> copy = array -> {
        ArrayList<Pair<Integer, Integer>> copy = new ArrayList<>();
        array.stream().map(pair -> new Pair<>(pair.first, pair.second)).forEach(copy::add);
        return copy;
    };

    public static final Function<ArrayList<Pair<Integer, Integer>>, Long> getExCategorize = array -> {
        ArrayList<Pair<Integer, Integer>> arrayCopy = copy.apply(array);
        Map<Integer, Long> prefixSum = new HashMap<>();
        Map<Integer, Long> suffixSum = new HashMap<>();
        Map<Integer, Integer> prefixCount = new HashMap<>();
        Map<Integer, Integer> suffixCount = new HashMap<>();
        Long absolutePrefixSum = 0L;
        Long absoluteSuffixSum = 0L;
        arrayCopy.sort(Comparator.comparingInt(o -> o.first));
        for (Pair<Integer, Integer> i : arrayCopy) {
            suffixSum.merge(i.second, Long.valueOf(i.first), Long::sum);
            suffixCount.merge(i.second, 1, Integer::sum);
            absoluteSuffixSum += i.first;
        }
        Long returned = 0L;
        for (int i = 0; i < arrayCopy.size(); i++) {
            Pair<Integer, Integer> element = arrayCopy.get(i);
            absolutePrefixSum += element.first;
            absoluteSuffixSum -= element.first;
            prefixSum.merge(element.second, Long.valueOf(element.first), Long::sum);
            suffixSum.merge(element.second, -Long.valueOf(element.first), Long::sum);
            prefixCount.merge(element.second, 1, Integer::sum);
            suffixCount.merge(element.second, -1, Integer::sum);
            Integer prefixLocalCount = (i + 1) - prefixCount.get(element.second);
            Integer suffixLocalCount = (arrayCopy.size() - i - 1) - suffixCount.get(element.second);
            Long prefixLocalSum = absolutePrefixSum - prefixSum.get(element.second);
            Long suffixLocalSum = absoluteSuffixSum - suffixSum.get(element.second);
            returned += ((long) prefixLocalCount) * ((long) element.first) - prefixLocalSum;
            returned += suffixLocalSum - ((long) suffixLocalCount) * ((long) element.first);
        }
        return returned;
    };

    public static final Function<ArrayList<Pair<Integer, Integer>>, Pair<Long, Long>>
            getCategorize = array -> new Pair<>(getInCategorize.apply(array), getExCategorize.apply(array));

    public static final Function<Pair<Long, Long>, String> toString = pair -> String.format("%d\n%d", pair.first, pair.second);

    public static void main(String[] args) {
        FastReader fastReader = new FastReader();
        PrintWriter printWriter = new PrintWriter(System.out);
        printWriter.print(toString.apply(getCategorize.apply(readAll.apply(fastReader))));
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
