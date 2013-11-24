package simcity.bank;

public class BankTransaction {
	
	// State Variables
	
	double transactionAmount;
	String transactionType, landlordName;
	
	
	// Functions
	
	public double getTransactionAmount() { return transactionAmount;}

	public void setTransactionAmount(double transactionAmount) { this.transactionAmount = transactionAmount;}

	public String getTransactionType() { return transactionType;}

	public void setTransactionType(String transactionType) { this.transactionType = transactionType;}

	public String getLandlordName() { return landlordName;}

	public void setLandlordName(String landlordName) { this.landlordName = landlordName;}

	
	// Constructors
	
	public BankTransaction() {
		transactionAmount = 0;
		transactionType = "";
		landlordName = "";
	}
	
	public BankTransaction(String tT, double tA) {
		transactionType = tT;
		transactionAmount = tA;
		
		landlordName = ""; //Not used for the transaction and is therefore blank
	}
	
	public BankTransaction(String tT, double tA, String lN) {
		transactionType = tT;
		transactionAmount = tA;
		landlordName = lN;
	}
	
}
