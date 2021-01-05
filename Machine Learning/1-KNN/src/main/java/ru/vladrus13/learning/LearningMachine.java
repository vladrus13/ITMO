package ru.vladrus13.learning;

import ru.vladrus13.bean.Network;
import ru.vladrus13.bean.Person;
import ru.vladrus13.csv.CSVParser;
import ru.vladrus13.utils.*;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Class learning
 */
public class LearningMachine {

    /**
     * Logger
     */
    private static final Logger logger = Logger.getLogger(LearningMachine.class.getName());

    /**
     * @param array array where we increase element
     * @param i     row
     * @param j     column
     */
    public static void increaseElement(ArrayList<ArrayList<Integer>> array, int i, int j) {
        ArrayList<Integer> row = array.get(i);
        row.set(j, row.get(j) + 1);
    }

    /**
     * Start testing
     *
     * @throws IOException if we have problem with files
     */
    public static void start() throws IOException {
        BufferedWriter bufferedWriter;
        ArrayList<Person> persons;
        try {
            persons = new ArrayList<>(new HashSet<>(CSVParser.readAll()));
        } catch (Exception e) {
            Writer.printStackTrace(logger, e);
            return;
        }
        @SuppressWarnings("OptionalGetWithoutIsPresent")
        int maxResult = persons.stream().mapToInt(Person::getResult).max().getAsInt();
        ArrayList<Integer> kList = new ArrayList<>(List.of(1, 3, 5, 7, 9, 10));
        int superK = 0;
        double superF;
        DistanceFunction superDistanceFunction = DistanceFunction.EUCLIDEAN;
        KernelFunction superKernelFunction = KernelFunction.COSINE;
        WindowCalculator superWindowCalculator = WindowCalculator.FIXED;
        int superCount = 0;
        HashMap<Boolean, String> variants = new HashMap<>(Map.ofEntries(
                new AbstractMap.SimpleEntry<>(Boolean.TRUE, "naive"),
                new AbstractMap.SimpleEntry<>(Boolean.FALSE, "onehot")));
        persons = persons.stream().limit(2000).collect(Collectors.toCollection(ArrayList::new));
        for (boolean isNaive : variants.keySet()) {
            bufferedWriter = Files.newBufferedWriter(Path.of(String.format("resources/%s.out", variants.get(isNaive))));
            superF = -1;
            for (WindowCalculator windowCalculator : WindowCalculator.values()) {
                for (int k : kList) {
                    double realK = windowCalculator.equals(WindowCalculator.VARIABLE) ? 1.0 / k : k;
                    for (DistanceFunction distanceFunction : DistanceFunction.values()) {
                        for (KernelFunction kernelFunction : KernelFunction.values()) {
                            Network network = new Network(distanceFunction, kernelFunction, realK, windowCalculator, isNaive);
                            network.learn(persons);
                            int right = 0;
                            ArrayList<ArrayList<Integer>> matrix = new ArrayList<>();
                            IntStream.range(0, maxResult + 1).forEach(index -> {
                                matrix.add(new ArrayList<>());
                                IntStream.range(0, maxResult + 1).forEach(indexJ -> matrix.get(index).add(0));
                            });
                            for (Person kicked : persons) {
                                network.remove(kicked);
                                Person networkPerson = kicked.copy();
                                network.get(networkPerson);
                                right += networkPerson.getResult() == kicked.getResult() ? 1 : 0;
                                increaseElement(matrix, kicked.getResult(), networkPerson.getResult());
                                network.learn(kicked);
                            }
                            FCalculator fCalculator = new FCalculator(matrix);
                            double F = fCalculator.returnMicro().first;
                            if (superF < F) {
                                superF = F;
                                superK = k;
                                superCount = right;
                                superDistanceFunction = distanceFunction;
                                superKernelFunction = kernelFunction;
                                superWindowCalculator = windowCalculator;
                            }
                            System.out.printf("F = %f Window = %s k = %d Distance = %s Kernel = %s result = %d/%d\n",
                                    F, windowCalculator, k, distanceFunction.name(), kernelFunction.name(), right, persons.size());
                            bufferedWriter.write(String.format("F = %f Window = %s k = %d Distance = %s Kernel = %s result = %d/%d\n",
                                    F, windowCalculator, k, distanceFunction.name(), kernelFunction.name(), right, persons.size()));
                        }
                    }
                }
            }
            bufferedWriter.write("----------------------------\n");
            bufferedWriter.write("---------TOP RESULT---------\n");
            bufferedWriter.write("----------------------------\n");
            bufferedWriter.write(String.format("F = %f Window = %s k = %d Distance = %s Kernel = %s result = %d/%d\n",
                    superF,
                    superWindowCalculator,
                    superK,
                    superDistanceFunction.name(),
                    superKernelFunction.name(),
                    superCount,
                    persons.size()));
            bufferedWriter.close();
        }
    }
}
