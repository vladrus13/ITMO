package bean.normal

import bean.Attributes
import bean.Functionals
import bean.result.Result
import bean.result.Utils

class NF3 : NF() {
    override fun decompose(form: Pair<Attributes, Functionals>): Result<List<Pair<Attributes, Functionals>>> {
        val sb = StringBuilder()
        val forms = mutableListOf(form)
        var isNew = true
        while (isNew) {
            isNew = false
            loop@ for (newForm in forms) {
                sb.appendLine("Рассмотрим отношение:").appendLine(toString(newForm))
                val attributes = newForm.first
                val functionals = newForm.second
                val keys = functionals.getKeys(attributes).result!!
                sb.append("Ключи:").appendLine(Utils.getKeys(keys))
                for (attribute in attributes) {
                    var isKey = false
                    for (key in keys) {
                        isKey = isKey or key.contains(attribute)
                    }
                    if (!isKey) {
                        var isValue = false
                        for (functional in functionals) {
                            if (functional.to.contains(attribute)) {
                                isValue = isValue or keys.any {
                                    it.containsAll(functional.from)
                                }
                            }
                        }
                        if (!isValue) {
                            for (functional in functionals) {
                                if (functional.to.contains(attribute)) {
                                    sb.appendLine("Аттрибут не зависит от ключей: ").appendLine(attribute)
                                    forms.remove(newForm)
                                    val newAttributes = attributes.attributes.toMutableSet().apply { remove(attribute) }
                                    val newFunctionals = functionalsCut(newAttributes, functionals)
                                    forms.add(Pair(Attributes(newAttributes), newFunctionals))
                                    val secondaryAttributes = functional.from + attribute
                                    forms.add(Pair(Attributes(secondaryAttributes), functionalsCut(secondaryAttributes, functionals)))
                                    isNew = true
                                    break@loop
                                }
                            }
                        }
                    }
                }
            }
        }
        return Result(sb, forms)
    }
}