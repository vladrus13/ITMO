package ru.clust.cluster;

import ru.clust.bean.Cluster;
import ru.clust.bean.Clusters;
import ru.clust.bean.Pair;

import java.util.*;

public class DBSCANClusterer implements Clusterer {

    private final double radius;
    private final int minimaCount;

    private enum Status {
        NOISE, CLUSTERING
    }

    public DBSCANClusterer(double radius, int minimaCount) {
        this.radius = radius;
        this.minimaCount = minimaCount;
    }

    @Override
    public Clusters cluster(final Collection<Pair<ArrayList<Double>, Integer>> beans) {
        ArrayList<Cluster> clusterArrayList = new ArrayList<>();
        int count = 0;
        Map<Pair<ArrayList<Double>, Integer>, Status> got = new HashMap<>();
        for (Pair<ArrayList<Double>, Integer> bean : beans) {
            if (got.containsKey(bean)) {
                continue;
            }
            ArrayList<Pair<ArrayList<Double>, Integer>> neighbors = getNeighbors(bean, beans);
            //if (neighbors.size() >= minimaCount) {
                clusterArrayList.add(expand(new Cluster(count), bean, neighbors, beans, got));
                count++;
            //} else {
                //got.put(bean, Status.NOISE);
            //}
        }
        int countTaken = (int) got.entrySet().stream().filter(pa -> pa.getValue() == Status.CLUSTERING).count();
        return new Clusters(clusterArrayList, countTaken);
    }

    private Cluster expand(Cluster cluster,
                           final Pair<ArrayList<Double>, Integer> bean,
                           final ArrayList<Pair<ArrayList<Double>, Integer>> neighbors,
                           final Collection<Pair<ArrayList<Double>, Integer>> beans,
                           final Map<Pair<ArrayList<Double>, Integer>, Status> got) {
        cluster.add(bean);
        got.put(bean, Status.CLUSTERING);
        ArrayList<Pair<ArrayList<Double>, Integer>> copy = new ArrayList<>(neighbors);
        for (int i = 0; i < copy.size(); i++) {
            Pair<ArrayList<Double>, Integer> current = copy.get(i);
            Status status = got.get(current);
            if (status == null) {
                ArrayList<Pair<ArrayList<Double>, Integer>> currentNeighbors = getNeighbors(current, beans);
                if (currentNeighbors.size() >= minimaCount) {
                    merge(copy, currentNeighbors);
                }
            }
            if (status != Status.CLUSTERING) {
                got.put(current, Status.CLUSTERING);
                cluster.add(current);
            }
        }
        return cluster;
    }

    private ArrayList<Pair<ArrayList<Double>, Integer>> merge(ArrayList<Pair<ArrayList<Double>, Integer>> a,
                                                              ArrayList<Pair<ArrayList<Double>, Integer>> b) {
        Set<Pair<ArrayList<Double>, Integer>> set = new HashSet<>(a);
        for (Pair<ArrayList<Double>, Integer> p : b) {
            if (!set.contains(p)) {
                a.add(p);
            }
        }
        return a;
    }

    private ArrayList<Pair<ArrayList<Double>, Integer>> getNeighbors(Pair<ArrayList<Double>, Integer> bean, Collection<Pair<ArrayList<Double>, Integer>> beans) {
        ArrayList<Pair<ArrayList<Double>, Integer>> returned = new ArrayList<>();
        for (Pair<ArrayList<Double>, Integer> point : beans) {
            if (point != bean && ClusterUtils.distance(bean.first, point.first) <= radius) {
                returned.add(point);
            }
        }
        return returned;
    }
}
