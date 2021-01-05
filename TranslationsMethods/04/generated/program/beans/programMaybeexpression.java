package program.beans;

import program.*;
import java.util.ArrayList;
import java.util.List;

public class programMaybeexpression extends programNode {

    private final List<programNode> children = new ArrayList<>();

    public programMaybeexpression(programLexer lexer, programNode parent) throws program.exception.ParseException {
        programToken token = lexer.getCurrentToken();
        try {
            switch (token.getType()) {
				case Mod, OrOr, Minus, DivAssign, NotEqual, OrAssign, LessEqual, ModAssign, XorAssign, Div, MinusMinus, Not, And, AndAnd, Greater, Assign, Xor, Word, MinusAssign, Or, OpenBracket, AndAssign, Digits, RightShift, True, False, Quotes, LeftShiftAssign, LeftShift, RightShiftAssign, Multiply, MultiplyAssign, Equal, GreaterEqual, PlusPlus, PlusAssign, Less, Plus:
					children.add(new programExpression(lexer, this));
					children.add(new programTerminal(programEnum.Semicolon, lexer));
					s = children.get(0).s + ";";
					break;
				case Semicolon:
					children.add(new programTerminal(programEnum.Semicolon, lexer));
					s = ";";
					break;
                default:
					throw new program.exception.UnexpectedTokenException("Unexpected token exception. Actual: " + token.getType());
            }
        } catch (Exception exception) {
            throw new program.exception.ParseException("Get exception while parse program", exception);
        }
    }
}