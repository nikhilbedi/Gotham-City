package simcity.restaurants.restaurant3;

import java.util.Vector;

import trace.AlertLog;
import trace.AlertTag;

public class RevolvingStand {

	public static Vector<Order> data = new Vector<Order>();
	
	public static void pushOrder(Order o, Restaurant3CookRole cook){
		data.add(o);
		cook.orderIsOnStand();
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
		//AlertLog.getInstance().logInfo(AlertTag.RESIDENT_ROLE, this.getName(),
			//	"checking stand...");
		//System.out.println("Checking stand...");
		if (!data.isEmpty()){
			return true;
		}
		return false;
	}
}
