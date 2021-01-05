package program.beans;

import program.*;
import java.util.ArrayList;
import java.util.List;

public class programT0 extends programNode {

    private final List<programNode> children = new ArrayList<>();

    public programT0(programLexer lexer, programNode parent) throws program.exception.ParseException {
        programToken token = lexer.getCurrentToken();
        try {
            switch (token.getType()) {
				case Mod, OrOr, Minus, DivAssign, NotEqual, OrAssign, LessEqual, ModAssign, XorAssign, Div, And, AndAnd, Greater, Assign, Xor, MinusAssign, Or, AndAssign, RightShift, LeftShiftAssign, LeftShift, RightShiftAssign, Multiply, MultiplyAssign, Equal, GreaterEqual, PlusAssign, Less, Plus:
					children.add(new programBinaryassignornotoperator(lexer, this));
					children.add(new programExpression(lexer, this));
					children.add(new programT0(lexer, this));
					s = " " + children.get(0).s + " " + children.get(1).s + children.get(2).s;
					break;
				case MinusMinus, PlusPlus:
					children.add(new programMaybesingleassignoperator(lexer, this));
					s = children.get(0).s;
					break;

                default:
					s = "";

            }
        } catch (Exception exception) {
            throw new program.exception.ParseException("Get exception while parse program", exception);
        }
    }
}