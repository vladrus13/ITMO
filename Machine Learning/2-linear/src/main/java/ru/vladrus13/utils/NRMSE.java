package ru.vladrus13.utils;

import ru.vladrus13.bean.Bean;

import java.util.ArrayList;

public class NRMSE {
    public static double calculate(ArrayList<Bean> beans, ArrayList<Double> line) {
        double answer = 0;
        for (Bean bean : beans) {
            double sum = 0;
            for (int i = 0; i < line.size(); i++) {
                sum += Math.pow(bean.getAttributes().get(i) * line.get(i), 2);
            }
            sum /= line.size();
            answer += sum;
        }
        return Math.sqrt(answer / beans.size());
    }

    public static double calculateOne(ArrayList<Double> beans, Double line) {
        double answer = 0;
        for (Double bean : beans) {
            answer += Math.pow(bean * line, 2);
        }
        return Math.sqrt(answer / beans.size());
    }
}
