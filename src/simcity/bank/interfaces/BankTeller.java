package simcity.bank.interfaces;

import simcity.bank.BankDatabase;
import simcity.bank.BankTransaction;

public interface BankTeller {

	public abstract void setAvailable(boolean b);

	public abstract boolean isAvailable();

	public abstract void msgNeedATransaction(BankCustomer bankCustomer, BankTransaction transaction);

	public abstract void msgDoneAndLeaving(BankCustomer bankCustomer);

	public abstract int getIndex();

	public abstract void setIndex(int i);

	public abstract void msgGiveMeMoney(BankRobber robber);

	public abstract void msgDoneAndLeaving(BankRobber bankRobber);

	public abstract void setGreeter(BankGreeter greeter);

	public abstract void setBankDatabase(BankDatabase db);
}