package bank;

public class BankReceipt {
	double transactionAmount, originalBalance, newBalance;
	
	public BankReceipt(double tA, double oB) {
		transactionAmount = tA;
		originalBalance = oB;
		newBalance = tA + oB;
	}
	
	public double getAmount() { return transactionAmount;}
	public double getOriginalBalance() { return originalBalance;}
	public double getNewBalance() { return newBalance;}
}
