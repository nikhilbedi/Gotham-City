package simcity.restaurants.restaurant1;

import Gui.RoleGui;
import agent.Agent;
import simcity.PersonAgent;
import simcity.tests.mock.*;
import simcity.restaurants.restaurant1.RevolvingStand;
import simcity.restaurants.restaurant1.WaiterRole;
import simcity.restaurants.restaurant1.Restaurant1CustomerRole;
import simcity.restaurants.restaurant1.Menu;
import simcity.restaurants.restaurant1.gui.CookGui;
import simcity.restaurants.restaurant1.gui.WaiterGui;
import simcity.restaurants.restaurant1.gui.CookGui.FoodGui;
import simcity.restaurants.restaurant1.interfaces.*;
import trace.AlertLog;
import trace.AlertTag;
import agent.Role;

import java.util.*;
import java.util.concurrent.Semaphore;
import java.util.Collections;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Restaurant Cook Agent
 */
public class CookRole extends Role implements Cook {

	//Notice that we implement MyCustomers using ArrayList, but type it
	//with List semantics.
	public List<Order> orders
	= Collections.synchronizedList(new ArrayList<Order>());

	//implement a queue of markets. Remove when you have sent an order, and add it right back. 
	//That way the "top" function is always different
	/*   public Queue<MarketAgent> markets
	= new PriorityQueue<MarketAgent>();*/
	private List<MarketRole> markets = new ArrayList<MarketRole>();
	private String name;
	private CookGui cookGui;
	private int nextMarket = 1;
	private Timer cookingTimer = new Timer();

	private Map<String,Food> foods = new HashMap<String, Food>() {{
		//the parameters for each food are (String type, int time [in seconds], int amount, int capacity) 
		put("Pizza", new Food("Pizza", 5, 100, 4));
		put("Steak", new Food("Steak", 7, 100, 4));
		put("Salad", new Food("Salad", 3, 100, 4));
	}};

	//creating states outside the Order class
	public enum OrderState {pending, cooking, done, plated, pickedUp, finished};
	public enum FoodState {haveEnough, needToOrderMore, waitingForDeliveries};

	public CookRole(PersonAgent p) {
		super(p);
	}

	public CookRole() {
		super();
	}

	public String getName() {
		return name;
	}

	public List getCurrentOrders() {
		return orders;
	}

	public void addMarket(MarketRole m) {
		markets.add(m);
	}

	// Messages

	//from gui
	public void printFoodCount() {
		for(Food f : foods.values()) {
			print(f.type + " " + f.amount);
		}
	}

	//cook receives an order from the waiter and stores it in a list
	public void hereIsOrder(Waiter waiter, int table, String choice) {
		print("Received order " + choice + " from waiter for table " + table);
		orders.add(new Order(waiter, choice, table));
		stateChanged();
	}

	public void orderIsHere() {
		print("Looks as if there is something on revolving stand");
		stateChanged();
	}

	public void foodDone(Order o) {
		o.state = OrderState.done;
		stateChanged();
	}

	public void pickedUpFood(Waiter w, int table, String choice) {
		synchronized(orders) {
			for(Order o : orders) {
				if(o.waiter == w && o.table == table && o.choice == choice) {
					o.state = OrderState.pickedUp;
					print("Waiter picked up the food.");
					break;
				}
			}
		}
		stateChanged();
	}

	public void canSupply(int amt, String type) {
		//This will tell us if we need to go to another market
		Food temp = foods.get(type);
		temp.pendingAmt += temp.amount + amt;
		//if pendingAmt isn not capacity, then go to next market
		if(temp.pendingAmt >= temp.capacity) {
			temp.state = FoodState.waitingForDeliveries;
		}
		else
			temp.state = FoodState.needToOrderMore;
		if(amt == 0) {
			nextMarket++;
		}
		stateChanged();
	}

	public void foodDelivered(String type, int amount) {
		print("Received delivery of " + amount + " " + type);
		Food item = foods.get(type);
		item.amount += amount;
		if(item.amount > item.low) {
			item.state = FoodState.haveEnough;
		}
		//message waiter to add food to menu
	}

	public void HereIsYourFood(Map<String,Integer> m) {
		//Add items to my inventory TODO
	}

	/**
	 * Scheduler.  Determine what action is called for, and do it.
	 */
	public boolean pickAndExecuteAnAction() {
		if(theManLeavingMe != null && orders.isEmpty()) {
			leaveWork();
			return true;
		}
		
		for(Food f : foods.values()) {
			/* if(f.state == FoodState.needToOrderMore) {
			orderFoodThatIsLow(f.type);
			}*/
			if(f.pendingAmt <= f.low && f.state == FoodState.needToOrderMore) {
				orderFoodThatIsLow(f.type);
				return true;
			}
		}

		synchronized(orders) {
			for (Order o : orders) {
				if(o.state == OrderState.pickedUp) {
					removeOrderHistory(o);
					return true;
				}
				if(o.state == OrderState.done){
					plateFood(o);		
					return true;
				}
				if(o.state == OrderState.pending) {
					tryAndCookIt(o);
					return true;
				}
			}
		}

		if(RevolvingStand.checkStand()) {
			goCheckStand();
			return true;
		}

		return false;
		//we have tried all our rules and found
		//nothing to do. So return false to main loop of abstract agent
		//and wait.
	}

	// Actions
	
	private void goCheckStand() {
		print("checking stand");
		final Order o = RevolvingStand.removeOrder();
		String paperOrder = "";
		if(o != null) {
			paperOrder = o.getChoice().substring(0,2);
			cookGui.addToStand(paperOrder);
			final String temp = paperOrder;
			cookingTimer.schedule(new TimerTask() {
				public void run() {
					print("Taking order of stand and preparing to cook");
					orders.add(o);
					cookGui.removeFromStand(temp);
					stateChanged();
				}
			},
			1200);
		}
	}


	//Food order is cooked and is managed by a timer
	private void tryAndCookIt(Order o) {
		final Order order = o;
		Food customerMeal = foods.get(o.choice);
		if(customerMeal.amount == 0) {
			o.waiter.outOfFood(o.table, o.choice);
			orders.remove(o);
			return;
		}

		//the false is to denote that it is still cooking and shouldnt be laid out in the plating area
		cookGui.foodTypes.add(new CookGui.FoodGui(o.choice.substring(0, 2)));

		customerMeal.amount--;
		customerMeal.pendingAmt--;
		print("I have " + customerMeal.amount + " " + customerMeal.type + " left.");
		o.state = OrderState.cooking;
		//Timer to cook whatever kind of food it is
		cookingTimer.schedule(new TimerTask() {
			public void run() {
				//Find the corresponding GUI food item and put it to plating area
				synchronized(cookGui.foodTypes){
					for(FoodGui f :cookGui.foodTypes){
						if(f.type.equals(order.choice.substring(0,2)) && f.plated == false)
							f.plated = true;	
					}
				}
				foodDone(order);
			}
		},
		customerMeal.cookingTime*1000);
		//if that certain food is low, message the market
		if(customerMeal.amount <= customerMeal.low) {
			print(o.choice + " is low. I will ask the market for more");
			// orderFoodThatIsLow(o.choice);
			customerMeal.state = FoodState.needToOrderMore;
		}
	}

	//Waiter is notifed of the cooked order after the timer has run, and the cook has changed the status of the order
	private void plateFood(Order o) {
		o.state = OrderState.plated;
		//The correct cooked food order is given to the correct order
		o.waiter.orderDone(o.table, o.choice);
	}

	private void removeOrderHistory(Order o) {
		o.state = OrderState.finished;
		/*Iterator<Map.Entry<String, Boolean>> iter = cookGui.foodTypes.entrySet().iterator();
    	while(iter.hasNext()) {
    		Map.Entry<String, Boolean> entry = iter.next();
    		if(entry.getKey().equals(o.choice.substring(0,2)) && entry.getValue() == true)
				iter.remove();		
    	}*/
		for(FoodGui f :cookGui.foodTypes){
			if(f.type.equals(o.choice.substring(0,2)) && f.plated == true) {
				cookGui.foodTypes.remove(f);
				break;
			}
		}
	}

	private void orderFoodThatIsLow(String item) {
		//the parameter below is a way of cycling through different markets
		//MarketRole m = markets.get(nextMarket % markets.size());
		MarketRole m = new MarketRole("market");
		Food f = foods.get(item);
		Map<String, Integer> mapToSend = new HashMap<String, Integer>();
		for(Food f1: foods.values()) {
			if(f1.amount< f1.low){
				int temp = f.capacity - f.pendingAmt;
				if(temp > 0) {
					mapToSend.put(f1.type, temp);
					f.state = FoodState.waitingForDeliveries;
				}
			}
		}
		if(!mapToSend.isEmpty()) {
			//marketCashier.INeedFood(mapToSend, this, cashier);
		}
		//nextMarket++;
	}

	public String toString() {
		return "cook " + getName();
	}

	public void setGui(RoleGui g) {
		super.setGui(g);
/*		AlertLog.getInstance().logInfo(AlertTag.GUI, "CookROle",
				"At this point: " + g.toString());*/
		cookGui = (CookGui) g;
	}


	public CookGui getGui() {
		return cookGui;
	}


	//Food and Order classes properly created and used
	class Order {
		Waiter waiter;
		String choice;
		int table;
		public OrderState state = OrderState.pending;

		public Order(Waiter w, String c, int t) {
			waiter = w;
			choice = c;
			table = t;
		}
		public String getChoice() {
			return choice;
		}
	}
	class Food {
		public String type;
		public int cookingTime;
		public int amount;
		public int pendingAmt;
		public int low = 2;
		public int capacity = 10;
		public double price;
		public FoodState state = FoodState.needToOrderMore;

		public Food(int time, double cost) {
			cookingTime = time;
			price = cost;
			amount = 3;
		}

		public Food(String t, int time, int amt, int fill) {
			type = t;
			cookingTime = time;
			amount = amt;
			pendingAmt = amt;
			capacity = fill;
		}

		public Food(Food food) {
			cookingTime = food.cookingTime;
			price = food.price;
		}
	}
}