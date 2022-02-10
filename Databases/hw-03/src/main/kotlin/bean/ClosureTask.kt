package bean

import org.json.JSONArray
import java.util.HashSet

class ClosureTask {
    constructor(j: JSONArray) {
        current = mutableSetOf()
        j.forEach {
            current.add(it as String)
        }
    }

    constructor(j : Set<String>) {
        current = j.toMutableSet()
    }

    val current : MutableSet<String>

    private fun toString(set: MutableSet<String>): String {
        return set.sorted().joinToString(separator = ", ", prefix = "{", postfix = "}")
    }

    fun toStringBuilder(functionals: Functionals): StringBuilder {
        return getClosure(functionals).first
    }

    fun run(functionals: Functionals): String {
        return getClosure(functionals).first.toString()
    }

    fun getRealClosure(functionals: Functionals) : List<String> {
        return getClosure(functionals).second
    }

    fun equals(functionals1: Functionals, functionals2: Functionals) : Boolean {
        val a1 = getRealClosure(functionals1)
        val a2 = getRealClosure(functionals2)
        return a1.containsAll(a2) && a2.containsAll(a1)
    }

    private fun getClosure(functionals: Functionals, isShort: Boolean = false) : Pair<StringBuilder, List<String>> {
        val sb = StringBuilder()
        val current = HashSet(current)
        sb.appendLine(toString(current))
        var isNewGet = true
        while (isNewGet) {
            isNewGet = false
            for (functional in functionals) {
                if (current.containsAll(functional.from) && functional.to.any { !current.contains(it) }) {
                    if (!isShort) {
                        sb.append("Run rule:\n")
                            .append(functional.from.joinToString(separator = ", ", prefix = "[", postfix = "]"))
                            .append(" -> ")
                            .append(functional.to.joinToString(separator = ", ", prefix = "[", postfix = "]"))
                            .append("\n")
                    }
                    current.addAll(functional.to)
                    sb.appendLine(toString(current))
                    isNewGet = true
                    break
                }
            }
        }
        return Pair(sb, current.toList())
    }

    fun contains(attributes: Attributes) {
        check(attributes.attributes.containsAll(current))
    }
}