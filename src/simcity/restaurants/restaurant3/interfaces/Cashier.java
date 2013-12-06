package simcity.restaurants.restaurant3.interfaces;

import simcity.restaurants.restaurant3.*;

public interface Cashier {

	
	
	public abstract void msgRequestCheck(Customer cust, Order o);
	
	public abstract void msgCustomerPayingCheck(Order o);
}
