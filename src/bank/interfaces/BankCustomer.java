package bank.interfaces;

import bank.BankReceipt;

public interface BankCustomer {

	void msgWaitHere(int i);

	void msgGoToTeller(BankTeller teller);

	void NotEligibleForLoan();

	void HereIsReceipt(BankReceipt receipt);

	void HereIsReceiptAndAccountInfo(BankReceipt bankReceipt, int accountNumber);

	void HereIsLoan(BankReceipt bankReceipt, double transactionAmount);

}
