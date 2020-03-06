package prolog.prtest.list;

import alice.tuprolog.Int;
import prolog.prtest.map.Settings;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public class PrologListFloorTest extends PrologListTest {
    public PrologListFloorTest(final boolean hard) {
        super(hard);
    }

    class ReplaceListTest extends ListTest {
        public ReplaceListTest(final Settings settings) {
            super(settings);
            actions.clear();
            actions(() -> existingKey(this::floor), () -> uniqueKey(this::floor));
            if (updates) {
                actions(this::putExisting, this::putMissing);
            }
        }

        protected void floor(final Int key) {
            settings.log("map_floorKey", "%s", key);

            assertCall(existingKeys.floor(key), "map_floorKey", actual, key);
        }
    }

    @Override
    protected MapTest<?> test(final Settings settings) {
        return new ReplaceListTest(settings);
    }

    public static void main(final String... args) {
        new PrologListFloorTest(isSorted(args, PrologListFloorTest.class)).test();
    }
}
