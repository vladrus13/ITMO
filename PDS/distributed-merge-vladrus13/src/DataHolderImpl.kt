import system.DataHolderEnvironment
import java.util.*

class DataHolderImpl<T : Comparable<T>>(
    private val keys: List<T>,
    private val dataHolderEnvironment: DataHolderEnvironment
) : DataHolder<T> {
    private var last = 0
    private var current = 0
    override fun checkpoint() {
        last = current
    }

    override fun rollBack() {
        current = last
    }

    override fun getBatch(): List<T> {
        var count = 0
        val returned = LinkedList<T>()
        while (count < dataHolderEnvironment.batchSize && current < keys.size) {
            returned.add(keys[current])
            current++
            count++
        }
        return returned
    }
}