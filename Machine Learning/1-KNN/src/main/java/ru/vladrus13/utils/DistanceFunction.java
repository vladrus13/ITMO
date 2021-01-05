package ru.vladrus13.utils;

import java.util.ArrayList;

/**
 * Distance classes
 */
public enum DistanceFunction {
    /**
     * Manhattan
     */
    MANHATTAN {
        @Override
        public double get(ArrayList<Double> a, ArrayList<Double> b) {
            double distance = 0;
            for (int i = 0; i < a.size(); i++) {
                distance += Math.abs(a.get(i) - b.get(i));
            }
            return distance;
        }
    },
    /**
     * Euclidean
     */
    EUCLIDEAN {
        @Override
        public double get(ArrayList<Double> a, ArrayList<Double> b) {
            double distance = 0;
            for (int i = 0; i < a.size(); i++) {
                distance += Math.pow(a.get(i) - b.get(i), 2);
            }
            return Math.sqrt(distance);
        }
    },
    /**
     * Chebyshev
     */
    CHEBYSHEV {
        @Override
        public double get(ArrayList<Double> a, ArrayList<Double> b) {
            double distance = 0;
            for (int i = 0; i < a.size(); i++) {
                distance = Math.max(distance, Math.abs(a.get(i) - b.get(i)));
            }
            return distance;
        }
    };

    /**
     * Get distance from ponts
     *
     * @param a first
     * @param b second
     * @return distance
     */
    public abstract double get(ArrayList<Double> a, ArrayList<Double> b);
}
