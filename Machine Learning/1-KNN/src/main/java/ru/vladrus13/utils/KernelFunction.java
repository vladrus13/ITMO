package ru.vladrus13.utils;

/**
 * Kernel function
 */
public enum KernelFunction {
    /**
     * Uniform
     */
    UNIFORM {
        @Override
        public double get(double distance) {
            return Math.abs(distance) < 1 ? (double) 1 / 2 : 0;
        }
    },
    /**
     * Triangular
     */
    TRIANGULAR {
        @Override
        public double get(double distance) {
            return Math.abs(distance) < 1 ? 1 - distance : 0;
        }
    },
    /**
     * Epanechnikov
     */
    EPANECHNIKOV {
        @Override
        public double get(double distance) {
            return Math.abs(distance) < 1 ? ((double) 3 / 4) * (1 - (Math.pow(distance, 2))) : 0;
        }
    },
    /**
     * Quartic
     */
    QUARTIC {
        @Override
        public double get(double distance) {
            return Math.abs(distance) < 1 ? ((double) 15 / 16) * Math.pow((1 - Math.pow(distance, 2)), 2) : 0;
        }
    },
    /**
     * Triweight
     */
    TRIWEIGHT {
        @Override
        public double get(double distance) {
            return Math.abs(distance) < 1 ? ((double) 35 / 32) * Math.pow((1 - Math.pow(distance, 2)), 3) : 0;
        }
    },
    /**
     * Trucube
     */
    TRICUBE {
        @Override
        public double get(double distance) {
            return Math.abs(distance) < 1 ? ((double) 70 / 81) * Math.pow((1 - Math.pow(distance, 3)), 3) : 0;
        }
    },
    /**
     * Gaussian
     */
    GAUSSIAN {
        @Override
        public double get(double distance) {
            return (1 / Math.sqrt(2 * Math.PI)) * Math.exp(((double) -1 / 2) * Math.pow(distance, 2));
        }
    },
    /**
     * Cosine
     */
    COSINE {
        @Override
        public double get(double distance) {
            return Math.abs(distance) < 1 ? (Math.PI / 4) * Math.cos(Math.PI * distance / 2) : 0;
        }
    },
    /**
     * Logistic
     */
    LOGISTIC {
        @Override
        public double get(double distance) {
            return 1 / (Math.exp(distance) + 2 + Math.exp(-distance));
        }
    },
    /**
     * Sigmoid
     */
    SIGMOID {
        @Override
        public double get(double distance) {
            return (2 / Math.PI) / (Math.exp(distance) + Math.exp(-distance));
        }
    };

    /**
     * Get kernel distance on normally distance
     *
     * @param distance normally distance in [0, 1]
     * @return kernel function
     */
    public abstract double get(double distance);
}
