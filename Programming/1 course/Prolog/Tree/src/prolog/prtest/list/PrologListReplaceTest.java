package prolog.prtest.list;

import alice.tuprolog.Int;
import alice.tuprolog.Term;
import prolog.prtest.map.Settings;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public class PrologListReplaceTest extends PrologListTest {
    public PrologListReplaceTest(final boolean hard) {
        super(hard);
    }

    class ReplaceListTest extends ListTest {
        public ReplaceListTest(final Settings settings) {
            super(settings);
            actions(
                    () -> existingKey(this::replace),
                    () -> uniqueKey(this::replace),
                    () -> removedKey(this::replace)
            );
        }

        private void replace(final Int key) {
            final Term value = randomValue();
            expected.stream()
                    .filter(predicate(key))
                    .findFirst().ifPresent(entry -> entry.setValue(value));
            existingKeys.add(key);
            update("map_replace", key, value);
        }
    }

    @Override
    protected MapTest<?> test(final Settings settings) {
        return new ReplaceListTest(settings);
    }

    public static void main(final String... args) {
        new PrologListReplaceTest(isSorted(args, PrologListReplaceTest.class)).test();
    }
}
