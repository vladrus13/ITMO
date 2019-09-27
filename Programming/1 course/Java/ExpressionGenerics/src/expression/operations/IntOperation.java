package expression.operations;

import expression.exceptions.ParsingException;

import java.math.BigInteger;

public class IntOperation implements Operation<Integer> {
    private final String overflowMessage = "Checker %s say, what your number is so %s!";
    private boolean check;

    public IntOperation(boolean b) {
        this.check = b;
    }

    @Override
    public Integer parseNumber(String number) throws ParsingException {
        try {
            return Integer.parseInt(number);
        } catch (NumberFormatException e) {
            throw new ParsingException("Uncorrected number in ParserInteger");
        }
    }

    private void checkAdd(Integer x, Integer y) throws ParsingException {
        if (x > 0 && Integer.MAX_VALUE - x < y) {
            throw new ParsingException(String.format(overflowMessage, "Add", "big"));
        }
        if (x < 0 && Integer.MIN_VALUE - x > y) {
            throw new ParsingException(String.format(overflowMessage, "Add", "small"));
        }
    }

    private void checkSub(Integer x, Integer y) throws ParsingException {
        if (x >= 0 && y < 0 && x - Integer.MAX_VALUE > y) {
            throw new ParsingException(String.format(overflowMessage, "Sub", "big"));
        }
        if (x <= 0 && y > 0 && Integer.MIN_VALUE - x > -y) {
            throw new ParsingException(String.format(overflowMessage, "Sub", "small"));
        }
    }

    private void checkMul(Integer x, Integer y) throws ParsingException {
        if (x > 0 && y > 0 && Integer.MAX_VALUE / x < y) {
            throw new ParsingException(String.format(overflowMessage, "Mul", "big"));
        }
        if (x > 0 && y < 0 && Integer.MIN_VALUE / x > y) {
            throw new ParsingException(String.format(overflowMessage, "Mul", "small"));
        }
        if (x < 0 && y > 0 && Integer.MIN_VALUE / y > x) {
            throw new ParsingException(String.format(overflowMessage, "Mul", "small"));
        }
        if (x < 0 && y < 0 && Integer.MAX_VALUE / x > y) {
            throw new ParsingException(String.format(overflowMessage, "Mul", "big"));
        }
    }

    private void checkDiv(Integer x, Integer y) throws ParsingException {
        if (x == Integer.MIN_VALUE && y == -1) {
            throw new ParsingException(String.format(overflowMessage, "Div", "big"));
        }
    }

    private void checkNot(Integer x) throws ParsingException {
        if (x == Integer.MIN_VALUE) {
            throw new ParsingException(String.format(overflowMessage, "Not", "big"));
        }
    }

    @Override
    public Integer add(Integer x, Integer y) throws ParsingException {
        if (check) {
            checkAdd(x, y);
        }
        return x + y;
    }

    @Override
    public Integer subtract(Integer x, Integer y) throws ParsingException {
        if (check) {
            checkSub(x, y);
        }
        return x - y;
    }

    @Override
    public Integer multiply(Integer x, Integer y) throws ParsingException {
        if (check) {
            checkMul(x, y);
        }
        return x * y;
    }

    @Override
    public Integer divide(Integer x, Integer y) throws ParsingException {
        if (y == 0) {
            throw new ParsingException("Integer divide by zero!");
        }
        if (check) {
            checkDiv(x, y);
        }
        return x / y;
    }

    @Override
    public Integer not(Integer x) throws ParsingException {
        if (check) {
            checkNot(x);
        }
        return -x;
    }

    @Override
    public Integer square(Integer x) throws ParsingException {
        return multiply(x, x);
    }

    @Override
    public Integer abs(Integer x) throws ParsingException {
        if (x < 0) {
            return not(x);
        } else {
            return x;
        }
    }

    @Override
    public Integer mod(Integer x, Integer y) throws ParsingException{
        if (y == 0) {
            throw new ParsingException("Integer divide by zero!");
        }
        return x %= y;
    }
}
