import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;
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

    public static void main(String[] args) {
        FastReader fastReader = new FastReader();
        PrintWriter printWriter = new PrintWriter(System.out);
        int n = fastReader.nextInt();
        int m = fastReader.nextInt();
        int k = fastReader.nextInt();
        ArrayList<Pair<Integer, Integer>> sizes = new ArrayList<>();
        Network network = new Network();
        for (int i = 0; i < n; i++) {
            String type;
            type = fastReader.next();

            switch (type) {
                case "var":
                    int a = fastReader.nextInt();
                    int b = fastReader.nextInt();
                    sizes.add(new Pair<>(a, b));
                    network.add(new Var());
                    break;
                case "tnh":
                    int c = fastReader.nextInt();
                    network.add(new Tnh(network.nodes.get(c - 1)));
                    break;
                case "rlu":
                    int d = fastReader.nextInt();
                    int e = fastReader.nextInt();
                    network.add(new Rlu(d, network.nodes.get(e - 1)));
                    break;
                case "mul":
                    int f = fastReader.nextInt();
                    int g = fastReader.nextInt();
                    network.add(new Mul(network.nodes.get(f - 1), network.nodes.get(g - 1)));
                    break;
                case "sum":
                    int length = fastReader.nextInt();
                    ArrayList<Integer> nodes = new ArrayList<>();
                    for (int j = 0; j < length; j++) {
                        nodes.add(fastReader.nextInt() - 1);
                    }
                    network.add(new Sum(network.getPrevious(nodes)));
                    break;
                case "had":
                    int length_1 = fastReader.nextInt();
                    ArrayList<Integer> nodes_1 = new ArrayList<>();
                    for (int j = 0; j < length_1; j++) {
                        nodes_1.add(fastReader.nextInt() - 1);
                    }
                    network.add(new Had(network.getPrevious(nodes_1)));
                    break;
                default:
                    throw new IllegalStateException("GO BRR. Wait, it's illegal!");
            }
        }
        for (int i = 0; i < m; i++) {
            ArrayList<ArrayList<Double>> temp = generate(sizes.get(i).first, sizes.get(i).second);
            for (int j = 0; j < sizes.get(i).first; j++) {
                for (int kk = 0; kk < sizes.get(i).second; kk++) {
                    temp.get(j).set(kk, (double) fastReader.nextInt());
                }
            }
            if (network.nodes.get(i) instanceof Var) {
                ((Var) network.nodes.get(i)).set(temp);
            } else {
                throw new IllegalStateException("GO BRR? No, this too illegal!");
            }
        }
        network.getAll();
        for (int i = n - k; i < n; i++) {
            network.printNode(i, printWriter);
        }
        for (int i = n - k; i < n; i++) {
            network.nodes.get(i).fillDifferentials(fastReader);
        }
        network.diff();
        for (int i = 0; i < m; i++) {
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
