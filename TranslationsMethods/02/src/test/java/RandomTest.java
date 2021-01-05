
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.junit.runners.MethodSorters;
import ru.parser.Parser;
import ru.parser.exception.ParseException;

import java.util.Random;

import static org.junit.Assert.*;

@RunWith(JUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RandomTest {

    final Random random = new Random();
    private final String alphabet = "abcdefghijklmnopqrstuvwxyz";
    private final String bigAlphabet = alphabet + alphabet.toUpperCase();
    private final String all = bigAlphabet + "0123456789" + "_";

    private void simpleCheck(String s) {
        try {
            assertEquals(s, Parser.parse(s).toString());
        } catch (ParseException e) {
            e.printStackTrace();
            fail();
        }
    }

    private char generateAlphabetChar() {
        return bigAlphabet.charAt(random.nextInt(bigAlphabet.length()));
    }

    private char generateRandomSymbol() {
        return all.charAt(random.nextInt(all.length()));
    }

    private String generateName() {
        StringBuilder s = new StringBuilder();
        int size = random.nextInt(1000) + 1;
        s.append(generateAlphabetChar());
        for (int i = 1; i < size; i++) {
            s.append(generateRandomSymbol());
        }
        return s.toString();
    }

    @Test
    public void test_1_many_references() {
        simpleCheck("int" + "*".repeat(random.nextInt(1000)) + " hello(int " + "*".repeat(random.nextInt(999)) + "a);");
    }

    @Test
    public void test_2_variables() {
        StringBuilder s = new StringBuilder();
        s.append(generateName());
        s.append("*".repeat(random.nextInt(10)));
        s.append(" ");
        s.append(generateName());
        s.append("(");
        int size = random.nextInt(1000);
        if (size != 0) {
            while (size > 1) {
                s.append(generateName());
                s.append(' ');
                s.append("*".repeat(10));
                s.append(generateName());
                s.append(", ");
                size--;
            }
            s.append(generateName());
            s.append(' ');
            s.append("*".repeat(10));
            s.append(generateName());
        }
        s.append(");");
        simpleCheck(s.toString());
    }
}
