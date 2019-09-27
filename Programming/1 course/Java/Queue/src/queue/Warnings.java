package queue;

public class Warnings {
    private static int MODE_CONSOLE = 0;
    private static int ALL_WARNINGS_ERROR = 0;

    public static void send(int code){
        switch (code){
            // class.getMethod(Method) and invoke. But its VERY NOT FAST
            case 1: send_0001(); break;
            case 10: send_0010(); break;
            case 2: send_0002(); break;
            case 3: send_0003(); break;
            case 4: send_0004(); break;
            default: sendErr(code); break;
        }
    }

    private static void sendErr(int code){
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
            System.out.print("Error: Element is NULL\n");
        }
        if (ALL_WARNINGS_ERROR == 1) {
            System.exit(1);
        } else {
            System.exit(0);
        }
    }

    private static void send_0004(){
        if (MODE_CONSOLE == 0) {
            System.out.print("Error: \n");
        }
        if (ALL_WARNINGS_ERROR == 1) {
            System.exit(1);
        } else {
            System.exit(0);
        }
    }

    private static void send_0002(){
        if (MODE_CONSOLE == 0) {
            System.out.print("Error: Erase element from empty stack\n");
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

    private static void send_0010(){
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
