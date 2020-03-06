import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class RunJS {
    @SuppressWarnings("MethodMayBeStatic")
    public static class IO {
        private final ScriptEngine engine;
        public IO(final ScriptEngine engine) {
            this.engine = engine;
        }

        public void print(final String message) {
            System.out.print(message);
        }

        public void println(final String message) {
            System.out.println(message);
        }

        public void include(final String file) throws IOException, ScriptException {
            engine.eval(new FileReader(file, StandardCharsets.UTF_8));
        }
    }

    public static void main(final String[] args) throws ScriptException, IOException {
        final ScriptEngine engine = new ScriptEngineManager().getEngineByName("Graal.js");
        if (engine == null) {
            System.err.println("Graal.js not found");
            System.err.println("Use the following command line to run RunJS:");
            System.err.println("java -XX:+UnlockExperimentalVMOptions -XX:+EnableJVMCI --module-path=graal --upgrade-module-path=graal/compiler.jar -cp . RunJS");
            return;
        }

        final IO io = new IO(engine);
        engine.put("io", io);
        engine.eval("var global = this;");
        engine.eval("var println = function() { io.println(Array.prototype.map.call(arguments, String).join(' ')); };");
        engine.eval("var print   = function() { io.print  (Array.prototype.map.call(arguments, String).join(' ')); };");
        engine.eval("var include = function(file) { io.include(file); }");
        io.include("examples.js");
    }
}
