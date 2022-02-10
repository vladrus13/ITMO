package bean.normal

import bean.Attributes
import bean.Functional
import bean.Functionals
import bean.result.Result
import bean.result.Utils

abstract class NF {

    companion object {

        fun removeTrivials(list: List<Functional>): List<Functional> = list.filter {
            !isTrivial(it)
        }

        fun toStringFS(functionals: Functionals) : String {
            val nonTrivials = Functionals(removeTrivials(functionals.functionals.toList()))
            return if (nonTrivials.functionals.size > 5) "\nСлишком много фз" else "\nФЗ:" + nonTrivials.toArrayString()
        }

        fun toString(form: List<Pair<Attributes, Functionals>>): String =
            form.joinToString(separator = "\n") {

                "\nАттрибуты\n" + it.first.attributes.joinToString(separator = ", ", prefix = "{", postfix = "}") +
                        "\nКлючи:\n" + Utils.getKeys(it.second.getKeys(it.first).result!!) +
                        toStringFS(it.second)
            }

        fun toString(form: Pair<Attributes, Functionals>): String =
            form.first.attributes.joinToString(separator = ", ", prefix = "(", postfix = ")\n") +
                    toStringFS(form.second)

        fun isTrivial(functional: Functional): Boolean {
            if (functional.from.containsAll(functional.to)) return true
            return false
        }

        fun functionalsCut(attributes: Set<String>, functionals: Functionals): Functionals {
            val returned = functionals.functionals.map {
                Functional(it.from.toSet(), it.to.toSet())
            }
            return Functionals(returned.mapNotNull {
                if (it.from.any { it1 -> !attributes.contains(it1) }) {
                    null
                } else {
                    val f = Functional(it.from, it.to.filter { it1 -> attributes.contains(it1) }.toSet())
                    f
                    /*
                    if (isTrivial(f)) {
                        null
                    } else {
                        f
                    }*/
                }
            })
        }
    }

    fun decompose(form: List<Pair<Attributes, Functionals>>): Result<List<Pair<Attributes, Functionals>>?> {
        val sb = StringBuilder()
        return Result(sb, form.flatMap {
            val result = decompose(it)
            sb.appendLine(result.stringBuilder)
            result.result!!
        })
    }


    abstract fun decompose(form: Pair<Attributes, Functionals>): Result<List<Pair<Attributes, Functionals>>>
}