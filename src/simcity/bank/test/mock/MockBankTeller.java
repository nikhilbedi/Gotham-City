package simcity.bank.test.mock;

import simcity.bank.BankTransaction;
import simcity.bank.interfaces.BankCustomer;
import simcity.bank.interfaces.BankTeller;

public class MockBankTeller extends Mock implements BankTeller{
	boolean available;
	
	public MockBankTeller(String name) {
		super(name);
	}

	@Override
	public void setAvailable(boolean b) {
		available = b;
	}

	@Override
	public boolean isAvailable() {
		return available;
	}

	@Override
	public void msgNeedATransaction(BankCustomer bankCustomer, BankTransaction transaction) {
		
	}

	@Override
	public void msgDoneAndLeaving(BankCustomer bankCustomer) {
		
	}

	@Override
	public int getIndex() {
		return 0;
	}

	@Override
	public void setIndex(int i) {
		
	}

	
}