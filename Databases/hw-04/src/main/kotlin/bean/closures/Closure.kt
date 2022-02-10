package bean.closures

import bean.Functional
import bean.Functionals
import bean.result.Result
import bean.result.Utils

class Closure {
    companion object {
        fun getClosureAttributes(attributes: List<String>, functionals: Functionals): Result<List<String>> {
            val sb = StringBuilder()
            val current = attributes.toMutableSet()
            sb.append(Utils.getStringOfArray(current))
            var isNewGet = true
            while (isNewGet) {
                isNewGet = false
                for (functional in functionals) {
                    if (current.containsAll(functional.from) && functional.to.any { !current.contains(it) }) {
                        sb.append("Использовано правило:\n")
                            .append(functional.from.joinToString(separator = ", ", prefix = "[", postfix = "]"))
                            .append(" -> ")
                            .append(functional.to.joinToString(separator = ", ", prefix = "[", postfix = "]"))
                            .append("\n")
                        current.addAll(functional.to)
                        sb.appendLine(Utils.getStringOfArray(current))
                        isNewGet = true
                        break
                    }
                }
            }
            return Result(sb, current.toList())
        }

        private fun getReflexion(answer: MutableList<Functional>, list: MutableList<Int>, attributes: List<String>) {
            if (list.size == attributes.size) {
                answer.add(
                    Functional(
                        list.mapIndexed { index, i -> if (i == 1 || i == 2) attributes[index] else null }.filterNotNull().toSet(),
                        list.mapIndexed { index, i -> if (i == 2) attributes[index] else null }.filterNotNull().toSet()
                    )
                )
            } else {
                getReflexion(answer, list.toMutableList().apply { add(0) }.toMutableList(), attributes)
                getReflexion(answer, list.toMutableList().apply { add(1) }.toMutableList(), attributes)
                getReflexion(answer, list.toMutableList().apply { add(2) }.toMutableList(), attributes)
            }
        }

        private fun getReflexion(attributes: List<String>): Functionals {
            val list = mutableListOf<Int>()
            val answer = mutableListOf<Functional>()
            getReflexion(answer, list, attributes)
            return Functionals(answer)
        }

        fun getClosureFunction(attributes: List<String>, functionals: Functionals): Result<Functionals> {
            val sb = StringBuilder()
            val current = functionals.functionals.toMutableSet()
            sb.append(Utils.getStringOfArray(current) { it.toString() })
            var isNewGet = true

            current.addAll(getReflexion(attributes).functionals)

            while (isNewGet) {
                isNewGet = false


                for (attr in attributes) {

                    for (functional in functionals) {
                        isNewGet = isNewGet or current.add(
                            Functional(
                                functional.from.toMutableList().apply { add(attr) }.toSet(),
                                functional.to.toMutableList().apply { add(attr) }.toSet()
                            )
                        )
                    }
                }

                for (functional1 in functionals) {
                    for (functional2 in functionals) {
                        if (functional1.to.containsAll(functional2.from)) {
                            isNewGet = isNewGet or current.add(
                                Functional(
                                    functional1.from.toSet(),
                                    functional2.to.toSet()
                                )
                            )
                        }
                    }
                }

                for (functional1 in functionals) {
                    for (functional2 in functionals) {
                        isNewGet = isNewGet or current.add(
                            Functional(
                                (functional1.from + functional2.from).toSet(),
                                (functional1.to + functional2.to).toSet()
                            )
                        )
                    }
                }
            }
            return Result(sb, Functionals(current.toList()))
        }
    }
}