package ru.clust;

import ru.clust.bean.Clusters;
import ru.clust.bean.Pair;
import ru.clust.cluster.Clusterer;
import ru.clust.cluster.DBSCANClusterer;
import ru.clust.distance.ExternalDistance;
import ru.clust.drawing.ClusterDrawer;
import ru.clust.drawing.ImageSaver;
import ru.clust.util.CSVReader;
import ru.clust.util.Utils;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Launcher {

    private static final ArrayList<String> bases = new ArrayList<>(List.of("dataset_191_wine"));

    public static void main(String[] args) throws IOException {
        BufferedWriter bufferedWriter = Files.newBufferedWriter(Path.of("resources").resolve("result.txt"));
        for (String dataset : bases) {
            System.out.println("Test: " + dataset);
            ArrayList<Pair<ArrayList<Double>, Integer>> data = Utils.normalize(CSVReader.read(dataset));
            ImageSaver.save(ClusterDrawer.createImageCompose(dataset, "real", data), dataset, "real");
            for (double radius = .01; radius < .3; radius += .01) {
                for (int minSize = 1; minSize < 3; minSize++) {
                    System.out.printf("\tOn radius = %f, minSize = %d%n", radius, minSize);
                    Clusterer clusterer = new DBSCANClusterer(radius, minSize);
                    Clusters clusters = clusterer.cluster(data);
                    String name = String.format("r-%f-size-%d", radius, minSize);
                    ImageSaver.save(ClusterDrawer.createImageCompose(dataset, name, clusters), dataset, name);
                    bufferedWriter.write(String.format("r-%f-size-%d-f-%f\n", radius, minSize, ExternalDistance.F.calculate(clusters)));
                }
            }
        }
        bufferedWriter.close();
    }
}
