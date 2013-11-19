package simcity.restaurant_evan.src.restaurant.interfaces;

import simcity.restaurant_evan.src.restaurant.Order;

public interface Cashier {

	
	
	public abstract void msgRequestCheck(Customer cust, Order o);
	
	public abstract void msgCustomerPayingCheck(Order o);
}
