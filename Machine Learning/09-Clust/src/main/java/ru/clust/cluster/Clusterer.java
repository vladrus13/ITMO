package ru.clust.cluster;

import ru.clust.bean.Clusters;
import ru.clust.bean.Pair;

import java.util.ArrayList;
import java.util.Collection;

public interface Clusterer {

    Clusters cluster(final Collection<Pair<ArrayList<Double>, Integer>> beans);
}
