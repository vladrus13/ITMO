import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Launcher {

    public static final double EPS = 1e-6;

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
            return x * x + y * y;
        }

        public double distance(Point other) {
            return Math.sqrt(subtract(other).sqr());
        }

        public long sqr_distance(Point other) {
            return subtract(other).sqr();
        }

        public long dot(Point other) {
            return x * other.x + y * other.y;
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
            return x * other.y - y * other.x;
        }
    }

    private static boolean check(int length, ArrayList<Point> list) {
        return length < list.size();
    }

    private static long min_dist = Long.MAX_VALUE;
    private static Point a1, a2;

    public static ArrayList<Point> recCD(int l, int r, ArrayList<Point> points) {
        ArrayList<Point> returned = new ArrayList<>();
        if (r - l == 1) {
            returned.add(points.get(l));
        } else {
            int m = (l + r) / 2;
            ArrayList<Point> left = recCD(l, m, points);
            ArrayList<Point> right = recCD(m, r, points);
            int leftHer = 0;
            int rightHer = 0;
            int leftStep = 0;
            int rightStep = 0;
            while (check(leftHer, left) || check(rightHer, right)) {
                if ((check(leftHer, left) && check(rightHer, right) && left.get(leftHer).y <= right.get(rightHer).y) || rightHer == right.size()) {
                    if (left.get(leftHer).x > points.get(m).x - Math.sqrt(min_dist)) {
                        while (check(leftStep, right) && right.get(leftStep).y <= left.get(leftHer).y - Math.sqrt(min_dist))
                            leftStep++;
                        while (check(rightStep, right) && right.get(rightStep).y < left.get(leftHer).y + Math.sqrt(min_dist))
                            rightStep++;
                        for (int i = leftStep; i < rightStep; i++) {
                            long current = left.get(leftHer).sqr_distance(right.get(i));
                            if (current < min_dist) {
                                min_dist = current;
                                a1 = left.get(leftHer);
                                a2 = right.get(i);
                            }
                        }
                    }
                    returned.add(left.get(leftHer));
                    leftHer++;
                } else {
                    returned.add(right.get(rightHer));
                    rightHer++;
                }
            }
        }
        return returned;
    }
    /*
    4
0 0
1 0
0 1
1 1
     */

    public static void main(String[] args) {
        FastReader fastReader = new FastReader();
        PrintWriter printWriter = new PrintWriter(System.out);
        ArrayList<Point> points = new ArrayList<>();
        int n = fastReader.nextInt();
        for (int i = 0; i < n; i++) {
            points.add(new Point(fastReader));
        }
        points.sort((point, t1) -> (int) (point.x - t1.x));
        recCD(0, n, points);
        printWriter.printf("%d %d\n%d %d\n", a1.x, a1.y, a2.x, a2.y);
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
