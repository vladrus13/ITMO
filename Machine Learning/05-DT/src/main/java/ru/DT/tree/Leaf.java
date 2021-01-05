package ru.DT.tree;

public class Leaf extends Node {
    private final int group;

    public Leaf(int group, int id) {
        this.group = group;
        this.id = id;
    }

    public int getGroup() {
        return group;
    }

    @Override
    public String toString() {
        return String.format("C %d", group);
    }

    @Override
    public StringBuilder toStringTree() {
        return new StringBuilder(this.toString()).append("\n");
    }
}
