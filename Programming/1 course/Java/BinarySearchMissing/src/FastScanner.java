import search.Tools;

import java.io.*;
import java.lang.StringBuilder;

public class FastScanner {
    private static int CLUSTER = 256;
    private static byte[] buffer;
    public static String Sbuffer;
    private static File file;
    private static InputStream reader;
    private static int currentByte, currentString;
    private static byte[] byter;
    private static byte byter_real_size;

    FastScanner(File f) {
        file = f;
        currentByte = CLUSTER + 1;
        buffer = new byte[CLUSTER];
        Sbuffer = "";
        byter = new byte[0];
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
        byter = new byte[0];
        try {
            reader = new ByteArrayInputStream(s.getBytes());
        } catch (Exception e){
            // and what UTF-8?
        }
    }

    FastScanner(InputStream f) {
        buffer = new byte[CLUSTER];
        byter = new byte[0];
        Sbuffer = "";
        reader = f;
        currentByte = CLUSTER + 1;
    }

    public static void close() {
        currentByte = CLUSTER + 1;
        buffer = new byte[CLUSTER];
        byter = new byte[0];
        try {
            reader.close();
        } catch (IOException e) {
            Tools.send(0002);
        }
        file = null;
    }

    public static boolean end(){
        return (currentByte >= CLUSTER);
    }

    public static byte nextByte(){
        for (int i = 1; i < byter.length; i++){
            if (byter[i] != 0) {
                byte answer = byter[i];
                byter[i] = 0;
                return answer;
            }
        }
        byte answer = buffer[currentByte];
        currentByte++;
        return answer;
    }

    private static int symbolLength(byte c) {
        if (c == 13){
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

    private static boolean readBuffer() {
        int numReader = 0;
        currentByte = 0;
        currentString = 0;
        buffer = new byte[CLUSTER];
        while (numReader == 0) {
            try {
                numReader = reader.read(buffer);
                if (numReader == -1) {
                    if (Sbuffer.charAt(0) == 0) {
                        return false;
                    }
                }
                // make string symbols
                StringBuilder s = new StringBuilder();
                while (true) {
                    if (end()) {
                        break;
                    }
                    byte controlSymbol;
                    if (byter_real_size == 0) {
                        controlSymbol = nextByte();
                        byter_real_size = controlSymbol;
                    }
                    else {
                        controlSymbol = byter_real_size;
                    }
                    byte[] symbol = new byte[symbolLength(controlSymbol)];
                    symbol[0] = controlSymbol;
                    boolean cut = false;
                    for (int i = 1; i < symbolLength(controlSymbol); i++){
                        if (end()) {
                            byter = symbol;
                            cut = true;
                        }
                        else {
                            if (!cut) {
                                symbol[i] = nextByte();
                            }
                        }
                    }
                    if (cut) {
                        break;
                    }
                    else {
                        s.append(new String(symbol, "UTF-8"));
                        byter_real_size = 0;
                    }
                }
                Sbuffer = new String(s);
            } catch (IOException e) {
                Tools.send(0002);
            }
        }
        return true;
    }

    public int nextInt() {
        StringBuilder returnString = new StringBuilder("");
        while (true) {
            if (currentString >= Sbuffer.length()) {
                if (!readBuffer()) {
                    Tools.send(0003);
                }
            }
            char character = Sbuffer.charAt(currentString);
            currentString++;
            if (Character.isDigit(character) || (character == '-' && returnString.length() == 0)) {
                returnString.append(character);
            } else {
                currentString--;
                try {
                    return Integer.parseInt(returnString.toString());
                } catch (NumberFormatException e){
                    Tools.send(0011);
                }
            }
        }
    }

    public String nextLine() {
        StringBuilder returnString = new StringBuilder("");
        while (true) {
            if (currentString >= Sbuffer.length()) {
                if (!readBuffer()) {
                    Tools.send(0003);
                }
            }
            char character = Sbuffer.charAt(currentString);
            currentString++;
            if (character == System.lineSeparator().charAt(0) || character == (char) 0) {
                currentString++;
                return returnString.toString();
            } else {
                returnString.append(character);
            }
        }
    }

    public String nextString() {
        StringBuilder returnString = new StringBuilder("");
        while (true) {
            if (currentString >= Sbuffer.length()) {
                if (!readBuffer()) {
                    Tools.send(0003);
                }
            }
            char character = Sbuffer.charAt(currentString);
            currentString++;
            if (Character.isLetter(character) || Tools.isApostrophe(character) || Tools.isDash(character)) {
                returnString.append(character);
            }
            else {
                return returnString.toString();
            }
        }
    }

    public void carriage(){
        currentString++;
    }

    public boolean hasNext() {
        if (currentString < Sbuffer.length() && Sbuffer.charAt(currentString) != (char) 0) {
            return true;
        } else {
            if (readBuffer()) {
                return true;
            } else {
                return false;
            }
        }
    }

    public boolean hasNextInt() {
        if (currentString >= Sbuffer.length()) {
            if (!readBuffer()) {
                return false;
            }
        }
        if ((char) Sbuffer.charAt(0) <= '9' && (char) Sbuffer.charAt(0) >= '0') {
            return true;
        } else {
            return false;
        }
    }
}
