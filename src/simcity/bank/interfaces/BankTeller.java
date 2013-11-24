package simcity.bank.interfaces;

import simcity.bank.BankTransaction;

public interface BankTeller {

	public abstract void setAvailable(boolean b);

	public abstract boolean isAvailable();

	public abstract void msgNeedATransaction(BankCustomer bankCustomer, BankTransaction transaction);

	public abstract void msgDoneAndLeaving(BankCustomer bankCustomer);

	public abstract int getIndex();

	public abstract void setIndex(int i);
}