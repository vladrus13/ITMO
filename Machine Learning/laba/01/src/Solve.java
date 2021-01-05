import java.io.*;
import java.util.*;
import java.util.stream.IntStream;

public class Solve {

    public static void main(String[] args) {
        FastReader fastReader = new FastReader();
        PrintWriter printWriter = new PrintWriter(System.out);
        int n, m, k;
        LinkedList<Integer> input = new LinkedList<>();
        n = fastReader.nextInt();
        m = fastReader.nextInt();
        k = fastReader.nextInt();
        for (int i = 0; i < n; i++) {
            input.add(fastReader.nextInt());
        }
        CrossMaker crossMaker = new CrossMaker(input, m, k);
        ArrayList<LinkedList<Integer>> answer = crossMaker.getCross();
        for (LinkedList<Integer> term : answer) {
            printWriter.print(term.size() + " ");
            for (Integer number : term) {
                printWriter.print(number + " ");
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

    public static class CrossMaker {
        private final LinkedList<Integer> classes;
        private ArrayList<LinkedList<Integer>> answer;
        private final int m, k;

        CrossMaker(LinkedList<Integer> classes, int m, int k) {
            this.classes = classes;
            this.m = m;
            this.k = k;
            make();
        }

        private void make() {
            answer = new ArrayList<>();
            IntStream.range(0, k).forEach(element -> answer.add(new LinkedList<>()));
            int it = 1;
            Map<Integer, ArrayList<Integer>> numbers = new HashMap<>();
            while (!classes.isEmpty()) {
                Integer first = classes.pollFirst();
                if (!numbers.containsKey(first)) {
                    numbers.put(first, new ArrayList<>());
                }
                numbers.get(first).add(it++);
            }
            it = 0;
            for (ArrayList<Integer> oneNumber : numbers.values()) {
                for (Integer number : oneNumber) {
                    answer.get(it).add(number);
                    it = (it + 1) % k;
                }
            }
        }

        public ArrayList<LinkedList<Integer>> getCross() {
            return answer;
        }
    }
}
