package simcity.Bank.test.mock;

import simcity.Bank.interfaces.BankCustomer;
import simcity.Bank.interfaces.BankGreeter;
import simcity.Bank.interfaces.BankTeller;

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
