package expression.generic;

import expression.TripleExpression;
import expression.exceptions.ParsingException;
import expression.operations.*;
import expression.parser.ExpressionParser;
import expression.parser.Parser;

import java.util.Map;

public class GenericTabulator implements Tabulator {
    private final static Map<String, Operation<?>> MODE = Map.ofEntries(
            Map.entry("i", new IntOperation(true)),
            Map.entry("u", new IntOperation(false)),
            Map.entry("bi", new BigIntegerOperation()),
            Map.entry("d", new DoubleOperation()),
            Map.entry("l", new LongOperation()),
            Map.entry("f", new FloatOperation()),
            Map.entry("b", new ByteOperation()),
            Map.entry("s", new ShortOperation())
    );

    @Override
    public Object[][][] tabulate(String mode, String expression, int x1, int x2, int y1, int y2, int z1, int z2) {
        return solve(operation(mode), expression, x1, x2, y1, y2, z1, z2);
    }

    private <T> Object[][][] solve(Operation<T> operation, String exp, int x1, int x2, int y1, int y2, int z1, int z2) {
        Parser<T> parser = new ExpressionParser<>(operation);
        TripleExpression<T> parse;
        Object[][][] returned = new Object[x2 - x1 + 1][y2 - y1 + 1][z2 - z1 + 1];
        try {
            parse = parser.parse(exp);
        } catch (Exception e) {
            return returned;
        }
        for (int i = x1; i <= x2; i++) {
            for (int j = y1; j <= y2; j++) {
                for (int k = z1; k <= z2; k++) {
                    try {
                        returned[i - x1][j - y1][k - z1] = parse.evaluate(operation.parseNumber(Integer.toString(i)), operation.parseNumber(Integer.toString(j)), operation.parseNumber(Integer.toString(k)));
                    } catch (ParsingException e) {
                        returned[i - x1][j - y1][k - z1] = null;
                    }
                }
            }
        }
        return returned;
    }

    private Operation<?> operation(String m) {
        return MODE.get(m);
    }
}
