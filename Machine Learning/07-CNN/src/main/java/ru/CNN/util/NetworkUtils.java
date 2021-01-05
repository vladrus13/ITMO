package ru.CNN.util;

import ru.CNN.bean.Matrix;
import ru.CNN.bean.Vector;

import static ru.CNN.util.Utils.*;

public class NetworkUtils {
    public static final double B1 = 0.9;
    public static final double B2 = 0.999;
    public static final double EPS = 1e-6;

    public static Vector updateMoments(Vector moments, Vector grad, int t) {
        double DIVISOR = 1.0 - Math.pow(B1, t);
        Vector hats = Vector.generate(moments.in.size(), 0.0);
        for (int i = 0; i < moments.in.size(); ++i) {
            moments.apply(i, second, B1 * moments.get(i) + (1 - B1) * grad.get(i));
            hats.apply(i, second, moments.get(i) / DIVISOR);
        }
        return hats;
    }

    public static Vector updateAdaptive(Vector adaptive, Vector grad, int t) {
        double DIVISOR = 1 - Math.pow(B2, t);
        Vector hats = Vector.generate(adaptive.in.size(), 0.0);
        for (int i = 0; i < adaptive.in.size(); ++i) {
            adaptive.apply(i, second, B2 * adaptive.get(i) + (1 - B2) * grad.get(i) * grad.get(i));
            hats.apply(i, second, adaptive.get(i) / DIVISOR);
        }
        return hats;
    }

    public static void updateParams(Vector moments, Vector adaptive, Vector grad, Vector params, int t, double mu) {
        Vector hat_moments = updateMoments(moments, grad, t);
        Vector hat_adaptive = updateAdaptive(adaptive, grad, t);
        for (int i = 0; i < grad.in.size(); ++i) {
            double diff = mu * hat_moments.get(i) / (Math.sqrt(hat_adaptive.get(i)) + EPS);
            params.apply(i, sum, -diff);
        }
    }

    public static void updateParams(Matrix moments, Matrix adaptive, Matrix grad, Matrix params, int t, double mu) {
        for (int i = 0; i < grad.in.size(); ++i) {
            updateParams(moments.get(i), adaptive.get(i), grad.get(i), params.get(i), t, mu);
        }
    }
}
