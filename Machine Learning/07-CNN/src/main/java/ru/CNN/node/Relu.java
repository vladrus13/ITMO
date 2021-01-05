package ru.CNN.node;

import ru.CNN.bean.Matrix;
import ru.CNN.bean.Tensor;
import ru.CNN.bean.TensorSize;
import ru.CNN.bean.Vector;

import static ru.CNN.util.Utils.divide;
import static ru.CNN.util.Utils.sum;

public class Relu extends Node<Tensor, Tensor> {
    double invAlpha;
    Tensor copy;

    public Relu(double invAlpha, TensorSize tensorSize) {
        this.invAlpha = invAlpha;
        inputSize = tensorSize.copy();
        outputSize = tensorSize.copy();
    }

    @Override
    public Tensor makeStep(Tensor input) {
        Tensor value = input.copy();
        copy = input.copy();
        for (Matrix i : value.in) {
            for (Vector j : i.in) {
                for (int k = 0; k < j.in.size(); k++) {
                    if (j.in.get(k) < 0) {
                        j.apply(k, divide, invAlpha);
                    }
                }
            }
        }
        return value;
    }

    @Override
    public Tensor makeBackpropagation(Tensor output) {
        Tensor returned = Tensor.generate(inputSize.depth, inputSize.height, inputSize.width, 0);
        for (int i = 0; i < outputSize.depth; ++i) {
            for (int j = 0; j < outputSize.height; ++j) {
                for (int k = 0; k < outputSize.width; ++k) {
                    Double current = copy.get(i).get(j).get(k);
                    double koff;
                    if (current >= 0) {
                        koff = 1.0;
                    } else {
                        koff = 1.0 / invAlpha;
                    }
                    returned.get(i).get(j).apply(k, sum, koff * output.get(i).get(j).get(k));
                }
            }
        }
        return returned;
    }
}

