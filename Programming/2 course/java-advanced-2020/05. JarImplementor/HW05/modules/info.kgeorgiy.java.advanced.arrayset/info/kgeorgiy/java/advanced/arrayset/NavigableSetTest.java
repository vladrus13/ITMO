package info.kgeorgiy.java.advanced.arrayset;

import net.java.quickcheck.collection.Pair;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.util.*;
import java.util.function.Function;

import static net.java.quickcheck.generator.CombinedGeneratorsIterables.somePairs;
import static net.java.quickcheck.generator.PrimitiveGenerators.fixedValues;
import static org.junit.Assert.assertEquals;

/**
 * Tests for hard version
 * of <a href="https://www.kgeorgiy.info/courses/java-advanced/homeworks.html#homework-arrayset">ArraySet</a> homework
 * for <a href="https://www.kgeorgiy.info/courses/java-advanced/">Java Advanced</a> course.
 *
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class NavigableSetTest extends SortedSetTest {
    @Test
    public void test31_lower() {
        for (final Pair<NamedComparator, List<Integer>> pair : withComparator()) {
            final List<Integer> elements = pair.getSecond();
            final Comparator<Integer> comparator = pair.getFirst();
            final NavigableSet<Integer> set = set(elements, comparator);
            final NavigableSet<Integer> treeSet = treeSet(elements, comparator);

            for (final Integer element : inAndOut(elements)) {
                assertEquals(
                        "in lower(" + element + ") (comparator = " + comparator + ", elements = " + elements + ")",
                        treeSet.lower(element),
                        set.lower(element)
                );
            }
        }
    }

    @Test
    public void test32_ceiling() {
        for (final Pair<NamedComparator, List<Integer>> pair : withComparator()) {
            final List<Integer> elements = pair.getSecond();
            final Comparator<Integer> comparator = pair.getFirst();
            final NavigableSet<Integer> set = set(elements, comparator);
            final NavigableSet<Integer> treeSet = treeSet(elements, comparator);

            for (final Integer element : inAndOut(elements)) {
                assertEquals(
                        "in " + "ceiling" + "(" + element + ") (comparator = " + comparator + ", elements = " + elements + ")",
                        treeSet.ceiling(element),
                        set.ceiling(element)
                );
            }
        }
    }

    @Test
    public void test33_higher() {
        for (final Pair<NamedComparator, List<Integer>> pair : withComparator()) {
            final List<Integer> elements = pair.getSecond();
            final Comparator<Integer> comparator = pair.getFirst();
            final NavigableSet<Integer> set = set(elements, comparator);
            final NavigableSet<Integer> treeSet = treeSet(elements, comparator);

            for (final Integer element : inAndOut(elements)) {
                assertEquals(
                        "in " + "higher(" + element + ") (comparator = " + comparator + ", elements = " + elements + ")",
                        treeSet.higher(element),
                        set.higher(element)
                );
            }
        }
    }

    @Test
    public void test34_floor() {
        for (final Pair<NamedComparator, List<Integer>> pair : withComparator()) {
            final List<Integer> elements = pair.getSecond();
            final Comparator<Integer> comparator = pair.getFirst();
            final NavigableSet<Integer> set = set(elements, comparator);
            final NavigableSet<Integer> treeSet = treeSet(elements, comparator);

            for (final Integer element : inAndOut(elements)) {
                assertEquals(
                        "in floor(" + element + ") (comparator = " + comparator + ", elements = " + elements + ")",
                        treeSet.floor(element),
                        set.floor(element)
                );
            }
        }
    }

    @Test
    public void test35_navigableTailSet() {
        for (final Pair<NamedComparator, List<Integer>> pair : withComparator()) {
            final List<Integer> elements = pair.getSecond();
            final Comparator<Integer> comparator = pair.getFirst();
            final NavigableSet<Integer> set = set(elements, comparator);
            final NavigableSet<Integer> treeSet = treeSet(elements, comparator);

            for (final Integer element : inAndOut(elements)) {
                assertEq(
                        set.tailSet(element, true),
                        treeSet.tailSet(element, true),
                        "in tailSet(" + element + ", true) (comparator = " + comparator + ", elements = " + elements + ")"
                );
                assertEq(
                        set.tailSet(element, false),
                        treeSet.tailSet(element, false),
                        "in tailSet(" + element + ", false) (comparator = " + comparator + ", elements = " + elements + ")"
                );
            }
        }
    }

    @Test
    public void test36_navigableHeadSet() {
        for (final Pair<NamedComparator, List<Integer>> pair : withComparator()) {
            final List<Integer> elements = pair.getSecond();
            final Comparator<Integer> comparator = pair.getFirst();
            final NavigableSet<Integer> set = set(elements, comparator);
            final NavigableSet<Integer> treeSet = treeSet(elements, comparator);

            for (final Integer element : inAndOut(elements)) {
                assertEq(
                        set.headSet(element, true),
                        treeSet.headSet(element, true),
                        "in headSet(" + element + ", true) (comparator = " + comparator + ", elements = " + elements + ")"
                );
                assertEq(
                        set.headSet(element, false),
                        treeSet.headSet(element, false),
                        "in headSet(" + element + ", false) (comparator = " + comparator + ", elements = " + elements + ")"
                );
            }
        }
    }

    @Test
    public void test37_navigableSubSet() {
        for (final Pair<NamedComparator, List<Integer>> pair : withComparator()) {
            final List<Integer> elements = pair.getSecond();
            final Comparator<Integer> comparator = pair.getFirst() != null ? pair.getFirst() : Comparator.naturalOrder();
            final NavigableSet<Integer> set = set(elements, pair.getFirst());
            final NavigableSet<Integer> treeSet = treeSet(elements, pair.getFirst());

            final Collection<Integer> all = values(elements);
            for (final Pair<Integer, Integer> p : somePairs(fixedValues(all), fixedValues(all))) {
                final Integer from = p.getFirst();
                final Integer to = p.getSecond();
                if (comparator.compare(from, to) <= 0) {
                    for (int i = 0; i < 4; i++) {
                        assertEq(
                                set.subSet(from, i % 2 == 1, to, i / 2 == 1),
                                treeSet.subSet(from, i % 2 == 1, to, i / 2 == 1),
                                String.format("in subSet(%d, %b, %d, %b) (comparator = %s, elements = %s",
                                        from, i % 2 == 1,
                                        to, i / 2 == 1,
                                        comparator, elements
                                )
                        );
                    }
                }
            }
        }
    }

    @Test
    public void test38_mutators() {
        final NavigableSet<Integer> set = set(List.of(1, 2, 3), Integer::compareTo);
        testMutator(set::pollFirst);
        testMutator(set::pollLast);
    }

    @Test
    public void test39_descendingSet() {
        testDescendingSet(set(List.of(10, 20, 30), Integer::compareTo).descendingSet());
        testDescendingSet(set(List.of(10, 20, 30), null).descendingSet());
    }

    private static void testDescendingSet(final NavigableSet<Integer> set) {
        assertEquals("toArray()", List.of(30, 20, 10), toArray(set));
        assertEquals("size()", 3, set.size());
        assertEquals("first()", 30, set.first().intValue());
        assertEquals("last()", 10, set.last().intValue());
        assertEquals("descendingIterator().next()", 10, set.descendingIterator().next().intValue());

        testGet("floor(%s)", set::floor, descendingPairs(10, 10, 20, 20, 30, 30, null));
        testGet("lower(%s)", set::lower, descendingPairs(10, 20, 20, 30, 30, null, null));
        testGet("ceiling(%s)", set::ceiling, descendingPairs(null, 10, 10, 20, 20, 30, 30));
        testGet("higher(%s)", set::higher, descendingPairs(null, null, 10, 10, 20, 20, 30));

        testGet("headSet(%s).size()", i -> set.headSet(i).size(), descendingPairs(3, 2, 2, 1, 1, 0, 0));
        testGet("tailSet(%s).size()", i -> set.tailSet(i).size(), descendingPairs(0, 1, 1, 2, 2, 3, 3));
        assertEquals("descendingSet().toArray()", List.of(10, 20, 30), toArray(set.descendingSet()));
    }

    private static List<Pair<Integer, Integer>> descendingPairs(final Integer v5, final Integer v10, final Integer v15, final Integer v20, final Integer v25, final Integer v30, final Integer v35) {
        return List.of(
                pair(5, v5),
                pair(10, v10),
                pair(15, v15),
                pair(20, v20),
                pair(25, v25),
                pair(30, v30),
                pair(35, v35)
        );
    }

    private static <T> void testGet(final String format, final Function<T, T> method, final List<Pair<T, T>> pairs) {
        for (final Pair<T, T> pair : pairs) {
            assertEquals(String.format(format, pair.getFirst()), pair.getSecond(), method.apply(pair.getFirst()));
        }
    }

    private static <T> Pair<T, T> pair(final T arg, final T result) {
        return new Pair<>(arg, result);
    }

    protected static void testMutator(final Runnable mutator) {
        try {
            mutator.run();
            Assert.fail("Expected UnsupportedOperationException");
        } catch (final UnsupportedOperationException ignore) {
            // Passed
        }
    }

    protected NavigableSet<Integer> set(final List<Integer> elements, final Comparator<Integer> comparator) {
        return (NavigableSet<Integer>) super.set(elements, comparator);
    }

    protected NavigableSet<Integer> treeSet(final List<Integer> elements, final Comparator<Integer> comparator) {
        return (NavigableSet<Integer>) super.treeSet(elements, comparator);
    }
}
