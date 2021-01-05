package ru.CNN.bean;

public class TensorSize extends Triple<Integer, Integer, Integer> {
    public TensorSize(Integer first, Integer second, Integer third) {
        super(first, second, third);
    }

    public TensorSize copy() {
        return new TensorSize(depth, height, width);
    }

    public int cube() {
        return depth * width * height;
    }
}