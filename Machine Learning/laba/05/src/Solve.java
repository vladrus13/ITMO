import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;
import java.util.stream.IntStream;

public class Solve {

    static long time;

    static class SVM {

        private final ArrayList<ArrayList<Integer>> matrix;
        private final ArrayList<Integer> ys;
        private final int C;
        private final ArrayList<Double> lambda = new ArrayList<>();
        private double B;

        SVM(ArrayList<ArrayList<Integer>> matrix, ArrayList<Integer> ys, int C) {
            this.matrix = matrix;
            this.ys = ys;
            this.C = C;
        }

        private double wCalculate(int position) {
            double answer = B;
            for (int i = 0; i < ys.size(); i++) {
                answer += matrix.get(position).get(i) * ys.get(i) * lambda.get(i);
            }
            return answer;
        }

        public void calculateAnswer() {
            B = 0;
            ArrayList<Integer> pairs = new ArrayList<>();
            IntStream.range(0, ys.size()).forEach(element -> {
                lambda.add(0.0);
                pairs.add(element);
            });
            while (System.currentTimeMillis() - time < 1000) {
                Collections.shuffle(pairs);
                for (int i = 0; i < ys.size(); i++) {
                    double ei = wCalculate(i) - ys.get(i);
                    double eps = 1e-6;
                    if ((ys.get(i) * ei < -eps && lambda.get(i) < C) || (ys.get(i) * ei > eps && lambda.get(i) > 0)) {
                        int swapper = pairs.get(i);
                        if (i == swapper) continue;
                        double eswapper = wCalculate(swapper) - ys.get(swapper);
                        if (Math.abs(ei - eswapper) < eps) continue;
                        double L, H;
                        if (!ys.get(i).equals(ys.get(swapper))) {
                            L = Math.max(0.0, lambda.get(swapper) - lambda.get(i));
                            H = Math.min(C, C + lambda.get(swapper) - lambda.get(i));
                        } else {
                            L = Math.max(0.0, lambda.get(swapper) + lambda.get(i) - C);
                            H = Math.min(C, lambda.get(swapper) + lambda.get(i));
                        }
                        if (Math.abs(L - H) < eps) continue;
                        double drob = 2 * matrix.get(i).get(swapper) - matrix.get(i).get(i) - matrix.get(swapper).get(swapper);
                        if (drob > eps) continue;
                        double aswapperOld = lambda.get(swapper);
                        double aswapper = Math.max(L, Math.min(H, aswapperOld - ys.get(swapper) * (ei - eswapper) / drob));
                        if (Math.abs(aswapper - aswapperOld) < eps) continue;
                        double aiOld = lambda.get(i);
                        double ai = aiOld + ys.get(i) * ys.get(swapper) * (aswapperOld - aswapper);
                        double k1 = B - ei - ys.get(i) * (ai - aiOld) * matrix.get(i).get(i) - ys.get(swapper) * (aswapper - aswapperOld) * matrix.get(i).get(swapper);
                        double k2 = B - eswapper - ys.get(i) * (ai - aiOld) * matrix.get(i).get(swapper) - ys.get(swapper) * (aswapper - aswapperOld) * matrix.get(swapper).get(swapper);
                        if (aiOld > 0 && aiOld < C) {
                            B = k1;
                        } else {
                            if (aswapperOld > 0 && aswapperOld < C) {
                                B = k2;
                            } else {
                                B = (k1 + k2) / 2;
                            }
                        }
                        lambda.set(i, ai);
                        lambda.set(swapper, aswapper);
                    }
                }
            }
        }

        public ArrayList<Double> getLambda() {
            return lambda;
        }

        public double getB() {
            return B;
        }
    }

    public static void main(String[] args) {
        time = System.currentTimeMillis();
        FastReader fastReader = new FastReader();
        PrintWriter printWriter = new PrintWriter(System.out);
        int n = fastReader.nextInt();
        ArrayList<ArrayList<Integer>> matrix = new ArrayList<>();
        ArrayList<Integer> ys = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            ArrayList<Integer> temp = new ArrayList<>();
            for (int j = 0; j < n; j++) {
                temp.add(fastReader.nextInt());
            }
            ys.add(fastReader.nextInt());
            matrix.add(temp);
        }
        SVM svm = new SVM(matrix, ys, fastReader.nextInt());
        svm.calculateAnswer();
        for (Double it : svm.getLambda()) {
            printWriter.println(String.format("%.15f", it));
        }
        printWriter.println(String.format("%.15f", svm.getB()));
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
