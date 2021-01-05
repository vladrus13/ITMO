package generator.problem;

import generator.Utils;
import generator.bean.Container;
import generator.bean.Lexem;
import generator.bean.Token;
import generator.bean.TokenOrLexemName;

import java.util.*;

public class RulesFixer {

    private static boolean fixNeeded(Container container) {
        ArrayDeque<String> deque = new ArrayDeque<>();
        Set<String> names = new HashSet<>();
        deque.add(container.getMain().getName());
        names.add(container.getMain().getName());
        boolean lost = false;
        while (!deque.isEmpty()) {
            String it = deque.pollFirst();
            if (Utils.isLexem(it)) {
                Lexem lexem = container.findLexem(it);
                if (lexem == null) {
                    Utils.writelnFix("WARNING: Can't find lexem: " + it);
                    lost = true;
                    continue;
                }
                for (ArrayList<TokenOrLexemName> array : lexem.getCombine()) {
                    for (TokenOrLexemName name : array) {
                        if (!names.contains(name.getName())) {
                            names.add(name.getName());
                            deque.addLast(name.getName());
                        }
                    }
                }
            }
        }
        if (lost) {
            return true;
        }
        for (Lexem lexem : container.getLexems()) {
            if (!names.contains(lexem.getName())) {
                Utils.writelnFix("WARNING: Useless lexem" + lexem.getName());
            }
        }
        for (Token token : container.getTokens()) {
            if (!names.contains(token.getName())) {
                Utils.writelnFix("WARNING: Useless token: " + token.getName());
            }
        }
        return false;
    }

    public static boolean fixE(Container container) {
        Set<String> eGenerateSet = new HashSet<>();
        for (Lexem lexem : container.getLexems()) {
            for (ArrayList<TokenOrLexemName> arrayList : lexem.getCombine()) {
                if (arrayList.size() == 0) {
                    eGenerateSet.add(lexem.getName());
                }
            }
        }
        boolean isNew = true;
        while (isNew) {
            isNew = false;
            for (Lexem lexem : container.getLexems()) {
                if (eGenerateSet.contains(lexem.getName())) continue;
                for (ArrayList<TokenOrLexemName> arrayList : lexem.getCombine()) {
                    int countNonE = 0;
                    for (TokenOrLexemName name : arrayList) {
                        if (!eGenerateSet.contains(name.getName())) {
                            countNonE++;
                        }
                    }
                    if (countNonE == 0) {
                        eGenerateSet.add(lexem.getName());
                        isNew = true;
                        break;
                    }
                }
            }
        }
        boolean isFixed = false;
        for (Lexem lexem : container.getLexems()) {
            ArrayList<ArrayList<TokenOrLexemName>> added = new ArrayList<>();
            for (ArrayList<TokenOrLexemName> array : lexem.getCombine()) {
                int countEGenerated = 0;
                for (TokenOrLexemName name : array) {
                    if (eGenerateSet.contains(name.getName())) {
                        countEGenerated++;
                    }
                }
                if (countEGenerated > 0) {
                    isFixed = true;
                    Utils.writelnFix("WARNING: Eps-generated rule: " + Utils.arrayNameToString.apply(array) + ". Try to fix:");
                }
                for (int i = 0; i < countEGenerated; i++) {
                    int bit = i;
                    ArrayList<TokenOrLexemName> next = new ArrayList<>();
                    for (TokenOrLexemName name : array) {
                        if (eGenerateSet.contains(name.getName())) {
                            next.add(name);
                        } else {
                            if (bit % 2 != 0) {
                                next.add(name);
                            }
                            bit /= 2;
                        }
                    }
                    added.add(next);
                    Utils.writeln(Utils.arrayNameToString.apply(next));
                }
            }
            lexem.getCombine().addAll(added);
        }
        for (Lexem lexem : container.getLexems()) {
            for (int i = 0; i < lexem.getCombine().size(); i++) {
                if (lexem.getCombine().get(i).size() == 0) {
                    if (lexem.getName().equals(container.getMain().getName())) {
                        Utils.writelnFix("WARNING: Find a eps-state at starting lexem. Hardly recommended remove it");
                    }
                    lexem.getCombine().remove(i);
                    i--;
                }
            }
        }
        return isFixed;
    }

    @SuppressWarnings("SameReturnValue")
    private static boolean fixRightF(Container container) {
        for (int kk = 0; kk < container.getLexems().size(); kk++) {
            Lexem lexem = container.getLexems().get(kk);
            for (int i = 0; i < lexem.getCombine().size(); i++) {
                if (lexem.getCombine().get(i).isEmpty()) continue;
                for (int j = i + 1; j < lexem.getCombine().size(); j++) {
                    if (lexem.getCombine().get(j).isEmpty()) continue;
                    // A -> ijkB A -> ijkC
                    int it = 0;
                    while (lexem.getCombine().get(i).size() > it && lexem.getCombine().get(j).size() > it &&
                            lexem.getCombine().get(i).get(it).equals(lexem.getCombine().get(j).get(it))) {
                        it++;
                    }
                    if (it != 0) {
                        Utils.writelnFix("WARNING: Find right branch: \n\t" + Utils.arrayNameToString.apply(lexem.getCombine().get(i)) +
                                "\n\t" + Utils.arrayNameToString.apply(lexem.getCombine().get(j)));
                        String nameNewState = Utils.nextRandomLexem(container);
                        ArrayList<TokenOrLexemName> first = new ArrayList<>();
                        for (int k = it; k < lexem.getCombine().get(i).size(); k++) {
                            first.add(lexem.getCombine().get(i).get(k));
                        }
                        while (lexem.getCombine().get(i).size() > it) {
                            lexem.getCombine().get(i).remove(it);
                        }
                        lexem.getCombine().get(i).add(new TokenOrLexemName(nameNewState));
                        ArrayList<TokenOrLexemName> second = new ArrayList<>();
                        for (int k = it; k < lexem.getCombine().get(j).size(); k++) {
                            second.add(lexem.getCombine().get(j).get(k));
                        }
                        lexem.getCombine().remove(j);
                        j--;
                        Lexem newState = new Lexem(nameNewState, new ArrayList<>(), new ArrayList<>(List.of(first, second)));
                        container.addLexems(newState);
                        Utils.writeln("Add new state: " + newState.toString());
                    }
                }
            }
        }
        return false;
    }

    private static boolean fixLeftRecursion(Container container) {
        Utils.writelnFix("Remember, please, choose sequence from 1 to n for left recursion");
        boolean isFixed = false;
        for (int i = 0; i < container.getLexems().size(); i++) {
            Lexem lexem = container.getLexems().get(i);
            for (ArrayList<TokenOrLexemName> array : lexem.getCombine()) {
                if (array.isEmpty()) {
                    continue;
                }
                if (lexem.getName().equals(array.get(0).getName())) {
                    Utils.writelnFix(String.format("Find left recursion rule at %s: %s",
                            lexem.getName(), Utils.arrayArrayNameToString.apply(lexem.getCombine())));
                    ArrayList<ArrayList<TokenOrLexemName>> withoutRecursion = new ArrayList<>();
                    ArrayList<ArrayList<TokenOrLexemName>> withRecursion = new ArrayList<>();
                    TokenOrLexemName nameNewState = new TokenOrLexemName(Utils.nextRandomLexem(container));
                    for (ArrayList<TokenOrLexemName> it : lexem.getCombine()) {
                        if (it.isEmpty() || !it.get(0).getName().equals(lexem.getName())) {
                            withoutRecursion.add(it);
                        } else {
                            withRecursion.add(it);
                            it.remove(0);
                        }
                        it.add(nameNewState);
                    }
                    lexem.getCombine().clear();
                    lexem.getCombine().addAll(withoutRecursion);
                    Lexem newState = new Lexem(nameNewState.getName(), new ArrayList<>(), withRecursion);
                    container.addLexems(newState);
                    Utils.writeln(String.format("Changed rule %s to %s\nAdd rule %s: %s",
                            lexem.getName(), Utils.arrayArrayNameToString.apply(lexem.getCombine()), newState.getName(), Utils.arrayArrayNameToString.apply(newState.getCombine())));
                    isFixed = true;
                    break;
                }
            }
        }
        return isFixed;
    }

    public static boolean fix(Container container) {
        if (fixNeeded(container)) {
            return true;
        }
        if (fixRightF(container)) {
            return true;
        }
        /*
        if (fixE(container)) {
            return true;
        }*/
        return fixLeftRecursion(container);
    }
}
