package ru.CNN.node;

import ru.CNN.bean.Matrix;
import ru.CNN.bean.Pair;
import ru.CNN.bean.TensorSize;
import ru.CNN.bean.Vector;
import ru.CNN.util.Generator;
import ru.CNN.util.NetworkUtils;

import static ru.CNN.util.Utils.sum;

public class Ender extends Node<Vector, Vector> {

    Matrix w;
    Matrix dw;

    Matrix mo;
    Matrix adapt;
    Vector copy;

    public Ender(int count, TensorSize tensorSize) {
        inputSize = tensorSize;
        outputSize = new TensorSize(1, 1, count);
        w = Generator.getMatrix.apply(new Pair<>(count, inputSize.width));
        mo = Matrix.generate(count, inputSize.width, 0);
        adapt = mo.copy();
        dw = mo.copy();
    }

    @Override
    public Vector makeStep(Vector in) {
        copy = in.copy();
        Vector values = Vector.generate(outputSize.width, 0);
        for (int i = 0; i < outputSize.width; i++) {
            for (int j = 0; j < outputSize.width; j++) {
                values.apply(i, sum, in.get(j) * w.get(i).get(j));
            }
        }
        dw = Matrix.generate(outputSize.width, inputSize.width, 0);
        return values;
    }

    @Override
    public Vector makeBackpropagation(Vector out) {
        Vector result = Vector.generate(inputSize.width, 0);
        for (int i = 0; i < outputSize.width; i++) {
            for (int j = 0; j < inputSize.width; j++) {
                result.apply(j, sum, out.get(i) * w.get(i).get(j));
                dw.get(i).apply(j, sum, out.get(i) * copy.get(j));
            }
        }
        return result;
    }

    public void updateGradient(int position, double m) {
        NetworkUtils.updateParams(mo, adapt, dw, w, position, m);
    }
}
