package ru.DT.tree;

import ru.DT.bean.Pair;
import ru.DT.bean.ResultPair;
import ru.DT.util.Splitter;

import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.IntStream;

public class DecisionTree {
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

    private int dominantClass(ArrayList<Pair<ArrayList<Double>, Integer>> beans) {
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

    private Node train(ArrayList<Pair<ArrayList<Double>, Integer>> beans, int depth) {
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
            node.setDivider(result.divider);
            node.setHold(result.hold);
            ArrayList<Pair<ArrayList<Double>, Integer>> left = new ArrayList<>();
            ArrayList<Pair<ArrayList<Double>, Integer>> right = new ArrayList<>();
            for (var bean : beans) {
                if (bean.first.get(result.divider) < result.hold) {
                    left.add(bean);
                } else {
                    right.add(bean);
                }
            }
            node.setLeft(train(left, depth + 1));
            node.setRight(train(right, depth + 1));
            node.id = nodeId;
            return node;
        } else {
            return new Leaf(dominantClass(beans), nodeId);
        }
    }

    private int precision(Node node, Pair<ArrayList<Double>, Integer> bean) {
        if (node instanceof Leaf) {
            return ((Leaf) node).getGroup();
        }
        if (node instanceof EntireNode) {
            if (bean.first.get(((EntireNode) node).getDivider()) > ((EntireNode) node).getHold()) {
                return precision(((EntireNode) node).getRight(), bean);
            } else {
                return precision(((EntireNode) node).getLeft(), bean);
            }
        }
        throw new IllegalArgumentException("Can't find type of node");
    }

    public int precision(Pair<ArrayList<Double>, Integer> bean) {
        return precision(root, bean);
    }

    public void train(ArrayList<Pair<ArrayList<Double>, Integer>> beans) {
        root = train(beans, 0);
    }

    public String toString() {
        return (id + "\n")+ root.toStringTree();
    }
}
