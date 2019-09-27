package expression.operations;

import expression.exceptions.ParsingException;

import java.math.BigInteger;

public class BigIntegerOperation implements Operation<BigInteger> {

    @Override
    public BigInteger parseNumber(String number) throws ParsingException {
        try {
            return new BigInteger(number);
        } catch (NumberFormatException e) {
            throw new ParsingException("Uncorrected number in ParserBigInteger");
        }
    }

    private void isZero(BigInteger a) throws ParsingException {
        if (a.equals(BigInteger.ZERO)) {
            throw new ParsingException("BigInteger is zero, dividing by zero");
        }
    }

    @Override
    public BigInteger add(BigInteger x, BigInteger y) {
        return x.add(y);
    }

    @Override
    public BigInteger subtract(BigInteger x, BigInteger y) {
        return x.subtract(y);
    }

    @Override
    public BigInteger multiply(BigInteger x, BigInteger y) {
        return x.multiply(y);
    }

    @Override
    public BigInteger divide(BigInteger x, BigInteger y) throws ParsingException {
        isZero(y);
        return x.divide(y);
    }

    @Override
    public BigInteger not(BigInteger x) {
        return x.negate();
    }

    @Override
    public BigInteger square(BigInteger x) {
        return multiply(x, x);
    }

    @Override
    public BigInteger abs(BigInteger x) {
        return x.abs();
    }

    @Override
    public BigInteger mod(BigInteger x, BigInteger y) throws ParsingException {
        isZero(y);
        if (!y.equals(abs(y))) {
            throw new ParsingException("BigInteger is negative, mod by neg");
        }
        return x.mod(y);
    }
}
