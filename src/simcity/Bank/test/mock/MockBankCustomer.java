package simcity.Bank.test.mock;

import simcity.Bank.BankReceipt;
import simcity.Bank.interfaces.BankCustomer;
import simcity.Bank.interfaces.BankTeller;
import simcity.interfaces.Person;

/**
 * A sample MockBankCustomer built to unit test
 *
 * @author Brice Roland
 *
 */
public class MockBankCustomer extends Mock implements BankCustomer {
	EventLog log = new EventLog();
	/**
	 * Reference to the Cashier under test that can be set by the unit test.
	 */
	double debt = 0;
	double cash = 0;
//	public Cashier cashier;

	public MockBankCustomer(String name) {
		super(name);

	}
	
	@Override
	public void msgWaitHere(int i) {
		
	}
	
	@Override
	public void msgGoToTeller(BankTeller teller) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void NotEligibleForLoan() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void HereIsReceipt(BankReceipt receipt) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void HereIsReceiptAndAccountInfo(BankReceipt bankReceipt,
			int accountNumber) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void HereIsLoan(BankReceipt bankReceipt, double transactionAmount) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Person getPersonAgent() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
