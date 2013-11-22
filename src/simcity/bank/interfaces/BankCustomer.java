package simcity.bank.interfaces;

import simcity.interfaces.Person;
import simcity.bank.BankReceipt;

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
