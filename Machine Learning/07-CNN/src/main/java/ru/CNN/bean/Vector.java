package ru.CNN.bean;

import java.util.ArrayList;
import java.util.function.BinaryOperator;
import java.util.stream.IntStream;

public class Vector {
    public ArrayList<Double> in;

    public Vector(ArrayList<Double> in) {
        this.in = in;
    }

    public static Vector generate(int i, double x) {
        ArrayList<Double> returned = new ArrayList<>();
        IntStream.range(0, i).forEach(j -> returned.add(x));
        return new Vector(returned);
    }

    public Double get(int i) {
        return in.get(i);
    }

    public void apply(int i, BinaryOperator<Double> function, Double x) {
        in.set(i, function.apply(in.get(i), x));
    }

    public Vector copy() {
        ArrayList<Double> copies = new ArrayList<>();
        IntStream.range(0, in.size()).forEach(e -> copies.add(in.get(e)));
        return new Vector(copies);
    }
}
