package bean

import bean.closures.Closure
import bean.normal.NF
import bean.normal.NF1
import bean.normal.NF2
import bean.normal.NF3
import org.json.JSONObject
import java.nio.file.Files
import java.nio.file.Path

class Task(i: JSONObject) {
    val attributes: Attributes
    val functionals: Functionals

    init {
        attributes = Attributes(i.getJSONArray("attributes"))
        functionals = Functionals(i.getJSONArray("functional"))
    }

    fun check() {
        functionals.contain(attributes)
    }

    fun execute() {
        Files.newBufferedWriter(Path.of("output.txt")).use {
            check()
            it.write("ФЗ:")
            it.newLine()
            it.write(functionals.toFullString(true))
            it.newLine()

            val keysResult = functionals.getKeys(attributes)

            it.newLine()
            it.write("\nКлючи:")
            it.newLine()
            it.write(keysResult.result!!.joinToString(separator = "\n", postfix = "\n") { it1 ->
                it1.joinToString(separator = ", ")
            })
            it.newLine()


            val closureX = Closure.getClosureFunction(attributes.attributes.toList(), functionals)
            /*
            it.write(closureX.result!!.functionals
                .joinToString(separator = "\n") { it1 ->
                    it1.toString()
                })
            it.newLine()*/

            val closure = closureX.result!!

            val resultNF1 = NF1().decompose(listOf(Pair(attributes, closure)))
            it.newLine()
            it.write("--------------------- NF1 ----------------- Talk")
            it.newLine()
            it.write(resultNF1.stringBuilder.toString())
            it.newLine()
            it.write("--------------------- NF1 ----------------- Result")
            it.newLine()
            val nf1 = resultNF1.result!!
            it.write(NF.toString(nf1))
            it.newLine()

            val resultNF2 = NF2().decompose(nf1)
            it.newLine()
            it.write("--------------------- NF2 ----------------- Talk")
            it.newLine()
            it.write(resultNF2.stringBuilder.toString())
            it.newLine()
            it.write("--------------------- NF2 ----------------- Result")
            it.newLine()
            val nf2 = resultNF2.result!!
            it.write(NF.toString(nf2))
            it.newLine()

            val resultNF3 = NF3().decompose(nf2)
            it.newLine()
            it.write("--------------------- NF3 ----------------- Talk")
            it.newLine()
            it.write(resultNF3.stringBuilder.toString())
            it.newLine()
            it.write("--------------------- NF3 ----------------- Result")
            it.newLine()
            val nf3 = resultNF3.result!!
            it.write(NF.toString(nf3))
            it.newLine()
        }
    }
}