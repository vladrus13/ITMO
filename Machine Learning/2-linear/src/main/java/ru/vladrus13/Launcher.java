package ru.vladrus13;

import ru.vladrus13.bean.Bean;
import ru.vladrus13.bean.Point;
import ru.vladrus13.drawing.ImageCreator;
import ru.vladrus13.drawing.ImageSaver;
import ru.vladrus13.files.BeanReader;
import ru.vladrus13.methods.Generic;
import ru.vladrus13.methods.Gradient;
import ru.vladrus13.methods.MMS;
import ru.vladrus13.methods.Method;
import ru.vladrus13.utils.NRMSE;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Launcher {

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    public static void main(String[] args) throws IOException {
        for (int file = 0; file <= 6; file++) {
            System.out.println("File: " + file);
            BeanReader.readBeans(file);
            ArrayList<Bean> train = BeanReader.getTrain();
            ArrayList<Bean> test = BeanReader.getTest();
            Map<String, ArrayList<Point>> testCalc = new HashMap<>();
            Map<String, ArrayList<Point>> trainCalc = new HashMap<>();
            ArrayList<String> names = new ArrayList<>(List.of("Generic", "Gradient", "MMS"));
            for (String name : names) {
                testCalc.put(name, new ArrayList<>());
                trainCalc.put(name, new ArrayList<>());
            }
            for (int kk = 1; kk <= 10; kk++) {
                System.out.println("\tK = " + kk);
                double k = kk / 10.0;
                ArrayList<Method> methods = new ArrayList<>(List.of(
                        new Generic(train),
                        new Gradient(train, kk),
                        new MMS(train, k)));
                for (Method method : methods) {
                    double testTest = NRMSE.calculate(test, method.getAnswer());
                    double trainTest = NRMSE.calculate(train, method.getAnswer());
                    testCalc.get(method.getName()).add(new Point(kk * 80, (long) testTest));
                    trainCalc.get(method.getName()).add(new Point(kk * 80, (long) trainTest));
                }
            }
            for (String name : names) {
                ArrayList<Point> testArray = testCalc.get(name);
                long maxima = testArray.stream().mapToLong(element -> element.y).max().getAsLong() / 800;
                for (int i = 0; i < 10; i++) {
                    testArray.set(i, new Point((i + 1) * 80, testArray.get(i).y / maxima));
                }
                ArrayList<Point> trainArray = trainCalc.get(name);
                maxima = trainArray.stream().mapToLong(element -> element.y).max().getAsLong() / 800;
                for (int i = 0; i < 10; i++) {
                    trainArray.set(i, new Point((i + 1) * 80, trainArray.get(i).y / maxima));
                }
                ImageSaver.save(ImageCreator.draw(String.valueOf(file), name, testArray, trainArray), String.valueOf(file), name);
            }
        }
    }
}
