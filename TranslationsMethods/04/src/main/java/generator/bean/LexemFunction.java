package generator.bean;

public class LexemFunction extends Mono {
    private final String code;

    public LexemFunction(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    @Override
    public String toString() {
        return "[<" + code + ">]";
    }
}
