package info.kgeorgiy.java.advanced.implementor.basic.classes.standard;

import javax.management.relation.RelationException;

/**
 * Copy of {@link javax.management.relation.RelationNotFoundException}
 */
public class RelationNotFoundException extends RelationException {

    /* Serial version */
    private static final long serialVersionUID = -3793951411158559116L;

    /**
     * Constructor with given message put in exception.
     *
     * @param message the detail message.
     */
    public RelationNotFoundException(String message) {
        super(message);
    }
}
