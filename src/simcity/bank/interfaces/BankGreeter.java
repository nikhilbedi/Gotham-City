package simcity.bank.interfaces;

public interface BankGreeter {
	
	void msgNeedATeller(BankCustomer bankCustomerRole);

	void msgReadyForCustomer(BankTeller bankTellerRole);

}