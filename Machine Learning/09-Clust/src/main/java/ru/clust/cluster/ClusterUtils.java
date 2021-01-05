package ru.clust.cluster;

import ru.clust.bean.Cluster;
import ru.clust.bean.Clusters;
import ru.clust.bean.Pair;

import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ClusterUtils {
    public static Clusters clusterization(ArrayList<Pair<ArrayList<Double>, Integer>> array) {
        int countClusters = array.stream().mapToInt(i -> i.second).max().orElseThrow();
        ArrayList<Cluster> clusters = IntStream
                .range(0, countClusters)
                .mapToObj(Cluster::new)
                .collect(Collectors.toCollection(ArrayList::new));
        for (Pair<ArrayList<Double>, Integer> bean : array) {
            clusters.get(bean.second).add(bean);
        }
        return new Clusters(clusters, array.size());
    }

    public static double distance(ArrayList<Double> a, ArrayList<Double> b) {
        double d = 0.0;
        for (int i = 0; i < a.size(); i++) {
            d += Math.pow(a.get(i) - b.get(i), 2);
        }
        return d;
    }
}
