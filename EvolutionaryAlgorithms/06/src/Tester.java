import ru.vladrus13.system.result.Table;

import java.nio.file.Path;

public class Tester {
    public static void main(String[] args) {
        Test test = new Tests();
        Table table = test.test();
        table.write(Path.of("results.html"));
    }
}
