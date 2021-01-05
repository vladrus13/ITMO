package program.beans;

import program.*;
import java.util.ArrayList;
import java.util.List;

public class programType extends programNode {

    private final List<programNode> children = new ArrayList<>();

    public programType(programLexer lexer, programNode parent) throws program.exception.ParseException {
        programToken token = lexer.getCurrentToken();
        try {
            switch (token.getType()) {
				case Int:
					children.add(new programTerminal(programEnum.Int, lexer));
					s = "int";
					break;
				case LongLong:
					children.add(new programTerminal(programEnum.LongLong, lexer));
					s = "long long";
					break;
				case Auto:
					children.add(new programTerminal(programEnum.Auto, lexer));
					s = "auto";
					break;
				case Bool:
					children.add(new programTerminal(programEnum.Bool, lexer));
					s = "bool";
					break;
				case Double:
					children.add(new programTerminal(programEnum.Double, lexer));
					s = "double";
					break;
				case LongDouble:
					children.add(new programTerminal(programEnum.LongDouble, lexer));
					s = "long double";
					break;
				case Void:
					children.add(new programTerminal(programEnum.Void, lexer));
					s = "void";
					break;
				case String:
					children.add(new programTerminal(programEnum.String, lexer));
					s = "string";
					break;
				case Vector:
					children.add(new programTerminal(programEnum.Vector, lexer));
					children.add(new programTerminal(programEnum.Less, lexer));
					children.add(new programType(lexer, this));
					children.add(new programTerminal(programEnum.Greater, lexer));
					s = "vector <" + children.get(2).s + ((programTerminal)children.get(3)).getData();
					break;
                default:
					throw new program.exception.UnexpectedTokenException("Unexpected token exception. Actual: " + token.getType());
            }
        } catch (Exception exception) {
            throw new program.exception.ParseException("Get exception while parse program", exception);
        }
    }
}