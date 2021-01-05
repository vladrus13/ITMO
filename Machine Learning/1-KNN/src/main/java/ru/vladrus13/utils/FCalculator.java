package ru.vladrus13.utils;

import java.util.ArrayList;

/**
 * Calculate F
 */
public class FCalculator {
    /**
     * Matrix
     */
    private final ArrayList<ArrayList<Integer>> matrix;
    /**
     * Get macro F
     */
    double macro_f;
    /**
     * Get micro F
     */
    final double micro_f;

    /**
     * @param index index of getting line
     * @return sum of line
     */
    private int getLine(int index) {
        return matrix.get(index).stream().mapToInt(Integer::intValue).sum();
    }

    /**
     * @param index index of result
     * @return reslut
     */
    private Result getResult(int index) {
        int normal = matrix.get(index).get(index);
        int tpFp = matrix.stream().mapToInt(integers -> integers.get(index)).sum();
        int tpFn = getLine(index);
        Result returned = new Result();
        returned.first = tpFp == 0 ? 0 : (double) normal / tpFp;
        returned.second = tpFn == 0 ? 0 : (double) normal / tpFn;
        return returned;
    }

    /**
     * @param result result
     * @return F
     */
    private double getF(Result result) {
        return result.second + result.first == 0 ? 0 : 2 * result.first * result.second / (result.first + result.second);
    }

    /**
     * @param index index of getting F
     * @return F
     */
    private double getFReal(int index) {
        return getF(getResult(index));
    }

    /**
     * @param matrix matrix
     */
    public FCalculator(ArrayList<ArrayList<Integer>> matrix) {
        this.matrix = matrix;
        int total_sum = 0;
        for (ArrayList<Integer> i : matrix) {
            for (Integer j : i) {
                total_sum += j;
            }
        }
        macro_f = 0;
        for (int i = 0; i < matrix.size(); i++) {
            macro_f += getFReal(i) * getLine(i);
        }
        macro_f /= total_sum;
        double micro_pre = 0;
        for (int i = 0; i < matrix.size(); i++) {
            micro_pre += getResult(i).first * getLine(i);
        }
        micro_pre /= total_sum;
        double micro_recall = 0;
        for (int i = 0; i < matrix.size(); i++) {
            micro_recall += getResult(i).second * getLine(i);
        }
        micro_recall /= total_sum;
        micro_f = getF(new Result(micro_pre, micro_recall));
    }

    /**
     * @return result of F
     */
    public Result returnMicro() {
        return new Result(micro_f, macro_f);
    }

    /**
     * Result class
     */
    public static class Result {
        /**
         * First element
         */
        public double first;
        /**
         * Second element
         */
        public double second;

        /**
         * @param first  first
         * @param second second
         */
        public Result(double first, double second) {
            this.first = first;
            this.second = second;
        }

        /**
         * Empty constructor
         */
        public Result() {
        }
    }
}
