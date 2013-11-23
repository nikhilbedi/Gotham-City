package simcity.bank;

public class BankAccount {
	double accountBalance;
	int accountNumber;
	String accountHolderName;
	
	public BankAccount(double aB, int aN, String n) {
		accountBalance = aB;
		accountNumber = aN;
		accountHolderName = n;
	}
	
	public void depositMoney(double amount) {
		accountBalance += amount;
	}
	
	public void withdrawMoney(double amount) {
		accountBalance -= amount;
	}
}
