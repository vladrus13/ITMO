package program;

public class programToken {
    private final programEnum type;
    private final String data;

    public programToken(programEnum type, String data) {
        this.type = type;
        this.data = data;
    }

    public programToken(programEnum type) {
            this.type = type;
            this.data = null;
    }


    public programEnum getType() {
        return type;
    }

    public String getData() {
        return data;
    }
}