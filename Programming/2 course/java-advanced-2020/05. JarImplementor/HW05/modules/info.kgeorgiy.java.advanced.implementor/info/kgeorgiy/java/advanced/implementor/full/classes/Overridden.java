package info.kgeorgiy.java.advanced.implementor.full.classes;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public class Overridden {
    public static final Class<?>[] OK = {
            Base.class, Child.class,
            FinalGrandChild.class, NonFinalGrandChild.class,
            SuperMethods.class, $.class,
            PrivateSuper.class
    };

    public static final Class<?>[] FAILED = {PrivateClass.class, PrivateArg.class, PrivateResult.class};

    public static class Base {
        protected int protectedHello() {
            return 0;
        }
        public int publicHello() {
            return 0;
        }
        int packageHello() {
            return 0;
        }
    }

    @SuppressWarnings("AbstractMethodOverridesConcreteMethod")
    public static abstract class Child extends Base {
        @Override
        protected abstract int protectedHello();

        @Override
        public abstract int publicHello();

        @Override
        abstract int packageHello();
    }

    public static abstract class FinalGrandChild extends Child {
        private final int value;

        public FinalGrandChild(int value) {
            this.value = value;
        }

        @Override
        protected final int protectedHello() {
            return value;
        }

        @Override
        public final int publicHello() {
            return value;
        }

        @Override
        final int packageHello() {
            return value;
        }
    }

    public static abstract class NonFinalGrandChild extends Child {
    }

    @SuppressWarnings("MethodNameSameAsClassName")
    public static abstract class SuperMethods extends Child {
        SuperMethods(final SuperMethods methods) {
            System.out.println(methods);
        }
        abstract void SuperMethods(SuperMethods methods);
        abstract void Child(Child child);
        abstract void Base(Base base);
        abstract void NonFinalGrandChild(NonFinalGrandChild child);
    }

    public static abstract class $ {
        abstract void $();
        abstract void $$();
        abstract void __();
    }

    public abstract static class PrivateSuper extends PrivateClass {
        public PrivateSuper(final int value) {
            super(value);
        }
    }

    private static abstract class PrivateClass {
        private final int value;

        public PrivateClass(int value) {
            this.value = value;
        }

        abstract int hello();
    }

    public static abstract class PrivateArg {
        abstract int get(PrivateClass arg);
    }

    public static abstract class PrivateResult {
        abstract PrivateClass get();
    }
}
