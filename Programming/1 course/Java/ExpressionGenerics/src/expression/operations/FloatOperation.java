package expression.operations;

import expression.exceptions.ParsingException;

import java.math.BigInteger;

public class FloatOperation implements Operation<Float> {
    @Override
    public Float parseNumber(String number) throws ParsingException {
        try {
            return Float.parseFloat(number);
        } catch (NumberFormatException e) {
            throw new ParsingException("Uncorrected number in ParserDouble");
        }
    }

    @Override
    public Float add(Float x, Float y) {
        return x + y;
    }

    @Override
    public Float subtract(Float x, Float y) {
        return x - y;
    }

    @Override
    public Float multiply(Float x, Float y) {
        return x * y;
    }

    @Override
    public Float divide(Float x, Float y) {
        return x / y;
    }

    @Override
    public Float not(Float x) {
        return -x;
    }

    @Override
    public Float square(Float x) {
        return multiply(x, x);
    }

    @Override
    public Float abs(Float x) {
        if (x < 0) {
            return not(x);
        } else {
            return x;
        }
    }

    @Override
    public Float mod(Float x, Float y) {

        return x % y;
    }
}
