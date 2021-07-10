import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Launcher {

    public static int n;
    public static int dimension;
    public static PrintWriter printWriter;
    public static final int SQUARE_LAUNCH = -1;

    public enum CompareResult {
        GREATER, LESS, EQUALS, NON
    }

    public static class Tester {

        private static final ArrayList<Integer> dimensions = new ArrayList<>();
        private static final ArrayList<ArrayList<Point>> tests = new ArrayList<>();

        public static int[] get(int size, Random random) {
            int[] result = new int[size];
            for (int i = 0; i < size; i++) {
                result[i] = random.nextInt();
            }
            return result;
        }

        public static int[] get(int size, long randomSeed) {
            return get(size, new Random(randomSeed));
        }

        public static int[] get(int size) {
            return get(size, System.nanoTime());
        }

        public static void test1() {
            dimensions.add(2);
            tests.add(new ArrayList<>(Arrays.asList(
                    new Point(new int[]{1, 0}, 0),
                    new Point(new int[]{0, 0}, 1),
                    new Point(new int[]{1, 1}, 2),
                    new Point(new int[]{0, 1}, 3)
            )));
        }

        public static void test2() {
            dimensions.add(2);
            tests.add(new ArrayList<>(Arrays.asList(
                    new Point(new int[]{1, 0}, 0),
                    new Point(new int[]{0, 1}, 1),
                    new Point(new int[]{0, 0}, 2),
                    new Point(new int[]{1, 1}, 3),
                    new Point(new int[]{0, 1}, 4),
                    new Point(new int[]{1, 1}, 5)
            )));
        }

        public static void test3() {
            dimensions.add(3);
            tests.add(new ArrayList<>(Arrays.asList(
                    new Point(new int[]{1, 1, 3}, 0),
                    new Point(new int[]{2, 1, 3}, 1),
                    new Point(new int[]{1, 2, 1}, 2),
                    new Point(new int[]{3, 2, 3}, 3),
                    new Point(new int[]{2, 3, 1}, 4),
                    new Point(new int[]{3, 3, 2}, 5)
            )));
        }

        public static void test4() {
            dimensions.add(4);
            tests.add(new ArrayList<>(Arrays.asList(
                    new Point(new int[]{4, 1, 2, 3}, 0),
                    new Point(new int[]{5, 2, 4, 3}, 1),
                    new Point(new int[]{6, 3, 5, 3}, 2),
                    new Point(new int[]{7, 4, 6, 1}, 3)
            )));
        }

        public static void test5() {
            dimensions.add(2);
            Random random = new Random(124);
            ArrayList<Point> points = new ArrayList<>();
            for (int i = 0; i < 20; i++) {
                points.add(new Point(get(2, random), i));
            }
            tests.add(points);
        }

        public static void test6() {
            dimensions.add(3);
            Random random = new Random(3496739);
            ArrayList<Point> points = new ArrayList<>();
            for (int i = 0; i < 20; i++) {
                points.add(new Point(get(3, random), i));
            }
            tests.add(points);
        }

        public static void test7() {
            dimensions.add(2);
            Random random = new Random(234522);
            ArrayList<Point> points = new ArrayList<>();
            for (int i = 0; i < 20; i++) {
                points.add(new Point(get(2, random), i));
            }
            tests.add(points);
        }

        public static void test8() {
            dimensions.add(2);
            Random random = new Random(134356);
            ArrayList<Point> points = new ArrayList<>();
            for (int i = 0; i < 20; i++) {
                points.add(new Point(get(2, random), i));
            }
            tests.add(points);
        }

        public static void test9() {
            dimensions.add(3);
            Random random = new Random(35656);
            ArrayList<Point> points = new ArrayList<>();
            for (int i = 0; i < 20; i++) {
                points.add(new Point(get(3, random), i));
            }
            tests.add(points);
        }

        public static void test10() {
            dimensions.add(3);
            Random random = new Random(3474633);
            ArrayList<Point> points = new ArrayList<>();
            for (int i = 0; i < 20; i++) {
                points.add(new Point(get(3, random), i));
            }
            tests.add(points);
        }

        public static void test11() {
            dimensions.add(4);
            Random random = new Random(357424);
            ArrayList<Point> points = new ArrayList<>();
            for (int i = 0; i < 20; i++) {
                points.add(new Point(get(4, random), i));
            }
            tests.add(points);
        }

        public static void test12() {
            dimensions.add(4);
            Random random = new Random(279441);
            ArrayList<Point> points = new ArrayList<>();
            for (int i = 0; i < 20; i++) {
                points.add(new Point(get(4, random), i));
            }
            tests.add(points);
        }

        public static void test13() {
            dimensions.add(3);
            ArrayList<Point> points = new ArrayList<>();
            Random random = new Random(638463);
            for (int i = 0; i < 100; i++) {
                points.add(new Point(get(3, random), i));
            }
            tests.add(points);
        }

        public static void test14() {
            dimensions.add(3);
            ArrayList<Point> points = new ArrayList<>();
            Random random = new Random(349653749);
            for (int i = 0; i < 1000; i++) {
                points.add(new Point(get(3, random), i));
            }
            tests.add(points);
        }

        public static void test15() {
            dimensions.add(3);
            ArrayList<Point> points = new ArrayList<>();
            Random random = new Random(39464535);
            for (int i = 0; i < 2000; i++) {
                points.add(new Point(get(3, random), i));
            }
            tests.add(points);
        }

        public static void test16() {
            dimensions.add(3);
            ArrayList<Point> points = new ArrayList<>();
            Random random = new Random(34854350);
            for (int i = 0; i < 3000; i++) {
                points.add(new Point(get(3, random), i));
            }
            tests.add(points);
        }

        public static void test17() {
            dimensions.add(4);
            ArrayList<Point> points = new ArrayList<>();
            Random random = new Random(34854350);
            for (int i = 0; i < 13; i++) {
                points.add(new Point(get(4, random), i));
            }
            tests.add(points);
        }

        public static void test18() {
            dimensions.add(3);
            Random random = new Random(3946485);
            ArrayList<Point> points = new ArrayList<>();
            for (int i = 0; i < 4000; i++) {
                points.add(new Point(get(3, random), i));
            }
            tests.add(points);
        }

        public static void test19() {
            dimensions.add(4);
            ArrayList<Point> points = new ArrayList<>();
            Random random = new Random(0);
            for (int i = 0; i < 10000; i++) {
                points.add(new Point(get(4, random), i));
            }
            tests.add(points);
        }

        public static void test20() {
            dimensions.add(2);
            ArrayList<Point> points = new ArrayList<>();
            Random random = new Random(1395);
            for (int i = 0; i < 100000; i++) {
                points.add(new Point(get(2, random), i));
            }
            tests.add(points);
        }

        public static void test21() {
            dimensions.add(3);
            ArrayList<Point> points = new ArrayList<>();
            Random random = new Random(2475480);
            for (int i = 0; i < 100000; i++) {
                points.add(new Point(get(3, random), i));
            }
            tests.add(points);
        }

        public static void test22() {
            dimensions.add(4);
            ArrayList<Point> points = new ArrayList<>();
            Random random = new Random(3478406);
            for (int i = 0; i < 100000; i++) {
                points.add(new Point(get(4, random), i));
            }
            tests.add(points);
        }

        public static void initStart() {
            test1();
            test2();
            test3();
            test4();
        }

        public static void initRandom() {
            test5();
            test6();
            test7();
            test8();
            test9();/*
            test10();
            test11();
            test12();
            test13();
            test14();
            test15();
            test16();
            test17();
            test18();
            test19();
            test20();
            test21();
            test22();*/
        }

        public static void test(PrintWriter out, Class<?> clazz) {
            dimensions.clear();
            tests.clear();
            initStart();
            initRandom();
            for (int i = 0; i < dimensions.size(); i++) {
                long current = System.currentTimeMillis();
                write(out, clazz, dimensions.get(i), tests.get(i));
                printWriter.println(String.format("Test: %d. Time: %d", i, (System.currentTimeMillis() - current)));
            }
        }

        public static void test(PrintWriter out, Class<?> clazz1, Class<?> clazz2) {
            initStart();
            initRandom();
            for (int i = 0; i < dimensions.size(); i++) {
                compare(out, clazz1, clazz2, dimensions.get(i), tests.get(i));
            }
        }

        public static void ready(Class<?> clazz, int dimension, ArrayList<Point> points) {
            for (Point point : points) {
                point.rank = 0;
            }
            Launcher.dimension = dimension;
            Sorter sorter;
            try {
                Constructor<?> constructor = clazz.getConstructor(ArrayList.class);
                sorter = (Sorter) constructor.newInstance(points);
            } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
                return;
            }
            sorter.sort();
        }

        public static void compare(PrintWriter out, Class<?> clazz1, Class<?> clazz2, int dimension, ArrayList<Point> points) {
            ready(clazz1, dimension, points);
            printWriter.println();
            ArrayList<Integer> first = points.stream().map(point -> point.rank).collect(Collectors.toCollection(ArrayList::new));
            ready(clazz2, dimension, points);
            ArrayList<Integer> second = points.stream().map(point -> point.rank).collect(Collectors.toCollection(ArrayList::new));
            if (!first.equals(second)) {
                out.println("=".repeat(40));
                out.println("Test:\n");
                for (Point point : points) {
                    point.println(out);
                }
                out.println("=".repeat(40));
                for (Integer i : first) {
                    out.printf("%d ", i);
                }
                out.println("=".repeat(40));
                for (Integer i : second) {
                    out.printf("%d ", i);
                }
                out.println("=".repeat(40));

            }
        }

        public static void write(PrintWriter out, Class<?> clazz, int dimension, ArrayList<Point> points) {
            ready(clazz, dimension, points);
            /*
            out.println("=".repeat(40));
            out.println("Test:\n");
            for (Point point : points) {
                point.println(out);
            }
            out.println("=".repeat(40));*/
            for (Point point : points) {
                out.printf("%d ", point.rank);
            }
            out.println();
        }
    }

    public static class PointNumberComparator implements Comparator<Point> {
        @Override
        public int compare(Point t0, Point t1) {
            return Integer.compare(t0.number, t1.number);
        }
    }

    public static class PointCriteriasComparator implements Comparator<Point> {

        @Override
        public int compare(Point t0, Point t1) {
            CompareResult result = compareTo(t0, t1);
            if (result == CompareResult.LESS) {
                return -1;
            }
            if (result == CompareResult.GREATER) {
                return 1;
            }
            return 0;
        }

        public CompareResult compareTo(Point t0, Point t1) {
            if (t1.coordinates.length != t0.coordinates.length) {
                throw new IllegalStateException("Can't compare this types");
            }
            boolean isLess = false;
            boolean isGreater = false;
            for (int i = 0; i < t1.coordinates.length; i++) {
                int comparing = Integer.compare(t0.coordinates[i], t1.coordinates[i]);
                if (comparing > 0) {
                    isGreater = true;
                } else {
                    if (comparing < 0) {
                        isLess = true;
                    }
                }
            }
            if (isLess && isGreater) {
                return CompareResult.NON;
            }
            if (isLess) {
                return CompareResult.LESS;
            }
            if (isGreater) {
                return CompareResult.GREATER;
            }
            return CompareResult.EQUALS;
        }
    }

    public static class PointFullyCriteriasComparator implements Comparator<Point> {

        @Override
        public int compare(Point t0, Point t1) {
            if (t1.coordinates.length != t0.coordinates.length) {
                throw new IllegalStateException("Can't compare this types");
            }
            for (int i = 0; i < t1.coordinates.length; i++) {
                int comparing = Integer.compare(t0.coordinates[i], t1.coordinates[i]);
                if (comparing != 0) {
                    return comparing;
                }
            }
            return 0;
        }
    }

    public static class PointCriteriaComparator {
        public int compare(Point t1, Point t2, int position) {
            return Integer.compare(t1.coordinates[position], t2.coordinates[position]);
        }

        public static Comparator<Point> getComparator(int position) {
            return Comparator.comparingInt(point -> point.coordinates[position]);
        }
    }

    public static class Point {
        public int[] coordinates;
        public int rank = 0;
        public int number;

        public Point(int[] coordinates, int number) {
            this.coordinates = coordinates;
            this.number = number;
        }

        public Point(FastReader fastReader, int number) {
            coordinates = new int[dimension];
            for (int i = 0; i < dimension; i++) {
                coordinates[i] = fastReader.nextInt();
            }
            this.number = number;
        }

        public void print(PrintWriter printWriter) {
            for (int i = 0; i < dimension; i++) {
                printWriter.printf("%d ", coordinates[i]);
            }
        }

        public void println(PrintWriter printWriter) {
            for (int i = 0; i < dimension; i++) {
                printWriter.printf("%d ", coordinates[i]);
            }
            printWriter.println();
        }
    }

    public static abstract class Sorter {

        public ArrayList<Point> points;

        public Sorter(ArrayList<Point> points) {
            this.points = new ArrayList<>(points);
        }

        public abstract void sort();
    }

    public static class SquareSorter extends Sorter {

        public SquareSorter(ArrayList<Point> points) {
            super(points);
        }

        @Override
        public void sort() {
            PointCriteriasComparator pointCriteriasComparator = new PointCriteriasComparator();
            boolean[] kicked = new boolean[points.size()];
            points.sort(new PointFullyCriteriasComparator());
            Arrays.fill(kicked, false);
            int elapsed = points.size();
            int currentRank = 0;
            int minima = 0;
            while (elapsed > 0) {
                ArrayList<Integer> domina = new ArrayList<>();
                for (int i = minima; i < points.size(); i++) {
                    if (kicked[i]) continue;
                    Point current = points.get(i);
                    boolean isDominant = false;
                    for (int j = minima; j < i; j++) {
                        Point point = points.get(j);
                        if (kicked[j] || current == point) continue;
                        if (pointCriteriasComparator.compareTo(current, point) == CompareResult.GREATER) {
                            isDominant = true;
                            break;
                        }
                    }
                    if (!isDominant) {
                        current.rank = currentRank;
                        domina.add(i);
                        elapsed--;
                    }
                }
                for (Integer dom : domina) {
                    kicked[dom] = true;
                    if (minima == dom) {
                        minima++;
                    }
                }
                currentRank++;
            }
            points.sort(new PointNumberComparator());
        }
    }

    public static class Valuer {
        public static boolean equals(ArrayList<Point> points, ArrayList<Integer> indexies, int from, int to, int dim) {
            for (int i = from + 1; i < to; i++) {
                if (points.get(indexies.get(i)).coordinates[dim] != points.get(indexies.get(i - 1)).coordinates[dim]) {
                    return false;
                }
            }
            return true;
        }

        public static boolean dominatedByDim(ArrayList<Point> points, ArrayList<Integer> indexies, int a, int b, int dim) {
            Point aa = points.get(indexies.get(a));
            Point bb = points.get(indexies.get(b));
            for (int i = 0; i <= dim; i++) {
                if (aa.coordinates[i] > bb.coordinates[i]) {
                    return false;
                }
            }
            return true;
        }

        public static Point suite(ArrayList<Point> points, ArrayList<Integer> indexies, int from, int to, Comparator<Point> comparator) {
            Point best = points.get(indexies.get(from));
            for (int i = from + 1; i < to; i++) {
                if (comparator.compare(best, points.get(indexies.get(i))) < 0) {
                    best = points.get(indexies.get(i));
                }
            }
            return best;
        }
    }

    public static abstract class QueryClass {

        protected final int[] real;

        public QueryClass(ArrayList<Point> points, ArrayList<Integer> indexies, int from, int to) {
            real = new int[points.size()];
            int it = 0;
            for (int i = from; i < to; i++) {
                real[it] = points.get(indexies.get(i)).coordinates[1];
                it++;
            }
        }

        public abstract int index(int k);

        public abstract void set(int k, int value);

        public abstract int get(int k);
    }

    public static class Fenwick extends QueryClass {

        private final int size;
        private final int[] data;

        public Fenwick(ArrayList<Point> points, ArrayList<Integer> indexies, int from, int to) {
            super(points, indexies, from, to);
            // Launcher.print("fenwick-init", String.format("%d %d", from, to));
            Arrays.sort(real, 0, to - from);
            int last = 0;
            for (int i = 1; i < to - from; i++) {
                if (real[i] != real[last]) {
                    last++;
                    real[last] = real[i];
                }
            }
            size = last + 1;
            data = new int[points.size()];
            Arrays.fill(data, 0, size, -1);
        }

        @Override
        public int index(int k) {
            // Launcher.print("fenwick-index", String.format("%d", k));
            int l = -1, r = size, m;
            while (1 < r - l) {
                m = (l + r) / 2;
                if (real[m] > k) {
                    r = m;
                } else {
                    l = m;
                }
            }
            // Launcher.printOut("fenwick-index", String.format("%d", l));
            return l;
        }

        @Override
        public void set(int k, int value) {
            // Launcher.print("fenwick-set", String.format("%d %d", k, value));
            int index = index(k);
            while (index < size) {
                data[index] = Math.max(data[index], value);
                index |= index + 1;
            }
        }

        @Override
        public int get(int k) {
            // Launcher.print("fenwick-get", String.format("%d", k));
            int index = index(k);
            if (index < 0 || index >= size) {
                return -1;
                // throw new IllegalStateException("Can't be");
            }
            int minima = -1;
            while (index >= 0) {
                minima = Math.max(minima, data[index]);
                index = ((index + 1) & index) - 1;
            }
            // Launcher.printOut("fenwick-get", String.format("%d", minima));
            return minima;
        }
    }

    public static class SplitResult {
        public final int less;
        public final int equals;

        public SplitResult(int less, int equals) {
            this.less = less;
            this.equals = equals;
        }
    }

    public static class FastSorter extends Sorter {

        public final int n;
        public QueryClass queryClass;
        public ArrayList<Integer> indexies;
        public ArrayList<Integer> comparing;
        public int[] merger;

        public FastSorter(ArrayList<Point> points) {
            super(points);
            n = points.size();
            merger = new int[n];
        }

        private void update(int first, int second) {
            points.get(first).rank =
                    !Objects.equals(comparing.get(first), comparing.get(second)) ? Math.max(points.get(first).rank, points.get(second).rank + 1) : points.get(second).rank;
        }

        private void pop(Iterator<Integer> iterator, int sum) {
            if (iterator.hasNext()) {
                Integer v = iterator.next();
                if (points.get(v).rank <= sum) {
                    iterator.remove();
                    pop(iterator, sum);
                }
            }
        }

        private void smolHelperA(int from, int to) {
            PointCriteriasComparator pointCriteriasComparator = new PointCriteriasComparator();
            Comparator<Integer> pointCriterias = (t0, t1) -> -pointCriteriasComparator.compare(points.get(t0), points.get(t1));
            TreeSet<Integer> set = new TreeSet<>(pointCriterias);
            for (int i = from; i < to; i++) {
                int kuru = indexies.get(i);
                Iterator<Integer> it = set.tailSet(kuru, false).iterator();
                if (it.hasNext()) {
                    update(kuru, it.next());
                }
                pop(set.headSet(kuru, true).descendingIterator(), points.get(kuru).rank);
                set.add(kuru);
            }
        }

        private void smolHelperB(int fromX, int toX, int fromY, int toY) {
            PointCriteriasComparator pointCriteriasComparator = new PointCriteriasComparator();
            Comparator<Integer> pointCriterias = (t0, t1) -> -pointCriteriasComparator.compare(points.get(t0), points.get(t1));
            TreeSet<Integer> set = new TreeSet<>(pointCriterias);
            int it = fromX;
            for (int il = fromY; il < toY; il++) {
                int current = indexies.get(il);
                while (it < toX && indexies.get(il) > indexies.get(it)) {
                    int currX = indexies.get(it);
                    Iterator<Integer> iterator = set.tailSet(currX, true).iterator();
                    if (!iterator.hasNext() || points.get(iterator.next()).rank < points.get(currX).rank) {
                        pop(set.headSet(currX, true).descendingIterator(), points.get(currX).rank);
                        set.add(currX);
                    }
                    it++;
                }
                Iterator<Integer> iterator = set.tailSet(current, true).iterator();
                if (iterator.hasNext()) {
                    update(current, iterator.next());
                }
            }
        }

        private SplitResult split(int from, int to, int dim, Point median) {
            // Launcher.print("split", String.format("%d %d %d %d", from, to, dim, median.coordinates[dim]));
            int less = 0;
            int equal = 0;
            Comparator<Point> comparator = PointCriteriaComparator.getComparator(dim);
            for (int i = from; i < to; i++) {
                int comp = comparator.compare(points.get(indexies.get(i)), median);
                if (comp < 0) {
                    less++;
                }
                if (comp == 0) {
                    equal++;
                }
            }
            int lessIt = 0;
            int equalsIt = less;
            int greaterIt = less + equal;
            // printArray(indexies);
            // printArray(merger);
            for (int i = from; i < to; i++) {
                int comp = comparator.compare(points.get(indexies.get(i)), median);
                if (comp < 0) {
                    merger[lessIt] = indexies.get(i);
                    lessIt++;
                    continue;
                }
                if (comp == 0) {
                    merger[equalsIt] = indexies.get(i);
                    equalsIt++;
                    continue;
                }
                merger[greaterIt] = indexies.get(i);
                greaterIt++;
            }
            // printArray(merger);
            // printArray(indexies);
            int it = 0;
            for (int i = from; i < to; i++) {
                indexies.set(i, merger[it]);
                it++;
            }
            // printArray(indexies);
            return new SplitResult(less, equal);
        }

        public Point getMiddle(int from, int to, int dim) {
            // Launcher.print("getMiddle", String.format("%d %d %d", from, to, dim));
            // printArray(merger);
            // printArray(indexies);
            int median = (from + to) / 2;
            to--;
            while (from <= to) {
                int it = points.get(merger[(from + to) / 2]).coordinates[dim];
                int start = from;
                int finish = to;
                while (start <= finish) {
                    while (points.get(merger[start]).coordinates[dim] < it) start++;
                    while (points.get(merger[finish]).coordinates[dim] > it) finish--;
                    if (start <= finish) {
                        int temp = merger[start];
                        merger[start] = merger[finish];
                        merger[finish] = temp;
                        start++;
                        finish--;
                    }
                }
                if (median <= finish) {
                    to = finish;
                } else {
                    if (median >= start) {
                        from = start;
                    } else {
                        // printOut("getMiddle", String.format("%d", points.get(merger[median]).coordinates[dim]));
                        return points.get(merger[median]);
                    }
                }
            }
            return points.get(merger[from]);
        }

        public void merge(int from, int m, int to) {
            // Launcher.print("merge", String.format("%d %d %d", from, m, to));
            int it1 = from, it2 = m;
            for (int i = from; i < to; i++) {
                if (it1 == m || it2 < to && comparing.get(indexies.get(it2)) < comparing.get(indexies.get(it1))) {
                    merger[i] = indexies.get(it2);
                    it2++;
                } else {
                    merger[i] = indexies.get(it1);
                    it1++;
                }
            }
            // printArray(indexies);
            for (int i = from; i < to; i++) {
                indexies.set(i, merger[i]);
            }
            // printArray(indexies);
            // printOut("merge", "end");
        }

        private void helperA(int from, int to, int k) {
            // Launcher.print("helperA", String.format("%d %d %d", from, to, k));
            if (to - from == 2) {
                if (Valuer.dominatedByDim(points, indexies, from, from + 1, k)) {
                    update(indexies.get(from + 1), indexies.get(from));
                }
            } else {
                if (to - from < SQUARE_LAUNCH) {
                    ArrayList<Point> newPoints = IntStream
                            .range(from, to)
                            .mapToObj(i -> points.get(indexies.get(i)))
                            .collect(Collectors.toCollection(ArrayList::new));
                    SquareSorter squareSorter = new SquareSorter(newPoints);
                    squareSorter.sort();
                    return;
                }
                if (k == 1) {
                    smolHelperA(from, to);
                    return;
                }
                if (Valuer.equals(points, indexies, from, to, k)) {
                    helperA(from, to, k - 1);
                } else {
                    for (int i = from; i < to; i++) {
                        merger[i] = indexies.get(i);
                    }
                    Point median = getMiddle(from, to, k);
                    // printArray(indexies);
                    SplitResult splitResult = split(from, to, k, median);
                    int middleX = from + splitResult.less;
                    int middleY = middleX + splitResult.equals;
                    helperA(from, middleX, k);
                    helperB(from, middleX, middleX, middleY, k - 1);
                    helperA(middleX, middleY, k - 1);
                    merge(from, middleX, middleY);
                    helperB(from, middleY, middleY, to, k - 1);
                    helperA(middleY, to, k);
                    merge(from, middleY, to);
                }
            }
        }

        private void helperB(int fromX, int toX, int fromY, int toY, int k) {
            // Launcher.print("helperB", String.format("%d %d %d %d %d", fromX, toX, fromY, toY, k));
            if (toX - fromX == 0 || toY - fromY == 0) {
                return;
            }
            if (toX - fromX == 1) {
                for (int i = fromY; i < toY; ++i) {
                    if (Valuer.dominatedByDim(points, indexies, fromX, i, k)) {
                        update(indexies.get(i), indexies.get(fromX));
                    }
                }
            } else if (toY - fromY == 1) {
                for (int i = fromX; i < toX; ++i) {
                    if (Valuer.dominatedByDim(points, indexies, i, fromY, k)) {
                        update(indexies.get(fromY), indexies.get(i));
                    }
                }
            } else if (k == 1) {
                smolHelperB(fromX, toX, fromY, toY);
            } else {
                if (Valuer.suite(points, indexies, fromX, toX, PointCriteriaComparator.getComparator(k)).coordinates[k]
                        <= Valuer.suite(points, indexies, fromY, toY, PointCriteriaComparator.getComparator(k).reversed()).coordinates[k]) {
                    helperB(fromX, toX, fromY, toY, k - 1);
                } else {
                    int it;
                    it = fromX;
                    for (int i = 0; i < toX - fromX; i++) {
                        merger[i] = indexies.get(it);
                        it++;
                    }
                    it = fromY;
                    for (int i = toX - fromX; i < toX - fromX + toY - fromY; i++) {
                        merger[i] = indexies.get(it);
                        it++;
                    }
                    Point median = getMiddle(0, toX - fromX + toY - fromY, k);
                    SplitResult splitResult;
                    splitResult = split(fromX, toX, k, median);
                    int leftMiddleX = fromX + splitResult.less;
                    int leftMiddleRight = leftMiddleX + splitResult.equals;
                    splitResult = split(fromY, toY, k, median);
                    int leftMiddleY = fromY + splitResult.less;
                    int leftMiddleLeft = leftMiddleY + splitResult.equals;
                    helperB(fromX, leftMiddleX, fromY, leftMiddleY, k);
                    helperB(fromX, leftMiddleX, leftMiddleY, leftMiddleLeft, k - 1);
                    helperB(leftMiddleX, leftMiddleRight, leftMiddleY, leftMiddleLeft, k - 1);
                    merge(fromX, leftMiddleX, leftMiddleRight);
                    merge(fromY, leftMiddleY, leftMiddleLeft);
                    helperB(fromX, leftMiddleRight, leftMiddleLeft, toY, k - 1);
                    helperB(leftMiddleRight, toX, leftMiddleLeft, toY, k);
                    merge(fromX, leftMiddleRight, toX);
                    merge(fromY, leftMiddleLeft, toY);
                }
            }
        }

        @Override
        public void sort() {
            ArrayList<Integer> indexies = new ArrayList<>();
            ArrayList<Integer> comparing = new ArrayList<>();
            ArrayList<Point> copy = new ArrayList<>(points);
            copy.sort(new PointFullyCriteriasComparator());
            for (Point point : copy) {
                indexies.add(point.number);
            }
            for (int i = 0; i < points.size(); i++) {
                comparing.add(0);
            }
            int current = 0;
            comparing.set(indexies.get(0), current);
            PointCriteriasComparator pointCriteriasComparator = new PointCriteriasComparator();
            for (int i = 1; i < indexies.size(); i++) {
                if (pointCriteriasComparator.compareTo(points.get(indexies.get(i)), points.get(indexies.get(i - 1))) != CompareResult.EQUALS) {
                    current++;
                }
                comparing.set(indexies.get(i), current);
            }
            sortPrint(points, indexies, comparing);
            sort(indexies, comparing);
        }

        private void sort(ArrayList<Integer> indexies, ArrayList<Integer> comparing) {
            this.indexies = indexies;
            this.comparing = comparing;
            helperA(0, n, dimension - 1);
        }
    }

    public static void realSolve(FastReader fastReader, PrintWriter printWriter) {
        n = fastReader.nextInt();
        dimension = fastReader.nextInt();
        ArrayList<Point> points = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            points.add(new Point(fastReader, i));
        }
        Sorter sorter = new SquareSorter(points);
        sorter.sort();
        for (Point point : points) {
            printWriter.printf("%d ", point.rank);
        }
    }

    public static void test(PrintWriter printWriter, Class<?> sorterClass) {
        Tester.test(printWriter, sorterClass);
    }

    public static void sortPrint(ArrayList<Point> points, ArrayList<Integer> indexies, ArrayList<Integer> comparing) {
        if (!isPrint()) return;
        int x = 0;
        try {
            Files.createDirectories(Path.of("tests"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (true) {
            x++;
            Path path = Path.of("tests/" + x + ".test");
            if (Files.exists(path)) continue;
            try {
                Files.createFile(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
            try (PrintWriter printWriter = new PrintWriter("tests/" + x + ".test")) {
                for (Point point : points) {
                    point.println(printWriter);
                }
                printWriter.println("===".repeat(3));
                for (Integer i : indexies) {
                    points.get(i).println(printWriter);
                }
                printWriter.println("===".repeat(3));
                for (Integer i : comparing) {
                    printWriter.printf("%d ", i);
                }
                printWriter.println();
                printWriter.println("===".repeat(3));
                for (Integer i : indexies) {
                    printWriter.printf("%d ", comparing.get(i));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            break;
        }
    }

    public static void test(PrintWriter printWriter, Class<?> sorterClass1, Class<?> sorterClass2) {
        Tester.test(printWriter, sorterClass1, sorterClass2);
    }

    public static boolean isPrint() {
        return false;
    }

    public static void print(String name, String parameters) {
        if (!isPrint()) return;
        printWriter.println("=== Calling method: " + name + " with parameters: " + parameters);
    }

    public static void printOut(String name, String output) {
        if (!isPrint()) return;
        printWriter.println("=== Calling method: " + name + " with output: " + output);
    }

    public static void printArray(int[] array) {
        if (!isPrint()) return;
        printWriter.print("Array: ");
        for (int j : array) {
            printWriter.printf("%d ", j);
        }
        printWriter.println();
    }

    public static void printArray(ArrayList<Integer> array) {
        if (!isPrint()) return;
        printWriter.print("Array: ");
        for (Integer integer : array) {
            printWriter.printf("%d ", integer);
        }
        printWriter.println();
    }

    public static void main(String[] args) throws FileNotFoundException {
        FastReader fastReader;
        if (args == null || args.length == 0) {
            fastReader = new FastReader("sorting.in");
            printWriter = new PrintWriter("sorting.out");
            realSolve(fastReader, printWriter);
            printWriter.close();
        } else {
            for (String s : args) {
                printWriter = new PrintWriter(s + ".out");
                if (s.equals("Fast")) {
                    test(printWriter, FastSorter.class);
                }
                if (s.equals("Sq")) {
                    test(printWriter, SquareSorter.class);
                }
                if (s.equals("Brain")) {
                    test(printWriter, BrainWash.class);
                }
                if (s.equals("heh")) {
                    test(printWriter, FastSorter.class, BrainWash.class);
                }
                printWriter.close();
            }
        }
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