package generator;

import generator.bean.*;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;
import ru.parser.ParserBaseVisitor;
import ru.parser.ParserLexer;
import ru.parser.ParserParser;

import java.util.ArrayList;

@SuppressWarnings("unchecked")
public class GeneratorVisitor extends ParserBaseVisitor<Object> {

    @Override
    public Mono visitFile(ParserParser.FileContext ctx) {
        Container container = new Container();
        for (ParseTree parseTree : ctx.children) {
            if (parseTree instanceof ParserParser.TokenContext) {
                container.addToken((Token) parseTree.accept(this));
                continue;
            }
            if (parseTree instanceof ParserParser.LexemContext) {
                container.addLexems((Lexem) parseTree.accept(this));
                continue;
            }
            if (parseTree instanceof ParserParser.MainContext) {
                container.setMain(new TokenOrLexemName((String) parseTree.accept(this)));
                continue;
            }
            if (parseTree instanceof ParserParser.GlobalContext) {
                container.addAllGlobal((ArrayList<String>) parseTree.accept(this));
                continue;
            }
            throw new IllegalStateException(
                    String.format("Find: %s in FileContext", parseTree.getClass().getName()));
        }
        return container;
    }

    @Override
    public String visitMain(ParserParser.MainContext ctx) {
        return ctx.children.get(1).getText();
    }

    @Override
    public Token visitToken(ParserParser.TokenContext ctx) {
        String name = (String) ctx.children.get(0).accept(this);
        ArrayList<ArrayList<Regular>> matches = (ArrayList<ArrayList<Regular>>) ctx.children.get(2).accept(this);
        return new Token(name, matches, ctx.children.size() == 5);
    }

    @Override
    public Lexem visitLexem(ParserParser.LexemContext ctx) {
        String name = (String) ctx.children.get(0).accept(this);
        ArrayList<TreeLexem> rules = (ArrayList<TreeLexem>) ctx.children.get(2).accept(this);
        ArrayList<ArrayList<LexemFunction>> functions = new ArrayList<>();
        ArrayList<ArrayList<TokenOrLexemName>> matches = new ArrayList<>();
        for (TreeLexem treeLexem : rules) {
            functions.add(treeLexem.getFunction());
            matches.add(treeLexem.getRule());
        }
        return new Lexem(name, functions, matches);
    }

    @Override
    public ArrayList<String> visitGlobal(ParserParser.GlobalContext ctx) {
        ArrayList<String> globals = new ArrayList<>();
        if (ctx.children == null) {
            return globals;
        }
        for (ParseTree parseTree : ctx.children) {
            globals.add((String) parseTree.accept(this));
        }
        return globals;
    }

    @Override
    public ArrayList<LexemFunction> visitFunctions(ParserParser.FunctionsContext ctx) {
        ArrayList<LexemFunction> functions = new ArrayList<>();
        for (int it = 1; it < ctx.children.size(); it += 3) {
            String code = ((String) ctx.children.get(it).accept(this));
            functions.add(new LexemFunction(code.substring(1, code.length() - 1)));
        }
        return functions;
    }

    @Override
    public ArrayList<TreeLexem> visitMatches(ParserParser.MatchesContext ctx) {
        ArrayList<TreeLexem> returned = new ArrayList<>();
        int it = 0;
        while (it < ctx.children.size()) {
            TreeLexem match = (TreeLexem) ctx.children.get(it).accept(this);
            returned.add(match);
            it += 2;
        }
        return returned;
    }

    @Override
    public ArrayList<ArrayList<Regular>> visitRegularas(ParserParser.RegularasContext ctx) {
        ArrayList<ArrayList<Regular>> returned = new ArrayList<>();
        int it = 0;
        while (it < ctx.children.size()) {
            ArrayList<Regular> match = (ArrayList<Regular>) ctx.children.get(it).accept(this);
            returned.add(match);
            it += 2;
        }
        return returned;
    }

    @Override
    public ArrayList<Regular> visitRegular(ParserParser.RegularContext ctx) {
        ArrayList<Regular> returned = new ArrayList<>();
        for (ParseTree parseTree : ctx.children) {
            String text = (String) parseTree.accept(this);
            if (text.startsWith("$")) {
                text = text.substring(1);
                returned.add(new Regular(text, true));
            } else {
                returned.add(new Regular(text, false));
            }
        }
        return returned;
    }

    @Override
    public TreeLexem visitMatch(ParserParser.MatchContext ctx) {
        ArrayList<TokenOrLexemName> rules = new ArrayList<>();
        ArrayList<LexemFunction> lexemFunctions = new ArrayList<>();
        if (ctx.children == null) {
            return new TreeLexem(rules, new ArrayList<>());
        }
        for (ParseTree parseTree : ctx.children) {
            if (parseTree instanceof ParserParser.FunctionsContext) {
                lexemFunctions = (ArrayList<LexemFunction>) parseTree.accept(this);
            } else {
                String name = (String) parseTree.accept(this);
                if (Utils.isLexem(name) || Utils.isToken(name)) {
                    rules.add(new TokenOrLexemName((String) parseTree.accept(this)));
                }
            }
        }
        return new TreeLexem(rules, lexemFunctions);
    }

    @Override
    public String visitTerminal(TerminalNode node) {
        if (node.getSymbol().getType() == ParserLexer.Regular) {
            String temp = node.getSymbol().getText();
            return temp.substring(1, temp.length() - 1);
        }
        if (node.getSymbol().getType() == ParserLexer.StrongRegular) {
            String temp = node.getSymbol().getText();
            return temp.charAt(0) + temp.substring(2, temp.length() - 1);
        }
        if (node.getSymbol().getType() == ParserLexer.Varible) {
            String temp = node.getSymbol().getText();
            return temp.substring(1);
        }
        return node.getSymbol().getText();
    }

    @Override
    public Mono visitErrorNode(ErrorNode node) {
        throw new IllegalArgumentException("Find error node");
    }

    @Override
    protected Mono defaultResult() {
        throw new IllegalArgumentException("Find not implemented file");
    }
}
