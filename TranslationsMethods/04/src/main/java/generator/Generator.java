package generator;

import bean.Flags;
import generator.bean.*;
import generator.problem.RulesFixer;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.apache.commons.text.StringEscapeUtils;
import ru.parser.ParserLexer;
import ru.parser.ParserParser;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Generator {

    private final Path resourcesPath = Path.of("resources");
    private final Path enumPath = resourcesPath.resolve("submethods").resolve("enum.txt");
    private final Path lexerPath = resourcesPath.resolve("submethods").resolve("lexer.txt");
    private final Path exceptionsPath = resourcesPath.resolve("submethods").resolve("exceptions");
    private final Path pathToFiles;
    private final String lowerName;
    private final Flags flags;

    // deadly memes with streams
    private static final Function<ArrayList<Regular>, String> arrayMonoToString = element ->
            element.stream()
                    .map(mono -> StringEscapeUtils.escapeJava(mono.isStrong() ? mono.getRegular() : Pattern.quote(mono.getRegular())))
                    .collect(Collectors.joining());

    private static final Function<ArrayList<ArrayList<Regular>>, String> arrayArrayMonoToString = element ->
            element.stream()
                    .map(array -> "(" + arrayMonoToString.apply(array) + ")")
                    .collect(Collectors.joining("|"));

    public Generator(Flags flags) {
        this.flags = flags;
        pathToFiles = Path.of(flags.getOutput()).resolve(flags.getName().toLowerCase());
        lowerName = flags.getName().toLowerCase();
    }

    public String formatterType(String s) {
        return s.replace("%type", lowerName);
    }

    public void generateExceptions() throws IOException {
        BufferedReader bufferedReader;
        BufferedWriter bufferedWriter;
        String program;
        Files.createDirectories(pathToFiles.resolve("exceptions"));
        for (String exception : List.of("ParseException", "UnexpectedTokenException", "UnsupportedCharacterException")) {
            bufferedReader = Files.newBufferedReader(exceptionsPath.resolve(exception + ".txt"));
            program = bufferedReader.lines().collect(Collectors.joining(System.lineSeparator()));
            bufferedWriter = Files.newBufferedWriter(
                    pathToFiles.resolve("exceptions").resolve(exception + ".java"),
                    StandardCharsets.UTF_8, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.CREATE);
            bufferedWriter.write(formatterType(program));
            bufferedWriter.close();
            bufferedReader.close();
        }
    }

    public void generateEnum(Container container) throws IOException {
        BufferedReader bufferedReader = Files.newBufferedReader(enumPath, StandardCharsets.UTF_8);
        BufferedWriter bufferedWriter = Files.newBufferedWriter(
                pathToFiles.resolve(lowerName + "Enum.java"), StandardCharsets.UTF_8, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.CREATE);
        String program = bufferedReader.lines().collect(Collectors.joining(System.lineSeparator()));
        String enums = container.getTokens()
                .stream()
                .map(token -> token.getName() + "(\"" + arrayArrayMonoToString.apply(token.getContain()) + ".*\", " + token.isSkipable() + ")")
                .collect(Collectors.joining(", "));
        bufferedWriter.write(String.format(formatterType(program), enums));
        bufferedReader.close();
        bufferedWriter.close();
        bufferedReader = Files.newBufferedReader(resourcesPath.resolve("submethods").resolve("token.txt"), StandardCharsets.UTF_8);
        bufferedWriter = Files.newBufferedWriter(
                pathToFiles.resolve(lowerName + "Token.java"), StandardCharsets.UTF_8, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.CREATE);
        bufferedWriter.write(formatterType(bufferedReader.lines().collect(Collectors.joining(System.lineSeparator()))));
        bufferedWriter.close();
    }

    public void generateLexer() throws IOException {
        BufferedReader bufferedReader = Files.newBufferedReader(lexerPath, StandardCharsets.UTF_8);
        BufferedWriter bufferedWriter = Files.newBufferedWriter(
                pathToFiles.resolve(lowerName + "Lexer.java"), StandardCharsets.UTF_8, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.CREATE);
        String program = bufferedReader.lines().collect(Collectors.joining(System.lineSeparator()));
        bufferedWriter.write(formatterType(program));
        bufferedReader.close();
        bufferedWriter.close();
    }


    public void generatePreBeans(Container container) throws IOException {
        BufferedReader bufferedReader;
        bufferedReader = Files.newBufferedReader(resourcesPath.resolve("submethods").resolve("beans").resolve("node.txt"), StandardCharsets.UTF_8);
        Path beans = pathToFiles.resolve("beans");
        Files.createDirectories(beans);
        String globals = String.join("\n\t", container.getGlobals());
        BufferedWriter bufferedWriter = Files.newBufferedWriter(
                beans.resolve(lowerName + "Node.java"), StandardCharsets.UTF_8, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.CREATE);
        bufferedWriter.write(
                String.format(formatterType(bufferedReader.lines()
                        .collect(Collectors.joining(System.lineSeparator()))), globals));
        bufferedWriter.close();
        bufferedReader = Files.newBufferedReader(resourcesPath.resolve("submethods").resolve("beans").resolve("terminalNode.txt"), StandardCharsets.UTF_8);
        bufferedWriter = Files.newBufferedWriter(
                beans.resolve(lowerName + "Terminal.java"), StandardCharsets.UTF_8, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.CREATE);
        bufferedWriter.write(
                formatterType(
                        bufferedReader.lines()
                                .collect(Collectors.joining(System.lineSeparator()))));
        bufferedWriter.close();
    }

    public StringBuilder getCase(ArrayList<TokenOrLexemName> mono, ArrayList<LexemFunction> functions, Container container) {
        StringBuilder stringBuilder = new StringBuilder();
        if (mono.size() == 0) return stringBuilder;
        TokenOrLexemName firstName = mono.get(0);
        String caseArguments;
        if (Character.isUpperCase(firstName.getName().charAt(0))) {
            caseArguments = firstName.getName();
        } else {
            caseArguments = container.findLexem(firstName.getName()).getFirst(container).stream().map(Token::getName).collect(Collectors.joining(", "));
        }
        stringBuilder.append("\t\t\t\tcase ").append(caseArguments).append(":").append(System.lineSeparator());
        for (TokenOrLexemName name : mono) {
            String added;
            if (Character.isUpperCase(name.getName().charAt(0))) {
                added = "Terminal(" + lowerName + "Enum." + name.getName() + ", lexer)";
            } else {
                added = Utils.capitalization(name.getName()) + "(lexer, this)";
            }
            stringBuilder.append("\t\t\t\t\tchildren.add(new ").append(lowerName).append(added).append(");").append(System.lineSeparator());
        }
        stringBuilder.append(functions.stream().map(element -> "\t\t\t\t\t" + formatterType(element.getCode()) + ";\n").collect(Collectors.joining()));
        stringBuilder.append("\t\t\t\t\tbreak;");
        return stringBuilder;
    }

    public String getSwitch(Lexem lexem, Container container) {
        StringBuilder buffer = new StringBuilder();
        for (int i = 0; i < lexem.getCombine().size(); i++) {
            buffer.append(getCase(lexem.getCombine().get(i), lexem.getFunctions().get(i), container)).append(System.lineSeparator());
        }
        buffer.deleteCharAt(buffer.length() - 1);
        return buffer.toString();
    }

    public String generateFromNull(Lexem lexem) {
        int i;
        for (i = 0; i < lexem.getCombine().size(); i++) {
            if (lexem.getCombine().get(i).isEmpty()) {
                break;
            }
        }
        return lexem.getFunctions().get(i).stream().map(element -> "\t\t\t\t\t" + formatterType(element.getCode()) + ";\n").collect(Collectors.joining());
    }

    public void generateBeans(Container container) throws IOException {
        generatePreBeans(container);
        Path beans = pathToFiles.resolve("beans");
        BufferedReader bufferedReader = Files.newBufferedReader(resourcesPath.resolve("submethods").resolve("beans").resolve("class.txt"), StandardCharsets.UTF_8);
        String code = bufferedReader.lines().collect(Collectors.joining(System.lineSeparator()));
        BufferedWriter bufferedWriter;
        for (Lexem lexem : container.getLexems()) {
            String capitalizationName = Utils.capitalization(lexem.getName());
            Utils.writeln("\t\t" + capitalizationName + " class");
            bufferedWriter = Files.newBufferedWriter(
                    beans.resolve(lowerName + capitalizationName + ".java"),
                    StandardCharsets.UTF_8, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.CREATE);
            String className = lowerName + capitalizationName;
            String defaultSwitch = lexem.getCombine().stream().noneMatch(mono -> mono.size() == 0) ?
                    formatterType("\t\t\t\t\tthrow new %type.exception.UnexpectedTokenException(\"Unexpected token exception. Actual: \" + token.getType());") :
                    generateFromNull(lexem);
            bufferedWriter.write(
                    String.format(
                            formatterType(code), className, className, getSwitch(lexem, container), defaultSwitch));
            bufferedWriter.close();
        }
    }

    public void generateLauncher(Container container) throws IOException {
        BufferedReader bufferedReader = Files.newBufferedReader(resourcesPath.resolve("submethods").resolve("launcher.txt"), StandardCharsets.UTF_8);
        String code = bufferedReader.lines().collect(Collectors.joining(System.lineSeparator()));
        BufferedWriter bufferedWriter = Files.newBufferedWriter(
                pathToFiles.resolve(lowerName + "Launcher.java"),
                StandardCharsets.UTF_8, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.CREATE);
        bufferedWriter.write(String.format(formatterType(code), Utils.capitalization(container.getMain().getName())));
        bufferedWriter.close();
    }

    public void generate() throws IOException {
        Utils.writeln("\t--- Start lexer...");
        ParserLexer lexer = new ParserLexer(CharStreams.fromPath(Path.of(flags.getInput()), StandardCharsets.UTF_8));
        CommonTokenStream tokenStream = new CommonTokenStream(lexer);
        ParserParser parser = new ParserParser(tokenStream);
        GeneratorVisitor generatorVisitor = new GeneratorVisitor();
        Utils.writeln("\t--- Start visitor...");
        Container container = (Container) generatorVisitor.visit(parser.file());
        if (!flags.isLazy()) {
            Utils.writeln("Program not lazy, so...");
            Utils.writeln("\t--- Check epsilon rules and needed rules...");
            if (RulesFixer.fix(container)) {
                Utils.writeln("\t--- Read trace and fix problems");
                return;
            }
        }
        Files.createDirectories(pathToFiles);
        Utils.writeln("\t--- Generate enum...");
        generateEnum(container);
        Utils.writeln("\t--- Generate exceptions...");
        generateExceptions();
        Utils.writeln("\t--- Generate lexer...");
        generateLexer();
        Utils.writeln("\t--- Generate beans...");
        generateBeans(container);
        Utils.writeln("\t--- Generate launcher...");
        generateLauncher(container);
    }
}
