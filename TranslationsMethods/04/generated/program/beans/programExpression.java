package program.beans;

import program.*;
import java.util.ArrayList;
import java.util.List;

public class programExpression extends programNode {

    private final List<programNode> children = new ArrayList<>();

    public programExpression(programLexer lexer, programNode parent) throws program.exception.ParseException {
        programToken token = lexer.getCurrentToken();
        try {
            switch (token.getType()) {
				case Not:
					children.add(new programUnaryoperation(lexer, this));
					children.add(new programExpression(lexer, this));
					children.add(new programT0(lexer, this));
					s = children.get(0).s + " " + children.get(1).s + children.get(2).s;
					break;
				case OpenBracket:
					children.add(new programTerminal(programEnum.OpenBracket, lexer));
					children.add(new programExpression(lexer, this));
					children.add(new programTerminal(programEnum.CloseBracket, lexer));
					children.add(new programT0(lexer, this));
					s = "(" + children.get(1).s + ")";
					break;
				case Mod, OrOr, Minus, DivAssign, NotEqual, OrAssign, LessEqual, ModAssign, XorAssign, Div, MinusMinus, And, AndAnd, Greater, Assign, Xor, MinusAssign, Or, AndAssign, RightShift, LeftShiftAssign, LeftShift, RightShiftAssign, Multiply, MultiplyAssign, Equal, GreaterEqual, PlusPlus, PlusAssign, Less, Plus:
					children.add(new programT0(lexer, this));
					s = children.get(0).s;
					break;
				case Word, Digits, True, False, Quotes:
					children.add(new programValue(lexer, this));
					children.add(new programT0(lexer, this));
					s = children.get(0).s + children.get(1).s;
					break;
                default:
					throw new program.exception.UnexpectedTokenException("Unexpected token exception. Actual: " + token.getType());
            }
        } catch (Exception exception) {
            throw new program.exception.ParseException("Get exception while parse program", exception);
        }
    }
}