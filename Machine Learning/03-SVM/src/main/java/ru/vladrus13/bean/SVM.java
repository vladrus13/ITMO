package ru.vladrus13.bean;

import ru.vladrus13.kernel.KernelClass;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

public class SVM {

    private final ArrayList<ArrayList<Double>> matrix;
    private final ArrayList<ArrayList<Double>> pre;
    private final ArrayList<Integer> ys;
    private final double C;
    private final ArrayList<Double> lambda = new ArrayList<>();
    private double B;
    private final KernelClass kernelClass;

    public ArrayList<ArrayList<Double>> kernelMatrix(ArrayList<ArrayList<Double>> a, ArrayList<ArrayList<Double>> b) {
        ArrayList<ArrayList<Double>> result = new ArrayList<>();
        for (ArrayList<Double> ae : a) {
            ArrayList<Double> temp = new ArrayList<>();
            for (ArrayList<Double> be : b) {
                temp.add(kernelClass.calculate(ae, be));
            }
            result.add(temp);
        }
        return result;
    }

    public SVM(ArrayList<ArrayList<Double>> pre, ArrayList<Integer> ys, KernelClass kernelClass) {
        this.pre = pre;
        this.ys = ys;
        this.C = kernelClass.getC();
        this.kernelClass = kernelClass;
        matrix = kernelMatrix(pre, pre);
    }

    private double wCalculate(int position) {
        double answer = B;
        for (int i = 0; i < ys.size(); i++) {
            answer += matrix.get(position).get(i) * ys.get(i) * lambda.get(i);
        }
        return answer;
    }

    public double predict(ArrayList<Double> point) {
        double result = 0;
        ArrayList<ArrayList<Double>> single = new ArrayList<>(List.of(point));
        ArrayList<ArrayList<Double>> smallMatrix = kernelMatrix(single, pre);
        for (int i = 0; i < matrix.size(); i++) {
            result += lambda.get(i) * ys.get(i) * smallMatrix.get(0).get(i);
        }
        return Math.signum(result - B);
    }

    public void calculateAnswer() {
        B = 0;
        ArrayList<Integer> pairs = new ArrayList<>();
        IntStream.range(0, ys.size()).forEach(element -> {
            lambda.add(0.0);
            pairs.add(element);
        });
        for (int iii = 0; iii < 5000; iii++) {
            Collections.shuffle(pairs);
            for (int i = 0; i < ys.size(); i++) {
                double ei = wCalculate(i) - ys.get(i);
                double eps = 1e-6;
                if ((ys.get(i) * ei < -eps && lambda.get(i) < C) || (ys.get(i) * ei > eps && lambda.get(i) > 0)) {
                    int swapper = pairs.get(i);
                    if (i == swapper) continue;
                    double eswapper = wCalculate(swapper) - ys.get(swapper);
                    if (Math.abs(ei - eswapper) < eps) continue;
                    double L, H;
                    if (!ys.get(i).equals(ys.get(swapper))) {
                        L = Math.max(0.0, lambda.get(swapper) - lambda.get(i));
                        H = Math.min(C, C + lambda.get(swapper) - lambda.get(i));
                    } else {
                        L = Math.max(0.0, lambda.get(swapper) + lambda.get(i) - C);
                        H = Math.min(C, lambda.get(swapper) + lambda.get(i));
                    }
                    if (Math.abs(L - H) < eps) continue;
                    double drob = 2 * matrix.get(i).get(swapper) - matrix.get(i).get(i) - matrix.get(swapper).get(swapper);
                    if (drob > eps) continue;
                    double aswapperOld = lambda.get(swapper);
                    double aswapper = Math.max(L, Math.min(H, aswapperOld - ys.get(swapper) * (ei - eswapper) / drob));
                    if (Math.abs(aswapper - aswapperOld) < eps) continue;
                    double aiOld = lambda.get(i);
                    double ai = aiOld + ys.get(i) * ys.get(swapper) * (aswapperOld - aswapper);
                    double k1 = B - ei - ys.get(i) * (ai - aiOld) * matrix.get(i).get(i) - ys.get(swapper) * (aswapper - aswapperOld) * matrix.get(i).get(swapper);
                    double k2 = B - eswapper - ys.get(i) * (ai - aiOld) * matrix.get(i).get(swapper) - ys.get(swapper) * (aswapper - aswapperOld) * matrix.get(swapper).get(swapper);
                    if (aiOld > 0 && aiOld < C) {
                        B = k1;
                    } else {
                        if (aswapperOld > 0 && aswapperOld < C) {
                            B = k2;
                        } else {
                            B = (k1 + k2) / 2;
                        }
                    }
                    lambda.set(i, ai);
                    lambda.set(swapper, aswapper);
                }
            }
        }
    }

    public ArrayList<Double> getLambda() {
        return lambda;
    }

    public double getB() {
        return B;
    }
}
