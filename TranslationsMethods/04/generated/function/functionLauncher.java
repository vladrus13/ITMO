package function;

import function.beans.*;

import java.util.Scanner;

public class functionLauncher {
    /**
     * Return parsed node
     * @param s parsed string
     * @return {@link functionNode} - node
     * @throws ParseException if we have parsing problem like unsupported character, unexpected token, etc.
     */
    public static functionNode parse(String s) throws function.exception.ParseException {
        functionLexer lexer = new functionLexer(s);
        lexer.nextToken();
        return new functionFunction(lexer, null);
    }

    public static void main(String[] args) throws function.exception.ParseException {
        Scanner scanner = new Scanner(System.in);
        try {
            parse(scanner.nextLine());
        } catch (function.exception.ParseException e) {
            e.printStackTrace();
        }
    }
}