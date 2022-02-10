package bean

import org.json.JSONObject
import java.nio.file.Files
import java.nio.file.Path

class Task(i: JSONObject) {
    val attributes : Attributes
    val functionals : Functionals
    val closureTasks : ClosureTasks

    init {
        attributes = Attributes(i.getJSONArray("attributes"))
        functionals = Functionals(i.getJSONArray("functional"))
        closureTasks = ClosureTasks(i.getJSONArray("task_closure"))
    }

    fun check() {
        functionals.contain(attributes)
        closureTasks.contain(attributes)
    }

    fun execute() {
        Files.newBufferedWriter(Path.of("output.txt")).use {
            check()
            it.write("ФЗ:")
            it.newLine()
            it.write(functionals.toFullString(true))
            it.newLine()

            it.write("\nПолучение ключей:")
            it.newLine()
            it.write(functionals.runKeys(attributes))

            it.newLine()
            it.write("\nКлючи:")
            it.newLine()
            it.write(functionals.getKeys(attributes).joinToString(separator = "\n", postfix = "\n") { it1 ->
                it1.joinToString(separator = ", ")
            })
            it.newLine()

            it.write(closureTasks.run(functionals))
            it.newLine()

            val splitted = functionals.splitting()
            val newFunctional = Functionals(splitted)
            it.write("\nПосле этапа сплита:")
            it.newLine()
            it.write(newFunctional.toFullString(false))
            it.newLine()
            var sb = StringBuilder()
            newFunctional.removeFunctionalUseless(sb)
            it.write(sb.toString())
            it.newLine()
            it.write("\nПосле этапа удаления атрибутов:")
            it.newLine()
            it.write(newFunctional.toFullString(false))
            it.newLine()
            sb = StringBuilder()
            newFunctional.removeUseless(sb)
            it.write(sb.toString())
            it.newLine()
            it.write("\nПосле этапа удаления правил:")
            it.newLine()
            it.write(newFunctional.toFullString(false))
            it.newLine()
        }
    }
}