import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.*;
import java.util.stream.IntStream;

public class SolveTree {

    public static class Pair<T, E> {
        public T first;
        public E second;

        public Pair(T first, E second) {
            this.first = first;
            this.second = second;
        }
    }

    public static class ResultPair {
        public int divider;
        public double hold;

        public ResultPair(int divider, double hold) {
            this.divider = divider;
            this.hold = hold;
        }
    }

    public enum Splitter {
        GINI {
            @Override
            public Optional<ResultPair> split(int k, ArrayList<Pair<ArrayList<Integer>, Integer>> beans, int feature) {
                double bestScore = Double.MIN_VALUE;
                Optional<ResultPair> bestSplit = Optional.empty();
                for (int i = 0; i < feature; i++) {
                    int finalI = i;
                    beans.sort((o1, o2) -> o2.first.get(finalI) - o1.first.get(finalI));
                    if (beans.get(0).first.get(i).equals(beans.get(beans.size() - 1).first.get(i))) {
                        continue;
                    }
                    ArrayList<Integer> left = new ArrayList<>();
                    ArrayList<Integer> right = new ArrayList<>();
                    IntStream.range(0, k + 1).forEach(x -> {
                        left.add(0);
                        right.add(0);
                    });
                    int leftSize = 0;
                    int rightSize = beans.size();
                    long leftSum = 0;
                    long rightSum = 0;
                    for (Pair<ArrayList<Integer>, Integer> bean : beans) {
                        rightSum -= right.get(bean.second) * right.get(bean.second);
                        right.set(bean.second, right.get(bean.second) + 1);
                        rightSum += right.get(bean.second) * right.get(bean.second);
                    }
                    int previous = -1;
                    for (int j = 0; j < beans.size(); j++) {
                        if (j != 0 && beans.get(j).first.get(i) != previous) {
                            double score = 1.0 * leftSum / leftSize + 1.0 * rightSum / rightSize;
                            if (score > bestScore) {
                                bestScore = score;
                                bestSplit = Optional.of(new ResultPair(i, ((double) previous + beans.get(j).first.get(i)) / 2));
                            }
                        }
                        rightSum -= right.get(beans.get(j).second) * right.get(beans.get(j).second);
                        right.set(beans.get(j).second, right.get(beans.get(j).second) - 1);
                        rightSum += right.get(beans.get(j).second) * right.get(beans.get(j).second);
                        rightSize--;

                        leftSum -= left.get(beans.get(j).second) * left.get(beans.get(j).second);
                        left.set(beans.get(j).second, left.get(beans.get(j).second) + 1);
                        leftSum += left.get(beans.get(j).second) * left.get(beans.get(j).second);
                        leftSize++;

                        previous = beans.get(j).first.get(i);
                    }
                }
                return bestSplit;
            }
        },
        ENTROPY {
            private double entropy(ArrayList<Integer> counts, int size) {
                double sum = 0;
                for (int i : counts) {
                    if (i != 0) {
                        sum -= ((double) i / size) * Math.log((double) i / size);
                    }
                }
                return sum;
            }

            @Override
            public Optional<ResultPair> split(int k, ArrayList<Pair<ArrayList<Integer>, Integer>> beans, int feature) {
                double bestScore = Double.MAX_VALUE;
                Optional<ResultPair> bestSplit = Optional.empty();
                for (int i = 0; i < feature; i++) {
                    int finalI = i;
                    beans.sort((o1, o2) -> o2.first.get(finalI) - o1.first.get(finalI));
                    if (beans.get(0).first.get(i).equals(beans.get(beans.size() - 1).first.get(i))) {
                        continue;
                    }
                    ArrayList<Integer> left = new ArrayList<>();
                    ArrayList<Integer> right = new ArrayList<>();
                    IntStream.range(0, k + 1).forEach(x -> {
                        left.add(0);
                        right.add(0);
                    });
                    int leftSize = 0;
                    int rightSize = beans.size();
                    for (Pair<ArrayList<Integer>, Integer> bean : beans) {
                        right.set(bean.second, right.get(bean.second) + 1);
                    }
                    int previous = -1;
                    for (int j = 0; j < beans.size(); j++) {
                        if (j != 0 && beans.get(j).first.get(i) != previous) {
                            double score = entropy(left, leftSize) * leftSize + entropy(right, rightSize) * rightSize;
                            if (score < bestScore) {
                                bestScore = score;
                                bestSplit = Optional.of(new ResultPair(i, ((double) previous + beans.get(j).first.get(i)) / 2));
                            }
                        }
                        right.set(beans.get(j).second, right.get(beans.get(j).second) - 1);
                        rightSize--;
                        left.set(beans.get(j).second, left.get(beans.get(j).second) + 1);
                        leftSize++;
                        previous = beans.get(j).first.get(i);
                    }
                }
                return bestSplit;
            }
        };
        public abstract Optional<ResultPair> split(int k, ArrayList<Pair<ArrayList<Integer>, Integer>> beans, int feature);
    }

    public abstract static class Node {
        protected int id;

        public int getId() {
            return id;
        }

        public abstract String toString();

        public abstract StringBuilder toStringTree();
    }

    public static class Leaf extends Node {
        private final int group;

        public Leaf(int group, int id) {
            this.group = group;
            this.id = id;
        }

        public int getGroup() {
            return group;
        }

        @Override
        public String toString() {
            return String.format("C %d", group);
        }

        @Override
        public StringBuilder toStringTree() {
            return new StringBuilder(this.toString()).append("\n");
        }
    }

    public static class EntireNode extends Node {
        private int divider;
        private double hold;
        private Node left;
        private Node right;

        @Override
        public String toString() {
            return String.format("Q %d %.10f %d %d", divider + 1, hold, left.getId(), right.getId());
        }

        @Override
        public StringBuilder toStringTree() {
            return new StringBuilder(this.toString()).append("\n").append(left.toStringTree()).append(right.toStringTree());
        }
    }

    public static class DecisionTree {
        private final int k;
        private Node root;
        private int id;
        private final int max_depth;
        private final Splitter splitter;

        public DecisionTree(int k, int max_depth, Splitter splitter) {
            this.k = k;
            this.max_depth = max_depth;
            this.splitter = splitter;
            this.id = 0;
            this.root = new Leaf(-1, -1);
        }

        private int dominantClass(ArrayList<Pair<ArrayList<Integer>, Integer>> beans) {
            ArrayList<Integer> counts = new ArrayList<>();
            IntStream.range(0, k + 1).forEach(element -> counts.add(0));
            for (var bean : beans) {
                counts.set(bean.second, counts.get(bean.second) + 1);
            }
            //noinspection OptionalGetWithoutIsPresent
            int max = counts.stream().mapToInt(element -> element).max().getAsInt();
            for (int i = 0; i < counts.size(); i++) {
                if (counts.get(i) == max) {
                    return i;
                }
            }
            throw new ArithmeticException("NO");
        }

        private Node train(ArrayList<Pair<ArrayList<Integer>, Integer>> beans, int depth) {
            int nodeId = ++id;
            int divider = beans.get(0).first.size();
            if (depth == max_depth) {
                return new Leaf(dominantClass(beans), nodeId);
            }
            int minima = Integer.MAX_VALUE;
            int maxima = Integer.MIN_VALUE;
            for (var bean : beans) {
                minima = Math.min(minima, bean.second);
                maxima = Math.max(maxima, bean.second);
            }
            if (minima == maxima) {
                return new Leaf(beans.get(0).second, nodeId);
            }
            Optional<ResultPair> split = splitter.split(k, beans, divider);
            if (split.isPresent()) {
                ResultPair result = split.get();
                EntireNode node = new EntireNode();
                node.divider = result.divider;
                node.hold = result.hold;
                ArrayList<Pair<ArrayList<Integer>, Integer>> left = new ArrayList<>();
                ArrayList<Pair<ArrayList<Integer>, Integer>> right = new ArrayList<>();
                for (var bean : beans) {
                    if (bean.first.get(result.divider) < result.hold) {
                        left.add(bean);
                    } else {
                        right.add(bean);
                    }
                }
                node.left = train(left, depth + 1);
                node.right = train(right, depth + 1);
                node.id = nodeId;
                return node;
            } else {
                return new Leaf(dominantClass(beans), nodeId);
            }
        }

        public void train(ArrayList<Pair<ArrayList<Integer>, Integer>> beans) {
            root = train(beans, 0);
        }

        public String toString() {
            return (id + "\n")+ root.toStringTree();
        }
    }

    public static void main(String[] args) {
        FastReader fastReader = new FastReader();
        PrintWriter printWriter = new PrintWriter(System.out);
        int m = fastReader.nextInt();
        int k = fastReader.nextInt();
        int max_depth = fastReader.nextInt();
        int n = fastReader.nextInt();
        ArrayList<Pair<ArrayList<Integer>, Integer>> beans = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            ArrayList<Integer> numbers = new ArrayList<>();
            for (int j = 0; j < m; j++) {
                numbers.add(fastReader.nextInt());
            }
            beans.add(new Pair<>(numbers, fastReader.nextInt()));
        }
        DecisionTree tree = new DecisionTree(k, max_depth, (n > 500) ? Splitter.GINI : Splitter.ENTROPY);
        tree.train(beans);
        printWriter.print(tree.toString());
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
