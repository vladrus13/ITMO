package ru.clust.distance;

import ru.clust.bean.Cluster;
import ru.clust.bean.Clusters;
import ru.clust.bean.Pair;
import ru.clust.util.Utils;

import java.util.ArrayList;
import java.util.stream.IntStream;

public enum ExternalDistance {
    @Deprecated
    RAND {
        @Override
        public double calculate(Clusters array) {
            return 0;
        }
    },
    @Deprecated
    JACCARD {
        @Override
        public double calculate(Clusters array) {
            return 0;
        }
    },
    F {
        @Override
        public double calculate(Clusters array) {
            ArrayList<ArrayList<Integer>> matrix = F.matrix(array);
            int countClasses = array.getIn().stream()
                    .mapToInt(cluster ->
                            cluster.getCluster().stream()
                                    .mapToInt(pair -> pair.second)
                                    .max().orElseThrow())
                    .max().orElseThrow() + 1;
            ArrayList<Integer> classCounter = Utils.generate(countClasses, 0);
            ArrayList<Integer> clusterCounter = Utils.generate(array.getCountClusters(), 0);
            for (int i = 0; i < matrix.size(); i++) {
                for (int j = 0; j < matrix.get(i).size(); j++) {
                    classCounter.set(j, classCounter.get(j) + 1);
                    clusterCounter.set(i, clusterCounter.get(i) + 1);
                }
            }
            double F = 0.0;
            for (int i = 0; i < array.getCountClusters(); i++) {
                int finalI = i;
                F += ((double) clusterCounter.get(i) / array.getCountElements()) *
                        IntStream.range(0, countClasses)
                                .mapToDouble(j ->
                                        calc(matrix.get(finalI).get(j), clusterCounter.get(finalI), classCounter.get(j))).max().orElseThrow();
            }
            return F;
        }
    };

    private ArrayList<ArrayList<Integer>> matrix(Clusters clusters) {
        int countClusters = clusters.getCountClusters();
        int countClasses = clusters.getIn().stream()
                .mapToInt(cluster ->
                        cluster.getCluster().stream()
                                .mapToInt(pair -> pair.second)
                                .max().orElseThrow())
                .max().orElseThrow() + 1;
        ArrayList<ArrayList<Integer>> matrix = Utils.generate(countClusters, countClasses, 0);
        for (Cluster cluster : clusters.getIn()) {
            for (Pair<ArrayList<Double>, Integer> pair : cluster.getCluster()) {
                matrix.get(cluster.getId()).set(pair.second, matrix.get(cluster.getId()).get(pair.second) + 1);
            }
        }
        return matrix;
    }

    /**
     * Return metrix
     * @param clusters clusters
     * @return [TP, TN, FP, FN]
     */
    @Deprecated
    private ArrayList<Double> pairs(Clusters clusters) {
        return new ArrayList<>();
    }

    double calc(double matrix, double cluster, double classer) {
        return 2 * check(check(Math.pow(matrix, 2), (classer * cluster)),
                (check(matrix, classer) + check(matrix, cluster)));
    }

    double check(double a, double b) {
        if (Math.abs(b) <= 1e-5) return 0;
        else return a / b;
    }

    public abstract double calculate(Clusters array);
}
