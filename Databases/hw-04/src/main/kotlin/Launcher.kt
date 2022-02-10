import bean.Task
import org.json.JSONObject
import java.nio.file.Files
import java.nio.file.Path

fun main() {
    val task = Task(
        JSONObject(
            Files.newBufferedReader(
                Path.of("src").resolve("main").resolve("resources").resolve("input.json")
            )
                .readText()
        )
    )
    task.execute()
}