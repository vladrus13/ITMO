package clojure.cljtest;

import clojure.java.api.Clojure;
import clojure.lang.IFn;
import jstest.Engine;
import jstest.EngineException;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public class ClojureScript {
    public static final IFn LOAD_STRING = var("clojure.core/load-string");
    public static final IFn LOAD_FILE = asUser("load-file");
    public static final IFn LOAD_STRING_IN = asUser("load-string");

    private static IFn asUser(final String function) {
        return (IFn) LOAD_STRING.invoke(
                "(fn " + function +  "-in [arg]" +
                        "  (binding [*ns* *ns*]" +
                        "    (in-ns 'user)" +
                        "    (" + function + " arg)))"
        );
    }

    public static void loadScript(final String script) {
        LOAD_STRING.invoke("(println *ns*)");
        LOAD_FILE.invoke(script);
    }

    protected static <T> Engine.Result<T> call(final IFn f, final Object[] args, final Class<T> type, final String context) {
        final Object result;
        try {
            result = callUnsafe(f, args);
        } catch (final Throwable e) {
            throw new EngineException("No error expected in " + context, e);
        }
        if (result == null) {
            throw new EngineException(String.format("Expected %s, found null\n%s", type.getSimpleName(), context), null);
        }
        if (!type.isAssignableFrom(result.getClass())) {
            throw new EngineException(String.format("Expected %s, found %s (%s)\n%s", type.getSimpleName(), result, result.getClass().getSimpleName(), context), null);
        }
        return new Engine.Result<>(context, type.cast(result));
    }

    private static Object callUnsafe(final IFn f, final Object[] args) {
        switch (args.length) {
            case 0: return f.invoke();
            case 1: return f.invoke(args[0]);
            case 2: return f.invoke(args[0], args[1]);
            case 3: return f.invoke(args[0], args[1], args[2]);
            case 4: return f.invoke(args[0], args[1], args[2], args[3]);
            case 5: return f.invoke(args[0], args[1], args[2], args[3], args[4]);
            case 6: return f.invoke(args[0], args[1], args[2], args[3], args[4], args[5]);
            case 7: return f.invoke(args[0], args[1], args[2], args[3], args[4], args[5], args[6]);
            case 8: return f.invoke(args[0], args[1], args[2], args[3], args[4], args[5], args[6], args[7]);
            default: throw new AssertionError("Too many arguments");
        }
    }

    public static Engine.Result<Throwable> expectException(final IFn f, final Object[] args, final String context) {
        try {
            callUnsafe(f, args);
        } catch (final Throwable e) {
            return new Engine.Result<>(context, e);
        }
        assert false : "Exception expected in " + context;
        return null;
    }

    public static <T> F<T> function(final String name, final Class<T> type) {
        return new F<>(name, type);
    }

    /**
     * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
     */
    public static class F<T> {
        private final String name;
        private final Class<T> type;
        private final IFn f;

        public F(final String name, final Class<T> type) {
            this.name = name.substring(name.indexOf("/") + 1);
            this.type = type;
            f = var(name);
        }

        public Engine.Result<T> call(final Engine.Result<?>... args) {
            return ClojureScript.call(
                    f,
                    Arrays.stream(args).map(arg -> arg.value).toArray(),
                    type,
                    "(" + name + " " + Arrays.stream(args).map(arg -> arg.context).collect(Collectors.joining(" ")) + ")"
            );
        }

        public Engine.Result<Throwable> expectException(final Engine.Result<?>... args) {
            return ClojureScript.expectException(
                    f,
                    Arrays.stream(args).map(arg -> arg.value).toArray(),
                    "(" + name + " " + Arrays.stream(args).map(arg -> arg.context).collect(Collectors.joining(" ")) + ")"
            );
        }
    }

    public static IFn var(final String name) {
        final String qualifiedName = (name.contains("/") ? "" : "user/") + name;
        return Clojure.var(qualifiedName);
    }
}
