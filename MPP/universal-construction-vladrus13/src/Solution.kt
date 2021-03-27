/**
 * @author Kuznetsov Vladislav
 */
class Solution : AtomicCounter {
    // объявите здесь нужные вам поля
    // обьявляю)
    private val root = Node(0)
    private val last : ThreadLocal<Node> = ThreadLocal.withInitial { root }

    override fun getAndAdd(x: Int): Int {
        // напишите здесь код
        // "ладно."
        var old : Int = last.get().value
        var node = Node(old + x)
        last.set(last.get().next.decide(node))
        while (node != last.get()) {
            old = last.get().value
            node = Node(old + x)
            last.set(last.get().next.decide(node))
        }
        return old
    }

    // вам наверняка потребуется дополнительный класс
    private class Node(x: Int) {
        val value : Int = x
        val next : Consensus<Node> = Consensus()
    }
}