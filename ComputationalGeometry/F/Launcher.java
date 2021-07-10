import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

public class Launcher {
    public static class Point {
        public final long x;
        public final long y;

        public Point(long x, long y) {
            this.x = x;
            this.y = y;
        }

        public Point(FastReader fastReader) {
            this.x = fastReader.nextLong();
            this.y = fastReader.nextLong();
        }

        public Point subtract(Point other) {
            return new Point(x - other.x, y - other.y);
        }

        public Point add(Point other) {
            return new Point(x + other.x, y + other.y);
        }

        public Point multiply(Point other) {
            return new Point(x * other.x, y * other.y);
        }

        public long sqr() {
            return (long) x * x + (long) y * y;
        }

        public double distance(Point other) {
            return Math.sqrt(subtract(other).sqr());
        }

        public long sqr_distance(Point other) {
            return subtract(other).sqr();
        }

        public long dot(Point other) {
            return (long) x * other.x + (long) y * other.y;
        }

        public Point dot(long a) {
            return new Point(x * a, y * a);
        }

        public Point divide(long a) {
            return new Point(x / a, y / a);
        }

        public Point copy() {
            return new Point(x, y);
        }

        public long cross(Point other) {
            return (long) x * other.y - (long) y * other.x;
        }

        public void print(PrintWriter printWriter) {
            printWriter.println(String.format("%d %d", x, y));
        }
    }

    public static long angle(Point a, Point b, Point c) {
        return b.subtract(a).cross(c.subtract(b));
    }

    public static long antiAngle(Point a, Point b, Point c) {
        return b.subtract(a).cross(c.subtract(a));
    }

    public static class ConvexHull {

        private final ArrayList<Point> points;
        private final LinkedList<Point> convex;

        public Point last() {
            return convex.getLast();
        }

        public Point preLast() {
            return convex.get(convex.size() - 2);
        }

        public ConvexHull(ArrayList<Point> input) {
            points = new ArrayList<>(input);
            convex = new LinkedList<>();
            Point minima = input.stream().min((p1, p2) -> p1.y == p2.y ? (p1.x - p2.x > 0 ? -1 : 1) : (p1.y - p2.y > 0 ? -1 : 1)).orElseThrow();
            points.sort((p1, p2) -> {
                long angle = antiAngle(minima, p1, p2);
                long distance = p1.sqr_distance(minima) - p2.sqr_distance(minima);
                return angle == 0L ? (Long.compare(distance, 0L)) : (angle > 0L ? -1 : 1);
            });
            convex.add(minima);
            convex.add(points.get(1));
            points.remove(0);
            points.remove(0);
            for (Point current : points) {
                while (convex.size() > 1 && angle(preLast(), last(), current) <= 0L) {
                    convex.removeLast();
                }
                convex.add(current);
            }
        }

        private boolean in(Point a, Point b, Point c) {
            return angle(a, b, c) == 0 &&
                    (c.x >= Math.min(a.x, b.x) && c.x <= Math.max(a.x, b.x) && c.y >= Math.min(a.y, b.y) && c.y <= Math.max(a.y, b.y));
        }

        public LinkedList<Point> getConvex() {
            return convex;
        }
    }

    public static class Area {
        public static long areaOn2(List<Point> points) {
            long answer = 0L;
            Point prev = points.get(0);
            for (Point point : points) {
                answer += prev.cross(point);
                prev = point;
            }
            answer += points.get(points.size() - 1).cross(points.get(0));
            return answer;
        }
    }

    public static void main(String[] args) {
        FastReader fastReader = new FastReader();
        PrintWriter printWriter = new PrintWriter(System.out);
        ArrayList<Point> points = new ArrayList<>();
        long n = fastReader.nextInt();
        for (long i = 0; i < n; i++) {
            points.add(new Point(fastReader));
        }
        ConvexHull convexHull = new ConvexHull(points);
        LinkedList<Point> hull = convexHull.getConvex();
        printWriter.println(hull.size());
        for (Point point : hull) {
            point.print(printWriter);
        }
        BigDecimal area = new BigDecimal(Area.areaOn2(hull));
        printWriter.printf("%s\n", area.setScale(10, RoundingMode.CEILING).divide(new BigDecimal(2), RoundingMode.CEILING));
        printWriter.close();
    }

    static class FastReader {
        BufferedReader br;
        StringTokenizer st;

        public FastReader() {
            br = new BufferedReader(new InputStreamReader(System.in));
        }

        public FastReader(String s) throws FileNotFoundException {
            br = new BufferedReader(new FileReader(s));
        }

        String next() {
            while (st == null || !st.hasMoreElements()) {
                try {
                    st = new StringTokenizer(br.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return st.nextToken();
        }

        int nextInt() {
            return Integer.parseInt(next());
        }

        long nextLong() {
            return Long.parseLong(next());
        }

        double nextDouble() {
            return Double.parseDouble(next());
        }

        String nextLine() {
            String str = "";
            try {
                str = br.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return str;
        }
    }
}