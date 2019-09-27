package expression;

import expression.exceptions.DivideByZeroException;
import expression.exceptions.OverflowException;
import expression.exceptions.ParsingException;

public class Main {
    public static void main(String[] s) throws DivideByZeroException, ParsingException, OverflowException {
        System.out.print(new CheckedHigh(new CheckedConst(-4)).evaluate(0, 0 , 0));
    }
}
