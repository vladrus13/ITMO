import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.IntStream;

public class Solve {

    public static ArrayList<Double> cloneA(ArrayList<Double> a) {
        ArrayList<Double> copy = new ArrayList<>();
        IntStream.range(0, a.size()).forEach(i -> copy.add(a.get(i)));
        return copy;
    }

    public static ArrayList<ArrayList<Double>> cloneAA(ArrayList<ArrayList<Double>> a) {
        ArrayList<ArrayList<Double>> copy = new ArrayList<>();
        IntStream.range(0, a.size()).forEach(i -> copy.add(cloneA(a.get(i))));
        return copy;
    }

    public static class Pair<F, S> {
        F first;
        S second;

        public Pair(F first, S second) {
            this.first = first;
            this.second = second;
        }
    }

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

    public abstract static class Node {
        protected final ArrayList<Node> in;
        protected ArrayList<ArrayList<Double>> values;
        protected ArrayList<ArrayList<Double>> differentials;

        public Node(ArrayList<Node> nodes) {
            this.in = new ArrayList<>(nodes);
        }

        public abstract void get();

        public abstract void diff();

        public void initializeDifferentials() {
            differentials = generate(values.size(), values.get(0).size());
        }

        public void fillDifferentials(FastReader reader) {
            for (ArrayList<Double> i : differentials) {
                for (int j = 0; j < i.size(); j++) {
                    i.set(j, (double) reader.nextInt());
                }
            }
        }

        private void printSome(PrintWriter printWriter, ArrayList<ArrayList<Double>> printed) {
            for (ArrayList<Double> i : printed) {
                for (Double j : i) {
                    printWriter.print(String.format("%.20f ", j));
                }
                printWriter.println();
            }
        }

        public void printNode(PrintWriter printWriter) {
            printSome(printWriter, values);
        }

        public void printDifferentials(PrintWriter printWriter) {
            printSome(printWriter, differentials);
        }
    }

    public static class Var extends Node {

        public Var() {
            super(new ArrayList<>());
        }

        public void set(ArrayList<ArrayList<Double>> d) {
            values = cloneAA(d);
        }

        @Override
        public void get() {
            initializeDifferentials();
        }

        @Override
        public void diff() {
        }
    }

    public static class Tnh extends Node {

        public Tnh(Node node) {
            super(new ArrayList<>(Collections.singleton(node)));
        }

        @Override
        public void get() {
            values = cloneAA(in.get(0).values);
            for (ArrayList<Double> i : values) {
                for (int j = 0; j < i.size(); j++) {
                    i.set(j, Math.tanh(i.get(j)));
                }
            }
            initializeDifferentials();
        }

        @Override
        public void diff() {
            for (int i = 0; i < values.size(); i++) {
                for (int j = 0; j < values.get(i).size(); j++) {
                    in.get(0).differentials.get(i).set(j,
                            in.get(0).differentials.get(i).get(j) + differentials.get(i).get(j) * (1 - Math.pow(values.get(i).get(j), 2)));
                }
            }
        }
    }

    public static class Rlu extends Node {

        double invAlpha;

        public Rlu(double invAlpha, Node node) {
            super(new ArrayList<>(Collections.singleton(node)));
            this.invAlpha = invAlpha;
        }

        @Override
        public void get() {
            values = cloneAA(in.get(0).values);
            for (ArrayList<Double> i : values) {
                for (int j = 0; j < i.size(); j++) {
                    if (i.get(j) < 0) {
                        i.set(j, i.get(j) / invAlpha);
                    }
                }
            }
            initializeDifferentials();
        }

        @Override
        public void diff() {
            for (int i = 0; i < values.size(); i++) {
                for (int j = 0; j < values.get(i).size(); j++) {
                    double current = in.get(0).values.get(i).get(j);
                    double koff;
                    if (current >= 0) {
                        koff = 1;
                    } else {
                        koff = 1 / invAlpha;
                    }
                    in.get(0).differentials.get(i).set(j, in.get(0).differentials.get(i).get(j) + koff * differentials.get(i).get(j));
                }
            }
        }
    }

    public static class Sigm extends Node {

        public Sigm(Node node) {
            super(new ArrayList<>(Collections.singleton(node)));
        }

        @Override
        public void get() {
            values = cloneAA(in.get(0).values);
            for (ArrayList<Double> i : values) {
                for (int j = 0; j < i.size(); j++) {
                    i.set(j, 1.0 / (1 + Math.exp(-i.get(j))));
                }
            }
            initializeDifferentials();
        }

        @Override
        public void diff() {
            for (int i = 0; i < values.size(); ++i) {
                for (int j = 0; j < values.get(0).size(); ++j) {
                    double current = values.get(i).get(j);
                    in.get(0).differentials.get(i).set(j, in.get(0).differentials.get(i).get(j) + current * (1 - current) * differentials.get(i).get(j));
                }
            }
        }
    }

    public static class Mul extends Node {

        public Mul(Node a1, Node a2) {
            super(new ArrayList<>(List.of(a1, a2)));
        }

        @Override
        public void get() {
            ArrayList<ArrayList<Double>> first = in.get(0).values;
            ArrayList<ArrayList<Double>> second = in.get(1).values;
            int m = first.get(0).size();
            int k = second.get(0).size();
            values = generate(first.size(), k);
            for (int i = 0; i < first.size(); i++) {
                for (int j = 0; j < k; j++) {
                    for (int l = 0; l < m; l++) {
                        values.get(i).set(j, values.get(i).get(j) + first.get(i).get(l) * second.get(l).get(j));
                    }
                }
            }
            initializeDifferentials();
        }

        @Override
        public void diff() {
            ArrayList<ArrayList<Double>> first = in.get(0).values;
            ArrayList<ArrayList<Double>> second = in.get(1).values;
            ArrayList<ArrayList<Double>> dfirst = in.get(0).differentials;
            ArrayList<ArrayList<Double>> dsecond = in.get(1).differentials;
            int n = first.size();
            int m = first.get(0).size();
            int k = second.get(0).size();
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    double cur_diff = 0;
                    for (int t = 0; t < k; ++t) {
                        cur_diff += differentials.get(i).get(t) * second.get(j).get(t);
                    }
                    dfirst.get(i).set(j, dfirst.get(i).get(j) + cur_diff);
                }
            }
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < k; j++) {
                    double cur_diff = 0;
                    for (int t = 0; t < n; ++t) {
                        cur_diff += differentials.get(t).get(j) * first.get(t).get(i);
                    }
                    dsecond.get(i).set(j, dsecond.get(i).get(j) + cur_diff);
                }
            }

        }
    }

    public static class Sum extends Node {

        public Sum(ArrayList<Node> nodes) {
            super(nodes);
        }

        @Override
        public void get() {
            int n = in.get(0).values.size();
            int m = in.get(0).values.get(0).size();
            values = generate(n, m);
            for (Node node : in) {
                ArrayList<ArrayList<Double>> current = node.values;
                for (int i = 0; i < n; ++i) {
                    for (int j = 0; j < m; ++j) {
                        values.get(i).set(j, values.get(i).get(j) + current.get(i).get(j));
                    }
                }
            }
            initializeDifferentials();
        }

        @Override
        public void diff() {
            for (int i = 0; i < values.size(); ++i) {
                for (int j = 0; j < values.get(0).size(); ++j) {
                    for (Node node : in) {
                        node.differentials.get(i).set(j, node.differentials.get(i).get(j) + differentials.get(i).get(j));
                    }
                }
            }
        }
    }

    public static class Had extends Node {

        public Had(ArrayList<Node> nodes) {
            super(nodes);
        }

        @Override
        public void get() {
            int n = in.get(0).values.size();
            int m = in.get(0).values.get(0).size();
            values = generate(n, m, 1.0);
            for (Node input : in) {
                ArrayList<ArrayList<Double>> current = input.values;
                for (int i = 0; i < n; ++i) {
                    for (int j = 0; j < m; ++j) {
                        values.get(i).set(j, values.get(i).get(j) * current.get(i).get(j));
                    }
                }
            }
            initializeDifferentials();
        }

        @Override
        public void diff() {
            for (int i = 0; i < values.size(); ++i) {
                for (int j = 0; j < values.get(0).size(); ++j) {
                    for (int k = 0; k < in.size(); ++k) {
                        double koff = 1;
                        for (int t = 0; t < in.size(); ++t) {
                            if (t != k) {
                                koff *= in.get(t).values.get(i).get(j);
                            }
                        }
                        in.get(k).differentials.get(i).set(j, in.get(k).differentials.get(i).get(j) + koff * differentials.get(i).get(j));
                    }
                }
            }
        }
    }

    public static class Network {
        ArrayList<Node> nodes = new ArrayList<>();

        public ArrayList<Node> getPrevious(ArrayList<Integer> indexes) {
            ArrayList<Node> returned = new ArrayList<>();
            for (int index : indexes) {
                returned.add(nodes.get(index));
            }
            return returned;
        }

        public void add(Node node) {
            nodes.add(node);
        }

        public void printNode(int index, PrintWriter printWriter) {
            nodes.get(index).printNode(printWriter);
        }

        public void printDifferentials(int index, PrintWriter printWriter) {
            nodes.get(index).printDifferentials(printWriter);
        }

        public void getAll() {
            for (Node node : nodes) {
                node.get();
            }
        }

        public void diff() {
            for (int i = nodes.size() - 1; i >= 0; i--) {
                nodes.get(i).diff();
            }
        }

        public void symmetrization() {
            Node t = nodes.get(13);
            nodes.set(13, nodes.get(12));
            nodes.set(12, t);
        }

        public int size() {
            return nodes.size();
        }

        public Node get(int i) {
            return nodes.get(i);
        }
    }

    public static ArrayList<ArrayList<Double>> generate(int n, int m) {
        return generate(n, m, 0.0);
    }

    public static ArrayList<ArrayList<Double>> generate(int n, int m, double x) {
        ArrayList<ArrayList<Double>> returned = new ArrayList<>();
        IntStream.range(0, n).forEach(i -> {
            returned.add(new ArrayList<>());
            IntStream.range(0, m).forEach(j ->
                    returned.get(i).add(x));
        });
        return returned;
    }

    public static Function<Triple<Integer, Integer, FastReader>, ArrayList<ArrayList<Double>>> readMatrix = triple -> {
        ArrayList<ArrayList<Double>> m = generate(triple.a, triple.b, 0);
        for (int i = 0; i < triple.a; i++) {
            for (int j = 0; j < triple.b; j++) {
                m.get(i).set(j, (double) triple.c.nextInt());
            }
        }
        return m;
    };

    public static Consumer<Triple<Pair<Integer, Integer>, FastReader, Network>> readAndSetMatrix = pair -> {
        ArrayList<ArrayList<Double>> m = readMatrix.apply(new Triple<>(pair.a.first, pair.a.second, pair.b));
        Var node = new Var();
        node.set(m);
        pair.c.add(node);
    };

    public static void main(String[] args) {
        FastReader fastReader = new FastReader();
        PrintWriter printWriter = new PrintWriter(System.out);
        Network network = new Network();
        int n = fastReader.nextInt();
        Triple<Pair<Integer, Integer>, FastReader, Network> readAndGotMatrix = new Triple<>(new Pair<>(n, n), fastReader, network);
        Triple<Pair<Integer, Integer>, FastReader, Network> readAndGotVector = new Triple<>(new Pair<>(n, 1), fastReader, network);
        readAndSetMatrix.accept(readAndGotMatrix);
        readAndSetMatrix.accept(readAndGotMatrix);
        readAndSetMatrix.accept(readAndGotVector);
        readAndSetMatrix.accept(readAndGotMatrix);
        readAndSetMatrix.accept(readAndGotMatrix);
        readAndSetMatrix.accept(readAndGotVector);
        readAndSetMatrix.accept(readAndGotMatrix);
        readAndSetMatrix.accept(readAndGotMatrix);
        readAndSetMatrix.accept(readAndGotVector);
        readAndSetMatrix.accept(readAndGotMatrix);
        readAndSetMatrix.accept(readAndGotMatrix);
        readAndSetMatrix.accept(readAndGotVector);
        int m = fastReader.nextInt();
        readAndSetMatrix.accept(readAndGotVector);
        readAndSetMatrix.accept(readAndGotVector);
        network.symmetrization();
        ArrayList<Integer> fPositions = new ArrayList<>(Collections.singleton(-1));
        ArrayList<Integer> cPositions = new ArrayList<>(Collections.singleton(12));
        ArrayList<Integer> hPositions = new ArrayList<>(Collections.singleton(13));
        ArrayList<Integer> xPositions = new ArrayList<>(Collections.singleton(-1));
        for (int i = 0; i < m; i++) {
            ArrayList<ArrayList<Double>> matrix = readMatrix.apply(new Triple<>(n, 1, fastReader));
            Var node = new Var();
            node.set(matrix);
            network.add(node);
            int startPosition = 14 + 21 * i;
            int hPreviousPosition = startPosition - 1;
            int cPreviousPosition = hPreviousPosition - 1;
            ArrayList<Integer> blocksWithSum = new ArrayList<>();
            for (int j = 0; j < 4; j++) {
                int WxPosition = network.size();
                network.add(new Mul(network.get(j * 3), node));
                int UxPosition = network.size();
                network.add(new Mul(network.get(j * 3 + 1), network.get(hPreviousPosition)));
                blocksWithSum.add(network.size());
                network.add(new Sum(new ArrayList<>(List.of(network.get(WxPosition), network.get(UxPosition), network.get(j * 3 + 2)))));
            }
            int fPosition = network.nodes.size();
            network.add(new Sigm(network.get(blocksWithSum.get(0))));
            int iPosition = network.nodes.size();
            network.add(new Sigm(network.get(blocksWithSum.get(1))));
            int oPosition = network.nodes.size();
            network.add(new Sigm(network.get(blocksWithSum.get(2))));
            int tanhPosition = network.nodes.size();
            network.add(new Tnh(network.get(blocksWithSum.get(3))));
            int iMultiplyTanhPosition = network.nodes.size();
            network.add(new Had(new ArrayList<>(List.of(network.get(iPosition), network.get(tanhPosition)))));
            int previousPreviousFPosition = network.nodes.size();
            network.add(new Had(new ArrayList<>(List.of(network.get(fPosition), network.get(cPreviousPosition)))));
            int currentCPosition = network.nodes.size();
            network.add(new Sum(new ArrayList<>(List.of(network.get(previousPreviousFPosition), network.get(iMultiplyTanhPosition)))));
            int currentHPosition = network.nodes.size();
            network.add(new Had(new ArrayList<>(List.of(network.get(oPosition), network.get(currentCPosition)))));
            fPositions.add(oPosition);
            cPositions.add(currentCPosition);
            hPositions.add(currentHPosition);
            xPositions.add(startPosition);
        }
        network.getAll();
        network.get(hPositions.get(m)).fillDifferentials(fastReader);
        network.get(cPositions.get(m)).fillDifferentials(fastReader);
        for (int i = m; i >= 1; i--) {
            network.get(fPositions.get(i)).fillDifferentials(fastReader);
        }
        network.diff();
        for (int i = 1; i <= m; i++) {
            network.printNode(fPositions.get(i), printWriter);
        }
        network.printNode(hPositions.get(m), printWriter);
        network.printNode(cPositions.get(m), printWriter);
        for (int i = m; i >= 1; i--) {
            network.printDifferentials(xPositions.get(i), printWriter);
        }
        network.printDifferentials(hPositions.get(0), printWriter);
        network.printDifferentials(cPositions.get(0), printWriter);
        for (int i = 0; i < 12; i++) {
            network.printDifferentials(i, printWriter);
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
