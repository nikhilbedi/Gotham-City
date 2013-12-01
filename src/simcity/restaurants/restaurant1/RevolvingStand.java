package simcity.restaurants.restaurant1;

import java.util.ArrayList;
import java.util.List;
import simcity.restaurants.restaurant1.CookRole.Order;


public final class RevolvingStand {
	private static List<Order> paperOrders = new ArrayList<Order>();
	
	public void addOrder(WaiterRole w, String s, int t, CookRole cook) {
		paperOrders.add(cook.new Order(w, s, t));
	}
	
	public void removeOrder(Order o) {
		paperOrders.remove(o);
	}
}