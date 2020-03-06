package prolog.prtest.map;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public class Settings {
    public final int size;
    public final int range;
    public final int updates;
    public final boolean verbose;

    public Settings(final int size, final int range, final int updates, final boolean verbose) {
        this.size = size;
        this.range = range;
        this.updates = updates;
        this.verbose = verbose;
    }

    public void log(final String name, final String format, final Object... args) {
        if (verbose) {
            System.out.format("%15s %s%n", name, String.format(format, args));
        }
    }

    public void tick(final int i) {
        if (!verbose && i > 0 && i % 10 == 0) {
            System.out.format("    update %s of %s%n", i, updates);
        }
    }

    void start() {
        System.out.format("=== size = %d%n", size);
    }
}
