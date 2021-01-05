package ru.CNN.node;

import ru.CNN.bean.*;
import ru.CNN.util.Generator;

import java.util.ArrayList;

import static ru.CNN.util.Utils.sum;

public abstract class Cnv extends Node<Tensor, Tensor> {
    ArrayList<Tensor> kernels;
    ArrayList<Tensor> dkernels;
    ArrayList<Tensor> momentum;
    ArrayList<Tensor> adaptive;
    int p;
    int s;
    Tensor paddedInput;

    public Cnv(int cnt, int n, int m, int p, int s, TensorSize inputSize) {
        kernels = new ArrayList<>();
        for (int i = 0; i < cnt; i++) {
            kernels.add(Generator.getTensor.apply(new Triple<>(inputSize.depth, n, m)));
        }
        this.p = p;
        this.s = s;
        this.inputSize = inputSize.copy();
        outputSize = new TensorSize(cnt, (this.inputSize.height + 2 * p - n) / s + 1, (this.inputSize.width + 2 * p - m) / s + 1);
        momentum = new ArrayList<>();
        for (int i = 0; i < cnt; i++) {
            momentum.add(Tensor.generate(inputSize.depth, n, m, 0));
        }
        adaptive = new ArrayList<>();
        for (int i = 0; i < cnt; i++) {
            adaptive.add(momentum.get(i).copy());
        }
    }

    public abstract Matrix getPaddedLayer(Matrix matrixLayer);

    public abstract void makeCompress(Matrix matrixLayer);

    @Override
    public Tensor makeStep(Tensor input) {
        int d = inputSize.depth;
        int layer1 = kernels.get(0).get(0).in.size();
        int layer2 = kernels.get(0).get(0).get(0).in.size();
        paddedInput = new Tensor(new ArrayList<>());
        for (int i = 0; i < d; ++i) {
            paddedInput.in.add(getPaddedLayer(input.get(i)));
        }
        Tensor returned = Tensor.generate(outputSize.depth, outputSize.height, outputSize.width, 0);
        for (int i = 0; i < outputSize.depth; ++i) {
            for (int cur_d = 0; cur_d < d; ++cur_d) {
                for (int j = 0; j < outputSize.height; ++j) {
                    for (int k = 0; k < outputSize.width; ++k) {
                        for (int di = 0; di < layer1; ++di) {
                            for (int dj = 0; dj < layer2; ++dj) {
                                returned.get(i).get(j).apply(k, sum, paddedInput.get(cur_d).get(j * s + di).get(k * s + dj) * kernels.get(i).get(cur_d).get(di).get(dj));
                            }
                        }
                    }
                }
            }
        }
        dkernels = new ArrayList<>();
        for (int i = 0; i < kernels.size(); i++) {
            dkernels.add(Tensor.generate(d, layer1, layer2, 0));
        }
        return returned;
    }

    @Override
    public Tensor makeBackpropagation(Tensor outputDiff) {
        Tensor paddedInputDiff = getPaddedBackpropagation(outputDiff);
        int d = inputSize.depth;
        int n = inputSize.height;
        int m = inputSize.width;
        Tensor returned = Tensor.generate(d, n, m, 0);
        for (int i = 0; i < d; ++i) {
            makeCompress(paddedInputDiff.get(i));
            for (int j = 0; j < n; ++j) {
                for (int k = 0; k < m; ++k) {
                    returned.get(i).get(j).apply(k, sum, paddedInputDiff.get(i).get(j + p).get(k + p));
                }
            }
        }
        return returned;
    }

    public Tensor getPaddedBackpropagation(Tensor outDiff) {
        int d = inputSize.depth;
        int n = inputSize.height;
        int m = inputSize.width;
        int layer1 = kernels.get(0).get(0).in.size();
        int layer2 = kernels.get(0).get(0).get(0).in.size();
        Tensor paddedInputDiff = Tensor.generate(d, n + 2 * p, m + 2 * p, 0);
        for (int i = 0; i < outputSize.depth; ++i) {
            for (int current = 0; current < d; ++current) {
                for (int j = 0; j < outputSize.height; ++j) {
                    for (int k = 0; k < outputSize.width; ++k) {
                        for (int di = 0; di < layer1; ++di) {
                            for (int dj = 0; dj < layer2; ++dj) {
                                paddedInputDiff.get(current).get(j * s + di).apply(k * s + dj, sum,
                                        kernels.get(i).get(current).get(di).get(dj) * outDiff.get(i).get(j).get(k));
                                dkernels.get(i).get(current).get(di).apply(dj, sum,
                                        paddedInput.get(current).get(j * s + di).get(k * s + dj) * outDiff.get(i).get(j).get(k));
                            }
                        }
                    }
                }
            }
        }
        return paddedInputDiff;
    }
}