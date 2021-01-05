package ru.vladrus13.utils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.Logger;

/**
 * Writer-utils class
 */
public class Writer {
    /**
     * @param logger logger where to write
     * @param e      exception we write
     */
    public static void printStackTrace(Logger logger, Exception e) {
        StringWriter stringWriter = new StringWriter();
        e.printStackTrace(new PrintWriter(stringWriter));
        logger.severe(stringWriter.toString());
    }
}
