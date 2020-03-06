package prolog.prtest.map;

import alice.tuprolog.Int;
import alice.tuprolog.Struct;
import alice.tuprolog.Term;
import alice.tuprolog.Var;
import base.Asserts;
import base.Randomized;
import base.TestCounter;
import prolog.prtest.PrologScript;

import java.util.*;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public abstract class PrologMapTest extends Randomized {
    public static final Var V = new Var("V");
    protected final PrologScript prolog;
    protected final boolean updates;
    private final TestCounter counter = new TestCounter();

    public PrologMapTest(final boolean updates, final String file) {
        prolog = new PrologScript(file);
        this.updates = updates;
    }

    protected void test() {
        run();
        counter.printStatus(getClass(), toString());
    }

    @Override
    public String toString() {
        return super.getClass().getSimpleName() + "(" + updates + ")";
    }

    private void run() {
        for (int i = 0; i < 10; i++) {
            runTest(new Settings(i, 10, 10, true));
        }
        runTest(new Settings(100, 10000, 100, false));
        runTest(new Settings(200, 10000, 0, false));
    }

    private void runTest(final Settings settings) {
        test(settings).run();
    }

    protected abstract MapTest<?> test(Settings settings);

    protected static int mode(final String[] args, final Class<?> type, final String... modes) {
        if (args.length == 0) {
            System.err.println("No arguments found");
        } else if (args.length > 1) {
            System.err.println("Only one argument expected, " + args.length + " found");
        } else if (Arrays.asList(modes).indexOf(args[0]) < 0) {
            System.err.println("First argument should be one of: \"" + String.join("\", \"", modes) + "\", found: \"" + args[0] + "\"");
        } else {
            return Arrays.asList(modes).indexOf(args[0]);
        }
        System.err.println("Usage: java -ea " + type.getName() + " {" + String.join("|", modes) + "}");
        System.exit(0);
        return -1;
    }

    protected abstract class MapTest<T> {
        protected final Settings settings;
        protected final T expected;
        protected final NavigableSet<Int> existingKeys = new TreeSet<>();
        final Set<Int> removedKeys = new TreeSet<>();
        final List<Int> testKeys = new ArrayList<>();
        protected Term actual;
        protected final List<BooleanSupplier> actions = new ArrayList<>();

        public MapTest(final Settings settings, final T expected) {
            this.settings = settings;
            this.expected = expected;
            if (updates) {
                actions(this::putExisting, this::putMissing, this::removeExisting, this::removeMissing);
            }
        }

        protected void actions(final BooleanSupplier... actions) {
            this.actions.addAll(List.of(actions));
        }

        public void run() {
            settings.start();

            final List<Entry> entries = new ArrayList<>();
            for (int i = 0; i < settings.size; i++) {
                final Entry entry = new Entry(uniqueKey(), randomValue());
                entries.add(entry);
                existingKeys.add(entry.getKey());
            }

            entries.sort(Comparator.comparing(Entry::getKey));

            build(entries);

            testKeys.addAll(existingKeys);
            for (int i = 0; i < 10; i++) {
                uniqueKey(testKeys::add);
            }

            check();

            if (!actions.isEmpty()) {
                for (int i = 0; i < settings.updates; i++) {
                    settings.tick(i);
                    while (!randomItem(actions).getAsBoolean()) {
                        // Empty
                    }
                    check();
                }
            }
        }

        private void build(final List<Entry> entries) {
            settings.log("build", "%s", entries);
            actual = build(entries, new Struct(entries.stream().map(Entry::getStruct).toArray(Term[]::new)));
        }

        protected abstract Term build(List<Entry> entries, Struct pairs);

        protected Term call(final String name, final Term... args) {
            return prolog.solveOne(V, query(name, args));
        }

        private Struct query(final String name, final Term[] args) {
            final Term[] fullArgs = Arrays.copyOf(args, args.length + 1);
            fullArgs[args.length] = V;
//            System.out.println(new Struct(name, fullArgs));
            return new Struct(name, fullArgs);
        }

        protected boolean putExisting() {
            return existingKey(this::put);
        }

        protected boolean putMissing() {
            return uniqueKey(this::put);
        }

        private void put(final Int key) {
            final Term value = randomValue();
            update("map_put", key, value);

            existingKeys.add(key);
            testKeys.add(key);
            removedKeys.remove(key);
            putImpl(key, value);
        }

        protected void get(final Term expected, final Int key) {
            assertCall(expected, "map_get", actual, key);
        }

        protected abstract void putImpl(final Int key, final Term value);

        protected boolean removeExisting() {
            return existingKey(this::remove);
        }

        protected boolean removeMissing() {
            return uniqueKey(this::remove);
        }

        private void remove(final Int key) {
            existingKeys.remove(key);
            removedKeys.add(key);
            update("map_remove", key);
            removeImpl(key);
        }

        protected abstract void removeImpl(Int key);

        protected boolean existingKey(final Consumer<Int> consumer) {
            return random(existingKeys, consumer);
        }

        protected boolean removedKey(final Consumer<Int> consumer) {
            return random(removedKeys, consumer);
        }

        private boolean random(final Set<Int> keys, final Consumer<Int> consumer) {
            if (keys.isEmpty()) {
                return false;
            }

            consumer.accept(random(keys));
            return true;
        }

        protected Int random(final Set<Int> ints) {
            return ints.stream()
                    .skip(randomInt(0, ints.size()))
                    .findFirst().orElseThrow();
        }

        protected Term randomValue() {
            return new Struct(randomString(ENGLISH));
        }

        private Int randomKey() {
            return new Int(randomInt(-settings.range, settings.range));
        }

        protected Int uniqueKey() {
            while (true) {
                final Int key = randomKey();
                if (!existingKeys.contains(key)) {
                    return key;
                }
            }
        }

        protected boolean uniqueKey(final Consumer<Int> consumer) {
            consumer.accept(uniqueKey());
            return true;
        }

        protected void check() {
            counter.nextTest();
            settings.log("check", "%s", expected);
//            settings.log("", "%s", actual);
            Collections.shuffle(testKeys, random);
            check(testKeys);
            counter.passed();
        }

        protected abstract void check(List<Int> testKeys);

        protected void assertCall(final Object value, final String name, final Term... args) {
            if (value != null) {
                if (!Objects.equals(value, call(name, args))) {
                    throw Asserts.error("%s:%n    expected `%s`,%n   actual `%s`", query(name, args), value, call(name, args));
                }
            } else {
                prolog.solveNone(V, query(name, args));
            }
        }

        protected void update(final String name, final Term... args) {
            settings.log(name, "(map, %s, V)", Stream.of(args).map(Object::toString).collect(Collectors.joining(", ")));
            final Term[] fullArgs = new Term[args.length + 1];
            fullArgs[0] = actual;
            System.arraycopy(args, 0, fullArgs, 1, args.length);
            actual = call(name, fullArgs);
        }
    }
}
