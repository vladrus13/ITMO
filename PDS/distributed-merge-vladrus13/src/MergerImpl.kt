import system.MergerEnvironment

class MergerImpl<T : Comparable<T>>(
    private val mergerEnvironment: MergerEnvironment<T>,
    prevStepBatches: Map<Int, List<T>>?
) : Merger<T> {

    private val batches : HashMap<Int, List<T>> = getAll(prevStepBatches)

    private fun getAll(last: Map<Int, List<T>>?): HashMap<Int, List<T>> {
        val returned : HashMap<Int, List<T>> = HashMap()
        if (last != null) {
            for (it in last) {
                returned[it.key] = it.value
            }
        } else {
            for (i in 0 until mergerEnvironment.dataHoldersCount) {
                returned[i] = mergerEnvironment.requestBatch(i)
            }
        }
        return returned
    }

    override fun mergeStep(): T? {
        var minima : T? = null
        var numberMinima : Int = -1
        for (it in batches) {
            if (minima == null || it.value.first() < minima) {
                minima = it.value.first()
                numberMinima = it.key
            }
        }
        if (numberMinima != -1) {
            var us = batches[numberMinima].orEmpty()
            us = us.subList(1, us.size)
            if (us.isEmpty()) {
                us = mergerEnvironment.requestBatch(numberMinima)
                if (us.isEmpty()) {
                    batches.remove(numberMinima)
                } else {
                    batches[numberMinima] = us
                }
            } else {
                batches[numberMinima] = us
            }
        }
        return minima
    }

    override fun getRemainingBatches(): Map<Int, List<T>> {
        return batches
    }
}