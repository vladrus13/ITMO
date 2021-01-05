package ru.CNN.node;

import ru.CNN.bean.Matrix;
import ru.CNN.bean.TensorSize;

import static ru.CNN.util.Utils.second;
import static ru.CNN.util.Utils.sum;

public class Cnvc extends Cnv {

    public Cnvc(int cnt, int n, int m, int p, int s, TensorSize inputSize) {
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
            for (int j = p - 1; j >= 0; --j) {
                returned.get(i).apply(j, second, returned.get(i).get(j + matrixLayer.get(0).in.size()));
            }
            for (int j = matrixLayer.get(0).in.size() + p; j < matrixLayer.get(0).in.size() + 2 * p; ++j) {
                returned.get(i).apply(j, second, returned.get(i).get(j - matrixLayer.get(0).in.size()));
            }
        }
        for (int i = p - 1; i >= 0; --i) {
            for (int j = 0; j < matrixLayer.get(0).in.size() + 2 * p; ++j) {
                returned.get(i).apply(j, second, returned.get(i + matrixLayer.in.size()).get(j));
            }
        }
        for (int i = matrixLayer.in.size() + p; i < matrixLayer.in.size() + 2 * p; ++i) {
            for (int j = 0; j < matrixLayer.get(0).in.size() + 2 * p; ++j) {
                returned.get(i).apply(j, second, returned.get(i - matrixLayer.in.size()).get(j));
            }
        }
        return returned;
    }

    @Override
    public void makeCompress(Matrix matrixLayer) {
        int n = matrixLayer.in.size() - 2 * p;
        int m = matrixLayer.get(0).in.size() - 2 * p;
        for (int i = p - 1; i >= 0; --i) {
            for (int j = 0; j < m + 2 * p; ++j) {
                matrixLayer.get(i + n).apply(j, sum, matrixLayer.get(i).get(j));
            }
        }
        for (int i = n + p; i < n + 2 * p; ++i) {
            for (int j = 0; j < m + 2 * p; ++j) {
                matrixLayer.get(i - n).apply(j, sum, matrixLayer.get(i).get(j));
            }
        }

        for (int i = p; i < p + n; ++i) {
            for (int j = p - 1; j >= 0; --j) {
                matrixLayer.get(i).apply(j + m, sum, matrixLayer.get(i).get(j));
            }
            for (int j = m + p; j < m + 2 * p; ++j) {
                matrixLayer.get(i).apply(j - m, sum, matrixLayer.get(i).get(j));
            }
        }
    }
}
