package ru.CNN.node;

import ru.CNN.bean.Matrix;
import ru.CNN.bean.TensorSize;

import static ru.CNN.util.Utils.second;
import static ru.CNN.util.Utils.sum;

public class Cnve extends Cnv {

    public Cnve(int cnt, int n, int m, int p, int s, TensorSize inputSize) {
        super(cnt, n, m, p, s, inputSize);
    }

    @Override
    public Matrix getPaddedLayer(Matrix matrixLayer) {
        Matrix returned = Matrix.generate(matrixLayer.in.size() + 2 * p, matrixLayer.get(0).in.size() + 2 * p, 0);
        for (int i = 0; i < matrixLayer.in.size(); ++i) {
            for (int j = 0; j < matrixLayer.get(0).in.size(); ++j) {
                returned.get(i + p).apply(j + p, second, matrixLayer.get(i).get(j));
            }
        }
        for (int i = p; i < p + matrixLayer.in.size(); ++i) {
            for (int j = 0; j < p; ++j) {
                returned.get(i).apply(j, second, matrixLayer.get(i - p).get(0));
            }
            for (int j = matrixLayer.get(0).in.size() + p; j < matrixLayer.get(0).in.size() + 2 * p; ++j) {
                returned.get(i).apply(j, second, matrixLayer.get(i - p).get(matrixLayer.get(i - p).in.size() - 1));
            }
        }
        for (int i = 0; i < p; ++i) {
            for (int j = 0; j < matrixLayer.get(0).in.size() + 2 * p; ++j) {
                returned.get(i).apply(j, second, returned.get(p).get(j));
            }
        }
        for (int i = matrixLayer.in.size() + p; i < matrixLayer.in.size() + 2 * p; ++i) {
            for (int j = 0; j < matrixLayer.get(0).in.size() + 2 * p; ++j) {
                returned.get(i).apply(j, second, returned.get(matrixLayer.in.size() + p - 1).get(j));
            }
        }
        return returned;
    }

    @Override
    public void makeCompress(Matrix matrixLayer) {
        int n = matrixLayer.in.size() - 2 * p;
        int m = matrixLayer.get(0).in.size() - 2 * p;

        for (int i = 0; i < p; ++i) {
            for (int j = 0; j < m + 2 * p; ++j) {
                matrixLayer.get(p).apply(j, sum, matrixLayer.get(i).get(j));
            }
        }
        for (int i = n + p; i < n + 2 * p; ++i) {
            for (int j = 0; j < m + 2 * p; ++j) {
                matrixLayer.get(n + p - 1).apply(j, sum, matrixLayer.get(i).get(j));
            }
        }

        for (int i = p; i < p + n; ++i) {
            for (int j = 0; j < p; ++j) {
                matrixLayer.get(i).apply(p, sum, matrixLayer.get(i).get(j));
            }
            for (int j = m + p; j < m + 2 * p; ++j) {
                matrixLayer.get(i).apply(m + p - 1, sum, matrixLayer.get(i).get(j));
            }
        }
    }
}
