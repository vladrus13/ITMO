package bean

import org.json.JSONArray
import org.json.JSONObject

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

    fun removeFunctionalUseless(stringBuilder: StringBuilder) {
        var isNewFunctionals = true
        while (isNewFunctionals) {
            isNewFunctionals = false
            val newFunctionals: MutableList<Functional> = mutableListOf()
            val toRemoveFunctionals: MutableList<Functional> = mutableListOf()
            functionals.forEach {
                do {
                    val new = it.removeUseless(functionals.toMutableList().apply { remove(it) }, stringBuilder)
                    if (new != null) {
                        newFunctionals.add(new)
                        toRemoveFunctionals.add(it)
                        isNewFunctionals = true
                    }
                } while (new != null)
            }
            functionals.addAll(newFunctionals)
            functionals.removeAll(toRemoveFunctionals)
        }
    }

    fun removeUseless(stringBuilder: StringBuilder) {
        var isNewFunctionals = true
        while (isNewFunctionals) {
            isNewFunctionals = false
            for (it in functionals) {
                if (ClosureTask(it.from).getRealClosure(
                        Functionals(functionals.toMutableList().apply { remove(it) })
                    ).containsAll(it.to)
                ) {
                    stringBuilder.appendLine("Remove rule: ${it.toArrayString()}")
                    functionals.remove(it)
                    isNewFunctionals = true
                    break
                }
            }
        }
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

    fun getKeys(
        list: Set<String>,
        attributes: Attributes,
        sb: StringBuilder? = null,
        cantBreak: List<String>,
        used: MutableSet<Set<String>>
    ): Set<List<String>> {
        var flag = false
        val returned: MutableSet<List<String>> = mutableSetOf()
        val newCantBreak = cantBreak.toMutableList()
        list.forEach {
            if (!cantBreak.contains(it)) {
                val trying = list.minus(it)
                var newSB = sb
                if (used.contains(trying)) {
                    newSB = null
                }
                newSB?.appendLine("Попытка убрать ключ: $it")
                used.add(trying)
                val closure = ClosureTask(trying).getRealClosure(this)
                newSB?.appendLine("Получено замыкание: ${closure.joinToString(separator = ", ")}")
                if (closure.containsAll(attributes.attributes) && attributes.attributes.containsAll(closure)) {
                    newSB?.appendLine("Оно полное! Убираем ключ $it. Подключ: ${trying.joinToString(separator = ", ")}")
                    returned.addAll(getKeys(trying, attributes, newSB, newCantBreak.toList(), used))
                    flag = true
                } else {
                    newSB?.appendLine("Оно не полное. Мы не будем убирать этот ключ")
                    newCantBreak.add(it)
                }
            }
        }
        if (!flag) {
            returned.add(list.toList())
            sb?.appendLine("У нас есть ключ ${list.joinToString(separator = ", ")}")
        }
        return returned
    }

    fun runKeys(attributes: Attributes): String {
        val sb = StringBuilder()
        getKeys(attributes.attributes, attributes, sb, emptyList(), mutableSetOf())
        return sb.toString()
    }

    fun getKeys(attributes: Attributes): Set<List<String>> {
        return getKeys(attributes.attributes, attributes, null, emptyList(), mutableSetOf())
    }
}