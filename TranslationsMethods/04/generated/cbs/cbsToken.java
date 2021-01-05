package cbs;

public class cbsToken {
    private final cbsEnum type;
    private final String data;

    public cbsToken(cbsEnum type, String data) {
        this.type = type;
        this.data = data;
    }

    public cbsToken(cbsEnum type) {
            this.type = type;
            this.data = null;
    }


    public cbsEnum getType() {
        return type;
    }

    public String getData() {
        return data;
    }
}