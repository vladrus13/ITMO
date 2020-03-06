package prolog.prtest.tree;

import alice.tuprolog.Int;
import prolog.prtest.map.Settings;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public class PrologTreeFloorTest extends PrologTreeTest {
    public PrologTreeFloorTest(final boolean hard) {
        super(hard);
    }

    class ReplaceTreeTest extends TreeTest {
        public ReplaceTreeTest(final Settings settings) {
            super(settings);
            actions.clear();
            actions(() -> existingKey(this::floor), () -> uniqueKey(this::floor));
            if (updates) {
                actions(this::putExisting, this::putMissing);
            }
        }

        protected void floor(final Int key) {
            settings.log("map_floorKey", "%s", key);
            assertCall(expected.floorKey(key), "map_floorKey", actual, key);
        }
    }

    @Override
    protected MapTest<?> test(final Settings settings) {
        return new ReplaceTreeTest(settings);
    }

    public static void main(final String... args) {
        new PrologTreeFloorTest(isBonus(args, PrologTreeFloorTest.class)).test();
    }
}
