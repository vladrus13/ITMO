package program.beans;

import program.*;
import java.util.ArrayList;
import java.util.List;

public class programBinaryassignoperator extends programNode {

    private final List<programNode> children = new ArrayList<>();

    public programBinaryassignoperator(programLexer lexer, programNode parent) throws program.exception.ParseException {
        programToken token = lexer.getCurrentToken();
        try {
            switch (token.getType()) {
				case Assign:
					children.add(new programTerminal(programEnum.Assign, lexer));
					s = "=";
					break;
				case PlusAssign:
					children.add(new programTerminal(programEnum.PlusAssign, lexer));
					s = "+=";
					break;
				case MinusAssign:
					children.add(new programTerminal(programEnum.MinusAssign, lexer));
					s = "-=";
					break;
				case MultiplyAssign:
					children.add(new programTerminal(programEnum.MultiplyAssign, lexer));
					s = "*=";
					break;
				case DivAssign:
					children.add(new programTerminal(programEnum.DivAssign, lexer));
					s = "/=";
					break;
				case ModAssign:
					children.add(new programTerminal(programEnum.ModAssign, lexer));
					s = "%=";
					break;
				case XorAssign:
					children.add(new programTerminal(programEnum.XorAssign, lexer));
					s = "^=";
					break;
				case AndAssign:
					children.add(new programTerminal(programEnum.AndAssign, lexer));
					s = "&=";
					break;
				case OrAssign:
					children.add(new programTerminal(programEnum.OrAssign, lexer));
					s = "|=";
					break;
				case LeftShiftAssign:
					children.add(new programTerminal(programEnum.LeftShiftAssign, lexer));
					s = "<<=";
					break;
				case RightShiftAssign:
					children.add(new programTerminal(programEnum.RightShiftAssign, lexer));
					s = ((programTerminal)children.get(0)).getData();
					break;
                default:
					throw new program.exception.UnexpectedTokenException("Unexpected token exception. Actual: " + token.getType());
            }
        } catch (Exception exception) {
            throw new program.exception.ParseException("Get exception while parse program", exception);
        }
    }
}