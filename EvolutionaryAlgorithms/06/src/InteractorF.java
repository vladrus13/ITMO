import ru.vladrus13.system.interactor.Interactor;
import ru.vladrus13.system.result.LimitsPack;
import ru.vladrus13.system.result.Result;
import ru.vladrus13.system.result.ResultType;

import java.util.ArrayList;
import java.util.Random;

public class InteractorF extends Interactor {

    public final String finish;
    public final int id;
    public final String start;
    public ArrayList<Character> current;
    public Launcher.OneMaxBox box;

    public InteractorF setBox(Launcher.OneMaxBox box) {
        this.box = box;
        return this;
    }

    public InteractorF(int id, String start, String finish) {
        this.finish = finish;
        this.id = id;
        this.start = start;
    }

    public String generateString(Random random, int size) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size; i++) {
            sb.append(random.nextBoolean() ? "1" : "0");
        }
        return sb.toString();
    }

    public void reverse(ArrayList<Integer> revevrso) {
        for (int it : revevrso) {
            current.set(it, current.get(it) == '1' ? '0' : '1');
        }
    }

    public int fitness() {
        int answer = 0;
        for (int i = 0; i < finish.length(); i++) {
            if (current.get(i) == finish.charAt(i)) answer++;
        }
        return answer;
    }

    public InteractorF(int id, Random random, int size) {
        this.id = id;
        finish = generateString(random, size);
        start = generateString(random, size);
    }

    @Override
    public String toString() {
        return "InteractorF: id: " + id + ", length: " + start.length();
    }

    @Override
    public Result test(Process process, LimitsPack limitsPack) {
        int count = 0;
        current = new ArrayList<>();
        for (int i = 0; i < start.length(); i++) {
            current.add(start.charAt(i));
        }
        while (true) {
            int fitness = fitness();
            if (fitness == start.length()) {
                return new Result(ResultType.OK, "OK: count: " + count);
            }
            if (count > start.length() * 3 / 4 + 99) {
                return new Result(ResultType.TIME_LIMIT, "Count of requests exceeded");
            }
            count++;
            ArrayList<Integer> pair = box.query(fitness);
            reverse(pair);
        }
    }
}
