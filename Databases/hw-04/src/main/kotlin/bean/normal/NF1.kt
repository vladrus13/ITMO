package bean.normal

import bean.Attributes
import bean.Functionals
import bean.result.Result

class NF1 : NF() {
    override fun decompose(form: Pair<Attributes, Functionals>): Result<List<Pair<Attributes, Functionals>>> {
        val sb = StringBuilder()
        sb.appendLine("Рассмотрим отношение:").appendLine(toString(form))
        return Result(sb, listOf(Pair(form.first, functionalsCut(form.first.attributes, form.second))))
    }

}