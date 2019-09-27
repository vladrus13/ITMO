package expression;

import expression.exceptions.DivideByZeroException;
import expression.exceptions.OverflowException;
import expression.exceptions.ParsingException;

public class Main {
    public static void main(String[] s) throws DivideByZeroException, ParsingException, OverflowException {
        System.out.print(new CheckedLog(new CheckedConst(8), new CheckedConst(2)).evaluate(0, 0,0));
    }
}
