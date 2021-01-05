package calculator;

public class calculatorToken {
    private final calculatorEnum type;
    private final String data;

    public calculatorToken(calculatorEnum type, String data) {
        this.type = type;
        this.data = data;
    }

    public calculatorToken(calculatorEnum type) {
            this.type = type;
            this.data = null;
    }


    public calculatorEnum getType() {
        return type;
    }

    public String getData() {
        return data;
    }
}