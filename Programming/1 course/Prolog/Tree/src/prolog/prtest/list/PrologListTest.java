package prolog.prtest.list;

import alice.tuprolog.Int;
import alice.tuprolog.Struct;
import alice.tuprolog.Term;
import base.Asserts;
import prolog.prtest.map.Entry;
import prolog.prtest.map.PrologMapTest;
import prolog.prtest.map.Settings;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public class PrologListTest extends PrologMapTest {
    private final boolean sorted;

    public PrologListTest(final boolean sorted) {
        super(true, sorted ? "sorted-list-map.pl" : "list-map.pl");
        this.sorted = sorted;
    }

    @Override
    public String toString() {
        return super.getClass().getSimpleName() + "(" + sorted + ")";
    }

    class ListTest extends MapTest<List<Entry>> {
        public ListTest(final Settings settings) {
            super(settings, new ArrayList<>());
        }

        @Override
        protected Term build(final List<Entry> entries, final Struct pairs) {
            expected.addAll(entries);
            return pairs;
        }

        @Override
        protected void check(final List<Int> testKeys) {
            Asserts.assertTrue("Type", actual.isList());
            final List<Term> actualList = toList((Struct) actual);
            if (sorted) {
                expected.sort(Comparator.comparing(Entry::getKey));
            }
            Asserts.assertEquals("Length", expected.size(), actualList.size());
            for (int i = 0; i < expected.size(); i++) {
                final Entry entry = expected.get(i);
                Asserts.assertEquals("Element " + i, entry.getStruct(), actualList.get(i));
                get(entry.getValue(), entry.getKey());
            }

            for (final Int key : testKeys) {
                if (!existingKeys.contains(key)) {
                    get(null, key);
                }
            }
        }

        private List<Term> toList(Struct list) {
            final List<Term> result = new ArrayList<>();
            while (!list.isEmptyList()) {
                result.add(list.getTerm(0));
                list = (Struct) list.getTerm(1);
            }
            return result;
        }

        @Override
        protected void putImpl(final Int key, final Term value) {
            expected.stream()
                    .filter(predicate(key))
                    .findFirst()
                    .ifPresentOrElse(
                            entry -> entry.setValue(value),
                            () -> expected.add(0, new Entry(key, value))
                    );
        }

        @Override
        protected void removeImpl(final Int key) {
            expected.removeIf(predicate(key));
        }

        protected Predicate<Entry> predicate(final Int key) {
            return entry -> entry.getKey().equals(key);
        }
    }

    @Override
    protected MapTest<?> test(final Settings settings) {
        return new ListTest(settings);
    }

    public static void main(final String... args) {
        new PrologListTest(isSorted(args, PrologListTest.class)).test();
    }

    protected static boolean isSorted(final String[] args, final Class<? extends PrologListTest> type) {
        return mode(args, type, "list", "sorted") == 1;
    }
}
