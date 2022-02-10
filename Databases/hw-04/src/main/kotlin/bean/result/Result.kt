package bean.result

data class Result<T>(val stringBuilder: StringBuilder = StringBuilder(),
                     var result : T? = null) {
}