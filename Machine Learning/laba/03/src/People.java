import java.util.ArrayList;
import java.util.Objects;

public class People {
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
