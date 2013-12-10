package simcity.restaurants.restaurant5;
import Gui.RoleGui;
import agent.Agent;
import agent.Role;

import java.util.*;
import java.util.concurrent.Semaphore;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.Map;
import java.util.TimerTask;

import simcity.PersonAgent;
import simcity.TheCity;
import simcity.Market.MarketCashierRole;
import simcity.restaurants.Restaurant;
import simcity.restaurants.restaurant5.gui.CashierGui;
import simcity.restaurants.restaurant5.interfaces.Cashier;
import simcity.restaurants.restaurant5.interfaces.Customer;
import simcity.restaurants.restaurant5.interfaces.Market;
import simcity.restaurants.restaurant5.interfaces.Waiter;

/**
 * Restaurant Cook Agent
 */
public class CashierRole extends Role implements Cashier {

	public Map<String, Double> prices = new HashMap<String, Double>();
	private String name;

	public List<MyCustomer> customers = Collections.synchronizedList(new ArrayList<MyCustomer>());
	public List<MyMarket> markets = Collections.synchronizedList(new ArrayList<MyMarket>());

	public double register;

	public CashierRole(String n) {
		prices.put("Steak", 15.99);
		prices.put("Chicken", 10.99);
		prices.put("Salad", 5.99);
		prices.put("Pizza", 8.99);

		register = 50.00;

		name = n;

	}
	private CashierGui gui = null;


	// Messages

	public CashierRole(PersonAgent cashierPerson) {
		super(cashierPerson);
		prices.put("Steak", 15.99);
		prices.put("Chicken", 10.99);
		prices.put("Salad", 5.99);
		prices.put("Pizza", 8.99);

		register = 50.00;
	}


	public CashierRole() {
		super();
		prices.put("Steak", 15.99);
		prices.put("Chicken", 10.99);
		prices.put("Salad", 5.99);
		prices.put("Pizza", 8.99);

		register = 50.00;
	}


	//v2.1
	public void computeBill(Waiter w, Customer c, String choice){
		synchronized(customers){
			for(MyCustomer mycustomer: customers){
				if((mycustomer.c == c) && (mycustomer.cs == CustomerState.debted)){
					print("Waiter has noted that customer " + c.getName() + " owes the restauarant money!");	
					//do debt stuff
				}
			}
		}
		print("Received request from " + w.getName() + " to compute bill.");
		customers.add(new MyCustomer(c, choice, w));
		stateChanged();
	}


	public void payment(Customer c, double cash, double check){
		print("Received payment from " + c.getName());
		synchronized(customers){
			for(MyCustomer mycustomer: customers){
				if(mycustomer.c == c){
					mycustomer.cs = CustomerState.paying;
					mycustomer.cash = cash;
					mycustomer.owed = check;
				}
			}
		}
		stateChanged();
	}
	//v2.2
	public void hereIsMarketBill(Market market, double bill) {
		print("Recieved bill from " + market.getName());
		synchronized(markets){
			for(MyMarket mm : markets){
				if(mm.market.equals(market)){
					mm.bill = bill;
					mm.debtsPaid = false;
				}
			}
		}
		stateChanged();
	}

	/**
	 * Scheduler.  Determine what action is called for, and do it.
	 */
	public boolean pickAndExecuteAnAction() {
		synchronized(customers){
			for(MyCustomer mc: customers){
				if(mc.cs == CustomerState.calculating){
					generateBill(mc);
					return true;
				}
				if(mc.cs == CustomerState.paying){
					processFinishedCustomer(mc);
					return true;
				}
			}
		}
		synchronized(markets){
			for(MyMarket mm: markets){
				if(!mm.debtsPaid){
					payMarket(mm);
					return true;
				}
			}
		}

		return false;

		//we have tried all our rules and found
		//nothing to do. So return false to main loop of abstract agent
		//and wait.
	}

	private void payMarket(MyMarket mm) {
		register -= mm.bill;
		mm.debtsPaid = true;
		mm.market.hereIsPayment(this, mm.bill);
		mm.bill = 0;

	}
	// Actions
	private void generateBill(MyCustomer mc){
		mc.cs = CustomerState.eating;
		mc.w.hereIsCheck(this, mc.c, prices.get(mc.choice));
	}

	private void processFinishedCustomer(MyCustomer mc){
		//print("Processing finished customer");
		if(mc.cash<mc.owed){
			mc.cs = CustomerState.debted;
			mc.debt += mc.cash - mc.owed;
			mc.c.giveDebt(this, mc.debt);
		}
		else{
			mc.cs = CustomerState.left;
			register += mc.owed;
			double change = mc.cash-mc.owed;
			mc.c.giveChange(this, change);
			customers.remove(mc);
		}
	}

	//utilities


	public void setGui(RoleGui g) {
		super.setGui(g);
		gui = (CashierGui)g;
	}

	public CashierGui getGui() {
		return gui;
	}

	public void addMarket(Market market) {
		markets.add(new MyMarket(market));

	}
	private enum CustomerState{
		calculating, eating, paying, left, debted
	};


	private class MyMarket{
		double bill;
		boolean debtsPaid;
		Market market;
		public MyMarket(Market m){
			market = m;
			bill = 0.0;
			debtsPaid = true;
		}

	}


	private class MyCustomer{
		Customer c;
		CustomerState cs;
		Waiter w;
		String choice;
		double cash,owed,debt;
		public MyCustomer(Customer cust, String type, Waiter wait){
			c = cust;
			choice = type;
			w = wait;
			cs = CustomerState.calculating;
		}
	}


	public String getName(){
		return name;
	}

	public double getMoney(){
		return register;
	}


	private class Food{
		String type;
		int cookingTime;
		public Food(String n, int i){
			type = n;
			cookingTime = i;
		}
		int amount = 2;//Hacked for testing
		int low = 1;

	}


	public void HereIsYourChange(double i, MarketCashierRole marketCashierRole) {
		// TODO Auto-generated method stub
		register += i;
		
	}


	public void amountDue(double amountDue, MarketCashierRole marketCashierRole) {
		// TODO Auto-generated method stub
		register -= amountDue;
		marketCashierRole.hereIsMoneyRestaurant((Restaurant)TheCity.getBuildingFromString("Restaurant 5"), amountDue);
	}






}

