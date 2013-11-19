package simcity.restaurant_evan.src.restaurant.interfaces;

import simcity.restaurant_evan.src.restaurant.CustomerRole;
import simcity.restaurant_evan.src.restaurant.Order;

public interface Waiter {
	
	public abstract void msgSitAtTable(CustomerRole c, int table);
	
	public abstract void msgReadyToOrder(CustomerRole cust);
	
	public abstract void msgHereIsMyChoice(CustomerRole cust, String choice);
	
	public abstract void msgWaiterOutOfFood(Order o);
	
	public abstract void msgOrderIsReady(Order order);
	
	public abstract void msgIWantTheCheck(CustomerRole cust);
	
	public abstract void msgWaiterHereIsCheck(Order o);
	
	public abstract void msgDoneEatingAndLeavingTable(CustomerRole cust);

}
