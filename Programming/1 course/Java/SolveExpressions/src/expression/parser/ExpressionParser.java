package expression.parser;

import expression.*;

public class ExpressionParser {
    private String data;
    private String variableName;
    private int index = 0;
    private int value = -0;

    private enum Token {
        ADD, AND, BINARY_NOT, CLOSE_B, OPEN_B, COUNT, DIV, MULTI, UNARY_NOT, SUB, OR, XOR, ERROR, CONST, VARIABLE, END;
    }

    private void deleteWhiteSpaces(String s) {
        StringBuilder ans = new StringBuilder("");
        for (int i = 0; i < s.length(); i++) {
            if (!Character.isWhitespace(s.charAt(i))) {
                ans.append(s.charAt(i));
            }
        }
        data = ans.toString();
    }

    Token currentToken = Token.ERROR;

    Token nextToken() {
        if (index >= data.length()) {
            return Token.END;
        }
        char currentCharacter = data.charAt(index);
        index++;
        switch (currentCharacter) {
            case '+':
                return currentToken = Token.ADD;
            case '-':
                if (currentToken == Token.VARIABLE || currentToken == Token.CONST || currentToken == Token.CLOSE_B) {
                    return currentToken = Token.SUB;
                } else {
                    return currentToken = Token.UNARY_NOT;
                }
            case '/':
                return currentToken = Token.DIV;
            case '*':
                return currentToken = Token.MULTI;
            case '(':
                return currentToken = Token.OPEN_B;
            case ')':
                return currentToken = Token.CLOSE_B;
            case '&':
                return currentToken = Token.AND;
            case '|':
                return currentToken = Token.OR;
            case '^':
                return currentToken = Token.XOR;
            case '~':
                return currentToken = Token.BINARY_NOT;
            default:
                if (Character.isDigit(currentCharacter)) {
                    int beginChar = index - 1;
                    index--;
                    while (index < data.length() && Character.isDigit(data.charAt(index))) {
                        index++;
                    }
                    value = Integer.parseUnsignedInt(data.substring(beginChar, index));
                    return currentToken = Token.CONST;
                } else {
                    index--;
                    if (index + 5 <= data.length() && data.substring(index, index + 5).equals("count")) {
                        index += 5;
                        return currentToken = Token.COUNT;
                    } else {
                        index++;
                        if (Character.isLetter(currentCharacter)) {
                            int beginChar = index - 1;
                            index--;
                            while (index < data.length() && Character.isLetter(data.charAt(index))) {
                                index++;
                            }
                            variableName = data.substring(beginChar, index);
                            return currentToken = Token.VARIABLE;
                        }
                    }
                }
        }
        return currentToken = Token.ERROR;
    }

    private TripleExpression unaryOperation() {
        nextToken();
        TripleExpression returned;
        switch (currentToken) {
            case CONST:
                returned = new Const(value);
                nextToken();
                return returned;
            case VARIABLE:
                returned = new Variable(variableName);
                nextToken();
                return returned;
            case BINARY_NOT:
                return new BinaryNot(unaryOperation());
            case UNARY_NOT:
                return new UnaryNot(unaryOperation());
            case COUNT:
                return new Count(unaryOperation());
            case OPEN_B:
                returned = or();
                nextToken();
                return returned;
            default:
                return new Const(123456);
        }
    }

    private TripleExpression multiplyDivide() {
        TripleExpression returned = unaryOperation();
        while (true) {
            switch (currentToken) {
                case MULTI:
                    returned = new Multiply(returned, unaryOperation());
                    break;
                case DIV:
                    returned = new Divide(returned, unaryOperation());
                    break;
                default:
                    return returned;
            }
        }
    }

    private TripleExpression addSubstract() {
        TripleExpression returned = multiplyDivide();
        while (true) {
            switch (currentToken) {
                case ADD:
                    returned = new Add(returned, multiplyDivide());
                    break;
                case SUB:
                    returned = new Subtract(returned, multiplyDivide());
                    break;
                default:
                    return returned;
            }
        }
    }

    private TripleExpression and() {
        TripleExpression returned = addSubstract();
        while (true) {
            switch (currentToken) {
                case AND:
                    returned = new And(returned, and());
                    break;
                default:
                    return returned;
            }
        }
    }

    private TripleExpression xor() {
        TripleExpression returned = and();
        while (true) {
            switch (currentToken) {
                case XOR:
                    returned = new Xor(returned, xor());
                    break;
                default:
                    return returned;
            }
        }
    }

    private TripleExpression or() {
        TripleExpression returned = xor();
        while (true) {
            switch (currentToken) {
                case OR:
                    returned = new Or(returned, xor());
                    break;
                default:
                    return returned;
            }
        }
    }

    public TripleExpression parse(String s) {
        index = 0;
        deleteWhiteSpaces(s);
        currentToken = Token.ERROR;
        return or();
    }
}
