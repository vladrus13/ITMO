package ru.clust.drawing;

import ru.clust.bean.Cluster;
import ru.clust.bean.Clusters;
import ru.clust.bean.Pair;
import ru.clust.bean.Point;
import ru.clust.tsne.TSNE;
import ru.clust.util.Utils;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class ClusterDrawer {
    private static final TSNE tsne = TSNE.FASTTSNE;
    private static final ArrayList<Color> colors = new ArrayList<>(List.of(Color.RED, Color.BLUE, Color.GREEN, Color.ORANGE));

    public static BufferedImage createImageCompose(String name, String data, ArrayList<Pair<ArrayList<Double>, Integer>> arrayList) {
        return createImage(name, data, tsne.tsne(arrayList));
    }

    public static BufferedImage createImage(String name, String data, ArrayList<Pair<Pair<Double, Double>, Integer>> arrayList) {
        int countClasses = arrayList.stream().mapToInt(i -> i.second).max().orElseThrow() + 1;
        double maxX = arrayList.stream().mapToDouble(i -> i.first.first).max().orElseThrow();
        double minX = arrayList.stream().mapToDouble(i -> i.first.first).min().orElseThrow();
        double maxY = arrayList.stream().mapToDouble(i -> i.first.second).max().orElseThrow();
        double minY = arrayList.stream().mapToDouble(i -> i.first.second).min().orElseThrow();
        BufferedImage image = ImageCreator.empty(name, data);
        ArrayList<String> x = new ArrayList<>();
        ArrayList<String> y = new ArrayList<>();
        for (double xx = minX; xx < maxX; xx += (maxX - minX) / 10) {
            x.add(String.valueOf(xx));
        }
        for (double yy = minY; yy < maxY; yy += (maxY - minY) / 10) {
            y.add(String.valueOf(yy));
        }
        ImageCreator.addValues(image, x, y);
        for (int i = 0; i < countClasses; i++) {
            ArrayList<Point> points = new ArrayList<>();
            for (Pair<Pair<Double, Double>, Integer> bean : arrayList) {
                if (bean.second == i) {
                    points.add(new Point(
                            (int) ((bean.first.first - minX) * 800 / (maxX - minX)),
                            (int) ((bean.first.second - minY) * 800 / (maxY - minY))
                    ));
                }
            }
            if (i >= colors.size()) {
                ImageCreator.addPoints(image, points, Utils.generate());
            } else {
                ImageCreator.addPoints(image, points, colors.get(i));
            }
        }
        return image;
    }

    public static BufferedImage createImageCompose(String name, String data, Clusters clusters) {
        ArrayList<Pair<ArrayList<Double>, Integer>> result = new ArrayList<>();
        for (int i = 0; i < clusters.getCountClusters(); i++) {
            for (Pair<ArrayList<Double>, Integer> in : clusters.getIn().get(i).getCluster()) {
                result.add(new Pair<>(in.first, i));
            }
        }
        return createImageCompose(name, data, result);
    }
}
