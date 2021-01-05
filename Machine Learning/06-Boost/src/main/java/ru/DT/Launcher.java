package ru.DT;

import ru.DT.bean.AdaBoost;
import ru.DT.bean.Pair;
import ru.DT.bean.Point;
import ru.DT.drawing.ImageCreator;
import ru.DT.drawing.ImageSaver;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Launcher {

    private static final int countPixels = 10;

    private static ArrayList<Pair<ArrayList<Double>, Integer>> read(String name) throws IOException {
        BufferedReader bufferedReader = Files.newBufferedReader(Path.of("resources").resolve(name + ".csv"), StandardCharsets.UTF_8);
        bufferedReader.readLine();
        ArrayList<Pair<ArrayList<Double>, Integer>> beans = new ArrayList<>();
        String s;
        while ((s = bufferedReader.readLine()) != null) {
            String[] readen = s.split(",");
            beans.add(new Pair<>(new ArrayList<>(List.of(Double.parseDouble(readen[0]), Double.parseDouble(readen[1]))), readen[2].equals("N") ? 1 : 0));
        }
        return beans;
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    private static double checkTree(AdaBoost boost, ArrayList<Pair<ArrayList<Double>, Integer>> beans, int step, String file) {
        ArrayList<ArrayList<Integer>> result = new ArrayList<>();
        int countSteps = 800 / countPixels;
        double xMinima = beans.stream().mapToDouble(bean -> bean.first.get(0)).min().getAsDouble();
        double xMaxima = beans.stream().mapToDouble(bean -> bean.first.get(0)).max().getAsDouble();
        double yMinima = beans.stream().mapToDouble(bean -> bean.first.get(1)).min().getAsDouble();
        double yMaxima = beans.stream().mapToDouble(bean -> bean.first.get(1)).max().getAsDouble();
        double realSizeX = (xMaxima - xMinima) / countSteps;
        double realSizeY = (yMaxima - yMinima) / countSteps;
        for (double x = xMinima; x < xMaxima; x += realSizeX) {
            ArrayList<Integer> temp = new ArrayList<>();
            for (double y = yMinima; y < yMaxima; y += realSizeY) {
                temp.add(boost.predict(new Pair<>(new ArrayList<>(List.of(x, y)), null)));
            }
            result.add(temp);
        }
        BufferedImage image = ImageCreator.empty(file, String.valueOf(step));
        ImageCreator.addMatrix(image, result);
        ArrayList<String> x = new ArrayList<>();
        ArrayList<String> y = new ArrayList<>();
        for (double xx = xMinima; xx < xMaxima; xx += (xMaxima - xMinima) / 10) {
            x.add(String.valueOf(xx));
        }
        for (double yy = yMinima; yy < yMaxima; yy += (yMaxima - yMinima) / 10) {
            y.add(String.valueOf(yy));
        }
        ImageCreator.addValues(image, x, y);
        ArrayList<Point> red = new ArrayList<>();
        ArrayList<Point> green = new ArrayList<>();
        int countWrong = 0;
        for (Pair<ArrayList<Double>, Integer> bean : beans) {
            Point point = new Point((int) ((bean.first.get(0) - xMinima) * 800 / (xMaxima - xMinima)),
                    (int) ((bean.first.get(1) - yMinima) * 800 / (yMaxima - yMinima)));
            if (bean.second == 1) {
                red.add(point);
            } else {
                green.add(point);
            }
            countWrong += bean.second != (boost.predict(bean)) ? 1 : 0;
        }
        ImageCreator.addPoints(image, red, Color.RED);
        ImageCreator.addPoints(image, green, Color.GREEN);
        ImageSaver.save(image, file, String.valueOf(step));
        return (double) countWrong / beans.size();
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        List<Integer> rows = List.of(1, 2, 3, 5, 8, 13, 21, 34, 55);
        Map<Integer, Double> results = new ConcurrentHashMap<>();
        for (String name : List.of("chips", "geyser")) {
            System.out.println("\tTest: " + name);
            ArrayList<Pair<ArrayList<Double>, Integer>> beans = read(name);
            ArrayList<Integer> numbers = new ArrayList<>(rows);
            ExecutorService executorService = Executors.newFixedThreadPool(Thread.activeCount());
            AdaBoost boost = new AdaBoost(beans);
            int step = 0;
            while (!numbers.isEmpty()) {
                System.out.println("Go to step " + step);
                boost.fit();
                step++;
                if (step == numbers.get(0)) {
                    numbers.remove(0);
                    AdaBoost copy = boost;
                    int real = step;
                    //executorService.submit(() -> {
                        try {
                            System.out.println("Ready to built " + real);
                            double result = checkTree(copy, beans, real, name);
                            results.put(real, result);
                            System.out.println(real + " done!");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    //});
                }
            }
            do {
                Thread.sleep(100);
            } while (results.size() != rows.size());
            ArrayList<Point> points = new ArrayList<>();
            BufferedImage image = ImageCreator.empty(name, "result");
            for (Map.Entry<Integer, Double> entry : results.entrySet()) {
                points.add(new Point(entry.getKey() * 800 / 55, (int) (entry.getValue() * 800)));
            }
            ImageCreator.addPoints(image, points, Color.BLACK);
            ArrayList<String> x = new ArrayList<>();
            ArrayList<String> y = new ArrayList<>();
            for (int i = 0; i <= 55; i += 5) {
                x.add(String.valueOf(i));
            }
            for (int i = 0; i <= 10; i++) {
                y.add(String.valueOf(((double) i) / 10));
            }
            ImageCreator.addValues(image, x, y);
            ImageSaver.save(image, name, "result");
        }
    }
}
