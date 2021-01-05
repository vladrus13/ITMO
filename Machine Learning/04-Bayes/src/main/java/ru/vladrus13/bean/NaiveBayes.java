package ru.vladrus13.bean;

import java.util.*;

public class NaiveBayes {
    private final int k;
    private final double alpha;
    private final ArrayList<Double> lambdas;
    private final ArrayList<Map<String, Integer>> counts;
    private final ArrayList<Integer> classCounts;
    private final Set<String> allWords;
    private ArrayList<Map<String, Double>> problem;
    private int n;

    public NaiveBayes(int k, double alpha, ArrayList<Double> lambdas) {
        this.k = k;
        this.alpha = alpha;
        this.lambdas = lambdas;
        counts = new ArrayList<>();
        classCounts = new ArrayList<>();
        allWords = new HashSet<>();
        for (int i = 0; i < k; i++) {
            counts.add(new HashMap<>());
            classCounts.add(0);
        }
        n = 0;
    }

    public void teach(int c, Collection<String> words) {
        Set<String> setWords = new HashSet<>();
        for (String word : words) {
            allWords.add(word);
            setWords.add(word);
        }
        setWords.forEach(element -> counts.get(c).merge(element, 1, Integer::sum));
        classCounts.set(c, classCounts.get(c) + 1);
        n++;
    }

    public void readyToPredict() {
        problem = new ArrayList<>();
        for (int i = 0; i < k; i++) {
            problem.add(new HashMap<>());
            for (Map.Entry<String, Integer> entry : counts.get(i).entrySet()) {
                problem.get(i).put(entry.getKey(), ((double) entry.getValue() + alpha) / (classCounts.get(i) + 2 * alpha));
            }
        }
    }

    public ArrayList<Double> predict(Collection<String> words) {
        ArrayList<Double> scores = new ArrayList<>();
        for (int j = 0; j < k; j++) {
            scores.add(0.0);
        }
        int counter = 0;
        double sum = 0;
        for (int j = 0; j < k; j++) {
            if (classCounts.get(j) != 0) {
                double score = Math.log((double) lambdas.get(j) * classCounts.get(j) / n);
                for (String word : allWords) {
                    double v = (problem.get(j).getOrDefault(word, (alpha / ((double) classCounts.get(j) + alpha * 2))));
                    if (words.contains(word)) {
                        score += Math.log(v);
                    } else {
                        score += Math.log(1 - v);
                    }
                }
                scores.set(j, score);
                counter++;
                sum += score;
            }
        }
        double avg = -sum / counter;
        sum = 0;
        for (int j = 0; j < k; j++) {
            if (classCounts.get(j) != 0) {
                scores.set(j, Math.exp(avg + scores.get(j)));
                sum += scores.get(j);
            }
        }
        for (int j = 0; j < k; j++) {
            scores.set(j, scores.get(j) / sum);
        }
        return scores;
    }
}
