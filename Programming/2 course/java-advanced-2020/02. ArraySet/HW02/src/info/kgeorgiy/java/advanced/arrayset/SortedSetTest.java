package info.kgeorgiy.java.advanced.arrayset;

import info.kgeorgiy.java.advanced.base.BaseTest;
import net.java.quickcheck.Generator;
import net.java.quickcheck.collection.Pair;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.util.*;

import static net.java.quickcheck.generator.CombinedGenerators.excludeValues;
import static net.java.quickcheck.generator.CombinedGenerators.lists;
import static net.java.quickcheck.generator.CombinedGeneratorsIterables.*;
import static net.java.quickcheck.generator.PrimitiveGenerators.fixedValues;
import static net.java.quickcheck.generator.PrimitiveGenerators.integers;

/**
 * Tests for easy version
 * of <a href="https://www.kgeorgiy.info/courses/java-advanced/homeworks.html#homework-arrayset">ArraySet</a> homework
 * for <a href="https://www.kgeorgiy.info/courses/java-advanced/">Java Advanced</a> course.
 *
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SortedSetTest extends BaseTest {
    @Test
    public void test01_constructors() {
        final Class<?> token = loadClass();
        Assert.assertTrue(token.getName() + " should implement SortedSet interface", SortedSet.class.isAssignableFrom(token));

        checkConstructor("default constructor", token);
        checkConstructor("constructor out of Collection", token, Collection.class);
        checkConstructor("constructor out of Collection and Comparator", token, Collection.class, Comparator.class);
    }

    @Test
    public void test02_empty() {
        final SortedSet<Integer> set = create(new Object[]{});
        Assert.assertEquals("Empty set size should be zero", 0, set.size());
        Assert.assertTrue("Empty set should be empty", set.isEmpty());
        Assert.assertEquals("toArray for empty set should return empty array", 0, (Object) set.toArray().length);
    }

    @Test
    public void test04_externalOrder() {
        for (final Pair<NamedComparator, List<Integer>> pair : withComparator()) {
            final List<Integer> elements = pair.getSecond();
            final Comparator<Integer> comparator = pair.getFirst();

            assertEq(
                    set(elements, comparator),
                    treeSet(elements, comparator),
                    "(comparator = " + comparator + ", elements = " + elements + ")"
            );
        }
    }

    protected Iterable<Pair<NamedComparator, List<Integer>>> withComparator() {
        return somePairs(comparators, lists(integers()));
    }

    @Test
    public void test07_contains() {
        for (final Pair<NamedComparator, List<Integer>> pair : withComparator()) {
            final List<Integer> elements = pair.getSecond();
            final Comparator<Integer> comparator = pair.getFirst();

            final SortedSet<Integer> set = set(elements, comparator);
            final String context = "(comparator = " + comparator + ", elements = " + elements + ")";
            for (final Integer element : elements) {
                Assert.assertTrue("set should contains() element " + element + " " + context, set.contains(element));
            }

            final SortedSet<Integer> treeSet = set(elements, comparator);
            for (final Integer element : someOneOf(excludeValues(integers(), elements))) {
                Assert.assertEquals("contains(" + element + ") " + context, treeSet.contains(element), set.contains(element));
            }
        }
    }

    @Test
    public void test09_containsAll() {
        for (final Pair<NamedComparator, List<Integer>> pair : withComparator()) {
            final List<Integer> elements = pair.getSecond();
            final Comparator<Integer> comparator = pair.getFirst();

            final SortedSet<Integer> set = set(elements, comparator);
            final String context = "(comparator = " + comparator + ", elements = " + elements + ")";
            Assert.assertTrue("set should contains() all elements " + " " + context, set.containsAll(elements));

            final SortedSet<Integer> treeSet = set(elements, comparator);
            for (final Integer element : someOneOf(excludeValues(integers(), elements))) {
                final List<Integer> l = new ArrayList<>(elements);
                elements.add(element);
                Assert.assertEquals("containsAll(" + l + ") " + context, treeSet.containsAll(l), set.containsAll(l));
            }
        }
    }

    private List<Integer> toList(final SortedSet<Integer> set) {
        return new ArrayList<>(set);
    }

    protected List<Number> toArray(final SortedSet<Integer> set) {
        return Arrays.asList(set.toArray(new Number[0]));
    }

    private TreeSet<Integer> treeSet(final List<Integer> elements) {
        return new TreeSet<>(elements);
    }

    private SortedSet<Integer> set(final List<Integer> elements) {
        return create(new Object[]{elements}, Collection.class);
    }

    protected SortedSet<Integer> set(final List<Integer> elements, final Comparator<Integer> comparator) {
        return create(new Object[]{elements, comparator}, Collection.class, Comparator.class);
    }

    protected void assertEq(final SortedSet<Integer> set, final SortedSet<Integer> treeSet, final String context) {
        Assert.assertEquals("invalid element order " + context, toList(treeSet), toList(set));
        Assert.assertEquals("invalid toArray " + context, toArray(set), toArray(set));
        Assert.assertEquals("invalid set size " + context, treeSet.size(), (Object) set.size());
        Assert.assertEquals("invalid isEmpty " + context, treeSet.isEmpty(), set.isEmpty());
        Assert.assertSame("invalid comparator " + context, treeSet.comparator(), set.comparator());
    }

    protected SortedSet<Integer> treeSet(final List<Integer> elements, final Comparator<Integer> comparator) {
        final SortedSet<Integer> set = new TreeSet<>(comparator);
        set.addAll(elements);
        return set;
    }

    protected final class NamedComparator implements Comparator<Integer> {
        private final String name;
        private final Comparator<Integer> comparator;

        private NamedComparator(final String name, final Comparator<Integer> comparator) {
            this.name = name;
            this.comparator = comparator;
        }

        @Override
        public int compare(final Integer o1, final Integer o2) {
            return comparator.compare(o1, o2);
        }

        @Override
        public String toString() {
            return name;
        }
    }

    private final Generator<NamedComparator> comparators = fixedValues(Arrays.asList(
            new NamedComparator("Natural order", Integer::compare),
            new NamedComparator("Reverse order", Comparator.comparingInt(Integer::intValue).reversed()),
            new NamedComparator("Div 100", Comparator.comparingInt(i -> i / 100)),
            new NamedComparator("Even first", Comparator.<Integer>comparingInt(i -> i % 2).thenComparing(Integer::intValue)),
            new NamedComparator("All equal", Comparator.comparingInt(i -> 0))
    ));

    private SortedSet<Integer> create(final Object[] params, final Class<?>... types) {
        try {
            @SuppressWarnings("unchecked") final
            SortedSet<Integer> set = (SortedSet<Integer>) loadClass().getConstructor(types).newInstance(params);
            return set;
        } catch (final Exception e) {
            e.printStackTrace();
            Assert.fail("Instantiation error");
            throw new AssertionError();
        }
    }

    @Test
    public void test11_comparator() {
        for (final Pair<NamedComparator, List<Integer>> pair : withComparator()) {
            final List<Integer> elements = pair.getSecond();
            final Comparator<Integer> comparator = pair.getFirst();

            Assert.assertSame("comparator() should return provided comparator", comparator, set(elements, comparator).comparator());
        }
        for (final List<Integer> elements : someLists(integers())) {
            Assert.assertNull("comparator() should return null for default order", set(elements).comparator());
        }
    }

    @Test
    public void test12_headSet() {
        for (final Pair<NamedComparator, List<Integer>> pair : withComparator()) {
            final List<Integer> elements = pair.getSecond();
            final Comparator<Integer> comparator = pair.getFirst();
            final SortedSet<Integer> set = set(elements, comparator);
            final SortedSet<Integer> treeSet = treeSet(elements, comparator);

            for (final Integer element : inAndOut(elements)) {
                assertEq(
                        set.headSet(element),
                        treeSet.headSet(element),
                        "in headSet(" + element + ") (comparator = " + comparator + ", elements = " + elements + ")"
                );
            }
        }
    }

    protected Collection<Integer> inAndOut(final List<Integer> elements) {
        return concat(elements, someOneOf(excludeValues(integers(), elements)));
    }

    private Collection<Integer> concat(final Iterable<Integer> items1, final Iterable<Integer> items2) {
        final List<Integer> list = new ArrayList<>();
        items1.forEach(list::add);
        items2.forEach(list::add);
        return list;
    }

    @Test
    public void test14_subSet() {
        for (final Pair<NamedComparator, List<Integer>> pair : withComparator()) {
            final List<Integer> elements = pair.getSecond();
            final Comparator<Integer> comparator = pair.getFirst();
            final SortedSet<Integer> set = set(elements, comparator);
            final SortedSet<Integer> treeSet = treeSet(elements, comparator);

            final Collection<Integer> all = values(elements);
            for (final Pair<Integer, Integer> p : somePairs(fixedValues(all), fixedValues(all))) {
                final Integer from = p.getFirst();
                final Integer to = p.getSecond();
                if (comparator.compare(from, to) <= 0) {
                    assertEq(
                            set.subSet(from, to),
                            treeSet.subSet(from, to),
                            "in subSet(" + from + ", " + to + ") (comparator = " + comparator + ", elements = " + elements + ")"
                    );
                }
            }
        }
    }

    protected Collection<Integer> values(final List<Integer> elements) {
        return concat(inAndOut(elements), Arrays.asList(0, Integer.MAX_VALUE, Integer.MIN_VALUE));
    }

    @Test
    public void test16_first() {
        for (final Pair<NamedComparator, List<Integer>> pair : withComparator()) {
            final List<Integer> elements = pair.getSecond();
            final Comparator<Integer> comparator = pair.getFirst();

            final SortedSet<Integer> set = set(elements, comparator);
            if (set.isEmpty()) {
                try {
                    set.first();
                    Assert.fail("first() should throw NoSuchElementException for empty set");
                } catch (final NoSuchElementException e) {
                    // Expected behavior
                }
            } else {
                Assert.assertEquals("first() " + "(comparator = " + comparator + ", elements = " + elements + ")", set(elements, comparator).first(), set.first());
            }
        }
    }
}
