package ru.clust.bean;

import java.util.ArrayList;

public class Cluster {
    private final int id;
    private final ArrayList<Pair<ArrayList<Double>, Integer>> cluster;

    public Cluster(int id, ArrayList<Pair<ArrayList<Double>, Integer>> cluster) {
        this.id = id;
        this.cluster = cluster;
    }

    public Cluster(int id) {
        this.id = id;
        this.cluster = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public ArrayList<Pair<ArrayList<Double>, Integer>> getCluster() {
        return cluster;
    }

    public void add(final Pair<ArrayList<Double>, Integer> added) {
        cluster.add(added);
    }
}
