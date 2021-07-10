import impl.evol.e.Bug;

import java.util.Arrays;

public record ResultPart(double[] results, Bug bug, int genome) implements Comparable<ResultPart> {

    @Override
    public int compareTo(ResultPart another) {
        int compareLength = Integer.compare(this.results.length, another.results.length);
        if (compareLength != 0) {
            return compareLength;
        }
        for (int i = 0; i < this.results.length; i++) {
            int compareI = Double.compare(this.results[i], another.results[i]);
            if (compareI != 0) {
                return compareI;
            }
        }
        return Integer.compare(this.genome, another.genome);
    }

    @Override
    public Bug bug() {
        return bug;
    }

    public String toString() {
        return Arrays.toString(results) + "\n" + Launcher.toString(bug);
    }
}
