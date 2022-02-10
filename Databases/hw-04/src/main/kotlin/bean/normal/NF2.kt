package bean.normal

import bean.Attributes
import bean.Functionals
import bean.result.Result
import bean.result.Utils

class NF2 : NF() {
    override fun decompose(form: Pair<Attributes, Functionals>): Result<List<Pair<Attributes, Functionals>>> {
        val sb = StringBuilder()
        var isNew = true
        val newList = mutableListOf(form)
        while (isNew) {
            isNew = false
            loop@ for (newRatio in newList) {
                sb.appendLine("Рассмотрим отношение:").appendLine(toString(newRatio))
                val attributes = newRatio.first
                val functionals = newRatio.second
                val keys = functionals.getKeys(attributes).result!!
                sb.append("Ключи:").appendLine(Utils.getKeys(keys))
                for (functional in functionals) {
                    if (isTrivial(functional)) continue
                    for (key in keys) {
                        if (key.containsAll(functional.from) && !functional.from.containsAll(key)) {
                            val newAttributes = attributes.attributes.toMutableSet().apply { removeAll(functional.to) }
                            val newFunctionals = functionalsCut(newAttributes, functionals)
                            val secondaryAttributes = functional.from + functional.to
                            if (newAttributes.size > 1 && secondaryAttributes.size > 1) {
                                sb.appendLine("Вижу конфликт с зависимостью: ").appendLine(functional.toArrayString())
                                newList.remove(newRatio)
                                newList.add(
                                    Pair(
                                        Attributes(secondaryAttributes),
                                        functionalsCut(secondaryAttributes, functionals)
                                    )
                                )
                                newList.add(Pair(Attributes(newAttributes), newFunctionals))
                                sb.appendLine("Разделение:").appendLine(
                                    "${Utils.getStringOfList(attributes.attributes)} => ${
                                        Utils.getStringOfList(newAttributes)
                                    }; ${Utils.getStringOfList(secondaryAttributes)}"
                                )
                                isNew = true
                                break@loop
                            }
                        }
                    }
                }
            }
        }
        return Result(sb, newList)
    }
}