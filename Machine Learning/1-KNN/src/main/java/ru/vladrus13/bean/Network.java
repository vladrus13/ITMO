package ru.vladrus13.bean;

import ru.vladrus13.utils.DistanceFunction;
import ru.vladrus13.utils.KernelFunction;
import ru.vladrus13.utils.WindowCalculator;

import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Network (just table) class
 */
public class Network {
    /**
     * {@link DistanceFunction}
     */
    private final DistanceFunction distanceFunction;
    /**
     * {@link KernelFunction}
     */
    private final KernelFunction kernelFunction;
    /**
     * {@link WindowCalculator}
     */
    private final WindowCalculator windowCalculator;
    /**
     * All persons
     */
    private final ArrayList<Person> data;
    /**
     * Window width or kNN-value
     *
     * @see WindowCalculator
     */
    private final double k;
    /**
     * Is naive method
     */
    private final boolean isNaive;

    /**
     * Constructor for class
     *
     * @param distanceFunction {@link DistanceFunction}
     * @param kernelFunction   {@link KernelFunction}
     * @param k                Window width or kNN-value
     * @param windowCalculator {@link WindowCalculator}
     * @param isNaive          is naive method
     */
    public Network(DistanceFunction distanceFunction, KernelFunction kernelFunction, double k, WindowCalculator windowCalculator, boolean isNaive) {
        this.distanceFunction = distanceFunction;
        this.kernelFunction = kernelFunction;
        this.windowCalculator = windowCalculator;
        this.k = k;
        this.isNaive = isNaive;
        data = new ArrayList<>();
    }

    /**
     * Add to data more elements
     *
     * @param newData elements
     */
    public void learn(ArrayList<Person> newData) {
        data.addAll(newData);
    }

    /**
     * Add to data more elements
     *
     * @param newData elements
     */
    public void learn(Person newData) {
        data.add(newData);
    }

    /**
     * Remove from data element
     *
     * @param removingData removing element
     */
    public void remove(Person removingData) {
        data.remove(removingData);
    }

    /**
     * Set to person his result from network
     *
     * @param person person
     */
    public void get(Person person) {
        double neibe;
        ArrayList<Double> target = person.getPosition();
        data.sort((o1, o2) -> (int) ((distanceFunction.get(o1.getPosition(), target) - distanceFunction.get(o2.getPosition(), target)) * 1000000));
        if (windowCalculator.equals(WindowCalculator.FIXED)) {
            neibe = distanceFunction.get(data.get((int) k).getPosition(), target);
        } else {
            neibe = k;
        }
        if (neibe < 0.00001) {
            double result;
            ArrayList<Person> neighborhood = data.stream().filter(element -> element.getPosition().equals(target)).collect(Collectors.toCollection(ArrayList::new));
            if (neighborhood.size() == 0) {
                result = data.stream().mapToDouble(Person::getResult).sum() / data.size();
            } else {
                result = neighborhood.stream().mapToDouble(Person::getResult).sum() / neighborhood.size();
            }
            person.setResult((int) Math.round(result));
        } else {
            if (isNaive) {
                double result;
                double x1 = data
                        .parallelStream()
                        .mapToDouble(element ->
                                ((double) element.getResult()) *
                                        kernelFunction.get(distanceFunction.get(element.getPosition(), target) / neibe)).sum();
                double x2 = data
                        .parallelStream()
                        .mapToDouble(element ->
                                kernelFunction.get(distanceFunction.get(element.getPosition(), target) / neibe)).sum();
                if (x2 == 0) {
                    result = data.stream().mapToDouble(Person::getResult).sum() / data.size();
                } else {
                    result = x1 / x2;
                }
                person.setResult((int) Math.round(result));
            } else {
                @SuppressWarnings("OptionalGetWithoutIsPresent")
                int max = data.stream().mapToInt(Person::getResult).max().getAsInt() + 1;
                ArrayList<Double> x1 = new ArrayList<>();
                ArrayList<Double> x2 = new ArrayList<>();
                IntStream.range(0, max).forEach(element -> {
                    x1.add(0.0);
                    x2.add(0.0);
                });
                data.parallelStream().forEach(element -> {
                            x1.set(element.getResult(), x1.get(element.getResult()) + ((double) element.getResult()) *
                                    kernelFunction.get(distanceFunction.get(element.getPosition(), target) / neibe));
                            x2.set(element.getResult(), x2.get(element.getResult()) +
                                    kernelFunction.get(distanceFunction.get(element.getPosition(), target) / neibe));
                        }
                );
                int maxOnArrayPosition = 0;
                double maxOnArray = 0;
                for (int i = 0; i < max; i++) {
                    if (x2.get(i) > 0.00001) {
                        double divide = x1.get(i) / x2.get(i);
                        if (divide > maxOnArray) {
                            maxOnArray = divide;
                            maxOnArrayPosition = i;
                        }
                    }
                }
                person.setResult(maxOnArrayPosition);
            }
        }
    }
}
