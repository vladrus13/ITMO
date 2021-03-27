import kotlinx.atomicfu.atomic
import kotlinx.atomicfu.atomicArrayOfNulls
import java.util.*
import java.util.concurrent.locks.ReentrantLock

class FCPriorityQueue<E : Comparable<E>> {
    private val q = PriorityQueue<E>()
    private val size = 4 * Runtime.getRuntime().availableProcessors()
    private val combinerArray = atomicArrayOfNulls<Operation<E>>(size)
    private val lock = atomic<Boolean>(false)
    private val DEBUG = false

    private fun fortify(operation: Operation<E>): Operation<E> {
        when (operation.operation) {
            "POLL" -> operation.element = q.poll()
            "ADD" -> q.add(operation.element)
            else -> throw UnsupportedOperationException("Operation ${operation.operation} not supported")
        }
        return Operation(operation.element, "DONE")
    }

    private fun combinerWork() {
        if (!lock.compareAndSet(expect = false, update = true)) return
        if (DEBUG) println("EAT LOCK")
        for (i in 0 until size) {
            val operation = combinerArray[i]
            val real = operation.value ?: continue
            if (DEBUG) println("Took ${real.operation} from $i")
            if (real.operation == "POLL" || real.operation == "ADD") {
                if (combinerArray[i].compareAndSet(real, Operation(real.element, "PROGRESS"))) {
                    if (DEBUG) println("FORTIFY: ${real.operation}")
                    val result = fortify(real)
                    combinerArray[i].getAndSet(result)
                }
            }
        }
        if (DEBUG) println("DROP LOCK")
        lock.compareAndSet(expect = true, update = false)
    }

    private fun addOperation(operation: Operation<E>): E? {
        var realPosition = -1
        while (true) {
            if (realPosition == -1) {
                for (position in 0 until size) {
                    if (combinerArray[position].compareAndSet(null, operation)) {
                        realPosition = position
                        if (DEBUG) println("SET operation ${operation.operation} at $realPosition")
                        break
                    }
                }
            } else {
                if (!lock.value) {
                    combinerWork()
                }
                val result = combinerArray[realPosition].value
                    ?: throw IllegalStateException("Can't be null at combiner array")
                if (result.operation == "DONE") {
                    if (DEBUG) println("Remove: ${result.operation}")
                    combinerArray[realPosition].compareAndSet(result, null)
                    return result.element
                }
            }
        }
    }

    /**
     * Retrieves the element with the highest priority
     * and returns it as the result of this function;
     * returns `null` if the queue is empty.
     */
    fun poll(): E? {
        if (DEBUG) println("POOL come")
        val operation = Operation<E>(null, "POLL")
        return addOperation(operation)
    }

    /**
     * Returns the element with the highest priority
     * or `null` if the queue is empty.
     */
    fun peek(): E? {
        return q.peek()
    }

    /**
     * Adds the specified element to the queue.
     */
    fun add(element: E) {
        if (DEBUG) println("ADD COME")
        val operation = Operation(element, "ADD")
        addOperation(operation)
    }
}

class Operation<E>(var element: E?, val operation: String)