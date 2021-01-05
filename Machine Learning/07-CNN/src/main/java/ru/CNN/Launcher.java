package ru.CNN;

import ru.CNN.bean.Network;
import ru.CNN.bean.Pair;
import ru.CNN.bean.Tensor;
import ru.CNN.bean.TensorSize;
import ru.CNN.node.CnvType;
import ru.CNN.reader.MNISTReader;
import ru.CNN.util.Utils;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Launcher {

    public static final String TRAIN_IMAGES = "train-images-idx3-ubyte";
    public static final String TRAIN_LABELS = "train-labels-idx1-ubyte";
    public static final String TEST_IMAGES = "t10k-images-idx3-ubyte";
    public static final String TEST_LABELS = "t10k-labels-idx1-ubyte";
    public static final ArrayList<String> databases = new ArrayList<>(List.of("digits", "fashion"));
    public static final int TRIES = 5;

    public static Pair<ArrayList<Tensor>, ArrayList<Integer>> loadTrain(String baseName) {
        return new Pair<>(MNISTReader.readImages(Path.of("resources").resolve(baseName).resolve(TRAIN_IMAGES)),
                MNISTReader.readLabels(Path.of("resources").resolve(baseName).resolve(TRAIN_LABELS)));
    }

    public static Pair<ArrayList<Tensor>, ArrayList<Integer>> loadTest(String baseName) {
        return new Pair<>(MNISTReader.readImages(Path.of("resources").resolve(baseName).resolve(TEST_IMAGES)),
                MNISTReader.readLabels(Path.of("resources").resolve(baseName).resolve(TEST_LABELS)));
    }

    public static Network getSuperNetwork(int labels, int h, int w) {
        Network network = new Network(new TensorSize(1, h, w));
        network.addCnv(20, 6, 6, 2, 1, CnvType.M);
        network.addRelu(10);
        network.addCnv(5, 5, 5, 2, 1, CnvType.C);
        network.addBias();
        network.addPool(2);
        network.addCnv(20, 4, 4, 1, 1, CnvType.M);
        network.addRelu(10);
        network.addBias();
        network.addPool(2);
        network.end(labels);
        return network;
    }

    public static String toStringA(ArrayList<Integer> a) {
        StringBuilder sb = new StringBuilder();
        for (Integer i : a) {
            sb.append(String.format("%3d", i));
        }
        return sb.toString();
    }

    public static String toStringAA(ArrayList<ArrayList<Integer>> a) {
        StringBuilder sb = new StringBuilder();
        for (ArrayList<Integer> i : a) {
            sb.append(toStringA(i)).append(System.lineSeparator());
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        for (String databaseName : databases) {
            System.out.println("Test: " + databaseName);
            Pair<ArrayList<Tensor>, ArrayList<Integer>> base = loadTrain(databaseName);
            System.out.println("Data loaded. Images: " + base.first.size());
            int labels = Utils.maxInt.apply(base.second);
            Network network = getSuperNetwork(labels + 1, base.first.get(0).get(0).in.size(), base.first.get(0).get(0).get(0).in.size());
            network.addImages(base.first, base.second);
            base = loadTest(databaseName);
            System.out.println("Data loaded");
            int countWrong = 0;
            ArrayList<ArrayList<Integer>> matrix = Utils.generate(labels + 1, labels + 1, 0);
            for (int i = 0; i < base.first.size(); i++) {
                int predict = network.predict(base.first.get(i));
                if (predict != base.second.get(i)) {
                    countWrong++;
                }
                matrix.get(base.second.get(i)).set(predict, matrix.get(base.second.get(i)).get(predict) + 1);
            }
            System.out.printf("--- Result: %d / %d%n", countWrong, base.first.size());
            System.out.println(toStringAA(matrix));
        }
    }
}
