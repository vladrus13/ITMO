package info.kgeorgiy.java.advanced.implementor.full.interfaces;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public class Interfaces {
    public static final Class<?>[] OK = {
            PublicInterface.class,
            PackagePrivateInterface.class,
            InheritedInterface.class,
            ProtectedInterface.class
    };
    public static final Class<?>[] FAILED = {
            PrivateInterface.class
    };

    public interface PublicInterface {
        String hello();
    }

    protected interface ProtectedInterface {
        String hello();
    }

    interface PackagePrivateInterface {
        String hello();
    }

    private interface PrivateInterface {
        String hello();
    }

    interface InheritedInterface extends PrivateInterface {
    }
}
