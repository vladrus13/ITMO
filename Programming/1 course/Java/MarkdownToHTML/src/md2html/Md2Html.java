package md2html;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static md2html.Md2HtmlSource.END;

public class Md2Html {
    private static Md2HtmlSource source;
    private static Map<String, String> Tags = new HashMap<>();
    private static Set<String> SpecialSymbols = new HashSet<>();
    private static Map<String, String> HTMLSpecialSymbols = new HashMap<>();
    private static boolean true_close_recursion = false;

    static {
        Tags.put("*", "em");
        Tags.put("**", "strong");
        Tags.put("_", "em");
        Tags.put("__", "strong");
        Tags.put("--", "s");
        Tags.put("`", "code");
        Tags.put("~", "mark");
        Tags.put("!", "img alt='");
        Tags.put("[", "a href='");
        Tags.put("++", "u");
        HTMLSpecialSymbols.put("<", "&lt;");
        HTMLSpecialSymbols.put(">", "&gt;");
        HTMLSpecialSymbols.put("&", "&amp;");
        SpecialSymbols.add("*");
        SpecialSymbols.add("-");
        SpecialSymbols.add("_");
        SpecialSymbols.add("`");
        SpecialSymbols.add("~");
        SpecialSymbols.add("\\");
        SpecialSymbols.add("!");
        SpecialSymbols.add("[");
        SpecialSymbols.add("]");
        SpecialSymbols.add("+");
    }

    private static boolean test(final char c) {
        return source.getChar() == c;
    }

    public static void main(String[] arg) throws Md2HtmlException {
        try {
            source = new FileMd2HtmlSource(arg[0]);
        } catch (Md2HtmlException e) {
            throw source.error("Error opened file. Error:\n %s", e.getMessage());
        }
        FileWriter out;
        try {
            out = new FileWriter(new File(arg[1]), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw source.error("Error opened file. Error:\n %s", e.getMessage());
        }
        String ret;
        try {
            ret = parse().toString();
        } catch (Md2HtmlException e) {
            throw source.error("Parsing error. Error:\n %s", e.getMessage());
        }
        try {
            out.write(ret);
            out.close();
        } catch (IOException e) {
            throw source.error("Error rewriting file. Error:\n %s", e.getMessage());
        }
        source.close();
    }

    private static boolean testNext(final char c) throws Md2HtmlException {
        if (source.getChar() == c) {
            source.nextChar();
            return true;
        } else {
            return false;
        }
    }

    private static StringBuilder parse() throws Md2HtmlException {
        StringBuilder returned = new StringBuilder();
        StringBuilder prefix = new StringBuilder();
        source.nextChar();
        int level;
        while (!test(END)) {
            while (isNewLine()) {
                source.nextChar();
            }
            // check for head
            level = 0;
            // HeadHunter
            while (testNext('#')) {
                level++;
            }
            for (int i = 0; i < level; i++) {
                prefix.append('#');
            }
            if (!test(' ')) {
                level = 0;
            } else {
                prefix = new StringBuilder();
                if (level > 0) {
                    source.nextChar();
                }
            }
            StringBuilder in = parseRecursiveValue('\1', 2);
            while (in.length() > 0 && in.charAt(in.length() - 1) == '\n') {
                in.deleteCharAt(in.length() - 1);
            }
            if (in.length() > 0) {
                if (level == 0) {
                    in.insert(0, prefix);
                    prefix = new StringBuilder();
                    in.insert(0, "<p>");
                    in.append("</p>\n");
                    returned.append(in);
                } else {
                    in.insert(0, prefix);
                    prefix = new StringBuilder();
                    in.insert(0, String.format("<h%s>", String.valueOf(level)));
                    in.append(String.format("</h%s>\n", String.valueOf(level)));
                    returned.append(in);
                }
            }
        }
        return returned;
    }

    private static boolean isNewLine() throws Md2HtmlException {
        if (source.getChar() == '\r') {
            source.nextChar();
            return true;
        }
        return source.getChar() == '\n';
    }

    // you can parse paragraph at this
    // what about get paragraph like as marking?
    private static StringBuilder parseRecursiveValue(char recur, int type) throws Md2HtmlException {
        StringBuilder returned = new StringBuilder();
        String temp;
        while (true) {
            if (test(END) || (type == 3 && test(']'))) {
                return returned;
            }
            if (SpecialSymbols.contains("" + source.getChar())) {
                temp = "" + source.getChar();
                source.nextChar();
                if (SpecialSymbols.contains("" + source.getChar())) {
                    temp += source.getChar();
                    if (temp.charAt(0) == source.getChar()) {
                        source.nextChar();
                    }
                }
                if (temp.length() == 1) {
                    if (recur == temp.charAt(0) && type == 1) {
                        // close
                        true_close_recursion = true;
                        return returned;
                    } else {
                        // open new rec
                        if (temp.charAt(0) == '[') {
                            // its link
                            StringBuilder text = parseRecursiveValue(']', 3);
                            source.nextChar();
                            source.nextChar();
                            StringBuilder link = parseNotRecursiveValue(')');
                            StringBuilder in = new StringBuilder("<" + Tags.get("["));
                            in.append(link);
                            in.append("'>");
                            in.append(text);
                            in.append("</a>");
                            returned.append(in);
                            source.nextChar();
                        } else {
                            StringBuilder in = parseRecursiveValue(temp.charAt(0), 1);
                            if (!true_close_recursion) {
                                in.insert(0, temp.charAt(0));
                                returned.append(in);
                                return returned;
                            } else {
                                // close
                                true_close_recursion = false;
                                in.insert(0, "<" + Tags.get("" + temp.charAt(0)) + ">");
                                in.append(String.format("</%s>", Tags.get("" + temp.charAt(0))));
                                returned.append(in);
                            }
                        }
                    }
                } else {
                    if (type == 2 && temp.charAt(0) == temp.charAt(1) && temp.charAt(1) == recur) {
                        // close
                        true_close_recursion = true;
                        return returned;
                    }
                    if (temp.charAt(0) == '!') {
                        // its image
                        source.nextChar();
                        StringBuilder text = parseNotRecursiveValue(']');
                        source.nextChar();
                        source.nextChar();
                        StringBuilder link = parseNotRecursiveValue(')');
                        StringBuilder in = new StringBuilder("<" + Tags.get("!"));
                        in.append(text);
                        in.append("' src='");
                        in.append(link);
                        in.append("'>");
                        returned.append(in);
                        source.nextChar();
                    } else {
                        if (temp.charAt(0) == '\\') {
                            returned.append(temp.charAt(1));
                            source.nextChar();
                        } else {
                            if (temp.charAt(0) == '\'') {
                                StringBuilder in = parseRecursiveValue(temp.charAt(0), 1);
                                if (true_close_recursion) {
                                    // close
                                    true_close_recursion = false;
                                    in.insert(0, "<" + Tags.get("" + temp.charAt(0)) + ">");
                                    in.append(String.format("</%s>", Tags.get("" + temp.charAt(0))));
                                    returned.append(in);
                                } else {
                                    in.insert(0, temp.charAt(0));
                                    returned.append(in);
                                    return returned;
                                }
                            } else {
                                if (temp.charAt(0) != temp.charAt(1)) {
                                    if (recur == temp.charAt(0) && type == 1) {
                                        // close
                                        true_close_recursion = true;
                                        return returned;
                                    }
                                    StringBuilder in = parseRecursiveValue(temp.charAt(0), 1);
                                    if (true_close_recursion) {
                                        true_close_recursion = false;
                                        // close
                                        in.insert(0, "<" + Tags.get("" + temp.charAt(0)) + ">");
                                        in.append(String.format("</%s>", Tags.get("" + temp.charAt(0))));
                                        returned.append(in);
                                    } else {
                                        in.insert(0, "" + temp.charAt(0));
                                        returned.append(in);
                                        return returned;
                                    }
                                } else {
                                    StringBuilder in = parseRecursiveValue(temp.charAt(0), 2);
                                    if (true_close_recursion) {
                                        // close
                                        true_close_recursion = false;
                                        in.insert(0, "<" + Tags.get("" + temp.charAt(0) + temp.charAt(0)) + ">");
                                        in.append(String.format("</%s>", "" + Tags.get("" + temp.charAt(0) + temp.charAt(0))));
                                        returned.append(in);
                                    } else {
                                        in.insert(0, "" + temp.charAt(0) + temp.charAt(0));
                                        returned.append(in);
                                        return returned;
                                    }
                                }
                            }
                        }
                    }
                }
            } else {
                int cnt = 0;
                while (isNewLine()) {
                    cnt++;
                    source.nextChar();
                }
                if (cnt > 1) {
                    true_close_recursion = false;
                    return returned;
                }
                if (cnt == 1) {
                    returned.append('\n');
                } else {
                    if (HTMLSpecialSymbols.containsKey("" + source.getChar())) {
                        returned.append(HTMLSpecialSymbols.get("" + source.getChar()));
                    } else {
                        returned.append(source.getChar());
                    }
                    source.nextChar();
                }
            }
        }
    }

    private static StringBuilder parseNotRecursiveValue(char end) throws Md2HtmlException {
        StringBuilder returned = new StringBuilder();
        while (true) {
            if (test(END) || test(end)) {
                return returned;
            } else {
                returned.append(source.getChar());
                source.nextChar();
            }
        }
    }
}
