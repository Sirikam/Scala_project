import exceptions._

class Account(val bank: Bank, initialBalance: Double) {

  class Balance(var amount: Double) {}

  var balance = new Balance(initialBalance)
  val uid = bank.generateAccountId

  def withdraw(amount: Double): Unit = {
  	this.synchronized{
  		if (amount < 0) {
		throw new IllegalAmountException("cant subtract negative numbers")
		} 
		if (amount > balance) {
  			throw new NoSufficientFundsException("Not enough money left in the account")
  		}
  		balance = new Balance(balance - amount)
  	}
  } 
  def deposit(amount: Double): Unit = {
  	this.synchronized{
  		if (amount < 0) {
		throw new IllegalAmountException("cant subtract negative numbers")
		}
  		balance = new Balance(balance + amount)
  	}
  } 
  def getBalanceAmount: Double = balance;  

  def transferTo(account: Account, amount: Double) = {
    bank addTransactionToQueue (this, account, amount)
  }
}
