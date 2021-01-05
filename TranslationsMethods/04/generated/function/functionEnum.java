package function;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum functionEnum {
    OpenBracket("(\\Q(\\E).*", false), CloseBracket("(\\Q)\\E).*", false), Semicolon("(\\Q;\\E).*", false), Comma("(\\Q,\\E).*", false), Reference("(\\Q*\\E).*", false), Name("([a-zA-Z]+).*", false), WS("([ \\t\\n\\r]).*", true), End("", false);

    functionEnum(String matching, boolean isSkipable) {
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