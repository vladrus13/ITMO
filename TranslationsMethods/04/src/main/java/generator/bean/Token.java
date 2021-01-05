package generator.bean;

import java.util.ArrayList;
import java.util.Objects;

public class Token extends Mono {
    private final String name;
    private final ArrayList<ArrayList<Regular>> contain;
    private final boolean skipable;

    public Token(String name, ArrayList<ArrayList<Regular>> contain, boolean skipable) {
        this.name = name;
        this.contain = contain;
        this.skipable = skipable;
    }

    public String getName() {
        return name;
    }

    public ArrayList<ArrayList<Regular>> getContain() {
        return contain;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Token token = (Token) o;
        return name.equals(token.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    public boolean isSkipable() {
        return skipable;
    }
}
