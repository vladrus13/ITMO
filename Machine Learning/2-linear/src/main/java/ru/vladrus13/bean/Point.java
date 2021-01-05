package ru.vladrus13.bean;

/**
 * Point class
 */
public class Point {
    /**
     * X-axis position
     */
    public final long x;
    /**
     * Y-axis position
     */
    public final long y;

    /**
     * @param x x-axis position
     * @param y y-axis position
     */
    public Point(long x, long y) {
        this.x = x;
        this.y = y;
    }
}
