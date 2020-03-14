package info.kgeorgiy.java.advanced.implementor.basic.interfaces.standard;

import javax.accessibility.AccessibleContext;

/**
 * Copy of {@link javax.accessibility.Accessible}
 */
public interface Accessible {

    /**
     * Returns the {@code AccessibleContext} associated with this object. In
     * most cases, the return value should not be {@code null} if the object
     * implements interface {@code Accessible}. If a component developer creates
     * a subclass of an object that implements {@code Accessible}, and that
     * subclass is not {@code Accessible}, the developer should override the
     * {@code getAccessibleContext} method to return {@code null}.
     *
     * @return the {@code AccessibleContext} associated with this object
     */
    public AccessibleContext getAccessibleContext();
}
