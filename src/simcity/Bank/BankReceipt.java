package simcity.Bank;

//Class to hold information about a transaction
public class BankReceipt {
	double transactionAmount, originalBalance, newBalance;
	String transactionType;
	
	public BankReceipt(double tA, double oB, String tT) {
		transactionAmount = tA;
		originalBalance = oB;
		newBalance = tA + oB;
		transactionType = tT;
	}
	
	public double getAmount() { return transactionAmount;}
	public double getOriginalBalance() { return originalBalance;}
	public double getNewBalance() { return newBalance;}
	public String getTransactionType() { return transactionType;}
}
