package prolog.prtest.map;

import alice.tuprolog.Int;
import alice.tuprolog.Struct;
import alice.tuprolog.Term;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public final class Entry {
    private final Int key;
    private Term value;
    private Struct struct;

    public Entry(final Int key, final Term value) {
        this.key = key;
        setValue(value);
    }

    public void setValue(final Term value) {
        this.value = value;
        struct = new Struct(",", key, value);
    }

    public Int getKey() {
        return key;
    }

    public Term getValue() {
        return value;
    }

    public Struct getStruct() {
        return struct;
    }

    @Override
    public String toString() {
        return key + "=" + value;
    }
}
