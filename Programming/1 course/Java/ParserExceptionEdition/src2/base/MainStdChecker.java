package base;

import java.io.*;
import java.util.List;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public class MainStdChecker extends MainChecker {

    public MainStdChecker(final String className) {
        super(className);
    }

    protected List<String> runStd(final List<String> input) {
        final InputStream oldIn = System.in;
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (final PrintWriter writer = new PrintWriter(baos)) {
            input.forEach(writer::println);
        }

        try {
            System.setIn(new ByteArrayInputStream(baos.toByteArray()));
            return run();
        } finally {
            System.setIn(oldIn);
        }
    }
}
