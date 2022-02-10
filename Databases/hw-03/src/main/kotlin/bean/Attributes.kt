package bean

import org.json.JSONArray

class Attributes(s: JSONArray) {
    val attributes : MutableSet<String> = mutableSetOf()

    init {
        s.forEach {
            attributes.add(it.toString())
        }
    }

    operator fun iterator() : MutableIterator<String> {
        return attributes.iterator()
    }
}