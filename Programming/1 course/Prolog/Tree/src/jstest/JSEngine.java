package jstest;

import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public class JSEngine implements Engine {
    public static final String OPTIONS = "-XX:+UnlockExperimentalVMOptions -XX:+EnableJVMCI --module-path=<js>/graal --upgrade-module-path=<js>/graal/compiler.jar";
    private final ScriptEngine engine;
    private final String evaluate;
    private String expression;

    public String toStringMethod = "toString";

    public JSEngine(final String script, final String evaluate) {
        this.evaluate = evaluate;

        try {
            final ScriptEngineManager factory = new ScriptEngineManager();
            engine = factory.getEngineByName("Graal.js");
            if (engine == null) {
                System.err.println("Graal.js not found");
                System.err.println("Use the following options to run tests:");
                System.err.println(OPTIONS);
                System.err.println("Where <js> - path to the javascript directory of this repository");
                throw new AssertionError("Graal.js not found");
            }

            engine.put("io", new IO(engine));
            engine.put("global", engine.getContext().getBindings(ScriptContext.ENGINE_SCOPE));
            engine.eval("var println = function() { io.println(Array.prototype.map.call(arguments, String).join(' ')); };");
            engine.eval("var print   = function() { io.print  (Array.prototype.map.call(arguments, String).join(' ')); };");
            engine.eval("var include = function(file) { io.include(file); }");
            engine.eval("var expr;");
        } catch (final ScriptException e) {
            throw new EngineException("Invalid initialization", e);
        }

        try {
            engine.eval(new FileReader(script, StandardCharsets.UTF_8));
        } catch (final ScriptException e) {
            throw new EngineException("Script error", e);
        } catch (final IOException e) {
            throw new EngineException(String.format("Script '%s' not found", script), e);
        }
    }

    @Override
    public void parse(final String expression) {
        try {
            engine.eval("expr = " + expression);
            this.expression = expression;
        } catch (final ScriptException e) {
            throw new EngineException("Parse error for " + expression, e);
        }
    }

    private <T> Result<T> evaluate(final String code, final Class<T> token) {
        final String context = String.format("%n    in %s%n    where expr = %s%n", code, expression);
        try {
            final Object result = engine.eval(code);
            if (result == null) {
                throw new EngineException("Result is null", null);
            }
            if (token.isAssignableFrom(result.getClass())) {
                return new Result<>(context, token.cast(result));
            }
            throw new EngineException(String.format(
                    "Expected %s, found \"%s\" (%s)%s",
                    token.getSimpleName(),
                    result,
                    result.getClass().getSimpleName(),
                    context
            ), null);
        } catch (final ScriptException e) {
            throw new EngineException("No error expected" + context, e);
        }
    }

    @Override
    public Result<Number> evaluate(final double[] vars) {
        final String code = String.format(
                "expr%s(%s);",
                evaluate,
                Arrays.stream(vars).mapToObj(v -> String.format("%.20f", v)).collect(Collectors.joining(","))
        );
        return evaluate(code, Number.class);
    }

    public Result<String> parsedToString() {
        return evaluate("expr." + toStringMethod + "()", String.class);
    }

    @SuppressWarnings("MethodMayBeStatic")
    public static class IO {
        private final ScriptEngine engine;
        public IO(final ScriptEngine engine) {
            this.engine = engine;
        }

        public void print(final String message) {
            System.out.print(message);
        }

        public void println(final String message) {
            System.out.println(message);
        }

        public void include(final String file) throws IOException, ScriptException {
            engine.eval(new FileReader(file, StandardCharsets.UTF_8));
        }
    }
}
