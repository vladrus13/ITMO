package cbs;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum cbsEnum {
    OpenBracket("(\\Q(\\E).*"), CloseBracket("(\\Q)\\E).*"), E("(\\Qe\\E).*"), End("");

    cbsEnum(String matching) {
        this.matching = matching;
        this.pattern = Pattern.compile(matching);
    }

    public String getMatching() {
            return matching;
    }

    public Matcher mather(String s) {
        return pattern.matcher(s);
    }

    private final String matching;
    private final Pattern pattern;
}