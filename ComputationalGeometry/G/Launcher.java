import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;
import java.util.StringTokenizer;

public class Launcher {

    public static final double EPS = 1e-8;

    public static boolean isLess(double a, double b) {
        return a < b;
    }

    public static boolean isBigger(double a, double b) {
        return a > b;
    }

    public static boolean isEquals(double a, double b) {
        return Math.abs(a - b) < EPS;
    }

    public static boolean isZero(double a) {
        return a < EPS;
    }


    public static class Point implements Comparable<Point> {
        public final double x;
        public final double y;

        public Point(double x, double y) {
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

        public double sqr() {
            return x * x + y * y;
        }

        public double distance(Point other) {
            return Math.sqrt(subtract(other).sqr());
        }

        public double sqr_distance(Point other) {
            return subtract(other).sqr();
        }

        public double dot(Point other) {
            return x * other.x + y * other.y;
        }

        public Point dot(double a) {
            return new Point(x * a, y * a);
        }

        public Point divide(int a) {
            return new Point(x / a, y / a);
        }

        public Point copy() {
            return new Point(x, y);
        }

        public double cross(Point other) {
            return x * other.y - y * other.x;
        }

        @Override
        public int compareTo(Point point) {
            int compare = Double.compare(y, point.y);
            if (compare != 0) {
                return compare;
            }
            return Double.compare(x, point.x);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Point point = (Point) o;
            return Double.compare(point.x, x) == 0 && Double.compare(point.y, y) == 0;
        }
    }

    public static class Points {
        public enum Type {
            SEGMENT, RAY, STRAIGHT
        }

        public Point start;
        public Point finish;
        public Type type;

        public Points(Point start, Point finish, Type type) {
            this.start = start;
            this.finish = finish;
            this.type = type;
        }

        public boolean on(Point a) {
            switch (type) {
                case STRAIGHT:
                    return this.toLine().contain(a);
                case RAY:
                    if (isLess(start.x, finish.x) && isLess(a.x, start.x)) return false;
                    if (isBigger(start.x, finish.x) && isBigger(a.x, start.x)) return false;
                    if (isEquals(start.x, finish.x) && !isEquals(a.x, start.x)) return false;
                    if (isLess(start.y, finish.y) && isLess(a.y, start.y)) return false;
                    if (isBigger(start.y, finish.y) && isBigger(a.y, start.y)) return false;
                    if (isEquals(start.y, finish.y) && !isEquals(a.y, start.y)) return false;
                    return (new Points(start, finish, Type.STRAIGHT)).on(a);
                case SEGMENT:
                    if (isLess(start.x, finish.x)) {
                        if (isLess(a.x, start.x) || isBigger(a.x, finish.x)) return false;
                    }
                    if (isBigger(start.x, finish.x)) {
                        if (isBigger(a.x, start.x) || isLess(a.x, finish.x)) return false;
                    }
                    if (isEquals(start.x, finish.x)) {
                        if (!isEquals(a.x, start.x)) return false;
                    }
                    if (isLess(start.y, finish.y)) {
                        if (isLess(a.y, start.y) || isBigger(a.y, finish.y)) return false;
                    }
                    if (isBigger(start.y, finish.y)) {
                        if (isBigger(a.y, start.y) || isLess(a.y, finish.y)) return false;
                    }
                    if (isEquals(start.y, finish.y)) {
                        if (!isEquals(a.y, start.y)) return false;
                    }
                    return (new Points(start, finish, Type.STRAIGHT)).on(a);
                default:
                    throw new IllegalStateException("BAN!");
            }
        }

        public Point closest(Point a) {
            switch (type) {
                case STRAIGHT:
                    Point as = a.subtract(start);
                    Point fa = finish.subtract(start);
                    double alpha = fa.sqr();
                    double dotAlpha = fa.dot(as);
                    double t;
                    if (isZero(alpha)) {
                        t = Double.MAX_VALUE;
                    } else {
                        t = dotAlpha / alpha;
                    }
                    return start.add(fa.dot(t));
                case RAY:
                    Point closest = (new Points(start, finish, Type.STRAIGHT)).closest(a);
                    return on(closest) ? closest : start;
                case SEGMENT:
                    Point closest1 = (new Points(start, finish, Type.STRAIGHT)).closest(a);
                    if (on(closest1)) {
                        return closest1;
                    }
                    double toStart = a.distance(start);
                    double toFinish = a.distance(finish);
                    return toStart > toFinish ? finish.copy() : start.copy();
                default:
                    throw new IllegalStateException("BAN!");
            }
        }

        public double distance(Point a) {
            return a.distance(this.closest(a));
        }

        public Line toLine() {
            return new Line(
                    finish.y - start.y,
                    start.x - finish.x,
                    ((start.y - finish.y) * finish.x)
                            - (start.x - finish.x) * finish.y);
        }
    }

    public static class Line {
        public final double a;
        public final double b;
        public final double c;

        public Line(double a, double b, double c) {
            this.a = a;
            this.b = b;
            this.c = c;
        }

        private double det(double a, double b, double c, double d) {
            return a * d - b * c;
        }

        public Point intersect(Line other) {
            double z = det(a, b, other.a, other.b);
            if (z < EPS) {
                return null;
            }
            return new Point(
                    (-det(c, b, other.c, other.b) / z),
                    (-det(a, c, other.a, other.c) / z));
        }

        public boolean parallel(Line other) {
            return Math.abs(det(a, b, other.a, other.b)) < EPS;
        }

        public boolean equivalent(Line other) {
            return isZero(det(a, b, other.a, other.b)) &&
                    isZero(det(a, c, other.a, other.c)) &&
                    isZero(det(b, c, other.b, other.c));
        }

        public boolean contain(Point point) {
            return a * point.x + b * point.y + c < EPS;
        }
    }

    public static class Minkowski {
        public final ArrayList<Point> points;

        public Point last() {
            return points.get(points.size() - 1);
        }

        public Minkowski(Polygon a, Polygon b) {
            points = new ArrayList<>();
            ArrayList<Vector> vectors = new ArrayList<>();
            vectors.addAll(a.vectors);
            vectors.addAll(b.vectors);
            points.add(a.bottom.add(b.bottom));
            vectors.sort(Vector::compareTo);
            for (Vector vector : vectors) {
                points.add(last().add(vector.vector));
            }
            if (last().equals(points.get(0))) {
                points.remove(points.size() - 1);
            } else {
                throw new IllegalStateException("Can't be");
            }
        }

        public double distance(Point point) {
            double min = (new Points(points.get(points.size() - 1), points.get(0), Points.Type.SEGMENT)).distance(point);
            for (int i = 1; i < points.size(); i++) {
                min = Math.min(min, (new Points(points.get(i - 1), points.get(i), Points.Type.SEGMENT)).distance(point));
            }
            return min;
        }
    }

    public static class Vector implements Comparable<Vector> {
        public final Point vector;
        public final double angle;

        public Vector(Point vector) {
            this.vector = vector;
            double temp = Math.atan2(vector.y, vector.x);
            if (temp >= 0) {
                angle = temp;
            } else {
                angle = 2 * Math.PI + temp;
            }
        }


        @Override
        public int compareTo(Vector vector) {
            return Double.compare(this.angle, vector.angle);
        }
    }

    public static class Polygon {
        public final ArrayList<Vector> vectors;
        public Point bottom;

        public Polygon(ArrayList<Point> points) {
            bottom = points.get(0);
            vectors = new ArrayList<>();
            for (int i = 1; i < points.size(); i++) {
                vectors.add(new Vector(points.get(i).subtract(points.get(i - 1))));
                if (points.get(i).compareTo(bottom) < 0) {
                    bottom = points.get(i);
                }
            }
            vectors.add(new Vector(points.get(0).subtract(points.get(points.size() - 1))));
        }
    }

    public static void main(String[] args) {
        FastReader fastReader = new FastReader();
        PrintWriter printWriter = new PrintWriter(System.out);
        int n = fastReader.nextInt();
        ArrayList<Point> a = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            a.add(new Point(fastReader));
        }
        Collections.reverse(a);
        int m = fastReader.nextInt();
        ArrayList<Point> b = new ArrayList<>();
        Point zero = new Point(0, 0);
        for (int i = 0; i < m; i++) {
            b.add(zero.subtract(new Point(fastReader)));
        }
        Collections.reverse(b);
        Polygon p1 = new Polygon(a);
        Polygon p2 = new Polygon(b);
        Minkowski minkowski = new Minkowski(p1, p2);
        printWriter.printf("%.15f", minkowski.distance(new Point(0, 0)));
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
