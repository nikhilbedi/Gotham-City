package simcity.restaurants.restaurant2;

import java.util.*;

import Gui.RoleGui;
import Gui.ScreenFactory;
import agent.Role;
import simcity.restaurants.Restaurant;
import simcity.restaurants.restaurant2.gui.CashierGui;
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
	
	CookGui cookGui = new CookGui(cook, ScreenFactory.getMeScreen(this.getName()));
	WaiterGui waiterGui1 = new WaiterGui(waiter1, ScreenFactory.getMeScreen(this.getName()));
	WaiterGui waiterGui2 = new WaiterGui(waiter2, ScreenFactory.getMeScreen(this.getName()));
	HostGui hostGui = new HostGui(host, ScreenFactory.getMeScreen("Restaurant 2"));
	CashierGui cashierGui = new CashierGui(cashier, ScreenFactory.getMeScreen("Restaurant 2"));
	
	public Restaurant2(String type, int entranceX, int entranceY, int guiX,
			int guiY) {
		super(type, entranceX, entranceY, guiX, guiY);
		
		//set opening and closing hours
		setWeekdayHours(12, 24);
		setWeekendHours(12, 22);
		
		//set guis
		cook.setGui((RoleGui)cookGui);
		waiter1.setGui((RoleGui)waiterGui1);
		waiter2.setGui((RoleGui)waiterGui2);
		waiter3.setGui(waiterGui1);
		waiter4.setGui(waiterGui2);
		host.setGui(hostGui);
		cashier.setGui(cashierGui);
		
		Map<String, Role> jobs = Collections.synchronizedMap(new HashMap<String, Role>());
		jobs.put("cook", cook);
		jobs.put("host", host);
		jobs.put("cashier", cashier);
		jobs.put("waiter1",(Role)waiter1);
		jobs.put("waiter2", (Role)waiter2);
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
		return "restaurant2Customer";
	}
	
	public Vector<String> getBuildingInfo(){
		Vector<String> info = new Vector<String>();
		info.add("Restaurant 2");
		info.add("Created by: Brice Roland");
		info.add("this is even more super class info");
		return info;
	}
}