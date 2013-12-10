package simcity.restaurants.restaurant3;

import java.util.*;
import java.util.concurrent.Semaphore;
import java.util.Collections;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

import javax.print.attribute.standard.MediaSize.NA;

import simcity.PersonAgent;
import Gui.RoleGui;
import agent.Role;
import simcity.restaurants.*;
import simcity.restaurants.restaurant3.*;
import simcity.restaurants.restaurant3.Order.OrderState;
import simcity.restaurants.restaurant3.gui.CookGui;
import simcity.restaurants.restaurant3.gui.HostGui;
import simcity.restaurants.restaurant3.gui.WaiterGui;
import simcity.restaurants.restaurant3.interfaces.*;
import agent.Agent;
import Gui.ScreenFactory;

/**
 * Evan's restaurant
 * @author Evan Coutre
 *
 */

public class Restaurant3 extends Restaurant {
	//create roles
	Host host = new HostRole();
	Cashier cashier = new Restaurant3CashierRole();
	Waiter waiter1 = new WaiterRole();
	Waiter waiter2 = new WaiterSharedData();
	Waiter waiter3 = new WaiterRole();
	Waiter waiter4 = new WaiterSharedData();
	Cook cook = new Restaurant3CookRole(); 
	
	
	//create guis
	HostGui hostGui = new HostGui(host, ScreenFactory.getMeScreen("Restaurant 3"));
	//CashierGui cashierGui = new CashierGui(cashier, ScreenFactory.getMeScreen(this.getName()));
	WaiterGui waiterGui1 = new WaiterGui(waiter1, ScreenFactory.getMeScreen("Restaurant 3"));
	WaiterGui waiterGui2 = new WaiterGui(waiter2, ScreenFactory.getMeScreen("Restaurant 3"));
	WaiterGui waiterGui3 = new WaiterGui(waiter3, ScreenFactory.getMeScreen("Restaurant 3"));
	WaiterGui waiterGui4 = new WaiterGui(waiter4, ScreenFactory.getMeScreen("Restaurant 3"));
	
	CookGui cookGui = new CookGui(cook, ScreenFactory.getMeScreen("Restaurant 3"));
	
	//public HostRole host = new HostRole();
	//public Restaurant3CashierRole cashier = new Restaurant3CashierRole();
	//public Restaurant3CookRole cook = new Restaurant3CookRole();
   
    //List<WaiterRole> waiters = new ArrayList<WaiterRole>();
    //public WaiterRole waiter1 = new WaiterRole();
    //public WaiterSharedData waiter2 = new WaiterSharedData();
  
	
	public List<Order> orders = Collections.synchronizedList(new Vector<Order>()); 
	public Map<String, Food> foods = Collections.synchronizedMap(new HashMap<String, Food>());
	private String name;
	private Food f;
	//public HostGui hostGui = null;
	//private WaiterAgent waiter;
	//CashierState cashState;
	public enum CashierState {idle, calculating}
	CashierState cashState;
	public Map<Customer, Double> owed = Collections.synchronizedMap(new HashMap<Customer, Double>());
	private double restaurantRevenue = 100;

	 
	public Restaurant3(String type, int entranceX, int entranceY, int guiX,
            int guiY) {
		super(type, entranceX, entranceY, guiX, guiY);

		this.name = type;
		cashState = CashierState.idle;
		
		setWeekdayHours(6,24);
		setWeekendHours(0, 0);
		

		((HostRole) host).setGui((RoleGui)hostGui);
		//((Restaurant3CashierRole) cashier).setGui((RoleGui)cashierGui);
		((WaiterRole) waiter1).setGui((RoleGui)waiterGui1);
		((WaiterSharedData) waiter2).setGui((RoleGui)waiterGui2);
		((WaiterRole) waiter3).setGui((RoleGui)waiterGui3);
		((WaiterSharedData) waiter4).setGui((RoleGui)waiterGui4);
		((Restaurant3CookRole) cook).setGui((RoleGui)cookGui);
		
		jobRoles.put("Host", (Role)host);
		//jobRoles.put("Host Late",  (Role)host);

		jobRoles.put("Cashier",(Role)cashier);
		//jobRoles.put("Cashier Late", (Role)cashier);
		
		jobRoles.put("Waiter1",(Role)waiter1);
		//jobRoles.put("Waiter1 Late", (Role)waiter1);
		
		jobRoles.put("Waiter2",(Role)waiter2);
		//jobRoles.put("Waiter2 Late", (Role)waiter2);

		jobRoles.put("Waiter3",(Role)waiter3);
		//jobRoles.put("Waiter3 Late", (Role)waiter3);
		

		jobRoles.put("Waiter4",(Role)waiter4);
		//jobRoles.put("Waiter4 Late", (Role)waiter4);
		
		
		jobRoles.put("Cook",(Role)cook);
		//jobRoles.put("Cook Late", (Role)cook);
		
		//Set the open and closing hours
        //setWeekdayHours(6, 24);
        //setWeekendHours(0, 0);
    /*    //Add the key: strings & value: roles
       // Map<String, Role> jobs = Collections.synchronizedMap(new HashMap<String, Role>());
        jobsRoles.put("Host", (Role) host);
        jobs.put("Cashier", (Role) cashier);
        jobs.put("Cook", (Role) cook);
        jobs.put("Waiter1", (Role) waiter1);
        jobs.put("Waiter2", (Role) waiter2);
        jobs.put("Waiter3", (Role) waiter3);
        jobs.put("Waiter4", (Role) waiter4);
        //setJobRoles(jobs);
*/        
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
	
	public Vector<String> getBuildingInfo(){
		Vector<String> info = new Vector<String>();
		info.add("Restaurant 3");
		info.add("Created by: Evan Coutre");
		info.add("this is even more super class info");
		return info;
	}
	
}

