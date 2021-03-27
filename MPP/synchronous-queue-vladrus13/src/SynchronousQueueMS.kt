
import java.util.concurrent.atomic.AtomicReference
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class SynchronousQueueMS<E> : SynchronousQueue<E> {

    private open class Node {
        val next = AtomicReference<Node>(null)
    }

    private class Receiver<E>(val action: Continuation<E>) : Node()

    private class Sender<E>(val element: E, val action: Continuation<Unit>) : Node()

    private val head : AtomicReference<Node>
    private val tail : AtomicReference<Node>

    init {
        val node = Node()
        head = AtomicReference(node)
        tail = AtomicReference(node)
    }

    override suspend fun send(element: E) {
        while (true) {
            val currentTail = tail.get()
            if (currentTail == head.get() || currentTail is Sender<*>) {
                val result = suspendCoroutine<Any?> sendAttack@{cont ->
                    val newTail = Sender(element, cont)
                    val oldTail = tail.get()
                    if ((oldTail is Sender<*> || oldTail == head.get()) && oldTail.next.compareAndSet(null, newTail)) {
                        tail.compareAndSet(oldTail, newTail)
                    } else {
                        cont.resume(null)
                        return@sendAttack
                    }
                }
                if (result != null) return
            } else {
                val currentHead = head.get()
                if (currentHead == tail.get() || currentHead.next.get() == null) continue
                val nextHead = currentHead.next.get()
                if (nextHead is Receiver<*> && head.compareAndSet(currentHead, nextHead)) {
                    @Suppress("UNCHECKED_CAST")
                    (nextHead.action as Continuation<E>).resume(element)
                    return
                }
            }
        }
    }

    override suspend fun receive(): E {
        while (true) {
            val currentTail = tail.get()
            if (currentTail == head.get() || currentTail is Receiver<*>) {
                val result = suspendCoroutine<E?> receiveAttack@{cont ->
                    val newTail = Receiver(cont)
                    val oldTail = tail.get()
                    if ((oldTail is Receiver<*> || oldTail == head.get()) && oldTail.next.compareAndSet(null, newTail)) {
                        tail.compareAndSet(oldTail, newTail)
                    } else {
                        cont.resume(null)
                        return@receiveAttack
                    }
                }
                if (result != null) return result
            } else {
                val currentHead = head.get()
                if (currentHead == tail.get() || currentHead.next.get() == null) continue
                val nextHead = currentHead.next.get()
                if (nextHead is Sender<*> && currentHead != tail.get() && head.compareAndSet(currentHead, nextHead)) {
                    nextHead.action.resume(Unit)
                    @Suppress("UNCHECKED_CAST")
                    return nextHead.element as E
                }
            }
        }
    }
}
