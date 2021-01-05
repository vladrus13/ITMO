import java.io.*;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Objects;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

public class Solve {

    public static void main(String[] args) {
        FastReader fastReader = new FastReader();
        PrintWriter printWriter = new PrintWriter(System.out);
        int n, m;
        n = fastReader.nextInt();
        m = fastReader.nextInt();
        ArrayList<People> data = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            ArrayList<Integer> temp = new ArrayList<>();
            for (int j = 0; j <= m; j++) {
                temp.add(fastReader.nextInt());
            }
            Integer result = temp.get(temp.size() - 1);
            temp.remove(temp.size() - 1);
            data.add(new People(temp, result));
        }
        ArrayList<Integer> target = new ArrayList<>();
        for (int i = 0; i < m; i++) {
            target.add(fastReader.nextInt());
        }
        DistanceFunction distanceFunction = DistanceFunction.valueOf(fastReader.next().toUpperCase());
        KernelFunction kernelFunction = KernelFunction.valueOf(fastReader.next().toUpperCase());
        String neibType = fastReader.next();
        int neib = fastReader.nextInt();
        double neibe;
        data.sort((o1, o2) -> (int) ((distanceFunction.get(o1.getClasses(), target) - distanceFunction.get(o2.getClasses(), target)) * 100000));
        if (!neibType.equals("fixed")) {
            neibe = distanceFunction.get(data.get(neib).getClasses(), target);
        } else {
            neibe = neib;
        }
        double result;
        if (neibe < 1) {
            ArrayList<People> neighborhood = data.stream().filter(element -> element.getClasses().equals(target)).collect(Collectors.toCollection(ArrayList::new));
            if (neighborhood.size() == 0) {
                result = data.stream().mapToDouble(People::getResult).sum() / n;
            } else {
                result = neighborhood.stream().mapToDouble(People::getResult).sum() / neighborhood.size();
            }
        } else {
            double x1 = data
                    .stream()
                    .mapToDouble(element ->
                            ((double) element.getResult()) *
                                    kernelFunction.get(distanceFunction.get(element.getClasses(), target) / neibe)).sum();
            double x2 = data
                    .stream()
                    .mapToDouble(element ->
                            kernelFunction.get(distanceFunction.get(element.getClasses(), target) / neibe)).sum();
            if (x2 == 0) {
                result = data.stream().mapToDouble(People::getResult).sum() / n;
            } else {
                result = x1 / x2;
            }

        }
        printWriter.println(String.format("%.15f", result));
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

    public class Point {
        public double x, y;
    }

    public static class People {
        private final ArrayList<Integer> classes;
        private final Integer result;

        public People(ArrayList<Integer> classes, Integer result) {
            this.classes = classes;
            this.result = result;
        }

        public ArrayList<Integer> getClasses() {
            return classes;
        }

        public Integer getResult() {
            return result;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            People people = (People) o;
            return Objects.equals(classes, people.classes);
        }
    }

    public enum KernelFunction {
        UNIFORM {
            @Override
            public double get(double distance) {
                return Math.abs(distance) < 1 ? ((double) 1) / 2 : 0;
            }
        }, TRIANGULAR {
            @Override
            public double get(double distance) {
                return Math.abs(distance) < 1 ? 1 - Math.abs(distance) : 0;
            }
        }, EPANECHNIKOV {
            @Override
            public double get(double distance) {
                return Math.abs(distance) < 1 ? ((double) 3 / 4) * (1 - (Math.pow(distance, 2))) : 0;
            }
        }, QUARTIC {
            @Override
            public double get(double distance) {
                return Math.abs(distance) < 1 ? ((double) 15 / 16) * Math.pow((1 - Math.pow(distance, 2)), 2) : 0;
            }
        }, TRIWEIGHT {
            @Override
            public double get(double distance) {
                return Math.abs(distance) < 1 ? ((double) 35 / 32) * Math.pow((1 - Math.pow(distance, 2)), 3) : 0;
            }
        }, TRICUBE {
            @Override
            public double get(double distance) {
                return Math.abs(distance) < 1 ? ((double) 70 / 81) * Math.pow((1 - Math.pow(distance, 3)), 3) : 0;
            }
        }, GAUSSIAN {
            @Override
            public double get(double distance) {
                return (1 / Math.sqrt(2 * Math.PI)) * Math.exp(((double) -1 / 2) * Math.pow(distance, 2));
            }
        }, COSINE {
            @Override
            public double get(double distance) {
                return Math.abs(distance) < 1 ? (Math.PI / 4) * Math.cos(Math.PI * distance / 2) : 0;
            }
        }, LOGISTIC {
            @Override
            public double get(double distance) {
                return 1 / (Math.exp(distance) + 2 + Math.exp(-distance));
            }
        }, SIGMOID {
            @Override
            public double get(double distance) {
                return (2 / Math.PI) / (Math.exp(distance) + Math.exp(-distance));
            }
        };

        /**
         * Get kernel distance on normally distance
         *
         * @param distance normally distance in [0, 1]
         * @return kernel function
         */
        public abstract double get(double distance);
    }

    public enum DistanceFunction {
        MANHATTAN {
            @Override
            public double get(ArrayList<Integer> a, ArrayList<Integer> b) {
                double distance = 0;
                for (int i = 0; i < a.size(); i++) {
                    distance += Math.abs(a.get(i) - b.get(i));
                }
                return distance;
            }
        }, EUCLIDEAN {
            @Override
            public double get(ArrayList<Integer> a, ArrayList<Integer> b) {
                double distance = 0;
                for (int i = 0; i < a.size(); i++) {
                    distance += Math.pow(a.get(i) - b.get(i), 2);
                }
                return Math.sqrt(distance);
            }
        }, CHEBYSHEV {
            @Override
            public double get(ArrayList<Integer> a, ArrayList<Integer> b) {
                double distance = 0;
                for (int i = 0; i < a.size(); i++) {
                    distance = Math.max(distance, Math.abs(a.get(i) - b.get(i)));
                }
                return distance;
            }
        };

        public abstract double get(ArrayList<Integer> a, ArrayList<Integer> b);
    }
}