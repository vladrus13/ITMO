import java.io.*;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.*;

public class Launcher {

    public static class Point {
        public final int x;
        public final int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public Point(FastReader fastReader) {
            this.x = fastReader.nextInt();
            this.y = fastReader.nextInt();
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

        public Point dot(int a) {
            return new Point(x * a, y * a);
        }

        public Point divide(int a) {
            return new Point(x / a, y / a);
        }

        public Point copy() {
            return new Point(x, y);
        }

        public long cross(Point other) {
            return (long) x * other.y - (long) y * other.x;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Point point = (Point) o;
            return x == point.x && y == point.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }

        public void println(PrintWriter printWriter) {
            printWriter.printf("%d %d\n", x, y);
        }
    }

    public static class PointCoordinatesComparator implements Comparator<Point> {
        @Override
        public int compare(Point t0, Point t1) {
            int compare = Integer.compare(t0.x, t1.x);
            if (compare != 0) {
                return compare;
            }
            return Integer.compare(t0.y, t1.y);
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
        int n = fastReader.nextInt();
        for (int i = 0; i < n; i++) {
            points.add(new Point(fastReader));
        }
        ConvexHull convexHull = new ConvexHull(points);
        ArrayList<Point> hull = new ArrayList<>(convexHull.getConvex());
        if (Area.areaOn2(hull) > 0) {
            Collections.reverse(hull);
        }
        PointCoordinatesComparator pointCoordinatesComparator = new PointCoordinatesComparator();
        Point mininaPoint = hull.stream().min(pointCoordinatesComparator).orElseThrow();
        Point maximaPoint = hull.stream().max(pointCoordinatesComparator).orElseThrow();
        int leftest = hull.indexOf(mininaPoint);
        int rightest = hull.indexOf(maximaPoint);
        if (leftest == -1 || rightest == -1) {
            throw new IllegalStateException("Can't be");
        }
        int first = leftest;
        int second = rightest;
        long maxima = 0L;
        while (first != rightest || second != leftest) {
            int firstNext = (first + 1) % hull.size();
            int secondNext = (second + 1) % hull.size();
            Point firstNextPoint = hull.get(firstNext);
            Point secondNextPoint = hull.get(secondNext);
            Point firstPoint = hull.get(first);
            Point secondPoint = hull.get(second);

            maxima = Math.max(maxima, firstPoint.sqr_distance(secondPoint));

            long angle = antiAngle(firstPoint, firstNextPoint, firstPoint.add(secondNextPoint.subtract(secondPoint)));
            if (angle < 0) {
                second = secondNext;
            }
            if (angle > 0) {
                first = firstNext;
            }
            if (angle == 0) {
                first = firstNext;
                second = secondNext;
            }
        }
        printWriter.printf("%s\n", new BigDecimal(maxima).setScale(50, RoundingMode.CEILING).sqrt(MathContext.DECIMAL128));
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