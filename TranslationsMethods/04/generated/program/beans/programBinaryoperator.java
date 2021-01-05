package program.beans;

import program.*;
import java.util.ArrayList;
import java.util.List;

public class programBinaryoperator extends programNode {

    private final List<programNode> children = new ArrayList<>();

    public programBinaryoperator(programLexer lexer, programNode parent) throws program.exception.ParseException {
        programToken token = lexer.getCurrentToken();
        try {
            switch (token.getType()) {
				case Plus:
					children.add(new programTerminal(programEnum.Plus, lexer));
					s = "+";
					break;
				case Minus:
					children.add(new programTerminal(programEnum.Minus, lexer));
					s = "-";
					break;
				case Multiply:
					children.add(new programTerminal(programEnum.Multiply, lexer));
					s = "*";
					break;
				case Div:
					children.add(new programTerminal(programEnum.Div, lexer));
					s = "/";
					break;
				case Mod:
					children.add(new programTerminal(programEnum.Mod, lexer));
					s = "%";
					break;
				case Xor:
					children.add(new programTerminal(programEnum.Xor, lexer));
					s = "^";
					break;
				case And:
					children.add(new programTerminal(programEnum.And, lexer));
					s = "&";
					break;
				case Or:
					children.add(new programTerminal(programEnum.Or, lexer));
					s = "|";
					break;
				case Less:
					children.add(new programTerminal(programEnum.Less, lexer));
					s = "<";
					break;
				case Greater:
					children.add(new programTerminal(programEnum.Greater, lexer));
					s = ((programTerminal)children.get(0)).getData();
					break;
				case Equal:
					children.add(new programTerminal(programEnum.Equal, lexer));
					s = "=";
					break;
				case NotEqual:
					children.add(new programTerminal(programEnum.NotEqual, lexer));
					s = "!=";
					break;
				case LessEqual:
					children.add(new programTerminal(programEnum.LessEqual, lexer));
					s = "<=";
					break;
				case GreaterEqual:
					children.add(new programTerminal(programEnum.GreaterEqual, lexer));
					s = ((programTerminal)children.get(0)).getData();
					break;
				case AndAnd:
					children.add(new programTerminal(programEnum.AndAnd, lexer));
					s = "&&";
					break;
				case OrOr:
					children.add(new programTerminal(programEnum.OrOr, lexer));
					s = "||";
					break;
				case LeftShift:
					children.add(new programTerminal(programEnum.LeftShift, lexer));
					s = ((programTerminal)children.get(0)).getData();
					break;
				case RightShift:
					children.add(new programTerminal(programEnum.RightShift, lexer));
					break;
                default:
					throw new program.exception.UnexpectedTokenException("Unexpected token exception. Actual: " + token.getType());
            }
        } catch (Exception exception) {
            throw new program.exception.ParseException("Get exception while parse program", exception);
        }
    }
}