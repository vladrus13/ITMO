package cbs;

import cbs.beans.*;

import java.util.Scanner;

public class cbsLauncher {
    /**
     * Return parsed node
     * @param s parsed string
     * @return {@link cbsNode} - node
     * @throws ParseException if we have parsing problem like unsupported character, unexpected token, etc.
     */
    public static cbsNode parse(String s) throws cbs.exception.ParseException {
        cbsLexer lexer = new cbsLexer(s);
        lexer.nextToken();
        return new cbsS(lexer);
    }

    public static void main(String[] args) throws cbs.exception.ParseException {
        Scanner scanner = new Scanner(System.in);
        try {
            parse(scanner.nextLine());
        } catch (cbs.exception.ParseException e) {
            e.printStackTrace();
        }
    }
}