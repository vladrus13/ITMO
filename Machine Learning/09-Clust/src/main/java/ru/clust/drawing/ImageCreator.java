package ru.clust.drawing;

import ru.clust.bean.Point;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.stream.IntStream;

/**
 * Image creator
 */
public class ImageCreator {

    /**
     * @param point point in (0, 0) on left-bottom
     * @return point in (0, 0) on left-up
     */
    private static Point makeNormal(Point point) {
        return new Point(point.x, 1000 - point.y);
    }

    /**
     * @param point point on graphic
     * @return point in (0, 0) on left-up
     */
    private static Point makeNormalOnGraphic(Point point) {
        return makeNormal(new Point(point.x + 100, point.y + 100));
    }

    /**
     * Draw line
     *
     * @param graphics {@link Graphics}
     * @param start    start point
     * @param finish   finish point
     */
    private static void lineOnGraphic(Graphics graphics, Point start, Point finish) {
        start = makeNormalOnGraphic(start);
        finish = makeNormalOnGraphic(finish);
        graphics.drawLine(start.x, start.y, finish.x, finish.y);
    }

    /**
     * Draw point
     *
     * @param graphics {@link Graphics}
     * @param point    point
     */
    private static void pointOnGraphic(Graphics graphics, Point point) {
        point = makeNormalOnGraphic(point);
        graphics.fillOval(point.x - 4, point.y - 4, 8, 8);
    }

    private static void squareOnGraphic(Graphics graphics, Point start, Point size, Color color) {
        start = makeNormalOnGraphic(start);
        start = new Point(start.x, start.y - size.y);
        graphics.setColor(color);
        graphics.fillRect(start.x, start.y, size.x, size.y);
    }

    public static BufferedImage empty(String file, String method) {
        BufferedImage image = new BufferedImage(1000, 1000, BufferedImage.TYPE_INT_RGB);
        Graphics graphics = image.createGraphics();
        graphics.setColor(Color.LIGHT_GRAY);
        graphics.fillRect(0, 0, 1000, 1000);
        graphics.setColor(Color.BLACK);
        lineOnGraphic(graphics, new Point(0, 0), new Point(800, 0));
        lineOnGraphic(graphics, new Point(0, 0), new Point(0, 800));
        lineOnGraphic(graphics, new Point(800, 0), new Point(800 - 10, -10));
        lineOnGraphic(graphics, new Point(800, 0), new Point(800 - 10, 10));
        lineOnGraphic(graphics, new Point(0, 800), new Point(-10, 790));
        lineOnGraphic(graphics, new Point(0, 800), new Point(10, 790));
        graphics.drawString(String.format("File: %s, Method: %s", file, method), 10, 10);
        graphics.drawString("@vladrus13", 930, 980);
        return image;
    }

    public static void addMatrix(BufferedImage image, ArrayList<ArrayList<Integer>> matrix) {
        Graphics graphics = image.getGraphics();
        int xSize = 800 / matrix.get(0).size();
        int ySize = 800 / matrix.size();
        for (int i = 0; i < matrix.size(); i++) {
            for (int j = 0; j < matrix.get(i).size(); j++) {
                int value = matrix.get(i).get(j);
                squareOnGraphic(graphics, new Point(xSize * i, ySize * j),
                        new Point(xSize, ySize), new Color(value * 127, (1 - value) * 127, 0, 127));
            }
        }
    }

    public static void addPoints(BufferedImage image, ArrayList<Point> points, Color color) {
        Graphics graphics = image.getGraphics();
        graphics.setColor(color);
        for (Point point : points) {
            pointOnGraphic(graphics, point);
        }
    }

    public static void addValues(BufferedImage image, ArrayList<String> x, ArrayList<String> y) {
        Graphics graphics = image.getGraphics();
        graphics.setColor(Color.BLACK);
        int xSize = 800 / x.size();
        int ySize = 800 / y.size();
        for (int i = 0; i < x.size(); i++) {
            lineOnGraphic(graphics, new Point(i * xSize, -10), new Point(i * xSize, 10));
            Point point = makeNormalOnGraphic(new Point(i * xSize, -25));
            graphics.drawString(x.get(i), point.x, point.y);
        }
        for (int i = 0; i < y.size(); i++) {
            lineOnGraphic(graphics, new Point(-10, i * ySize), new Point(10, i * ySize));
            Point point = makeNormalOnGraphic(new Point(-25, i * ySize));
            graphics.drawString(y.get(i), point.x, point.y);
        }
    }
}