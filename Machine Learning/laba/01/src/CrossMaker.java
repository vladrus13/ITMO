import java.util.*;
import java.util.stream.IntStream;

public class CrossMaker {
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
