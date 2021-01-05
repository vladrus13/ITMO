package ru.vladrus13.util;

import ru.vladrus13.bean.Bean;

public class Mathematics {
    public static double scalar(Bean a, Bean b) {
        double result = 0;
        for (int i = 0; i < a.getPosition().size(); i++) {
            result += a.getPosition().get(i) * b.getPosition().get(i);
        }
        return result;
    }

    public static double manhattan(Bean a, Bean b) {
        double result = 0;
        for (int i = 0; i < a.getPosition().size(); i++) {
            result += Math.pow(a.getPosition().get(i) - b.getPosition().get(i), 2);
        }
        return Math.sqrt(result);
    }

    public static double sqr(double x) {
        return x * x;
    }
}
