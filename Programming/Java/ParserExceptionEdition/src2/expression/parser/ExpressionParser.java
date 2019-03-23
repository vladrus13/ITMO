package expression.parser;

import expression.*;
import expression.exceptions.*;

import java.util.EnumSet;
import java.util.Set;

public class ExpressionParser implements Parser {
    private String data;
    private String variableName;
    private int index = 0;
    private int value = -0;
    private int balance = 0;

    private enum Token {
        ADD, CLOSE_B, OPEN_B, DIV, MULTI, NEGATE, SUB, ERROR, CONST, VARIABLE, END, MAX, MIN, ABS, SQRT, LOG2, POW2
    }

    private Set<Token> operations = EnumSet.of(Token.ADD, Token.SUB, Token.DIV, Token.MULTI, Token.NEGATE);

    private Token currentToken = Token.ERROR;

    private void skipSpace() {
        while (index < data.length() && Character.isWhitespace(data.charAt(index))) {
            index++;
        }
    }

    private void checkOperator() throws MissingOperatorException {
        if (operations.contains(currentToken) || currentToken == Token.OPEN_B || currentToken == Token.ERROR) {
            if (index == 0) {
                throw new MissingOperatorException("Begin from Operator... \nError at index " + String.valueOf(index - 1));
            } else {

                throw new MissingOperatorException("Second Operator... \nError at index " + String.valueOf(index - 1));
            }
        }
    }

    private void checkOperation() throws MissingOperatorException {
        if (currentToken == Token.CLOSE_B || currentToken == Token.VARIABLE || currentToken == Token.CONST) {
            throw new MissingOperatorException("Second Value... \nError at index " + String.valueOf(index));
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
                        throw new MissingOperatorException("No value after minus... \nError at index" + String.valueOf(index));
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
                            throw new ConstException();
                        }
                        return currentToken = Token.CONST;
                    }
                    return currentToken = Token.NEGATE;
                }
            case '/':
                checkOperator();
                if (index >= data.length()) {
                    throw new MissingOperatorException("No value after divide... \nError at index" + String.valueOf(index));
                }
                return currentToken = Token.DIV;
            case '*':
                checkOperator();
                if (index >= data.length()) {
                    throw new MissingOperatorException("No value after multiply...\nError at index" + String.valueOf(index));
                }
                return currentToken = Token.MULTI;
            case '(':
                checkOperation();
                balance++;
                return currentToken = Token.OPEN_B;
            case ')':
                if (operations.contains(currentToken) || currentToken == Token.OPEN_B) {
                    throw new MissingOperatorException("Operator before bracket...\nError at index " + String.valueOf(index));
                }
                balance--;
                if (balance < 0) {
                    throw new BraketsBalanceException("Error balance brackets \nError at index " + String.valueOf(index));
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
                        throw new ConstException();
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
                        if (variableName.equals("max") || variableName.equals("min")) {
                            checkOperator();
                            if (!Character.isWhitespace(data.charAt(beginChar - 1)) && !Character.isWhitespace(data.charAt(index))) {
                                throw new MissingOperatorException("max/min before&after no space... \nError at index" + String.valueOf(index));
                            }
                            if (index >= data.length()) {
                                throw new MissingOperatorException("No value after max/min... \nError at index" + String.valueOf(index));
                            }
                            return currentToken = (variableName.equals("max") ? Token.MAX : Token.MIN);
                        }
                        if (variableName.equals("sqrt") || variableName.equals("abs")) {
                            return currentToken = (variableName.equals("sqrt") ? Token.SQRT : Token.ABS);
                        }
                        if (variableName.equals("log") && data.charAt(index) == '2') {
                            index++;
                            if (Character.isLetter(data.charAt(index))) {
                                throw new MissingOperatorException("Letter after log... \nError at index" + String.valueOf(index));
                            }
                            return currentToken = Token.LOG2;
                        }
                        if (variableName.equals("pow") && data.charAt(index) == '2') {
                            index++;
                            return currentToken = Token.POW2;
                        }
                        if (!(variableName.equals("x") || variableName.equals("y") || variableName.equals("z"))) {
                            throw new ParsingException("Unknown Variable " + variableName);
                        }
                        return currentToken = Token.VARIABLE;
                    } else {
                        throw new UnknownSymbol("Unknown Symbol: " + "\nAt position: " + String.valueOf(index));
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
            case SQRT:
                return new CheckedSqrt(unaryOperation());
            case ABS:
                return new CheckedAbs(unaryOperation());
            case OPEN_B:
                int position = index;
                returned = maxMin();
                if (currentToken != Token.CLOSE_B) {
                    throw new UncorrectPSP("Where is brakets? \nPosition: " + String.valueOf(position) + String.valueOf(index));
                }
                nextToken();
                return returned;
            case LOG2:
                return new CheckedLog(new CheckedConst(2), unaryOperation());
            case POW2:
                return new CheckedPower(new CheckedConst(2), unaryOperation());
            default:
                throw new UnknownSymbol("Unknown Symbol: " + String.valueOf(index) + "\nAt position: " + String.valueOf(index));
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

    private TripleExpression maxMin() throws ParsingException, ValueException {
        TripleExpression returned = addSubstract();
        while (true) {
            switch (currentToken) {
                case MAX:
                    returned = new CheckedMax(returned, addSubstract());
                    break;
                case MIN:
                    returned = new CheckedMin(returned, addSubstract());
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
        return maxMin();
    }
}
