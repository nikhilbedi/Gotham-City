package simcity.restaurants.restaurant3.src.restaurant.interfaces;

import simcity.restaurants.restaurant3.src.restaurant.Order;

public interface Cashier {

	
	
	public abstract void msgRequestCheck(Customer cust, Order o);
	
	public abstract void msgCustomerPayingCheck(Order o);
}
