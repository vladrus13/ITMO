import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Solve {
    public static void main(String[] args) {
        FastReader fastReader = new FastReader();
        PrintWriter printWriter = new PrintWriter(System.out);
        int k;
        ArrayList<ArrayList<Integer>> list = new ArrayList<>();
        k = fastReader.nextInt();
        for (int i = 0; i < k; i++) {
            ArrayList<Integer> temp = new ArrayList<>();
            for (int j = 0; j < k; j++) {
                temp.add(fastReader.nextInt());
            }
            list.add(temp);
        }
        FCalculator fCalculator = new FCalculator(list);
        FCalculator.Result result = fCalculator.returnMicro();
        printWriter.println(result.precision);
        printWriter.println(result.recall);
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

    public static class FCalculator {
        private final ArrayList<ArrayList<Integer>> matrix;
        double macro_f, micro_f;

        private int getLine(int index) {
            return matrix.get(index).stream().mapToInt(Integer::intValue).sum();
        }

        private Result getResult(int index) {
            int normal = matrix.get(index).get(index);
            int tpFp = matrix.stream().mapToInt(integers -> integers.get(index)).sum();
            int tpFn = getLine(index);
            Result returned = new Result();
            returned.precision = tpFp == 0 ? 0 : (double) normal / tpFp;
            returned.recall = tpFn == 0 ? 0 : (double) normal / tpFn;
            return returned;
        }

        private double getF(Result result) {
            return result.recall + result.precision == 0 ? 0 : 2 * result.precision * result.recall / (result.precision + result.recall);
        }

        private double getFReal(int index) {
            return getF(getResult(index));
        }

        public FCalculator(ArrayList<ArrayList<Integer>> matrix) {
            this.matrix = matrix;
            int total_sum = 0;
            for (ArrayList<Integer> i : matrix) {
                for (Integer j : i) {
                    total_sum += j;
                }
            }
            macro_f = 0;
            for (int i = 0; i < matrix.size(); i++) {
                macro_f += getFReal(i) * getLine(i);
            }
            macro_f /= total_sum;
            double micro_pre = 0;
            for (int i = 0; i < matrix.size(); i++) {
                micro_pre += getResult(i).precision * getLine(i);
            }
            micro_pre /= total_sum;
            double micro_recall = 0;
            for (int i = 0; i < matrix.size(); i++) {
                micro_recall += getResult(i).recall * getLine(i);
            }
            micro_recall /= total_sum;
            micro_f = getF(new Result(micro_pre, micro_recall));
        }

        public Result returnMicro() {
            return new Result(micro_f, macro_f);
        }

        static class Result {
            double precision, recall;

            public Result(double precision, double recall) {
                this.precision = precision;
                this.recall = recall;
            }

            public Result() {}
        }
    }
}
