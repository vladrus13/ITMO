package bean

import bean.closures.Closure
import org.json.JSONArray
import org.json.JSONObject
import bean.result.Result

class Functionals {
    constructor(j: JSONArray) {
        this.functionals = mutableSetOf()
        j.forEach {
            functionals.add(Functional(it as JSONObject))
        }
    }

    constructor(j: List<Functional>) {
        this.functionals = j.toMutableSet()
    }

    val functionals: MutableSet<Functional>

    fun toStringBuilder(): StringBuilder {
        val sb = StringBuilder("Functionals\n")
        functionals.forEach {
            sb.append(it.toStringBuilder()).append("\n")
        }
        return sb
    }

    fun toArrayString(): String {
        return functionals.joinToString(separator = "\n", prefix = "{\n", postfix = "\n}") {
            it.toArrayString()
        }
    }

    operator fun iterator(): Iterator<Functional> {
        return functionals.iterator()
    }

    fun splitting(): List<Functional> {
        val list = mutableListOf<Functional>()
        functionals.forEach {
            list.addAll(it.splitting())
        }
        return list
    }

    fun contain(attributes: Attributes) {
        functionals.forEach {
            it.contain(attributes)
        }
    }

    fun toFullString(isWhy: Boolean = true): String {
        return functionals.joinToString(separator = "\n") {
            it.toFullString(isWhy)
        }
    }

    private fun getKeys(
        list: Set<String>,
        attributes: Attributes,
        cantBreak: List<String>,
        used: MutableMap<Set<String>, Boolean>
    ): Result<Set<List<String>>> {
        var flag = false
        val sb = StringBuilder()
        val returned: MutableSet<List<String>> = mutableSetOf()
        val newCantBreak = cantBreak.toMutableList()
        list.forEach {
            if (!cantBreak.contains(it)) {
                val trying = list.minus(it)
                sb.appendLine("Попытка убрать ключ: $it")
                if (!used.contains(trying)) {
                    val closure = Closure.getClosureAttributes(trying.toList(), this).result!!
                    sb.appendLine("Получено замыкание: ${closure.joinToString(separator = ", ")}")
                    if (closure.containsAll(attributes.attributes) && attributes.attributes.containsAll(closure)) {
                        sb.appendLine("Оно полное! Убираем ключ $it. Подключ: ${trying.joinToString(separator = ", ")}")
                        val result = getKeys(trying, attributes, newCantBreak.toList(), used)
                        sb.append(result.stringBuilder)
                        returned.addAll(result.result!!)
                        flag = true
                    } else {
                        sb.appendLine("Оно не полное. Мы не будем убирать этот ключ")
                        newCantBreak.add(it)
                    }
                    used[trying] = flag;
                } else {
                    flag = flag or used[trying]!!
                }
            }
        }
        if (!flag) {
            returned.add(list.toList())
            sb.appendLine("У нас есть ключ ${list.joinToString(separator = ", ")}")
        }
        return Result(sb, returned)
    }

    fun getKeys(attributes: Attributes): Result<Set<List<String>>> {
        return getKeys(attributes.attributes, attributes, emptyList(), mutableMapOf())
    }
}