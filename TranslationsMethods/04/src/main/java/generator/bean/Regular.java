package generator.bean;

public class Regular extends Mono {
    private final String regular;
    private final boolean strong;

    public Regular(String regular, boolean strong) {
        this.regular = regular;
        this.strong = strong;
    }

    public String getRegular() {
        return regular;
    }

    public boolean isStrong() {
        return strong;
    }
}
