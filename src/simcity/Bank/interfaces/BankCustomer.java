package simcity.Bank.interfaces;

import simcity.Bank.BankReceipt;
import simcity.interfaces.Person;

public interface BankCustomer {

	void msgWaitHere(int i);

	void msgGoToTeller(BankTeller teller);

	void NotEligibleForLoan();

	void HereIsReceipt(BankReceipt receipt);

	void HereIsReceiptAndAccountInfo(BankReceipt bankReceipt, int accountNumber);

	void HereIsLoan(BankReceipt bankReceipt, double transactionAmount);

	String getName();

	Person getPersonAgent();
}
