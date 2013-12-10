package simcity.restaurants.restaurant5;

import java.util.*;

import Gui.RoleGui;
import Gui.ScreenFactory;
import agent.Role;
import simcity.Building;
import simcity.CityClock;
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
	Waiter waiter2 = new WaiterRole();
	Cook cook = new CookRole(); 


	public CookRole getCook() {
		return (CookRole)cook;
	}

	//create guis
	HostGui hostGui = new HostGui(host, ScreenFactory.getMeScreen("Restaurant 5"));
	CashierGui cashierGui = new CashierGui(cashier, ScreenFactory.getMeScreen("Restaurant 5"));
	CookGui cookGui = new CookGui(cook, ScreenFactory.getMeScreen("Restaurant 5"));

	WaiterGui waiterGui1 = new WaiterGui(waiter1, ScreenFactory.getMeScreen("Restaurant 5"));
	WaiterGui waiterGui2 = new WaiterGui(waiter2, ScreenFactory.getMeScreen("Restaurant 5"));
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
		//TODO change to 0 0 
		setWeekendHours(4,24);


		((HostRole) host).setGui((RoleGui)hostGui);
		((CashierRole) cashier).setGui((RoleGui)cashierGui);
		((WaiterRole) waiter1).setGui((RoleGui)waiterGui1);
		((WaiterRole) waiter2).setGui((RoleGui)waiterGui2);
		((CookRole) cook).setGui((RoleGui)cookGui);

		jobRoles.put("Host", (Role)host);
		//jobRoles.put("Host Late",  (Role)host);

		jobRoles.put("Cashier",(Role)cashier);
		//jobRoles.put("Cashier Late", (Role)cashier);

		jobRoles.put("Waiter1",(Role)waiter1);
		jobRoles.put("Waiter2",(Role)waiter2);

		//jobRoles.put("Waiter1 Late", (Role)waiter1);

		jobRoles.put("Cook",(Role)cook);
		//jobRoles.put("Cook Late", (Role)cook);


		/*PersonAgent waiterPerson = new Robot("waiter");
		PersonAgent hostPerson = new Robot("host");
		PersonAgent cookPerson = new Robot("cook");
		PersonAgent cashierPerson = new Robot("cashier");*/

		/*	waiterPerson.addRole((WaiterRole)waiter1);
		hostPerson.addRole((HostRole)host);
		cookPerson.addRole((CookRole)cook);
		cashierPerson.addRole((CashierRole)cashier);*/

		((WaiterRole)waiter1).setHost(host);
		((WaiterRole)waiter1).setCook(cook);
		((WaiterRole)waiter1).setCashier(cashier);
		((HostRole)host).addWaiter(waiter1);

		/*		ScreenFactory.getMeScreen("Restaurant 5").addGui(hostGui);
		ScreenFactory.getMeScreen("Restaurant 5").addGui(cookGui);
		ScreenFactory.getMeScreen("Restaurant 5").addGui(waiterGui);
		ScreenFactory.getMeScreen("Restaurant 5").addGui(cashierGui);*/

	}


	public String getCustomerName(){
		return "restaurant5customer";
	}

	@Override
	public boolean isOpen() {
		//Weekday
		if(CityClock.getDay() != 0 && CityClock.getDay() !=6) {
			if(CityClock.getTime() > weekdayOpen && CityClock.getTime() < weekdayClose) {
				if(((Role)host).checkWorkStatus() && ((Role)cashier).checkWorkStatus() && ((Role)cook).checkWorkStatus()) {
					if(((Role)waiter1).checkWorkStatus() || ((Role)waiter2).checkWorkStatus())
						//|| ((Role)waiter3).checkWorkStatus() || ((Role)waiter4).checkWorkStatus()) {
						return true;
				}
			}
		}

		//weekend
		else {
			if(CityClock.getTime() > weekendOpen && CityClock.getTime() < weekendClose){
				if(((Role)host).checkWorkStatus() && ((Role)cashier).checkWorkStatus() && ((Role)cook).checkWorkStatus()) {
					if(((Role)waiter1).checkWorkStatus() || ((Role)waiter2).checkWorkStatus())
						//|| ((Role)waiter3).checkWorkStatus() || ((Role)waiter4).checkWorkStatus()) {
						return true;
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
	public CashierRole getCashier() {
		return (CashierRole)cashier;
	}

	@Override
	public void setCook(Role cook) {
		this.cook = (CookRole)cook;
	}

	/*	@Override
	public Role getCook() {
		return (CookRole)cook;
	}
	 */
	public Vector<String> getBuildingInfo(){
		Vector<String> info = new Vector<String>();
		info.add("Restaurant 5");
		info.add("Created by: William McNichols");
		info.add("5 this is even more test info");
		return info;
	}

	public Vector<Item> getStockItems(){
		inventory = ((CookRole) cook).getInventory();
	/*	AlertLog.getInstance().logInfo(AlertTag.GUI, "Rest 5",
				inventory.toString());*/
		return inventory;
	}

	public void updateItem(String s, int hashCode) {
		// TODO Auto-generated method stub
		//THIS MUST BE UPDATED BY YOUR BUILDING
		((CookRole) cook).updateItem(s, hashCode);
	}

}
