import java.io.*;
import java.util.*;

public class Solve {

    public static String parsed;
    public static int index = 0;

    public static char getSymbol() {
        return parsed.length() == index ? '#' : parsed.charAt(index);
    }

    public static void next() {
        index++;
    }

    public static long pow(int a, int b) {
        long result = 1;
        for (int i = 0; i < b; i++) {
            result *= a;
        }
        return result;
    }

    public static class Pair {
        public int a;
        public int b;

        public Pair(int a, int b) {
            this.a = a;
            this.b = b;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Pair pair = (Pair) o;
            return a == pair.a && b == pair.b;
        }

        @Override
        public int hashCode() {
            return Objects.hash(a, b);
        }
    }

    public static class Monomial {

        public final boolean isX;
        public final boolean isY;
        public final int koff;
        public final int powerX;
        public final int powerY;

        public Monomial() {
            boolean isMinus = false;
            if (getSymbol() == '-') {
                isMinus = true;
                next();
            }
            if (getSymbol() == '+') {
                next();
            }
            if (Character.isDigit(getSymbol())) {
                koff = Integer.parseInt("" + getSymbol()) * (isMinus ? -1 : 1);
                next();
            } else {
                koff = (isMinus ? -1 : 1);
            }
            if (getSymbol() == 'x') {
                isX = true;
                next();
                if (getSymbol() == '^') {
                    next();
                    powerX = Integer.parseInt("" + getSymbol());
                    next();
                } else {
                    powerX = 1;
                }
            } else {
                isX = false;
                powerX = 1;
            }
            if (getSymbol() == 'y') {
                isY = true;
                next();
                if (getSymbol() == '^') {
                    next();
                    powerY = Integer.parseInt("" + getSymbol());
                    next();
                } else {
                    powerY = 1;
                }
            } else {
                isY = false;
                powerY = 1;
            }
        }

        public long get(int x, int y) {
            return koff * (isX ? (pow(x, powerX)) : 1) * (isY ? (pow(y, powerY)) : 1);
        }
    }

    public static class Polynomial {
        public int isMinima = 0;
        public int isMaxima = 0;
        public boolean isFlat = false;
        public ArrayList<Monomial> monomialArrayList;
        public Map<Pair, Long> values = new HashMap<>();

        public Polynomial() {
            monomialArrayList = new ArrayList<>();
            while (parsed.length() > index) {
                monomialArrayList.add(new Monomial());
            }
        }

        public Long get(int x, int y) {
            if (x > 10 || x < -10 || y > 10 || y < -10) {
                return null;
            }
            Pair pair = new Pair(x, y);
            if (!values.containsKey(pair)) {
                long accum = 0;
                for (Monomial monomial : monomialArrayList) {
                    accum += monomial.get(x, y);
                }
                // System.out.printf("x = %d, y = %d, result = %d%n", x, y, accum);
                values.put(pair, accum);
            }
            return values.get(pair);
        }

        public boolean isBigger(long a, Long b) {
            if (b == null) return true;
            return a > b;
        }

        public boolean isLower(long a, Long b) {
            if (b == null) return true;
            return a < b;
        }

        public boolean isFlat(long a, Long b) {
            if (b == null) return false;
            return a == b;
        }

        public void run() {
            for (int x = -10; x <= 10; x++) {
                for (int y = -10; y <= 10; y++) {
                    if (isBigger(get(x, y), get(x - 1, y)) &&
                            isBigger(get(x, y), get(x + 1, y)) &&
                            isBigger(get(x, y), get(x, y - 1)) &&
                            isBigger(get(x, y), get(x, y + 1))) {
                        isMaxima++;
                    }
                    if (isLower(get(x, y), get(x - 1, y)) &&
                            isLower(get(x, y), get(x + 1, y)) &&
                            isLower(get(x, y), get(x, y - 1)) &&
                            isLower(get(x, y), get(x, y + 1))) {
                        isMinima++;
                    }
                    if (isFlat(get(x, y), get(x - 1, y)) ||
                            isFlat(get(x, y), get(x + 1, y)) ||
                            isFlat(get(x, y), get(x, y - 1)) ||
                            isFlat(get(x, y), get(x, y + 1))) {
                        isFlat = true;
                    }
                }
            }
        }

        public String toString() {
            return String.format("Multiple local maxima: %s\nMultiple local minima: %s\nPlateaus: %s\n",
                    isMaxima > 1 ? "Yes" : "No", isMinima > 1 ? "Yes" : "No", isFlat ? "Yes" : "No");
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        FastReader fastReader = new FastReader("unimulti.in");
        // FastReader fastReader = new FastReader();
        PrintWriter printWriter = new PrintWriter("unimulti.out");
        // PrintWriter printWriter = new PrintWriter(System.out);
        parsed = fastReader.next();
        Polynomial polynomial = new Polynomial();
        polynomial.run();
        printWriter.println(polynomial.toString());
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
