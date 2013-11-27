package simcity.restaurants.restaurant5.interfaces;

import simcity.restaurants.restaurant5.Menu;



public interface Waiter {
	public abstract String getName();
    public abstract void doneMoving();
	// Messages
	//Messages added from v2

	public abstract void sitAtTable(Host h, Customer c, int table);

	public abstract void imReadyToOrder(Customer c);

	public abstract void hereIsMyChoice(Customer c, String choice);

	public abstract void hereIsAnOrder(Cook ck, String choice, int table);

	public abstract void doneEatingAndLeaving(Customer c);

	//Messages added from v2.1
	public abstract void outOfOrder(String choice, int i, Menu m);

	public abstract void doneEating(Customer c);

	public abstract void gotCheckAndLeaving(Customer c);

	public abstract void cantOrderLeaving(Customer c) ;
	public abstract void hereIsCheck(Cashier ch, Customer c, Double bill);
	public abstract void breakApproved();
	public abstract void cantBreak();
}
