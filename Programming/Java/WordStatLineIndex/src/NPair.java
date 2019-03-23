public class NPair implements Comparable<NPair>{
    private Integer f;
    private Integer s;

    public NPair(Integer t, Integer u) {
        this.f = t;
        this.s = u;
    }

    public Integer getFirst() {
        return f;
    }

    public Integer getSecond() {
        return s;
    }

    public int compareTo(NPair pair2) {
        return (f.compareTo(pair2.f) == 0 ? s.compareTo(pair2.s) : f.compareTo(pair2.f));
    }
}