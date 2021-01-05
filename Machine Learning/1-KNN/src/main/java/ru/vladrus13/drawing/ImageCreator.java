package ru.vladrus13.drawing;

import ru.vladrus13.bean.Point;
import ru.vladrus13.utils.DistanceFunction;
import ru.vladrus13.utils.KernelFunction;

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

    /**
     * Draw a graphic
     *
     * @param type             type of network
     * @param distanceFunction {@link DistanceFunction}
     * @param kernelFunction   {@link KernelFunction}
     * @param fixed            fixed-window points
     * @param variable         variable-window points
     * @return {@link BufferedImage}
     */
    public static BufferedImage draw(String type, DistanceFunction distanceFunction, KernelFunction kernelFunction, ArrayList<Point> fixed, ArrayList<Point> variable) {
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
        IntStream.range(0, 11).forEach(element -> {
                    lineOnGraphic(graphics, new Point(-10, element * 80), new Point(10, element * 80));
                    lineOnGraphic(graphics, new Point(element * 80, -10), new Point(element * 80, 10));
                    graphics.drawString(String.valueOf((double) element / 10), 10, 1000 - (element * 80 + 100));
                    graphics.drawString(String.valueOf(element), 100 + element * 80, 990);
                }
        );
        graphics.drawString(String.format("Type: %s, Distance function: %s, Kernel function: %s", type, distanceFunction.toString(), kernelFunction.toString()), 10, 10);
        graphics.drawString("Red - fixed window, Blue - variable window", 10, 30);
        graphics.drawString("@vladrus13", 930, 980);
        graphics.setColor(Color.RED);
        for (Point point : fixed) {
            pointOnGraphic(graphics, point);
        }
        graphics.setColor(Color.BLUE);
        for (Point point : variable) {
            pointOnGraphic(graphics, point);
        }
        return image;
    }
}
