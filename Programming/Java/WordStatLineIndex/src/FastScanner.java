import java.io.*;
import java.lang.StringBuilder;

public class FastScanner {
    private static int CLUSTER = 256;
    private static byte[] buffer;
    public static String Sbuffer;
    private static File file;
    private static InputStream reader;
    private static int currentByte;

    FastScanner(File f) {
        file = f;
        currentByte = CLUSTER + 1;
        buffer = new byte[CLUSTER];
        Sbuffer = "";
        try {
            reader = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            Tools.send(0001);
        }
    }

    FastScanner(String s) {
        file = null;
        currentByte = CLUSTER + 1;
        Sbuffer = "";
        buffer = new byte[CLUSTER];
        try {
            reader = new ByteArrayInputStream(s.getBytes());
        } catch (Exception e) {
            // and what UTF-8?
        }
    }

    FastScanner(InputStream f) {
        buffer = new byte[CLUSTER];
        Sbuffer = "";
        reader = f;
        currentByte = CLUSTER + 1;
    }

    public static void close() {
        currentByte = CLUSTER + 1;
        buffer = new byte[CLUSTER];
        try {
            reader.close();
        } catch (IOException e) {
            Tools.send(0002);
        }
        file = null;
    }

    public static boolean end() {
        return (currentByte >= CLUSTER || buffer[currentByte] == 0);
    }

    public static byte nextByte() {
        if (end()) {
            if (!readBuffer()) {
                return (byte) 0;
            }
        }
        byte answer = buffer[currentByte];
        currentByte++;
        return answer;
    }

    private static int symbolLength(byte c) {
        if (c == 13) {
            return 2;
        }
        if ((c >> 7) == 0) {
            return 1;
        }
        if ((c >> 5) == -2) {
            return 2;
        }
        if ((c >> 4) == -2) {
            return 3;
        }
        if ((c >> 3) == -2) {
            return 4;
        }
        return -1;
    }

    private static char nextChar() {
        while (true) {
            byte controlSymbol;
            controlSymbol = nextByte();
            if (controlSymbol == 0) {
                return (char) 0;
            }
            byte[] symbol = new byte[symbolLength(controlSymbol)];
            symbol[0] = controlSymbol;
            for (int i = 1; i < symbolLength(controlSymbol); i++) {
                symbol[i] = nextByte();
                if (symbol[i] == 0) return (char) 0;
            }
            try {
                String temp = new String(symbol, "UTF-8");
                return temp.charAt(0);
            } catch (UnsupportedEncodingException e) {
                Tools.send(0004);
            }
        }
    }

    private static boolean readBuffer() {
        int numReader = 0;
        currentByte = 0;
        buffer = new byte[CLUSTER];
        while (numReader == 0) {
            try {
                numReader = reader.read(buffer);
                if (numReader == -1) {
                    return false;
                }
            } catch (IOException e) {
                Tools.send(0002);
            }
        }
        return true;
    }

    public String nextLine() {
        StringBuilder returnString = new StringBuilder("");
        while (true) {
            char character = nextChar();
            if (character == 0) {
                return returnString.toString();
            }
            if (character == System.lineSeparator().charAt(0) || character == (char) 0) {
                return returnString.toString();
            } else {
                returnString.append(character);
            }
        }
    }

    public boolean hasNext() {
        if (!end()) {
            return true;
        } else {
            if (readBuffer()) {
                return true;
            } else {
                return false;
            }
        }
    }
}
