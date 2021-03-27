import java.util.concurrent.locks.ReentrantLock

/**
 * Bank implementation.
 *
 * @author Vladislav Kuznetsov
 */
class BankImpl(n: Int) : Bank {
    private val accounts: Array<Account> = Array(n) { Account() }

    override val numberOfAccounts: Int
        get() = accounts.size

    override fun getAmount(index: Int): Long {
        accounts[index].lock.lock()
        val res = accounts[index].amount
        accounts[index].lock.unlock()
        return res
    }

    override val totalAmount: Long
        get() {
            accounts.forEach { account ->
                account.lock.lock()
            }
            val res = accounts.sumOf { account ->
                account.amount
            }
            accounts.forEach { account ->
                account.lock.unlock()
            }
            return res
        }

    override fun deposit(index: Int, amount: Long): Long {
        require(amount > 0) { "Invalid amount: $amount" }
        val account = accounts[index]
        account.lock.lock()
        if (amount > Bank.MAX_AMOUNT || account.amount + amount > Bank.MAX_AMOUNT) {
            account.lock.unlock()
            throw IllegalStateException("Overflow")
        }
        account.amount += amount
        val res = account.amount
        account.lock.unlock()
        return res
    }

    override fun withdraw(index: Int, amount: Long): Long {
        require(amount > 0) { "Invalid amount: $amount" }
        val account = accounts[index]
        account.lock.lock()
        if (account.amount - amount < 0) {
            account.lock.unlock()
            throw IllegalStateException("Underflow")
        }
        account.amount -= amount
        val res = account.amount
        account.lock.unlock()
        return res
    }

    private fun changeLock(fromIndex: Int, toIndex: Int, fromAccount: Account, toAccount: Account, lock: Boolean) {
        if (lock) {
            if (fromIndex < toIndex) {
                fromAccount.lock.lock()
                toAccount.lock.lock()
            } else {
                toAccount.lock.lock()
                fromAccount.lock.lock()
            }
        } else {
            if (fromIndex < toIndex) {
                fromAccount.lock.unlock()
                toAccount.lock.unlock()
            } else {
                toAccount.lock.unlock()
                fromAccount.lock.unlock()
            }
        }
    }

    override fun transfer(fromIndex: Int, toIndex: Int, amount: Long) {
        require(amount > 0) { "Invalid amount: $amount" }
        require(fromIndex != toIndex) { "fromIndex == toIndex" }
        val from = accounts[fromIndex]
        val to = accounts[toIndex]
        changeLock(fromIndex, toIndex, from, to, true)
        if (amount > from.amount) {
            changeLock(fromIndex, toIndex, from, to, false)
            throw IllegalStateException("Underflow")
        } else {
            if (amount > Bank.MAX_AMOUNT || to.amount + amount > Bank.MAX_AMOUNT) {
                changeLock(fromIndex, toIndex, from, to, false)
                throw IllegalStateException("Overflow")
            }
        }
        from.amount -= amount
        to.amount += amount
        changeLock(fromIndex, toIndex, from, to, false)
    }

    /**
     * Private account data structure.
     */
    class Account {
        /**
         * Amount of funds in this account.
         */
        var amount: Long = 0

        /**
         * Lock of account
         */
        var lock: ReentrantLock = ReentrantLock()
    }
}