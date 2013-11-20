package bank;

<<<<<<< HEAD
//Class to hold information about a transaction
public class BankReceipt {
	double transactionAmount, originalBalance, newBalance;
	String transactionType;
	
	public BankReceipt(double tA, double oB, String tT) {
		transactionAmount = tA;
		originalBalance = oB;
		newBalance = tA + oB;
		transactionType = tT;
=======
public class BankReceipt {
	double transactionAmount, originalBalance, newBalance;
	
	public BankReceipt(double tA, double oB) {
		transactionAmount = tA;
		originalBalance = oB;
		newBalance = tA + oB;
>>>>>>> 625dd9d9e00dcb14eaa686b434feee1c9294a813
	}
	
	public double getAmount() { return transactionAmount;}
	public double getOriginalBalance() { return originalBalance;}
	public double getNewBalance() { return newBalance;}
<<<<<<< HEAD
	public String getTransactionType() { return transactionType;}
=======
>>>>>>> 625dd9d9e00dcb14eaa686b434feee1c9294a813
}
