package ru.vladrus13.bean;

import java.util.ArrayList;

public class Bean {
    private final ArrayList<Double> attributes;
    private final double y;

    public Bean(ArrayList<Double> attributes) {
        this.attributes = attributes;
        this.y = attributes.get(attributes.size() - 1);
        attributes.set(attributes.size() - 1, 1.0);
    }

    public ArrayList<Double> getAttributes() {
        return attributes;
    }

    public double getY() {
        return y;
    }
}
