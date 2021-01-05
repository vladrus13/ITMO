package ru.clust.util;

import com.jujutsu.tsne.TSne;
import ru.clust.bean.Pair;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Utils {
    public static ArrayList<Pair<ArrayList<Double>, Integer>> normalize(ArrayList<Pair<ArrayList<Double>, Integer>> arrayList) {
        int attributes = arrayList.get(0).first.size();
        ArrayList<Double> max = IntStream
                .range(0, attributes)
                .mapToDouble(i -> arrayList.stream()
                        .mapToDouble(bean -> bean.first.get(i))
                        .max().orElseThrow())
                .boxed()
                .collect(Collectors.toCollection(ArrayList::new));
        ArrayList<Double> min = IntStream
                .range(0, attributes)
                .mapToDouble(i -> arrayList.stream()
                        .mapToDouble(bean -> bean.first.get(i))
                        .min().orElseThrow())
                .boxed()
                .collect(Collectors.toCollection(ArrayList::new));
        ArrayList<Pair<ArrayList<Double>, Integer>> beans = new ArrayList<>();
        for (Pair<ArrayList<Double>, Integer> bean : arrayList) {
            ArrayList<Double> temp =
                    IntStream.range(0, attributes)
                            .mapToDouble(i -> (bean.first.get(i) - min.get(i)) / (max.get(i) - min.get(i)))
                            .boxed().collect(Collectors.toCollection(ArrayList::new));
            beans.add(new Pair<>(temp, bean.second));
        }
        return beans;
    }

    public static ArrayList<ArrayList<Integer>> generate(int n, int m, int x) {
        ArrayList<ArrayList<Integer>> returned = new ArrayList<>();
        IntStream.range(0, n).forEach(i -> {
            returned.add(new ArrayList<>());
            IntStream.range(0, m).forEach(j ->
                    returned.get(i).add(x));
        });
        return returned;
    }

    public static ArrayList<Integer> generate(int n, int x) {
        ArrayList<Integer> returned = new ArrayList<>();
        IntStream.range(0, n).forEach(i -> returned.add(x));
        return returned;
    }

    public static Color generate() {
        Random random = new Random(System.currentTimeMillis());
        return new Color((int) (random.nextDouble() * 255), (int) (random.nextDouble() * 255), (int) (random.nextDouble() * 255));
    }
}
