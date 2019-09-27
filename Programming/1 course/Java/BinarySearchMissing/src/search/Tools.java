package search;

public class Tools {
    // NOTE: MODE_CONSOLE
    //                    = 0: if we want to get an error in the console
    //                    = 1: else
    public static int MODE_CONSOLE = 0;

    // NOTE: ALL_WARNINGS_ERROR
    //                          = 1: if we want to break the program with type errors "WARNINGS"
    //                          = 0: else
    public static int ALL_WARNINGS_ERROR = 1;

    // pre: char symbol
    // post: booolean = (symblol is Dash)
    public static boolean isDash(char symbol) {
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

    // pre: char symbol
    // post: boolean = (symbol is Apostrophe)
    public static boolean isApostrophe(char symbol) {
        if (symbol == '\'') return true;
        return false;
    }

    // pre: code in Z and is an error code (maybe, unexisting)
    // post: send error code and (maybe) break the program
    public static void send(int code) {
        switch (code) {
            // class.getMethod(Method) and invoke. But its VERY NOT FAST
            case 1:
                send_0001();
                break;
            case 10:
                send_0010();
                break;
            case 2:
                send_0002();
                break;
            case 3:
                send_0003();
                break;
            case 4:
                send_0004();
                break;
            case 11:
                send_0011();
                break;
            default:
                send_err(code);
                break;
        }
    }

    // pre: code in Z and unexisting code error
    // post: send error code and (maybe) break the program
    private static void send_err(int code) {
        if (MODE_CONSOLE == 0) {
            System.out.print("Error: Unknown Error Code " + Integer.toString(code) + '\n');
        }
        if (ALL_WARNINGS_ERROR == 1) {
            System.exit(1);
        } else {
            System.exit(0);
        }
    }

    // pre: code in Z and existing code error
    // post: send error code and (maybe) break the program
    private static void send_0001() {
        if (MODE_CONSOLE == 0) {
            System.out.print("\n");
        }
        if (ALL_WARNINGS_ERROR == 1) {
            System.exit(1);
        } else {
            System.exit(0);
        }
    }

    // pre: code in Z and existing code error
    // post: send error code and (maybe) break the program
    private static void send_0004() {
        if (MODE_CONSOLE == 0) {
            System.out.print("\n");
        }
        if (ALL_WARNINGS_ERROR == 1) {
            System.exit(1);
        } else {
            System.exit(0);
        }
    }

    // pre: code in Z and existing code error
    // post: send error code and (maybe) break the program
    private static void send_0002() {
        if (MODE_CONSOLE == 0) {
            System.out.print("\n");
        }
        if (ALL_WARNINGS_ERROR == 1) {
            System.exit(1);
        } else {
            System.exit(0);
        }
    }

    // pre: code in Z and existing code error
    // post: send error code and (maybe) break the program
    private static void send_0003() {
        if (MODE_CONSOLE == 0) {
            System.out.print("\n");
        }
        if (ALL_WARNINGS_ERROR == 1) {
            System.exit(1);
        } else {
            System.exit(0);
        }
    }

    // pre: code in Z and existing code error
    // post: send error code and (maybe) break the program
    private static void send_0010() {
        if (MODE_CONSOLE == 0) {
            if (MODE_CONSOLE == 0) {
                System.out.print("Warning: Input number is too long!\n");
            }
        }
        if (ALL_WARNINGS_ERROR == 1) {
            System.exit(1);
        }
    }

    // pre: code in Z and existing code error
    // post: send error code and (maybe) break the program
    private static void send_0011() {
        if (MODE_CONSOLE == 0) {
            if (MODE_CONSOLE == 0) {
                System.out.print("Warning: Input number is not number!\n");
            }
        }
        if (ALL_WARNINGS_ERROR == 1) {
            System.exit(1);
        }
    }
}
