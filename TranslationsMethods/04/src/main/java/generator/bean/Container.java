package generator.bean;

import java.util.ArrayList;
import java.util.Collection;

public class Container extends Mono {
    private TokenOrLexemName main;
    private final ArrayList<Token> tokens = new ArrayList<>();
    private final ArrayList<Lexem> lexems = new ArrayList<>();
    private final ArrayList<String> globals = new ArrayList<>();

    public void addToken(Token token) {
        tokens.add(token);
    }

    public void addLexems(Lexem lexem) {
        lexems.add(lexem);
    }

    public void addAllGlobal(ArrayList<String> globals) {
        this.globals.addAll(globals);
    }

    public Collection<Token> getTokens() {
        return tokens;
    }

    public ArrayList<Lexem> getLexems() {
        return lexems;
    }

    public void setMain(TokenOrLexemName main) {
        this.main = main;
    }

    public TokenOrLexemName getMain() {
        return main;
    }

    public ArrayList<String> getGlobals() {
        return globals;
    }

    public Token findToken(String s) {
        for (Token token : tokens) {
            if (s.equals(token.getName())) {
                return token;
            }
        }
        return null;
    }

    public Lexem findLexem(String s) {
        for (Lexem lexem : lexems) {
            if (s.equals(lexem.getName())) {
                return lexem;
            }
        }
        return null;
    }
}
