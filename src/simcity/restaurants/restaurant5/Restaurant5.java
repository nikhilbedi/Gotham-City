package simcity.restaurants.restaurant5;

import java.util.*;

import Gui.RoleGui;
import Gui.ScreenFactory;
import agent.Role;
import simcity.Item;
import simcity.PersonAgent;
import simcity.Robot;
import simcity.bank.BankGreeterRole;
import simcity.bank.BankTellerRole;
import simcity.restaurants.Restaurant;
import simcity.restaurants.restaurant5.CookRole;
import simcity.restaurants.restaurant5.gui.*;
//import simcity.restaurants.restaurant5.*;
import simcity.restaurants.restaurant5.interfaces.*;
import trace.AlertLog;
import trace.AlertTag;


public class Restaurant5 extends Restaurant {


	//create roles
	Host host = new HostRole();
	Cashier cashier = new CashierRole();
	Waiter waiter1 = new WaiterRole();
	Cook cook = new CookRole(); 


	//create guis
	HostGui hostGui = new HostGui(host, ScreenFactory.getMeScreen(this.getName()));
	CashierGui cashierGui = new CashierGui(cashier, ScreenFactory.getMeScreen(this.getName()));
	WaiterGui waiterGui = new WaiterGui(waiter1, ScreenFactory.getMeScreen(this.getName()));
	CookGui cookGui = new CookGui(cook, ScreenFactory.getMeScreen(this.getName()));

	//create 2 waiters and 2 sharedDataWaiter
	


	List<WaiterRole> waiters = new ArrayList<WaiterRole>();
	//open and closing hours? hmmm..

	Vector<Item> inventory;
	
	Menu menu;

	public Restaurant5(String type, int entranceX, int entranceY, int guiX,
			int guiY) {
		super(type, entranceX, entranceY, guiX, guiY);

		//waiters.add((WaiterRole)waiter1);//do i need this?
		menu = new Menu();
		
		setWeekdayHours(9, 17);
		setWeekendHours(0,0);
		

		((HostRole) host).setGui((RoleGui)hostGui);
		((CashierRole) cashier).setGui((RoleGui)cashierGui);
		((WaiterRole) waiter1).setGui((RoleGui)waiterGui);
		((CookRole) cook).setGui((RoleGui)cookGui);

		jobRoles.put("Host Early", (Role)host);
		jobRoles.put("Host Late",  (Role)host);

		jobRoles.put("Cashier Early",(Role)cashier);
		jobRoles.put("Cashier Late", (Role)cashier);

		jobRoles.put("Waiter1 Early",(Role)waiter1);
		jobRoles.put("Waiter1 Late", (Role)waiter1);

		jobRoles.put("Cook Early",(Role)cook);
		jobRoles.put("Cook Late", (Role)cook);
		
		
		PersonAgent waiterPerson = new Robot("waiter");
		PersonAgent hostPerson = new Robot("host");
		PersonAgent cookPerson = new Robot("cook");
		PersonAgent cashierPerson = new Robot("cashier");
		
		waiterPerson.addRole((WaiterRole)waiter1);
		hostPerson.addRole((HostRole)host);
		cookPerson.addRole((CookRole)cook);
		cashierPerson.addRole((CashierRole)cashier);
		
		((WaiterRole)waiter1).setHost(host);
		((WaiterRole)waiter1).setCook(cook);
		((WaiterRole)waiter1).setCashier(cashier);
		((HostRole)host).addWaiter(waiter1);
		
		ScreenFactory.getMeScreen("Restaurant 5").addGui(hostGui);
		ScreenFactory.getMeScreen("Restaurant 5").addGui(cookGui);
		ScreenFactory.getMeScreen("Restaurant 5").addGui(waiterGui);
		ScreenFactory.getMeScreen("Restaurant 5").addGui(cashierGui);

	}


	public String getCustomerName(){
		return "restaurant5customer";
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

	public Vector<String> getBuildingInfo(){
		Vector<String> info = new Vector<String>();
		info.add("Restaurant 5");
		info.add("Created by: William McNichols");
		info.add("5 this is even more test info");
		return info;
	}

	public Vector<Item> getStockItems(){
		inventory = ((CookRole) cook).getInventory();
		AlertLog.getInstance().logInfo(AlertTag.GUI, "Rest 5",
				inventory.toString());
		return inventory;
	}

}
