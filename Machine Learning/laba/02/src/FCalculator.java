import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.IntStream;

public class FCalculator {
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
