import exceptions._

class Account(var initialBalance: Double, val uid: Int = Bank getUniqueId) {
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
}
