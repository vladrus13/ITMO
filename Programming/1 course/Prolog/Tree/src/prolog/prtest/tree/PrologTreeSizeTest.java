package prolog.prtest.tree;

import alice.tuprolog.Int;
import prolog.prtest.map.Settings;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public class PrologTreeSizeTest extends PrologTreeTest {
    public PrologTreeSizeTest(final boolean hard) {
        super(hard);
    }

    class SizeTreeTest extends TreeTest {
        public SizeTreeTest(final Settings settings) {
            super(settings);
        }

        @Override
        protected void check() {
            assertCall(new Int(expected.size()), "map_size", actual);
            super.check();
        }
    }

    @Override
    protected MapTest<?> test(final Settings settings) {
        return new SizeTreeTest(settings);
    }

    public static void main(final String... args) {
        new PrologTreeSizeTest(isBonus(args, PrologTreeSizeTest.class)).test();
    }
}
