import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.function.BinaryOperator;
import java.util.stream.IntStream;

public class Solve {
    public static final String precision = ".10";
    public static final BinaryOperator<Double> divide = (a, b) -> a / b;
    public static final BinaryOperator<Double> second = (a, b) -> b;
    public static final BinaryOperator<Double> sum = Double::sum;

    public static class Triple<A, B, C> {
        A depth;
        B height;
        C width;

        public Triple(A depth, B height, C width) {
            this.depth = depth;
            this.height = height;
            this.width = width;
        }

    }

    public static class TensorSize extends Triple<Integer, Integer, Integer> {
        public TensorSize(Integer first, Integer second, Integer third) {
            super(first, second, third);
        }

        private TensorSize copy() {
            return new TensorSize(depth, height, width);
        }
    }

    public static class Vector {
        public ArrayList<Double> in;

        public Vector(ArrayList<Double> in) {
            this.in = in;
        }

        public static Vector generate(int i, double x) {
            ArrayList<Double> returned = new ArrayList<>();
            IntStream.range(0, i).forEach(j -> returned.add(x));
            return new Vector(returned);
        }

        public Double get(int i) {
            return in.get(i);
        }

        public void apply(int i, BinaryOperator<Double> function, Double x) {
            in.set(i, function.apply(in.get(i), x));
        }

        public Vector copy() {
            ArrayList<Double> copies = new ArrayList<>();
            IntStream.range(0, in.size()).forEach(e -> copies.add(in.get(e)));
            return new Vector(copies);
        }
    }

    public static class Matrix {
        public ArrayList<Vector> in;

        public Matrix(ArrayList<Vector> in) {
            this.in = in;
        }

        public static Matrix generate(int j, int i, double x) {
            ArrayList<Vector> returned = new ArrayList<>();
            IntStream.range(0, j).forEach(k -> returned.add(Vector.generate(i, x)));
            return new Matrix(returned);
        }

        public Vector get(int i) {
            return in.get(i);
        }

        public Matrix copy() {
            ArrayList<Vector> copies = new ArrayList<>();
            IntStream.range(0, in.size()).forEach(i -> copies.add(in.get(i).copy()));
            return new Matrix(copies);
        }
    }

    public static class Tensor {
        public ArrayList<Matrix> in;

        public Tensor(ArrayList<Matrix> in) {
            this.in = in;
        }

        public void print(PrintWriter printWriter) {
            for (Matrix matrix : in) {
                for (Vector vector : matrix.in) {
                    for (Double d : vector.in) {
                        printWriter.printf("%" + precision + "f ", d);
                    }
                }
            }
            printWriter.println();
        }

        public static Tensor generate(int k, int j, int i, double x) {
            ArrayList<Matrix> returned = new ArrayList<>();
            IntStream.range(0, k).forEach(l -> returned.add(Matrix.generate(j, i, x)));
            return new Tensor(returned);
        }

        public Tensor copy() {
            ArrayList<Matrix> copies = new ArrayList<>();
            IntStream.range(0, in.size()).forEach(i -> copies.add(in.get(i).copy()));
            return new Tensor(copies);
        }

        public Matrix get(int i) {
            return in.get(i);
        }
    }

    public static abstract class Node {
        protected TensorSize inputSize;
        protected TensorSize outputSize;
        protected int steps;

        public Node() {
            steps = 1;
        }

        public abstract Tensor makeStep(Tensor in);

        public abstract Tensor makeBackpropagation(Tensor out);

        Tensor getBackpropagation(Tensor out) {
            return makeBackpropagation(out);
        }

        public abstract void print(PrintWriter printWriter);
    }

    public static class Relu extends Node {
        double invAlpha;
        Tensor copy;

        public Relu(double invAlpha, TensorSize tensorSize) {
            this.invAlpha = invAlpha;
            inputSize = tensorSize.copy();
            outputSize = tensorSize.copy();
        }

        @Override
        public Tensor makeStep(Tensor input) {
            Tensor value = input.copy();
            copy = input.copy();
            for (Matrix i : value.in) {
                for (Vector j : i.in) {
                    for (int k = 0; k < j.in.size(); k++) {
                        if (j.in.get(k) < 0) {
                            j.apply(k, divide, invAlpha);
                        }
                    }
                }
            }
            return value;
        }

        @Override
        public Tensor makeBackpropagation(Tensor output) {
            Tensor returned = Tensor.generate(inputSize.depth, inputSize.height, inputSize.width, 0);
            for (int i = 0; i < outputSize.depth; ++i) {
                for (int j = 0; j < outputSize.height; ++j) {
                    for (int k = 0; k < outputSize.width; ++k) {
                        Double current = copy.get(i).get(j).get(k);
                        double koff;
                        if (current >= 0) {
                            koff = 1.0;
                        } else {
                            koff = 1.0 / invAlpha;
                        }
                        returned.get(i).get(j).apply(k, sum, koff * output.get(i).get(j).get(k));
                    }
                }
            }
            return returned;
        }

        @Override
        public void print(PrintWriter printWriter) {
        }
    }

    public static class Pool extends Node {
        int s;
        Tensor copy;

        public Pool(int s, TensorSize tensorSize) {
            this.s = s;
            inputSize = tensorSize.copy();
            outputSize = new TensorSize(inputSize.depth, (inputSize.height - s) / s + 1, (inputSize.width - s) / s + 1);
        }

        @Override
        public Tensor makeStep(Tensor input) {
            Tensor value = Tensor.generate(outputSize.depth, outputSize.height, outputSize.width, -Double.MAX_VALUE);
            copy = input.copy();
            for (int i = 0; i < outputSize.depth; ++i) {
                for (int j = 0; j < outputSize.height; ++j) {
                    for (int k = 0; k < outputSize.width; ++k) {
                        for (int dj = 0; dj < s; ++dj) {
                            for (int dk = 0; dk < s; ++dk) {
                                value.get(i).get(j).apply(k, Math::max, input.get(i).get(j * s + dj).get(k * s + dk));
                            }
                        }
                    }
                }
            }
            return value;
        }

        @Override
        public Tensor makeBackpropagation(Tensor output) {
            Tensor res = Tensor.generate(inputSize.depth, inputSize.height, inputSize.width, 0);
            for (int i = 0; i < outputSize.depth; ++i) {
                for (int j = 0; j < outputSize.height; ++j) {
                    for (int k = 0; k < outputSize.width; ++k) {
                        double cur_max = -Double.MAX_VALUE;
                        for (int di = 0; di < s; ++di) {
                            for (int dj = 0; dj < s; ++dj) {
                                cur_max = Math.max(cur_max, copy.get(i).get(j * s + di).get(k * s + dj));
                            }
                        }
                        for (int di = 0; di < s; ++di) {
                            for (int dj = 0; dj < s; ++dj) {
                                if (copy.get(i).get(j * s + di).get(k * s + dj) == cur_max) {
                                    res.get(i).get(j * s + di).apply(k * s + dj, sum, output.get(i).get(j).get(k));
                                }
                            }
                        }
                    }
                }
            }
            return res;
        }

        @Override
        public void print(PrintWriter printWriter) { }
    }

    public static class Bias extends Node {

        Vector bias;
        Vector dbias;

        Vector momentum;
        Vector adaptive;

        public Bias(TensorSize tensorSize, FastReader fastReader) {
            bias = Vector.generate(tensorSize.depth, 0);
            for (int i = 0; i < tensorSize.depth; i++) {
                bias.apply(i, second, (double) fastReader.nextInt());
            }
            inputSize = tensorSize.copy();
            outputSize = tensorSize.copy();
            momentum = Vector.generate(bias.in.size(), 0);
            adaptive = Vector.generate(bias.in.size(), 0);
        }

        @Override
        public Tensor makeStep(Tensor input) {
            Tensor value = input.copy();
            for (int i = 0; i < inputSize.depth; ++i) {
                for (Vector j : value.get(i).in) {
                    for (int k = 0; k < j.in.size(); k++) {
                        j.apply(k, sum, bias.get(i));
                    }
                }
            }
            return value;
        }

        @Override
        public Tensor makeBackpropagation(Tensor output) {
            Tensor returned = Tensor.generate(inputSize.depth, inputSize.height, inputSize.width, 0);
            dbias = Vector.generate(bias.in.size(), 0);
            for (int i = 0; i < outputSize.depth; ++i) {
                for (int j = 0; j < outputSize.height; ++j) {
                    for (int k = 0; k < outputSize.width; ++k) {
                        returned.get(i).get(j).apply(k, sum, output.get(i).get(j).get(k));
                        dbias.apply(i, sum, output.get(i).get(j).get(k));
                    }
                }
            }
            return returned;
        }

        @Override
        public void print(PrintWriter printWriter) {
            for (Double d : dbias.in) {
                printWriter.printf("%" + precision + "f ", d);
            }
            printWriter.println();
        }
    }

    public enum CnvType {
        M, E, C
    }

    public abstract static class Cnv extends Node {
        ArrayList<Tensor> kernels;
        ArrayList<Tensor> dkernels;
        ArrayList<Tensor> momentum;
        ArrayList<Tensor> adaptive;
        int p;
        int s;
        Tensor paddedInput;

        public Cnv(int cnt, int n, int m, int p, int s, TensorSize inputSize, FastReader fastReader) {
            kernels = new ArrayList<>();
            for (int i = 0; i < cnt; i++) {
                kernels.add(Tensor.generate(inputSize.depth, n, m, 0));
            }
            this.p = p;
            this.s = s;
            this.inputSize = inputSize.copy();
            outputSize = new TensorSize(cnt, (this.inputSize.height + 2 * p - n) / s + 1, (this.inputSize.width + 2 * p - m) / s + 1);
            for (Tensor kernel : kernels) {
                for (Matrix matrix : kernel.in) {
                    for (Vector vector : matrix.in) {
                        for (int i = 0; i < vector.in.size(); i++) {
                            vector.apply(i, second, (double) fastReader.nextInt());
                        }
                    }
                }
            }
            momentum = new ArrayList<>();
            for (int i = 0; i < cnt; i++) {
                momentum.add(Tensor.generate(inputSize.depth, n, m, 0));
            }
            adaptive = new ArrayList<>();
            for (int i = 0; i < cnt; i++) {
                adaptive.add(momentum.get(i).copy());
            }
        }

        public abstract Matrix getPaddedLayer(Matrix matrixLayer);

        public abstract void makeCompress(Matrix matrixLayer);

        @Override
        public Tensor makeStep(Tensor input) {
            int d = inputSize.depth;
            int layer1 = kernels.get(0).get(0).in.size();
            int layer2 = kernels.get(0).get(0).get(0).in.size();
            paddedInput = new Tensor(new ArrayList<>());
            for (int i = 0; i < d; ++i) {
                paddedInput.in.add(getPaddedLayer(input.get(i)));
            }
            Tensor returned = Tensor.generate(outputSize.depth, outputSize.height, outputSize.width, 0);
            for (int i = 0; i < outputSize.depth; ++i) {
                for (int cur_d = 0; cur_d < d; ++cur_d) {
                    for (int j = 0; j < outputSize.height; ++j) {
                        for (int k = 0; k < outputSize.width; ++k) {
                            for (int di = 0; di < layer1; ++di) {
                                for (int dj = 0; dj < layer2; ++dj) {
                                    returned.get(i).get(j).apply(k, sum, paddedInput.get(cur_d).get(j * s + di).get(k * s + dj) * kernels.get(i).get(cur_d).get(di).get(dj));
                                }
                            }
                        }
                    }
                }
            }
            dkernels = new ArrayList<>();
            for (int i = 0; i < kernels.size(); i++) {
                dkernels.add(Tensor.generate(d, layer1, layer2, 0));
            }
            return returned;
        }

        @Override
        public Tensor makeBackpropagation(Tensor outputDiff) {
            Tensor paddedInputDiff = getPaddedBackpropagation(outputDiff);
            int d = inputSize.depth;
            int n = inputSize.height;
            int m = inputSize.width;
            Tensor returned = Tensor.generate(d, n, m, 0);
            for (int i = 0; i < d; ++i) {
                makeCompress(paddedInputDiff.get(i));
                for (int j = 0; j < n; ++j) {
                    for (int k = 0; k < m; ++k) {
                        returned.get(i).get(j).apply(k, sum, paddedInputDiff.get(i).get(j + p).get(k + p));
                    }
                }
            }
            return returned;
        }

        public Tensor getPaddedBackpropagation(Tensor outDiff) {
            int d = inputSize.depth;
            int n = inputSize.height;
            int m = inputSize.width;
            int layer1 = kernels.get(0).get(0).in.size();
            int layer2 = kernels.get(0).get(0).get(0).in.size();
            Tensor paddedInputDiff = Tensor.generate(d, n + 2 * p, m + 2 * p, 0);
            for (int i = 0; i < outputSize.depth; ++i) {
                for (int current = 0; current < d; ++current) {
                    for (int j = 0; j < outputSize.height; ++j) {
                        for (int k = 0; k < outputSize.width; ++k) {
                            for (int di = 0; di < layer1; ++di) {
                                for (int dj = 0; dj < layer2; ++dj) {
                                    paddedInputDiff.get(current).get(j * s + di).apply(k * s + dj, sum,
                                            kernels.get(i).get(current).get(di).get(dj) * outDiff.get(i).get(j).get(k));
                                    dkernels.get(i).get(current).get(di).apply(dj, sum,
                                            paddedInput.get(current).get(j * s + di).get(k * s + dj) * outDiff.get(i).get(j).get(k));
                                }
                            }
                        }
                    }
                }
            }
            return paddedInputDiff;
        }

        @Override
        public void print(PrintWriter printWriter) {
            for (Tensor tensor : dkernels) {
                for (Matrix matrix : tensor.in) {
                    for (Vector vector : matrix.in) {
                        for (Double d : vector.in) {
                            printWriter.printf("%" + precision + "f ", d);
                        }
                    }
                }
            }
            printWriter.println();
        }
    }

    public static class Cnve extends Cnv {

        public Cnve(int cnt, int n, int m, int p, int s, TensorSize inputSize, FastReader fastReader) {
            super(cnt, n, m, p, s, inputSize, fastReader);
        }

        @Override
        public Matrix getPaddedLayer(Matrix matrixLayer) {
            Matrix returned = Matrix.generate(matrixLayer.in.size() + 2 * p, matrixLayer.get(0).in.size() + 2 * p, 0);
            for (int i = 0; i < matrixLayer.in.size(); ++i) {
                for (int j = 0; j < matrixLayer.get(0).in.size(); ++j) {
                    returned.get(i + p).apply(j + p, second, matrixLayer.get(i).get(j));
                }
            }
            for (int i = p; i < p + matrixLayer.in.size(); ++i) {
                for (int j = 0; j < p; ++j) {
                    returned.get(i).apply(j, second, matrixLayer.get(i - p).get(0));
                }
                for (int j = matrixLayer.get(0).in.size() + p; j < matrixLayer.get(0).in.size() + 2 * p; ++j) {
                    returned.get(i).apply(j, second, matrixLayer.get(i - p).get(matrixLayer.get(i - p).in.size() - 1));
                }
            }
            for (int i = 0; i < p; ++i) {
                for (int j = 0; j < matrixLayer.get(0).in.size() + 2 * p; ++j) {
                    returned.get(i).apply(j, second, returned.get(p).get(j));
                }
            }
            for (int i = matrixLayer.in.size() + p; i < matrixLayer.in.size() + 2 * p; ++i) {
                for (int j = 0; j < matrixLayer.get(0).in.size() + 2 * p; ++j) {
                    returned.get(i).apply(j, second, returned.get(matrixLayer.in.size() + p - 1).get(j));
                }
            }
            return returned;
        }

        @Override
        public void makeCompress(Matrix matrixLayer) {
            int n = matrixLayer.in.size() - 2 * p;
            int m = matrixLayer.get(0).in.size() - 2 * p;

            for (int i = 0; i < p; ++i) {
                for (int j = 0; j < m + 2 * p; ++j) {
                    matrixLayer.get(p).apply(j, sum, matrixLayer.get(i).get(j));
                }
            }
            for (int i = n + p; i < n + 2 * p; ++i) {
                for (int j = 0; j < m + 2 * p; ++j) {
                    matrixLayer.get(n + p - 1).apply(j, sum, matrixLayer.get(i).get(j));
                }
            }

            for (int i = p; i < p + n; ++i) {
                for (int j = 0; j < p; ++j) {
                    matrixLayer.get(i).apply(p, sum, matrixLayer.get(i).get(j));
                }
                for (int j = m + p; j < m + 2 * p; ++j) {
                    matrixLayer.get(i).apply(m + p - 1, sum, matrixLayer.get(i).get(j));
                }
            }
        }
    }

    public static class Cnvc extends Cnv {

        public Cnvc(int cnt, int n, int m, int p, int s, TensorSize inputSize, FastReader fastReader) {
            super(cnt, n, m, p, s, inputSize, fastReader);
        }

        @Override
        public Matrix getPaddedLayer(Matrix matrixLayer) {
            Matrix returned = Matrix.generate(matrixLayer.in.size() + 2 * p, matrixLayer.get(0).in.size() + 2 * p, 0);
            for (int i = 0; i < matrixLayer.in.size(); ++i) {
                for (int j = 0; j < matrixLayer.get(0).in.size(); ++j) {
                    returned.get(i + p).apply(j + p, second, matrixLayer.get(i).get(j));
                }
            }
            for (int i = p; i < p + matrixLayer.in.size(); ++i) {
                for (int j = p - 1; j >= 0; --j) {
                    returned.get(i).apply(j, second, returned.get(i).get(j + matrixLayer.get(0).in.size()));
                }
                for (int j = matrixLayer.get(0).in.size() + p; j < matrixLayer.get(0).in.size() + 2 * p; ++j) {
                    returned.get(i).apply(j, second, returned.get(i).get(j - matrixLayer.get(0).in.size()));
                }
            }
            for (int i = p - 1; i >= 0; --i) {
                for (int j = 0; j < matrixLayer.get(0).in.size() + 2 * p; ++j) {
                    returned.get(i).apply(j, second, returned.get(i + matrixLayer.in.size()).get(j));
                }
            }
            for (int i = matrixLayer.in.size() + p; i < matrixLayer.in.size() + 2 * p; ++i) {
                for (int j = 0; j < matrixLayer.get(0).in.size() + 2 * p; ++j) {
                    returned.get(i).apply(j, second, returned.get(i - matrixLayer.in.size()).get(j));
                }
            }
            return returned;
        }

        @Override
        public void makeCompress(Matrix matrixLayer) {
            int n = matrixLayer.in.size() - 2 * p;
            int m = matrixLayer.get(0).in.size() - 2 * p;
            for (int i = p - 1; i >= 0; --i) {
                for (int j = 0; j < m + 2 * p; ++j) {
                    matrixLayer.get(i + n).apply(j, sum, matrixLayer.get(i).get(j));
                }
            }
            for (int i = n + p; i < n + 2 * p; ++i) {
                for (int j = 0; j < m + 2 * p; ++j) {
                    matrixLayer.get(i - n).apply(j, sum, matrixLayer.get(i).get(j));
                }
            }

            for (int i = p; i < p + n; ++i) {
                for (int j = p - 1; j >= 0; --j) {
                    matrixLayer.get(i).apply(j + m, sum, matrixLayer.get(i).get(j));
                }
                for (int j = m + p; j < m + 2 * p; ++j) {
                    matrixLayer.get(i).apply(j - m, sum, matrixLayer.get(i).get(j));
                }
            }
        }
    }

    public static class Cnvm extends Cnv {

        public Cnvm(int cnt, int n, int m, int p, int s, TensorSize inputSize, FastReader fastReader) {
            super(cnt, n, m, p, s, inputSize, fastReader);
        }

        @Override
        public Matrix getPaddedLayer(Matrix matrixLayer) {
            Matrix returned = Matrix.generate(matrixLayer.in.size() + 2 * p, matrixLayer.get(0).in.size() + 2 * p, 0);
            for (int i = 0; i < matrixLayer.in.size(); ++i) {
                for (int j = 0; j < matrixLayer.get(0).in.size(); ++j) {
                    returned.get(i + p).apply(j + p, second, matrixLayer.get(i).get(j));
                }
            }
            for (int i = p; i < p + matrixLayer.in.size(); ++i) {
                for (int j = 0; j < p; ++j) {
                    returned.get(i).apply(j, second, matrixLayer.get(i - p).get(p - j));
                }
                for (int j = p + matrixLayer.get(0).in.size(); j < matrixLayer.get(0).in.size() + 2 * p; ++j) {
                    returned.get(i).apply(j, second, matrixLayer.get(i - p).get(2 * matrixLayer.get(0).in.size() + p - j - 2));
                }
            }
            for (int i = 0; i < p; ++i) {
                for (int j = 0; j < matrixLayer.get(0).in.size() + 2 * p; ++j) {
                    returned.get(i).apply(j, second, returned.get(2 * p - i).get(j));
                }
            }
            for (int i = matrixLayer.in.size() + p; i < matrixLayer.in.size() + 2 * p; ++i) {
                for (int j = 0; j < matrixLayer.get(0).in.size() + 2 * p; ++j) {
                    returned.get(i).apply(j, second, returned.get(2 * (matrixLayer.in.size() + p) - i - 2).get(j));
                }
            }
            return returned;
        }

        @Override
        public void makeCompress(Matrix matrixLayer) {
            int n = matrixLayer.in.size() - 2 * p;
            int m = matrixLayer.get(0).in.size() - 2 * p;
            for (int i = 0; i < p; ++i) {
                for (int j = 0; j < m + 2 * p; ++j) {
                    matrixLayer.get(2 * p - i).apply(j, sum, matrixLayer.get(i).get(j));
                }
            }
            for (int i = n + p; i < n + 2 * p; ++i) {
                for (int j = 0; j < m + 2 * p; ++j) {
                    matrixLayer.get(2 * (n + p) - i - 2).apply(j, sum, matrixLayer.get(i).get(j));
                }
            }
            for (int i = p; i < p + n; ++i) {
                for (int j = 0; j < p; ++j) {
                    matrixLayer.get(i).apply(p - j + p, sum, matrixLayer.get(i).get(j));
                }
                for (int j = p + m; j < m + 2 * p; ++j) {
                    matrixLayer.get(i).apply(2 * m + p - j - 2 + p, sum, matrixLayer.get(i).get(j));
                }
            }
        }
    }

    public static class Network {
        public TensorSize size;
        public ArrayList<Node> nodes;
        public FastReader fastReader;

        public Network(TensorSize size, FastReader fastReader) {
            this.size = size;
            this.nodes = new ArrayList<>();
            this.fastReader = fastReader;
        }

        public TensorSize getSize() {
            return nodes.isEmpty() ? size : nodes.get(nodes.size() - 1).outputSize;
        }

        public void addBias() {
            nodes.add(new Bias(getSize(), fastReader));
        }

        public void addRelu(int invAlpha) {
            nodes.add(new Relu(invAlpha, getSize()));
        }

        public void addPool(int s) {
            nodes.add(new Pool(s, getSize()));
        }

        public void addCnv(int cnt, int n, int m, int p, int s, CnvType type) {
            switch (type) {
                case E:
                    nodes.add(new Cnve(cnt, n, m, p, s, getSize(), fastReader));
                    break;
                case M:
                    nodes.add(new Cnvm(cnt, n, m, p, s, getSize(), fastReader));
                    break;
                case C:
                    nodes.add(new Cnvc(cnt, n, m, p, s, getSize(), fastReader));
                    break;
            }
        }
    }

    public static void main(String[] args) {
        FastReader fastReader = new FastReader();
        PrintWriter printWriter = new PrintWriter(System.out);
        int n = fastReader.nextInt(), d = fastReader.nextInt();
        Tensor value = Tensor.generate(d, n, n, 0);
        for (int i = 0; i < d; i++) {
            for (int j = 0; j < n; j++) {
                for (int k = 0; k < n; k++) {
                    value.get(i).get(j).apply(k, second, (double) fastReader.nextInt());
                }
            }
        }
        Network network = new Network(new TensorSize(d, n, n), fastReader);
        int L = fastReader.nextInt();
        String type;
        int a, b, c, e;
        for (int l = 0; l < L; l++) {
            type = fastReader.next();
            switch (type) {
                case "relu":
                    a = fastReader.nextInt();
                    network.addRelu(a);
                    break;
                case "pool":
                    a = fastReader.nextInt();
                    network.addPool(a);
                    break;
                case "bias":
                    network.addBias();
                    break;
                case "cnvm":
                    a = fastReader.nextInt();
                    b = fastReader.nextInt();
                    c = fastReader.nextInt();
                    e = fastReader.nextInt();
                    network.addCnv(a, b, b, e, c, CnvType.M);
                    break;
                case "cnve":
                    a = fastReader.nextInt();
                    b = fastReader.nextInt();
                    c = fastReader.nextInt();
                    e = fastReader.nextInt();
                    network.addCnv(a, b, b, e, c, CnvType.E);
                    break;
                case "cnvc":
                    a = fastReader.nextInt();
                    b = fastReader.nextInt();
                    c = fastReader.nextInt();
                    e = fastReader.nextInt();
                    network.addCnv(a, b, b, e, c, CnvType.C);
                    break;
            }
        }
        for (Node node : network.nodes) {
            value = node.makeStep(value);
        }
        value.print(printWriter);
        int a1 = value.in.size();
        int a2 = value.get(0).in.size();
        int a3 = value.get(0).get(0).in.size();
        Tensor differential = Tensor.generate(a1, a2, a3, 0);
        for (Matrix matrix : differential.in) {
            for (Vector vector : matrix.in) {
                for (int i = 0; i < vector.in.size(); i++) {
                    vector.apply(i, second , (double) fastReader.nextInt());
                }
            }
        }
        for (int i = network.nodes.size() - 1; i >= 0; i--) {
            differential = network.nodes.get(i).getBackpropagation(differential);
        }
        differential.print(printWriter);
        for (Node node : network.nodes) {
            node.print(printWriter);
        }
        printWriter.close();
    }

    public static class FastReader {
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
