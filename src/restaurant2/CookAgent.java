package restaurant2;

import agent.Agent;
//import restaurant.WaiterAgent;





import agent.Role;
import restaurant2.CustomerAgent.AgentEvent;
import restaurant2.gui.CookGui;
import restaurant2.gui.HostGui;
import restaurant2.interfaces.Cook;
import restaurant2.interfaces.Waiter;

import java.util.*;
import java.util.concurrent.Semaphore;

/**
 * Restaurant Cook Agent
 */
public class CookAgent extends Role implements Cook{
	List<Order> orders = new LinkedList<Order>();
	List<Integer> deliveryOrder = new LinkedList<Integer>();
	List<Market> markets = new LinkedList<Market>();
	
	Map <String, Food> foods = new HashMap<String, Food>();
	CookGui cookGui;
	
	int marketIndex = 0, marketIterations;
	private String name;
	//Timer timer = new Timer();
	public HostGui hostGui = null;

	public CookAgent(String name) {
		super();
		
		foods.put("Steak", new Food("Steak"));
		foods.put("Chicken", new Food("Chicken"));
		foods.put("Salad", new Food("Salad"));
		foods.put("Pizza", new Food("Pizza"));
		
		this.name = name;
		
		
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
	
	public void msgPickedUpOrder(int table) {
		cookGui.setStr(table-1, "");
	}
	
	public void msgHereIsInventoryReport(List<Integer> inventoryDifference) {
		print("Recieved Inventory Report");
		
		marketIndex++;
		if(marketIndex == markets.size())
			marketIndex = 0;
		
		marketIterations++;
	}
	
	public void msgHereIsDelivery(Map<String, Integer> foodsToDeliver) {
		print("Recieved Delivery.");
		
		foods.get("Steak").amount += foodsToDeliver.get("Steak");
		foods.get("Pizza").amount += foodsToDeliver.get("Pizza");
		foods.get("Chicken").amount += foodsToDeliver.get("Chicken");
		foods.get("Salad").amount += foodsToDeliver.get("Salad");
		
		print("New Stock List:");
		print("Steak: " + foods.get("Steak").amount);
		print("Pizza: " + foods.get("Pizza").amount);
		print("Chicken: " + foods.get("Chicken").amount);
		print("Salad: " + foods.get("Salad").amount);
		
		deliveryOrder.clear();
		
		//marketIterations = 0;
		
		checkForLowInventory();
	}
	
	/**
	 * Scheduler.  Determine what action is called for, and do it.
	 */
	protected boolean pickAndExecuteAnAction() {
		/* Think of this next rule as:
            Does there exist a table and customer,
            so that table is unoccupied and customer is waiting.
            If so seat him at the table.
		 */
		
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
			print("Food in Stock!");
			
			foods.get(orders.get(index).choice).amount--;
			
			print("Cooking " + orders.get(index).choice + " for table " + orders.get(index).table);
			orders.get(index).s = CookingState.Cooking;
			cookGui.setStr(orders.get(index).table -1 , orders.get(index).choice.substring(0, 2).toUpperCase());
			cookGui.setStrPos(orders.get(index).table -1, "right");
			orders.get(index).timer.schedule(new TimerTask() {
				Object cookie = 1;
				public void run() {
					print("Done cooking" + orders.get(index).choice + ", cookie=" + cookie);
					orders.get(index).s = CookingState.DoneCooking;
					stateChanged();
				}
			},
			foods.get(orders.get(index).choice).cookingTime);
		}
		else {
			print("No more of that Item");
			orders.get(index).waiter.msgOutOfChoice(orders.get(index).choice, orders.get(index).table);
			orders.remove(index);
		}
		
		/*print("Cooking " + orders.get(index).choice + " for table " + orders.get(index).table);
		orders.get(index).s = CookingState.Cooking;
		orders.get(index).timer.schedule(new TimerTask() {
			Object cookie = 1;
			public void run() {
				print("Done cooking" + orders.get(index).choice + ", cookie=" + cookie);
				orders.get(index).s = CookingState.DoneCooking;
				stateChanged();
			}
		},
		foods.get(orders.get(index).choice).cookingTime);*/
	}
	
	public void PlateIt(int orderIndex) {
		Order o = orders.get(orderIndex);
		print("Plating " + o.choice);
		cookGui.setStr(orders.get(orderIndex).table -1, orders.get(orderIndex).choice.substring(0, 2).toUpperCase());
		cookGui.setStrPos(orders.get(orderIndex).table -1, "left");
		o.s = CookingState.Plated;
		o.waiter.msgOrderDone(o.choice, o.table);
		//orders.remove(orderIndex);
		stateChanged();
	}
	
	public void CheckForDoneOrders() {
		for (int x = 0; x < orders.size(); x++) {
			if(orders.get(x).s == CookingState.Plated) {
				orders.remove(x);
			}
		}
	}
	
	public void checkForLowInventory() {
		boolean needDelivery = false;
		//deliveryOrder.clear();
		print("Checking for low inventory");
		if(foods.get("Steak").amount <= foods.get("Steak").low) {
			deliveryOrder.add(foods.get("Steak").capacity - foods.get("Steak").amount);
			needDelivery = true;
		}
		else
			deliveryOrder.add(0);
		
		if(foods.get("Pizza").amount <= foods.get("Pizza").low) {
			deliveryOrder.add(foods.get("Pizza").capacity - foods.get("Pizza").amount);
			needDelivery = true;
		}
		else
			deliveryOrder.add(0);
		
		if(foods.get("Chicken").amount <= foods.get("Chicken").low) {
			deliveryOrder.add(foods.get("Chicken").capacity - foods.get("Chicken").amount);
			needDelivery = true;
		}
		else
			deliveryOrder.add(0);
		
		if(foods.get("Salad").amount <= foods.get("Salad").low) {
			deliveryOrder.add(foods.get("Salad").capacity - foods.get("Salad").amount);
			needDelivery = true;
		}
		else
			deliveryOrder.add(0);
		
		//for(int x = 0; x < deliveryOrder.size(); x++)
			//print("Food " + x + " amount is " + deliveryOrder.get(x));
		
		if(needDelivery && marketIterations < markets.size())
			markets.get(marketIndex).msgNeedDelivery(deliveryOrder);
		else {
			deliveryOrder.clear();
			//marketIterations = 0;
		}
	}
	
	//utilities
	
	public void setGui(CookGui c) {
		cookGui = c;
	}
	
	class Order {
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
	}
	
	public enum CookingState {Pending, Cooking, DoneCooking, Plated}; 
	
	class Food {
		String type;
		int cookingTime, amount, capacity, low;
		FoodState s;
		
		public Food(String t) {
			type = t;
			
			switch(t) {
				case "Steak": 
					cookingTime = 11000;
					amount = 4;
					capacity = 10;
					low = 3; break;
				case "Chicken": 
					cookingTime = 9000;
					amount = 0;
					capacity = 12;
					low = 3; break;
				case "Pizza": 
					cookingTime = 6000;
					amount = 0;
					capacity = 8;
					low = 3; break;
				case "Salad": 
					cookingTime = 4000;
					amount = 5;
					capacity = 15;
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

