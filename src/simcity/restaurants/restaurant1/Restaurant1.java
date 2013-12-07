package simcity.restaurants.restaurant1;

import java.util.*;

import Gui.ScreenFactory;
import agent.Role;
import simcity.Market.MarketCustomerRole;
import simcity.Market.MarketGui.MarketCustomerGui;
import simcity.restaurants.Restaurant;
import simcity.restaurants.restaurant1.gui.CookGui;
import simcity.restaurants.restaurant1.gui.WaiterGui;
import simcity.restaurants.restaurant1.gui.WaiterSharedDataGui;

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
	
	//The GUIs
	CookGui cookGui = new CookGui(cook, ScreenFactory.getMeScreen("Restaurant 1"));
	WaiterGui w1Gui = new WaiterGui(waiter1, 0, ScreenFactory.getMeScreen("Restaurant 1"));
	WaiterSharedDataGui w2Gui = new WaiterSharedDataGui(waiter2, 0, ScreenFactory.getMeScreen("Restaurant 1"));

	
	public Restaurant1(String type, int entranceX, int entranceY, int guiX,
			int guiY) {
		super(type, entranceX, entranceY, guiX, guiY);
		//Set the open and closing hours
		setWeekdayHours(8, 22);
		setWeekendHours(9, 21);
		//Set guis
		cook.setGui(cookGui);
		waiter1.setGui(w1Gui);
		waiter2.setGui(w2Gui);
		//Add the key: strings & value: roles
		Map<String, Role> jobs = Collections.synchronizedMap(new HashMap<String, Role>());
		jobs.put("host", host);
		jobs.put("cashier", cashier);
		jobs.put("cook", cook);
		jobs.put("waiter1", waiter1);
		jobs.put("waiter2", waiter2);
		setJobRoles(jobs);
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
		return "restaurant1customer";
	}
}