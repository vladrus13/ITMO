package expression.operations;

import expression.exceptions.ParsingException;

import java.math.BigInteger;

public class DoubleOperation implements Operation<Double> {
    @Override
    public Double parseNumber(String number) throws ParsingException {
        try {
            return Double.parseDouble(number);
        } catch (NumberFormatException e) {
            throw new ParsingException("Uncorrected number in ParserDouble");
        }
    }

    @Override
    public Double add(Double x, Double y) {
        return x + y;
    }

    @Override
    public Double subtract(Double x, Double y) {
        return x - y;
    }

    @Override
    public Double multiply(Double x, Double y) {
        return x * y;
    }

    @Override
    public Double divide(Double x, Double y) {
        return x / y;
    }

    @Override
    public Double not(Double x) {
        return -x;
    }

    @Override
    public Double square(Double x) {
        return multiply(x, x);
    }

    @Override
    public Double abs(Double x) {
        if (x < 0) {
            return not(x);
        } else {
            return x;
        }
    }

    @Override
    public Double mod(Double x, Double y) {
        return x %= y;
    }
}
