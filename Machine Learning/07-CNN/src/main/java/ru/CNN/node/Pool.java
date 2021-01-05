package ru.CNN.node;

import ru.CNN.bean.Tensor;
import ru.CNN.bean.TensorSize;

import static ru.CNN.util.Utils.sum;

public class Pool extends Node<Tensor, Tensor> {
    int s;
    Tensor copy;

    public Pool(int s, TensorSize tensorSize) {
        this.s = s;
        inputSize = tensorSize.copy();
        outputSize = new TensorSize(inputSize.depth, (inputSize.height - s) / s + 1, (inputSize.width - s) / s + 1);
    }

    @Override
    public Tensor makeStep(Tensor input) {
        Tensor value = Tensor.generate(outputSize.depth, outputSize.height, outputSize.width, -Double.MAX_VALUE);
        copy = input.copy();
        for (int i = 0; i < outputSize.depth; ++i) {
            for (int j = 0; j < outputSize.height; ++j) {
                for (int k = 0; k < outputSize.width; ++k) {
                    for (int dj = 0; dj < s; ++dj) {
                        for (int dk = 0; dk < s; ++dk) {
                            value.get(i).get(j).apply(k, Math::max, input.get(i).get(j * s + dj).get(k * s + dk));
                        }
                    }
                }
            }
        }
        return value;
    }

    @Override
    public Tensor makeBackpropagation(Tensor output) {
        Tensor res = Tensor.generate(inputSize.depth, inputSize.height, inputSize.width, 0);
        for (int i = 0; i < outputSize.depth; ++i) {
            for (int j = 0; j < outputSize.height; ++j) {
                for (int k = 0; k < outputSize.width; ++k) {
                    double cur_max = -Double.MAX_VALUE;
                    for (int di = 0; di < s; ++di) {
                        for (int dj = 0; dj < s; ++dj) {
                            cur_max = Math.max(cur_max, copy.get(i).get(j * s + di).get(k * s + dj));
                        }
                    }
                    for (int di = 0; di < s; ++di) {
                        for (int dj = 0; dj < s; ++dj) {
                            if (copy.get(i).get(j * s + di).get(k * s + dj) == cur_max) {
                                res.get(i).get(j * s + di).apply(k * s + dj, sum, output.get(i).get(j).get(k));
                            }
                        }
                    }
                }
            }
        }
        return res;
    }
}
