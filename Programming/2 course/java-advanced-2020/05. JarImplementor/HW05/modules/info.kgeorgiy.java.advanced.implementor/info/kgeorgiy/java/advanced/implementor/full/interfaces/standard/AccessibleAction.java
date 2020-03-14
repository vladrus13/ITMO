package info.kgeorgiy.java.advanced.implementor.full.interfaces.standard;

/**
 * Copy of {@link javax.accessibility.AccessibleAction}
 */
public interface AccessibleAction {

    /**
     * An action which causes a tree node to collapse if expanded and expand if
     * collapsed.
     *
     * @since 1.5
     */
    public static final String TOGGLE_EXPAND =
            new String ("toggleexpand");

    /**
     * An action which increments a value.
     *
     * @since 1.5
     */
    public static final String INCREMENT =
            new String ("increment");


    /**
     * An action which decrements a value.
     *
     * @since 1.5
     */
    public static final String DECREMENT =
            new String ("decrement");

    /**
     * An action which causes a component to execute its default action.
     *
     * @since 1.6
     */
    public static final String CLICK = new String("click");

    /**
     * An action which causes a popup to become visible if it is hidden and
     * hidden if it is visible.
     *
     * @since 1.6
     */
    public static final String TOGGLE_POPUP = new String("toggle popup");

    /**
     * Returns the number of accessible actions available in this object If
     * there are more than one, the first one is considered the "default" action
     * of the object.
     *
     * @return the zero-based number of actions in this object
     */
    public int getAccessibleActionCount();

    /**
     * Returns a description of the specified action of the object.
     *
     * @param  i zero-based index of the actions
     * @return a {@code String} description of the action
     * @see #getAccessibleActionCount
     */
    public String getAccessibleActionDescription(int i);

    /**
     * Performs the specified action on the object.
     *
     * @param  i zero-based index of actions
     * @return {@code true} if the action was performed; otherwise {@code false}
     * @see #getAccessibleActionCount
     */
    public boolean doAccessibleAction(int i);
}
