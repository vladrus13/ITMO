package function;

public class functionToken {
    private final functionEnum type;
    private final String data;

    public functionToken(functionEnum type, String data) {
        this.type = type;
        this.data = data;
    }

    public functionToken(functionEnum type) {
            this.type = type;
            this.data = null;
    }


    public functionEnum getType() {
        return type;
    }

    public String getData() {
        return data;
    }
}