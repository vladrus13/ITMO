package program.beans;

import program.*;
import java.util.ArrayList;
import java.util.List;

public class programBinaryassignornotoperator extends programNode {

    private final List<programNode> children = new ArrayList<>();

    public programBinaryassignornotoperator(programLexer lexer, programNode parent) throws program.exception.ParseException {
        programToken token = lexer.getCurrentToken();
        try {
            switch (token.getType()) {
				case MinusAssign, MultiplyAssign, DivAssign, AndAssign, PlusAssign, OrAssign, Assign, ModAssign, LeftShiftAssign, XorAssign, RightShiftAssign:
					children.add(new programBinaryassignoperator(lexer, this));
					s = children.get(0).s;
					break;
				case Mod, OrOr, Minus, Or, NotEqual, RightShift, LessEqual, LeftShift, Multiply, Div, Equal, GreaterEqual, And, AndAnd, Greater, Xor, Less, Plus:
					children.add(new programBinaryoperator(lexer, this));
					s = children.get(0).s;
					break;
                default:
					throw new program.exception.UnexpectedTokenException("Unexpected token exception. Actual: " + token.getType());
            }
        } catch (Exception exception) {
            throw new program.exception.ParseException("Get exception while parse program", exception);
        }
    }
}