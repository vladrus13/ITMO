package ru.DT.bean;

import ru.DT.tree.DecisionTree;
import ru.DT.util.Splitter;

import java.util.ArrayList;
import java.util.Random;

public class AdaBoost {
    private final ArrayList<Double> errors;
    private final ArrayList<DecisionTree> trees;
    private final ArrayList<Double> w;
    private final ArrayList<Double> alpha;
    private final ArrayList<Pair<ArrayList<Double>, Integer>> data;
    private final ArrayList<Double> err;
    private final Random random;

    public AdaBoost(ArrayList<Pair<ArrayList<Double>, Integer>> data) {
        this.data = data;
        errors = new ArrayList<>();
        trees = new ArrayList<>();
        w = new ArrayList<>();
        alpha = new ArrayList<>();
        err = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            w.add((double) 1 / data.size());
            err.add(0.0);
        }
        random = new Random();
    }

    public AdaBoost(ArrayList<Double> errors,
                    ArrayList<DecisionTree> trees,
                    ArrayList<Double> w,
                    ArrayList<Double> alpha,
                    ArrayList<Pair<ArrayList<Double>, Integer>> data,
                    ArrayList<Double> err, Random random) {
        this.errors = errors;
        this.trees = trees;
        this.w = w;
        this.alpha = alpha;
        this.data = data;
        this.err = err;
        this.random = random;
    }

    public void fit() {
        double sum = 0.0;
        for (double i : w) {
            sum += i;
        }
        for (int i = 0; i < w.size(); i++) {
            w.set(i, w.get(i) / sum);
        }
        int height = trees.size() / 2 + 1;

        trees.add(new DecisionTree(2, height, trees.size() % 2 == 0 ? Splitter.GINI: Splitter.ENTROPY));
        trees.get(trees.size() - 1).train(data);
        for (int i = 0; i < data.size(); i++) {
            err.set(i, trees.get(trees.size() - 1).precision(data.get(i)) != data.get(i).second ? 1.0 : 0.0);
        }
        double e = 0.0;
        for (int i = 0; i < data.size(); i++) {
            if (err.get(i) > 0) {
                e += w.get(i);
            }
        }
        errors.add(e);
        alpha.add(Math.log((1 - e) / Math.max(1e-7, e)));
        double a = Math.exp(alpha.get(alpha.size() - 1));
        for (int i = 0; i < data.size(); i++) {
            if (err.get(i) > 0) {
                w.set(i, w.get(i) * a);
            }
        }
    }

    public int predict(Pair<ArrayList<Double>, Integer> x) {
        double[] predictor = new double[2];
        for (int i = 0; i < trees.size(); i++) {
            predictor[trees.get(i).precision(x)] += alpha.get(i);
        }
        if (predictor[0] > predictor[1]) return 0;
        else return 1;
    }

    @SuppressWarnings("MethodDoesntCallSuperMethod")
    public AdaBoost clone() {
        return new AdaBoost(errors, trees, w, alpha, data, err, random);
    }
}
