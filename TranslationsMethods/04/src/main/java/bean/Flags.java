package bean;

public class Flags {
    private boolean lazy = false;
    private boolean heavy = false;
    private String input = null;
    private String output = null;
    private String name = null;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isLazy() {
        return lazy;
    }

    public String getInput() {
        return input;
    }

    public String getOutput() {
        return output;
    }

    public void setLazy(boolean lazy) {
        this.lazy = lazy;
    }

    public void setHeavy(boolean heavy) {
        this.heavy = heavy;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public void setSmall() {
    }

    public void setUseless() {
    }

    public void setRepeater() {
    }

    public String toString() {
        return String.format("\t\tinput = %s\n\t\toutput = %s\n\t\tname = %s\n\t\tlazy = %b\n\t\theavy = %b", input, output, name, lazy, heavy);
    }
}
