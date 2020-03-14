package info.kgeorgiy.java.advanced.implementor.full.classes.standard;

import javax.management.remote.rmi.RMIConnection;
import javax.management.remote.rmi.RMIServerImpl;
import javax.security.auth.Subject;
import java.io.IOException;
import java.rmi.Remote;
import java.util.Map;

/**
 * Copy of {@link javax.management.remote.rmi.RMIIIOPServerImpl}
 */
public class RMIIIOPServerImpl extends RMIServerImpl {
    /**
     * Throws {@linkplain UnsupportedOperationException}
     *
     * @param env the environment containing attributes for the new
     * <code>RMIServerImpl</code>.  Can be null, which is equivalent
     * to an empty Map.
     *
     * @throws IOException if the RMI object cannot be created.
     */
    public RMIIIOPServerImpl(Map<String,?> env)
            throws IOException {
        super(env);

        throw new UnsupportedOperationException();
    }

    @Override
    protected void export() throws IOException {
        throw new UnsupportedOperationException("Method not supported. JMX RMI-IIOP is deprecated");
    }

    @Override
    protected String getProtocol() {
        return "iiop";
    }

    @Override
    public Remote toStub() throws IOException {
        throw new UnsupportedOperationException();
    }

    @Override
    protected RMIConnection makeClient(String connectionId, Subject subject)
            throws IOException {
        throw new UnsupportedOperationException();
    }

    @Override
    protected void closeClient(RMIConnection client) throws IOException {
        throw new UnsupportedOperationException();
    }

    @Override
    protected void closeServer() throws IOException {
        throw new UnsupportedOperationException();
    }
}
