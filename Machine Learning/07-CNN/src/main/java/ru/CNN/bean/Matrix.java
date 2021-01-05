package ru.CNN.bean;

import java.util.ArrayList;
import java.util.function.BinaryOperator;
import java.util.stream.IntStream;

public class Matrix {
    public ArrayList<Vector> in;

    public Matrix(ArrayList<Vector> in) {
        this.in = in;
    }

    public static Matrix generate(int j, int i, double x) {
        ArrayList<Vector> returned = new ArrayList<>();
        IntStream.range(0, j).forEach(k -> returned.add(Vector.generate(i, x)));
        return new Matrix(returned);
    }

    public Vector get(int i) {
        return in.get(i);
    }

    public Matrix copy() {
        ArrayList<Vector> copies = new ArrayList<>();
        IntStream.range(0, in.size()).forEach(i -> copies.add(in.get(i).copy()));
        return new Matrix(copies);
    }

    public void apply(int i, BinaryOperator<Vector> function, Vector x) {
        in.set(i, function.apply(in.get(i), x));
    }
}
