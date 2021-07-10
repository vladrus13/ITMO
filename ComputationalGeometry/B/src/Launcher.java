import java.io.*;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Launcher {

    public final static MathContext mathContext = new MathContext(150);
    public final static BigDecimal EPS = new BigDecimal("1e-30", mathContext);

    public interface Distanced {
        BigDecimal distance(Distanced a);
    }

    public static boolean isLess(BigDecimal a, BigDecimal b) {
        return a.add(EPS.divide(new BigDecimal(2), mathContext)).compareTo(b) < 0;
    }

    public static boolean isBigger(BigDecimal a, BigDecimal b) {
        return a.compareTo(b.add(EPS.divide(new BigDecimal(2), mathContext))) > 0;
    }

    public static boolean isEquals(BigDecimal a, BigDecimal b) {
        return isEquals(a, b, EPS);
    }

    public static boolean isEquals(BigDecimal a, BigDecimal b, BigDecimal eps) {
        return a.subtract(b).abs().compareTo(eps) < 0;
    }

    public static boolean isZero(BigDecimal a) {
        return isEquals(a, BigDecimal.ZERO);
    }

    public static class Line {
        public final BigDecimal a;
        public final BigDecimal b;
        public final BigDecimal c;

        public Line(BigDecimal a, BigDecimal b, BigDecimal c) {
            this.a = a;
            this.b = b;
            this.c = c;
        }

        private BigDecimal det(BigDecimal a, BigDecimal b, BigDecimal c, BigDecimal d) {
            return a.multiply(d).subtract(b.multiply(c));
        }

        public Point intersect(Line other) {
            BigDecimal z = det(a, b, other.a, other.b);
            if (z.abs().compareTo(EPS) < 0) {
                return null;
            }
            return new Point(
                    (det(c, b, other.c, other.b).negate().divide(z, mathContext)),
                    (det(a, c, other.a, other.c).negate().divide(z, mathContext)));
        }

        public boolean parallel(Line other) {
            return det(a, b, other.a, other.b).abs().compareTo(EPS) < 0;
        }

        public boolean equivalent(Line other) {
            return det(a, b, other.a, other.b).abs().compareTo(EPS) < 0 &&
                    det(a, c, other.a, other.c).abs().compareTo(EPS) < 0 &&
                    det(b, c, other.b, other.c).abs().compareTo(EPS) < 0;
        }

        public boolean contain(Point point) {
            return a.multiply(point.x).add(b.multiply(point.y).add(c)).abs().compareTo(EPS) < 0;
        }
    }

    public static class Point implements Distanced {
        public final BigDecimal x;
        public final BigDecimal y;

        public Point(BigDecimal x, BigDecimal y) {
            this.x = x;
            this.y = y;
        }

        public Point(FastReader fastReader) {
            this.x = new BigDecimal(fastReader.next(), mathContext);
            this.y = new BigDecimal(fastReader.next(), mathContext);
        }

        public Point minus(Point other) {
            return new Point(x.subtract(other.x), y.subtract(other.y));
        }

        public Point plus(Point other) {
            return new Point(x.add(other.x), y.add(other.y));
        }

        public BigDecimal sqr() {
            return x.multiply(x).add(y.multiply(y));
        }

        public BigDecimal distance(Point other) {
            return minus(other).sqr().sqrt(mathContext);
        }

        public BigDecimal dot(Point other) {
            return x.multiply(other.x).add(y.multiply(other.y));
        }

        public Point dot(BigDecimal a) {
            return new Point(x.multiply(a), y.multiply(a));
        }

        public BigDecimal distance(Points s) {
            return s.closest(this).distance(this);
        }

        public Point copy() {
            return new Point(x, y);
        }

        @Override
        public BigDecimal distance(Distanced a) {
            if (a instanceof Point) {
                return distance((Point) a);
            }
            if (a instanceof Points) {
                return distance((Points) a);
            }
            throw new IllegalStateException("BAN!");
        }
    }

    public static class Points implements Distanced {
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
                        if (a.x.compareTo(start.x) > 0 || a.x.compareTo(finish.x) < 0) return false;
                    }
                    if (isEquals(start.x, finish.x)) {
                        if (!isEquals(a.x, start.x)) return false;
                    }
                    if (isLess(start.y, finish.y)) {
                        if (isLess(a.y, start.y) || isBigger(a.y, finish.y)) return false;
                    }
                    if (isBigger(start.y, finish.y)) {
                        if (a.y.compareTo(start.y) > 0 || a.y.compareTo(finish.y) < 0) return false;
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
                    Point as = a.minus(start);
                    Point fa = finish.minus(start);
                    BigDecimal alpha = fa.sqr();
                    BigDecimal dotAlpha = fa.dot(as);
                    BigDecimal t;
                    if (isZero(alpha)) {
                        t = new BigDecimal("1e300");
                    } else {
                        t = dotAlpha.divide(alpha, mathContext);
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
                    BigDecimal toStart = a.distance(start);
                    BigDecimal toFinish = a.distance(finish);
                    return toStart.compareTo(toFinish) > 0 ? finish.copy() : start.copy();
                default:
                    throw new IllegalStateException("BAN!");
            }
        }

        @Override
        public BigDecimal distance(Distanced a) {
            if (a instanceof Point) {
                return distance((Point) a);
            }
            if (a instanceof Points) {
                return distance((Points) a);
            }
            throw new IllegalStateException("BAN!");
        }

        public BigDecimal distance(Point a) {
            return a.distance(this);
        }

        public BigDecimal distance(Points a) {
            Line thisLine = this.toLine();
            Line anotherLine = a.toLine();
            if (this.type == Type.STRAIGHT && a.type == Type.STRAIGHT) {
                if (thisLine.parallel(anotherLine)) {
                    return this.start.distance(a);
                } else {
                    return BigDecimal.ZERO;
                }
            }

            if (this.type == Type.STRAIGHT && a.type == Type.RAY) {
                Point inter = thisLine.intersect(anotherLine);
                if (inter != null && a.on(inter)) {
                    return BigDecimal.ZERO;
                }
                return a.start.distance(this);
            }
            if (this.type == Type.STRAIGHT && a.type == Type.SEGMENT) {
                if (thisLine.parallel(anotherLine)) {
                    return a.start.distance(this);
                }
                Point inter = thisLine.intersect(anotherLine);
                if (a.on(inter)) {
                    return BigDecimal.ZERO;
                }
                return a.start.distance(this).min(a.finish.distance(this));
            }
            if (this.type == Type.RAY && a.type == Type.STRAIGHT) {
                return a.distance(this);
            }
            if (this.type == Type.RAY && a.type == Type.RAY) {
                Point interact = thisLine.intersect(anotherLine);
                if (interact != null && this.on(interact) && a.on(interact)) {
                    return BigDecimal.ZERO;
                }
                BigDecimal toStart1 = this.closest(a.start).distance(a.start);
                BigDecimal toStart2 = a.closest(this.start).distance(this.start);
                return toStart1.min(toStart2);
            }
            if (this.type == Type.RAY && a.type == Type.SEGMENT) {
                Point inter = this.toLine().intersect(a.toLine());
                if (inter != null && this.on(inter) && a.on(inter)) {
                    return BigDecimal.ZERO;
                }
                return a.start.distance(this).min(a.finish.distance(this)).min(this.start.distance(a));
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
                    return BigDecimal.ZERO;
                }
                return a.start.distance(this).min(a.finish.distance(this)).min(start.distance(a)).min(finish.distance(a));
            }
            throw new IllegalStateException("BAN!");
        }

        public Line toLine() {
            return new Line(
                    finish.y.subtract(start.y),
                    start.x.subtract(finish.x),
                    (start.y.subtract(finish.y).multiply(finish.x))
                            .subtract(start.x.subtract(finish.x).multiply(finish.y)));
        }
    }

    public static void write(PrintWriter printWriter, BigDecimal answer) {
        printWriter.printf("%s\n", answer.setScale(20, RoundingMode.CEILING));
    }

    public static void solve(FastReader fastReader, PrintWriter printWriter) {
        Point a = new Point(fastReader);
        Point b = new Point(fastReader);
        Point c = new Point(fastReader);
        Point d = new Point(fastReader);
        ArrayList<Distanced> first = new ArrayList<>(Arrays.asList(
                a.copy(),
                new Points(a.copy(), b.copy(), Points.Type.SEGMENT),
                new Points(a.copy(), b.copy(), Points.Type.RAY),
                new Points(a.copy(), b.copy(), Points.Type.STRAIGHT)));
        ArrayList<Distanced> second = new ArrayList<>(Arrays.asList(
                c.copy(),
                new Points(c.copy(), d.copy(), Points.Type.SEGMENT),
                new Points(c.copy(), d.copy(), Points.Type.RAY),
                new Points(c.copy(), d.copy(), Points.Type.STRAIGHT)));
        for (Distanced it1 : first) {
            for (Distanced it2 : second) {
                write(printWriter, it1.distance(it2));
            }
        }
    }

    public static void stress() {
        // 16261
        for (int i = 1; i < 1000; i++) {
            if (i % 100 == 0) {
                System.out.println("=== " + i);
            }
            try {
                FastReader fastReader = new FastReader(Path.of("tests").resolve(i + ".in").toString());
                FastReader fastAnswer = new FastReader(Path.of("tests").resolve(i + ".out").toString());
                if (Path.of("tests").resolve(i + ".out").toFile().length() < 4) continue;
                Point a = new Point(fastReader);
                Point b = new Point(fastReader);
                Point c = new Point(fastReader);
                Point d = new Point(fastReader);
                ArrayList<Distanced> first = new ArrayList<>(Arrays.asList(
                        a.copy(),
                        new Points(a.copy(), b.copy(), Points.Type.SEGMENT),
                        new Points(a.copy(), b.copy(), Points.Type.RAY),
                        new Points(a.copy(), b.copy(), Points.Type.STRAIGHT)));
                ArrayList<Distanced> second = new ArrayList<>(Arrays.asList(
                        c.copy(),
                        new Points(c.copy(), d.copy(), Points.Type.SEGMENT),
                        new Points(c.copy(), d.copy(), Points.Type.RAY),
                        new Points(c.copy(), d.copy(), Points.Type.STRAIGHT)));
                boolean isIncorrect = false;
                boolean isNAN = false;
                ArrayList<String> usA = new ArrayList<>();
                ArrayList<String> theirA = new ArrayList<>();
                for (Distanced it1 : first) {
                    if (isNAN) break;
                    for (Distanced it2 : second) {
                        String fastAnswerS = fastAnswer.nextLine();
                        if (fastAnswerS.contains("nan")) {
                            isNAN = true;
                            break;
                        }
                        theirA.add(fastAnswerS);
                        BigDecimal their = new BigDecimal(fastAnswerS, mathContext);
                        BigDecimal us = it1.distance(it2);
                        usA.add(us.setScale(20, RoundingMode.CEILING).toString());
                        if (!isEquals(us, their, new BigDecimal("1e-9"))) {
                            isIncorrect = true;
                            System.out.println("Incorrect: " + i);
                        }
                    }
                }
                if (isIncorrect) {
                    try (BufferedWriter bufferedWriter = Files.newBufferedWriter(Path.of("incorrect").resolve(i + ".test"))) {
                        bufferedWriter.write("Test:\n");
                        for (Point point : Arrays.asList(a, b, c, d)) {
                            bufferedWriter.write(String.format("%s %s\n",
                                    point.x.setScale(20, RoundingMode.CEILING), point.y.setScale(20, RoundingMode.CEILING)));
                        }
                        bufferedWriter.write("\nRight:\n");
                        for (String s : theirA) {
                            bufferedWriter.write(s);
                            bufferedWriter.write("\n");
                        }
                        bufferedWriter.write("\nUs:\n");
                        for (String s : usA) {
                            bufferedWriter.write(s);
                            bufferedWriter.write("\n");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        FastReader fastReader = new FastReader();
        PrintWriter printWriter = new PrintWriter(System.out);
        solve(fastReader, printWriter);
        // stress();
        printWriter.close();
    }

    static class FastReader {
        BufferedReader br;
        StringTokenizer st;

        public FastReader() {
            br = new BufferedReader(new InputStreamReader(System.in));
        }

        public FastReader(String s) throws FileNotFoundException {
            br = new BufferedReader(new FileReader(new File(s)));
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