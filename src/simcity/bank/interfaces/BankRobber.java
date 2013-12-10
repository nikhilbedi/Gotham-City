package simcity.bank.interfaces;

public interface BankRobber {
	
	public abstract void msgGoToTeller(BankTeller teller);
	
	public abstract void msgHeresYourMoney(double payoff);
	
	String getName();

	
	
}
