package prolog.prtest.tree;

import alice.tuprolog.Int;
import alice.tuprolog.Term;
import prolog.prtest.map.Settings;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public class PrologTreeReplaceTest extends PrologTreeTest {
    public PrologTreeReplaceTest(final boolean hard) {
        super(hard);
    }

    class ReplaceTreeTest extends TreeTest {
        public ReplaceTreeTest(final Settings settings) {
            super(settings);
            actions(
                    () -> existingKey(this::replace),
                    () -> uniqueKey(this::replace),
                    () -> removedKey(this::replace)
            );
        }

        private void replace(final Int key) {
            final Term value = randomValue();
            expected.replace(key, value);
            update("map_replace", key, value);
        }
    }

    @Override
    protected MapTest<?> test(final Settings settings) {
        return new ReplaceTreeTest(settings);
    }

    public static void main(final String... args) {
        new PrologTreeReplaceTest(isBonus(args, PrologTreeReplaceTest.class)).test();
    }
}
