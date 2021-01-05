package ru.parser;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import ru.parser.antlr.ProgramLexer;
import ru.parser.antlr.ProgramParser;
import ru.parser.visitor.ProgramVisitor;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Objects;

public class Parser {

    public static void makeCpp(String path, String name, String end) throws IOException {
        ProgramLexer lexer = new ProgramLexer(
                CharStreams.fromPath(
                        Path.of(path).resolve(name + "." + end),
                        StandardCharsets.UTF_8));
        BufferedWriter bufferedWriter = Files.newBufferedWriter(
                Path.of(path).resolve(name + "_ideal." + end),
                StandardCharsets.UTF_8, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.CREATE
        );
        CommonTokenStream tokenStream = new CommonTokenStream(lexer);
        ProgramParser parser = new ProgramParser(tokenStream);
        ProgramVisitor programVisitor = new ProgramVisitor();
        StringBuilder result = programVisitor.visit(parser.program());
        bufferedWriter.write(result.toString());
        bufferedWriter.close();
    }

    public static void main(String[] args) throws IOException {
        String path = null;
        String name = null;
        String end = null;
        if (args.length == 1 && Objects.equals(args[0], "-file")) {
            path = "resources/simple";
            name = "hello_world";
            end = "cpp";
        }
        if (args.length == 4 &&
                Objects.equals(args[2], "-file") && Objects.equals(args[0], "-directory")) {
            path = args[1];
            name = args[3];
            end = "cpp";
        }
        if (args.length == 1 && Objects.equals(args[0], "-all")) {
            Path start = Path.of("resources");
            Files.walkFileTree(start, new SimpleFileVisitor<>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attributes) throws IOException {
                    String fileName = file.getFileName().toString();
                    if (!fileName.endsWith(".cpp") && !fileName.endsWith(".h")) {
                        return FileVisitResult.CONTINUE;
                    }
                    if (!fileName.endsWith("_ideal.cpp") && !fileName.endsWith("_ideal.h")) {
                        System.out.print("Visit: " + file);
                        String end = fileName.endsWith("p") ? "cpp" : "h";
                        makeCpp(file.getParent().toString(),
                                fileName.substring(0, fileName.length() - 1 - end.length()), end);
                        System.out.println(". Done");
                    }
                    return FileVisitResult.CONTINUE;
                }
            });
            return;
        }
        if (name == null || path == null) {
            throw new IllegalArgumentException("Illegal arguments");
        }
        makeCpp(path, name, end);
    }
}
