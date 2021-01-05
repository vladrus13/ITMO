package ru.DT.bean;

public class Pair<T, E> {
    public final T first;
    public final E second;

    public Pair(T first, E second) {
        this.first = first;
        this.second = second;
    }
}
