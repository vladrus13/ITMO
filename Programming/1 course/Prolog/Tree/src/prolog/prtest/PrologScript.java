package prolog.prtest;

import alice.tuprolog.*;
import base.Asserts;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public class PrologScript {
    private static PrologException error(final Throwable cause, final String format, final Object... arguments) {
        return new PrologException(String.format(format, arguments), cause);
    }

    private final Prolog prolog = new Prolog();

    public PrologScript() {
        prolog.addExceptionListener(exceptionEvent -> {throw new PrologException(exceptionEvent.getMsg());});
    }

    public PrologScript(final String file) {
        this();
        consult(file);
    }

    public void consult(final String file) {
        try {
            if (!prolog.solve(new Struct("consult", new Struct(file))).isSuccess()) {
                throw error(null, "Error opening '%s'", file);
            }
        } catch (final PrologException e) {
            throw error(e, "Error opening '%s': %s", file, e.getMessage());
        }
    }

    List<Term> solve(final Var var, final Term term) {
        SolveInfo info = prolog.solve(term);
        final List<Term> values = new ArrayList<>();
        try {
            while (info.isSuccess()) {
                values.add(info.getVarValue(var.getName()));
                if (!info.hasOpenAlternatives()) {
                    return values;
                }
                info = prolog.solveNext();
            }
            return values;
        } catch (final NoSolutionException | NoMoreSolutionException e) {
            throw new AssertionError(e);
        }
    }

    public void solveNone(final Var var, final Struct term) {
        final List<Term> values = solve(var, term);
        if (!values.isEmpty()) {
            throw Asserts.error("No solutions expected for %s in %s%n  found: %d %s", var, term, values.size(), values);
        }
    }

    public Term solveOne(final Var var, final Term term) {
        final List<Term> values = solve(var, term);
        if (values.size() != 1) {
            throw Asserts.error("Exactly one solution expected for %s in %s%n  found: %d %s", var, term, values.size(), values);
        }
        return values.get(0);
    }
}
