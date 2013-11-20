package simcity.restaurant_evan.src.restaurant;

import java.util.ArrayList;
import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.Semaphore;

import simcity.PersonAgent;
import simcity.agent.Role;
import simcity.restaurant_evan.src.restaurant.Order.OrderState;
import simcity.restaurant_evan.src.restaurant.gui.CookGui;
import simcity.restaurant_evan.src.restaurant.gui.HostGui;
import agent.Agent;
//import restaurant.WaiterAgent.myCustomer;

/**
 * Restaurant Host Agent
 */
public class CookRole extends Role {
	
	public List<Order> orders = Collections.synchronizedList(new Vector<Order>()); 
	public Map<String, Food> foods = Collections.synchronizedMap(new HashMap<String, Food>());
	public List<MarketRole> markets = Collections.synchronizedList(new ArrayList<MarketRole>());
	private int threshold;
	private String name;
	private Semaphore atTable = new Semaphore(0,true);
	private Food f;
	public HostGui hostGui = null;
	public CashierRole cashier;
	
	//private WaiterAgent waiter;
	
	 	 
	public CookRole() {
		

		this.name = name;
		// make some tables
		//tables = new ArrayList<Table>(NTABLES);
		//for (int ix = 1; ix <= NTABLES; ix++) {
			//tables.add(new Table(ix));//how you add to a collections
		//}
		
		Food f = new Food ("Chicken");
		foods.put("Chicken", f);
		
		f = new Food ("Steak");
		foods.put("Steak", f);
		
		f = new Food ("Pizza");
		foods.put("Pizza", f);
		
		f = new Food ("Salad");
		foods.put("Salad", f);
		
		MarketRole market = new MarketRole();
		markets.add(market);
		market = new MarketRole();
		markets.add(market);
		market = new MarketRole();
		markets.add(market);
		
	}

	public String getMaitreDName() {
		return name;
	}

	public String getName() {
		return name;
	}

	
	// Messages

	public void msgHereIsOrder(Order o) {
		
		System.out.println("waiter gives " + this.getName() + " an order of from customer" );
		orders.add(o);
		o.os = OrderState.pending;
		stateChanged();
	
		//orders.add(new Order(w, choice, table, OrderState.pending))
	}
	
	public void msgFoodDone(Order o) {
		System.out.println("The food is done cooking");
		o.os = OrderState.doneCooking;
		stateChanged();
	}
	/**
	 * Scheduler.  Determine what action is called for, and do it.
	 */
	  
	public boolean pickAndExecuteAnAction() {
	//	synchronized(orders) {
		try {
		for (Order o: orders) {
			if(o.os == OrderState.outOfInventory) {
				messageWaiterOutOfInventory(o);
				System.out.println("OUT OF INVENTORY");
				return true;
			}
	//	}
		}
	//	synchronized (orders){
		for (Order o: orders) {
			if(o.os == OrderState.doneCooking) {
				plateIt(o);
				return true;
			}
		}
	//	}
	//	synchronized(orders) {
		for (Order o: orders) {
			if(o.os == OrderState.pending) {
				tryToCookIt(o); 
				return true;
				}
			}
	//	}
	} catch (ConcurrentModificationException e) {
		e.printStackTrace();
	}
		return false;
		
	}
	// Actions

	private void messageWaiterOutOfInventory(Order o) {
		System.out.println("Out of inventory!!");
		o.os = OrderState.reordering;
		o.waiter.msgWaiterOutOfFood(o);
		
	}
	
	private void plateIt(Order o) {
		System.out.println(this.getName() + " is plating the food");
		DoPlating(o);
		o.waiter.msgOrderIsReady(o); 
		orders.remove(o);
	}
	
	private void tryToCookIt(Order o) {
		//print("cookIt action");
		
		Food f = foods.get(o.choice.getType());
		
		if (checkInventory(f)) {
			//print("cook is cooking the food");
			int amount = f.getAmount() - 1;
			f.setAmount (amount);			
			DoCooking(o);
			System.out.println(this.getName() + " is cooking the food");
			o.os = OrderState.cooking;
			CookGui.order = o.choice.getType();
			CookGui.tableNumber = o.tableNumber;
			CookGui.cooking = true;
			CookGui.plating = false;
			DoCooking(o); 
			msgFoodDone(o);
			
			// check low threshold
			if (amount <= f.getLowThreshold()) { 
				OrderFoodThatIsLow(f);
			}
		}
		else {
			o.os = OrderState.outOfInventory;
			o.outOfStock = true;
			System.out.println(this.getName() + " has run out of " + o.choice);
			stateChanged();
		}
	
		
		//if(f.amount == 0) {
			//o.waiter.outOfFood(o.table, o.choice);
			//orders.remove(o);
		//}
		
		//stateChanged();
		
		//timer.start(run(foodDone(o)));
		//foods.get(o.choice);
	}
	

	private void OrderFoodThatIsLow(Food f) {
		
		/*HashMap<String, Integer> groceryList = new HashMap<String, Integer>(); //Map of grocery list
		for(Map.Entry<String, Food> entry : foods.entrySet()) {					// iterate through map
			if(entry.getValue().getAmount()<entry.getValue().getLowThreshold()) { //Food.amount<food.lowthreshold
				groceryList.put(entry.getValue().getType(), entry.getValue().getAmount()); //groceryList.put(food.type, food.getCapacity)
			}
		}*/
		System.out.println(this.getName() + " is ordering from the market.");
		int amountNeeded = f.getCapacity() - f.getAmount();
		double bill = 0;
		
		// Market Order
		MarketOrder mo = new MarketOrder(f.getType(),amountNeeded);
		
		for (int i = 0; i < 3; ++i) {
			
			mo = markets.get(i).msgOrderRestock(mo);
			
			if (mo == null) {
				System.out.println("NO MORE FOOD LEFT IN THE TOWN");
				return;
			}
			
			else if (mo.amountNeeded == mo.amountProvided) {
				bill += mo.bill; 
				break;
			}
				
			// go to the next market
			else if (mo.amountNeeded > mo.amountProvided) {
				mo.amountNeeded -= mo.amountProvided;
				bill += mo.bill; 
			}
		}
			cashier.msgPayMarketBill(bill);
	}
	
	private boolean checkInventory(Food f) {
		//print("checking Inventory");
		
		int amount = f.getAmount(); 
		if (amount > 0) {
			return true;
		}
		return false;
	}

	//utilities

	private void DoCooking(Order o) {
		System.out.println("Do Cooking");
		int cookingTime = o.choice.getCookingTime();
		try {
			Thread.currentThread().sleep(cookingTime*250);
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	private void DoPlating(Order o) {
		//int cookingTime = o.choice.getCookingTime();
		try {
			CookGui.cooking = false;
			CookGui.plating = true;
			Thread.currentThread().sleep(2000);
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	public void setGui(HostGui gui) {
		hostGui = gui;
	}

	public HostGui getGui() {
		return hostGui;
	}
	
	public void setMarket(MarketRole market){
		//this.market = market;
	}

	public void setCashier(CashierRole cashier) {
		this.cashier = cashier;
		
	}
}

