package ru.CNN.util;

import ru.CNN.bean.Vector;

import java.util.ArrayList;

public class SoftMax {
    public static ArrayList<Double> softArgMax(Vector a) {
        double f = Utils.average.apply(a.in);
        ArrayList<Double> result = Utils.cloneA(a.in);
        double sum = 0.0;
        for (int i = 0; i < result.size(); i++) {
            double exp = Math.exp(result.get(i) - f);
            sum += exp;
            result.set(i, exp);
        }
        for (int i = 0; i < result.size(); i++) {
            result.set(i, result.get(i) / sum);
        }
        return result;
    }

    public static double crossEntropyLoss(Vector a, int label) {
        return -Math.log(softArgMax(a).get(label));
    }

    public static ArrayList<Double> crossEntropyDerivative(Vector a, int label) {
        ArrayList<Double> x = softArgMax(a);
        x.set(label, x.get(label) - 1);
        return x;
    }
}
