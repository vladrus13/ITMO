package ru.vladrus13;

import ru.vladrus13.bean.Point;
import ru.vladrus13.drawing.ImageCreator;
import ru.vladrus13.drawing.ImageSaver;
import ru.vladrus13.drawing.Parser;
import ru.vladrus13.utils.DistanceFunction;
import ru.vladrus13.utils.KernelFunction;
import ru.vladrus13.utils.WindowCalculator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Launcher for drawing
 */
public class LauncherDrawing {

    /**
     * From result in network to point in graphic-coordinates
     */
    static final Function<Map.Entry<Integer, Double>, Point> entryToPoint = element ->
            new Point(element.getKey() * 80, (int) (element.getValue() * 800));

    /**
     * @param args ignored
     * @throws IOException if we have problem with files
     */
    public static void main(String[] args) throws IOException {
        Parser parser = new Parser();
        ArrayList<String> destinations = new ArrayList<>(List.of("naive", "onehot"));
        for (String destination : destinations) {
            for (DistanceFunction distanceFunction : DistanceFunction.values()) {
                for (KernelFunction kernelFunction : KernelFunction.values()) {
                    Map<Integer, Double> fixedMap = parser.getData().get(destination).get(distanceFunction).get(kernelFunction).get(WindowCalculator.FIXED);
                    Map<Integer, Double> variableMap = parser.getData().get(destination).get(distanceFunction).get(kernelFunction).get(WindowCalculator.VARIABLE);
                    ArrayList<Point> fixed = fixedMap.entrySet().stream().map(entryToPoint).collect(Collectors.toCollection(ArrayList::new));
                    ArrayList<Point> variable = variableMap.entrySet().stream().map(entryToPoint).collect(Collectors.toCollection(ArrayList::new));
                    ImageSaver.save(ImageCreator.draw(destination, distanceFunction, kernelFunction, fixed, variable),
                            destination, distanceFunction, kernelFunction);
                }
            }
        }
    }
}
