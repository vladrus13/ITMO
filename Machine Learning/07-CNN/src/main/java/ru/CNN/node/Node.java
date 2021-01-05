package ru.CNN.node;

import ru.CNN.bean.Tensor;
import ru.CNN.bean.TensorSize;

public abstract class Node<A, B> {
    protected TensorSize inputSize;
    public TensorSize outputSize;
    protected int steps;

    public Node() {
        steps = 1;
    }

    public abstract A makeStep(B in);

    public abstract B makeBackpropagation(A out);

    B getBackpropagation(A out) {
        return makeBackpropagation(out);
    }
}