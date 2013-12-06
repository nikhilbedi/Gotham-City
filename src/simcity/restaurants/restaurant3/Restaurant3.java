package simcity.restaurants.restaurant3;

import java.util.*;
import java.util.concurrent.Semaphore;
import java.util.Collections;
import java.util.Timer;
import java.util.TimerTask;

import javax.print.attribute.standard.MediaSize.NA;

import simcity.PersonAgent;
import agent.Role;
import simcity.restaurants.*;
import simcity.restaurants.restaurant3.Restaurant3CashierRole;
import simcity.restaurants.restaurant3.HostRole;
import simcity.restaurants.restaurant3.*;
import simcity.restaurants.restaurant3.Order.OrderState;
import simcity.restaurants.restaurant3.gui.HostGui;
import simcity.restaurants.restaurant3.interfaces.*;
import agent.Agent;

/**
 * Evan's restaurant
 * @author Evan Coutre
 *
 */

public class Restaurant3 extends Restaurant {
	
	public List<Order> orders = Collections.synchronizedList(new Vector<Order>()); 
	public Map<String, Food> foods = Collections.synchronizedMap(new HashMap<String, Food>());
	private String name;
	private Food f;
	public HostGui hostGui = null;
	//private WaiterAgent waiter;
	//CashierState cashState;
	public enum CashierState {idle, calculating}
	CashierState cashState;
	public Map<Customer, Double> owed = Collections.synchronizedMap(new HashMap<Customer, Double>());
	private double restaurantRevenue = 100;
	
	public HostRole host = new HostRole();
    public Restaurant3CashierRole cashier = new Restaurant3CashierRole();
    //List<WaiterRole> waiters = new ArrayList<WaiterRole>();
    public WaiterRole waiter1 = new WaiterRole();
    public WaiterSharedData waiter2 = new WaiterSharedData();
    public Restaurant3CookRole cook = new Restaurant3CookRole();
	
	
	 
	public Restaurant3(String type, int entranceX, int entranceY, int guiX,
            int guiY) {
		super(type, entranceX, entranceY, guiX, guiY);

		this.name = type;
		cashState = CashierState.idle;
		
		//Set the open and closing hours
        //setWeekdayHours(6, 24);
        //setWeekendHours(0, 0);
        //Add the key: strings & value: roles
        Map<String, Role> jobs = Collections.synchronizedMap(new HashMap<String, Role>());
        jobs.put("host", host);
        jobs.put("cashier", cashier);
        jobs.put("cook", cook);
        jobs.put("waiter1", waiter1);
        jobs.put("waiter2", waiter2);
        //setJobRoles(jobs);
        
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
	//@Override
	public void setCook(Restaurant3CookRole cook) {
		this.cook = cook;
		
	}
	@Override
	public void setHost(Role host) {
		this.host = (HostRole) host;
	}
	
	@Override
	public Role getHost() {
		return (HostRole)host;
	}
	
	@Override
	public void setCashier(Role cashier) {
		this.cashier = (Restaurant3CashierRole)cashier;
	}
	
	@Override
	public Role getCashier() {
		return (Restaurant3CashierRole)cashier;
	}
	
	@Override
	public String getCustomerName(){
		return "restaurant3customer";
	}
	
}

