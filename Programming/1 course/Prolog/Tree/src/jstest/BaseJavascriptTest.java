package jstest;

import expression.BaseTest;

import java.util.*;
import java.util.function.*;

/**
 * @author Niyaz Nigmatullin
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public abstract class BaseJavascriptTest<E extends Engine> extends BaseTest {
    public static final int N = 5;
    public static final double EPS = 1e-5;

    protected final E engine;
    protected final Language language;

    final boolean testParsing;

    protected BaseJavascriptTest(final E engine, final Language language, final boolean testParsing) {
        this.engine = engine;
        this.language = language;
        this.testParsing = testParsing;
    }

    private static boolean safe(final char ch) {
        return !Character.isLetterOrDigit(ch) && "+-*/.".indexOf(ch) == -1;
    }

    protected static String addSpaces(final String expression, final Random random) {
        String spaced = expression;
        for (int n = StrictMath.min(10, 200 / expression.length()); n > 0;) {
            final int index = random.nextInt(spaced.length() + 1);
            final char c = index == 0 ? 0 : spaced.charAt(index - 1);
            final char nc = index == spaced.length() ? 0 : spaced.charAt(index);
            if ((safe(c) || safe(nc)) && c != '\'' && nc != '\'' && c != '"' && nc != '"') {
                spaced = spaced.substring(0, index) + " " + spaced.substring(index);
                n--;
            }
        }
        return spaced;
    }

    @Override
    protected void test() {
        for (final Expr<TExpr> test : language.tests) {
            test(test.parsed, test.answer, test.unparsed);
            if (testParsing) {
                test(parse(test.unparsed), test.answer, test.unparsed);
                test(parse(addSpaces(test.unparsed, random)), test.answer, test.unparsed);
            }
        }

        testRandom(444);
    }

    protected abstract String parse(final String expression);

    protected void test(final String expression, final TExpr f, final String unparsed) {
        System.out.println("Testing: " + expression);

        engine.parse(expression);
        for (double i = 0; i <= N; i += 1) {
            for (double j = 0; j <= N; j += 1) {
                for (double k = 0; k <= N; k += 1) {
                    final double[] vars = new double[]{i, j, k};
                    evaluate(vars, f.evaluate(vars), EPS);
                }
            }
        }

        test(expression, unparsed);
    }

    protected void test(final String parsed, final String unparsed) {
    }

    public void testRandom(final int n) {
        System.out.println("Testing random tests");
        for (int i = 0; i < n; i++) {
            if (i % 100 == 0) {
                System.out.println("    Completed " + i + " out of " + n);
            }
            final double[] vars = random.doubles().limit(language.abstractTests.getVariables().size()).toArray();

            final Expr<TExpr> test = language.randomTest(random, i);
            final double answer = test.answer.evaluate(vars);

            engine.parse(test.parsed);
            evaluate(vars, answer, EPS);
            test(test.parsed, test.unparsed);
            test(addSpaces(test.parsed, random), test.unparsed);
            if (testParsing) {
                counter.nextTest();
                final String expr = parse(test.unparsed);
                test(expr, test.unparsed);

                engine.parse(expr);
                evaluate(vars, answer, EPS);
                counter.passed();
            }
        }
    }

    protected void evaluate(final double[] vars, final double answer, final double precision) {
        counter.nextTest();
        final Engine.Result<Number> result = engine.evaluate(vars);
        assertEquals(result.context, precision, answer, result.value.doubleValue());
        counter.passed();
    }

    public static Dialect dialect(final String variable, final String constant, final BiFunction<String, List<String>, String> nary) {
        return new Dialect(variable, constant, nary);
    }

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
        System.err.println("Usage: java -ea " + JSEngine.OPTIONS + " " + type.getName() + " {" + String.join("|", modes) + "}");
        System.exit(0);
        return -1;
    }

    public interface TExpr {
        double evaluate(double... vars);
    }

    public interface Operator<T> {
        T apply(List<T> args);

        int arity();
    }

    public static class Dialect {
        private final String variable;
        private final String constant;
        private final BiFunction<String, List<String>, String> nary;
        private final Map<String, String> operations;

        public Dialect(final String variable, final String constant, final BiFunction<String, List<String>, String> nary) {
            this(variable, constant, nary, new HashMap<>());
        }

        private Dialect(final String variable, final String constant, final BiFunction<String, List<String>, String> nary, final Map<String, String> operations) {
            this.variable = variable;
            this.constant = constant;
            this.nary = nary;
            this.operations = operations;
        }

        public Dialect rename(final String name, final String alias) {
            operations.put(name, alias);
            return this;
        }

        public String variable(final String name) {
            return String.format(variable, name);
        }

        public String constant(final int value) {
            return String.format(constant, value);
        }

        public String operation(final String name, final List<String> as) {
            return nary.apply(operations.getOrDefault(name, name), as);
        }

        public static String nullary(final String name) {
            return name;
        }

        public Dialect copy() {
            return new Dialect(variable, constant, nary, new HashMap<>(operations));
        }
    }

    public static class Expr<T> {
        public final String parsed;
        public final String unparsed;
        public final T answer;

        protected Expr(final String parsed, final String unparsed, final T answer) {
            this.parsed = Objects.requireNonNull(parsed);
            this.unparsed = Objects.requireNonNull(unparsed);
            this.answer = answer;
        }
    }
}
