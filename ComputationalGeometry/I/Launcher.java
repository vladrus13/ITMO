import java.io.*;
import java.util.*;

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

        public long sqr() {
            return x * x + y * y;
        }

        public long sqr_distance(Point other) {
            return subtract(other).sqr();
        }

        public long cross(Point other) {
            return x * other.y - y * other.x;
        }

        public void print(PrintWriter printWriter) {
            printWriter.println(String.format("%d %d", x, y));
        }

        public boolean equals(Point point) {
            return this.x == point.x && this.y == point.y;
        }
    }

    public static class Triangle {
        public final Point a;
        public final Point b;
        public final Point c;

        public Triangle(Point a, Point b, Point c) {
            this.a = a;
            this.b = b;
            this.c = c;
        }

        public boolean in(Point point) {
            long a1 = singAntiAngle(a, b, point);
            long a2 = singAntiAngle(b, c, point);
            long a3 = singAntiAngle(c, a, point);
            long a4 = singAntiAngle(b, a, point);
            long a5 = singAntiAngle(c, b, point);
            long a6 = singAntiAngle(a, c, point);
            return (a1 * a6 <= 0L) && (a4 * a2 <= 0L) && (a3 * a5 <= 0L);
        }

        public boolean isVertex(Point point) {
            return a.equals(point) || b.equals(point) || c.equals(point);
        }
    }

    public static long angle(Point a, Point b, Point c) {
        return b.subtract(a).cross(c.subtract(b));
    }

    public static long antiAngle(Point a, Point b, Point c) {
        return b.subtract(a).cross(c.subtract(a));
    }

    public static int singAngle(Point a, Point b, Point c) {
        long angle = angle(a, b, c);
        return Long.compare(angle, 0L);
    }

    public static int singAntiAngle(Point a, Point b, Point c) {
        long angle = antiAngle(a, b, c);
        // if angle < 0 ? -1 : angle == 0 ? 0 : 1
        return Long.compare(angle, 0L);
    }

    public static class IndexedPoint {
        public final Point current;
        public int number;

        public IndexedPoint(Point current) {
            this.current = current;
        }
    }

    public static class ConvexHull {

        private final LinkedList<Point> convex;

        public Point last() {
            return convex.getLast();
        }

        public Point preLast() {
            return convex.get(convex.size() - 2);
        }

        public ConvexHull(final ArrayList<Point> input) {
            ArrayList<Point> points = new ArrayList<>(input);
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

    public static class EarClipping {
        public final ArrayList<Point> ears = new ArrayList<>();

        public IndexedPoint current(LinkedList<IndexedPoint> linked) {
            return linked.getLast();
        }

        public IndexedPoint previous(LinkedList<IndexedPoint> linked) {
            return linked.get(linked.size() - 2);
        }

        public IndexedPoint previousPrevious(LinkedList<IndexedPoint> linked) {
            return linked.get(linked.size() - 3);
        }

        public boolean check(ArrayList<Point> points, Point p1, Point p2, Point p3, boolean isReversed) {
            if (isReversed && singAntiAngle(p2, p1, p3) <= 0) return false;
            if (!isReversed && singAntiAngle(p2, p1, p3) >= 0) return false;
            Triangle triangle = new Triangle(p1, p2, p3);
            for (Point it : points) {
                if (!triangle.isVertex(it) && triangle.in(it)) {
                    return false;
                }
            }
            return true;
        }

        EarClipping(ArrayList<Point> points, boolean isReversed) {
            LinkedList<IndexedPoint> linked = new LinkedList<>();
            for (int i = 0; i < points.size(); i++) {
                Point point = points.get(i);
                IndexedPoint temp = new IndexedPoint(point);
                temp.number = i;
                linked.addLast(temp);
                while (linked.size() >= 3) {
                    if (check(points, previousPrevious(linked).current, previous(linked).current, current(linked).current, isReversed)) {
                        ears.add(new Point(previousPrevious(linked).number, current(linked).number));
                        IndexedPoint current = linked.removeLast();
                        linked.removeLast();
                        linked.add(current);
                    } else {
                        break;
                    }
                }
            }
            ears.remove(ears.size() - 1);
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
        boolean isReversed = Area.areaOn2(points) < 0;
        EarClipping earClipping = new EarClipping(points, isReversed);
        for (Point point : earClipping.ears) {
            point.print(printWriter);
        }
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