import exceptions._
import scala.collection.mutable._

object TransactionStatus extends Enumeration {
  val SUCCESS, PENDING, FAILED = Value
}

class TransactionQueue {

  private val q = new Queue[Transaction]()

  // Remove and return the first element from the queue
  def pop: Transaction = this.synchronized{ q.dequeue() }

  // Return whether the queue is empty
  def isEmpty: Boolean = q.isEmpty

  // Add new element to the back of the queue
  def push(t: Transaction): Unit = this.synchronized{ q.enqueue(t) }

  // Return the first element from the queue without removing it
  def peek: Transaction = q.head

  // Return an iterator to allow you to iterate over the queue
  def iterator: Iterator[Transaction] = q.toIterator
}

class Transaction(val transactionsQueue: TransactionQueue,
                  val processedTransactions: TransactionQueue,
                  val from: Account,
                  val to: Account,
                  val amount: Double,
                  val allowedAttemps: Int) extends Runnable {

  var status: TransactionStatus.Value = TransactionStatus.PENDING

  override def run: Unit = {

    def doTransaction() = {
      var tries = 0
      while (status == TransactionStatus.PENDING) {
        try {
          from withdraw amount
          to deposit amount
          status = TransactionStatus.SUCCESS
        } catch {
          case _: Throwable => {
            if (tries < allowedAttemps) {
              tries = tries + 1
            } else {
              status = TransactionStatus.FAILED
            }
          }
        }
      }
      processedTransactions.push(this)
    }

    if (from.uid < to.uid) from synchronized {
      to synchronized {
        doTransaction
      }
    } else to synchronized {
      from synchronized {
        doTransaction
      }
    }

  }
}
