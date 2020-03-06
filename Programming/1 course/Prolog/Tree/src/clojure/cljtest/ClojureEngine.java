package clojure.cljtest;

import clojure.lang.IFn;
import jstest.Engine;

import java.util.Optional;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public class ClojureEngine implements Engine {
    private static final IFn HASH_MAP = ClojureScript.var("clojure.core/hash-map");

    private final Optional<IFn> evaluate;
    private final String evaluateString;
    private Result<Object> parsed;
    private String expression;

    public ClojureEngine(final String script, final Optional<String> evaluate) {
        ClojureScript.loadScript(script);

        this.evaluate = evaluate.map(ClojureScript::var);
        evaluateString = evaluate.map(s -> s + " ").orElse("");
    }

    @Override
    public void parse(final String expression) {
        parsed = new Result<>(expression, ClojureScript.LOAD_STRING_IN.invoke(expression));
        this.expression = expression;
    }

    @Override
    public Result<Number> evaluate(final double[] vars) {
        final Object map = HASH_MAP.invoke("x", vars[0], "y", vars[1], "z", vars[2]);
        final String context = java.lang.String.format("(%sexpr %s)\nwhere expr = %s", evaluateString, map, expression);
        return evaluate
                .map(f -> ClojureScript.call(f, new Object[]{parsed.value, map}, Number.class, context))
                .orElseGet(() -> ClojureScript.call((IFn) parsed.value, new Object[]{map}, Number.class, context));
    }

    public Result<String> toString(final ClojureScript.F<String> f) {
        return f.call(parsed);
    }
}
