import scala.concurrent.forkjoin.ForkJoinPool
import scala.concurrent.AtomicInteger

class Bank(val allowedAttempts: Integer = 3) {

  private val uid = new AtomicInteger(0)
  private val transactionsQueue: TransactionQueue = new TransactionQueue()
  private val processedTransactions: TransactionQueue = new TransactionQueue()
  private val executorContext = ???

  def addTransactionToQueue(from: Account, to: Account, amount: Double): Unit = {
    transactionsQueue push new Transaction(
      transactionsQueue, processedTransactions, from, to, amount, allowedAttempts)
  }

  def generateAccountId(): Int =  uid.getAndIncrement()
    

  private def processTransactions: Unit = ???

  def addAccount(initialBalance: Double): Account = {
    new Account(this, initialBalance)
  }

  def getProcessedTransactionsAsList: List[Transaction] = {
    processedTransactions.iterator.toList
  }

}
