package info.kgeorgiy.java.advanced.student;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.util.*;
import java.util.function.BiFunction;
import java.util.stream.IntStream;

/**
 * Tests for hard version
 * of <a href="https://www.kgeorgiy.info/courses/java-advanced/homeworks.html#homework-student">Student</a> homework
 * for <a href="https://www.kgeorgiy.info/courses/java-advanced/">Java Advanced</a> course.
 *
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AdvancedStudentGroupQueryTest extends StudentGroupQueryTest implements AdvancedStudentGroupQuery {
    private static final int[] INDICES = RANDOM.ints(STUDENTS.size(), 0, STUDENTS.size()).toArray();

    private final AdvancedStudentGroupQuery db = createCUT();

    @Test
    public void test31_testGetMostPopularName() {
        test(this::getMostPopularName, db::getMostPopularName);
    }

    @Test
    public void test32_testGetFirstNames() {
        testIndexed(this::getFirstNames, db::getFirstNames);
    }

    @Test
    public void test33_testGetLastNames() {
        testIndexed(this::getLastNames, db::getLastNames);
    }

    @Test
    public void test34_testGetGroups() {
        testIndexed(this::getGroups, db::getGroups);
    }

    @Test
    public void test35_testGetFullNames() {
        testIndexed(this::getFullNames, db::getFullNames);
    }

    private static void testIndexed(
            final BiFunction<Collection<Student>, int[], List<String>> reference,
            final BiFunction<Collection<Student>, int[], List<String>> tested
    ) {
        for (int i = 0; i < STUDENTS.size(); i++) {
            final Collection<Student> students = STUDENTS.subList(0, i);
            final int max = i;
            final int[] indices = IntStream.of(INDICES).filter(v -> v < max).toArray();
            Assert.assertEquals(reference.apply(students, indices), tested.apply(students, indices));
        }
    }

    // Reference implementation follows
    // This implementation is intentionally poorly-written and contains a lot of copy-and-paste

    @Override
    public String getMostPopularName(final Collection<Student> students) {
        final NavigableMap<String, Integer> counts = new TreeMap<>();
        for (final Group group : getGroupsByName(students)) {
            for (final String name : Set.copyOf(getFullNames(group.getStudents()))) {
                counts.merge(name, 1, Integer::sum);
            }
        }

        if (counts.isEmpty()) {
            return "";
        }

        final int max = Collections.max(counts.values());
        for (final Map.Entry<String, Integer> entry : counts.descendingMap().entrySet()) {
            if (entry.getValue() == max) {
                return entry.getKey();
            }
        }
        return "";
    }

    @Override
    public List<String> getFirstNames(final Collection<Student> students, final int[] indices) {
        final List<String> firstNames = getFirstNames(List.copyOf(students));
        final List<String> result = new ArrayList<>();
        for (final int index : indices) {
            result.add(firstNames.get(index));
        }
        return result;
    }

    @Override
    public List<String> getLastNames(final Collection<Student> students, final int[] indices) {
        final List<String> lastNames = getLastNames(List.copyOf(students));
        final List<String> result = new ArrayList<>();
        for (final int index : indices) {
            result.add(lastNames.get(index));
        }
        return result;
    }

    @Override
    public List<String> getGroups(final Collection<Student> students, final int[] indices) {
        final List<String> groups = getGroups(List.copyOf(students));
        final List<String> result = new ArrayList<>();
        for (final int index : indices) {
            result.add(groups.get(index));
        }
        return result;
    }

    @Override
    public List<String> getFullNames(final Collection<Student> students, final int[] indices) {
        final List<String> fullNames = getFullNames(List.copyOf(students));
        final List<String> result = new ArrayList<>();
        for (final int index : indices) {
            result.add(fullNames.get(index));
        }
        return result;
    }
}
