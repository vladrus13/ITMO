import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.*;

public class Solve {

    public static class NaiveBayes {
        private final int k;
        private final double alpha;
        private final ArrayList<Double> lambdas;
        private final ArrayList<Map<String, Integer>> counts;
        private final ArrayList<Integer> classCounts;
        private final Set<String> allWords;
        private ArrayList<Map<String, Double>> problem;
        private int n;

        public NaiveBayes(int k, double alpha, ArrayList<Double> lambdas) {
            this.k = k;
            this.alpha = alpha;
            this.lambdas = lambdas;
            counts = new ArrayList<>();
            classCounts = new ArrayList<>();
            allWords = new HashSet<>();
            for (int i = 0; i < k; i++) {
                counts.add(new HashMap<>());
                classCounts.add(0);
            }
            n = 0;
        }

        public void teach(int c, Set<String> words) {
            Set<String> setWords = new HashSet<>();
            for (String word : words) {
                allWords.add(word);
                setWords.add(word);
            }
            setWords.forEach(element -> counts.get(c - 1).merge(element, 1, Integer::sum));
            classCounts.set(c - 1, classCounts.get(c - 1) + 1);
            n++;
        }

        public void readyToPredict() {
            problem = new ArrayList<>();
            for (int i = 0; i < k; i++) {
                problem.add(new HashMap<>());
                for (Map.Entry<String, Integer> entry : counts.get(i).entrySet()) {
                    problem.get(i).put(entry.getKey(), ((double) entry.getValue() + alpha) / (classCounts.get(i) + 2 * alpha));
                }
            }
        }

        public ArrayList<Double> predict(Set<String> words) {
            ArrayList<Double> scores = new ArrayList<>();
            for (int j = 0; j < k; j++) {
                scores.add(0.0);
            }
            int counter = 0;
            double sum = 0;
            for (int j = 0; j < k; j++) {
                if (classCounts.get(j) != 0) {
                    double score = Math.log((double) lambdas.get(j) * classCounts.get(j) / n);
                    for (String word : allWords) {
                        double v = (problem.get(j).getOrDefault(word, (alpha / ((double) classCounts.get(j) + alpha * 2))));
                        if (words.contains(word)) {
                            score += Math.log(v);
                        } else {
                            score += Math.log(1 - v);
                        }
                    }
                    scores.set(j, score);
                    counter++;
                    sum += score;
                }
            }
            double avg = -sum / counter;
            sum = 0;
            for (int j = 0; j < k; j++) {
                if (classCounts.get(j) != 0) {
                    scores.set(j, Math.exp(avg + scores.get(j)));
                    sum += scores.get(j);
                }
            }
            for (int j = 0; j < k; j++) {
                scores.set(j, scores.get(j) / sum);
            }
            return scores;
        }
    }

    public static void main(String[] args) {
        FastReader fastReader = new FastReader();
        PrintWriter printWriter = new PrintWriter(System.out);
        int k = fastReader.nextInt();
        ArrayList<Double> lambdas = new ArrayList<>();
        for (int i = 0; i < k; i++) {
            lambdas.add((double) fastReader.nextInt());
        }
        int alpha = fastReader.nextInt();
        int n = fastReader.nextInt();
        NaiveBayes naiveBayes = new NaiveBayes(k, alpha, lambdas);
        for (int i = 0; i < n; i++) {
            int c = fastReader.nextInt();
            int counter = fastReader.nextInt();
            Set<String> words = new HashSet<>();
            for (int j = 0; j < counter; j++) {
                String s = fastReader.next();
                words.add(s);
            }
            naiveBayes.teach(c, words);
        }
        naiveBayes.readyToPredict();
        int m = fastReader.nextInt();
        for (int i = 0; i < m; i++) {
            int count = fastReader.nextInt();
            Set<String> words = new HashSet<>();
            for (int j = 0; j < count; j++) {
                words.add(fastReader.next());
            }
            ArrayList<Double> scores = naiveBayes.predict(words);
            for (Double score : scores) {
                printWriter.print(String.format("%.8f ", score));
            }
            printWriter.println();
        }
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
