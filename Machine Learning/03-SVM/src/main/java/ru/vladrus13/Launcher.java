package ru.vladrus13;

import com.opencsv.exceptions.CsvValidationException;
import ru.vladrus13.bean.Bean;
import ru.vladrus13.bean.Point;
import ru.vladrus13.bean.SVM;
import ru.vladrus13.csv.CSVParser;
import ru.vladrus13.image.ImageCreator;
import ru.vladrus13.image.ImageSaver;
import ru.vladrus13.kernel.KernelClass;
import ru.vladrus13.kernel.KernelFunction;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Launcher {

    private static final int chapters = 100;

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    public static void main(String[] args) throws IOException, CsvValidationException {
        ArrayList<String> namesCSV = new ArrayList<>(List.of("chips", "geyser"));
        ArrayList<KernelClass> kernelClasses = KernelClass.generate();
        for (String name : namesCSV) {
            ArrayList<Bean> beans = CSVParser.readAll(Path.of("resources").resolve(name + ".csv"));
            KernelClass best = null;
            int resultBest = 0;
            for (KernelClass kernelClass : kernelClasses) {
                ArrayList<ArrayList<Double>> X = new ArrayList<>();
                ArrayList<Integer> Y = new ArrayList<>();
                for (Bean bean : beans) {
                    // bean.getPosition().add(1.0);
                    X.add(bean.getPosition());
                    Y.add(bean.getClassification());
                }
                System.out.println("Start machine:        " + kernelClass.toString());
                SVM svm = new SVM(X, Y, kernelClass);
                System.out.println("Start calculate:      " + kernelClass.toString());
                svm.calculateAnswer();
                System.out.println("Start predict manage: " + kernelClass.toString());
                double x_min = X.stream().mapToDouble(element -> element.get(0)).min().getAsDouble();
                double x_max = X.stream().mapToDouble(element -> element.get(0)).max().getAsDouble();
                double y_min = X.stream().mapToDouble(element -> element.get(1)).min().getAsDouble();
                double y_max = X.stream().mapToDouble(element -> element.get(1)).max().getAsDouble();
                double x_step = (x_max - x_min) / chapters;
                double y_step = (y_max - y_min) / chapters;
                ArrayList<ArrayList<Double>> colors = new ArrayList<>();
                for (int i = 0; i < chapters; i++) {
                    ArrayList<Double> temp = new ArrayList<>();
                    for (int j = 0; j < chapters; j++) {
                        temp.add(svm.predict(new ArrayList<>(List.of(x_min + i * x_step, y_min + j * y_step))));
                    }
                    colors.add(temp);
                }
                int result = 0;
                for (int i = 0; i < X.size(); i++) {
                    int predict = (int) svm.predict(X.get(i));
                    if (predict == Y.get(i)) {
                        result++;
                    }
                }
                if (result > resultBest) {
                    resultBest = result;
                    best = kernelClass;
                }
                System.out.println("Result: " + result + " from " + X.size());
                ImageSaver.save(ImageCreator.draw(name, kernelClass.toString(), X, Y,
                        new Point((int) (x_step * 800 / (x_max - x_min)), (int) (y_step * 800 / (y_max - y_min))), chapters, colors),
                        name, kernelClass);
            }
            if (best != null) {
                BufferedWriter bufferedWriter = Files.newBufferedWriter(Path.of("resources/" + name + ".txt"));
                bufferedWriter.write("Best: " + best.toString() + ".\nResult: " + ((double) resultBest / beans.size()));
                bufferedWriter.close();
            }
        }
    }
}
