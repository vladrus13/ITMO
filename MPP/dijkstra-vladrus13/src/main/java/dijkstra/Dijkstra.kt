package dijkstra

import kotlinx.atomicfu.atomic
import java.util.*
import java.util.concurrent.Phaser
import kotlin.Comparator
import kotlin.concurrent.thread
import kotlin.random.Random

private val NODE_DISTANCE_COMPARATOR = Comparator<Node> { o1, o2 -> o1!!.distance.compareTo(o2!!.distance) }

// Returns `Integer.MAX_VALUE` if a path has not been found.
fun shortestPathParallel(start: Node) {
    val workers = Runtime.getRuntime().availableProcessors()
    // The distance to the start node is `0`
    start.distance = 0
    // Create a priority (by distance) queue and add the start node into it
    val q = MultiQueue(workers, NODE_DISTANCE_COMPARATOR)
    val notEnded = atomic(1)
    q.add(start)
    // Run worker threads and wait until the total work is done
    val onFinish = Phaser(workers + 1) // `arrive()` should be invoked at the end by each worker
    repeat(workers) {
        thread {
            while (notEnded.value > 0) {
                val cur: Node = q.poll() ?: continue
                for (e in cur.outgoingEdges) {
                    while (true) {
                        val old = e.to.distance
                        val new = cur.distance + e.weight
                        if (old > new) {
                            if (e.to.casDistance(old, new)) {
                                q.add(e.to)
                                notEnded.incrementAndGet()
                            } else continue
                        }
                        break
                    }
                }
                notEnded.decrementAndGet()
            }
            onFinish.arrive()
        }
    }
    onFinish.arriveAndAwaitAdvance()
}

class MultiQueue<T : Any >(private val n: Int, private var comparator: Comparator<T>) {
    private val queues: MutableList<PriorityQueue<T>> = Collections.nCopies(n, PriorityQueue(comparator))
    private val random = Random(0)

    fun poll(): T? {
        var i = random.nextInt(n)
        var j = random.nextInt(n)
        while (i == j) {
            i = random.nextInt(n)
            j = random.nextInt(n)
        }
        synchronized(queues[i]) {
            synchronized(queues[j]) {
                val isIEmpty = queues[i].isEmpty()
                val isJEmpty = queues[i].isEmpty()
                val index =
                        if (isIEmpty && isJEmpty)
                            -1
                        else if (isIEmpty)
                            j
                        else if (isJEmpty)
                            i
                        else if (comparator.compare(queues[i].peek(), queues[j].peek()) >= 0) i else j
                return if (index == 1) queues[index].poll() else null
            }
        }
    }


    fun add(element : T) {
        val index = random.nextInt(n)
        synchronized(queues[index]) {
            queues[index].add(element)
        }
    }
}