import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.TreeSet;

public class Launcher {

    public final static double EPS = 1e-7;

    public static class Pair<A, B> {
        public final A a;
        public final B b;

        public Pair(A a, B b) {
            this.a = a;
            this.b = b;
        }
    }

    public static boolean isLess(double a, double b) {
        return a + EPS < b;
    }

    public static boolean isBigger(double a, double b) {
        return a > b + EPS;
    }

    public static boolean isEquals(double a, double b) {
        return isEquals(a, b, EPS);
    }

    public static boolean isEquals(double a, double b, double eps) {
        return Math.abs(a - b) < eps;
    }

    public static boolean isZero(double a) {
        return isEquals(a, 0.0);
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
            if (Math.abs(z) < EPS) {
                return null;
            }
            return new Point(
                    (-det(c, b, other.c, other.b) / z),
                    (-det(a, c, other.a, other.c) / z));
        }

        public boolean parallel(Line other) {
            return Math.abs(det(a, b, other.a, other.b)) < EPS;
        }

        public boolean contain(Point point) {
            return a * point.x + b * point.y + c < EPS;
        }
    }

    public static class Point implements Comparable<Point> {
        public final double x;
        public final double y;
        public Segment segment;

        public Point(double x, double y) {
            this.x = x;
            this.y = y;
        }

        public Point(FastReader fastReader) {
            this.x = fastReader.nextInt();
            this.y = fastReader.nextInt();
        }

        public Point subtruct(Point other) {
            return new Point(x - other.x, y - other.y);
        }

        public Point plus(Point other) {
            return new Point(x + other.x, y + other.y);
        }

        public double sqr() {
            return x * x + y * y;
        }

        public double distance(Point other) {
            return Math.sqrt(subtruct(other).sqr());
        }

        public double dot(Point other) {
            return x * other.x + y * other.y;
        }

        public Point dot(double a) {
            return new Point(x * a, y * a);
        }

        public double distance(Points s) {
            return s.closest(this).distance(this);
        }

        public Point copy() {
            return new Point(x, y);
        }

        @Override
        public int compareTo(Point point) {
            if (!isEquals(this.x, point.x)) {
                return Double.compare(this.x, point.x);
            }
            if (!isEquals(this.y, point.y)) {
                return Double.compare(this.y, point.y);
            }
            return 0;
        }

        public double cross(Point other) {
            return x * other.y - y * other.x;
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

        public Points(FastReader fastReader, Type type) {
            this.start = new Point(fastReader);
            this.finish = new Point(fastReader);
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
                    Point as = a.subtruct(start);
                    Point fa = finish.subtruct(start);
                    double alpha = fa.sqr();
                    double dotAlpha = fa.dot(as);
                    double t;
                    if (isZero(alpha)) {
                        t = Double.MAX_VALUE / 2;
                    } else {
                        t = dotAlpha / alpha;
                    }
                    return start.plus(fa.dot(t));
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
                    return Double.compare(toStart, toFinish) > 0 ? finish.copy() : start.copy();
                default:
                    throw new IllegalStateException("BAN!");
            }
        }

        public double distance(Points a) {
            Line thisLine = this.toLine();
            Line anotherLine = a.toLine();
            if (this.type == Type.STRAIGHT && a.type == Type.STRAIGHT) {
                if (thisLine.parallel(anotherLine)) {
                    return this.start.distance(a);
                } else {
                    return 0.0;
                }
            }

            if (this.type == Type.STRAIGHT && a.type == Type.RAY) {
                Point inter = thisLine.intersect(anotherLine);
                if (inter != null && a.on(inter)) {
                    return 0.0;
                }
                return a.start.distance(this);
            }
            if (this.type == Type.STRAIGHT && a.type == Type.SEGMENT) {
                if (thisLine.parallel(anotherLine)) {
                    return a.start.distance(this);
                }
                Point inter = thisLine.intersect(anotherLine);
                if (a.on(inter)) {
                    return 0.0;
                }
                return Double.min(a.start.distance(this), a.finish.distance(this));
            }
            if (this.type == Type.RAY && a.type == Type.STRAIGHT) {
                return a.distance(this);
            }
            if (this.type == Type.RAY && a.type == Type.RAY) {
                Point interact = thisLine.intersect(anotherLine);
                if (interact != null && this.on(interact) && a.on(interact)) {
                    return 0.0;
                }
                double toStart1 = this.closest(a.start).distance(a.start);
                double toStart2 = a.closest(this.start).distance(this.start);
                return Double.min(toStart1, toStart2);
            }
            if (this.type == Type.RAY && a.type == Type.SEGMENT) {
                Point inter = this.toLine().intersect(a.toLine());
                if (inter != null && this.on(inter) && a.on(inter)) {
                    return 0.0;
                }
                return Double.min(Double.min(a.start.distance(this), a.finish.distance(this)), this.start.distance(a));
            }
            if (this.type == Type.SEGMENT && a.type == Type.STRAIGHT) {
                return a.distance(this);
            }
            if (this.type == Type.SEGMENT && a.type == Type.RAY) {
                return a.distance(this);
            }
            if (this.type == Type.SEGMENT && a.type == Type.SEGMENT) {
                Point inter = thisLine.intersect(anotherLine);
                if (inter != null && this.on(inter) && a.on(inter)) {
                    return 0.0;
                }
                return Double.min(Double.min(Double.min(
                        a.start.distance(this),
                        a.finish.distance(this)),
                        start.distance(a)),
                        finish.distance(a));
            }
            throw new IllegalStateException("BAN!");
        }

        public Line toLine() {
            return new Line(
                    finish.y - start.y,
                    start.x - finish.x,
                    ((start.y - finish.y) * (finish.x))
                            - (start.x - finish.x) * finish.y);
        }
    }

    public static class Event implements Comparable<Event> {
        public final Point point;
        public final boolean isOpen;

        public Event(Point point, boolean isOpen) {
            this.point = point;
            this.isOpen = isOpen;
        }

        @Override
        public int compareTo(Event event) {
            int pointCompare = this.point.compareTo(event.point);
            if (pointCompare != 0) {
                return pointCompare;
            }
            if (!isOpen) return 1;
            return -1;
        }
    }

    public static int leftRotate(Point a, Point b, Point c) {
        double r = b.subtruct(a).cross(c.subtruct(a));
        return Math.abs(r) < EPS ? 0 : r > 0 ? 1 : -1;
    }

    public static class Segment implements Comparable<Segment> {

        public Point start;
        public Point finish;

        public final int number;

        public void swap() {
            if (start.compareTo(finish) > 0) {
                Point temp = start;
                start = finish;
                finish = temp;
            }
        }

        public Segment(FastReader fastReader, int number) {
            this.start = new Point(fastReader);
            this.finish = new Point(fastReader);
            this.number = number;
            start.segment = this;
            finish.segment = this;
            swap();
        }

        boolean smolIntersect(double a1, double a2, double a3, double a4) {
            if (a1 > a2) {
                double t = a1;
                a1 = a2;
                a2 = t;
            }
            if (a3 > a4) {
                double t = a3;
                a3 = a4;
                a4 = t;
            }
            return Double.max(a1, a3) <= Double.min(a2, a4) + EPS;
        }

        public boolean intersect(Segment segment) {
            return smolIntersect(this.start.x, this.finish.x, segment.start.x, segment.finish.x) &&
                    smolIntersect(this.start.y, this.finish.y, segment.start.y, segment.finish.y) &&
                    leftRotate(this.start, this.finish, segment.start) * leftRotate(this.start, this.finish, segment.finish) <= 0 &&
                    leftRotate(segment.start, segment.finish, this.start) * leftRotate(segment.start, segment.finish, this.finish) <= 0;
        }

        @Override
        public int compareTo(Segment segment) {
            if (this.number == segment.number) return 0;
            double minima = Double.max(segment.start.x, this.start.x);
            Point thisPosition = this.fill(new Point(minima, 0));
            Point otherPosition = segment.fill(new Point(minima, 0));
            return thisPosition.y < otherPosition.y - EPS ? -1 : 1;
        }

        public Point fill(Point a) {
            if (isZero(start.x - finish.x)) {
                return new Point(a.x, start.y);
            }
            return new Point(a.x, start.y + (finish.y - start.y) * (a.x - start.x) / (finish.x - start.x));
        }
    }

    public static class Intersect {
        public final ArrayList<Segment> segments;

        public Intersect(ArrayList<Segment> segments) {
            this.segments = segments;
        }

        public Pair<Segment, Segment> intersect() {
            TreeSet<Segment> segments = new TreeSet<>();
            ArrayList<Event> points = new ArrayList<>();
            for (Segment segment : this.segments) {
                points.add(new Event(segment.start, true));
                points.add(new Event(segment.finish, false));
            }
            points.sort(Event::compareTo);
            Segment current;
            for (Event pointer : points) {
                Point point = pointer.point;
                current = point.segment;
                Segment segmentHigher = segments.higher(current);
                Segment segmentLower = segments.lower(current);
                if (!pointer.isOpen) {
                    if (segmentHigher != null && segmentLower != null && segmentHigher.intersect(segmentLower)) {
                        return new Pair<>(segmentLower, segmentHigher);
                    }
                    segments.remove(current);
                } else {
                    if (segmentLower != null && current.intersect(segmentLower)) {
                        return new Pair<>(segmentLower, current);
                    }
                    if (segmentHigher != null && current.intersect(segmentHigher)) {
                        return new Pair<>(segmentHigher, current);
                    }
                    segments.add(current);
                }
            }
            return null;
        }
    }

    public static void main(String[] args) {
        FastReader fastReader = new FastReader();
        PrintWriter printWriter = new PrintWriter(System.out);
        int n = fastReader.nextInt();
        ArrayList<Segment> segments = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            segments.add(new Segment(fastReader, i));
        }
        Intersect intersect = new Intersect(segments);
        Pair<Segment, Segment> inter = intersect.intersect();
        if (inter != null) {
            printWriter.println("YES");
            printWriter.println(String.format("%d %d",
                    inter.a.number, inter.b.number));
        } else {
            printWriter.println("NO");
        }
        printWriter.close();
    }

    static class FastReader {
        BufferedReader br;
        StringTokenizer st;

        public FastReader() {
            br = new BufferedReader(new InputStreamReader(System.in));
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
    }
}