package bean

import org.json.JSONArray

class Attributes {
    constructor(s: JSONArray) {
        this.attributes = mutableSetOf()
        s.forEach {
            attributes.add(it.toString())
        }
    }

    constructor(s : Collection<String>) {
        attributes = mutableSetOf<String>().apply { addAll(s) }
    }

    val attributes : MutableSet<String>

    operator fun iterator() : MutableIterator<String> {
        return attributes.iterator()
    }
}