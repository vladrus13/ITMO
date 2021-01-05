package calculator;

import calculator.beans.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.stream.Collectors;

public class calculatorLauncher {
    /**
     * Return parsed node
     * @param s parsed string
     * @return {@link calculatorNode} - node
     * @throws ParseException if we have parsing problem like unsupported character, unexpected token, etc.
     */
    public static calculatorNode parse(String s) throws calculator.exception.ParseException {
        calculatorLexer lexer = new calculatorLexer(s);
        lexer.nextToken();
        return new calculatorAdded(lexer, null);
    }

    public static void main(String[] args) throws calculator.exception.ParseException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.println(parse(bufferedReader.lines().collect(Collectors.joining())).val);
        } catch (calculator.exception.ParseException e) {
            e.printStackTrace();
        }
    }
}