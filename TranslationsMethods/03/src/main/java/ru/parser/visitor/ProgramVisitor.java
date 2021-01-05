package ru.parser.visitor;

import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;
import ru.parser.antlr.ProgramBaseVisitor;
import ru.parser.antlr.ProgramParser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProgramVisitor extends ProgramBaseVisitor<StringBuilder> {

    private static int depth = 0;

    private void visit(ParseTree parseTree, StringBuilder stringBuilder, String append) {
        stringBuilder.append(parseTree.accept(this)).append(append);
        if (append.endsWith("\n")) {
            stringBuilder.append("\t".repeat(depth));
        }
    }

    private void visit(ParseTree parseTree, StringBuilder stringBuilder) {
        stringBuilder.append(parseTree.accept(this));
    }

    private StringBuilder visit(List<ParseTree> trees, List<String> appends) {
        StringBuilder stringBuilder = new StringBuilder();
        assert trees.size() == appends.size();
        for (int i = 0; i < trees.size(); i++) {
            if (appends.get(i) == null || appends.get(i).length() == 0) {
                visit(trees.get(i), stringBuilder);
            } else {
                visit(trees.get(i), stringBuilder, appends.get(i));
            }
        }
        return stringBuilder;
    }

    private StringBuilder visit(List<ParseTree> trees, String append) {
        StringBuilder stringBuilder = new StringBuilder();
        for (ParseTree tree : trees) {
            visit(tree, stringBuilder, append);
        }
        return stringBuilder;
    }

    @Override
    public StringBuilder visitEmptyExpression(ProgramParser.EmptyExpressionContext ctx) {
        return new StringBuilder();
    }

    @Override
    public StringBuilder visitVectorExpression(ProgramParser.VectorExpressionContext ctx) {
        return visit(ctx.children, List.of("", "", "", ""));
    }

    @Override
    public StringBuilder visitProgram(ProgramParser.ProgramContext ctx) {
        return visit(ctx.children, System.lineSeparator());
    }

    @Override
    public StringBuilder visitHeaders(ProgramParser.HeadersContext ctx) {
        if (ctx.children == null) {
            return new StringBuilder();
        }
        return visit(ctx.children, System.lineSeparator());
    }

    @Override
    public StringBuilder visitSingleAssignOperator(ProgramParser.SingleAssignOperatorContext ctx) {
        return visit(ctx.children.get(0));
    }

    @Override
    public StringBuilder visitHeader(ProgramParser.HeaderContext ctx) {
        List<String> appends = new ArrayList<>(List.of(" "));
        appends.addAll(Collections.nCopies(ctx.children.size() - 1, ""));
        return visit(ctx.children, appends);
    }

    @Override
    public StringBuilder visitTerminal(TerminalNode node) {
        return new StringBuilder(node.getSymbol().getText());
    }

    @Override
    public StringBuilder visitBlock(ProgramParser.BlockContext ctx) {
        ArrayList<String> appends = new ArrayList<>(Collections.nCopies(ctx.children.size() - 1, System.lineSeparator().repeat(2)));
        appends.add("\n");
        return visit(ctx.children, appends);
    }

    @Override
    public StringBuilder visitR_switch(ProgramParser.R_switchContext ctx) {
        depth++;
        ArrayList<String> appends = new ArrayList<>(List.of(" ", "", "", "\n", "\n"));
        appends.addAll(Collections.nCopies(ctx.children.size() - 6, "\n"));
        appends.add("");
        StringBuilder stringBuilder = visit(ctx.children, appends);
        depth--;
        return stringBuilder;
    }

    @Override
    public StringBuilder visitR_case(ProgramParser.R_caseContext ctx) {
        return visit(ctx.children, List.of(" ", "", "\n", ""));
    }

    @Override
    public StringBuilder visitUsing(ProgramParser.UsingContext ctx) {
        return visit(ctx.children, List.of(" ", " ", "", ""));
    }

    @Override
    public StringBuilder visitFullFunction(ProgramParser.FullFunctionContext ctx) {
        return visit(ctx.children, List.of(" ", "", "", "", "\n", "\n", "\n", ""));
    }

    @Override
    public StringBuilder visitNotFullFunction(ProgramParser.NotFullFunctionContext ctx) {
        return visit(ctx.children, List.of(" ", "", "", "", "", ""));
    }

    @Override
    public StringBuilder visitType(ProgramParser.TypeContext ctx) {
        return ctx.getChild(0).accept(this);
    }

    @Override
    public StringBuilder visitArguments(ProgramParser.ArgumentsContext ctx) {
        if (ctx.children == null) {
            return new StringBuilder();
        }
        ArrayList<ParseTree> argumentContexts = new ArrayList<>();
        ArrayList<String> appends = new ArrayList<>();
        for (ParseTree parseTree : ctx.children) {
            if (parseTree instanceof ProgramParser.ArgumentContext) {
                argumentContexts.add(parseTree);
                appends.add(", ");
            }
        }
        appends.set(appends.size() - 1, "");
        return visit(argumentContexts, appends);
    }

    @Override
    public StringBuilder visitPointerExpression(ProgramParser.PointerExpressionContext ctx) {
        return visit(ctx.children, List.of("", "", ""));
    }

    public boolean isThere(List<ParseTree> list, Class<?> check) {
        for (ParseTree parseTree : list) {
            if (parseTree.getClass().getName().equals(check.getName())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public StringBuilder visitArgument(ProgramParser.ArgumentContext ctx) {
        ArrayList<String> appends = new ArrayList<>();
        if (isThere(ctx.children, ProgramParser.TypeContext.class)) {
            appends.add(" ");
        } else {
            appends.add("");
        }
        if (isThere(ctx.children, ProgramParser.ReferencesContext.class)) {
            appends.add(" ");
        } else {
            appends.add("");
        }
        appends.add("");
        return visit(ctx.children, appends);
    }

    @Override
    public StringBuilder visitReferences(ProgramParser.ReferencesContext ctx) {
        if (ctx.children == null) {
            return new StringBuilder();
        }
        return visit(ctx.children, "");
    }

    @Override
    public StringBuilder visitBody(ProgramParser.BodyContext ctx) {
        depth++;
        List<String> appends = new ArrayList<>(Collections.nCopies(ctx.children.size() - 1, "\n"));
        appends.add("");
        StringBuilder stringBuilder = new StringBuilder("\t").append(visit(ctx.children, appends));
        depth--;
        return stringBuilder;
    }

    @Override
    public StringBuilder visitMaybeExpression(ProgramParser.MaybeExpressionContext ctx) {
        return visit(ctx.children, List.of("", ""));
    }

    @Override
    public StringBuilder visitR_class(ProgramParser.R_classContext ctx) {
        return visit(ctx.children, List.of(" ", "\n", "\n", "\n", ""));
    }

    @Override
    public StringBuilder visitClassOrStructBody(ProgramParser.ClassOrStructBodyContext ctx) {
        depth++;
        List<String> appends = new ArrayList<>(Collections.nCopies(ctx.children.size() - 1, "\n\n"));
        appends.add("");
        StringBuilder stringBuilder = new StringBuilder("\t").append(visit(ctx.children, appends));
        depth--;
        return stringBuilder;
    }

    @Override
    public StringBuilder visitCallingFunctionExpression(ProgramParser.CallingFunctionExpressionContext ctx) {
        return visit(ctx.children, List.of("", "", "", ""));
    }

    @Override
    public StringBuilder visitStruct(ProgramParser.StructContext ctx) {
        return visit(ctx.children, List.of(" ", "\n", "\n", "\n", ""));
    }

    @Override
    public StringBuilder visitField(ProgramParser.FieldContext ctx) {
        return visit(ctx.children, List.of(" ", "", ""));
    }

    @Override
    public StringBuilder visitR_for(ProgramParser.R_forContext ctx) {
        return visit(ctx.children, List.of(" ", "", " ", " ", "", "\n", "\n", "\n", ""));
    }

    @Override
    public StringBuilder visitR_while(ProgramParser.R_whileContext ctx) {
        return visit(ctx.children, List.of(" ", "", "", "\n", "\n", "\n", ""));
    }

    @Override
    public StringBuilder visitR_if(ProgramParser.R_ifContext ctx) {
        return visit(ctx.children, List.of(" ", "", "", "\n", "\n", "\n", ""));
    }

    @Override
    public StringBuilder visitInstruction(ProgramParser.InstructionContext ctx) {
        if (ctx.children.size() > 2) {
            return visit(ctx.children, List.of(" ", "", ""));
        } else {
            return visit(ctx.children, List.of("", ""));
        }
    }

    @Override
    public StringBuilder visitBinaryExpression(ProgramParser.BinaryExpressionContext ctx) {
        return visit(ctx.children, List.of(" ", " ", ""));
    }

    @Override
    public StringBuilder visitValueExpression(ProgramParser.ValueExpressionContext ctx) {
        return visit(ctx.children, List.of(""));
    }

    @Override
    public StringBuilder visitSingleAssignExpression(ProgramParser.SingleAssignExpressionContext ctx) {
        return visit(ctx.children, List.of("", ""));
    }

    @Override
    public StringBuilder visitInBracketExpression(ProgramParser.InBracketExpressionContext ctx) {
        return visit(ctx.children, List.of("", "", ""));
    }

    @Override
    public StringBuilder visitUnaryExpression(ProgramParser.UnaryExpressionContext ctx) {
        return visit(ctx.children, List.of("", ""));
    }

    @Override
    public StringBuilder visitValue(ProgramParser.ValueContext ctx) {
        if (ctx.children.size() == 1) {
            return visit(ctx.children, List.of(""));
        } else {
            return visit(ctx.children, List.of("", "", ""));
        }
    }

    @Override
    public StringBuilder visitBinaryAssignOperator(ProgramParser.BinaryAssignOperatorContext ctx) {
        return visit(ctx.children.get(0));
    }

    @Override
    public StringBuilder visitBinaryOperator(ProgramParser.BinaryOperatorContext ctx) {
        return visit(ctx.children.get(0));
    }

    @Override
    public StringBuilder visitUnaryOperation(ProgramParser.UnaryOperationContext ctx) {
        return visit(ctx.children.get(0));
    }

    @Override
    protected StringBuilder defaultResult() {
        throw new IllegalArgumentException("Not implement some expression");
    }

    @Override
    public StringBuilder visitErrorNode(ErrorNode node) {
        throw new IllegalArgumentException("Get error node");
    }
}
