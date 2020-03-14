package info.kgeorgiy.java.advanced.implementor.basic.classes.standard;

import java.util.ResourceBundle;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public class ClassLogger implements Logger {

    private final String className;
    private final Logger logger;

    public ClassLogger(String subsystem, String className) {
        logger = null;
        this.className = className;
    }

    public final boolean traceOn() {
        return logger.isLoggable(Level.TRACE);
    }

    public final boolean debugOn() {
        return logger.isLoggable(Level.DEBUG);
    }

    public final boolean warningOn() {
        return logger.isLoggable(Level.WARNING);
    }

    public final boolean infoOn() {
        return logger.isLoggable(Level.INFO);
    }

    public final boolean configOn() {
        return logger.isLoggable(Level.DEBUG);
    }

    public final boolean fineOn() {
        return logger.isLoggable(Level.DEBUG);
    }

    public final boolean finerOn() {
        return logger.isLoggable(Level.TRACE);
    }

    public final boolean finestOn() {
        return logger.isLoggable(Level.TRACE);
    }

    public final void debug(String func, String msg) {
        logger.log(Level.DEBUG, msg);
    }

    public final void debug(String func, Throwable t) {
        logger.log(Level.DEBUG, className + "::" + func, t);
    }

    public final void debug(String func, String msg, Throwable t) {
        logger.log(Level.DEBUG, msg, t);
    }

    public final void trace(String func, String msg) {
        logger.log(Level.TRACE, msg);
    }

    public final void trace(String func, Throwable t) {
        logger.log(Level.TRACE, className + "::" + func, t);
    }

    public final void trace(String func, String msg, Throwable t) {
        logger.log(Level.TRACE, msg, t);
    }

    public final void error(String func, String msg) {
        logger.log(Level.ERROR, msg);
    }

    public final void error(String func, Throwable t) {
        logger.log(Level.ERROR, className + "::" + func, t);
    }

    public final void error(String func, String msg, Throwable t) {
        logger.log(Level.ERROR, msg, t);
    }

    public final void finest(String func, String msg) {
        logger.log(Level.TRACE, msg);
    }

    public final void finest(String func, Throwable t) {
        logger.log(Level.TRACE, className + "::" + func, t);
    }

    public final void finest(String func, String msg, Throwable t) {
        logger.log(Level.TRACE, msg, t);
    }

    public final void finer(String func, String msg) {
        logger.log(Level.TRACE, msg);
    }

    public final void finer(String func, Throwable t) {
        logger.log(Level.TRACE, className + "::" + func, t);
    }

    public final void finer(String func, String msg, Throwable t) {
        logger.log(Level.DEBUG, msg, t);
    }

    public final void fine(String func, String msg) {
        logger.log(Level.DEBUG, msg);
    }

    public final void fine(String func, Throwable t) {
        logger.log(Level.DEBUG, className + "::" + func, t);
    }

    public final void fine(String func, String msg, Throwable t) {
        logger.log(Level.DEBUG, msg, t);
    }

    public final void config(String func, String msg) {
        logger.log(Level.DEBUG, msg);
    }

    public final void config(String func, Throwable t) {
        logger.log(Level.DEBUG, className + "::" + func, t);
    }

    public final void config(String func, String msg, Throwable t) {
        logger.log(Level.DEBUG, msg, t);
    }

    public final void info(String func, String msg) {
        logger.log(Level.INFO, msg);
    }

    public final void info(String func, Throwable t) {
        logger.log(Level.INFO, className + "::" + func, t);
    }

    public final void info(String func, String msg, Throwable t) {
        logger.log(Level.INFO, msg, t);
    }

    public final void warning(String func, String msg) {
        logger.log(Level.WARNING, msg);
    }

    public final void warning(String func, Throwable t) {
        logger.log(Level.WARNING, className + "::" + func, t);
    }

    public final void warning(String func, String msg, Throwable t) {
        logger.log(Level.WARNING, msg, t);
    }

    public final void severe(String func, String msg) {
        logger.log(Level.ERROR, msg);
    }

    public final void severe(String func, Throwable t) {
        logger.log(Level.ERROR, className + "::" + func, t);
    }

    public final void severe(String func, String msg, Throwable t) {
        logger.log(Level.ERROR, msg, t);
    }

    public final String getName() {
        return logger.getName();
    }

    public final boolean isLoggable(Level level) {
        return logger.isLoggable(level);
    }

    public final void log(Level level, ResourceBundle bundle, String msg,
                          Throwable thrown) {
        logger.log(level, bundle, msg, thrown);
    }

    public final void log(Level level, ResourceBundle bundle, String format,
                          Object... params) {
        logger.log(level, bundle, format, params);
    }

}
