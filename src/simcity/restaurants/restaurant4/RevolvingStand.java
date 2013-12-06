package simcity.restaurants.restaurant4;

import java.util.ArrayList;
import java.util.List;


import simcity.restaurants.restaurant4.Restaurant4CookRole;
import simcity.restaurants.restaurant4.Restaurant4CookRole.Order;
import simcity.restaurants.restaurant4.interfaces.Restaurant4Cook;
import simcity.restaurants.restaurant4.interfaces.Restaurant4Waiter;

public class RevolvingStand {
	public static List<StandOrder> orders = new ArrayList<StandOrder>();
	
	
	public static void addOrder(Restaurant4Cook cook, Restaurant4Waiter waiter, String choice, int table){
		orders.add(new StandOrder(cook, waiter, choice, table));
		cook.OrderOnTheStand();
	}
	
	
	
	static class StandOrder{
		Restaurant4Cook cook;
		Restaurant4Waiter waiter;
		String choice;
		int table;
		
		public StandOrder(Restaurant4Cook cook, Restaurant4Waiter waiter, String choice, int table){
			this.cook = cook;
			this.waiter = waiter;
			this.choice = choice;
			this.table = table;
		}
	}
	
	
	
}
