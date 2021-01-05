package ru.vladrus13.image;

import ru.vladrus13.bean.Point;

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
        return new Point(point.getX(), 1000 - point.getY());
    }

    /**
     * @param point point on graphic
     * @return point in (0, 0) on left-up
     */
    private static Point makeNormalOnGraphic(Point point) {
        return makeNormal(new Point(point.getX() + 100, point.getY() + 100));
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
        graphics.drawLine(start.getX(), start.getY(), finish.getX(), finish.getY());
    }

    private static void squareOnGraphic(Graphics graphics, Point start, Point size, Color color) {
        start = makeNormalOnGraphic(start);
        start = new Point(start.getX(), start.getY() - size.getY());
        graphics.setColor(color);
        graphics.fillRect(start.getX(), start.getY(), size.getX(), size.getY());
    }

    /**
     * Draw point
     *
     * @param graphics {@link Graphics}
     * @param point    point
     */
    private static void pointOnGraphic(Graphics graphics, Point point, Color color) {
        graphics.setColor(color);
        point = makeNormalOnGraphic(point);
        graphics.fillOval(point.getX() - 4, point.getY() - 4, 8, 8);
    }

    private static Color toColor(double m) {
        m += 1;
        return new Color((int) m * 127, 0, (int) (2 - m) * 127, 128);
    }

    private static Color NPToColor(int category) {
        if (category == 1) {
            return Color.RED;
        } else {
            return Color.BLUE;
        }
    }

    private static Point normalize(double x, double y, double x_min, double x_max, double y_min, double y_max) {
        return new Point((int) ((x - x_min) * 800 / (x_max - x_min)), (int) ((y - y_min) * 800 / (y_max - y_min)));
    }

    private static double lastDigit(double x) {
        return (double) ((int) (x * 1000)) / 1000;
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    public static BufferedImage draw(String file, String method, ArrayList<ArrayList<Double>> X, ArrayList<Integer> Y,
                                     Point size, int chapters, ArrayList<ArrayList<Double>> colors) {
        BufferedImage image = new BufferedImage(1000, 1000, BufferedImage.TYPE_INT_RGB);
        Graphics graphics = image.createGraphics();
        graphics.setColor(Color.LIGHT_GRAY);
        graphics.fillRect(0, 0, 1000, 1000);

        double x_min = X.stream().mapToDouble(e -> e.get(0)).min().getAsDouble();
        double x_max = X.stream().mapToDouble(e -> e.get(0)).max().getAsDouble();
        double y_min = X.stream().mapToDouble(e -> e.get(1)).min().getAsDouble();
        double y_max = X.stream().mapToDouble(e -> e.get(1)).max().getAsDouble();
        double x_step = (x_max - x_min) / chapters;
        double y_step = (y_max - y_min) / chapters;

        for (int i = 0; i < chapters; i++) {
            for (int j = 0; j < chapters; j++) {
                squareOnGraphic(graphics, normalize(x_min + i * x_step, y_min + j * y_step, x_min, x_max, y_min, y_max),
                        size, toColor(colors.get(i).get(j)));
            }
        }
        graphics.setColor(Color.BLACK);
        lineOnGraphic(graphics, new Point(0, 0), new Point(800, 0));
        lineOnGraphic(graphics, new Point(0, 0), new Point(0, 800));
        lineOnGraphic(graphics, new Point(800, 0), new Point(800 - 10, -10));
        lineOnGraphic(graphics, new Point(800, 0), new Point(800 - 10, 10));
        lineOnGraphic(graphics, new Point(0, 800), new Point(-10, 790));
        lineOnGraphic(graphics, new Point(0, 800), new Point(10, 790));
        IntStream.range(0, chapters).forEach(i -> {
                    lineOnGraphic(graphics, new Point(-10, i * (800 / chapters)), new Point(10, i * (800 / chapters)));
                    lineOnGraphic(graphics, new Point(i * (800 / chapters), -10), new Point(i * (800 / chapters), 10));
                    graphics.drawString(String.valueOf(lastDigit(x_min + x_step * i)), 10, 1000 - (i * (800 / chapters) + 100));
                    graphics.drawString(String.valueOf(lastDigit(y_min + y_step * i)), 100 + i * (800 / chapters), 990);
                }
        );
        graphics.drawString(String.format("File: %s, Method: %s", file, method), 10, 10);
        graphics.drawString("Red - N, Blue - P", 10, 30);
        graphics.drawString("@vladrus13", 930, 980);
        graphics.setColor(Color.RED);
        for (int i = 0; i < X.size(); i++) {
            pointOnGraphic(graphics, normalize(X.get(i).get(0), X.get(i).get(1), x_min, x_max, y_min, y_max), NPToColor(Y.get(i)));
        }
        return image;
    }
}