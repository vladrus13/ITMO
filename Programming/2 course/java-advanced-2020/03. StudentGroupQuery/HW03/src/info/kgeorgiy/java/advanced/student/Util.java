package info.kgeorgiy.java.advanced.student;

import org.junit.Assert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public final class Util {
    /** Utility class. */
    private Util() {}

    public static List<Student> getStudents(final List<Student> students, final int[] answer) {
        return Arrays.stream(answer).mapToObj(students::get).collect(Collectors.toList());
    }

    public static <T, A> void test(final List<List<Student>> inputs, final Function<List<Student>, T> query, final BiFunction<List<Student>, A, T> answer, final A[] answers) {
        for (int i = 0; i < inputs.size(); i++) {
            final List<Student> students = inputs.get(i);
            final T actual = query.apply(students);
            Assert.assertEquals(answer.apply(students, answers[i]), actual);
        }
        System.err.println();
    }

    public static List<Group> groups(final List<Student> students, final int[] answer) {
        String group = null;
        List<Student> groupStudents = new ArrayList<>();
        final List<Group> groups = new ArrayList<>();

        for (final Student student : getStudents(students, answer)) {
            if (group != null && !group.equals(student.getGroup())) {
                groups.add(new Group(group, groupStudents));
                groupStudents = new ArrayList<>();
            }
            group = student.getGroup();
            groupStudents.add(student);
        }
        if (!groupStudents.isEmpty()) {
            groups.add(new Group(group, groupStudents));
        }
        return groups;
    }
}
