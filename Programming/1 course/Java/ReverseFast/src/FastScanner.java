import javax.sql.rowset.serial.SQLInputImpl;
import java.io.*;
import java.lang.StringBuilder;

public class FastScanner {
    private static byte[] buffer;
    private static File file;
    private static InputStream reader;
    private static int iter;

    FastScanner(File f) {
        file = f;
        buffer = new byte[256];
        try {
            reader = new FileInputStream(f);
        } catch (FileNotFoundException e) {
            System.out.println("FileNotFoundERORRR");
            System.exit(0);
        }
    }

    FastScanner(InputStream f) {
        buffer = new byte[256];
        reader = f;
        iter = 0;
    }

    private static boolean readBuffer() {
        int numReader = 0;
        iter = 0;
        while (numReader == 0) {
            try {
                numReader = reader.read(buffer);
            } catch (IOException e) {
                System.out.print("Однажды ЭХ поспорил... У вас нет доступа к файлу, или он был внезапно удален");
                System.exit(0);
            }
        }
        if (numReader == -1) {
            if (buffer[0] == 0) {
                return false;
            }
        }
        return true;
    }

    /*
        public int nextInt() {
            if (iter == 0) {
                if (!readBuffer()) {
                    System.err.println("GoToEndBuffer");
                    System.exit(0);
                }
            }
            int returnInt = 0, koff = 1;
            while (true) {
                char digit = (char) buffer.charAt(0);
                buffer.deleteCharAt(0);
                if (digit == '-' && returnInt == 0) {
                    koff = -1;
                }
                if (Character.isDigit(digit)) {
                    returnInt = returnInt * 10 + (digit - '0');
                } else {
                    return returnInt * koff;
                }
                if (buffer.length() == 0) {
                    readBuffer();
                }
            }
        }
    */
    public String nextLine() {
        StringBuilder returnString = new StringBuilder();
        while (true) {
            if (iter >= 256) {
                if (!readBuffer()) {
                    System.err.println("GoToEndBuffer");
                    return returnString.toString();
                }
            }
            char character = (char) buffer[iter];
            iter++;
            if (character == System.lineSeparator().charAt(0)) {
                iter++;
                return returnString.toString();
            } else {
                returnString.append(character);
            }
        }
    }

    public boolean hasNext() {
        if (iter != 256) {
            return true;
        } else {
            if (readBuffer()) {
                return true;
            } else {
                return false;
            }
        }
    }
/*
    public boolean hasNextInt() {
        if (buffer.length() == 0) {
            if (readBuffer()) {
                return true;
            } else {
                return false;
            }
        } else {
            if ((char) buffer.charAt(0) <= '9' && (char) buffer.charAt(0) >= '0') {
                return true;
            } else {
                return false;
            }
        }

    }
    */
}
