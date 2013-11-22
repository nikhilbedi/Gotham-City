package bank.test.mock;

import bank.interfaces.BankCustomer;
import bank.interfaces.BankGreeter;
import bank.interfaces.BankTeller;

public class MockBankGreeter extends Mock implements BankGreeter{
	EventLog log = new EventLog();
	
	public MockBankGreeter(String name) {
		super(name);
	}

	@Override
	public void msgNeedATeller(BankCustomer bankCustomerRole) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void msgReadyForCustomer(BankTeller bankTellerRole) {
		// TODO Auto-generated method stub
		
	}
}
