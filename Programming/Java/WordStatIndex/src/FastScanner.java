import javax.xml.stream.events.Characters;
import java.io.*;
import java.lang.StringBuilder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class FastScanner {
    private static int CLUSTER = 134217728;
    private static byte[] buffer;
    public static String Sbuffer;
    private static File file;
    private static InputStream reader;
    private static int current;

    FastScanner(File f) {
        file = f;
        current = CLUSTER + 1;
        buffer = new byte[CLUSTER];
        try {
            reader = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            Tools.send(0001);
        }
    }

    FastScanner(String s) {
        file = null;
        current = CLUSTER + 1;
        buffer = new byte[CLUSTER];
        try {
            reader = new ByteArrayInputStream(s.getBytes());
        } catch (Exception e){
            // and what UTF-8?
        }
    }

    FastScanner(InputStream f) {
        buffer = new byte[CLUSTER];
        reader = f;
        current = CLUSTER + 1;
    }

    public void close() {
        current = CLUSTER + 1;
        buffer = new byte[CLUSTER];
        try {
            reader.close();
        } catch (IOException e) {
            Tools.send(0002);
        }
        file = null;
    }

    private static boolean readBuffer() {
        int numReader = 0;
        current = 0;
        buffer = new byte[CLUSTER];
        while (numReader == 0) {
            try {
                numReader = reader.read(buffer);
                Sbuffer = new String (buffer, StandardCharsets.UTF_8);
                CLUSTER = Sbuffer.length();
            } catch (IOException e) {
                Tools.send(0002);
            }
        }
        if (numReader == -1) {
            if (Sbuffer.charAt(0) == 0) {
                return false;
            }
        }
        return true;
    }

    public int nextInt() {
        StringBuilder returnString = new StringBuilder("");
        while (true) {
            if (current >= CLUSTER) {
                if (!readBuffer()) {
                    Tools.send(0003);
                }
            }
            char character = Sbuffer.charAt(current);
            current++;
            if (Character.isDigit(character) || (character == '-' && returnString.length() == 0)) {
                returnString.append(character);
            } else {
                current--;
                return Integer.parseInt(returnString.toString());
            }
        }
    }

    public String nextLine() {
        StringBuilder returnString = new StringBuilder("");
        while (true) {
            if (current >= CLUSTER) {
                if (!readBuffer()) {
                    Tools.send(0003);
                }
            }
            char character = Sbuffer.charAt(current);
            current++;
            if (character == System.lineSeparator().charAt(0) || character == (char) 0) {
                current++;
                return returnString.toString();
            } else {
                returnString.append(character);
            }
        }
    }

    public String nextString() {
        StringBuilder returnString = new StringBuilder("");
        while (true) {
            if (current >= CLUSTER) {
                if (!readBuffer()) {
                    Tools.send(0003);
                }
            }
            char character = Sbuffer.charAt(current);
            current++;
            if (Character.isLetter(character) || Tools.isApostrophe(character) || Tools.isDash(character)) {
                returnString.append(character);
            }
            else {
                return returnString.toString();
            }
        }
    }

    public void carriage(){
        current++;
    }

    public boolean hasNext() {
        if (current < CLUSTER && Sbuffer.charAt(current) != (char) 0) {
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
        if (current >= CLUSTER) {
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
