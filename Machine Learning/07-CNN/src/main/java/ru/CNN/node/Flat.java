package ru.CNN.node;

import ru.CNN.bean.Tensor;
import ru.CNN.bean.TensorSize;
import ru.CNN.bean.Vector;

import static ru.CNN.util.Utils.second;

public class Flat extends Node<Vector, Tensor> {
    public Flat(TensorSize tensorSize) {
        inputSize = tensorSize;
        outputSize = new TensorSize(1, 1, inputSize.cube());
    }

    @Override
    public Vector makeStep(Tensor in) {
        Vector returned = Vector.generate(outputSize.width, 0.0);
        for (int i = 0; i < inputSize.depth; i++) {
            for (int j = 0; j < inputSize.height; j++) {
                for (int k = 0; k < inputSize.width; k++) {
                    int coordinates = i * (inputSize.height * inputSize.width) + j * inputSize.width + k;
                    returned.apply(coordinates, second, in.get(i).get(j).get(k));
                }
            }
        }
        return returned;
    }

    @Override
    public Tensor makeBackpropagation(Vector out) {
        Tensor returned = Tensor.generate(inputSize.depth, inputSize.height, inputSize.width, 0.0);
        for (int i = 0; i < inputSize.depth; i++) {
            for (int j = 0; j < inputSize.height; j++) {
                for (int k = 0; k < inputSize.width; k++) {
                    int coordinates = i * (inputSize.height * inputSize.width) + j * inputSize.width + k;
                    returned.get(i).get(j).apply(k, second, out.get(coordinates));
                }
            }
        }
        return returned;
    }
}
