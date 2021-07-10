package dijkstra

import dijkstra.messages.Message
import dijkstra.messages.MessageRelax
import dijkstra.messages.MessageWithChildren
import dijkstra.system.environment.Environment

class ProcessImpl(private val environment: Environment) : Process {

    private var distance: Long? = null
    private var childCount: Int = 0
    private var balance: Int = 0
    private var parentId: Int = -1

    private fun sendToNeighbours() {
        if (distance == null) {
            throw IllegalStateException("Distance can't be null")
        }
        for (neighbour in environment.neighbours) {
            environment.send(neighbour.key, MessageRelax(distance!! + neighbour.value))
            printMessage("SEND", MessageRelax(distance!! + neighbour.value), environment.pid, neighbour.key)
            balance++
        }
        isRed()
    }

    private fun status() {
        // println("id = ${environment.pid} n = ${environment.neighbours.entries}, distance = $distance, childCount = $childCount, balance = $balance, parentId = $parentId")
    }

    private fun isRed() {
        if (childCount == 0 && balance == 0) {
            if (parentId == -1) {
                environment.finishExecution()
                // println("FINISH")
            } else {
                environment.send(parentId, MessageWithChildren(Type.NOW_IM_NOT_YOUR_CHILD))
                printMessage("SEND", MessageWithChildren(Type.NOW_IM_NOT_YOUR_CHILD), environment.pid, parentId)
                parentId = -1;
            }
        }
    }

    private fun printMessage(arg: String, message: Message, from: Int, to: Int) {
        // println("$arg $message from $from to $to")
    }

    override fun onMessage(srcId: Int, message: Message) {
        status()
        printMessage("GET", message, srcId, environment.pid)
        if (message is MessageRelax) {
            if (distance == null || distance!! > message.distance) {
                if (parentId != -1) {
                    environment.send(parentId, MessageWithChildren(Type.NOW_IM_NOT_YOUR_CHILD))
                    printMessage("SEND", MessageWithChildren(Type.NOW_IM_NOT_YOUR_CHILD), environment.pid, parentId)
                    parentId = -1
                }
                environment.send(srcId, MessageWithChildren(Type.YES_I_YOUR_CHILD))
                printMessage("SEND", MessageWithChildren(Type.YES_I_YOUR_CHILD), environment.pid, srcId)
                parentId = srcId
                distance = message.distance
                sendToNeighbours()
            } else {
                environment.send(srcId, MessageWithChildren(Type.NO_I_CANT_BE_YOUR_CHILD))
                printMessage("SEND", MessageWithChildren(Type.NO_I_CANT_BE_YOUR_CHILD), environment.pid, srcId)
            }
            status()
            return
        }
        if (message is MessageWithChildren) {
            when (message.type) {
                Type.YES_I_YOUR_CHILD -> {
                    childCount++
                    balance--
                }
                Type.NOW_IM_NOT_YOUR_CHILD -> {
                    childCount--
                }
                Type.NO_I_CANT_BE_YOUR_CHILD -> {
                    balance--
                }
            }
            status()
            isRed()
            return
        }
        throw IllegalStateException("Unsupported message")
    }

    override fun getDistance(): Long? {
        return distance
    }

    override fun startComputation() {
        distance = 0
        status()
        sendToNeighbours()
    }
}

enum class Type {
    NO_I_CANT_BE_YOUR_CHILD, YES_I_YOUR_CHILD, NOW_IM_NOT_YOUR_CHILD
}