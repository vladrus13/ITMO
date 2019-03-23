package expression.operations;

import expression.exceptions.ParsingException;

public class ByteOperation implements Operation<Byte> {

    @Override
    public Byte parseNumber(String number) throws ParsingException {
        try {
            return (byte) Integer.parseInt(number);
        } catch (NumberFormatException e) {
            throw new ParsingException("Uncorrected number in ParserInteger");
        }
    }

    @Override
    public Byte add(Byte x, Byte y) {
        return (byte) (x + y);
    }

    @Override
    public Byte subtract(Byte x, Byte y) {
        return (byte) (x - y);
    }

    @Override
    public Byte multiply(Byte x, Byte y) {
        return (byte) (x * y);
    }

    @Override
    public Byte divide(Byte x, Byte y) throws ParsingException {
        if (y == 0) {
            throw new ParsingException("Byte divide by zero!");
        }
        return (byte) (x / y);
    }

    @Override
    public Byte not(Byte x) {
        return (byte) (-x);
    }

    @Override
    public Byte square(Byte x) {
        return multiply(x, x);
    }

    @Override
    public Byte abs(Byte x) {
        if (x < 0) {
            return not(x);
        } else {
            return x;
        }
    }

    @Override
    public Byte mod(Byte x, Byte y) throws ParsingException {
        if (y == 0) {
            throw new ParsingException("Byte divide by zero!");
        }
        return (byte) (x % y);
    }
}
