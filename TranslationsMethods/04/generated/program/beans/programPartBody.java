package program.beans;

import program.*;
import java.util.ArrayList;
import java.util.List;

public class programPartBody extends programNode {

    private final List<programNode> children = new ArrayList<>();

    public programPartBody(programLexer lexer, programNode parent) throws program.exception.ParseException {
        programToken token = lexer.getCurrentToken();
        try {
            switch (token.getType()) {
				case Mod, OrOr, Minus, DivAssign, NotEqual, OrAssign, LessEqual, ModAssign, XorAssign, Div, MinusMinus, Semicolon, Not, And, AndAnd, Greater, Assign, Xor, Word, MinusAssign, Or, OpenBracket, AndAssign, Digits, RightShift, True, False, Quotes, LeftShiftAssign, LeftShift, RightShiftAssign, Multiply, MultiplyAssign, Equal, GreaterEqual, PlusPlus, PlusAssign, Less, Plus:
					children.add(new programMaybeexpression(lexer, this));
					depth = parent.depth;
					s = children.get(0).s;
					break;
				case For:
					children.add(new programRFor(lexer, this));
					depth = parent.depth;
					s = children.get(0).s;
					break;
				case While:
					children.add(new programRWhile(lexer, this));
					depth = parent.depth;
					s = children.get(0).s;
					break;
				case Delete, Break, Return, Continue:
					children.add(new programInstruction(lexer, this));
					depth = parent.depth;
					s = children.get(0).s;
					break;
				case If:
					children.add(new programRIf(lexer, this));
					depth = parent.depth;
					s = children.get(0).s;
					break;
				case Auto, LongDouble, LongLong, Bool, String, Void, Double, Vector, Int:
					children.add(new programField(lexer, this));
					depth = parent.depth;
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