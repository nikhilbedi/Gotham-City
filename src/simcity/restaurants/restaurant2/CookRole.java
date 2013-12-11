package simcity.restaurants.restaurant2;

import Gui.RoleGui;
import agent.Agent;
//import restaurant.WaiterAgent;
import agent.Role;
import simcity.Market.Market;
import simcity.Market.MarketWorkerRole;
import simcity.restaurants.Restaurant;
import simcity.restaurants.restaurant2.Order;
import simcity.restaurants.restaurant2.Order.CookingState;
import simcity.restaurants.restaurant2.Restaurant2CustomerRole.AgentEvent;
import simcity.restaurants.restaurant2.gui.CookGui;
import simcity.restaurants.restaurant2.gui.HostGui;
import simcity.restaurants.restaurant2.gui.WaiterGui;
import simcity.restaurants.restaurant2.interfaces.Cook;
import simcity.restaurants.restaurant2.interfaces.Waiter;
import simcity.Item;
import simcity.PersonAgent;
import simcity.TheCity;

import java.util.*;
import java.util.concurrent.Semaphore;

/**
 * Restaurant Cook Agent
 */
public class CookRole extends Role implements Cook{
	List<Order> orders = new LinkedList<Order>();
	Map<String, Integer> deliveryOrder = new HashMap<String, Integer>();
	List<Market> markets = new LinkedList<Market>();
	Restaurant r2;
	Map <String, Food> foods = new HashMap<String, Food>();
	CookGui cookGui;
	
	int marketIndex = 0, marketIterations;
	private String name;
	//Timer timer = new Timer();
	public HostGui hostGui = null;
	private boolean needFood = false;
	private boolean order = false;
	
	public CookRole(PersonAgent person) {
		super(person);
		
		foods.put("Steak", new Food("Steak"));
		foods.put("Chicken", new Food("Chicken"));
		foods.put("Salad", new Food("Salad"));
		foods.put("Pizza", new Food("Pizza"));
	}
	
	public CookRole() {
		super();
		
		foods.put("Steak", new Food("Steak"));
		foods.put("Chicken", new Food("Chicken"));
		foods.put("Salad", new Food("Salad"));
		foods.put("Pizza", new Food("Pizza"));
	}
	
	public void addMarket(Market m) {
		markets.add(m);
	}
	
	public String getName() {
		return name;
	}
	
	// Messages
	
	public void msgHereIsOrder(Waiter w, String choice, int table) {
		orders.add(new Order(w, choice, table));
		stateChanged();
	}
	
	public void msgHereIsOrder(Order o) {
		orders.add(o);
		stateChanged();
	}
	
	public void orderReady() {
		orders.add(RevolvingStand.getNextOrder());
	}
	
	public void msgPickedUpOrder(int table) {
		cookGui.setStr(table-1, "");
	}
	
	public void msgHereIsInventoryReport(List<Integer> inventoryDifference) {
		System.out.println(getName() + ": Recieved Inventory Report");
		
		marketIndex++;
		if(marketIndex == markets.size())
			marketIndex = 0;
		
		marketIterations++;
	}
	
	public void msgHereIsDelivery(Map<String, Integer> foodsToDeliver) {
		System.out.println(getName() + ": Recieved Delivery.");
		
		foods.get("Steak").amount += foodsToDeliver.get("Steak");
		foods.get("Pizza").amount += foodsToDeliver.get("Pizza");
		foods.get("Chicken").amount += foodsToDeliver.get("Chicken");
		foods.get("Salad").amount += foodsToDeliver.get("Salad");
		
		System.out.println(getName() + ": New Stock List:");
		System.out.println(getName() + ": Steak: " + foods.get("Steak").amount);
		System.out.println(getName() + ": Pizza: " + foods.get("Pizza").amount);
		System.out.println(getName() + ": Chicken: " + foods.get("Chicken").amount);
		System.out.println(getName() + ": Salad: " + foods.get("Salad").amount);
		
		deliveryOrder.clear();
		
		//marketIterations = 0;
		
		checkForLowInventory();
	}
	
	public void HereIsYourFood(Map<String, Integer> m, MarketWorkerRole worker){ //from market
		needFood = false;
		order = false;
		myPerson.Do("Sending market worker message that I got food");
		worker.Delivered(r2);
		for (Map.Entry<String, Integer> entry: m.entrySet()){
			Food f = foods.get(entry.getKey());
			f.amount = f.amount + entry.getValue();
			foods.put(entry.getKey(), f);
			myPerson.Do("Got order from market, now I have " + f.type + " " + f.amount);
		}
		
	}
	
	/**
	 * Scheduler.  Determine what action is called for, and do it.
	 */
	public boolean pickAndExecuteAnAction() {
		/* Think of this next rule as:
            Does there exist a table and customer,
            so that table is unoccupied and customer is waiting.
            If so seat him at the table.
		 */

		for (Map.Entry<String, Food> entry: foods.entrySet()){
			if (entry.getValue().amount<=2){
				myPerson.Do("Need " + entry.getKey());
				int needed = entry.getValue().capacity - entry.getValue().amount;
				deliveryOrder.put(entry.getKey(), needed);
				needFood = true;
			}
		}
		if ((needFood == true)&&(order == false)){
			order = true;
			myPerson.Do("Ordering food");
			orderFoodThatIsLow();
			return true;
		}
		
		

		if(theManLeavingMe != null && orders.isEmpty()) {
			leaveWork();
			return true;
		}

		
		synchronized(orders) {
			for (int x = 0; x < orders.size(); x++) {
				if (orders.get(x).s == CookingState.DoneCooking) {
					PlateIt(x);
					return true;
				}
			}
		}
		
		synchronized(orders) {
			for (int x = 0; x < orders.size(); x++) {
				if (orders.get(x).s == CookingState.Pending) {
					CookIt(x);
					marketIterations = 0;
					checkForLowInventory();
					return true;
				}
			}
		}
		
		boolean CookingSomething = false;
		synchronized(orders) {
			for (int x = 0; x < orders.size(); x++) {
				if (orders.get(x).s == CookingState.Cooking) {
					CookingSomething = true;
					return true;
				}
			}
		}
		
		synchronized(orders) {
			if(!CookingSomething) {
				CheckForDoneOrders();
				if(marketIterations < markets.size())
					checkForLowInventory();
			}
		}
		
		return false;
		//we have tried all our rules and found
		//nothing to do. So return false to main loop of abstract agent
		//and wait.
	}
	
	
	// Actions
	
	public void CookIt(int orderIndex) {
		final int index = orderIndex;
		if(foods.get(orders.get(index).choice).isInStock()) {
			System.out.println(getName() + ": Food in Stock!");
			
			foods.get(orders.get(index).choice).amount--;
			
			System.out.println(getName() + ": Cooking " + orders.get(index).choice + " for table " + orders.get(index).table);
			orders.get(index).s = CookingState.Cooking;
			cookGui.setStr(orders.get(index).table -1 , orders.get(index).choice.substring(0, 2).toUpperCase());
			cookGui.setStrPos(orders.get(index).table -1, "right");
			orders.get(index).timer.schedule(new TimerTask() {
				Object cookie = 1;
				public void run() {
					System.out.println(getName() + ": Done cooking" + orders.get(index).choice + ", cookie=" + cookie);
					orders.get(index).s = CookingState.DoneCooking;
					stateChanged();
				}
			},
			foods.get(orders.get(index).choice).cookingTime);
		}
		else {
			System.out.println(getName() + ": No more of that Item");
			orders.get(index).waiter.msgOutOfChoice(orders.get(index).choice, orders.get(index).table);
			orders.remove(index);
		}
	}
	
	public void PlateIt(int orderIndex) {
		Order o = orders.get(orderIndex);
		System.out.println(getName() + ": Plating " + o.choice);
		cookGui.setStr(orders.get(orderIndex).table -1, orders.get(orderIndex).choice.substring(0, 2).toUpperCase());
		cookGui.setStrPos(orders.get(orderIndex).table -1, "left");
		o.s = CookingState.Plated;
		o.waiter.msgOrderDone(o.choice, o.table);
		//orders.remove(orderIndex);
		stateChanged();
	}
	
	
	//This sends market order
	public void orderFoodThatIsLow(){
		Market m = (Market) TheCity.getBuildingFromString("Market"); // add one more market later
    	r2 = (Restaurant2) TheCity.getBuildingFromString("Restaurant 2");
		m.getCashier().INeedFood(deliveryOrder, r2);
	}
	
	
	public void CheckForDoneOrders() {
		for (int x = 0; x < orders.size(); x++) {
			if(orders.get(x).s == CookingState.Plated) {
				orders.remove(x);
			}
		}
	}
	
	public void checkForLowInventory() {
		/*boolean needDelivery = false;
		System.out.println(getName() + ": Checking for low inventory");
		if(foods.get("Steak").amount <= foods.get("Steak").low) {
			deliveryOrder.put("Steak", foods.get("Steak").capacity - foods.get("Steak").amount);
			needDelivery = true;
		}
		else
			deliveryOrder.put("Steak", 0);
		
		if(foods.get("Pizza").amount <= foods.get("Pizza").low) {
			deliveryOrder.put("Pizza", foods.get("Pizza").capacity - foods.get("Pizza").amount);
			needDelivery = true;
		}
		else
			deliveryOrder.put("Pizza", 0);
		
		if(foods.get("Chicken").amount <= foods.get("Chicken").low) {
			deliveryOrder.put("Chicken", foods.get("Chicken").capacity - foods.get("Chicken").amount);
			needDelivery = true;
		}
		else
			deliveryOrder.put("Chicken", 0);
		
		if(foods.get("Salad").amount <= foods.get("Salad").low) {
			deliveryOrder.put("Salad", foods.get("Salad").capacity - foods.get("Salad").amount);
			needDelivery = true;
		}
		else
			deliveryOrder.put("Salad", 0);
		*/
	/*	if(needDelivery && marketIterations < markets.size())
		//	markets.get(marketIndex).msgNeedDelivery(deliveryOrder);
		else {
			deliveryOrder.clear();
		}*/
	}
	
	//utilities
	
	/*public void setGui(CookGui c) {
		cookGui = c;
	}*/
	
	public void setGui(RoleGui g) {
		super.setGui(g);
		cookGui = (CookGui)gui;
	}
	
	public Vector<Item> getInventory() {
		Vector<Item> inventory = new Vector<Item>();
		for(Map.Entry<String, Food> f : foods.entrySet()) {
			inventory.add(new Item(f.getKey(), f.getValue().amount));
		}
		return inventory;
	}
	
	public void updateItem(String s, int hashCode) {
		foods.get(s).amount = hashCode;
	}
	
	class Food {
		String type;
		int cookingTime, amount, capacity, low;
		FoodState s;
		
		public Food(String t) {
			type = t;
			
			switch(t) {
				case "Steak": 
					cookingTime = 11000;
					amount = 45;
					capacity = 100;
					low = 3; break;
				case "Chicken": 
					cookingTime = 9000;
					amount = 45;
					capacity = 100;
					low = 3; break;
				case "Pizza": 
					cookingTime = 6000;
					amount = 45;
					capacity = 100;
					low = 3; break;
				case "Salad": 
					cookingTime = 4000;
					amount = 45;
					capacity = 100;
					low = 4; break;
			}
			
			s = FoodState.notOrdered;
		}
		
		public boolean isInStock() {
			if(amount>0) 
				return true;
			return false;
		}
	}
	public enum FoodState {notOrdered, ordered}
	
	
	
}

