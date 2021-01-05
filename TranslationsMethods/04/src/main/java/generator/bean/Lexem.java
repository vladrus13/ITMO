package generator.bean;

import generator.Utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Lexem extends Mono {
    private final String name;
    private final ArrayList<ArrayList<LexemFunction>> functions;
    private final ArrayList<ArrayList<TokenOrLexemName>> combine;
    private HashSet<Token> first;

    public Lexem(String name, ArrayList<ArrayList<LexemFunction>> functions, ArrayList<ArrayList<TokenOrLexemName>> combine) {
        this.name = name;
        this.functions = functions;
        this.combine = combine;
    }

    private Set<Token> recursion(TokenOrLexemName name, Container container) {
        if (Character.isUpperCase(name.getName().charAt(0))) {
            Token token = container.findToken(name.getName());
            return new HashSet<>(List.of(token));
        } else {
            Lexem lexem = container.findLexem(name.getName());
            return lexem.getFirst(container);
        }
    }

    private void calculateFirst(Container container) {
        first = new HashSet<>();
        for (ArrayList<TokenOrLexemName> names : combine) {
            if (!names.isEmpty()) {
                first.addAll(recursion(names.get(0), container));
            }
        }
    }

    public HashSet<Token> getFirst(Container container) {
        if (first == null) {
            calculateFirst(container);
        }
        return first;
    }

    public String getName() {
        return name;
    }

    public ArrayList<ArrayList<LexemFunction>> getFunctions() {
        return functions;
    }

    public ArrayList<ArrayList<TokenOrLexemName>> getCombine() {
        return combine;
    }

    @Override
    public String toString() {
        return name + " : " + Utils.arrayArrayNameToString.apply(combine) + "{" + Utils.arrayArrayLexemFunctionToString.apply(functions) + "}";
    }
}
