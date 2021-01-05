package program.beans;

import program.*;
import java.util.ArrayList;
import java.util.List;

public class programBody extends programNode {

    private final List<programNode> children = new ArrayList<>();

    public programBody(programLexer lexer, programNode parent) throws program.exception.ParseException {
        programToken token = lexer.getCurrentToken();
        try {
            switch (token.getType()) {
				case Mod, Minus, NotEqual, OrAssign, ModAssign, XorAssign, Div, LongDouble, AndAnd, Delete, Or, OpenBracket, AndAssign, Digits, Break, False, LeftShift, RightShiftAssign, Multiply, Equal, PlusPlus, Bool, OrOr, DivAssign, LessEqual, For, While, Double, MinusMinus, Semicolon, Not, And, Greater, Assign, Xor, Return, If, Word, MinusAssign, LongLong, RightShift, True, String, Quotes, LeftShiftAssign, Continue, Int, Auto, MultiplyAssign, GreaterEqual, PlusAssign, Void, Less, Vector, Plus:
					children.add(new programPartBody(lexer, this));
					children.add(new programBody(lexer, this));
					depth = parent.depth;
					s = children.get(0).s + (children.get(1).s.isEmpty() ? "" : "\n" + children.get(1).s);
					break;

                default:
					s = "";

            }
        } catch (Exception exception) {
            throw new program.exception.ParseException("Get exception while parse program", exception);
        }
    }
}