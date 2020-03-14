package info.kgeorgiy.java.advanced.base;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.rules.TestRule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;
import java.util.function.Consumer;

/**
 * Tests base class.
 *
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
@RunWith(JUnit4.class)
public class BaseTest {
    public static final String CUT_PROPERTY = "cut";

    @Rule
    public TestRule watcher = watcher(description -> System.err.println("=== Running " + description.getMethodName()));

    protected static TestWatcher watcher(final Consumer<Description> watcher) {
        return new TestWatcher() {
            @Override
            protected void starting(final Description description) {
                watcher.accept(description);
            }
        };
    }

    @SuppressWarnings({"unchecked", "unused"})
    public static <T> T createCUT() {
        try {
            return (T) loadClass().getDeclaredConstructor().newInstance();
        } catch (final InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            throw new AssertionError(e);
        } catch (final InvocationTargetException e) {
            throw new AssertionError(e.getCause());
        }
    }

    public static Class<?> loadClass() {
        final String className = System.getProperty(CUT_PROPERTY);
        Assert.assertNotNull("Class name not specified", className);

        try {
            return Class.forName(className);
        } catch (final ClassNotFoundException e) {
            throw new AssertionError(e);
        }
    }

    public <E extends Exception> void parallelCommands(final int threads, final List<Command<E>> commands) {
        final ExecutorService executor = Executors.newFixedThreadPool(threads);
        try {
            for (final Future<Void> future : executor.invokeAll(commands)) {
                future.get();
            }
            executor.shutdown();
        } catch (final InterruptedException | ExecutionException e) {
            throw new AssertionError(e);
        }
    }

    public <E extends Exception> void parallel(final int threads, final Command<E> command) {
        parallelCommands(threads, Collections.nCopies(threads, command));
    }

    public static void checkConstructor(final String description, final Class<?> token, final Class<?>... params) {
        try {
            token.getConstructor(params);
        } catch (final NoSuchMethodException e) {
            Assert.fail(token.getName() + " should have " + description);
        }
    }

    public interface Command<E extends Exception> extends Callable<Void> {
        @Override
        default Void call() throws E {
            run();
            return null;
        }

        void run() throws E;
    }

    public interface ConsumerCommand<T, E extends Exception> {
        void run(T value) throws E;
    }
}
