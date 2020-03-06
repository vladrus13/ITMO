package prolog.prtest.list;

import alice.tuprolog.Int;
import prolog.prtest.map.Settings;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public class PrologListSizeTest extends PrologListTest {
    public PrologListSizeTest(final boolean hard) {
        super(hard);
    }

    class SizeListTest extends ListTest {
        public SizeListTest(final Settings settings) {
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
        return new SizeListTest(settings);
    }

    public static void main(final String... args) {
        new PrologListSizeTest(isSorted(args, PrologListSizeTest.class)).test();
    }
}
