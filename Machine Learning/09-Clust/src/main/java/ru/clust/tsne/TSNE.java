package ru.clust.tsne;

import com.jujutsu.tsne.*;
import com.jujutsu.utils.TSneUtils;
import ru.clust.bean.Pair;

import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public enum TSNE {
    FASTTSNE {
        @Override
        double[][] realm(double[][] realm) {
            TSne tSne = new FastTSne();
            TSneConfiguration configuration = new TSneConfig(realm, 2, realm[0].length, FASTTSNE.perplexity, FASTTSNE.ITERATIONS, true, 0.5D, false, false);
            return tSne.tsne(configuration);
        }
    },
    BIASTSNE {
        @Override
        double[][] realm(double[][] realm) {
            TSne tSne = new BlasTSne();
            TSneConfiguration configuration = new TSneConfig(realm, 2, realm[0].length, BIASTSNE.perplexity, BIASTSNE.ITERATIONS, true, 0.5D, false, false);
            return tSne.tsne(configuration);
        }
    },
    SIMPLE {
        @Override
        double[][] realm(double[][] realm) {
            TSne tSne = new BlasTSne();
            TSneConfiguration configuration = new TSneConfig(realm, 2, realm[0].length, SIMPLE.perplexity, SIMPLE.ITERATIONS, true, 0.5D, false, false);
            return tSne.tsne(configuration);
        }
    };

    abstract double[][] realm(double[][] realm);

    public ArrayList<Pair<Pair<Double, Double>, Integer>> tsne(ArrayList<Pair<ArrayList<Double>, Integer>> array) {
        double[][] input = new double[array.size()][array.get(0).first.size()];
        for (int i = 0; i < array.size(); i++) {
            for (int j = 0; j < array.get(i).first.size(); j++) {
                input[i][j] = array.get(i).first.get(j);
            }
        }
        double[][] output = realm(input);
        return IntStream.range(0, output.length)
                .mapToObj(i -> new Pair<>(new Pair<>(output[i][0], output[i][1]), array.get(i).second))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    private final int ITERATIONS = 200;
    private final double perplexity = 50.0;

}
