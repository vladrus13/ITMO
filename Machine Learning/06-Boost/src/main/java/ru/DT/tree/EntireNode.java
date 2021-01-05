package ru.DT.tree;

public class EntireNode extends Node {
    private int divider;
    private double hold;
    private Node left;
    private Node right;

    @Override
    public String toString() {
        return String.format("Q %d %.10f %d %d", divider + 1, hold, left.getId(), right.getId());
    }

    @Override
    public StringBuilder toStringTree() {
        return new StringBuilder(this.toString()).append("\n").append(left.toStringTree()).append(right.toStringTree());
    }

    public void setDivider(int divider) {
        this.divider = divider;
    }

    public void setHold(double hold) {
        this.hold = hold;
    }

    public void setLeft(Node left) {
        this.left = left;
    }

    public void setRight(Node right) {
        this.right = right;
    }

    public int getDivider() {
        return divider;
    }

    public double getHold() {
        return hold;
    }

    public Node getLeft() {
        return left;
    }

    public Node getRight() {
        return right;
    }
}
