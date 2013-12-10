package simcity.restaurants.restaurant2;

import java.util.Timer;

import simcity.restaurants.restaurant2.interfaces.Waiter;

public class Order {
	Waiter waiter;
	String choice;
	int table;
	Timer timer = new Timer();
	CookingState s;
	
	public Order(Waiter w, String c, int t) {
		waiter = w;
		choice = c;
		table = t;
		
		s = CookingState.Pending;
	}
	
	public enum CookingState {Pending, Cooking, DoneCooking, Plated}; 
}

