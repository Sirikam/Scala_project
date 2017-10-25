import exceptions._

class Account(var initialBalance: Double, val uid: Int = Bank getUniqueId) {
  def withdraw(amount: Double): Unit = {
	if (amount < 0) {
		throw new IllegalAmountException("cant subtract negative numbers")
	}

  	else if (amount > initialBalance) {
  		throw new NoSufficientFundsException("Not enough money left in the account")
  	}
  	else {
  		initialBalance = initialBalance - amount
  	}
  } 
  def deposit(amount: Double): Unit = {
  	if (amount < 0) {
		throw new IllegalAmountException("cant subtract negative numbers")
	}
  	initialBalance = initialBalance + amount
  } 
  def getBalanceAmount: Double = initialBalance;  
}
