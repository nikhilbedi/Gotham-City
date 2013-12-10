package simcity.restaurants.restaurant1;

import java.util.*;

import Gui.ScreenFactory;
import agent.Role;
import simcity.CityClock;
import simcity.Market.MarketCustomerRole;
import simcity.Market.MarketGui.MarketCustomerGui;
import simcity.restaurants.Restaurant;
import simcity.restaurants.restaurant1.gui.CashierGui;
import simcity.restaurants.restaurant1.gui.CookGui;
import simcity.restaurants.restaurant1.gui.HostGui;
import simcity.restaurants.restaurant1.gui.WaiterGui;
import simcity.restaurants.restaurant1.gui.WaiterSharedDataGui;
import trace.AlertLog;
import trace.AlertTag;

/**
 * Nikhil's restaurant - original
 * @author nikhil
 *
 */
public class Restaurant1 extends Restaurant {
	HostRole host = new HostRole();
	CashierRole cashier = new CashierRole();
	CookRole cook = new CookRole();
	WaiterRole waiter1 = new WaiterRole();
	WaiterSharedData waiter2 = new WaiterSharedData();
	WaiterRole waiter3 = new WaiterRole();
	WaiterSharedData waiter4 = new WaiterSharedData();
	
	//The GUIs
	CookGui cookGui = new CookGui(cook, ScreenFactory.getMeScreen("Restaurant 1"));
	WaiterGui w1Gui = new WaiterGui(waiter1, 0, ScreenFactory.getMeScreen("Restaurant 1"));
	WaiterSharedDataGui w2Gui = new WaiterSharedDataGui(waiter2, 2, ScreenFactory.getMeScreen("Restaurant 1"));
	WaiterGui w3Gui = new WaiterGui(waiter3, 3, ScreenFactory.getMeScreen("Restaurant 1"));
	WaiterSharedDataGui w4Gui = new WaiterSharedDataGui(waiter4, 4, ScreenFactory.getMeScreen("Restaurant 1"));
	HostGui hostGui = new HostGui(host, ScreenFactory.getMeScreen("Restaurant 1"));
	CashierGui cashierGui = new CashierGui(cashier, ScreenFactory.getMeScreen("Restaurant 1"));

	
	public Restaurant1(String type, int entranceX, int entranceY, int guiX,
			int guiY) {
		super(type, entranceX, entranceY, guiX, guiY);
		//Set the open and closing hours
		setWeekdayHours(1, 12);
		setWeekendHours(12, 24);
		//Set guis
		cook.setGui(cookGui);
		waiter1.setGui(w1Gui);
		waiter2.setGui(w2Gui);
		waiter3.setGui(w1Gui);
		waiter4.setGui(w2Gui);
		host.setGui(hostGui);
		cashier.setGui(cashierGui);
		//Setting roles to each other
		host.addWaiter(waiter1);
		host.addWaiter(waiter2);
		host.addWaiter(waiter3);
		host.addWaiter(waiter4);
		waiter1.setHost(host);
		waiter2.setHost(host);
		waiter3.setHost(host);
		waiter4.setHost(host);
		waiter1.setCashier(cashier);
		waiter2.setCashier(cashier);
		waiter3.setCashier(cashier);
		waiter4.setCashier(cashier);
		waiter1.setCook(cook);
		waiter2.setCook(cook);
		waiter3.setCook(cook);
		waiter4.setCook(cook);
		//Add the key: strings & value: roles
		Map<String, Role> jobs = Collections.synchronizedMap(new HashMap<String, Role>());
		jobs.put("host", host);
		jobs.put("cashier", cashier);
		jobs.put("cook", cook);
		jobs.put("waiter1", waiter1);
		jobs.put("waiter2", waiter2);
		jobs.put("waiter3", waiter3);
		jobs.put("waiter4", waiter4);
		setJobRoles(jobs);
		
		//Remove the guis until people come in to take those roles
		//hostGui.getHomeScreen().removeGui(hostGui);
	}

	/**
	 * Not only must a person check if the business is open between certain hours,
	 * but the person must check if all the necessary roles are present within the building.
	 */
	@Override
	public boolean isOpen() {
		//Weekday
		if(CityClock.getDay() != 0 && CityClock.getDay() !=6) {
			if(CityClock.getTime() > weekdayOpen && CityClock.getTime() < weekdayClose) {
				if(host.checkWorkStatus() && cashier.checkWorkStatus() && cook.checkWorkStatus()) {
					if(waiter1.checkWorkStatus() || waiter2.checkWorkStatus()
						|| waiter3.checkWorkStatus() || waiter4.checkWorkStatus()) {
						return true;
					}
				}
			}
		}
		//weekend
		else {
			if(CityClock.getTime() > weekendOpen && CityClock.getTime() < weekendClose){
				if(host.checkWorkStatus() && cashier.checkWorkStatus() && cook.checkWorkStatus()) {
					if(waiter1.checkWorkStatus() || waiter2.checkWorkStatus()
						|| waiter3.checkWorkStatus() || waiter4.checkWorkStatus()) {
						return true;
					}
				}
			}
		}
		return false;
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
	public void setCook(Role cook) {
		this.cook = (CookRole)cook;
	}

	@Override
	public Role getCook() {
		return (CookRole)cook;
	}

	@Override
	public String getCustomerName(){
		return "restaurant1customer";
	}
	
	public Vector<String> getBuildingInfo(){
		Vector<String> info = new Vector<String>();
		info.add("Restaurant 1");
		info.add("Created by: Nikhil Bedi");
		info.add("The window might not be large enough to see my kitchen.\n"
				+ "Please resize on the right side to see it.");
		return info;
	}
}