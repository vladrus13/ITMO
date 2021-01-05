public enum KernelFunction {
    UNIFORM {
        @Override
        public double get(double distance) {
            return distance < 1 ? (double) 1 / 2 : 0;
        }
    }, TRIANGULAR {
        @Override
        public double get(double distance) {
            return distance < 1 ? 1 - distance : 0;
        }
    }, EPANECHNIKOV {
        @Override
        public double get(double distance) {
            return distance < 1 ? ((double) 3 / 4) * (1 - (Math.pow(distance, 2))) : 0;
        }
    }, QUARTIC {
        @Override
        public double get(double distance) {
            return distance < 1 ? ((double) 15 / 16) * Math.pow((1 - Math.pow(distance, 2)), 2) : 0;
        }
    }, TRIWEIGHT {
        @Override
        public double get(double distance) {
            return distance < 1 ? ((double) 35 / 32) * Math.pow((1 - Math.pow(distance, 2)), 3) : 0;
        }
    }, TRICUBE {
        @Override
        public double get(double distance) {
            return distance < 1 ? ((double) 70 / 81) * Math.pow((1 - Math.pow(distance, 3)), 3) : 0;
        }
    }, GAUSSIAN {
        @Override
        public double get(double distance) {
            return (1 / Math.sqrt(2 * Math.PI)) * Math.exp(((double) -1 / 2) * Math.pow(distance, 2));
        }
    }, COSINE {
        @Override
        public double get(double distance) {
            return distance < 1 ? (Math.PI / 4) * Math.cos(Math.PI * distance / 2) : 0;
        }
    }, LOGISTIC {
        @Override
        public double get(double distance) {
            return 1 / (Math.exp(distance) + 2 + Math.exp(-distance));
        }
    }, SIGMOID {
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
