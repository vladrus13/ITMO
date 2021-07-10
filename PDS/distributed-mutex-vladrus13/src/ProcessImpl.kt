package mutex

/**
 * Distributed mutual exclusion implementation.
 * All functions are called from the single main thread.
 *
 * @author Vladislav Kuznetsov
 */
class ProcessImpl(private val env: Environment) : Process {

    private var inCritical: Boolean = false
    private var tryToEat: Boolean = false
    private var strategy: Strategy = Strategy.ACYCLIC
    private val hand: ArrayList<ForkStatus> = strategy.hand(env)
    private val wantedQueue: BooleanArray = BooleanArray(env.nProcesses + 1)

    private fun sendMessageTake(srcId: Int) {
        env.send(srcId, Message {
            writeEnum(RequestStatus.TAKE)
        })
    }

    private fun sendMessageGive(srcId: Int) {
        env.send(srcId, Message {
            writeEnum(RequestStatus.GIVE)
        })
    }

    private fun setCritical() {
        inCritical = true
        tryToEat = false
    }

    private fun setHungry() {
        inCritical = false
        tryToEat = true
    }

    private fun setThinking() {
        inCritical = false
        tryToEat = false
    }

    private fun status() {
        println("Get process ${env.processId} array $hand, inCritical: $inCritical, tryToEat: $tryToEat")
    }

    override fun onMessage(srcId: Int, message: Message) {

        message.parse {
            when (readEnum<RequestStatus>()) {
                RequestStatus.TAKE -> {
                    hand[srcId] = ForkStatus.CLEAN
                    if (isReadyToEat()) {
                        setCritical()
                        env.locked()
                    }
                }
                RequestStatus.GIVE -> {
                    when (hand[srcId]) {
                        ForkStatus.DIRTY -> {
                            if (inCritical) {
                                wantedQueue[srcId] = true
                            } else {
                                hand[srcId] = ForkStatus.NONE
                                sendMessageTake(srcId)
                                if (tryToEat) {
                                    sendMessageGive(srcId)
                                }
                            }
                        }
                        ForkStatus.NONE -> throw IllegalArgumentException("Can't give a NONE fork")
                        ForkStatus.NOT_EXIST -> throw IllegalArgumentException("Can't give a not existing fork")
                        ForkStatus.CLEAN -> wantedQueue[srcId] = true
                        ForkStatus.ASKING -> throw IllegalArgumentException("Can't give a asking fork")
                    }
                }
            }
        }
    }

    override fun onLockRequest() {
        setHungry()
        if (isReadyToEat()) {
            setCritical()
            env.locked()
            return
        }
        for (i in 1..env.nProcesses) {
            if (hand[i] == ForkStatus.NONE) {
                sendMessageGive(i)
                hand[i] = ForkStatus.ASKING
            }
        }
    }

    override fun onUnlockRequest() {
        setThinking()
        env.unlocked()
        for (i in 1..env.nProcesses) {
            if (i != env.processId) {
                if (wantedQueue[i]) {
                    hand[i] = ForkStatus.NONE
                    sendMessageTake(i)
                    wantedQueue[i] = false
                } else {
                    hand[i] = ForkStatus.DIRTY
                }
            }
        }
    }

    fun isReadyToEat(): Boolean {
        for (i in 1..env.nProcesses) {
            if (i != env.processId) {
                if (!(hand[i] == ForkStatus.CLEAN || hand[i] == ForkStatus.DIRTY)) {
                    return false
                }
            }
        }
        return true
    }
}

enum class Strategy {
    ACYCLIC {
        override fun hand(env: Environment): ArrayList<ForkStatus> {
            val returned = ArrayList<ForkStatus>()
            returned.add(ForkStatus.NOT_EXIST)
            for (i in 1 until env.processId) {
                returned.add(ForkStatus.NONE)
            }
            returned.add(ForkStatus.NOT_EXIST)
            for (i in env.processId + 1..env.nProcesses) {
                returned.add(ForkStatus.CLEAN)
            }
            return returned
        }
    };

    abstract fun hand(env: Environment): ArrayList<ForkStatus>
}

enum class ForkStatus {
    CLEAN, DIRTY, NONE, NOT_EXIST, ASKING
}

enum class RequestStatus {
    GIVE, TAKE
}