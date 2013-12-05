package simcity.restaurants.restaurant3.interfaces;

import simcity.restaurants.restaurant3.*;

public interface Waiter {
	
	public abstract String getName();
	
	public abstract void msgSitAtTable(Customer c, int table);
	
	public abstract void msgReadyToOrder(Customer cust);
	
	public abstract void msgHereIsMyChoice(Customer cust, String choice);
	
	public abstract void msgWaiterOutOfFood(Order o);
	
	public abstract void msgOrderIsReady(Order order);
	
	public abstract void msgIWantTheCheck(Customer cust);
	
	public abstract void msgWaiterHereIsCheck(Order o);
	
	public abstract void msgDoneEatingAndLeavingTable(Customer cust);
	
	public abstract int getCustomersCount();

	public boolean isOnBreak = false;

	public abstract void msgAtTable();

	public abstract void msgAtHost();

	public abstract void msgAtCashier();

	public abstract void msgAtCook();

	public abstract void msgAtStand();
}
