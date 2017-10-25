import exceptions._

class Account(val bank: Bank, var initialBalance: Double) {

  class Balance(var amount: Double) {}

  val balance = new Balance(initialBalance)
  val uid = bank.generateAccountId

  def withdraw(amount: Double): Unit = {
  	this.synchronized{
  		if (amount < 0) {
		throw new IllegalAmountException("cant subtract negative numbers")
		} 
		if (amount > initialBalance) {
  			throw new NoSufficientFundsException("Not enough money left in the account")
  		}
  		initialBalance = initialBalance - amount
  	}
  } 
  def deposit(amount: Double): Unit = {
  	this.synchronized{
  		if (amount < 0) {
		throw new IllegalAmountException("cant subtract negative numbers")
		}
  		initialBalance = initialBalance + amount
  	}
  } 
  def getBalanceAmount: Double = initialBalance;  

  def transferTo(account: Account, amount: Double) = {
    bank addTransactionToQueue (this, account, amount)
  }
}
