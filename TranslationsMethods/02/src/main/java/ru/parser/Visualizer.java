package ru.parser;

import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.Graph;
import ru.parser.exception.ParseException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Random;
import java.util.Scanner;

import static guru.nidi.graphviz.model.Factory.*;

public class Visualizer {

    public static void draw(String s, String name) throws ParseException, IOException {
        Graph g = mutGraph("graph").add(Parser.parse(s).getNode()).toImmutable();
        Graphviz.fromGraph(g).width(1000).render(Format.PNG).toFile(new File(String.valueOf(Path.of("resources").resolve(name))));
    }

    public static void draw_01() throws IOException, ParseException {
        draw("void test();", "01");
    }
    public static void draw_02() throws IOException, ParseException {
        draw("int fib(int n);", "02");
    }

    public static void draw_03() throws IOException, ParseException {
        draw("string to_string(int calculated);", "03");
    }

    public static void draw_04() throws IOException, ParseException {
        draw("very_hard_type_1 very_hard_name_1(very_hard_type_2 vary_hard_name_2);", "04");
    }

    public static void draw_05() throws IOException, ParseException {
        draw("a b(a b, a **b, a ********************************b);", "05");
    }

    public static void draw_06() throws IOException, ParseException {
        draw("\t \ta\t \t*\t \t*\t \t\t \t\t \ta\t \t\t \t(\t \t\t \ta\t \t\t \t*\t \ta\t \t,\t \t\t \ta\t \t*\t \t\t \ta\t \t)\t \t;", "06");
    }

    static final Random random = new Random();
    private static final String alphabet = "abcdefghijklmnopqrstuvwxyz";
    private static final String bigAlphabet = alphabet + alphabet.toUpperCase();
    private static final String all = bigAlphabet + "0123456789" + "_";

    private static char generateAlphabetChar() {
        return bigAlphabet.charAt(random.nextInt(bigAlphabet.length()));
    }

    private static char generateRandomSymbol() {
        return all.charAt(random.nextInt(all.length()));
    }

    private static String generateName() {
        StringBuilder s = new StringBuilder();
        int size = random.nextInt(10) + 1;
        s.append(generateAlphabetChar());
        for (int i = 1; i < size; i++) {
            s.append(generateRandomSymbol());
        }
        return s.toString();
    }

    public static void draw_07_random() throws IOException, ParseException {
        StringBuilder s = new StringBuilder();
        s.append(generateName());
        s.append("*".repeat(random.nextInt(5)));
        s.append(" ");
        s.append(generateName());
        s.append("(");
        int size = random.nextInt(100);
        if (size != 0) {
            while (size > 1) {
                s.append(generateName());
                s.append(' ');
                s.append("*".repeat(10));
                s.append(generateName());
                s.append(", ");
                size--;
            }
            s.append(generateName());
            s.append(' ');
            s.append("*".repeat(10));
            s.append(generateName());
        }
        s.append(");");
        draw(s.toString(), "07");
    }

    public static void draw_all() {
        try {
            draw_01();
            draw_02();
            draw_03();
            draw_04();
            draw_05();
            draw_06();
            draw_07_random();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        if (args.length > 0) {
            draw_all();
        } else {
            Scanner scanner = new Scanner(System.in);
            String line = scanner.nextLine();
            try {
                Graph g = mutGraph("graph").add(Parser.parse(line).getNode()).toImmutable();
                Graphviz.fromGraph(g).width(1000).render(Format.PNG).toFile(new File(String.valueOf(Path.of("resources").resolve("example.png"))));
            } catch (ParseException | IOException e) {
                e.printStackTrace();
            }
        }
    }
}
