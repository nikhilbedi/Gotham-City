package restaurant2.interfaces;

import restaurant2.Check;

public interface Waiter {

	public abstract void msgIWantFood(Customer cust);

	public abstract void msgAtTable();

	public abstract void msgAtEntrance();

	public abstract void msgAtCook();

	public abstract void msgAtCashier();

	public abstract void msgPleaseSeatCustomer(Customer c, int table);

	public abstract void msgReadyToOrder(Customer c);

	public abstract void msgHereIsMyOrder(String choice, Customer c);

	public abstract void msgOutOfChoice(String choice, int table);

	public abstract void msgOrderDone(String choice, int table);

	public abstract void msgDoneAndLeaving(Customer c);

	public abstract void msgGoOnBreak(boolean canGoOnBreak);

	public abstract void msgCheckReady(Check c);

	
	//Hacks
	public abstract int getNumCustomers();

	public abstract boolean isAvailable();

	public abstract String getName();

}