package simcity.restaurants.restaurant2;

import java.util.*;

import Gui.RoleGui;
import Gui.ScreenFactory;
import agent.Role;
import simcity.restaurants.Restaurant;
import simcity.restaurants.restaurant2.gui.CookGui;
import simcity.restaurants.restaurant2.gui.WaiterGui;

/**
 * Brice's restaurant
 * @author Brice
 *
 */
public class Restaurant2 extends Restaurant {
	
	public Restaurant2(String type, int entranceX, int entranceY, int guiX,
			int guiY) {
		super(type, entranceX, entranceY, guiX, guiY);
		
		setWeekdayHours(12, 24);
		setWeekendHours(12, 22);
		
		((CookRole) cook).setGui((RoleGui)cookGui);
		((WaiterRole) waiter1).setGui((RoleGui)waiterGui1);
		((WaiterRole) waiter2).setGui((RoleGui)waiterGui2);
		
		jobRoles.put("Cook", (Role)cook);
		jobRoles.put("Cook", (Role)cook);

		jobRoles.put("Waiter1",(Role)waiter1);
		jobRoles.put("Waiter2", (Role)waiter2);
		
		jobRoles.put("Waiter3",(Role)waiter3);
		jobRoles.put("Waiter4", (Role)waiter4);
	}
	
	HostRole host = new HostRole();
	CashierRole cashier = new CashierRole();
	WaiterRole waiter1 = new WaiterRole(), waiter2 = new WaiterRole(), waiter3 = new WaiterRole(), waiter4 = new WaiterRole();
	List<WaiterRole> waiters = new ArrayList<WaiterRole>();
	CookRole cook = new CookRole();
	
	CookGui cookGui = new CookGui(cook, ScreenFactory.getMeScreen(this.getName()));
	WaiterGui waiterGui1 = new WaiterGui(waiter1, ScreenFactory.getMeScreen(this.getName()));
	WaiterGui waiterGui2 = new WaiterGui(waiter2, ScreenFactory.getMeScreen(this.getName()));
	//BankTellerGui tellerGui = new BankTellerGui(teller, ScreenFactory.getMeScreen(this.getName()));
	
	//open and closing hours? hmmm..
	
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
	public CashierRole getCashier() {
		return (CashierRole)cashier;
	}
	
	public CookRole getCook(){
		return (CookRole) cook;
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