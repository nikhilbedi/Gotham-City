package simcity.restaurants.restaurant3;

import java.util.*;
import java.util.concurrent.Semaphore;
import java.util.Collections;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

import javax.print.attribute.standard.MediaSize.NA;

import simcity.PersonAgent;
import agent.Role;
import simcity.restaurants.*;
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
	 
	public Restaurant3(String type, int entranceX, int entranceY, int guiX,
            int guiY) {
		super(type, entranceX, entranceY, guiX, guiY);

		this.name = type;
		cashState = CashierState.idle;
		
		
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
	public void setCook(CookRole cook) {
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
		this.cashier = (CashierRole)cashier;
	}
	
	@Override
	public Role getCashier() {
		return (CashierRole)cashier;
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

