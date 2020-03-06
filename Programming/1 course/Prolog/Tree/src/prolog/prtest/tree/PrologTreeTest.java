package prolog.prtest.tree;

import alice.tuprolog.Int;
import alice.tuprolog.Struct;
import alice.tuprolog.Term;
import prolog.prtest.map.Entry;
import prolog.prtest.map.PrologMapTest;
import prolog.prtest.map.Settings;

import java.util.List;
import java.util.NavigableMap;
import java.util.TreeMap;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public class PrologTreeTest extends PrologMapTest {

    public PrologTreeTest(final boolean updates, final String file) {
        super(updates, file);
    }

    public PrologTreeTest(final boolean updates) {
        this(updates, "tree-map.pl");
    }

    @Override
    protected MapTest<?> test(final Settings settings) {
        return new TreeTest(settings);
    }

    public static void main(final String... args) {
        new PrologTreeTest(isBonus(args, PrologTreeTest.class)).test();
    }

    protected static boolean isBonus(final String[] args, final Class<? extends PrologTreeTest> type) {
        return mode(args, type, "hard", "bonus") == 1;
    }

    protected class TreeTest extends MapTest<NavigableMap<Int, Term>> {
        public TreeTest(final Settings settings) {
            super(settings, new TreeMap<>());
        }

        @Override
        protected Term build(final List<Entry> entries, final Struct pairs) {
            entries.forEach(entry -> expected.put(entry.getKey(), entry.getValue()));
            return call("tree_build", pairs);
        }

        @Override
        protected void check(final List<Int> testKeys) {
            for (final Int key : testKeys) {
                get(expected.get(key), key);
            }
        }

        @Override
        protected void putImpl(final Int key, final Term value) {
            expected.put(key, value);
        }

        @Override
        protected void removeImpl(final Int key) {
            expected.remove(key);
        }
    }

}
