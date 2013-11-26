package simcity.restaurants.restaurant3;

//import Restaurant;
//import MarketAgent.Status;
import java.util.*;

import simcity.PersonAgent;
import agent.Role;
import simcity.restaurants.restaurant3.Order.OrderState;
import simcity.restaurants.restaurant3.interfaces.*;
import agent.Agent;
import java.util.Timer;
import java.util.TimerTask;



public class MarketRole extends Role implements Market{
	
	public List<Order> orders = Collections.synchronizedList(new Vector<Order>()); 
	public Map<String, MarketFood> foods = Collections.synchronizedMap(new HashMap<String, MarketFood>());
	private Map<String,Integer> inventory = Collections.synchronizedMap(new HashMap<String,Integer>());
	private int threshold;
	private String name;
	

	public enum Status {pending, processing, incomplete, gatheringIngredients, shipped, done};
	private Status status = Status.pending;
	
	//Restaurant restaurant;
	//private WaiterAgent waiter;
	
	 private int Inventory = 5;
	 
	public MarketRole(String name) {
		super();
		this.name = name;
		
		//Create the market's stock.
		inventory.put("Steak", 4);
		inventory.put("Chicken", 4);
		inventory.put("Pizza", 4);
		inventory.put("Salad", 4);
		    
		
		MarketFood f = new MarketFood ("Chicken");
		f.setPrice(7.99);
		foods.put("Chicken", f);
		
		f = new MarketFood ("Steak");
		f.setPrice(12.99);
		foods.put("Steak", f);
		
		f = new MarketFood ("Pizza");
		f.setPrice(5.99);
		foods.put("Pizza", f);
		
		f = new MarketFood ("Salad");
		f.setPrice(2.99);
		foods.put("Salad", f);
		
	}

	public String getMaitreDName() {
		return name;
	}

	public String getName() {
		return name;
	}

	
	// Messages

	public MarketOrder msgOrderRestock(MarketOrder mo) {
		//System.out.println("msgOrderRestock received");
		System.out.println("Order Food from Market: " + this.name);
		
		int stock = inventory.get(mo.foodType);
		
		// enough food
		if (stock >= mo.amountNeeded) { // process the purchase
			//f.setAmount(f.getAmount() + mo.amountNeeded);
			MarketFood f = foods.get(mo.foodType);
			inventory.put(mo.foodType, stock - mo.amountNeeded);
			double bill = f.getPrice() * mo.amountNeeded;
			mo.bill = bill;
			mo.amountProvided = mo.amountNeeded;
			return mo;
		}
		
		// no food at all
		else if (stock == 0) {
			System.out.println("Market is out of Inventory for " + mo.foodType);
			return null;
		}
		
		//partial order. Order from multiple markets
		else if (stock < mo.amountNeeded && stock > 0) {
			int amountProvided = stock;
			MarketFood f = foods.get(mo.foodType);
			inventory.put(mo.foodType, 0);
			double bill = f.getPrice() * amountProvided;
			mo.bill = bill;
			mo.amountProvided = amountProvided;
			return mo;
		}
		return null;
		
		//orders.add(o);
		//o.os = OrderState.pending;
		//stateChanged();
		
		//orders.add(new Order(w, choice, table, OrderState.pending));
		
	}
	
	/**
	 * Scheduler.  Determine what action is called for, and do it.
	 */
	  
	public boolean pickAndExecuteAnAction() {
	
		synchronized(orders) {
		for (Order o: orders) {
			if(o.os == OrderState.outOfInventory) {
				messageWaiterOutOfInventory(o);
				System.out.println("OUT OF INVENTORY");
				return true;
			}
		}
		}
		return false;
	}
	// Actions

	private void messageWaiterOutOfInventory(Order o) {
		System.out.println("Tells the waiter they are out of inventory");
		o.os = OrderState.reordering;
		o.waiter.msgWaiterOutOfFood(o);
		
	}
	//public int getAmountNeeded() {
		//return MarketOrder.getAmountNeeded();
		//return MarketOrder.amountNeeded;
	//}

	private boolean checkInventory(Food f) {
		System.out.println("checking Inventory");
		
		int capacity = f.getAmount(); 
		if (capacity > 0) {
			f.setAmount (capacity - 1);
			return true;
		}
		return false;
	}

	//utilities
	
	public void setCook(CookRole cook) {
			cook = cook;
	}
}
