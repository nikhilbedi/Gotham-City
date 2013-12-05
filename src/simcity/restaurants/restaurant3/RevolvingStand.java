package simcity.restaurants.restaurant3;

import java.util.Vector;

public class RevolvingStand {

	public static Vector<Order> data = new Vector<Order>();
	
	public static void pushOrder(Order o){
		data.add(o);
	}
	
	public static Order popOrder() {
		if (data.size() > 0) {// there is waiting orders
			Order o = data.get(0);
			data.remove(0);
			return o;
		}
		else 
			return null;		
	}
	
	public static boolean checkStand() {
		if (data.size() > 0)
			return true;
		return false;
	}
}
