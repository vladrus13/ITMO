package ru.vladrus13.files;

import ru.vladrus13.bean.Bean;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class BeanReader {

    private static final ArrayList<Bean> train = new ArrayList<>();
    private static final ArrayList<Bean> test = new ArrayList<>();

    public static void readBeans(int file) throws IOException {
        BufferedReader bufferedReader = Files.newBufferedReader(Path.of("resources/LR/" + file + ".txt"));
        int m = Integer.parseInt(bufferedReader.readLine());
        int n = Integer.parseInt(bufferedReader.readLine());
        readModel(bufferedReader, m, n, train);
        n = Integer.parseInt(bufferedReader.readLine());
        readModel(bufferedReader, m, n, test);
    }

    private static void readModel(BufferedReader bufferedReader, int m, int n, ArrayList<Bean> train) throws IOException {
        for (int i = 0; i < n; i++) {
            train.add(new Bean(Arrays.stream(bufferedReader.readLine().split(" "))
                    .map(Double::parseDouble).collect(Collectors.toCollection(ArrayList::new))));
            assert train.get(i).getAttributes().size() == m;
        }
    }

    public static ArrayList<Bean> getTrain() {
        return train;
    }

    public static ArrayList<Bean> getTest() {
        return test;
    }
}
