package simcity.restaurants.restaurant2;

import java.util.*;

import Gui.RoleGui;
import Gui.ScreenFactory;
import agent.Role;
import simcity.CityClock;
import simcity.Item;
import simcity.restaurants.Restaurant;
import simcity.restaurants.restaurant2.gui.CashierGui;
import simcity.restaurants.restaurant2.CookRole;
import simcity.restaurants.restaurant2.gui.CookGui;
import simcity.restaurants.restaurant2.gui.HostGui;
import simcity.restaurants.restaurant2.gui.WaiterGui;


/**
 * Brice's restaurant
 * @author Brice
 *
 */
public class Restaurant2 extends Restaurant {
	
	HostRole host = new HostRole();
	CashierRole cashier = new CashierRole();
	WaiterRole waiter1 = new WaiterRole(), waiter3 = new WaiterRole();
	StandWaiter waiter2 = new StandWaiter(), waiter4 = new StandWaiter();
	List<WaiterRole> waiters = new ArrayList<WaiterRole>();
	CookRole cook = new CookRole();
	
	Vector<Item> inventory;
	
	CookGui cookGui = new CookGui(cook, ScreenFactory.getMeScreen(this.getName()));
	WaiterGui waiterGui1 = new WaiterGui(waiter1, ScreenFactory.getMeScreen(this.getName()));
	WaiterGui waiterGui2 = new WaiterGui(waiter2, ScreenFactory.getMeScreen(this.getName()));
	WaiterGui waiterGui3 = new WaiterGui(waiter3, ScreenFactory.getMeScreen(this.getName()));
	WaiterGui waiterGui4 = new WaiterGui(waiter4, ScreenFactory.getMeScreen(this.getName()));
	HostGui hostGui = new HostGui(host, ScreenFactory.getMeScreen("Restaurant 2"));
	CashierGui cashierGui = new CashierGui(cashier, ScreenFactory.getMeScreen("Restaurant 2"));
	
	public Restaurant2(String type, int entranceX, int entranceY, int guiX,
			int guiY) {
		super(type, entranceX, entranceY, guiX, guiY);
		
		//set opening and closing hours
		setWeekdayHours(12, 24);
		//setWeekendHours(12, 22);
		setWeekendHours(2, 10);
		
		//set guis
		cook.setGui((RoleGui)cookGui);
		waiter1.setGui((RoleGui)waiterGui1);
		waiter2.setGui((RoleGui)waiterGui2);
		waiter3.setGui(waiterGui3);
		waiter4.setGui(waiterGui4);
		host.setGui(hostGui);
		cashier.setGui(cashierGui);
		
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
		
		Map<String, Role> jobs = Collections.synchronizedMap(new HashMap<String, Role>());
		jobs.put("cook", cook);
		jobs.put("host", host);
		jobs.put("cashier", cashier);
		jobs.put("waiter1",waiter1);
		jobs.put("waiter2",waiter2);
		jobs.put("waiter3",waiter3);
		jobs.put("waiter4",waiter4);
		setJobRoles(jobs);
	}
	
	//open and closing hours? hmmm..

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
		return "restaurant2Customer";
	}
	
	public Vector<Item> getStockItems() {
		inventory = ((CookRole) cook).getInventory();
		return inventory;
	}
	
	public void updateItem(String s, int hashCode) {
		((CookRole) cook).updateItem(s, hashCode);
	}
	
	public Vector<String> getBuildingInfo(){
		Vector<String> info = new Vector<String>();
		info.add("Restaurant 2");
		info.add("Created by: Brice Roland");
		info.add("this is even more super class info");
		return info;
	}
}