package simcity.restaurants.restaurant1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import simcity.restaurants.restaurant1.CookRole.Order;
import simcity.restaurants.restaurant1.interfaces.Waiter;


public class RevolvingStand {
	private static List<Order> paperOrders = Collections.synchronizedList(new ArrayList<Order>());

	public static void addOrder(Waiter w, String s, int t, CookRole cook) {
		synchronized(paperOrders) {
			paperOrders.add(cook.new Order(w, s, t));
		}
		cook.orderIsHere();
	}

	public static Order removeOrder() {
		Order o = null;
		if(checkStand()){
			synchronized(paperOrders) {
				o = paperOrders.get(0);
			}
		}
		if(o != null) {
			paperOrders.remove(o);
		}
		return o;
	}

	public static boolean checkStand() {
		synchronized(paperOrders) {
			if(paperOrders.isEmpty()) {
				return false;
			}
			else
				return true;
		}
	}
	
	public static List<String> choicesOnStand() {
		List<String> tempList = new ArrayList<String>();
		for(Order o : paperOrders) {
			tempList.add(((Order) paperOrders).getChoice());
		}
		return tempList;
	}
}