package expression.operations;

import expression.exceptions.ParsingException;

import java.math.BigInteger;

public class LongOperation implements Operation<Long> {

    @Override
    public Long parseNumber(String number) throws ParsingException {
        try {
            return Long.parseLong(number);
        } catch (NumberFormatException e) {
            throw new ParsingException("Uncorrected number in ParserInteger");
        }
    }

    @Override
    public Long add(Long x, Long y) {
        return (x + y);
    }

    @Override
    public Long subtract(Long x, Long y) {
        return x - y;
    }

    @Override
    public Long multiply(Long x, Long y) {
        return x * y;
    }

    @Override
    public Long divide(Long x, Long y) throws ParsingException {
        if (y == 0) {
            throw new ParsingException("Byte divide by zero!");
        }
        return x / y;
    }

    @Override
    public Long not(Long x) {
        return -x;
    }

    @Override
    public Long square(Long x) {
        return multiply(x, x);
    }

    @Override
    public Long abs(Long x) {
        if (x < 0) {
            return not(x);
        } else {
            return x;
        }
    }

    @Override
    public Long mod(Long x, Long y) {
        return Long.parseLong((new BigInteger(String.valueOf(x))).mod(new BigInteger(String.valueOf(y))).toString());
    }
}
