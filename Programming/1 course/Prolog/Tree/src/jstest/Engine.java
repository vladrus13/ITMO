package jstest;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public interface Engine {
    void parse(String expression);

    Result<Number> evaluate(double[] vars);

    class Result<T> {
        public final String context;
        public final T value;

        public Result(final String context, final T value) {
            this.context = context;
            this.value = value;
        }
    }
}
