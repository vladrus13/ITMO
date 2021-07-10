import java.io.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.StringTokenizer;

public class Launcher {

    public static class Queerer {
        public final FastReader fastReader;
        public final PrintWriter printWriter;
        public final OneMaxBox oneMaxBox;
        public final int n;

        public Queerer(FastReader fastReader, PrintWriter printWriter, OneMaxBox oneMaxBox, int n) {
            this.fastReader = fastReader;
            this.printWriter = printWriter;
            this.oneMaxBox = oneMaxBox;
            this.n = n;
        }

        public void run() {
            while (true) {
                int answer = fastReader.nextInt();
                if (answer == n) {
                    break;
                }
                ArrayList<Integer> query = oneMaxBox.query(answer);
                for (Integer it : query) {
                    printWriter.printf("%d ", it + 1);
                }
                printWriter.println();
                printWriter.flush();
            }
        }
    }

    public static class Pair<A, B> {
        public final A a;
        public final B b;

        public Pair(A a, B b) {
            this.a = a;
            this.b = b;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Pair<?, ?> pair = (Pair<?, ?>) o;
            return Objects.equals(a, pair.a) && Objects.equals(b, pair.b);
        }

        @Override
        public int hashCode() {
            return Objects.hash(a, b);
        }
    }

    public static class OneMaxBox {

        public ArrayList<Integer> endgame = new ArrayList<>();

        public interface Machine {
            ArrayList<Integer> query(int fitness);

            boolean isDone();
        }

        public static class EndGameMachine implements Machine {

            OneMaxBox oneMaxBox;

            public EndGameMachine(OneMaxBox oneMaxBox) {
                this.oneMaxBox = oneMaxBox;
            }

            @Override
            public ArrayList<Integer> query(int fitness) {
                return new ArrayList<>(oneMaxBox.endgame);
            }

            @Override
            public boolean isDone() {
                return false;
            }
        }

        public static class StupidMachine implements Machine {

            int current;
            int finish;
            boolean isFirst;
            OneMaxBox oneMaxBox;

            public StupidMachine(OneMaxBox oneMaxBox) {
                current = oneMaxBox.currentPosition;
                finish = oneMaxBox.size;
                isFirst = true;
                this.oneMaxBox = oneMaxBox;
            }

            @Override
            public ArrayList<Integer> query(int fitness) {
                ArrayList<Integer> answer = new ArrayList<>();
                if (isFirst) {
                    answer.add(current);
                    current++;
                    isFirst = false;
                    return answer;
                } else {
                    if (oneMaxBox.lastFitness > fitness) {
                        oneMaxBox.endgame.add(current - 1);
                    }
                    if (current >= finish) {
                        oneMaxBox.currentMachine = new EndGameMachine(oneMaxBox);
                        return null;
                    } else {
                        answer.add(current);
                        current++;
                        return answer;
                    }
                }
            }

            @Override
            public boolean isDone() {
                return current > finish;
            }
        }

        public static class MontrousMachine implements Machine {

            public final OneMaxBox oneMaxBox;
            public Pair<Integer, Integer> previous;
            public boolean isAsked;
            public ArrayList<Integer> mapa;
            public boolean isTyping = false;

            public MontrousMachine(OneMaxBox oneMaxBox) {
                this.oneMaxBox = oneMaxBox;
                this.mapa = new ArrayList<>();
                isAsked = false;
            }

            public void type(String s) {
                for (int i = 0; i < s.length(); i++) {
                    if (s.charAt(i) == '1') oneMaxBox.endgame.add(oneMaxBox.currentPosition + i);
                }
                isTyping = true;
            }

            public int get(int level, int i, int j) {
                if (mapa.size() > level) {
                    return mapa.get(level);
                } else {
                    previous = new Pair<>(i, j);
                    isAsked = true;
                    // System.out.println("Ask: " + i + " " + j);
                    return -1;
                }
            }

            public void clear() {
                mapa.clear();
            }

            @Override
            public ArrayList<Integer> query(int fitness) {
                if (previous != null && isAsked) {
                    ArrayList<Integer> returned = new ArrayList<>();
                    for (int i = previous.a; i <= previous.b; i++) {
                        returned.add(i + oneMaxBox.currentPosition);
                    }
                    isAsked = false;
                    return returned;
                }
                if (previous != null) {
                    // System.out.println("Get: [" + previous.a + ", " + previous.b + "]. Before: " + oneMaxBox.lastFitness + ". After: " + fitness + ". Add: " + (fitness - oneMaxBox.lastFitness + previous.b - previous.a + 1) / 2);
                    mapa.add((fitness - oneMaxBox.lastFitness + previous.b - previous.a + 1) / 2);
                    isAsked = false;
                    previous = null;
                }
                switch (get(0, 0, 0)) {
                    case 0:
                        switch (get(1, 0, 1)) {
                            case 0:
                                clear();
                                break;
                            case 1:
                                switch (get(2, 0, 5)) {
                                    case 0:
                                    case 6:
                                        clear();
                                        break;
                                    case 1:
                                        switch (get(3, 0, 0)) {
                                            case 0:
                                                clear();
                                                break;
                                            case 1:
                                                switch (get(4, 0, 6)) {
                                                    case 0:
                                                    case 7:
                                                    case 6:
                                                    case 3:
                                                    case 2:
                                                    case 1:
                                                        clear();
                                                        break;
                                                    case 4:
                                                        switch (get(5, 0, 7)) {
                                                            case 0:
                                                            case 8:
                                                            case 7:
                                                            case 6:
                                                            case 5:
                                                            case 2:
                                                            case 1:
                                                                clear();
                                                                break;
                                                            case 3:
                                                                type("00111101");
                                                                clear();
                                                                break;
                                                            case 4:
                                                                type("00111100");
                                                                clear();
                                                                break;
                                                            case -1:
                                                                return query(fitness);
                                                        }
                                                        break;
                                                    case 5:
                                                        switch (get(5, 0, 7)) {
                                                            case 0:
                                                            case 8:
                                                            case 7:
                                                            case 6:
                                                            case 5:
                                                            case 4:
                                                            case 1:
                                                                clear();
                                                                break;
                                                            case 2:
                                                                type("00111111");
                                                                clear();
                                                                break;
                                                            case 3:
                                                                type("00111110");
                                                                clear();
                                                                break;
                                                            case -1:
                                                                return query(fitness);
                                                        }
                                                        break;
                                                    case -1:
                                                        return query(fitness);
                                                }
                                                break;
                                            case -1:
                                                return query(fitness);
                                        }
                                        break;
                                    case 2:
                                        switch (get(3, 0, 3)) {
                                            case 0:
                                            case 4:
                                            case 1:
                                                clear();
                                                break;
                                            case 2:
                                                switch (get(4, 3, 7)) {
                                                    case 0:
                                                    case 1:
                                                        clear();
                                                        break;
                                                    case 2:
                                                        type("01110011");
                                                        clear();
                                                        break;
                                                    case 3:
                                                        switch (get(5, 0, 6)) {
                                                            case 0:
                                                            case 7:
                                                            case 6:
                                                            case 5:
                                                            case 1:
                                                                clear();
                                                                break;
                                                            case 2:
                                                                type("10111101");
                                                                clear();
                                                                break;
                                                            case 3:
                                                                type("10001111");
                                                                clear();
                                                                break;
                                                            case 4:
                                                                type("10001100");
                                                                clear();
                                                                break;
                                                            case -1:
                                                                return query(fitness);
                                                        }
                                                        break;
                                                    case 4:
                                                        switch (get(5, 0, 6)) {
                                                            case 0:
                                                            case 7:
                                                            case 6:
                                                            case 5:
                                                            case 4:
                                                                clear();
                                                                break;
                                                            case 1:
                                                                type("10111111");
                                                                clear();
                                                                break;
                                                            case 2:
                                                                type("10111100");
                                                                clear();
                                                                break;
                                                            case 3:
                                                                type("10001110");
                                                                clear();
                                                                break;
                                                            case -1:
                                                                return query(fitness);
                                                        }
                                                        break;
                                                    case 5:
                                                        type("01000000");
                                                        clear();
                                                        break;
                                                    case -1:
                                                        return query(fitness);
                                                }
                                                break;
                                            case 3:
                                                switch (get(4, 5, 7)) {
                                                    case 0:
                                                        type("01001111");
                                                        clear();
                                                        break;
                                                    case 1:
                                                        switch (get(5, 0, 6)) {
                                                            case 0:
                                                            case 7:
                                                            case 6:
                                                            case 5:
                                                            case 1:
                                                                clear();
                                                                break;
                                                            case 2:
                                                                type("10111101");
                                                                clear();
                                                                break;
                                                            case 3:
                                                                type("10110011");
                                                                clear();
                                                                break;
                                                            case 4:
                                                                type("10110000");
                                                                clear();
                                                                break;
                                                            case -1:
                                                                return query(fitness);
                                                        }
                                                        break;
                                                    case 2:
                                                        switch (get(5, 0, 6)) {
                                                            case 0:
                                                            case 7:
                                                            case 6:
                                                            case 5:
                                                            case 4:
                                                                clear();
                                                                break;
                                                            case 1:
                                                                type("10111111");
                                                                clear();
                                                                break;
                                                            case 2:
                                                                type("10111100");
                                                                clear();
                                                                break;
                                                            case 3:
                                                                type("10110010");
                                                                clear();
                                                                break;
                                                            case -1:
                                                                return query(fitness);
                                                        }
                                                        break;
                                                    case 3:
                                                        type("01000000");
                                                        clear();
                                                        break;
                                                    case -1:
                                                        return query(fitness);
                                                }
                                                break;
                                            case -1:
                                                return query(fitness);
                                        }
                                        break;
                                    case 3:
                                        switch (get(3, 4, 7)) {
                                            case 0:
                                                type("10111111");
                                                clear();
                                                break;
                                            case 1:
                                                switch (get(4, 0, 4)) {
                                                    case 0:
                                                    case 5:
                                                    case 1:
                                                        clear();
                                                        break;
                                                    case 2:
                                                        switch (get(5, 0, 2)) {
                                                            case 0:
                                                            case 3:
                                                                clear();
                                                                break;
                                                            case 1:
                                                                type("10111111");
                                                                clear();
                                                                break;
                                                            case 2:
                                                                type("10001111");
                                                                clear();
                                                                break;
                                                            case -1:
                                                                return query(fitness);
                                                        }
                                                        break;
                                                    case 3:
                                                        switch (get(5, 0, 2)) {
                                                            case 0:
                                                            case 3:
                                                                clear();
                                                                break;
                                                            case 1:
                                                                type("10110011");
                                                                clear();
                                                                break;
                                                            case 2:
                                                                type("10000011");
                                                                clear();
                                                                break;
                                                            case -1:
                                                                return query(fitness);
                                                        }
                                                        break;
                                                    case 4:
                                                        switch (get(5, 0, 6)) {
                                                            case 0:
                                                            case 7:
                                                            case 6:
                                                            case 5:
                                                            case 4:
                                                            case 1:
                                                                clear();
                                                                break;
                                                            case 2:
                                                                type("10111011");
                                                                clear();
                                                                break;
                                                            case 3:
                                                                type("10111000");
                                                                clear();
                                                                break;
                                                            case -1:
                                                                return query(fitness);
                                                        }
                                                        break;
                                                    case -1:
                                                        return query(fitness);
                                                }
                                                break;
                                            case 2:
                                                switch (get(4, 3, 4)) {
                                                    case 0:
                                                        switch (get(5, 0, 6)) {
                                                            case 0:
                                                            case 7:
                                                            case 3:
                                                            case 2:
                                                            case 1:
                                                                clear();
                                                                break;
                                                            case 4:
                                                                type("01100101");
                                                                clear();
                                                                break;
                                                            case 5:
                                                                type("01000011");
                                                                clear();
                                                                break;
                                                            case 6:
                                                                type("01000000");
                                                                clear();
                                                                break;
                                                            case -1:
                                                                return query(fitness);
                                                        }
                                                        break;
                                                    case 1:
                                                        switch (get(5, 4, 6)) {
                                                            case 0:
                                                                type("10111111");
                                                                clear();
                                                                break;
                                                            case 1:
                                                                type("10111100");
                                                                clear();
                                                                break;
                                                            case 2:
                                                                type("10000011");
                                                                clear();
                                                                break;
                                                            case 3:
                                                                type("10000000");
                                                                clear();
                                                                break;
                                                            case -1:
                                                                return query(fitness);
                                                        }
                                                        break;
                                                    case 2:
                                                        switch (get(5, 0, 6)) {
                                                            case 0:
                                                            case 7:
                                                            case 6:
                                                            case 5:
                                                            case 4:
                                                                clear();
                                                                break;
                                                            case 1:
                                                                type("01111111");
                                                                clear();
                                                                break;
                                                            case 2:
                                                                type("01111100");
                                                                clear();
                                                                break;
                                                            case 3:
                                                                type("01011010");
                                                                clear();
                                                                break;
                                                            case -1:
                                                                return query(fitness);
                                                        }
                                                        break;
                                                    case -1:
                                                        return query(fitness);
                                                }
                                                break;
                                            case 3:
                                                switch (get(4, 0, 4)) {
                                                    case 0:
                                                    case 5:
                                                    case 4:
                                                        clear();
                                                        break;
                                                    case 1:
                                                        switch (get(5, 0, 6)) {
                                                            case 0:
                                                            case 7:
                                                            case 6:
                                                            case 3:
                                                            case 2:
                                                            case 1:
                                                                clear();
                                                                break;
                                                            case 4:
                                                                type("10000111");
                                                                clear();
                                                                break;
                                                            case 5:
                                                                type("10000100");
                                                                clear();
                                                                break;
                                                            case -1:
                                                                return query(fitness);
                                                        }
                                                        break;
                                                    case 2:
                                                        switch (get(5, 0, 2)) {
                                                            case 0:
                                                            case 3:
                                                                clear();
                                                                break;
                                                            case 1:
                                                                type("10111100");
                                                                clear();
                                                                break;
                                                            case 2:
                                                                type("10001100");
                                                                clear();
                                                                break;
                                                            case -1:
                                                                return query(fitness);
                                                        }
                                                        break;
                                                    case 3:
                                                        switch (get(5, 0, 2)) {
                                                            case 0:
                                                            case 3:
                                                                clear();
                                                                break;
                                                            case 1:
                                                                type("10110000");
                                                                clear();
                                                                break;
                                                            case 2:
                                                                type("10000000");
                                                                clear();
                                                                break;
                                                            case -1:
                                                                return query(fitness);
                                                        }
                                                        break;
                                                    case -1:
                                                        return query(fitness);
                                                }
                                                break;
                                            case 4:
                                                type("10000000");
                                                clear();
                                                break;
                                            case -1:
                                                return query(fitness);
                                        }
                                        break;
                                    case 4:
                                        switch (get(3, 0, 3)) {
                                            case 0:
                                            case 4:
                                            case 3:
                                                clear();
                                                break;
                                            case 1:
                                                switch (get(4, 5, 7)) {
                                                    case 0:
                                                        type("01111111");
                                                        clear();
                                                        break;
                                                    case 1:
                                                        switch (get(5, 0, 6)) {
                                                            case 0:
                                                            case 7:
                                                            case 3:
                                                            case 2:
                                                            case 1:
                                                                clear();
                                                                break;
                                                            case 4:
                                                                type("10001101");
                                                                clear();
                                                                break;
                                                            case 5:
                                                                type("10000011");
                                                                clear();
                                                                break;
                                                            case 6:
                                                                type("10000000");
                                                                clear();
                                                                break;
                                                            case -1:
                                                                return query(fitness);
                                                        }
                                                        break;
                                                    case 2:
                                                        switch (get(5, 0, 6)) {
                                                            case 0:
                                                            case 7:
                                                            case 6:
                                                            case 2:
                                                            case 1:
                                                                clear();
                                                                break;
                                                            case 3:
                                                                type("10001111");
                                                                clear();
                                                                break;
                                                            case 4:
                                                                type("10001100");
                                                                clear();
                                                                break;
                                                            case 5:
                                                                type("10000010");
                                                                clear();
                                                                break;
                                                            case -1:
                                                                return query(fitness);
                                                        }
                                                        break;
                                                    case 3:
                                                        type("01110000");
                                                        clear();
                                                        break;
                                                    case -1:
                                                        return query(fitness);
                                                }
                                                break;
                                            case 2:
                                                switch (get(4, 3, 7)) {
                                                    case 0:
                                                        type("01111111");
                                                        clear();
                                                        break;
                                                    case 1:
                                                        switch (get(5, 0, 6)) {
                                                            case 0:
                                                            case 7:
                                                            case 3:
                                                            case 2:
                                                            case 1:
                                                                clear();
                                                                break;
                                                            case 4:
                                                                type("10110001");
                                                                clear();
                                                                break;
                                                            case 5:
                                                                type("10000011");
                                                                clear();
                                                                break;
                                                            case 6:
                                                                type("10000000");
                                                                clear();
                                                                break;
                                                            case -1:
                                                                return query(fitness);
                                                        }
                                                        break;
                                                    case 2:
                                                        switch (get(5, 0, 6)) {
                                                            case 0:
                                                            case 7:
                                                            case 6:
                                                            case 2:
                                                            case 1:
                                                                clear();
                                                                break;
                                                            case 3:
                                                                type("10110011");
                                                                clear();
                                                                break;
                                                            case 4:
                                                                type("10110000");
                                                                clear();
                                                                break;
                                                            case 5:
                                                                type("10000010");
                                                                clear();
                                                                break;
                                                            case -1:
                                                                return query(fitness);
                                                        }
                                                        break;
                                                    case 3:
                                                        type("01001100");
                                                        clear();
                                                        break;
                                                    case 4:
                                                    case 5:
                                                        clear();
                                                        break;
                                                    case -1:
                                                        return query(fitness);
                                                }
                                                break;
                                            case -1:
                                                return query(fitness);
                                        }
                                        break;
                                    case 5:
                                        switch (get(3, 0, 0)) {
                                            case 0:
                                                clear();
                                                break;
                                            case 1:
                                                switch (get(4, 0, 6)) {
                                                    case 0:
                                                        switch (get(5, 0, 7)) {
                                                            case 0:
                                                            case 6:
                                                            case 5:
                                                            case 4:
                                                            case 3:
                                                            case 2:
                                                            case 1:
                                                                clear();
                                                                break;
                                                            case 7:
                                                                type("00000001");
                                                                clear();
                                                                break;
                                                            case 8:
                                                                type("00000000");
                                                                clear();
                                                                break;
                                                            case -1:
                                                                return query(fitness);
                                                        }
                                                        break;
                                                    case 1:
                                                        switch (get(5, 0, 7)) {
                                                            case 0:
                                                            case 8:
                                                            case 5:
                                                            case 4:
                                                            case 3:
                                                            case 2:
                                                            case 1:
                                                                clear();
                                                                break;
                                                            case 6:
                                                                type("00000011");
                                                                clear();
                                                                break;
                                                            case 7:
                                                                type("00000010");
                                                                clear();
                                                                break;
                                                            case -1:
                                                                return query(fitness);
                                                        }
                                                        break;
                                                    case 2:
                                                    case 7:
                                                    case 6:
                                                    case 5:
                                                    case 4:
                                                    case 3:
                                                        clear();
                                                        break;
                                                    case -1:
                                                        return query(fitness);
                                                }
                                                break;
                                            case -1:
                                                return query(fitness);
                                        }
                                        break;
                                    case -1:
                                        return query(fitness);
                                }
                                break;
                            case 2:
                                switch (get(2, 0, 5)) {
                                    case 0:
                                        switch (get(3, 0, 0)) {
                                            case 0:
                                                clear();
                                                break;
                                            case 1:
                                                switch (get(4, 0, 6)) {
                                                    case 0:
                                                    case 7:
                                                    case 4:
                                                    case 3:
                                                    case 2:
                                                    case 1:
                                                        clear();
                                                        break;
                                                    case 5:
                                                        switch (get(5, 0, 7)) {
                                                            case 0:
                                                            case 8:
                                                            case 7:
                                                            case 6:
                                                            case 5:
                                                            case 4:
                                                            case 1:
                                                                clear();
                                                                break;
                                                            case 2:
                                                                type("01111101");
                                                                clear();
                                                                break;
                                                            case 3:
                                                                type("01111100");
                                                                clear();
                                                                break;
                                                            case -1:
                                                                return query(fitness);
                                                        }
                                                        break;
                                                    case 6:
                                                        switch (get(5, 0, 7)) {
                                                            case 0:
                                                            case 8:
                                                            case 7:
                                                            case 6:
                                                            case 5:
                                                            case 4:
                                                            case 3:
                                                                clear();
                                                                break;
                                                            case 1:
                                                                type("01111111");
                                                                clear();
                                                                break;
                                                            case 2:
                                                                type("01111110");
                                                                clear();
                                                                break;
                                                            case -1:
                                                                return query(fitness);
                                                        }
                                                        break;
                                                    case -1:
                                                        return query(fitness);
                                                }
                                                break;
                                            case -1:
                                                return query(fitness);
                                        }
                                        break;
                                    case 1:
                                        switch (get(3, 0, 3)) {
                                            case 0:
                                            case 2:
                                            case 1:
                                                clear();
                                                break;
                                            case 3:
                                                switch (get(4, 3, 7)) {
                                                    case 0:
                                                    case 1:
                                                        clear();
                                                        break;
                                                    case 2:
                                                        type("00110011");
                                                        clear();
                                                        break;
                                                    case 3:
                                                        switch (get(5, 0, 6)) {
                                                            case 0:
                                                            case 7:
                                                            case 6:
                                                            case 5:
                                                            case 4:
                                                                clear();
                                                                break;
                                                            case 1:
                                                                type("11111101");
                                                                clear();
                                                                break;
                                                            case 2:
                                                                type("11001111");
                                                                clear();
                                                                break;
                                                            case 3:
                                                                type("11001100");
                                                                clear();
                                                                break;
                                                            case -1:
                                                                return query(fitness);
                                                        }
                                                        break;
                                                    case 4:
                                                        switch (get(5, 0, 6)) {
                                                            case 0:
                                                                type("11111111");
                                                                clear();
                                                                break;
                                                            case 1:
                                                                type("11111100");
                                                                clear();
                                                                break;
                                                            case 2:
                                                                type("11001110");
                                                                clear();
                                                                break;
                                                            case 3:
                                                            case 7:
                                                            case 6:
                                                            case 5:
                                                            case 4:
                                                                clear();
                                                                break;
                                                            case -1:
                                                                return query(fitness);
                                                        }
                                                        break;
                                                    case 5:
                                                        type("00000000");
                                                        clear();
                                                        break;
                                                    case -1:
                                                        return query(fitness);
                                                }
                                                break;
                                            case 4:
                                                switch (get(4, 5, 7)) {
                                                    case 0:
                                                        type("00001111");
                                                        clear();
                                                        break;
                                                    case 1:
                                                        switch (get(5, 0, 6)) {
                                                            case 0:
                                                            case 7:
                                                            case 6:
                                                            case 5:
                                                            case 4:
                                                                clear();
                                                                break;
                                                            case 1:
                                                                type("11111101");
                                                                clear();
                                                                break;
                                                            case 2:
                                                                type("11110011");
                                                                clear();
                                                                break;
                                                            case 3:
                                                                type("11110000");
                                                                clear();
                                                                break;
                                                            case -1:
                                                                return query(fitness);
                                                        }
                                                        break;
                                                    case 2:
                                                        switch (get(5, 0, 6)) {
                                                            case 0:
                                                                type("11111111");
                                                                clear();
                                                                break;
                                                            case 1:
                                                                type("11111100");
                                                                clear();
                                                                break;
                                                            case 2:
                                                                type("11110010");
                                                                clear();
                                                                break;
                                                            case 3:
                                                            case 7:
                                                            case 6:
                                                            case 5:
                                                            case 4:
                                                                clear();
                                                                break;
                                                            case -1:
                                                                return query(fitness);
                                                        }
                                                        break;
                                                    case 3:
                                                        type("00000000");
                                                        clear();
                                                        break;
                                                    case -1:
                                                        return query(fitness);
                                                }
                                                break;
                                            case -1:
                                                return query(fitness);
                                        }
                                        break;
                                    case 2:
                                        switch (get(3, 4, 7)) {
                                            case 0:
                                                type("11111111");
                                                clear();
                                                break;
                                            case 1:
                                                switch (get(4, 0, 4)) {
                                                    case 0:
                                                    case 2:
                                                    case 1:
                                                        clear();
                                                        break;
                                                    case 3:
                                                        switch (get(5, 0, 2)) {
                                                            case 0:
                                                                type("11111111");
                                                                clear();
                                                                break;
                                                            case 1:
                                                                type("11001111");
                                                                clear();
                                                                break;
                                                            case 2:
                                                            case 3:
                                                                clear();
                                                                break;
                                                            case -1:
                                                                return query(fitness);
                                                        }
                                                        break;
                                                    case 4:
                                                        switch (get(5, 0, 2)) {
                                                            case 0:
                                                                type("11110011");
                                                                clear();
                                                                break;
                                                            case 1:
                                                                type("11000011");
                                                                clear();
                                                                break;
                                                            case 2:
                                                            case 3:
                                                                clear();
                                                                break;
                                                            case -1:
                                                                return query(fitness);
                                                        }
                                                        break;
                                                    case 5:
                                                        switch (get(5, 0, 6)) {
                                                            case 0:
                                                            case 7:
                                                            case 6:
                                                            case 5:
                                                            case 4:
                                                            case 3:
                                                                clear();
                                                                break;
                                                            case 1:
                                                                type("11111011");
                                                                clear();
                                                                break;
                                                            case 2:
                                                                type("11111000");
                                                                clear();
                                                                break;
                                                            case -1:
                                                                return query(fitness);
                                                        }
                                                        break;
                                                    case -1:
                                                        return query(fitness);
                                                }
                                                break;
                                            case 2:
                                                switch (get(4, 3, 4)) {
                                                    case 0:
                                                        switch (get(5, 0, 6)) {
                                                            case 0:
                                                            case 4:
                                                            case 3:
                                                            case 2:
                                                            case 1:
                                                                clear();
                                                                break;
                                                            case 5:
                                                                type("00100101");
                                                                clear();
                                                                break;
                                                            case 6:
                                                                type("00000011");
                                                                clear();
                                                                break;
                                                            case 7:
                                                                type("00000000");
                                                                clear();
                                                                break;
                                                            case -1:
                                                                return query(fitness);
                                                        }
                                                        break;
                                                    case 1:
                                                        switch (get(5, 4, 6)) {
                                                            case 0:
                                                                type("11111111");
                                                                clear();
                                                                break;
                                                            case 1:
                                                                type("11111100");
                                                                clear();
                                                                break;
                                                            case 2:
                                                                type("11000011");
                                                                clear();
                                                                break;
                                                            case 3:
                                                                type("11000000");
                                                                clear();
                                                                break;
                                                            case -1:
                                                                return query(fitness);
                                                        }
                                                        break;
                                                    case 2:
                                                        switch (get(5, 0, 6)) {
                                                            case 0:
                                                            case 7:
                                                            case 6:
                                                            case 5:
                                                            case 1:
                                                                clear();
                                                                break;
                                                            case 2:
                                                                type("00111111");
                                                                clear();
                                                                break;
                                                            case 3:
                                                                type("00111100");
                                                                clear();
                                                                break;
                                                            case 4:
                                                                type("00011010");
                                                                clear();
                                                                break;
                                                            case -1:
                                                                return query(fitness);
                                                        }
                                                        break;
                                                    case -1:
                                                        return query(fitness);
                                                }
                                                break;
                                            case 3:
                                                switch (get(4, 0, 4)) {
                                                    case 0:
                                                    case 5:
                                                    case 1:
                                                        clear();
                                                        break;
                                                    case 2:
                                                        switch (get(5, 0, 6)) {
                                                            case 0:
                                                            case 7:
                                                            case 6:
                                                            case 5:
                                                            case 2:
                                                            case 1:
                                                                clear();
                                                                break;
                                                            case 3:
                                                                type("11000111");
                                                                clear();
                                                                break;
                                                            case 4:
                                                                type("11000100");
                                                                clear();
                                                                break;
                                                            case -1:
                                                                return query(fitness);
                                                        }
                                                        break;
                                                    case 3:
                                                        switch (get(5, 0, 2)) {
                                                            case 0:
                                                                type("11111100");
                                                                clear();
                                                                break;
                                                            case 1:
                                                                type("11001100");
                                                                clear();
                                                                break;
                                                            case 2:
                                                            case 3:
                                                                clear();
                                                                break;
                                                            case -1:
                                                                return query(fitness);
                                                        }
                                                        break;
                                                    case 4:
                                                        switch (get(5, 0, 2)) {
                                                            case 0:
                                                                type("11110000");
                                                                clear();
                                                                break;
                                                            case 1:
                                                                type("11000000");
                                                                clear();
                                                                break;
                                                            case 2:
                                                            case 3:
                                                                clear();
                                                                break;
                                                            case -1:
                                                                return query(fitness);
                                                        }
                                                        break;
                                                    case -1:
                                                        return query(fitness);
                                                }
                                                break;
                                            case 4:
                                                type("11000000");
                                                clear();
                                                break;
                                            case -1:
                                                return query(fitness);
                                        }
                                        break;
                                    case 3:
                                        switch (get(3, 0, 3)) {
                                            case 0:
                                            case 4:
                                            case 1:
                                                clear();
                                                break;
                                            case 2:
                                                switch (get(4, 5, 7)) {
                                                    case 0:
                                                        type("00111111");
                                                        clear();
                                                        break;
                                                    case 1:
                                                        switch (get(5, 0, 6)) {
                                                            case 0:
                                                            case 7:
                                                            case 6:
                                                            case 2:
                                                            case 1:
                                                                clear();
                                                                break;
                                                            case 3:
                                                                type("11001101");
                                                                clear();
                                                                break;
                                                            case 4:
                                                                type("11000011");
                                                                clear();
                                                                break;
                                                            case 5:
                                                                type("11000000");
                                                                clear();
                                                                break;
                                                            case -1:
                                                                return query(fitness);
                                                        }
                                                        break;
                                                    case 2:
                                                        switch (get(5, 0, 6)) {
                                                            case 0:
                                                            case 7:
                                                            case 6:
                                                            case 5:
                                                            case 1:
                                                                clear();
                                                                break;
                                                            case 2:
                                                                type("11001111");
                                                                clear();
                                                                break;
                                                            case 3:
                                                                type("11001100");
                                                                clear();
                                                                break;
                                                            case 4:
                                                                type("11000010");
                                                                clear();
                                                                break;
                                                            case -1:
                                                                return query(fitness);
                                                        }
                                                        break;
                                                    case 3:
                                                        type("00110000");
                                                        clear();
                                                        break;
                                                    case -1:
                                                        return query(fitness);
                                                }
                                                break;
                                            case 3:
                                                switch (get(4, 3, 7)) {
                                                    case 0:
                                                        type("00111111");
                                                        clear();
                                                        break;
                                                    case 1:
                                                        switch (get(5, 0, 6)) {
                                                            case 0:
                                                            case 7:
                                                            case 6:
                                                            case 2:
                                                            case 1:
                                                                clear();
                                                                break;
                                                            case 3:
                                                                type("11110001");
                                                                clear();
                                                                break;
                                                            case 4:
                                                                type("11000011");
                                                                clear();
                                                                break;
                                                            case 5:
                                                                type("11000000");
                                                                clear();
                                                                break;
                                                            case -1:
                                                                return query(fitness);
                                                        }
                                                        break;
                                                    case 2:
                                                        switch (get(5, 0, 6)) {
                                                            case 0:
                                                            case 7:
                                                            case 6:
                                                            case 5:
                                                            case 1:
                                                                clear();
                                                                break;
                                                            case 2:
                                                                type("11110011");
                                                                clear();
                                                                break;
                                                            case 3:
                                                                type("11110000");
                                                                clear();
                                                                break;
                                                            case 4:
                                                                type("11000010");
                                                                clear();
                                                                break;
                                                            case -1:
                                                                return query(fitness);
                                                        }
                                                        break;
                                                    case 3:
                                                        type("00001100");
                                                        clear();
                                                        break;
                                                    case 4:
                                                    case 5:
                                                        clear();
                                                        break;
                                                    case -1:
                                                        return query(fitness);
                                                }
                                                break;
                                            case -1:
                                                return query(fitness);
                                        }
                                        break;
                                    case 4:
                                        switch (get(3, 0, 0)) {
                                            case 0:
                                                clear();
                                                break;
                                            case 1:
                                                switch (get(4, 0, 6)) {
                                                    case 0:
                                                    case 7:
                                                    case 6:
                                                    case 5:
                                                    case 4:
                                                    case 3:
                                                        clear();
                                                        break;
                                                    case 1:
                                                        switch (get(5, 0, 7)) {
                                                            case 0:
                                                            case 8:
                                                            case 5:
                                                            case 4:
                                                            case 3:
                                                            case 2:
                                                            case 1:
                                                                clear();
                                                                break;
                                                            case 6:
                                                                type("01000001");
                                                                clear();
                                                                break;
                                                            case 7:
                                                                type("01000000");
                                                                clear();
                                                                break;
                                                            case -1:
                                                                return query(fitness);
                                                        }
                                                        break;
                                                    case 2:
                                                        switch (get(5, 0, 7)) {
                                                            case 0:
                                                            case 8:
                                                            case 7:
                                                            case 4:
                                                            case 3:
                                                            case 2:
                                                            case 1:
                                                                clear();
                                                                break;
                                                            case 5:
                                                                type("01000011");
                                                                clear();
                                                                break;
                                                            case 6:
                                                                type("01000010");
                                                                clear();
                                                                break;
                                                            case -1:
                                                                return query(fitness);
                                                        }
                                                        break;
                                                    case -1:
                                                        return query(fitness);
                                                }
                                                break;
                                            case -1:
                                                return query(fitness);
                                        }
                                        break;
                                    case 5:
                                    case 6:
                                        clear();
                                        break;
                                    case -1:
                                        return query(fitness);
                                }
                                break;
                            case -1:
                                return query(fitness);
                        }
                        break;
                    case 1:
                        switch (get(1, 0, 1)) {
                            case 0:
                                switch (get(2, 0, 5)) {
                                    case 0:
                                    case 1:
                                        clear();
                                        break;
                                    case 2:
                                        switch (get(3, 0, 0)) {
                                            case 0:
                                                switch (get(4, 0, 6)) {
                                                    case 0:
                                                    case 7:
                                                    case 4:
                                                    case 3:
                                                    case 2:
                                                    case 1:
                                                        clear();
                                                        break;
                                                    case 5:
                                                        switch (get(5, 0, 7)) {
                                                            case 0:
                                                            case 8:
                                                            case 7:
                                                            case 6:
                                                            case 5:
                                                            case 4:
                                                            case 1:
                                                                clear();
                                                                break;
                                                            case 2:
                                                                type("10111101");
                                                                clear();
                                                                break;
                                                            case 3:
                                                                type("10111100");
                                                                clear();
                                                                break;
                                                            case -1:
                                                                return query(fitness);
                                                        }
                                                        break;
                                                    case 6:
                                                        switch (get(5, 0, 7)) {
                                                            case 0:
                                                            case 8:
                                                            case 7:
                                                            case 6:
                                                            case 5:
                                                            case 4:
                                                            case 3:
                                                                clear();
                                                                break;
                                                            case 1:
                                                                type("10111111");
                                                                clear();
                                                                break;
                                                            case 2:
                                                                type("10111110");
                                                                clear();
                                                                break;
                                                            case -1:
                                                                return query(fitness);
                                                        }
                                                        break;
                                                    case -1:
                                                        return query(fitness);
                                                }
                                                break;
                                            case 1:
                                                clear();
                                                break;
                                            case -1:
                                                return query(fitness);
                                        }
                                        break;
                                    case 3:
                                        switch (get(3, 0, 3)) {
                                            case 0:
                                            case 4:
                                            case 3:
                                                clear();
                                                break;
                                            case 1:
                                                switch (get(4, 3, 7)) {
                                                    case 0:
                                                    case 1:
                                                        clear();
                                                        break;
                                                    case 2:
                                                        type("11110011");
                                                        clear();
                                                        break;
                                                    case 3:
                                                        switch (get(5, 0, 6)) {
                                                            case 0:
                                                            case 7:
                                                            case 6:
                                                            case 2:
                                                            case 1:
                                                                clear();
                                                                break;
                                                            case 3:
                                                                type("00111101");
                                                                clear();
                                                                break;
                                                            case 4:
                                                                type("00001111");
                                                                clear();
                                                                break;
                                                            case 5:
                                                                type("00001100");
                                                                clear();
                                                                break;
                                                            case -1:
                                                                return query(fitness);
                                                        }
                                                        break;
                                                    case 4:
                                                        switch (get(5, 0, 6)) {
                                                            case 0:
                                                            case 7:
                                                            case 6:
                                                            case 5:
                                                            case 1:
                                                                clear();
                                                                break;
                                                            case 2:
                                                                type("00111111");
                                                                clear();
                                                                break;
                                                            case 3:
                                                                type("00111100");
                                                                clear();
                                                                break;
                                                            case 4:
                                                                type("00001110");
                                                                clear();
                                                                break;
                                                            case -1:
                                                                return query(fitness);
                                                        }
                                                        break;
                                                    case 5:
                                                        type("11000000");
                                                        clear();
                                                        break;
                                                    case -1:
                                                        return query(fitness);
                                                }
                                                break;
                                            case 2:
                                                switch (get(4, 5, 7)) {
                                                    case 0:
                                                        type("11001111");
                                                        clear();
                                                        break;
                                                    case 1:
                                                        switch (get(5, 0, 6)) {
                                                            case 0:
                                                            case 7:
                                                            case 6:
                                                            case 2:
                                                            case 1:
                                                                clear();
                                                                break;
                                                            case 3:
                                                                type("00111101");
                                                                clear();
                                                                break;
                                                            case 4:
                                                                type("00110011");
                                                                clear();
                                                                break;
                                                            case 5:
                                                                type("00110000");
                                                                clear();
                                                                break;
                                                            case -1:
                                                                return query(fitness);
                                                        }
                                                        break;
                                                    case 2:
                                                        switch (get(5, 0, 6)) {
                                                            case 0:
                                                            case 7:
                                                            case 6:
                                                            case 5:
                                                            case 1:
                                                                clear();
                                                                break;
                                                            case 2:
                                                                type("00111111");
                                                                clear();
                                                                break;
                                                            case 3:
                                                                type("00111100");
                                                                clear();
                                                                break;
                                                            case 4:
                                                                type("00110010");
                                                                clear();
                                                                break;
                                                            case -1:
                                                                return query(fitness);
                                                        }
                                                        break;
                                                    case 3:
                                                        type("11000000");
                                                        clear();
                                                        break;
                                                    case -1:
                                                        return query(fitness);
                                                }
                                                break;
                                            case -1:
                                                return query(fitness);
                                        }
                                        break;
                                    case 4:
                                        switch (get(3, 4, 7)) {
                                            case 0:
                                                type("00111111");
                                                clear();
                                                break;
                                            case 1:
                                                switch (get(4, 0, 4)) {
                                                    case 0:
                                                    case 5:
                                                    case 4:
                                                        clear();
                                                        break;
                                                    case 1:
                                                        switch (get(5, 0, 2)) {
                                                            case 0:
                                                            case 1:
                                                                clear();
                                                                break;
                                                            case 2:
                                                                type("00111111");
                                                                clear();
                                                                break;
                                                            case 3:
                                                                type("00001111");
                                                                clear();
                                                                break;
                                                            case -1:
                                                                return query(fitness);
                                                        }
                                                        break;
                                                    case 2:
                                                        switch (get(5, 0, 2)) {
                                                            case 0:
                                                            case 1:
                                                                clear();
                                                                break;
                                                            case 2:
                                                                type("00110011");
                                                                clear();
                                                                break;
                                                            case 3:
                                                                type("00000011");
                                                                clear();
                                                                break;
                                                            case -1:
                                                                return query(fitness);
                                                        }
                                                        break;
                                                    case 3:
                                                        switch (get(5, 0, 6)) {
                                                            case 0:
                                                            case 7:
                                                            case 6:
                                                            case 5:
                                                            case 2:
                                                            case 1:
                                                                clear();
                                                                break;
                                                            case 3:
                                                                type("00111011");
                                                                clear();
                                                                break;
                                                            case 4:
                                                                type("00111000");
                                                                clear();
                                                                break;
                                                            case -1:
                                                                return query(fitness);
                                                        }
                                                        break;
                                                    case -1:
                                                        return query(fitness);
                                                }
                                                break;
                                            case 2:
                                                switch (get(4, 3, 4)) {
                                                    case 0:
                                                        switch (get(5, 0, 6)) {
                                                            case 0:
                                                            case 7:
                                                            case 6:
                                                            case 2:
                                                            case 1:
                                                                clear();
                                                                break;
                                                            case 3:
                                                                type("11100101");
                                                                clear();
                                                                break;
                                                            case 4:
                                                                type("11000011");
                                                                clear();
                                                                break;
                                                            case 5:
                                                                type("11000000");
                                                                clear();
                                                                break;
                                                            case -1:
                                                                return query(fitness);
                                                        }
                                                        break;
                                                    case 1:
                                                        switch (get(5, 4, 6)) {
                                                            case 0:
                                                                type("00111111");
                                                                clear();
                                                                break;
                                                            case 1:
                                                                type("00111100");
                                                                clear();
                                                                break;
                                                            case 2:
                                                                type("00000011");
                                                                clear();
                                                                break;
                                                            case 3:
                                                                type("00000000");
                                                                clear();
                                                                break;
                                                            case -1:
                                                                return query(fitness);
                                                        }
                                                        break;
                                                    case 2:
                                                        switch (get(5, 0, 6)) {
                                                            case 0:
                                                                type("11111111");
                                                                clear();
                                                                break;
                                                            case 1:
                                                                type("11111100");
                                                                clear();
                                                                break;
                                                            case 2:
                                                                type("11011010");
                                                                clear();
                                                                break;
                                                            case 3:
                                                            case 7:
                                                            case 6:
                                                            case 5:
                                                            case 4:
                                                                clear();
                                                                break;
                                                            case -1:
                                                                return query(fitness);
                                                        }
                                                        break;
                                                    case -1:
                                                        return query(fitness);
                                                }
                                                break;
                                            case 3:
                                                switch (get(4, 0, 4)) {
                                                    case 0:
                                                        switch (get(5, 0, 6)) {
                                                            case 0:
                                                            case 7:
                                                            case 4:
                                                            case 3:
                                                            case 2:
                                                            case 1:
                                                                clear();
                                                                break;
                                                            case 5:
                                                                type("00000111");
                                                                clear();
                                                                break;
                                                            case 6:
                                                                type("00000100");
                                                                clear();
                                                                break;
                                                            case -1:
                                                                return query(fitness);
                                                        }
                                                        break;
                                                    case 1:
                                                        switch (get(5, 0, 2)) {
                                                            case 0:
                                                            case 1:
                                                                clear();
                                                                break;
                                                            case 2:
                                                                type("00111100");
                                                                clear();
                                                                break;
                                                            case 3:
                                                                type("00001100");
                                                                clear();
                                                                break;
                                                            case -1:
                                                                return query(fitness);
                                                        }
                                                        break;
                                                    case 2:
                                                        switch (get(5, 0, 2)) {
                                                            case 0:
                                                            case 1:
                                                                clear();
                                                                break;
                                                            case 2:
                                                                type("00110000");
                                                                clear();
                                                                break;
                                                            case 3:
                                                                type("00000000");
                                                                clear();
                                                                break;
                                                            case -1:
                                                                return query(fitness);
                                                        }
                                                        break;
                                                    case 3:
                                                    case 5:
                                                    case 4:
                                                        clear();
                                                        break;
                                                    case -1:
                                                        return query(fitness);
                                                }
                                                break;
                                            case 4:
                                                type("00000000");
                                                clear();
                                                break;
                                            case -1:
                                                return query(fitness);
                                        }
                                        break;
                                    case 5:
                                        switch (get(3, 0, 3)) {
                                            case 0:
                                                switch (get(4, 5, 7)) {
                                                    case 0:
                                                        type("11111111");
                                                        clear();
                                                        break;
                                                    case 1:
                                                        switch (get(5, 0, 6)) {
                                                            case 0:
                                                            case 4:
                                                            case 3:
                                                            case 2:
                                                            case 1:
                                                                clear();
                                                                break;
                                                            case 5:
                                                                type("00001101");
                                                                clear();
                                                                break;
                                                            case 6:
                                                                type("00000011");
                                                                clear();
                                                                break;
                                                            case 7:
                                                                type("00000000");
                                                                clear();
                                                                break;
                                                            case -1:
                                                                return query(fitness);
                                                        }
                                                        break;
                                                    case 2:
                                                        switch (get(5, 0, 6)) {
                                                            case 0:
                                                            case 7:
                                                            case 3:
                                                            case 2:
                                                            case 1:
                                                                clear();
                                                                break;
                                                            case 4:
                                                                type("00001111");
                                                                clear();
                                                                break;
                                                            case 5:
                                                                type("00001100");
                                                                clear();
                                                                break;
                                                            case 6:
                                                                type("00000010");
                                                                clear();
                                                                break;
                                                            case -1:
                                                                return query(fitness);
                                                        }
                                                        break;
                                                    case 3:
                                                        type("11110000");
                                                        clear();
                                                        break;
                                                    case -1:
                                                        return query(fitness);
                                                }
                                                break;
                                            case 1:
                                                switch (get(4, 3, 7)) {
                                                    case 0:
                                                        type("11111111");
                                                        clear();
                                                        break;
                                                    case 1:
                                                        switch (get(5, 0, 6)) {
                                                            case 0:
                                                            case 4:
                                                            case 3:
                                                            case 2:
                                                            case 1:
                                                                clear();
                                                                break;
                                                            case 5:
                                                                type("00110001");
                                                                clear();
                                                                break;
                                                            case 6:
                                                                type("00000011");
                                                                clear();
                                                                break;
                                                            case 7:
                                                                type("00000000");
                                                                clear();
                                                                break;
                                                            case -1:
                                                                return query(fitness);
                                                        }
                                                        break;
                                                    case 2:
                                                        switch (get(5, 0, 6)) {
                                                            case 0:
                                                            case 7:
                                                            case 3:
                                                            case 2:
                                                            case 1:
                                                                clear();
                                                                break;
                                                            case 4:
                                                                type("00110011");
                                                                clear();
                                                                break;
                                                            case 5:
                                                                type("00110000");
                                                                clear();
                                                                break;
                                                            case 6:
                                                                type("00000010");
                                                                clear();
                                                                break;
                                                            case -1:
                                                                return query(fitness);
                                                        }
                                                        break;
                                                    case 3:
                                                        type("11001100");
                                                        clear();
                                                        break;
                                                    case 4:
                                                    case 5:
                                                        clear();
                                                        break;
                                                    case -1:
                                                        return query(fitness);
                                                }
                                                break;
                                            case 2:
                                            case 4:
                                            case 3:
                                                clear();
                                                break;
                                            case -1:
                                                return query(fitness);
                                        }
                                        break;
                                    case 6:
                                        switch (get(3, 0, 0)) {
                                            case 0:
                                                switch (get(4, 0, 6)) {
                                                    case 0:
                                                    case 7:
                                                    case 6:
                                                    case 5:
                                                    case 4:
                                                    case 3:
                                                        clear();
                                                        break;
                                                    case 1:
                                                        switch (get(5, 0, 7)) {
                                                            case 0:
                                                            case 8:
                                                            case 5:
                                                            case 4:
                                                            case 3:
                                                            case 2:
                                                            case 1:
                                                                clear();
                                                                break;
                                                            case 6:
                                                                type("10000001");
                                                                clear();
                                                                break;
                                                            case 7:
                                                                type("10000000");
                                                                clear();
                                                                break;
                                                            case -1:
                                                                return query(fitness);
                                                        }
                                                        break;
                                                    case 2:
                                                        switch (get(5, 0, 7)) {
                                                            case 0:
                                                            case 8:
                                                            case 7:
                                                            case 4:
                                                            case 3:
                                                            case 2:
                                                            case 1:
                                                                clear();
                                                                break;
                                                            case 5:
                                                                type("10000011");
                                                                clear();
                                                                break;
                                                            case 6:
                                                                type("10000010");
                                                                clear();
                                                                break;
                                                            case -1:
                                                                return query(fitness);
                                                        }
                                                        break;
                                                    case -1:
                                                        return query(fitness);
                                                }
                                                break;
                                            case 1:
                                                clear();
                                                break;
                                            case -1:
                                                return query(fitness);
                                        }
                                        break;
                                    case -1:
                                        return query(fitness);
                                }
                                break;
                            case 1:
                                switch (get(2, 0, 5)) {
                                    case 0:
                                    case 6:
                                        clear();
                                        break;
                                    case 1:
                                        switch (get(3, 0, 0)) {
                                            case 0:
                                                switch (get(4, 0, 6)) {
                                                    case 0:
                                                    case 5:
                                                    case 4:
                                                    case 3:
                                                    case 2:
                                                    case 1:
                                                        clear();
                                                        break;
                                                    case 6:
                                                        switch (get(5, 0, 7)) {
                                                            case 0:
                                                            case 8:
                                                            case 7:
                                                            case 6:
                                                            case 5:
                                                            case 4:
                                                            case 3:
                                                                clear();
                                                                break;
                                                            case 1:
                                                                type("11111101");
                                                                clear();
                                                                break;
                                                            case 2:
                                                                type("11111100");
                                                                clear();
                                                                break;
                                                            case -1:
                                                                return query(fitness);
                                                        }
                                                        break;
                                                    case 7:
                                                        switch (get(5, 0, 7)) {
                                                            case 0:
                                                                type("11111111");
                                                                clear();
                                                                break;
                                                            case 1:
                                                                type("11111110");
                                                                clear();
                                                                break;
                                                            case 2:
                                                            case 8:
                                                            case 7:
                                                            case 6:
                                                            case 5:
                                                            case 4:
                                                            case 3:
                                                                clear();
                                                                break;
                                                            case -1:
                                                                return query(fitness);
                                                        }
                                                        break;
                                                    case -1:
                                                        return query(fitness);
                                                }
                                                break;
                                            case 1:
                                                clear();
                                                break;
                                            case -1:
                                                return query(fitness);
                                        }
                                        break;
                                    case 2:
                                        switch (get(3, 0, 3)) {
                                            case 0:
                                            case 4:
                                            case 1:
                                                clear();
                                                break;
                                            case 2:
                                                switch (get(4, 3, 7)) {
                                                    case 0:
                                                    case 1:
                                                        clear();
                                                        break;
                                                    case 2:
                                                        type("10110011");
                                                        clear();
                                                        break;
                                                    case 3:
                                                        switch (get(5, 0, 6)) {
                                                            case 0:
                                                            case 7:
                                                            case 6:
                                                            case 5:
                                                            case 1:
                                                                clear();
                                                                break;
                                                            case 2:
                                                                type("01111101");
                                                                clear();
                                                                break;
                                                            case 3:
                                                                type("01001111");
                                                                clear();
                                                                break;
                                                            case 4:
                                                                type("01001100");
                                                                clear();
                                                                break;
                                                            case -1:
                                                                return query(fitness);
                                                        }
                                                        break;
                                                    case 4:
                                                        switch (get(5, 0, 6)) {
                                                            case 0:
                                                            case 7:
                                                            case 6:
                                                            case 5:
                                                            case 4:
                                                                clear();
                                                                break;
                                                            case 1:
                                                                type("01111111");
                                                                clear();
                                                                break;
                                                            case 2:
                                                                type("01111100");
                                                                clear();
                                                                break;
                                                            case 3:
                                                                type("01001110");
                                                                clear();
                                                                break;
                                                            case -1:
                                                                return query(fitness);
                                                        }
                                                        break;
                                                    case 5:
                                                        type("10000000");
                                                        clear();
                                                        break;
                                                    case -1:
                                                        return query(fitness);
                                                }
                                                break;
                                            case 3:
                                                switch (get(4, 5, 7)) {
                                                    case 0:
                                                        type("10001111");
                                                        clear();
                                                        break;
                                                    case 1:
                                                        switch (get(5, 0, 6)) {
                                                            case 0:
                                                            case 7:
                                                            case 6:
                                                            case 5:
                                                            case 1:
                                                                clear();
                                                                break;
                                                            case 2:
                                                                type("01111101");
                                                                clear();
                                                                break;
                                                            case 3:
                                                                type("01110011");
                                                                clear();
                                                                break;
                                                            case 4:
                                                                type("01110000");
                                                                clear();
                                                                break;
                                                            case -1:
                                                                return query(fitness);
                                                        }
                                                        break;
                                                    case 2:
                                                        switch (get(5, 0, 6)) {
                                                            case 0:
                                                            case 7:
                                                            case 6:
                                                            case 5:
                                                            case 4:
                                                                clear();
                                                                break;
                                                            case 1:
                                                                type("01111111");
                                                                clear();
                                                                break;
                                                            case 2:
                                                                type("01111100");
                                                                clear();
                                                                break;
                                                            case 3:
                                                                type("01110010");
                                                                clear();
                                                                break;
                                                            case -1:
                                                                return query(fitness);
                                                        }
                                                        break;
                                                    case 3:
                                                        type("10000000");
                                                        clear();
                                                        break;
                                                    case -1:
                                                        return query(fitness);
                                                }
                                                break;
                                            case -1:
                                                return query(fitness);
                                        }
                                        break;
                                    case 3:
                                        switch (get(3, 4, 7)) {
                                            case 0:
                                                type("01111111");
                                                clear();
                                                break;
                                            case 1:
                                                switch (get(4, 0, 4)) {
                                                    case 0:
                                                    case 5:
                                                    case 1:
                                                        clear();
                                                        break;
                                                    case 2:
                                                        switch (get(5, 0, 2)) {
                                                            case 0:
                                                            case 3:
                                                                clear();
                                                                break;
                                                            case 1:
                                                                type("01111111");
                                                                clear();
                                                                break;
                                                            case 2:
                                                                type("01001111");
                                                                clear();
                                                                break;
                                                            case -1:
                                                                return query(fitness);
                                                        }
                                                        break;
                                                    case 3:
                                                        switch (get(5, 0, 2)) {
                                                            case 0:
                                                            case 3:
                                                                clear();
                                                                break;
                                                            case 1:
                                                                type("01110011");
                                                                clear();
                                                                break;
                                                            case 2:
                                                                type("01000011");
                                                                clear();
                                                                break;
                                                            case -1:
                                                                return query(fitness);
                                                        }
                                                        break;
                                                    case 4:
                                                        switch (get(5, 0, 6)) {
                                                            case 0:
                                                            case 7:
                                                            case 6:
                                                            case 5:
                                                            case 4:
                                                            case 1:
                                                                clear();
                                                                break;
                                                            case 2:
                                                                type("01111011");
                                                                clear();
                                                                break;
                                                            case 3:
                                                                type("01111000");
                                                                clear();
                                                                break;
                                                            case -1:
                                                                return query(fitness);
                                                        }
                                                        break;
                                                    case -1:
                                                        return query(fitness);
                                                }
                                                break;
                                            case 2:
                                                switch (get(4, 3, 4)) {
                                                    case 0:
                                                        switch (get(5, 0, 6)) {
                                                            case 0:
                                                            case 7:
                                                            case 3:
                                                            case 2:
                                                            case 1:
                                                                clear();
                                                                break;
                                                            case 4:
                                                                type("10100101");
                                                                clear();
                                                                break;
                                                            case 5:
                                                                type("10000011");
                                                                clear();
                                                                break;
                                                            case 6:
                                                                type("10000000");
                                                                clear();
                                                                break;
                                                            case -1:
                                                                return query(fitness);
                                                        }
                                                        break;
                                                    case 1:
                                                        switch (get(5, 4, 6)) {
                                                            case 0:
                                                                type("01111111");
                                                                clear();
                                                                break;
                                                            case 1:
                                                                type("01111100");
                                                                clear();
                                                                break;
                                                            case 2:
                                                                type("01000011");
                                                                clear();
                                                                break;
                                                            case 3:
                                                                type("01000000");
                                                                clear();
                                                                break;
                                                            case -1:
                                                                return query(fitness);
                                                        }
                                                        break;
                                                    case 2:
                                                        switch (get(5, 0, 6)) {
                                                            case 0:
                                                            case 7:
                                                            case 6:
                                                            case 5:
                                                            case 4:
                                                                clear();
                                                                break;
                                                            case 1:
                                                                type("10111111");
                                                                clear();
                                                                break;
                                                            case 2:
                                                                type("10111100");
                                                                clear();
                                                                break;
                                                            case 3:
                                                                type("10011010");
                                                                clear();
                                                                break;
                                                            case -1:
                                                                return query(fitness);
                                                        }
                                                        break;
                                                    case -1:
                                                        return query(fitness);
                                                }
                                                break;
                                            case 3:
                                                switch (get(4, 0, 4)) {
                                                    case 0:
                                                    case 5:
                                                    case 4:
                                                        clear();
                                                        break;
                                                    case 1:
                                                        switch (get(5, 0, 6)) {
                                                            case 0:
                                                            case 7:
                                                            case 6:
                                                            case 3:
                                                            case 2:
                                                            case 1:
                                                                clear();
                                                                break;
                                                            case 4:
                                                                type("01000111");
                                                                clear();
                                                                break;
                                                            case 5:
                                                                type("01000100");
                                                                clear();
                                                                break;
                                                            case -1:
                                                                return query(fitness);
                                                        }
                                                        break;
                                                    case 2:
                                                        switch (get(5, 0, 2)) {
                                                            case 0:
                                                            case 3:
                                                                clear();
                                                                break;
                                                            case 1:
                                                                type("01111100");
                                                                clear();
                                                                break;
                                                            case 2:
                                                                type("01001100");
                                                                clear();
                                                                break;
                                                            case -1:
                                                                return query(fitness);
                                                        }
                                                        break;
                                                    case 3:
                                                        switch (get(5, 0, 2)) {
                                                            case 0:
                                                            case 3:
                                                                clear();
                                                                break;
                                                            case 1:
                                                                type("01110000");
                                                                clear();
                                                                break;
                                                            case 2:
                                                                type("01000000");
                                                                clear();
                                                                break;
                                                            case -1:
                                                                return query(fitness);
                                                        }
                                                        break;
                                                    case -1:
                                                        return query(fitness);
                                                }
                                                break;
                                            case 4:
                                                type("01000000");
                                                clear();
                                                break;
                                            case -1:
                                                return query(fitness);
                                        }
                                        break;
                                    case 4:
                                        switch (get(3, 0, 3)) {
                                            case 0:
                                            case 4:
                                            case 3:
                                                clear();
                                                break;
                                            case 1:
                                                switch (get(4, 5, 7)) {
                                                    case 0:
                                                        type("10111111");
                                                        clear();
                                                        break;
                                                    case 1:
                                                        switch (get(5, 0, 6)) {
                                                            case 0:
                                                            case 7:
                                                            case 3:
                                                            case 2:
                                                            case 1:
                                                                clear();
                                                                break;
                                                            case 4:
                                                                type("01001101");
                                                                clear();
                                                                break;
                                                            case 5:
                                                                type("01000011");
                                                                clear();
                                                                break;
                                                            case 6:
                                                                type("01000000");
                                                                clear();
                                                                break;
                                                            case -1:
                                                                return query(fitness);
                                                        }
                                                        break;
                                                    case 2:
                                                        switch (get(5, 0, 6)) {
                                                            case 0:
                                                            case 7:
                                                            case 6:
                                                            case 2:
                                                            case 1:
                                                                clear();
                                                                break;
                                                            case 3:
                                                                type("01001111");
                                                                clear();
                                                                break;
                                                            case 4:
                                                                type("01001100");
                                                                clear();
                                                                break;
                                                            case 5:
                                                                type("01000010");
                                                                clear();
                                                                break;
                                                            case -1:
                                                                return query(fitness);
                                                        }
                                                        break;
                                                    case 3:
                                                        type("10110000");
                                                        clear();
                                                        break;
                                                    case -1:
                                                        return query(fitness);
                                                }
                                                break;
                                            case 2:
                                                switch (get(4, 3, 7)) {
                                                    case 0:
                                                        type("10111111");
                                                        clear();
                                                        break;
                                                    case 1:
                                                        switch (get(5, 0, 6)) {
                                                            case 0:
                                                            case 7:
                                                            case 3:
                                                            case 2:
                                                            case 1:
                                                                clear();
                                                                break;
                                                            case 4:
                                                                type("01110001");
                                                                clear();
                                                                break;
                                                            case 5:
                                                                type("01000011");
                                                                clear();
                                                                break;
                                                            case 6:
                                                                type("01000000");
                                                                clear();
                                                                break;
                                                            case -1:
                                                                return query(fitness);
                                                        }
                                                        break;
                                                    case 2:
                                                        switch (get(5, 0, 6)) {
                                                            case 0:
                                                            case 7:
                                                            case 6:
                                                            case 2:
                                                            case 1:
                                                                clear();
                                                                break;
                                                            case 3:
                                                                type("01110011");
                                                                clear();
                                                                break;
                                                            case 4:
                                                                type("01110000");
                                                                clear();
                                                                break;
                                                            case 5:
                                                                type("01000010");
                                                                clear();
                                                                break;
                                                            case -1:
                                                                return query(fitness);
                                                        }
                                                        break;
                                                    case 3:
                                                        type("10001100");
                                                        clear();
                                                        break;
                                                    case 4:
                                                    case 5:
                                                        clear();
                                                        break;
                                                    case -1:
                                                        return query(fitness);
                                                }
                                                break;
                                            case -1:
                                                return query(fitness);
                                        }
                                        break;
                                    case 5:
                                        switch (get(3, 0, 0)) {
                                            case 0:
                                                switch (get(4, 0, 6)) {
                                                    case 0:
                                                    case 7:
                                                    case 6:
                                                    case 5:
                                                    case 4:
                                                    case 1:
                                                        clear();
                                                        break;
                                                    case 2:
                                                        switch (get(5, 0, 7)) {
                                                            case 0:
                                                            case 8:
                                                            case 7:
                                                            case 4:
                                                            case 3:
                                                            case 2:
                                                            case 1:
                                                                clear();
                                                                break;
                                                            case 5:
                                                                type("11000001");
                                                                clear();
                                                                break;
                                                            case 6:
                                                                type("11000000");
                                                                clear();
                                                                break;
                                                            case -1:
                                                                return query(fitness);
                                                        }
                                                        break;
                                                    case 3:
                                                        switch (get(5, 0, 7)) {
                                                            case 0:
                                                            case 8:
                                                            case 7:
                                                            case 6:
                                                            case 3:
                                                            case 2:
                                                            case 1:
                                                                clear();
                                                                break;
                                                            case 4:
                                                                type("11000011");
                                                                clear();
                                                                break;
                                                            case 5:
                                                                type("11000010");
                                                                clear();
                                                                break;
                                                            case -1:
                                                                return query(fitness);
                                                        }
                                                        break;
                                                    case -1:
                                                        return query(fitness);
                                                }
                                                break;
                                            case 1:
                                                clear();
                                                break;
                                            case -1:
                                                return query(fitness);
                                        }
                                        break;
                                    case -1:
                                        return query(fitness);
                                }
                                break;
                            case 2:
                                clear();
                                break;
                            case -1:
                                return query(fitness);
                        }
                        break;
                    case -1:
                        return query(fitness);
                }


                return null;
            }

            @Override
            public boolean isDone() {
                return isTyping;
            }
        }

        public static class Q8 implements Machine {

            boolean isHasBeenNewAsking;
            public final OneMaxBox oneMaxBox;
            public boolean isDone;

            public Q8(OneMaxBox oneMaxBox) {
                this.oneMaxBox = oneMaxBox;
                this.isHasBeenNewAsking = false;
            }

            @Override
            public ArrayList<Integer> query(int fitness) {
                if (oneMaxBox.currentPosition + 8 > oneMaxBox.size) {
                    if (oneMaxBox.currentPosition == oneMaxBox.size) {
                        oneMaxBox.currentMachine = new EndGameMachine(oneMaxBox);
                    } else {
                        oneMaxBox.currentMachine = new StupidMachine(oneMaxBox);
                    }
                } else {
                    oneMaxBox.currentMachine = new MontrousMachine(oneMaxBox);
                }
                isDone = true;
                return null;
            }

            @Override
            public boolean isDone() {
                return isDone;
            }
        }

        int size;
        int lastFitness;
        int currentPosition;
        Machine currentMachine;

        public OneMaxBox(int size) {
            this.size = size;
            currentPosition = 0;
            takeMachine();
        }

        public void takeMachine() {
            if (currentPosition + 8 > size) {
                if (currentPosition == size) {
                    currentMachine = new EndGameMachine(this);
                } else {
                    currentMachine = new StupidMachine(this);
                }
            } else {
                currentMachine = new MontrousMachine(this);
            }
        }

        public ArrayList<Integer> query(int fitness) {
            ArrayList<Integer> answer = null;
            while (answer == null) {
                answer = currentMachine.query(fitness);
                if (currentMachine.isDone()) {
                    currentPosition += 8;
                    takeMachine();
                }
            }
            lastFitness = fitness;
            return answer;
        }
    }

    public static void main(String[] args) {
        FastReader fastReader;
        fastReader = new FastReader();
        PrintWriter printWriter = new PrintWriter(System.out);
        int n = fastReader.nextInt();
        Queerer queerer = new Queerer(fastReader, printWriter, new OneMaxBox(n), n);
        queerer.run();
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
