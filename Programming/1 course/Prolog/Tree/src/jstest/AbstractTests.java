package jstest;

import jstest.BaseJavascriptTest.Dialect;
import jstest.BaseJavascriptTest.Expr;
import jstest.BaseJavascriptTest.TExpr;
import jstest.BaseJavascriptTest.Operator;

import java.util.*;
import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleUnaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public abstract class AbstractTests implements Cloneable {
    public final List<AbstractExpression> tests = new ArrayList<>();
    private final Map<String, Operator<Double>> operators = new HashMap<>();
    private final Map<String, TExpr> nullary = new HashMap<>();
    private final List<AbstractExpression> variables = new ArrayList<>();

    private final Map<String, Integer> variableNames = new HashMap<>();
    private final List<String> operatorNames = new ArrayList<>();
    private final List<String> nullaryNames = new ArrayList<>();

    public List<Expr<TExpr>> renderTests(final Dialect parsed, final Dialect unparsed) {
        return tests.stream().map(test -> test.render(parsed, unparsed)).collect(Collectors.toList());
    }

    public Expr<TExpr> randomTest(final Random random, final int size, final Dialect parsed, final Dialect unparsed) {
        return generate(random, size / 5 + 2).render(parsed, unparsed);
    }

    private AbstractExpression generate(final Random random, final int depth) {
        if (depth > 0) {
            return generateOp(random, depth);
        } else if (random.nextBoolean()) {
            return variables.get(random.nextInt(variables.size()));
        } else if (nullary.isEmpty() || random.nextBoolean()){
            return c(random.nextInt());
        } else {
            return n(nullaryNames.get(random.nextInt(nullaryNames.size())));
        }
    }

    protected AbstractExpression generateOp(final Random random, final int depth) {
        if (random.nextInt(6) == 0) {
            return generateP(random, depth);
        } else {
            final String name = operatorNames.get(random.nextInt(operatorNames.size()));
            final int arity = operators.get(name).arity();
            return f(
                    name,
                    Stream.generate(() -> generateP(random, depth))
                            .limit(arity == -1 ? 1 + random.nextInt(4) : arity)
                            .toArray(AbstractExpression[]::new)
            );
        }
    }

    protected AbstractExpression generateP(final Random random, final int depth) {
        return generate(random, random.nextInt(depth));
    }

    protected void tests(final AbstractExpression... tests) {
        this.tests.addAll(Arrays.asList(tests));
    }

    protected void any(final String name, final int arity, final Function<List<Double>, Double> f) {
        operatorNames.add(name);
        operators.put(name, new Operator<Double>() {
            @Override
            public Double apply(final List<Double> args) {
                return f.apply(args);
            }

            @Override
            public int arity() {
                return arity;
            }
        });
    }

    public void unary(final String name, final DoubleUnaryOperator answer) {
        any(name, 1, args -> answer.applyAsDouble(args.get(0)));
    }

    public void binary(final String name, final DoubleBinaryOperator answer) {
        any(name, 2, args -> answer.applyAsDouble(args.get(0), args.get(1)));
    }

    public void nullary(final String name, final TExpr f) {
        nullaryNames.add(name);
        nullary.put(name, f);
    }

    protected AbstractExpression f(final String name, final AbstractExpression... args) {
        return (parsed, unparsed) -> {
            final List<Expr<TExpr>> rendered = Arrays.stream(args).map(a -> a.render(parsed, unparsed)).collect(Collectors.toList());
            return expr(
                    parsed.operation(name, rendered.stream().map(a -> a.parsed).collect(Collectors.toList())),
                    unparsed.operation(name, rendered.stream().map(a -> a.unparsed).collect(Collectors.toList())),
                    vars -> {
                        final Operator<Double> operator = operators.get(name);
                        assert operator != null : "Unknown operation " + name + " in " + getClass().getSimpleName();
                        return operator.apply(rendered.stream()
                                .map(a -> a.answer)
                                .map(a -> a.evaluate(vars))
                                .collect(Collectors.toList()));
                    }
            );
        };
    }

    protected AbstractExpression n(final String name) {
        return (parsed, unparsed) -> expr(Dialect.nullary(name), Dialect.nullary(name), nullary.get(name));
    }

    protected static AbstractExpression c(final int value) {
        return (parsed, unparsed) -> expr(parsed.constant(value), unparsed.constant(value), vars -> value);
    }

    protected AbstractExpression variable(final String name, final int index) {
        final AbstractExpression variable = (parsed, unparsed) -> expr(parsed.variable(name), unparsed.variable(name), vars -> vars[index]);
        variables.add(variable);
        variableNames.put(name, index);
        return variable;
    }

    protected interface AbstractExpression {
        Expr<TExpr> render(Dialect parsed, Dialect unparsed);
    }

    public static <T> Expr<T> expr(final String parsed, final String unparsed, final T answer) {
        return new Expr<>(parsed, unparsed, answer);
    }

    public List<AbstractExpression> getVariables() {
        return variables;
    }

    public Map<String, Integer> getVariableNames() {
        return variableNames;
    }
}
