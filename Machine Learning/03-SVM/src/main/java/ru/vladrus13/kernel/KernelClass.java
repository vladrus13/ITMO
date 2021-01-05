package ru.vladrus13.kernel;

import ru.vladrus13.bean.Bean;

import java.util.ArrayList;
import java.util.List;

public class KernelClass {
    private final KernelFunction kernelFunction;
    private final Double gamma;
    private final Double k;
    private final double C;

    public KernelClass(KernelFunction kernelFunction, Double gamma, Double k, double C) {
        this.kernelFunction = kernelFunction;
        this.gamma = gamma;
        this.k = k;
        this.C = C;
    }

    public double calculate(ArrayList<Double> a, ArrayList<Double> b) {
        return kernelFunction.calculate(new Bean(a, 0), new Bean(b, 0), gamma, k);
    }

    public static ArrayList<KernelClass> generate() {
        ArrayList<KernelClass> result = new ArrayList<>();
        for (double C : List.of(0.1, 1.0, 5.0, 10.0, 15.0, 50.0, 100.0, 1000.0)) {
            result.add(new KernelClass(KernelFunction.LINEAR, 0.0, 0.0, C));
            for (Double gamma : List.of(0.1, 0.2, 0.3)) {
                result.add(new KernelClass(KernelFunction.RBF, gamma, 0.0, C));
            }
            for (Double gamma : List.of(0.25, 0.5, 0.75, 1.0)) {
                for (Double k : List.of(0.0, 0.1, 0.25, 0.5, 0.75, 1.0)) {
                    result.add(new KernelClass(KernelFunction.SIGMOID, gamma, k, C));
                }
            }
            for (Double gamma : List.of(0.15, 0.5, 0.75, 1.0)) {
                for (Double k : List.of(0.0, 0.1, 0.25, 0.5, 0.75, 1.0)) {
                    result.add(new KernelClass(KernelFunction.POLY, gamma, k, C));
                }
            }
        }
        return result;
    }

    public String toString() {
        return "Kernel class: " + kernelFunction.name() + ", gamma = " + gamma + ", k = " + k + ", C = " + C;
    }
    public String toFileString() {
        return kernelFunction.name() + "-" + gamma + "-" + k + "-" + C;
    }

    public double getC() {
        return C;
    }
}
