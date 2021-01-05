package ru.CNN.bean;

import java.util.ArrayList;
import java.util.function.BinaryOperator;
import java.util.stream.IntStream;

public class Tensor {
    public ArrayList<Matrix> in;

    public Tensor(ArrayList<Matrix> in) {
        this.in = in;
    }

    public static Tensor generate(int k, int j, int i, double x) {
        ArrayList<Matrix> returned = new ArrayList<>();
        IntStream.range(0, k).forEach(l -> returned.add(Matrix.generate(j, i, x)));
        return new Tensor(returned);
    }

    public Tensor copy() {
        ArrayList<Matrix> copies = new ArrayList<>();
        IntStream.range(0, in.size()).forEach(i -> copies.add(in.get(i).copy()));
        return new Tensor(copies);
    }

    public Matrix get(int i) {
        return in.get(i);
    }

    public void apply(int i, BinaryOperator<Matrix> function, Matrix x) {
        in.set(i, function.apply(in.get(i), x));
    }
}
