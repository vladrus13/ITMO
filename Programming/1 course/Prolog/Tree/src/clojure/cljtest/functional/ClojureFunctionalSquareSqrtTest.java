package clojure.cljtest.functional;

import clojure.cljtest.multi.MultiSquareSqrtTests;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public class ClojureFunctionalSquareSqrtTest extends ClojureFunctionalExpressionTest {
    protected ClojureFunctionalSquareSqrtTest(final boolean testMulti) {
        super(new MultiSquareSqrtTests(testMulti));
    }

    public static void main(final String... args) {
        new ClojureFunctionalSquareSqrtTest(mode(args, ClojureFunctionalSquareSqrtTest.class)).run();
    }
}
