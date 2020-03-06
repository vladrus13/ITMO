package clojure.cljtest.object;

import clojure.cljtest.multi.MultiSinhCoshTests;
import clojure.cljtest.functional.ClojureFunctionalExpressionTest;
import jstest.Language;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public class ClojureObjectSinhCoshTest extends ClojureObjectExpressionTest {
    public static final Dialect PARSED = ClojureObjectExpressionTest.PARSED.copy()
            .rename("sinh", "Sinh")
            .rename("cosh", "Cosh");

    protected ClojureObjectSinhCoshTest(final boolean testMulti) {
        super(new Language(PARSED, ClojureFunctionalExpressionTest.UNPARSED, new MultiSinhCoshTests(testMulti)));
    }

    public static void main(final String... args) {
        new ClojureObjectSinhCoshTest(mode(args, ClojureObjectSinhCoshTest.class)).run();
    }
}
