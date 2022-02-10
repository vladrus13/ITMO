package bean

import org.json.JSONObject

class Functional {
    constructor(j: JSONObject) {
        this.from = mutableSetOf()
        this.to = mutableSetOf()
        val fromJson = j.getJSONArray("from")
        val toJson = j.getJSONArray("to")
        fromJson.forEach {
            from.add(it.toString())
        }
        toJson.forEach {
            to.add(it.toString())
        }
        this.why = if (j.has("why")) j.getString("why") else null
    }

    constructor(from: Set<String>, to: Set<String>) {
        this.from = from.toMutableSet()
        this.to = to.toMutableSet()
    }

    val from: MutableSet<String>
    val to: MutableSet<String>
    private var why : String? = null

    fun toStringBuilder(): StringBuilder {
        return StringBuilder()
            .append("\t").append(toString(from, to))
    }

    fun toArrayString(): String {
        return toString(from, to)
    }

    override operator fun equals(other: Any?): Boolean {
        return if (other is Functional) {
            (other.from.containsAll(from) && from.containsAll(other.from) &&
                    other.to.containsAll(to) && to.containsAll(other.to))
        } else {
            false
        }
    }

    fun splitting(): List<Functional> {
        return to.map {
            Functional(from.toSet(), mutableSetOf(it))
        }.toList()
    }

    private fun toString(it: Set<String>): String {
        return it.joinToString(separator = ", ", prefix = "[", postfix = "]")
    }

    private fun toString(from: Set<String>, to: Set<String>): String {
        return "{" + toString(from) + " -> " + toString(to) + "}"
    }

    override fun toString(): String {
        return toString(from, to)
    }

    fun toFullString(isWhy : Boolean = true) : String {
        return from.joinToString(separator = ", ") + " -> " + to.joinToString(separator = ", ") +
                if (isWhy && why != null) "\n-- $why" else ""
    }

    fun contain(attributes: Attributes) {
        check(attributes.attributes.containsAll(from))
        check(attributes.attributes.containsAll(to))
    }

    override fun hashCode(): Int {
        var result = from.hashCode()
        result = 31 * result + to.hashCode()
        return result
    }
}