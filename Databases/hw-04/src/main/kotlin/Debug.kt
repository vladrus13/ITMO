import bean.Attributes
import bean.Functional
import bean.Functionals
import bean.closures.Closure
import bean.normal.NF

fun main() {
    val attributes = Attributes(listOf("StudentId", "CourseId", "LecturerId", "Mark"))
    val functionals = Functionals(listOf(
        Functional(
            from = setOf("StudentId", "CourseId"),
            to = setOf("LecturerId", "Mark")
        )
    ))
    functionals.contain(attributes)
    val result = Closure.getClosureFunction(attributes.attributes.toList(), functionals).result!!
    println(Functionals(NF.removeTrivials(result.functionals.toList())).toArrayString())
}