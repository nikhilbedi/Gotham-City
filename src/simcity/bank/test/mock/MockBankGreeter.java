package simcity.bank.test.mock;

import simcity.bank.interfaces.BankCustomer;
import simcity.bank.interfaces.BankGreeter;
import simcity.bank.interfaces.BankRobber;
import simcity.bank.interfaces.BankTeller;

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

	@Override
	public void msgGiveMeATeller(BankRobber bankRobber) {
		// TODO Auto-generated method stub
		
	}
}
