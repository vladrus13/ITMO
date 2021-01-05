package ru.vladrus13.kernel;

import ru.vladrus13.bean.Bean;
import ru.vladrus13.util.Mathematics;

public enum KernelFunction {
    LINEAR {
        @Override
        public double calculate(Bean a, Bean b, double gamma, double k) {
            return Mathematics.scalar(a, b);
        }
    },
    SIGMOID {
        @Override
        public double calculate(Bean a, Bean b, double gamma, double k) {
            return Math.tanh(gamma * Mathematics.scalar(a, b) + k);
        }
    },
    RBF {
        @Override
        public double calculate(Bean a, Bean b, double gamma, double k) {
            return Math.exp(-gamma * Math.pow(Mathematics.manhattan(a, b), 2));
        }
    },
    POLY {
        @Override
        public double calculate(Bean a, Bean b, double gamma, double k) {
            return Math.pow(gamma * Mathematics.scalar(a, b) + k, 3);
        }
    };

    public abstract double calculate(Bean a, Bean b, double gamma, double k);
}
