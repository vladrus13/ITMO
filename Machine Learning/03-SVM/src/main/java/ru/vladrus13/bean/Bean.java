package ru.vladrus13.bean;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class Bean {
    private final ArrayList<Double> position;

    // = 1 if N else -1
    private final int classification;

    public Bean(String[] line) {
        this(new ArrayList<>(List.of(Double.parseDouble(line[0]), Double.parseDouble(line[1]))), (line[2].equals("N") ? 1 : -1));
    }

    public Bean(ArrayList<Double> position, int classification) {
        this.position = position;
        this.classification = classification;
    }

    public ArrayList<Double> getPosition() {
        return position;
    }

    public int getClassification() {
        return classification;
    }

    @SuppressWarnings("MethodDoesntCallSuperMethod")
    public Bean clone() {
        ArrayList<Double> copy = new ArrayList<>();
        //noinspection UnnecessaryUnboxing
        IntStream.range(0, position.size()).forEach(element -> copy.add(position.get(element).doubleValue()));
        return new Bean(copy, classification);
    }
}
