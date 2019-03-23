package expression.parser;

import expression.*;
import expression.exceptions.*;
import expression.TripleExpression;
import expression.Variable;

import java.util.EnumSet;
import java.util.Set;

public class ExpressionParser implements Parser {
    private String data;
    private String variableName;
    private int index = 0;
    private int value = -0;
    private int balance = 0;

    private enum Token {
        ADD, CLOSE_B, OPEN_B, DIV, MULTI, NEGATE, SUB, ERROR, CONST, VARIABLE, END, HIGH, LOW
    }

    private Set<Token> operations = EnumSet.of(Token.ADD, Token.SUB, Token.DIV, Token.MULTI, Token.NEGATE);

    private Token currentToken = Token.ERROR;

    private void skipSpace() {
        while (index < data.length() && Character.isWhitespace(data.charAt(index))) {
            index++;
        }
    }

    private String generateErrorString(int begin_error, int end_error) {
        if (begin_error < 0) begin_error = 0;
        if (end_error >= data.length()) end_error = data.length() - 1;
        StringBuilder answer = new StringBuilder("\nString:\n");
        int NUM_INDENTION = 3;
        answer.append(data.substring(Math.max(0, begin_error - NUM_INDENTION), begin_error));
        answer.append("<<<");
        answer.append(data.substring(begin_error, end_error));
        answer.append(">>>");
        if (end_error + 1 < data.length()) {
            answer.append(data.substring(end_error, Math.min(data.length(), end_error + NUM_INDENTION)));
        }
        answer.append('\n');
        return answer.toString();
    }

    private void checkOperator() throws MissingOperatorException {
        if (operations.contains(currentToken) || currentToken == Token.OPEN_B || currentToken == Token.ERROR) {
            if (index == 0) {
                throw new MissingOperatorException("Begin from Operator. \nError at index " + String.valueOf(index - 1) + generateErrorString(index, index));
            } else {
                throw new MissingOperatorException("Operator after operator. \nError at index " + String.valueOf(index - 1) + generateErrorString(index - 1, index));
            }
        }
    }

    private void checkOperation() throws MissingOperatorException {
        if (currentToken == Token.CLOSE_B || currentToken == Token.VARIABLE || currentToken == Token.CONST) {
            throw new MissingOperatorException("Value after value. \nError at index " + String.valueOf(index) + generateErrorString(index, index));
        }
    }

    private Token nextToken() throws ParsingException, ValueException {
        skipSpace();
        if (index >= data.length()) {
            checkOperator();
            return currentToken = Token.END;
        }
        char currentCharacter = data.charAt(index);
        index++;
        switch (currentCharacter) {
            case '+':
                checkOperator();
                return currentToken = Token.ADD;
            case '-':
                if (currentToken == Token.VARIABLE || currentToken == Token.CONST || currentToken == Token.CLOSE_B) {
                    return currentToken = Token.SUB;
                } else {
                    if (index >= data.length()) {
                        throw new MissingOperatorException("No value after minus. \nError at index " + String.valueOf(index) + generateErrorString(index - 1, index));
                    }
                    if (Character.isDigit(data.charAt(index))) {
                        int beginChar = index;
                        while (index < data.length() && Character.isDigit(data.charAt(index))) {
                            index++;
                        }
                        String temp = data.substring(beginChar, index);
                        try {
                            value = Integer.parseInt("-" + temp);
                        } catch (NumberFormatException e) {
                            throw new ConstException("\nError at index " + String.valueOf(index) + generateErrorString(beginChar - 1, index));
                        }
                        return currentToken = Token.CONST;
                    }
                    return currentToken = Token.NEGATE;
                }
            case '/':
                checkOperator();
                if (index >= data.length()) {
                    throw new MissingOperatorException("No value after divide. \nError at index " + String.valueOf(index) + generateErrorString(index - 1, index));
                }
                return currentToken = Token.DIV;
            case '*':
                checkOperator();
                if (index >= data.length()) {
                    throw new MissingOperatorException("No value after multiply...\nError at index " + String.valueOf(index) + generateErrorString(index - 1, index));
                }
                return currentToken = Token.MULTI;
            case '(':
                checkOperation();
                balance++;
                return currentToken = Token.OPEN_B;
            case ')':
                if (operations.contains(currentToken) || currentToken == Token.OPEN_B) {
                    throw new MissingOperatorException("Operator before bracket.\nError at index " + String.valueOf(index) + generateErrorString(index - 1, index));
                }
                balance--;
                if (balance < 0) {
                    throw new BraketsBalanceException("Error balance brackets. \nError at index " + String.valueOf(index) + generateErrorString(index - 1, index));
                }
                return currentToken = Token.CLOSE_B;
            default:
                if (Character.isDigit(currentCharacter)) {
                    checkOperation();
                    int beginChar = index - 1;
                    index--;
                    while (index < data.length() && Character.isDigit(data.charAt(index))) {
                        index++;
                    }
                    String temp = data.substring(beginChar, index);
                    try {
                        value = Integer.parseInt(temp);
                    } catch (NumberFormatException e) {
                        throw new ConstException("\nError at index " + String.valueOf(index) + generateErrorString(beginChar, index));
                    }
                    return currentToken = Token.CONST;
                } else {
                    if (Character.isLetter(currentCharacter)) {
                        int beginChar = index - 1;
                        index--;
                        while (index < data.length() && Character.isLetter(data.charAt(index))) {
                            index++;
                        }
                        variableName = data.substring(beginChar, index);
                        if (variableName.equals("low") || variableName.equals("high")) {
                            return currentToken = (variableName.equals("low") ? Token.LOW : Token.HIGH);
                        }
                        if (!(variableName.equals("x") || variableName.equals("y") || variableName.equals("z"))) {
                            throw new ParsingException("Unknown Variable " + variableName + generateErrorString(beginChar - 1, index));
                        }
                        return currentToken = Token.VARIABLE;
                    } else {
                        throw new UnknownSymbol("Unknown Symbol: " + "\nAt position: " + String.valueOf(index) + generateErrorString(index - 1, index));
                    }
                }
        }
    }

    private TripleExpression unaryOperation() throws ParsingException, ValueException {
        nextToken();
        TripleExpression returned;
        switch (currentToken) {
            case CONST:
                returned = new CheckedConst(value);
                nextToken();
                return returned;
            case VARIABLE:
                returned = new Variable(variableName);
                nextToken();
                return returned;
            case NEGATE:
                return new CheckedNegate(unaryOperation());
            case OPEN_B:
                int position = index;
                returned = addSubstract();
                if (currentToken != Token.CLOSE_B) {
                    throw new UncorrectPSP("Where is close brakets? \nPosition: " + String.valueOf(position) + String.valueOf(index) + generateErrorString(position, index));
                }
                nextToken();
                return returned;
            case HIGH:
                return new CheckedHigh(unaryOperation());
            case LOW:
                return new CheckedLow(unaryOperation());
            default:
                throw new UnknownSymbol("Unknown Symbol: " + String.valueOf(index) + "\nAt position: " + String.valueOf(index) + generateErrorString(index, index));
        }
    }

    private TripleExpression multiplyDivide() throws ParsingException, ValueException {
        TripleExpression returned = unaryOperation();
        while (true) {
            switch (currentToken) {
                case MULTI:
                    returned = new CheckedMultiply(returned, unaryOperation());
                    break;
                case DIV:
                    returned = new CheckedDivide(returned, unaryOperation());
                    break;
                default:
                    return returned;
            }
        }
    }

    private TripleExpression addSubstract() throws ParsingException, ValueException {
        TripleExpression returned = multiplyDivide();
        while (true) {
            switch (currentToken) {
                case ADD:
                    returned = new CheckedAdd(returned, multiplyDivide());
                    break;
                case SUB:
                    returned = new CheckedSubtract(returned, multiplyDivide());
                    break;
                default:
                    return returned;
            }
        }
    }

    public TripleExpression parse(String s) throws ParsingException, ValueException {
        index = 0;
        data = s;
        balance = 0;
        currentToken = Token.ERROR;
        return addSubstract();
    }
}
