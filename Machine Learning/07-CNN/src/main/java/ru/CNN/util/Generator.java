package ru.CNN.util;

import ru.CNN.bean.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Generator {
    private static final Random random = new Random();

    /**
     * Generate random double under [-1, 1]
     * @return random
     */
    private static Double getDouble() {
        return random.nextDouble() * 2 - 1;
    }

    public static final Function<Integer, Vector> getVector =
            count -> new Vector(IntStream.range(0, count)
                    .mapToDouble(i -> getDouble()).boxed()
                    .collect(Collectors.toCollection(ArrayList::new)));

    public static final Function<Pair<Integer, Integer>, Matrix> getMatrix =
            pair -> new Matrix(IntStream.range(0, pair.first)
                    .mapToObj(i -> getVector.apply(pair.second))
                    .collect(Collectors.toCollection(ArrayList::new)));

    public static final Function<Triple<Integer, Integer, Integer>, Tensor> getTensor =
            triple -> new Tensor(IntStream.range(0, triple.depth)
                    .mapToObj(i -> getMatrix.apply(new Pair<>(triple.height, triple.width)))
                    .collect(Collectors.toCollection(ArrayList::new)));
}
