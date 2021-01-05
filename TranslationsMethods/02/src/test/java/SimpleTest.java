import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.junit.runners.MethodSorters;
import ru.parser.Parser;
import ru.parser.exception.ParseException;

import static org.junit.Assert.*;

@RunWith(JUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SimpleTest {

    private void simpleCheck(String s) {
        try {
            assertEquals(s, Parser.parse(s).toString());
        } catch (ParseException e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void test1_without_any() {
        simpleCheck("void test();");
    }

    @Test
    public void test2_one_variable() {
        simpleCheck("int fib(int n);");
    }

    @Test
    public void test3_long_name() {
        simpleCheck("string to_string(int calculated);");
    }

    @Test
    public void test4_digit_in_name() {
        simpleCheck("very_hard_type_1 very_hard_name_1(very_hard_type_2 vary_hard_name_2);");
    }

    @Test
    public void test5_references() {
        simpleCheck("a b(a b, a **b, a ********************************b);");
    }

    @Test
    public void test6_unstable_spaces() {
        String answer = "a** a(a *a, a *a);";
        String test = "\t \ta\t \t*\t \t*\t \t\t \t\t \ta\t \t\t \t(\t \t\t \ta\t \t\t \t*\t \ta\t \t,\t \t\t \ta\t \t*\t \t\t \ta\t \t)\t \t;";
        try {
            assertEquals(answer, Parser.parse(test).toString());
        } catch (ParseException e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void test1_modification() {
        simpleCheck("int a(int);");
    }

    @Test
    public void test2_modification() {
        simpleCheck("int a(int, int, int, int);");
    }

    @Test
    public void test3_modification() {
        simpleCheck("int a(int, int a, int b, int);");
    }
}
