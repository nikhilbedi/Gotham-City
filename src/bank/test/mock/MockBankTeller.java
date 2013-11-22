package bank.test.mock;

import bank.interfaces.BankCustomer;
import bank.interfaces.BankTeller;

public class MockBankTeller extends Mock implements BankTeller{
	public MockBankTeller(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void setAvailable(boolean b) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isAvailable() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void msgNeedATransaction(BankCustomer bankCustomer, String type,
			double amount) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void msgDoneAndLeaving(BankCustomer bankCustomer) {
		// TODO Auto-generated method stub
		
	}

	
}
