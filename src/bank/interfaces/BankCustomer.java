package bank.interfaces;

<<<<<<< HEAD
import simcity.interfaces.Person;
=======
>>>>>>> 625dd9d9e00dcb14eaa686b434feee1c9294a813
import bank.BankReceipt;

public interface BankCustomer {

	void msgWaitHere(int i);

	void msgGoToTeller(BankTeller teller);

	void NotEligibleForLoan();

	void HereIsReceipt(BankReceipt receipt);

	void HereIsReceiptAndAccountInfo(BankReceipt bankReceipt, int accountNumber);

	void HereIsLoan(BankReceipt bankReceipt, double transactionAmount);

	String getName();

<<<<<<< HEAD
	Person getPersonAgent();
=======
>>>>>>> 625dd9d9e00dcb14eaa686b434feee1c9294a813
}
