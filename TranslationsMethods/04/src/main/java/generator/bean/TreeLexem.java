package generator.bean;

import java.util.ArrayList;

public class TreeLexem {
    private final ArrayList<TokenOrLexemName> rule;
    private final ArrayList<LexemFunction> function;

    public TreeLexem(ArrayList<TokenOrLexemName> rule, ArrayList<LexemFunction> function) {
        this.rule = rule;
        this.function = function;
    }

    public ArrayList<TokenOrLexemName> getRule() {
        return rule;
    }

    public ArrayList<LexemFunction> getFunction() {
        return function;
    }
}
