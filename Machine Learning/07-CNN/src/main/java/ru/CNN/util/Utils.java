package ru.CNN.util;

import ru.CNN.bean.Matrix;
import ru.CNN.bean.Tensor;
import ru.CNN.bean.Vector;

import java.util.ArrayList;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.IntStream;

public class Utils {
    public static ArrayList<Double> cloneA(ArrayList<Double> a) {
        ArrayList<Double> copy = new ArrayList<>();
        IntStream.range(0, a.size()).forEach(i -> copy.add(a.get(i)));
        return copy;
    }

    public static ArrayList<ArrayList<Double>> cloneAA(ArrayList<ArrayList<Double>> a) {
        ArrayList<ArrayList<Double>> copy = new ArrayList<>();
        IntStream.range(0, a.size()).forEach(i -> copy.add(cloneA(a.get(i))));
        return copy;
    }

    public static ArrayList<ArrayList<Double>> generate(int n, int m) {
        return generate(n, m, 0.0);
    }

    public static ArrayList<ArrayList<Double>> generate(int n, int m, double x) {
        ArrayList<ArrayList<Double>> returned = new ArrayList<>();
        IntStream.range(0, n).forEach(i -> {
            returned.add(new ArrayList<>());
            IntStream.range(0, m).forEach(j ->
                    returned.get(i).add(x));
        });
        return returned;
    }

    public static ArrayList<ArrayList<Integer>> generate(int n, int m, int x) {
        ArrayList<ArrayList<Integer>> returned = new ArrayList<>();
        IntStream.range(0, n).forEach(i -> {
            returned.add(new ArrayList<>());
            IntStream.range(0, m).forEach(j ->
                    returned.get(i).add(x));
        });
        return returned;
    }

    public static final Function<ArrayList<Double>, Double> average =
            array -> array.stream().mapToDouble(Double::doubleValue).sum() / array.size();

    public static final Function<ArrayList<Double>, Double> max =
            array -> array.stream().mapToDouble(Double::doubleValue).max().orElseThrow();

    public static final Function<ArrayList<Integer>, Integer> maxInt =
            array -> array.stream().mapToInt(Integer::intValue).max().orElseThrow();

    public static final Function<ArrayList<Double>, Integer> getPositionMax = array -> {
        double maxima = max.apply(array);
        return IntStream.range(0, array.size()).filter(i -> array.get(i) == maxima).findFirst().orElseThrow();
    };

    public static final String precision = ".10";
    public static final BinaryOperator<Double> divide = (a, b) -> a / b;
    public static final BinaryOperator<Double> second = (a, b) -> b;
    public static final BinaryOperator<Double> sum = Double::sum;
    public static final BinaryOperator<Vector> secondVector = (a, b) -> b;
    public static final BinaryOperator<Matrix> secondMatrix = (a, b) -> b;
    public static final BinaryOperator<Tensor> secondTensor = (a, b) -> b;
}
