package ru.vladrus13.bean;

public class Point {
    private final int x, y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @SuppressWarnings("MethodDoesntCallSuperMethod")
    public Point clone() {
        return new Point(x, y);
    }
}
