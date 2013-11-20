package simcity.restaurants.restaurant3.src.restaurant;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.Semaphore;

import javax.print.attribute.standard.MediaSize.NA;

import simcity.PersonAgent;
import simcity.agent.Role;
import simcity.restaurants.restaurant3.src.restaurant.Order.OrderState;
import simcity.restaurants.restaurant3.src.restaurant.gui.HostGui;
import simcity.restaurants.restaurant3.src.restaurant.interfaces.Cashier;
import simcity.restaurants.restaurant3.src.restaurant.interfaces.Customer;
import agent.Agent;
//import restaurant.WaiterAgent.myCustomer;

/**
 * Restaurant Host Agent
 */
//We only have 2 types of agents in this prototype. A customer and an agent that
//does all the rest. Rather than calling the other agent a waiter, we called him
//the HostAgent. A Host is the manager of a restaurant who sees that all
//is proceeded as he wishes.
public class CashierRole extends Role implements Cashier {
	public interface cashierBillState {

	}

	//static final int NTABLES = 4;//a global for the number of tables.
	//Notice that we implement waitingCustomers using ArrayList, but type it
	//with List semantics.
	//public List<CustomerAgent> waitingCustomers
	//= new ArrayList<CustomerAgent>();
	//public Collection<Table> tables;
	//note that tables is typed with Collection semantics.
	//Later we will see how it is implemented
	public List<Order> orders = Collections.synchronizedList(new Vector<Order>()); 
	public Map<String, Food> foods = Collections.synchronizedMap(new HashMap<String, Food>());
	private int threshold;
	private String name;
	private Semaphore atTable = new Semaphore(0,true);
	private Food f;
	public HostGui hostGui = null;
	public CookRole cook;
	//private WaiterAgent waiter;
	//CashierState cashState;
	public enum CashierState {idle, calculating}
	CashierState cashState;
	public Map<Customer, Double> owed = Collections.synchronizedMap(new HashMap<Customer, Double>());
	private double restaurantRevenue = 100;
	
	private int inventory = 5;
	 
	public CashierRole() {
		
		//super(person);

		this.name = name;
		cashState = CashierState.idle;
		
		
		// make some tables
		//tables = new ArrayList<Table>(NTABLES);
		//for (int ix = 1; ix <= NTABLES; ix++) {
			//tables.add(new Table(ix));//how you add to a collections
		//}
		
		f = new Food ("Chicken");
		foods.put("Chicken", f);
		
		f = new Food ("Steak");
		foods.put("Steak", f);
		
		f = new Food ("Pizza");
		foods.put("Pizza", f);
		
		f = new Food ("Salad");
		foods.put("Salad", f);
		
	}

	public String getMaitreDName() {
		return name;
	}

	public String getName() {
		return name;
	}

	
	// Messages
	public void msgRequestCheck(Customer cust, Order o) {
		System.out.println("Waiter needs the check from the cashier for his/her customer");
		orders.add(o);
		o.os = OrderState.requestCheck;
		
		stateChanged();		
	}
	
	public void msgCustomerPayingCheck(Order o) {
		
		System.out.println("The Customer is paying the check");
		o.os = OrderState.paying;
		
		stateChanged();
		
	}
	
	public void msgPayMarketBill(double bill) {
		
		this.restaurantRevenue -= bill;
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
		for(Order o:orders) {
			if(o.os == OrderState.requestCheck){
				calculate(o);
				return true;
			}
		}
		
		for(Order o:orders) {
			if(o.os == OrderState.paying){
				receivingCheck(o);
				return true;
			}
		}
		
		return false;
		
		
	}
	
	private void receivingCheck(Order o) {
		o.os = OrderState.idle;
		stateChanged();
		o.customer.setDonePayingState();
		orders.remove(o);
	}
	
	// Actions
	private  void calculate(Order o) {
		System.out.println("calculating order");
		
		// check if in owed list
		if (owed.get(o.customer) != null) {
			double previousBalance = owed.get(o.customer);
			o.setTotalPrice(o.choice.getFoodPrice() + previousBalance);
		}
		else 
			o.setTotalPrice(o.choice.getFoodPrice());
		
		
		o.waiter.msgWaiterHereIsCheck(o);
		//if(orders.size() == 0) {
		o.os = OrderState.idle;
		stateChanged();
		//}
		
	}

	//utilities

	public void setGui(HostGui gui) {
		hostGui = gui;
	}

	public HostGui getGui() {
		return hostGui;
	}

	public void setRestaurantRevenue(double totalPrice) {
		this.restaurantRevenue += totalPrice;
	}
	
	public double getRestaurantRevenue() {
		return restaurantRevenue;
	}
	
	public void setCook(CookRole cook) {
		this.cook = cook;
		
	}
	
}

