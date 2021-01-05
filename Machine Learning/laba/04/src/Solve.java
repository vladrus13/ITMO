import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Solve {

    public static class Triple<A, B, C> {
        A a;
        B b;
        C c;

        public Triple(A a, B b, C c) {
            this.a = a;
            this.b = b;
            this.c = c;
        }
    }

    public static class Pair<A, B> {
        A a;
        B b;

        public Pair(A a, B b) {
            this.a = a;
            this.b = b;
        }
    }

    public static int n;
    public static int m;
    public static final double M = 5e7;
    public static double start;
    public static final double START_GIGA = 100;
    public static long PAUSE = 300;

    public static class Bean<T> {
        private final ArrayList<T> attributes;
        private final int y;

        public Bean(ArrayList<T> attributes, int y) {
            this.attributes = attributes;
            this.y = y;
        }

        public ArrayList<T> getAttributes() {
            return attributes;
        }

        public T get(int i) {
            return attributes.get(i);
        }
    }

    public static final Function<FastReader, ArrayList<Double>> solveSmall = fastReader -> {
        m++;
        ArrayList<Bean<Double>> beans = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            ArrayList<Double> temp = new ArrayList<>();
            for (int j = 0; j < m - 1; j++) {
                temp.add((double) fastReader.nextInt());
            }
            temp.add(1.0);
            beans.add(new Bean<>(temp, fastReader.nextInt()));
        }
        ArrayList<Double> w = new ArrayList<>();
        IntStream.range(0, m).forEach(element -> w.add(0.0));
        double attributes0 = 0, y = 0;
        for (Bean<Double> bean : beans) {
            attributes0 += bean.attributes.get(0);
            y += bean.y;
        }
        double aa0 = attributes0 / n, ay = y / n, a = 0, b = 0;
        for (Bean<Double> bean : beans) {
            b += Math.pow(bean.get(0) - aa0, 2);
            a += (bean.getAttributes().get(0) - aa0) * (bean.y - ay);
        }
        w.set(0, b == 0 ? 0 : a / b);
        w.set(1, ay - w.get(0) * aa0);
        m--;
        return w;
    };

    public static final Function<Pair<Integer, FastReader>, ArrayList<Integer>> readOneBean =
            pair -> IntStream.range(0, pair.a).map(i -> pair.b.nextInt()).boxed().collect(Collectors.toCollection(ArrayList::new));

    public static final Function<FastReader, ArrayList<ArrayList<Integer>>> readAll =
            fastReader -> IntStream.range(0, n)
                    .mapToObj(i -> readOneBean.apply(new Pair<>(m + 1, fastReader)))
                    .collect(Collectors.toCollection(ArrayList::new));

    public static final Function<Pair<ArrayList<Double>, ArrayList<Double>>, Double> scalar =
            pair -> IntStream.range(0, pair.a.size()).mapToDouble(i -> pair.a.get(i) * pair.b.get(i)).sum();

    public static final Function<Pair<Integer, Double>, ArrayList<Double>> generateA =
            pair -> IntStream.range(0, pair.a)
                    .mapToDouble(i -> pair.b).boxed()
                    .collect(Collectors.toCollection(ArrayList::new));

    public static final Function<Triple<Integer, Integer, Double>, ArrayList<ArrayList<Double>>> generateAA =
            triple -> IntStream.range(0, triple.a)
                    .mapToObj(i -> generateA.apply(new Pair<>(triple.b, triple.c)))
                    .collect(Collectors.toCollection(ArrayList::new));

    public static final Function<ArrayList<ArrayList<Integer>>, Pair<ArrayList<ArrayList<Double>>, ArrayList<Integer>>> normalize = array -> {
        ArrayList<ArrayList<Double>> returned = generateAA.apply(new Triple<>(n, m + 1, 0.0));
        ArrayList<Integer> coeff = new ArrayList<>();
        for (int i = 0; i < m; i++) {
            int finalI = i;
            int maxima = array.stream().mapToInt(k -> Math.abs(k.get(finalI))).max().orElseThrow();
            for (int j = 0; j < n; j++) {
                returned.get(j).set(i, (double) array.get(j).get(i) / maxima);
            }
            coeff.add(maxima);
        }
        for (int i = 0; i < n; i++) {
            returned.get(i).set(m, Double.valueOf(array.get(i).get(m)));
        }
        return new Pair<>(returned, coeff);
    };

    public static final Function<Pair<ArrayList<ArrayList<Double>>, ArrayList<Double>>, Double> SMAPE = pair -> {
        double result = 0;
        for (int i = 0; i < n; i++) {
            ArrayList<Double> x = new ArrayList<>(pair.a.get(i));
            x.remove(x.size() - 1);
            x.add(1.0);
            double predict = scalar.apply(new Pair<>(x, pair.b));
            double real = pair.a.get(i).get(m);
            result += Math.abs(predict - real) / (Math.abs(predict) + Math.abs(real));
        }
        return result;
    };

    public static ArrayList<Double> step(ArrayList<ArrayList<Double>> data, ArrayList<Double> lastW) {
        ArrayList<Double> returned = generateA.apply(new Pair<>(lastW.size(), 0.0));
        for (int i = 0; i < n; i++) {
            ArrayList<Double> x = new ArrayList<>(data.get(i));
            x.remove(x.size() - 1);
            x.add(1.0);
            double predict = scalar.apply(new Pair<>(x, lastW));
            double real = data.get(i).get(m);
            int signum = (int) Math.signum(predict - real);
            for (int j = 0; j < m + 1; j++) {
                double multiply = real * predict;
                double c = x.get(j) * (Math.abs(multiply) + multiply);
                double z = Math.abs(predict) * Math.pow(Math.abs(predict) + Math.abs(real), 2);
                if (c != 0) {
                    returned.set(j, returned.get(j) + signum * c / z);
                }
            }
        }
        for (int i = 0; i < returned.size(); i++) {
            returned.set(i, returned.get(i) / n);
        }
        return returned;
    }

    public static Function<ArrayList<ArrayList<Double>>, ArrayList<Double>> linear = array -> {
        long currentTime = System.currentTimeMillis();
        ArrayList<Double> w = generateA.apply(new Pair<>(m + 1, start));
        while (System.currentTimeMillis() - currentTime < PAUSE) {
            ArrayList<Double> grad = step(array, w);
            IntStream.range(0, m + 1).forEach(i -> w.set(i, w.get(i) - M * grad.get(i)));
        }
        return w;
    };

    public static final Function<FastReader, ArrayList<Double>> solveBig = fastReader -> {
        Pair<ArrayList<ArrayList<Double>>, ArrayList<Integer>> pair = normalize.apply(readAll.apply(fastReader));
        ArrayList<ArrayList<Double>> ws = IntStream.range(-1, 2).mapToObj(i -> {
            start = i * START_GIGA;
            return linear.apply(pair.a);
        }).collect(Collectors.toCollection(ArrayList::new));
        for (ArrayList<Double> w : ws) {
            for (int j = 0; j < m; j++) {
                w.set(j, w.get(j) / pair.b.get(j));
            }
        }
        double minimaSMAPE = Double.MAX_VALUE;
        int who = -1;
        for (int i = 0; i < ws.size(); i++) {
            double smape = SMAPE.apply(new Pair<>(pair.a, ws.get(i)));
            if (smape < minimaSMAPE) {
                minimaSMAPE = smape;
                who = i;
            }
        }
        return ws.get(who);
    };

    public static void main(String[] args) {
        FastReader fastReader = new FastReader();
        PrintWriter printWriter = new PrintWriter(System.out);
        n = fastReader.nextInt();
        m = fastReader.nextInt();
        ArrayList<Double> w;
        if (m == 1) {
            w = solveSmall.apply(fastReader);
        } else {
            w = solveBig.apply(fastReader);
        }
        for (int i = 0; i <= m; i++) {
            printWriter.println(String.format("%.15f", w.get(i)));
        }
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
