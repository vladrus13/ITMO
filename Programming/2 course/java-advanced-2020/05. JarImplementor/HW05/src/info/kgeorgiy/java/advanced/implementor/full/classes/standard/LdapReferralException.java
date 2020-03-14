package info.kgeorgiy.java.advanced.implementor.full.classes.standard;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.ReferralException;
import javax.naming.ldap.Control;
import java.util.Hashtable;

/**
 * Copy of {@link javax.naming.ldap.LdapReferralException}
 */
public abstract class LdapReferralException extends ReferralException {
    /**
     * Constructs a new instance of LdapReferralException using the
     * explanation supplied. All other fields are set to null.
     *
     * @param   explanation     Additional detail about this exception. Can be null.
     * @see java.lang.Throwable#getMessage
     */
    protected LdapReferralException(String explanation) {
        super(explanation);
    }

    /**
     * Constructs a new instance of LdapReferralException.
     * All fields are set to null.
     */
    protected LdapReferralException() {
        super();
    }

    /**
     * Retrieves the context at which to continue the method using the
     * context's environment and no controls.
     * The referral context is created using the environment properties of
     * the context that threw the {@code ReferralException} and no controls.
     *<p>
     * This method is equivalent to
     *<blockquote><pre>
     * getReferralContext(ctx.getEnvironment(), null);
     *</pre></blockquote>
     * where {@code ctx} is the context that threw the {@code ReferralException.}
     *<p>
     * It is overridden in this class for documentation purposes only.
     * See {@code ReferralException} for how to use this method.
     *
     * @return The non-null context at which to continue the method.
     * @exception NamingException If a naming exception was encountered.
     * Call either {@code retryReferral()} or {@code skipReferral()}
     * to continue processing referrals.
     */
    public abstract Context getReferralContext() throws NamingException;

    /**
     * Retrieves the context at which to continue the method using
     * environment properties and no controls.
     * The referral context is created using {@code env} as its environment
     * properties and no controls.
     *<p>
     * This method is equivalent to
     *<blockquote><pre>
     * getReferralContext(env, null);
     *</pre></blockquote>
     *<p>
     * It is overridden in this class for documentation purposes only.
     * See {@code ReferralException} for how to use this method.
     *
     * @param env The possibly null environment to use when retrieving the
     *          referral context. If null, no environment properties will be used.
     *
     * @return The non-null context at which to continue the method.
     * @exception NamingException If a naming exception was encountered.
     * Call either {@code retryReferral()} or {@code skipReferral()}
     * to continue processing referrals.
     */
    public abstract Context
    getReferralContext(Hashtable<?,?> env)
            throws NamingException;

    /**
     * Retrieves the context at which to continue the method using
     * request controls and environment properties.
     * Regardless of whether a referral is encountered directly during a
     * context operation, or indirectly, for example, during a search
     * enumeration, the referral exception should provide a context
     * at which to continue the operation.
     * To continue the operation, the client program should re-invoke
     * the method using the same arguments as the original invocation.
     *<p>
     * {@code reqCtls} is used when creating the connection to the referred
     * server. These controls will be used as the connection request controls for
     * the context and context instances
     * derived from the context.
     * {@code reqCtls} will also be the context's request controls for
     * subsequent context operations. See the {@code LdapContext} class
     * description for details.
     *<p>
     * This method should be used instead of the other two overloaded forms
     * when the caller needs to supply request controls for creating
     * the referral context. It might need to do this, for example, when
     * it needs to supply special controls relating to authentication.
     *<p>
     * Service provider implementors should read the "Service Provider" section
     * in the {@code LdapContext} class description for implementation details.
     *
     * @param reqCtls The possibly null request controls to use for the new context.
     * If null or the empty array means use no request controls.
     * @param env The possibly null environment properties to use when
     * for the new context. If null, the context is initialized with no environment
     * properties.
     * @return The non-null context at which to continue the method.
     * @exception NamingException If a naming exception was encountered.
     * Call either {@code retryReferral()} or {@code skipReferral()}
     * to continue processing referrals.
     */
    public abstract Context
    getReferralContext(Hashtable<?,?> env,
                       Control[] reqCtls)
            throws NamingException;

    private static final long serialVersionUID = -1668992791764950804L;
}
