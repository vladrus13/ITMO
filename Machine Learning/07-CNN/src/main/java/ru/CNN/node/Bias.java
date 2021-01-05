package ru.CNN.node;

import ru.CNN.bean.Tensor;
import ru.CNN.bean.TensorSize;
import ru.CNN.bean.Vector;
import ru.CNN.util.Generator;

import static ru.CNN.util.Utils.second;
import static ru.CNN.util.Utils.sum;

public class Bias extends Node<Tensor, Tensor> {

    Vector bias;
    Vector dbias;

    Vector momentum;
    Vector adaptive;

    public Bias(TensorSize tensorSize) {
        bias = Generator.getVector.apply(tensorSize.depth);
        inputSize = tensorSize.copy();
        outputSize = tensorSize.copy();
        momentum = Vector.generate(bias.in.size(), 0);
        adaptive = Vector.generate(bias.in.size(), 0);
    }

    @Override
    public Tensor makeStep(Tensor input) {
        Tensor value = input.copy();
        for (int i = 0; i < inputSize.depth; ++i) {
            for (Vector j : value.get(i).in) {
                for (int k = 0; k < j.in.size(); k++) {
                    j.apply(k, sum, bias.get(i));
                }
            }
        }
        return value;
    }

    @Override
    public Tensor makeBackpropagation(Tensor output) {
        Tensor returned = Tensor.generate(inputSize.depth, inputSize.height, inputSize.width, 0);
        dbias = Vector.generate(bias.in.size(), 0);
        for (int i = 0; i < outputSize.depth; ++i) {
            for (int j = 0; j < outputSize.height; ++j) {
                for (int k = 0; k < outputSize.width; ++k) {
                    returned.get(i).get(j).apply(k, sum, output.get(i).get(j).get(k));
                    dbias.apply(i, sum, output.get(i).get(j).get(k));
                }
            }
        }
        return returned;
    }
}
