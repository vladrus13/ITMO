package ru.clust.bean;

import java.util.ArrayList;

public class Clusters {
    private final ArrayList<Cluster> in;
    private final int countClusters;
    private final int countElements;

    public Clusters(ArrayList<Cluster> in, int countElements) {
        this.in = in;
        this.countClusters = in.size();
        this.countElements = countElements;
    }

    public ArrayList<Cluster> getIn() {
        return in;
    }

    public int getCountClusters() {
        return countClusters;
    }

    public int getCountElements() {
        return countElements;
    }
}
