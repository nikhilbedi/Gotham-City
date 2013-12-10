package simcity.restaurants.restaurant3;

import java.util.*;
import java.util.concurrent.Semaphore;
import java.util.Collections;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

import javax.print.attribute.standard.MediaSize.NA;

import simcity.CityClock;
import simcity.Item;
import simcity.PersonAgent;
import Gui.RoleGui;
import agent.Role;
import simcity.restaurants.*;
import simcity.restaurants.restaurant3.*;
import simcity.restaurants.restaurant3.Order.OrderState;
import simcity.restaurants.restaurant3.gui.CashierGui;
import simcity.restaurants.restaurant3.gui.CookGui;
import simcity.restaurants.restaurant3.gui.HostGui;
import simcity.restaurants.restaurant3.gui.WaiterGui;
import simcity.restaurants.restaurant3.interfaces.*;
import trace.AlertLog;
import trace.AlertTag;
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
	CashierGui cashierGui = new CashierGui(cashier, ScreenFactory.getMeScreen("Restaurant 3"));
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

	Vector<Item> inventory = new Vector<Item>();

	public Restaurant3(String type, int entranceX, int entranceY, int guiX,
			int guiY) {
		super(type, entranceX, entranceY, guiX, guiY);

		this.name = type;
		cashState = CashierState.idle;

		setWeekdayHours(6,24);
		setWeekendHours(2,10);


		((HostRole) host).setGui((RoleGui)hostGui);
		((Restaurant3CashierRole) cashier).setGui((RoleGui)cashierGui);
		((WaiterRole) waiter1).setGui((RoleGui)waiterGui1);
		((WaiterSharedData) waiter2).setGui((RoleGui)waiterGui2);
		((WaiterRole) waiter3).setGui((RoleGui)waiterGui3);
		((WaiterSharedData) waiter4).setGui((RoleGui)waiterGui4);
		((Restaurant3CookRole) cook).setGui((RoleGui)cookGui);

		//Nikhil is setting these for you
		((HostRole) host).setWaiter(waiter1);
		((HostRole) host).setWaiter(waiter2);
		((HostRole) host).setWaiter(waiter3);
		((HostRole) host).setWaiter(waiter4);
		((WaiterRole) waiter1).setHost((HostRole) host);
		((WaiterSharedData) waiter2).setHost((HostRole) host);
		((WaiterRole) waiter3).setHost((HostRole) host);
		((WaiterSharedData) waiter4).setHost((HostRole) host);
		((WaiterRole) waiter1).setCashier((Restaurant3CashierRole) cashier);
		((WaiterSharedData) waiter2).setCashier((Restaurant3CashierRole) cashier);
		((WaiterRole) waiter3).setCashier((Restaurant3CashierRole) cashier);
		((WaiterSharedData) waiter4).setCashier((Restaurant3CashierRole) cashier);
		((WaiterRole) waiter1).setCook((Restaurant3CookRole) cook);
		((WaiterSharedData) waiter2).setCook((Restaurant3CookRole) cook);
		((WaiterRole) waiter3).setCook((Restaurant3CookRole) cook);
		((WaiterSharedData) waiter4).setCook((Restaurant3CookRole) cook);
		
		jobRoles.put("Host", (HostRole)host);

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
		
	}

	@Override
	public boolean isOpen() {
		//Weekday
		if(CityClock.getDay() != 0 && CityClock.getDay() !=6) {
			if(CityClock.getTime() > weekdayOpen && CityClock.getTime() < weekdayClose) {
				if(((Role)host).checkWorkStatus() && ((Role)cashier).checkWorkStatus() && ((Role)cook).checkWorkStatus()) {
					if(((Role)waiter1).checkWorkStatus() || ((Role)waiter2).checkWorkStatus()
							|| ((Role)waiter3).checkWorkStatus() || ((Role)waiter4).checkWorkStatus()) {
						return true;
					}
				}
			}
		}
		//weekend
		else {
			if(CityClock.getTime() > weekendOpen && CityClock.getTime() < weekendClose){
				if(((Role)host).checkWorkStatus() && ((Role)cashier).checkWorkStatus() && ((Role)cook).checkWorkStatus()) {
					if(((Role)waiter1).checkWorkStatus() || ((Role)waiter2).checkWorkStatus()
							|| ((Role)waiter3).checkWorkStatus() || ((Role)waiter4).checkWorkStatus()) {
						return true;
					}
				}
			}
		}
		return false;
	}

	//utilities

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
	public Restaurant3CashierRole getCashier() {
		return (Restaurant3CashierRole)cashier;
	}
	
	public Restaurant3CookRole getCook() {
		return (Restaurant3CookRole)cook;
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
	public Vector<Item> getStockItems(){
		inventory = ((Restaurant3CookRole) cook).getInventory();
		/*AlertLog.getInstance().logInfo(AlertTag.GUI, "Rest 3",
				inventory.toString());*/
		return inventory;
	}

	public void updateItem(String s, int hashCode) {
		// TODO Auto-generated method stub
		//THIS MUST BE UPDATED BY YOUR BUILDING
		((Restaurant3CookRole) cook).updateItem(s, hashCode);
	}

}

