package ru.vladrus13.drawing;

import ru.vladrus13.utils.DistanceFunction;
import ru.vladrus13.utils.KernelFunction;
import ru.vladrus13.utils.WindowCalculator;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Parser for network-testing result
 */
public class Parser {

    // naive/onehot | DistanceFunction | KernelFunction | WindowCalculator | Width/count window | F
    /**
     * All results
     */
    private final Map<String, Map<DistanceFunction, Map<KernelFunction, Map<WindowCalculator, Map<Integer, Double>>>>> data = new HashMap<>();

    /**
     * Add to map
     *
     * @param path             type of network
     * @param distanceFunction {@link DistanceFunction}
     * @param kernelFunction   {@link KernelFunction}
     * @param windowCalculator {@link WindowCalculator}
     * @param window           window
     * @param F                result
     */
    private void addToMap(String path, DistanceFunction distanceFunction,
                          KernelFunction kernelFunction, WindowCalculator windowCalculator,
                          Integer window, Double F) {
        if (!data.containsKey(path)) {
            data.put(path, new HashMap<>());
        }
        if (!data.get(path).containsKey(distanceFunction)) {
            data.get(path).put(distanceFunction, new HashMap<>());
        }
        if (!data.get(path).get(distanceFunction).containsKey(kernelFunction)) {
            data.get(path).get(distanceFunction).put(kernelFunction, new HashMap<>());
        }
        if (!data.get(path).get(distanceFunction).get(kernelFunction).containsKey(windowCalculator)) {
            data.get(path).get(distanceFunction).get(kernelFunction).put(windowCalculator, new HashMap<>());
        }
        data.get(path).get(distanceFunction).get(kernelFunction).get(windowCalculator).put(window, F);
    }

    /**
     * Constructor for class
     *
     * @throws IOException if we have problem with files
     */
    public Parser() throws IOException {
        BufferedReader bufferedReader;
        ArrayList<String> destinations = new ArrayList<>(List.of("naive", "onehot"));
        for (String destination : destinations) {
            bufferedReader = Files.newBufferedReader(Path.of(String.format("resources/%s.out", destination)));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                // example : "F = 0,431082 Window = FIXED k = 10 Distance = MANHATTAN Kernel = EPANECHNIKOV result = 724/2000"
                String[] splitted = line.split(" ");
                if (splitted.length != 18) continue;
                addToMap(destination,
                        DistanceFunction.valueOf(splitted[11]),
                        KernelFunction.valueOf(splitted[14]),
                        WindowCalculator.valueOf(splitted[5]),
                        Integer.parseInt(splitted[8]),
                        Double.parseDouble(splitted[2].replace(',', '.')));
            }
        }
    }

    /**
     * @return get all results
     */
    public Map<String, Map<DistanceFunction, Map<KernelFunction, Map<WindowCalculator, Map<Integer, Double>>>>> getData() {
        return data;
    }
}
