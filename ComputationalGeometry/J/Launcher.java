import java.io.*;
import java.util.*;

public class Launcher {

    public static final double EPS = 1e-6;

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


    public static class Point {
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

        public double angle() {
            return Math.atan2(y, x);
            // return atan > 0 ? atan : Math.PI * 2 - atan;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Point point = (Point) o;
            return Math.abs(point.x - x) < EPS && Math.abs(point.y - y) < EPS;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }

        public void println(PrintWriter printWriter) {
            printWriter.printf("%f %f\n", x, y);
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

        public boolean equivalent(Line other) {
            return isZero(det(a, b, other.a, other.b)) &&
                    isZero(det(a, c, other.a, other.c)) &&
                    isZero(det(b, c, other.b, other.c));
        }

        public boolean contain(Point point) {
            return a * point.x + b * point.y + c < EPS;
        }
    }

    public static class PointCoordinatesComparator implements Comparator<Point> {

        @Override
        public int compare(Point t0, Point t1) {
            double compare = t0.x - t1.x;
            if (Math.abs(compare) > EPS) {
                return compare < 0 ? -1 : 1;
            }
            compare = t0.y - t1.y;
            return Math.abs(compare) > EPS ? (compare < 0 ? -1 : 1) : 0;
        }
    }

    public static class Intersector {
        public final ArrayList<Line> lines;
        public final ArrayList<Double> areas;
        public final ArrayList<Point> interseptions;

        public static class PointComparator {
            public static Comparator<Point> getAngleComparator(Point point) {
                return Comparator.comparingDouble(t0 -> t0.subtract(point).angle());
            }
        }

        public class NodeComparator {
            public Comparator<Node> getAngleComparator(Node node) {
                return Comparator.comparingDouble(t0 -> interseptions.get(t0.number).subtract(interseptions.get(node.number)).angle());
            }
        }

        public static class Node {
            public final int number;
            public boolean used;

            public Node(int number) {
                this.number = number;
                used = false;
            }
        }

        public static class Area {
            public static double areaOn2(List<Point> points) {
                double answer = 0.0;
                Point prev = points.get(0);
                for (Point point : points) {
                    answer += prev.cross(point);
                    prev = point;
                }
                answer += points.get(points.size() - 1).cross(points.get(0));
                return answer / 2;
            }
        }

        public static class Printer {

            public static PrintWriter printWriter;
            public static final boolean isPrint = true;

            public static void printPoints(Collection<Point> points) {
                if (!isPrint) return;
                for (Point point : points) {
                    point.println(printWriter);
                }
                printWriter.println();
            }
        }

        public Intersector(ArrayList<Line> lines) {
            this.lines = lines;
            this.areas = new ArrayList<>();
            int counter = 0;
            interseptions = new ArrayList<>();
            ArrayList<ArrayList<Node>> g = new ArrayList<>();
            TreeMap<Point, Integer> indexies = new TreeMap<>(new PointCoordinatesComparator());
            int n = lines.size();
            for (int i = 0; i < n; i++) {
                ArrayList<Point> points = new ArrayList<>();
                for (Line line : lines) {
                    if (lines.get(i).parallel(line)) continue;
                    Point intersection = lines.get(i).intersect(line);
                    points.add(intersection);
                    if (!indexies.containsKey(intersection)) {
                        interseptions.add(intersection);
                        g.add(new ArrayList<>());
                        indexies.put(intersection, counter);
                        counter++;
                    }
                }
                PointCoordinatesComparator comparator = new PointCoordinatesComparator();
                points.sort(comparator);
                for (int it = 1; it < points.size(); it++) {
                    int v = indexies.get(points.get(it - 1));
                    int u = indexies.get(points.get(it));
                    if (comparator.compare(interseptions.get(v), interseptions.get(u)) != 0) {
                        g.get(v).add(new Node(u));
                        g.get(u).add(new Node(v));
                    }
                }
            }
            n = interseptions.size();
            NodeComparator nodeComparator = new NodeComparator();
            for (int i = 0; i < n; i++) {
                g.get(i).sort(nodeComparator.getAngleComparator(new Node(i)).reversed());
            }
            ArrayList<Double> copy = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < g.get(i).size(); j++) {
                    if (!g.get(i).get(j).used) {
                        int v = i;
                        int indexU = j;
                        ArrayList<Point> polygon = new ArrayList<>();
                        polygon.add(interseptions.get(v));
                        while (!g.get(v).get(indexU).used) {
                            g.get(v).get(indexU).used = true;
                            int newV = g.get(v).get(indexU).number;
                            ArrayList<Node> nodes = g.get(newV);
                            int index = -1;
                            for (int it = 0; it < nodes.size(); it++) {
                                Node node = nodes.get(it);
                                if (node.number == v) {
                                    index = (it + 1 + nodes.size()) % nodes.size();
                                    break;
                                }
                            }
                            if (!g.get(newV).get(index).used) {
                                polygon.add(interseptions.get(newV));
                            }
                            v = newV;
                            indexU = index;
                        }
                        // Printer.printPoints(polygon);
                        copy.add(Area.areaOn2(polygon));
                    }
                }
            }
            for (Double it : copy) {
                if (it > 1e-8) {
                    areas.add(it);
                }
            }
            areas.sort(Double::compareTo);
        }

        public ArrayList<Double> getAreas() {
            return areas;
        }
    }

    public static void main(String[] args) {
        FastReader fastReader = new FastReader();
        PrintWriter printWriter = new PrintWriter(System.out);
        Intersector.Printer.printWriter = printWriter;
        ArrayList<Line> lines = new ArrayList<>();
        int n = fastReader.nextInt();
        for (int i = 0; i < n; i++) {
            lines.add(new Points(new Point(fastReader), new Point(fastReader), Points.Type.SEGMENT).toLine());
        }
        Intersector intersector = new Intersector(lines);
        ArrayList<Double> answer = intersector.getAreas();
        printWriter.println(answer.size());
        for (Double it : answer) {
            printWriter.printf("%.15f\n", it);
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