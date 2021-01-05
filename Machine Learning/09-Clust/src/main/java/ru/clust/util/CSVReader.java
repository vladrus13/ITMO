package ru.clust.util;

import ru.clust.bean.Pair;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CSVReader {

    public static ArrayList<Pair<ArrayList<Double>, Integer>> read(String name) throws IOException {
        Map<String, Integer> classes = new HashMap<>();
        int countClasses = 0;
        BufferedReader bufferedReader = Files.newBufferedReader(Path.of("resources").resolve(name + ".csv"), StandardCharsets.UTF_8);
        bufferedReader.readLine();
        ArrayList<Pair<ArrayList<Double>, Integer>> beans = new ArrayList<>();
        String s;
        while ((s = bufferedReader.readLine()) != null) {
            String[] readen = s.split(",");
            int c;
            if (classes.containsKey(readen[0])) {
                c = classes.get(readen[0]);
            } else {
                c = countClasses;
                classes.put(readen[0], c);
                countClasses++;
            }
            beans.add(new Pair<>(
                    IntStream.range(1, readen.length)
                            .mapToDouble(i -> Double.parseDouble(readen[i]))
                            .boxed()
                            .collect(Collectors.toCollection(ArrayList::new)),
                    c
            ));
        }
        return beans;
    }
}
