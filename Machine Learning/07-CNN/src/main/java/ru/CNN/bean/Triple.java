package ru.CNN.bean;

public class Triple<A, B, C> {
    public A depth;
    public B height;
    public C width;

    public Triple(A depth, B height, C width) {
        this.depth = depth;
        this.height = height;
        this.width = width;
    }

}
