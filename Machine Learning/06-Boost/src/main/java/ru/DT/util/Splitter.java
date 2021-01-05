package ru.DT.util;

import ru.DT.bean.Pair;
import ru.DT.bean.ResultPair;

import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.IntStream;

public enum Splitter {
    GINI {
        @Override
        public Optional<ResultPair> split(int k, ArrayList<Pair<ArrayList<Double>, Integer>> beans, int feature) {
            double bestScore = Double.MIN_VALUE;
            Optional<ResultPair> bestSplit = Optional.empty();
            for (int i = 0; i < feature; i++) {
                int finalI = i;
                beans.sort((o1, o2) -> (int) ((o2.first.get(finalI) - o1.first.get(finalI)) * 100000));
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
                for (Pair<ArrayList<Double>, Integer> bean : beans) {
                    rightSum -= right.get(bean.second) * right.get(bean.second);
                    right.set(bean.second, right.get(bean.second) + 1);
                    rightSum += right.get(bean.second) * right.get(bean.second);
                }
                double previous = -1;
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
        public Optional<ResultPair> split(int k, ArrayList<Pair<ArrayList<Double>, Integer>> beans, int feature) {
            double bestScore = Double.MAX_VALUE;
            Optional<ResultPair> bestSplit = Optional.empty();
            for (int i = 0; i < feature; i++) {
                int finalI = i;
                beans.sort((o1, o2) -> (int) ((o2.first.get(finalI) - o1.first.get(finalI)) * 10000));
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
                for (Pair<ArrayList<Double>, Integer> bean : beans) {
                    right.set(bean.second, right.get(bean.second) + 1);
                }
                double previous = -1;
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
    public abstract Optional<ResultPair> split(int k, ArrayList<Pair<ArrayList<Double>, Integer>> beans, int feature);
}
