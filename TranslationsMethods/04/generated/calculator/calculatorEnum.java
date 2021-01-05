package calculator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum calculatorEnum {
    OpenBracket("(\\Q(\\E).*", false), CloseBracket("(\\Q)\\E).*", false), Plus("(\\Q+\\E).*", false), Number("([0-9]+).*", false), Multiply("(\\Q*\\E).*", false), Divide("(\\Q/\\E).*", false), WS("([ \\t\\n\\r]).*", true), End("", false);

    calculatorEnum(String matching, boolean isSkipable) {
        this.matching = matching;
        this.pattern = Pattern.compile(matching);
        this.isSkipable = isSkipable;
    }

    public String getMatching() {
            return matching;
    }

    public Matcher mather(String s) {
        return pattern.matcher(s);
    }

    public boolean isSkipable() {
        return isSkipable;
    }

    private final String matching;
    private final Pattern pattern;
    private final boolean isSkipable;
}