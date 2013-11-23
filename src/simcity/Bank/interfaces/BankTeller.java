package simcity.Bank.interfaces;

public interface BankTeller {

	public abstract void setAvailable(boolean b);

	public abstract boolean isAvailable();

	public abstract void msgNeedATransaction(BankCustomer bankCustomer, String type, double amount);

	public abstract void msgDoneAndLeaving(BankCustomer bankCustomer);

	public abstract int getIndex();

	public abstract void setIndex(int i);
}