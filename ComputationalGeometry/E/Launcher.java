import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Launcher {

    public static class Pair<A, B> {
        public final A first;
        public final B second;

        public Pair(A first, B second) {
            this.first = first;
            this.second = second;
        }
    }

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
    }

    public static long angle(Point a, Point b, Point c) {
        return b.subtract(a).cross(c.subtract(b));
    }

    public static class ConvexHull {

        public enum containType {
            CONTAIN, NOT_CONTAIN
        }

        private final ArrayList<Point> points;

        public ConvexHull(ArrayList<Point> input) {
            ArrayList<Point> real = new ArrayList<>();
            int positionLower = 0;
            Point lower = input.get(0);
            for (int i = 0; i < input.size(); i++) {
                if (input.get(i).x < lower.x || (input.get(i).x == lower.x && input.get(i).y < lower.y)) {
                    lower = input.get(i);
                    positionLower = i;
                }
            }
            for (int i = positionLower; i < input.size(); i++) {
                real.add(input.get(i));
            }
            for (int i = 0; i < positionLower; i++) {
                real.add(input.get(i));
            }
            this.points = real;
        }

        private boolean in(Point a, Point b, Point c) {
            return angle(a, b, c) == 0 &&
                    (c.x >= Math.min(a.x, b.x) && c.x <= Math.max(a.x, b.x) && c.y >= Math.min(a.y, b.y) && c.y <= Math.max(a.y, b.y));
        }

        private Point last() {
            return points.get(points.size() - 1);
        }

        public containType contain(Point p) {
            int count = 0;
            for (int i = 0; i < points.size(); i++) {
                Pair<Point, Point> in;
                if (points.get(i).y > points.get((i + 1) % points.size()).y) {
                    in = new Pair<>(points.get((i + 1) % points.size()), points.get(i));
                } else {
                    in = new Pair<>(points.get(i), points.get((i + 1) % points.size()));
                }
                if (in(in.first, in.second, p)) return containType.CONTAIN;
                count += (!(p.y < in.first.y || p.y >= in.second.y) && angle(in.first, in.second, p) > 0) ? 1 : 0;
            }
            return count % 2 == 0 ? containType.NOT_CONTAIN : containType.CONTAIN;
        }
    }

    public static void main(String[] args) {
        FastReader fastReader = new FastReader();
        PrintWriter printWriter = new PrintWriter(System.out);
        ArrayList<Point> points = new ArrayList<>();
        int n = fastReader.nextInt();
        int x = fastReader.nextInt();
        int y = fastReader.nextInt();
        for (int i = 0; i < n; i++) {
            points.add(new Point(fastReader));
        }
        ConvexHull convexHull = new ConvexHull(points);
        printWriter.println((convexHull.contain(new Point(x, y)) == ConvexHull.containType.CONTAIN ? "YES\n" : "NO\n"));
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