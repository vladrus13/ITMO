import java.util.ArrayList;

public class Tools {
    public static int MODE_CONSOLE = 0;
    public static int ALL_WARNINGS_ERROR = 1;
    //private static int numberWarnings = 0;

    public static String[] parseStringInt(String s){
        ArrayList <String> answer = new ArrayList <>();
        StringBuffer temp = new StringBuffer("");
        for (int i = 0; i < s.length(); i++){
            char character = s.charAt(i);
            if (Character.isLetter(character) || Tools.isApostrophe(character) || Tools.isDash(character)){
                temp.append(s.charAt(i));
            } else {
                if (temp.length() != 0) {
                    answer.add(temp.toString());
                    temp = new StringBuffer("");
                }
            }
        }
        if (temp.length() != 0) {
            answer.add(temp.toString());
            temp = new StringBuffer("");
        }
        String[] returned = new String[answer.size()];
        for (int i = 0; i < answer.size(); i++){
            returned[i] = answer.get(i);
        }
        return returned;
    }

    public static  boolean isDash(char symbol){
        if (symbol == '\u002D') return true;
        if (symbol == '\u058A') return true;
        if (symbol == '\u05BE') return true;
        if (symbol == '\u1400') return true;
        if (symbol == '\u1806') return true;
        if (symbol == '\u2010') return true;
        if (symbol == '\u2011') return true;
        if (symbol == '\u2012') return true;
        if (symbol == '\u2013') return true;
        if (symbol == '\u2014') return true;
        if (symbol == '\u2015') return true;
        if (symbol == '\u2E17') return true;
        if (symbol == '\u2E1A') return true;
        if (symbol == '\u2E3A') return true;
        if (symbol == '\u2E3B') return true;
        if (symbol == '\u2E40') return true;
        if (symbol == '\u301C') return true;
        if (symbol == '\u3030') return true;
        if (symbol == '\u30A0') return true;
        if (symbol == '\uFE31') return true;
        if (symbol == '\uFE32') return true;
        if (symbol == '\uFE58') return true;
        if (symbol == '\uFE63') return true;
        if (symbol == '\uFF0D') return true;
        return false;
    }

    public static  boolean isApostrophe(char symbol){
        //if (symbol == '\u02BC') return true;
        //if (symbol == '\u02EE') return true;
        //if (symbol == '\u07F4') return true;
        //if (symbol == '\u07F5') return true;
        if (symbol == '\'') return true;
        return false;
    }

    public static void send(int code){
        switch (code){
            // class.getMethod(Method) and invoke. But its VERY NOT FAST
            case 1: send_0001(); break;
            case 10: send_0010(); break;
            case 2: send_0002(); break;
            case 3: send_0003(); break;
            case 4: send_0004(); break;
            default: send_err(code); break;
        }
    }

    private static void send_err(int code){
        if (MODE_CONSOLE == 0){
            System.out.print("Error: Unknown Error Code " + Integer.toString(code) + '\n');
        }
        if (ALL_WARNINGS_ERROR == 1) {
            System.exit(1);
        } else {
            System.exit(0);
        }
    }

    private static void send_0001(){
        if (MODE_CONSOLE == 0) {
            System.out.print("Error: File not find or not access for file\n");
        }
        if (ALL_WARNINGS_ERROR == 1) {
            System.exit(1);
        } else {
            System.exit(0);
        }
    }

    private static void send_0004(){
        if (MODE_CONSOLE == 0) {
            System.out.print("Error: Encoding file is failed\n");
        }
        if (ALL_WARNINGS_ERROR == 1) {
            System.exit(1);
        } else {
            System.exit(0);
        }
    }

    private static void send_0002(){
        if (MODE_CONSOLE == 0) {
            System.out.print("Error: Not access for file or not access for directive\n");
        }
        if (ALL_WARNINGS_ERROR == 1) {
            System.exit(1);
        } else {
            System.exit(0);
        }
    }

    private static void send_0003(){
        if (MODE_CONSOLE == 0) {
            System.out.print("Error: Buffer end, but read not finished\n");
        }
        if (ALL_WARNINGS_ERROR == 1) {
            System.exit(1);
        } else {
            System.exit(0);
        }
    }

    private  static void send_0010(){
        if (MODE_CONSOLE == 0){
            if (MODE_CONSOLE == 0) {
                System.out.print("Warning: Input number is too long!\n");
            }
        }
        if (ALL_WARNINGS_ERROR == 1) {
            System.exit(1);
        }
    }
}
