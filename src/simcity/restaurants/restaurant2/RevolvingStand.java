package simcity.restaurants.restaurant2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import agent.Agent;
import simcity.restaurants.restaurant2.Order;
import simcity.restaurants.restaurant2.Order.CookingState;
import simcity.restaurants.restaurant2.interfaces.Cook;
import simcity.restaurants.restaurant2.interfaces.Waiter;

public class RevolvingStand{
	static List<Order> standOrders = Collections.synchronizedList(new LinkedList<Order>());
	static Timer timer = new Timer();
	
	public static void addOrder(Waiter w, String choice, int table, final Cook cook) {
		standOrders.add(new Order(w, choice, table));
		timer.schedule(new TimerTask() {
			Object cookie = 1;
			public void run() {
				cook.orderReady();
			}
		},
		2000);
	}
	
	//public RevolvingStand() {
		//standOrders = new ArrayList<Order>();
	//}
	
	public static Order getNextOrder() {
		if(standOrders.size() > 0) 
			return standOrders.remove(0);
		return null;
	}
}
