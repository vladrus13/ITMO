package expression.operations;

import expression.exceptions.ParsingException;

import java.math.BigInteger;

public class ShortOperation implements Operation<Short> {

    @Override
    public Short parseNumber(String number) throws ParsingException {
        try {
            return (short) Integer.parseInt(number);
        } catch (NumberFormatException e) {
            throw new ParsingException("Uncorrected number in ParserInteger");
        }
    }

    @Override
    public Short add(Short x, Short y) {
        return (short) (x + y);
    }

    @Override
    public Short subtract(Short x, Short y) {
        return (short) (x - y);
    }

    @Override
    public Short multiply(Short x, Short y) {
        return (short) (x * y);
    }

    @Override
    public Short divide(Short x, Short y) throws ParsingException {
        if (y == 0) {
            throw new ParsingException("Byte divide by zero!");
        }
        return (short) (x / y);
    }

    @Override
    public Short not(Short x) {
        return (short) -x;
    }

    @Override
    public Short square(Short x) {
        return multiply(x, x);
    }

    @Override
    public Short abs(Short x) {
        if (x < 0) {
            return not(x);
        } else {
            return x;
        }
    }

    @Override
    public Short mod(Short x, Short y) {
        return Short.parseShort((new BigInteger(String.valueOf(x))).mod(new BigInteger(String.valueOf(y))).toString());
    }
}
