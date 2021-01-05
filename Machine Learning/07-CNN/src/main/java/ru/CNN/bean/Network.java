package ru.CNN.bean;

import ru.CNN.node.*;
import ru.CNN.util.NetworkUtils;
import ru.CNN.util.SoftMax;
import ru.CNN.util.Utils;

import java.util.ArrayList;

public class Network {
    public TensorSize size;
    public ArrayList<Node<Tensor, Tensor>> nodes;
    public Flat toFlat;
    public Ender ender;

    public Network(TensorSize size) {
        this.size = size;
        this.nodes = new ArrayList<>();
    }

    public TensorSize getSize() {
        return nodes.isEmpty() ? size : nodes.get(nodes.size() - 1).outputSize;
    }

    public void addBias() {
        nodes.add(new Bias(getSize()));
    }

    public void addRelu(int invAlpha) {
        nodes.add(new Relu(invAlpha, getSize()));
    }

    public void addPool(int s) {
        nodes.add(new Pool(s, getSize()));
    }

    public void addCnv(int cnt, int n, int m, int p, int s, CnvType type) {
        switch (type) {
            case E:
                nodes.add(new Cnve(cnt, n, m, p, s, getSize()));
                break;
            case M:
                nodes.add(new Cnvm(cnt, n, m, p, s, getSize()));
                break;
            case C:
                nodes.add(new Cnvc(cnt, n, m, p, s, getSize()));
                break;
        }
    }

    public void addFlat() {
        toFlat = new Flat(getSize());
    }

    public void addEnder(int labelsCount) {
        ender = new Ender(labelsCount, toFlat.outputSize);
    }

    public void end(int labelsCount) {
        addFlat();
        addEnder(labelsCount);
    }

    public double addImage(Tensor image, int label) {
        Tensor copy = image.copy();
        for (Node<Tensor, Tensor> node : nodes) {
            copy = node.makeStep(copy);
        }
        Vector value = toFlat.makeStep(copy);
        value = ender.makeStep(value);
        Vector result = new Vector(SoftMax.crossEntropyDerivative(value, label));
        result = ender.makeBackpropagation(result);
        Tensor tensorResult = toFlat.makeBackpropagation(result);
        for (int i = nodes.size() - 1; i >= 0; i--) {
            tensorResult = nodes.get(i).makeBackpropagation(tensorResult);
        }
        return SoftMax.crossEntropyLoss(value, label);
    }

    public void addImages(ArrayList<Tensor> images, ArrayList<Integer> labels) {
        for (int i = 0; i < images.size(); i++) {
            addImage(images.get(i), labels.get(i));
            if (i % 100 == 0) {
                System.out.println("See " + i + "-th element");
            }
        }
    }

    public int predict(Tensor image) {
        Tensor copy = image.copy();
        for (Node<Tensor, Tensor> node : nodes) {
            copy = node.makeStep(copy);
        }
        Vector result = toFlat.makeStep(copy);
        result = ender.makeStep(result);
        ArrayList<Double> p = SoftMax.softArgMax(result);
        return Utils.getPositionMax.apply(p);
    }
}
