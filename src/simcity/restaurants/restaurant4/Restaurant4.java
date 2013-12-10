package simcity.restaurants.restaurant4;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;
import java.util.Map;

import Gui.ScreenFactory;
import agent.Role;
import simcity.Building;
import simcity.Item;
import simcity.Location;

import Gui.ScreenFactory;
import agent.Role;
import simcity.Building;
import simcity.CityClock;

import simcity.TheCity;
import simcity.Market.Market;
import simcity.Market.MarketCashierRole;
import simcity.restaurants.Restaurant;
import simcity.restaurants.restaurant4.Restaurant4Gui.Restaurant4CashierGui;
import simcity.restaurants.restaurant4.Restaurant4Gui.Restaurant4CookGui;
import simcity.restaurants.restaurant4.Restaurant4Gui.Restaurant4HostGui;
import simcity.restaurants.restaurant4.Restaurant4Gui.Restaurant4WaiterGui;
import simcity.restaurants.restaurant4.interfaces.Restaurant4Cashier;
import simcity.restaurants.restaurant4.interfaces.Restaurant4Cook;
import simcity.restaurants.restaurant4.interfaces.Restaurant4Host;
import simcity.restaurants.restaurant4.interfaces.Restaurant4Waiter;
import simcity.restaurants.restaurant5.CookRole;
import trace.AlertLog;
import trace.AlertTag;

public class Restaurant4 extends Restaurant {
	Restaurant4HostRole host = new Restaurant4HostRole();
	Restaurant4WaiterAgent waiter = new Restaurant4WaiterRole();
	Restaurant4WaiterAgent sharedDataWaiter = new Restaurant4SharedDataWaiterRole();
	Restaurant4WaiterAgent waiter2 = new Restaurant4WaiterRole();
	Restaurant4WaiterAgent sharedDataWaiter2 = new Restaurant4SharedDataWaiterRole();
	Restaurant4CashierRole cashier = new Restaurant4CashierRole();
	Restaurant4CookRole cook = new Restaurant4CookRole();
	public TheCity cp;
	private Menu menu;

	//gui
	Restaurant4WaiterGui waiterGui1 = new Restaurant4WaiterGui(waiter, 1, ScreenFactory.getMeScreen(this.getName()));
	Restaurant4WaiterGui waiterGui2 = new Restaurant4WaiterGui(sharedDataWaiter, 2, ScreenFactory.getMeScreen(this.getName()));
	Restaurant4WaiterGui waiterGui3 = new Restaurant4WaiterGui(waiter2, 3, ScreenFactory.getMeScreen(this.getName()));
	Restaurant4WaiterGui waiterGui4 = new Restaurant4WaiterGui(sharedDataWaiter2, 4, ScreenFactory.getMeScreen(this.getName()));
	Restaurant4CookGui cookGui = new Restaurant4CookGui(cook, ScreenFactory.getMeScreen(this.getName()));
	Restaurant4HostGui hostGui = new Restaurant4HostGui(host, ScreenFactory.getMeScreen(this.getName()));
	Restaurant4CashierGui cashierGui = new Restaurant4CashierGui(cashier, ScreenFactory.getMeScreen(this.getName()));

	Vector<Item> inventory = new Vector<Item>();
	

	public Restaurant4(String type, int entranceX, int entranceY, int guiX,
			int guiY) {
		super(type, entranceX, entranceY, guiX, guiY);
		waiter.setGui(waiterGui1);
		sharedDataWaiter.setGui(waiterGui2);
		waiter2.setGui(waiterGui3);
		sharedDataWaiter2.setGui(waiterGui4);
		cook.setGui(cookGui);
		host.setGui(hostGui);
		cashier.setGui(cashierGui);
		setWeekdayHours(2, 24);
		setWeekendHours(2, 24);
		Map<String, Role> jobs = Collections.synchronizedMap(new HashMap<String, Role>());
		jobs.put("Host", host);
		jobs.put("Cashier", cashier);
		jobs.put("Cook", cook);
		jobs.put("Waiter1", waiter);
		jobs.put("Waiter2", sharedDataWaiter);
		jobs.put("Waiter3", waiter2);
		jobs.put("Waiter4", sharedDataWaiter2);
		setJobRoles(jobs);
		host.setWaiter((Restaurant4Waiter) waiter);
		host.setWaiter((Restaurant4Waiter) sharedDataWaiter);
		host.setWaiter((Restaurant4Waiter) waiter2);
		host.setWaiter((Restaurant4Waiter) sharedDataWaiter2);
		waiter.setHost(host);
		sharedDataWaiter.setHost(host);
		waiter2.setHost(host);
		sharedDataWaiter2.setHost(host);
		waiter.setCashier(cashier);
		sharedDataWaiter.setCashier(cashier);
		waiter2.setCashier(cashier);
		sharedDataWaiter2.setCashier(cashier);
		waiter.setCook(cook);
		sharedDataWaiter.setCook(cook);
		waiter2.setCook(cook);
		sharedDataWaiter2.setCook(cook);
		cook.setCashier(cashier);
	}	

	@Override
	public boolean isOpen() {
		//Weekday
		if(CityClock.getDay() != 0 && CityClock.getDay() !=6) {
			if(CityClock.getTime() > weekdayOpen && CityClock.getTime() < weekdayClose) {
				if(host.checkWorkStatus() && cashier.checkWorkStatus() && cook.checkWorkStatus()) {
					if(waiter.checkWorkStatus() || sharedDataWaiter.checkWorkStatus()
							|| waiter2.checkWorkStatus() || sharedDataWaiter2.checkWorkStatus()) {
						return true;
					}
				}
			}
		}
		//weekend
		else {
			if(CityClock.getTime() > weekendOpen && CityClock.getTime() < weekendClose){
				if(host.checkWorkStatus() && cashier.checkWorkStatus() && cook.checkWorkStatus()) {
					if(waiter.checkWorkStatus() || sharedDataWaiter.checkWorkStatus()
							|| waiter2.checkWorkStatus() || sharedDataWaiter2.checkWorkStatus()) {
						return true;
					}
				}
			}
		}
		return false;
	}

	@Override
	public void setHost(Role host) {
		this.host = (Restaurant4HostRole) host;
	}

	@Override
	public Role getHost() {
		return (Restaurant4HostRole)host;
	}

	@Override
	public void setCashier(Role cashier) {
		this.cashier = (Restaurant4CashierRole)cashier;
	}


	public Restaurant4CashierRole getCashier() {
		return cashier;
	}

	@Override
	public void setCook(Role cook) {
		this.cook = (Restaurant4CookRole)cook;
	}

	/*public Role getCook() {
		return (Restaurant4CookRole)cook;
	}*/

	public Restaurant4WaiterAgent  getWaiter(){
		return waiter;
	}

	public  Restaurant4CookRole getCook(){
		return cook;
	}

	public void  setHost(Restaurant4HostRole h){
		host = h;
	}

	public void  setWaiter(Restaurant4Waiter h){
		waiter = (Restaurant4WaiterAgent) h;
	}

	public void setSharedDataWaiter(Restaurant4Waiter h){
		sharedDataWaiter = (Restaurant4SharedDataWaiterRole) h;
	}

	public void setCook(Restaurant4CookRole c){
		cook = c;
	}

	public void setCashier(Restaurant4CashierRole c){
		cashier = c;
	}

	@Override
	public String getCustomerName(){
		return "restaurant4customer";
	}

	public Vector<String> getBuildingInfo(){
		Vector<String> info = new Vector<String>();
		info.add("Restaurant 4");
		info.add("Created by: Meruyert Aitbay");
		info.add("this is even more super class info");
		return info;
	}

	
	public Vector<Item> getStockItems(){
		inventory = ((Restaurant4CookRole) cook).getInventory();
		AlertLog.getInstance().logInfo(AlertTag.GUI, "Rest 5",
				inventory.toString());
		return inventory;
	}
	
	public void updateItem(String s, int hashCode) {
		// TODO Auto-generated method stub
		//THIS MUST BE UPDATED BY YOUR BUILDING
		((Restaurant4CookRole) cook).updateItem(s, hashCode);
	}
	 

}
