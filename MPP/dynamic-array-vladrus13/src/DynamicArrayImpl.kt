import kotlinx.atomicfu.AtomicBoolean
import kotlinx.atomicfu.atomic
import kotlinx.atomicfu.atomicArrayOfNulls
import java.util.concurrent.atomic.AtomicInteger

class DynamicArrayImpl<E> : DynamicArray<E> {
    private val core = atomic(Core<E>(INITIAL_CAPACITY))
    private val taken: AtomicBoolean = atomic(false)

    override fun get(index: Int): E {
        if (index >= size) throw IllegalArgumentException("Index out of range: $index")
        val staticCore = core.value
        val currentNode: Node<E> =
            staticCore.array[index].value ?: throw IllegalArgumentException("Index out of range: $index")
        return currentNode.value
    }

    override fun put(index: Int, element: E) {
        while (true) {
            if (index >= size) throw IllegalArgumentException("Index out of range: $index")
            val staticCore = core.value
            val staticValue =
                staticCore.array[index].value ?: throw IllegalArgumentException("Index out of range: $index")
            if (staticValue.status == Status.PROGRESS) {
                continue
            }
            if (core.value.array[index].compareAndSet(staticValue, Node(element, Status.STAY))) {
                return
            }
        }
    }

    private fun resize(staticCore : Core<E>, staticSize : Int) {
        if (taken.compareAndSet(expect = false, update = true)) {
            val tempCore = Core<E>(staticCore.capacity * 2)
            for (i in 0 until staticSize) {
                while (true) {
                    val value = staticCore.array[i].value ?: continue
                    val valueNew = Node(value.value, Status.PROGRESS)
                    if (staticCore.array[i].compareAndSet(expect = value, update = valueNew)) {
                        tempCore.array[i].compareAndSet(expect = null, update = value)
                        break
                    }
                }
            }
            tempCore.size.compareAndSet(0, staticSize)
            core.compareAndSet(staticCore, tempCore)
            taken.compareAndSet(expect = true, update = false)
        }
    }

    override fun pushBack(element: E) {
        while (true) {
            val staticCore = core.value
            val staticSize = staticCore.size.get()
            if (staticSize == staticCore.capacity) {
                resize(staticCore, staticSize)
            } else {
                if (staticCore.array[staticSize].compareAndSet(expect = null, update = Node(element, Status.STAY))) {
                    staticCore.size.incrementAndGet()
                    return
                }
            }
        }
    }

    override val size: Int
        get() {
            return core.value.size.get()
        }
}

class Core<E>(
    val capacity: Int,
) {
    val size = AtomicInteger(0)
    val array = atomicArrayOfNulls<Node<E>>(capacity)
}

class Node<E>(
    val value: E,
    val status: Status
)

enum class Status {
    STAY, PROGRESS
}

private const val INITIAL_CAPACITY = 1 // DO NOT CHANGE ME