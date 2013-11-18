package simcity.Market;

import simcity.Market.interfaces.MarketCustomer;

public class Order {
	MarketCustomer customer;
	String choice;
	int quantity;
	boolean delivery;
	
	public Order(MarketCustomer c, String ch, int q, boolean d){
		customer = c;
		choice = ch;
		quantity = q;
		delivery = d;
		
	}
	
}
