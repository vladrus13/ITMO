package ru.vladrus13.methods;

import Jama.Matrix;
import Jama.SingularValueDecomposition;
import ru.vladrus13.bean.Bean;

import java.util.ArrayList;

public class MMS extends Method {

    final Matrix matrix;
    final Matrix ys;
    final ArrayList<Double> answer;

    public MMS(ArrayList<Bean> beans, double k) {
        super();
        double[][] prMatrix = new double[beans.size()][beans.get(0).getAttributes().size()];
        for (int i = 0; i < beans.size(); i++) {
            for (int j = 0; j < beans.get(0).getAttributes().size(); j++) {
                prMatrix[i][j] = beans.get(i).getAttributes().get(j);
            }
        }
        this.matrix = new Matrix(prMatrix);
        prMatrix = new double[1][beans.size()];
        for (int i = 0; i < beans.size(); i++) {
            prMatrix[0][i] = beans.get(i).getY();
        }
        this.ys = new Matrix(prMatrix);
        SingularValueDecomposition singularValueDecomposition = matrix.svd();
        Matrix V = singularValueDecomposition.getV();
        Matrix S = singularValueDecomposition.getS();
        Matrix U = singularValueDecomposition.getU();
        Matrix matrixAnswer = U.times(S.inverse()).times(k).times(V.transpose()).transpose().times(ys.transpose());
        answer = new ArrayList<>();
        double[][] copy = matrixAnswer.getArrayCopy();
        for (double[] doubles : copy) {
            for (double aDouble : doubles) {
                answer.add(aDouble);
            }
        }
    }

    public ArrayList<Double> getAnswer() {
        return answer;
    }

    @Override
    public String getName() {
        return "MMS";
    }
}
