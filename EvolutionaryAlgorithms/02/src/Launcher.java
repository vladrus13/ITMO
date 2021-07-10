import java.io.*;
import java.util.*;

public class Launcher {

    public static class ResultPair implements Comparable<ResultPair> {
        public final Point point;
        public final Result result;

        public ResultPair(Point point, Result result) {
            this.point = point;
            this.result = result;
        }

        @Override
        public int compareTo(ResultPair resultPair) {
            return this.result.compareTo(resultPair.result);
        }
    }

    public interface Test {
        Result getResult(RequestPoint point);
        void getAnswer(Point point);
    }

    public static class Square implements Test {
        public final Point leftDown;
        public final Point rightUp;
        public final Point treasure;
        public int count = 0;
        public final boolean[][] blocks;

        public Square(Point leftDown, Point size, Point treasure, int chanceBlock, int chanceNormal) {
            this.leftDown = leftDown;
            this.rightUp = leftDown.add(size.dot(10));
            this.treasure = treasure;
            int width = (int) size.x;
            int height = (int) size.y;
            blocks = new boolean[width + 1][height + 1];
            Random random = new Random();
            for (int i = 0; i < width + 1; i++) {
                for (int j = 0; j < height + 1; j++) {
                    blocks[i][j] = random.nextInt(chanceBlock + chanceNormal) > chanceNormal;
                }
            }
            Point treas = treasure.subtract(leftDown).divide(10);
            blocks[(int) treas.x][(int) treas.y] = false;
        }

        public boolean in(RequestPoint point) {
            return leftDown.x <= point.x && point.x <= rightUp.x &&
                    leftDown.y <= point.y && point.y <= rightUp.y;
        }

        public Result getResult(RequestPoint point) {
            Result.ActiveType activeType = count == 2967 ? Result.ActiveType.IPHONE : Result.ActiveType.ACTIVE;
            if (count > 2967) {
                throw new IllegalStateException("BAN!");
            }
            count++;
            if (in(point)) {
                if (blocks[(int) (point.x - leftDown.x) / 10][(int) (point.y - leftDown.y) / 10]) {
                    return new Result(Result.ResultType.BLOCKED, 10, activeType);
                }
                return new Result(Result.ResultType.INSIDE, treasure.distance(point), activeType);
            } else {
                if (leftDown.x <= point.x && point.x <= rightUp.x) {
                    double distance = point.y < leftDown.y ? leftDown.y - point.y : point.y - rightUp.y;
                    return new Result(Result.ResultType.OUTSIDE, distance, activeType);
                }
                if (leftDown.y <= point.y && point.y <= rightUp.y) {
                    double distance = point.x < leftDown.x ? leftDown.x - point.x : point.x - rightUp.x;
                    return new Result(Result.ResultType.OUTSIDE, distance, activeType);
                }
                double r = new Point(leftDown.x, rightUp.y).distance(point);
                double rr = new Point(leftDown.x, rightUp.y).distance(point);
                double r1 = leftDown.distance(point);
                double rr1 = rightUp.distance(point);
                return new Result(Result.ResultType.OUTSIDE, Math.min(Math.min(Math.min(r, rr), r1), rr1), activeType);
            }
        }

        public void getAnswer(Point point) {
            if (!point.equals(treasure)) {
                throw new IllegalStateException("Not equals!");
            }
            System.out.println("Get " + count + " requests");
        }
    }

    public static class Square45 implements Test {

        public final int size;
        public final double down;
        public final Point treasure;
        public int count = 0;
        public final Point start;

        public Square45(int size, Point treasure) {
            this.size = size;
            this.down = Math.sqrt(50 * size);
            this.treasure = treasure;
            this.start = new Point();
        }

        public Square45(int size, Point treasure, Point start) {
            this.size = size;
            this.down = Math.sqrt(50 * size);
            this.treasure = treasure;
            this.start = start;
        }

        @Override
        public Result getResult(RequestPoint point) {
            point = point.add(new RequestPoint(start));
            if (count > 2967) {
                throw new IllegalStateException("BAN!");
            }
            Result.ActiveType activeType = count == 2967 ? Result.ActiveType.IPHONE : Result.ActiveType.ACTIVE;
            count++;
            double dijonce = Math.abs(point.x) + Math.abs(point.y);
            if (dijonce > down) {
                double local = 1e20;
                for (Point direction : directions) {
                    local = Math.min(local, new RequestPoint(direction).dot(down).distance(point));
                }
                return new Result(Result.ResultType.OUTSIDE, local, activeType);
            } else {
                return new Result(Result.ResultType.INSIDE, point.subtract(new RequestPoint(start)).distance(new RequestPoint(treasure)), activeType);
            }
        }

        @Override
        public void getAnswer(Point point) {
            if (!treasure.equals(point)) {
                throw new IllegalStateException("Not equals");
            }
        }
    }

    public static class RequestPoint {
        public final double x;
        public final double y;

        public RequestPoint(double x, double y) {
            this.x = x;
            this.y = y;
        }

        public RequestPoint(Point point) {
            this.x = point.x;
            this.y = point.y;
        }

        public RequestPoint onAngle(double angle) {
            return new RequestPoint(x * Math.cos(angle) - y * Math.sin(angle), x * Math.sin(angle) + y * Math.cos(angle));
        }

        public RequestPoint add(RequestPoint point) {
            return new RequestPoint(x + point.x, y + point.y);
        }

        public RequestPoint subtract(RequestPoint point) {
            return new RequestPoint(x + point.x, y + point.y);
        }

        public double distance(RequestPoint point) {
            return Math.sqrt((x - point.x) * (x - point.x) + (y - point.y) * (y - point.y));
        }

        public RequestPoint dot(double a) {
            return new RequestPoint(x * a, y * a);
        }

        public String toString() {
            return String.format("%.15f %.15f", x, y);
        }

        public ArrayList<Point> toPoint() {
            return new ArrayList<>(Arrays.asList(
                    new Point((int) Math.ceil(x), (int) Math.ceil(y)),
                    new Point((int) Math.floor(x), (int) Math.ceil(y)),
                    new Point((int) Math.ceil(x), (int) Math.floor(y)),
                    new Point((int) Math.floor(x), (int) Math.floor(y))
                    ));
        }
    }

    public static class Point {
        public final long x;
        public final long y;

        public Point() {
            this.x = 0;
            this.y = 0;
        }

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

        public double distance(RequestPoint other) {
            return Math.sqrt((x - other.x) * (x - other.x) + (y - other.y) * (y - other.y));
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

        public String toString() {
            return x + " " + y;
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
    }

    public static final Point up = new Point(0, 1);
    public static final Point down = new Point(0, -1);
    public static final Point left = new Point(1, 0);
    public static final Point right = new Point(-1, 0);

    public static final List<Point> directions = Arrays.asList(up, down, left, right);

    public static class Result implements Comparable<Result> {
        public Result(ResultType resultType, double distance, ActiveType activeType) {
            this.resultType = resultType;
            this.distance = distance;
            this.activeType = activeType;
        }

        public Result(FastReader fastReader) {
            this.resultType = ResultType.valueOf(fastReader.next().toUpperCase());
            if (resultType == ResultType.BLOCKED) {
                this.distance = 10;
            } else {
                this.distance = fastReader.nextDouble();
            }
            this.activeType = ActiveType.valueOf(fastReader.next().toUpperCase());
        }

        @Override
        public int compareTo(Result result) {
            if (result == null) {
                return -1;
            }
            int compareType = this.resultType.compareTo(result.resultType);
            if (compareType != 0) {
                return compareType;
            }
            return Double.compare(this.distance, result.distance);
        }

        public enum ResultType {
            INSIDE, BLOCKED, OUTSIDE
        }

        public enum ActiveType {
            ACTIVE, IPHONE
        }

        public boolean isOutside() {
            return resultType == ResultType.OUTSIDE;
        }

        public final ResultType resultType;
        public final double distance;
        public final ActiveType activeType;

        @Override
        public String toString() {
            return "Result{" +
                    "resultType=" + resultType +
                    ", distance=" + distance +
                    ", activeType=" + activeType +
                    '}';
        }
    }

    public static class Requester {
        public final FastReader fastReader;
        public final PrintWriter printWriter;
        public final Map<RequestPoint, Result> resultMap = new HashMap<>();
        public Test square;

        public Requester(FastReader fastReader, PrintWriter printWriter) {
            this.fastReader = fastReader;
            this.printWriter = printWriter;
        }

        public void setSquare(Test square) {
            this.square = square;
        }

        public Result sendActivate(RequestPoint point) {
            if (resultMap.containsKey(point)) {
                return resultMap.get(point);
            }
            if (square != null) {
                System.out.println("SEND: " + point.toString());
                Result result = square.getResult(point);
                System.out.println("GET: " + result.toString());
                resultMap.put(point, result);
                return result;
            }
            printWriter.println(String.format("activate %s", point.toString()));
            printWriter.flush();
            Result result = new Result(fastReader);
            resultMap.put(point, result);
            return result;
        }

        public void sendFound(Point point) {
            if (square != null) {
                System.out.println("SEND ANSWER: " + point.toString());
                square.getAnswer(point);
                return;
            }
            printWriter.println(String.format("found %s", point.toString()));
            printWriter.flush();
        }
    }

    public static boolean isZero(double a) {
        return Math.abs(a) < EPS;
    }

    public static final int LOWER_POSITION = -5001;
    public static final int UPPER_POSITION = -LOWER_POSITION;
    public static final int LEFTER_POSITION = LOWER_POSITION;
    public static final int RIGHTER_POSITION = -LOWER_POSITION;
    public static double EPS = 1e-8;
    public static int n, m;
    public static double labyrinthAngle = 0;

    public static RequestPoint gotWall(Requester requester) {
        double l = LEFTER_POSITION, r = RIGHTER_POSITION, m1, m2;
        while (!isZero(r - l)) {
            m1 = l + (r - l) / 3;
            m2 = l + 2 * (r - l) / 3;
            Result result1 = requester.sendActivate(new RequestPoint(m1, LOWER_POSITION));
            Result result2 = requester.sendActivate(new RequestPoint(m2, LOWER_POSITION));
            if (result1.compareTo(result2) <= 0) {
                r = m2;
            } else {
                l = m1;
            }
        }
        double pointX = (l + r) / 2;
        double pointY;
        {
            Result distanceRequest = requester.sendActivate(new RequestPoint(pointX, LOWER_POSITION));
            pointY = distanceRequest.distance + LOWER_POSITION;
        }
        Result isHorizontalRequest = requester.sendActivate(new RequestPoint(pointX + 1, pointY));
        if (isHorizontalRequest.isOutside() && !isZero(isHorizontalRequest.distance)) {
            double anotherPointX = pointX;
            double anotherPointY = pointY + 0.1;
            l = anotherPointX;
            r = RIGHTER_POSITION;
            double m;
            while (!isZero(r - l)) {
                m = (l + r) / 2;
                Result request = requester.sendActivate(new RequestPoint(m, anotherPointY));
                if (request.isOutside()) {
                    r = m;
                } else {
                    l = m;
                }
            }
            anotherPointX = (l + r) / 2;
            l = LOWER_POSITION;
            r = anotherPointY;
            while (!isZero(r - l)) {
                m = (l + r) / 2;
                Result request = requester.sendActivate(new RequestPoint(anotherPointX, m));
                if (request.isOutside()) {
                    l = m;
                } else {
                    r = m;
                }
            }
            anotherPointY = (l + r) / 2;
            labyrinthAngle = Math.atan2(anotherPointY - pointY, anotherPointX - pointX);
        }
        return new RequestPoint(pointX, pointY);
    }

    public static RequestPoint makeRequested(Point point, RequestPoint start) {
        return start.add(new RequestPoint(point.dot(10).add(new Point(5, 5))).onAngle(labyrinthAngle));
    }

    public static boolean isCorrect(Point point) {
        return point.x >= 0 && point.x < Math.max(n, m) && point.y >= 0 && point.y < Math.max(n, m);
    }

    public static Point getTreasure(RequestPoint labyrinthStart, Requester requester) {
        PriorityQueue<ResultPair> queue = new PriorityQueue<>();
        HashSet<Point> points = new HashSet<>();
        queue.add(new ResultPair(new Point(0, 0), requester.sendActivate(makeRequested(new Point(0, 0), labyrinthStart))));
        while (!queue.isEmpty()) {
            ResultPair resultPair = queue.remove();
            Result result = resultPair.result;
            Point us = resultPair.point;
            if (result.resultType == Result.ResultType.INSIDE && result.distance <= 10.0 / 2 * Math.sqrt(2)) {
                // FOUND HEH!!!!
                queue.clear();
                points.clear();
                ArrayList<Point> startPoints = makeRequested(us, labyrinthStart).toPoint();
                points.addAll(startPoints);
                startPoints.stream()
                        .map(point -> new ResultPair(point, requester.sendActivate(new RequestPoint(point))))
                        .forEach(queue::add);
                while (!queue.isEmpty()) {
                    ResultPair realUsResult = queue.remove();
                    Point realUs = realUsResult.point;
                    if (realUsResult.result.distance < 1e-3) {
                        return realUs;
                    }
                    for (Point directionReal : directions) {
                        Point realFuture = realUs.add(directionReal);
                        if (points.contains(realFuture)) continue;
                        Result real = requester.sendActivate(new RequestPoint(realFuture));
                        if (!result.isOutside()) {
                            queue.add(new ResultPair(realFuture, real));
                            points.add(realFuture);
                        }
                    }
                }
            }
            for (Point direction : directions) {
                Point future = us.add(direction);
                if (!isCorrect(future)) continue;
                if (points.contains(future)) continue;
                result = requester.sendActivate(makeRequested(future, labyrinthStart));
                if (result.activeType == Result.ActiveType.IPHONE) {
                    makeTL();
                }
                if (!result.isOutside()) {
                    queue.add(new ResultPair(future, result));
                    points.add(future);
                }
            }
        }
        throw new IllegalStateException("Queue is empty. Something go wrong");
    }

    public static void makeTL() {
        int x = 1;
        while (x > 0) {
            x++;
        }
    }

    public static void main(String[] args) {
        FastReader fastReader = new FastReader();
        n = fastReader.nextInt();
        m = fastReader.nextInt();
        PrintWriter printWriter = new PrintWriter(System.out);
        Requester requester = new Requester(fastReader, printWriter);
        // requester.square = new Square(new Point(4000, 4000), new Point(50, 50), new Point(4499, 4499), 1, 0);
        // requester.square = new Square45(1, new Point(1, 1), new Point(1, 1));
        RequestPoint inside = gotWall(requester);
        Point treasure = getTreasure(inside, requester);
        requester.sendFound(treasure);
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