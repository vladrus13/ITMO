package program;

import program.beans.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.stream.Collectors;

public class programLauncher {
    /**
     * Return parsed node
     * @param s parsed string
     * @return {@link programNode} - node
     * @throws ParseException if we have parsing problem like unsupported character, unexpected token, etc.
     */
    public static programNode parse(String s) throws program.exception.ParseException {
        programLexer lexer = new programLexer(s);
        lexer.nextToken();
        return new programProgram(lexer, null);
    }

    public static void main(String[] args) throws program.exception.ParseException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.println(parse(bufferedReader.lines().collect(Collectors.joining())).s);
        } catch (program.exception.ParseException e) {
            e.printStackTrace();
        }
    }
}