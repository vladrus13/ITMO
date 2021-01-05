import bean.Flags;
import exception.FlagsException;
import generator.Generator;
import generator.Utils;

import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

public class Launcher {

    public static Flags parseAttributes(String[] args) throws FlagsException {
        Flags flags = new Flags();
        if (Arrays.stream(args).anyMatch(element -> element == null || Objects.equals(element, ""))) {
            throw new FlagsException("Are you jokes on me? Please, don't send me null or empty flags");
        }
        try {
            int it = 0;
            while (it < args.length) {
                switch (args[it]) {
                    case "-lazy" -> flags.setLazy(true);
                    case "-heavy" -> flags.setHeavy(true);
                    case "-input" -> {
                        it++;
                        flags.setInput(args[it]);
                    }
                    case "-output" -> {
                        it++;
                        flags.setOutput(args[it]);
                    }
                    case "-name" -> {
                        it++;
                        flags.setName(args[it]);
                    }
                    case "-small" -> flags.setSmall();
                    case "-useless" -> flags.setUseless();
                    case "-repeater" -> flags.setRepeater();
                    default -> throw new FlagsException("Unexpected flag: " + args[it]);
                }
                it++;
            }
        } catch (Exception e) {
            throw new FlagsException("Throwing exception while get flags", e);
        }
        if (flags.getInput() == null || flags.getOutput() == null || flags.getName() == null) {
            throw new FlagsException("Flags doesn't contain input or (and) output or (and) name information");
        }
        return flags;
    }

    public static String help() {
        return "\tHelp: Flags:" +
                "\n\t\t-lazy - not checking for left-recursion and etc." +
                "\n\t\t-heavy - try to fix this errors." +
                "\n\t\t-input - input file." +
                "\n\t\t-output - output directory" +
                "\n\t\t-small - take long rules and try to smelliest it" +
                "\n\t\t-useless - delete useless rules" +
                "\n\t\t-repeater - delete repeat rules";
    }

    public static void main(String[] args) throws IOException, FlagsException {
        Utils.setOut(System.out);
        Utils.writeln(help());
        Utils.writeln("\t--- Read flags...");
        Flags flags;
        flags = parseAttributes(args);
        Utils.writeln("\tFlags:");
        Utils.writeln(flags.toString());
        Generator generator = new Generator(flags);
        generator.generate();
    }
}
