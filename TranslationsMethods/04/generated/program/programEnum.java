package program;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum programEnum {
    Int("(\\Qint\\E).*", false), LongLong("(\\Qlong long\\E).*", false), Auto("(\\Qauto\\E).*", false), Bool("(\\Qbool\\E).*", false), Double("(\\Qdouble\\E).*", false), LongDouble("(\\Qlong double\\E).*", false), Void("(\\Qvoid\\E).*", false), String("(\\Qstring\\E).*", false), Vector("(\\Qvector\\E).*", false), PlusPlus("(\\Q++\\E).*", false), MinusMinus("(\\Q--\\E).*", false), Not("(\\Q!\\E)|(\\Qnot\\E).*", false), PlusAssign("(\\Q+=\\E).*", false), MinusAssign("(\\Q-=\\E).*", false), MultiplyAssign("(\\Q*=\\E).*", false), DivAssign("(\\Q/=\\E).*", false), ModAssign("(\\Q%=\\E).*", false), XorAssign("(\\Q^=\\E).*", false), AndAssign("(\\Q&=\\E).*", false), OrAssign("(\\Q|=\\E).*", false), LeftShiftAssign("(\\Q<<=\\E).*", false), RightShiftAssign("(\\Q>>=\\E).*", false), Plus("(\\Q+\\E).*", false), Minus("(\\Q-\\E).*", false), Multiply("(\\Q*\\E).*", false), Div("(\\Q/\\E).*", false), Mod("(\\Q%\\E).*", false), Xor("(\\Q^\\E).*", false), And("(\\Q&\\E).*", false), Or("(\\Q|\\E).*", false), Equal("(\\Q==\\E).*", false), NotEqual("(\\Q!=\\E).*", false), LessEqual("(\\Q<=\\E).*", false), GreaterEqual("(\\Q>=\\E).*", false), AndAnd("(\\Q&&\\E)|(\\Qand\\E).*", false), OrOr("(\\Q||\\E)|(\\Qor\\E).*", false), LeftShift("(\\Q<<\\E).*", false), RightShift("(\\Q>>\\E).*", false), Assign("(\\Q=\\E).*", false), Less("(\\Q<\\E).*", false), Greater("(\\Q>\\E).*", false), Tilde("(\\Q~\\E).*", false), Comma("(\\Q,\\E).*", false), Semicolon("(\\Q;\\E).*", false), OpenBracket("(\\Q(\\E).*", false), CloseBracket("(\\Q)\\E).*", false), OpenFigureBracket("(\\Q{\\E).*", false), CloseFigureBracket("(\\Q}\\E).*", false), Point("(\\Q.\\E).*", false), Pointer("(\\Q->\\E).*", false), OpenSqBracket("(\\Q[\\E).*", false), CloseSqBracket("(\\Q]\\E).*", false), Child("(\\Q::\\E).*", false), Colon("(\\Q:\\E).*", false), Quotes("(\\Q\"\\E).*", false), Break("(\\Qbreak\\E).*", false), Return("(\\Qreturn\\E).*", false), Continue("(\\Qcontinue\\E).*", false), Delete("(\\Qdelete\\E).*", false), Include("(\\Q#include\\E).*", false), For("(\\Qfor\\E).*", false), While("(\\Qwhile\\E).*", false), Class("(\\Qclass\\E).*", false), Struct("(\\Qstruct\\E).*", false), If("(\\Qif\\E).*", false), Switch("(\\Qswitch\\E).*", false), Case("(\\Qcase\\E).*", false), Using("(\\Qusing\\E).*", false), Digits("([0-9]+).*", false), True("(\\Qtrue\\E).*", false), False("(\\Qfalse\\E).*", false), Word("([a-zA-Z_]+).*", false), WS("([ \\t\\n\\r]+).*", true), End("", false);

    programEnum(String matching, boolean isSkipable) {
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