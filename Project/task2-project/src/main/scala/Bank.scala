import scala.concurrent.forkjoin.ForkJoinPool

class Bank(val allowedAttempts: Integer = 3) {

  private var uid = 0
  private val transactionsQueue: TransactionQueue = new TransactionQueue()
  private val processedTransactions: TransactionQueue = new TransactionQueue()
  private val executorContext = new ForkJoinPool()

  def addTransactionToQueue(from: Account, to: Account, amount: Double): Unit = {
    transactionsQueue push new Transaction(
      transactionsQueue, processedTransactions, from, to, amount, allowedAttempts)
  }

  def generateAccountId(): Int =  { this.synchronized{
          val old = uid
          uid += 1
          return old
        }
      }

  private def processTransactions: Unit = {
    for (transaction <- transactionsQueue.iterator) executorContext.execute(transaction)

  }

  def addAccount(initialBalance: Double): Account = {
    new Account(this, initialBalance)
  }

  def getProcessedTransactionsAsList: List[Transaction] = {
    processedTransactions.iterator.toList
  }

}
