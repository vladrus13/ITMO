package bean.result

import bean.Functional

class Utils {
    companion object {
        fun <T> getStringOfArray(list : Collection<T>, transformer : (T) -> String) : String {
            return list.joinToString(
                separator = ", ",
                prefix = "{",
                postfix = "}",
                transform = transformer)
        }

        fun getKeys(keys : Set<List<String>>) : String {
            return getStringOfArray(keys) { getStringOfList(it) }
        }

        fun getStringOfArray(list : Collection<String>) : String {
            return list.joinToString(
                separator = ", ",
                prefix = "{",
                postfix = "}")
        }

        fun <T> getStringOfList(list : Collection<T>, transformer : (T) -> String) : String {
            return list.joinToString(
                separator = ", ",
                prefix = "[",
                postfix = "]",
                transform = transformer)
        }

        fun getStringOfList(list : Collection<String>) : String {
            return list.joinToString(
                separator = ", ",
                prefix = "[",
                postfix = "]")
        }
    }
}