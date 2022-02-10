package bean

import org.json.JSONArray

class ClosureTasks(j: JSONArray) {
    private val closureTasks: MutableList<ClosureTask> = mutableListOf()

    init {
        j.forEach {
            closureTasks.add(ClosureTask(it as JSONArray))
        }
    }

    fun run(functionals: Functionals): String {
        val sb = StringBuilder("Задания:\n")
        closureTasks.forEachIndexed { index, closureTask ->
            sb.appendLine("Задача $index:")
            sb.appendLine(closureTask.run(functionals))
        }
        return sb.toString()
    }


    operator fun iterator(): MutableIterator<ClosureTask> {
        return closureTasks.iterator()
    }

    fun contain(attributes: Attributes) {
        closureTasks.forEach {
            it.contains(attributes)
        }
    }
}