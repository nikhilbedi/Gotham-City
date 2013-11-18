package Gui;

import java.util.*;

import javax.swing.JPanel;

import simcity.PersonAgent;
import simcity.restaurants.restaurant1.CashierRole;
import simcity.restaurants.restaurant1.CookRole;
import simcity.restaurants.restaurant1.CustomerRole;
import simcity.restaurants.restaurant1.HostRole;
import simcity.restaurants.restaurant1.WaiterRole;
import simcity.restaurants.restaurant1.gui.*;

public class ScreenFactory {
	List<Screen> screenList = new ArrayList<Screen>();
	Screen main = new Screen(1);
	Screen rest = new AnimationPanel();
	Screen market = new Screen(3);
	Screen bank = new Screen(4);
	Screen home = new Screen(5);

	
	HostRole host = new HostRole();
	HostGui hostGui = new HostGui(host);

	//Implementing Cooks
	CookRole cook = new CookRole();

	//implement cashier
	CashierRole cashier = new CashierRole();

	WaiterRole waiter = new WaiterRole();

	//Customer
	CustomerRole customer = new CustomerRole();

	CookGui g = new CookGui(cook);
	
	PersonAgent hostp = new PersonAgent("Host");
	PersonAgent waiterp = new PersonAgent("waiter");
	PersonAgent customerp = new PersonAgent("customer");
	PersonAgent cookp = new PersonAgent("cook");
	PersonAgent cashierp = new PersonAgent("cashier");
	
	CustomerGui cgui = new CustomerGui(customer);
	WaiterGui wgui = new WaiterGui(waiter,0);
	
	public ScreenFactory(){
		
		host.setGui(hostGui);
		host.setPerson(hostp);

		//setting cook
	
		cook.setPerson(cookp);
		cook.setGui(g);

		//setting cashier
		//cashier.startThread();
		cashier.setPerson(cashierp);

		waiter.setPerson(waiterp);
		
		customer.setPerson(customerp);
		
		hostp.startThread();
		customerp.startThread();
		cashierp.startThread();
		waiterp.startThread();
		cookp.startThread();
		
		hostp.addRole(host);
		customerp.addRole(customer);
		cashierp.addRole(cashier);
		waiterp.addRole(waiter);
		cookp.addRole(cook);
		
		customer.setGui(cgui);
		waiter.setGui(wgui);
		host.msgIWantFood(customer);

		rest.addGui(new CookGui(cook));
		rest.addGui(new HostGui(host));
		rest.addGui(wgui);
		rest.addGui(cgui);
		screenList.add(main);
		screenList.add(rest);
		screenList.add(market);
		screenList.add(bank);
		screenList.add(home);


		RoleGui gui1, gui2;


		//These are testing hacks
		/*gui1 = new RoleGuiTest("red");
		gui2 = new RoleGuiTest("notred");
		main.addGui(gui1);
		swap.addGui(gui2);*/
	}

	public Screen getCity(){
		return main;
	}
	public Screen getRestaurant(){
		return rest;
	}
	public Screen getMarket(){
		return market;
	}
	public Screen getBank(){
		return bank;
	}
	public Screen getHome(){
		return home;
	}


	public void updateAllPositions(){
		for (Screen s : screenList) {
			s.updateAgents();
		}
	}
	public Screen getScreen(String s){
		if(s.equalsIgnoreCase("City")){
			return main;
		}
		if(s.equalsIgnoreCase("Restaurant")){
			return rest;
		}
		if(s.equalsIgnoreCase("Market")){
			return market;
		}
		if(s.equalsIgnoreCase("Bank")){
			return bank;
		}
		if(s.equalsIgnoreCase("Home")){
			return home;
		}

		return null;
	}

}
