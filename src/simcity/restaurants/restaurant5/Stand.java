package simcity.restaurants.restaurant5;

import java.util.Vector;

import simcity.restaurants.restaurant5.CookRole.Order;
import simcity.restaurants.restaurant5.interfaces.*;

public class Stand extends Object{

	public static Vector<Order> theData = new Vector<Order>();
	
	public static void pushOrder(Waiter w, String s, int t, Cook cook) {
		synchronized(theData) {
			theData.add(((CookRole)cook).new Order(w, s, t));
		}
		//cook.notifyOrderAvailable();
	}

	public static Order popOrder() {
		Order o = null;
		if(!theData.isEmpty()){
			synchronized(theData) {
				o = theData.get(0);
				theData.remove(o);
			}
		}
		return o;
	}
	public static boolean hasOrders(){
		return (!theData.isEmpty());
	}
}	


