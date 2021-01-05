package ru.DT;

import ru.DT.bean.Pair;
import ru.DT.bean.Point;
import ru.DT.drawing.ImageCreator;
import ru.DT.drawing.ImageSaver;
import ru.DT.tree.DecisionTree;
import ru.DT.util.Splitter;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class Launcher {

    private static ArrayList<Integer> parseInt(String s) {
        String[] strings = s.split(" ");
        ArrayList<Integer> returned = new ArrayList<>();
        for (String str : strings) {
            returned.add(Integer.parseInt(str));
        }
        return returned;
    }

    private static ArrayList<Pair<ArrayList<Integer>, Integer>> readReader(BufferedReader bufferedReader, int count) throws IOException {
        ArrayList<Pair<ArrayList<Integer>, Integer>> result = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            ArrayList<Integer> parsed = parseInt(bufferedReader.readLine());
            int c = parsed.get(parsed.size() - 1);
            parsed.remove(parsed.size() - 1);
            result.add(new Pair<>(parsed, c));
        }
        return result;
    }

    public static void main(String[] args) {
        for (int i = 1; i <= 21; i++) {
            System.out.println("\t--- Test" + i);
            try (
                    BufferedReader bufferedReaderTest = Files.newBufferedReader(Path.of("resources").resolve("DT_txt").resolve(String.format("%02d_test.txt", i)));
                    BufferedReader bufferedReaderTrain = Files.newBufferedReader(Path.of("resources").resolve("DT_txt").resolve(String.format("%02d_train.txt", i)))
            ) {
                System.out.println("\t---Reading");
                ArrayList<Integer> temp = parseInt(bufferedReaderTest.readLine());
                int m = temp.get(0);
                int k = temp.get(1);
                int nTest = parseInt(bufferedReaderTest.readLine()).get(0);
                temp = parseInt(bufferedReaderTrain.readLine());
                if (temp.get(0) != m || temp.get(1) != k) {
                    System.out.println("Failed. Different data");
                    continue;
                }
                int nTrain = parseInt(bufferedReaderTrain.readLine()).get(0);
                ArrayList<Pair<ArrayList<Integer>, Integer>> test = readReader(bufferedReaderTest, nTest);
                ArrayList<Pair<ArrayList<Integer>, Integer>> train = readReader(bufferedReaderTrain, nTrain);
                System.out.println("\t---Reading end");
                ArrayList<Point> trainPoints = new ArrayList<>();
                ArrayList<Point> testPoints = new ArrayList<>();
                for (int maxDepth = 0; maxDepth < 40; maxDepth += 4) {
                    DecisionTree decisionTree = new DecisionTree(k, maxDepth, Splitter.GINI);
                    decisionTree.train(train);
                    int trainMistakes = 0;
                    int testMistakes = 0;
                    for (var bean : test) {
                        int precisionClass = decisionTree.precision(bean);
                        if (precisionClass != bean.second) testMistakes++;
                    }
                    testPoints.add(new Point(maxDepth * 20, (testMistakes * 800 / test.size())));
                    for (var bean : train) {
                        int precisionClass = decisionTree.precision(bean);
                        if (precisionClass != bean.second) trainMistakes++;
                    }
                    trainPoints.add(new Point(maxDepth * 20, (trainMistakes * 800 / train.size())));
                    System.out.printf("Test - %02d. Depth - %d. Mistakes: %d / %d - test, %d / %d - train%n",
                            i, maxDepth, testMistakes, test.size(), trainMistakes, train.size());
                }
                ImageSaver.save(ImageCreator.draw(String.valueOf(i), String.valueOf(i), testPoints, trainPoints), String.valueOf(i));
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }

        }
    }
}
