package generator;

import generator.bean.Container;
import generator.bean.LexemFunction;
import generator.bean.TokenOrLexemName;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Utils {
    private static PrintStream out;

    public static void writeln(String s) {
        out.println(s);
    }

    public static void writelnFix(String s) {
        out.println("-".repeat(100));
        out.println(s);
    }

    public static void setOut(PrintStream out1) {
        out = out1;
    }

    private static final Function<String, String> capitalize = element ->
            element.substring(0, 1).toUpperCase() + element.substring(1).toLowerCase();

    public static String capitalization(String s) {
        return Arrays.stream(s.split("_")).map(capitalize).collect(Collectors.joining());
    }

    public static boolean isLexem(String s) {
        return s.matches("[a-z][a-zA-Z_0-9]*");
    }

    public static boolean isToken(String s) {
        return s.matches("[A-Z][a-zA-Z_0-9]*");
    }

    public static final Function<ArrayList<TokenOrLexemName>, String> arrayNameToString = element ->
            element.stream().map(TokenOrLexemName::getName).collect(Collectors.joining(" "));

    public static final Function<ArrayList<ArrayList<TokenOrLexemName>>, String> arrayArrayNameToString = element ->
            element.stream().map(arrayNameToString).collect(Collectors.joining(" | "));

    public static final Function<ArrayList<LexemFunction>, String> arrayLexemFunctionToString = element ->
            element.stream().map(LexemFunction::toString).collect(Collectors.joining());

    public static final Function<ArrayList<ArrayList<LexemFunction>>, String> arrayArrayLexemFunctionToString = element ->
            element.stream().map(function -> "[" + arrayLexemFunctionToString.apply(function) + "]").collect(Collectors.joining());

    public static String nextRandomLexem(Container container) {
        for (int i = 0; i < 1000000; i++) {
            if (container.findLexem("t_" + i) == null) {
                return "t_" + i;
            }
        }
        throw new IllegalStateException("Too many lexems");
    }
}
