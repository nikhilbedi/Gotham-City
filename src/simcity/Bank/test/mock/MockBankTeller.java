package simcity.Bank.test.mock;

import simcity.Bank.interfaces.BankCustomer;
import simcity.Bank.interfaces.BankTeller;

public class MockBankTeller extends Mock implements BankTeller{
	public MockBankTeller(String name) {
		super(name);
	}

	@Override
	public void setAvailable(boolean b) {
		
	}

	@Override
	public boolean isAvailable() {
		return false;
	}

	@Override
	public void msgNeedATransaction(BankCustomer bankCustomer, String type,
			double amount) {
		
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
