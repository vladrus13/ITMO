package ru.DT.tree;

public abstract class Node {
    protected int id;

    public int getId() {
        return id;
    }

    public abstract String toString();

    public abstract StringBuilder toStringTree();
}
