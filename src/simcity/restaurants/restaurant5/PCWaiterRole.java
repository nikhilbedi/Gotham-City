package simcity.restaurants.restaurant5;

import Gui.RoleGui;
import agent.Agent;
import agent.Role;

import java.util.*;
import java.util.concurrent.Semaphore;
import java.util.Collections;
import java.util.Timer;
import java.util.Map;
import java.util.TimerTask;

import simcity.PersonAgent;
import simcity.restaurants.restaurant1.RevolvingStand;
import simcity.restaurants.restaurant5.interfaces.*;
import simcity.restaurants.restaurant5.gui.*;


import java.util.*;
import java.util.concurrent.Semaphore;

/**
 * Restaurant Waiter Agent
 */
public class PCWaiterRole extends AbstractWaiterRole implements Waiter {
	static final int NTABLES = 3;//a global for the number of tables.

	//Notice that we implement waitingCustomers using ArrayList, but type it
	//with List semantics.
	public List<MyCustomer> customers
	= Collections.synchronizedList(new ArrayList<MyCustomer>());

	Menu menu = new Menu();

	public Collection<Table> tables;
	//note that tables is typed with Collection semantics.
	//Later we will see how it is implemented

	private String name;

	private Semaphore moving = new Semaphore(0,true);

	boolean wantBreak = false;
	boolean onBreak = false;

	//Agent correspondants; simplfies messaging 
	private Host h;
	public Host getHost() {
		return h;
	}

	public void setHost(Host h) {
		this.h = h;
	}

	public Cook getCook() {
		return cook;
	}

	public void setCook(Cook cook) {
		this.cook = cook;
	}

	public Cashier getCashier() {
		return cashier;
	}

	public void setCashier(Cashier cashier) {
		this.cashier = cashier;
	}


	public Cook cook;
	private Cashier cashier;

	//Gui stuff
	public WaiterGui waiterGui = null;

	WorkState work;

	private enum WorkState{
		working, wantBreak, onBreak}

	public PCWaiterRole(String name) {
		super();
		work = WorkState.working;
		this.name = super.getName();
		// make some tables
		tables = new ArrayList<Table>(NTABLES);
		for (int ix = 0; ix < NTABLES; ix++) {
			tables.add(new Table(ix));//how you add to a collections
		}

	}

	public PCWaiterRole(String name, Host h, Cook c, Cashier ca)//constructor that includes agents (used to simplify things in the gui implementation)
	{
		this(name);
		this.h = h;
		this.cook = c;
		this.cashier = ca;
	}

	public PCWaiterRole(PersonAgent waiterPerson) {
		super(waiterPerson);
		work = WorkState.working;
		this.name = name;
		// make some tables
		tables = new ArrayList<Table>(NTABLES);
		for (int ix = 0; ix < NTABLES; ix++) {
			tables.add(new Table(ix));//how you add to a collections
		}
	}





	public PCWaiterRole() {
		super();
		work = WorkState.working;
		this.name = name;
		// make some tables
		tables = new ArrayList<Table>(NTABLES);
		for (int ix = 0; ix < NTABLES; ix++) {
			tables.add(new Table(ix));//how you add to a collections
		}
	}

	public List getWaitingCustomers() {
		return customers;
	}

	public Collection getTables() {
		return tables;
	}

	// Messages
	//Messages added from v2

	public void sitAtTable(Host h, Customer c, int table){
		print("Seating " + c.getName() + " at table " + table);
		customers.add(new MyCustomer(c, table, CustomerState.waiting));
		stateChanged();

	}

	public void imReadyToOrder(Customer c){
		print("Received message that " + c.getName() + " is ready to order.");
		synchronized(customers){
			for(MyCustomer mycustomer: customers){
				if(mycustomer.c == c){
					mycustomer.s = CustomerState.readyToOrder;
				}
			}
		}
		stateChanged();
	}

	public void hereIsMyChoice(Customer c, String choice){
		print("Received message that " + c.getName() + " has given choice " + choice + ".");
		synchronized(customers){
		for(MyCustomer mycustomer: customers){
			if(mycustomer.c == c){
				mycustomer.s = CustomerState.givingOrder;
				mycustomer.choice = choice;
			}
		}
		}
		stateChanged();
	}

	public void hereIsAnOrder(Cook ck, String choice, int table){//(order o)
		print("Received message that cook " + ck.getName() + " has finished " + choice + " for table " + table + ".");
		synchronized(customers){
		for(MyCustomer mycustomer: customers){
			if(mycustomer.table == table){
				mycustomer.o.os = OrderState.done;
			}
		}
		}
		stateChanged();
	}

	public void doneEatingAndLeaving(Customer c){
		print("Received message that customer " + c.getName() + " has finished eating.");
		synchronized(customers){
		for(MyCustomer mycustomer: customers){
			if(mycustomer.c.equals(c)){
				mycustomer.s = CustomerState.leaving;
			}
		}
		}
		stateChanged();

	}

	//Messages added from v2.1
	public void outOfOrder(String choice, int i, Menu m){
		print("Received message that we are out of " + choice);
		synchronized(customers){
		for(MyCustomer mycustomer: customers){
			if(mycustomer.choice.equals(choice) && mycustomer.table == i){
				mycustomer.o.os = OrderState.invalid;
				mycustomer.myMenu = m;
				 ((WaiterGui) waiterGui).removeIcon(mycustomer.table, mycustomer.choice);			
			}
		}
		}
		stateChanged();

	}

	public void doneEating(Customer c) {
		print("Received message that " + c.getName() + " is done eating");
		synchronized(customers){
		for(MyCustomer mycustomer: customers){
			if(mycustomer.c == c){
				mycustomer.s = CustomerState.needCheck;
			}
		}
		}
		stateChanged();

	}

	public void gotCheckAndLeaving(Customer c) {
		print("Received message that customer " + c.getName() + " has received check and is leaving.");
		synchronized(customers){
		for(MyCustomer mycustomer: customers){
			if(mycustomer.c == c){
				mycustomer.s = CustomerState.leaving;
			}
		}
		}
		stateChanged();

	}

	public void cantOrderLeaving(Customer c) {
		print("Received message that customer " + c.getName() + " cant order and is leaving.");
		synchronized(customers){
			for(MyCustomer mycustomer: customers){
				if(mycustomer.c == c){
					mycustomer.s = CustomerState.leaving;
				}
			}
		}
		stateChanged();

	}

	public void hereIsCheck(Cashier ch, Customer c, Double bill) {
		print("Received bill for customer " + c.getName() + " from cashier.");
		synchronized(customers){
		for(MyCustomer mycustomer: customers){
			if(mycustomer.c == c){
				mycustomer.cost = bill;
			}
		}
		}
		stateChanged();

	}

	public void breakApproved(){
		work = WorkState.onBreak;
	}

	public void cantBreak(){
		work = WorkState.working;
	}

	/**
	 * Scheduler.  Determine what action is called for, and do it.
	 */
	public boolean pickAndExecuteAnAction() {
		try{
			for(MyCustomer mc: customers){
					if(mc.s == CustomerState.givingOrder){
						takeOrder(mc);
						return true;
					}
					if(mc.s == CustomerState.waitingForFood){
						if(mc.o.os == OrderState.invalid){
							notifyCustomerInvalid(mc);
							return true;
						}

						else if(mc.o.os == OrderState.done){
							serveCustomer(mc);
							return true;
						}
					}	
					if(mc.s == CustomerState.readyToOrder){
						askOrder(mc);
						return true;
					}
					if(mc.s == CustomerState.leaving){
						processLeavingCustomer(mc);
						return true;
					}
					if(mc.s == CustomerState.needCheck){
						giveCheck(mc);
						return true;
					}
					if(mc.s == CustomerState.waiting){
						seatCustomer(mc);
						return true;
					}
				}	
			if(!customers.isEmpty()){
				return true;
			}

			if(work == WorkState.onBreak){//not sure if I like how this is done. Consider changing
				print("Finished current customers, going on break");
				DoGoToOrigin();
				return false;
			}

			return false;
		}
		catch(ConcurrentModificationException e){
			return true;
		}
		//we have tried all our rules and found
		//nothing to do. So return false to main loop of abstract agent
		//and wait.
	}

	// Actions

	private void seatCustomer(MyCustomer mc) {
		DoSeatCustomer((Restaurant5CustomerGui) mc.c.getGui(), mc.table);
		mc.c.msgSitAtTable(mc.table);
		mc.c.followMeToTable(this, new Menu());
		mc.s = CustomerState.seated;
		if(getName().equals("Lazy")){
			h.iWantABreak(this);
		}
	}

	public void askOrder(MyCustomer mc){
		DoGoToTable(mc.table);
		mc.c.whatWouldYouLike(this);
		mc.s = CustomerState.asked;

	}

	public void takeOrder(MyCustomer mc){
		DoTakeOrder(mc.table, mc.choice);
		//Add icon to take
		waiterGui.addIcon(mc.table, mc.choice);
		DoGoToStand();
		Stand.pushOrder(this, mc.choice, mc.table, cook);
		mc.s = CustomerState.waitingForFood;
		mc.o = new Order(mc.choice, mc.table);
		mc.o.os = OrderState.cooking;
	}


	public void serveCustomer(MyCustomer mc){
		DoGoToCook();
		waiterGui.updateWaiterIcon(mc.choice);
		DoGoToTable(mc.table);
		waiterGui.readyIcon(mc.table, mc.choice);
		waiterGui.removeWaiterIcon();
		//DoServeFood()
		mc.c.hereIsYourFood(this);
		cashier.computeBill(this, mc.c, mc.choice);//new
		mc.s = CustomerState.eating;
		mc.o.os = OrderState.served;
	}

	public void processLeavingCustomer(MyCustomer mc){
		//remove icon
		waiterGui.removeIcon(mc.table, mc.choice);
		h.tableIsFree(this, mc.table);
		mc.table = -1;
		mc.s = CustomerState.left;
		customers.remove(mc);
	}


	//v2.1
	public void notifyCustomerInvalid(MyCustomer mc){
		DoGoToTable(mc.table);
		mc.o.os = OrderState.DNE;
		//System.err.println("New Menu size:" + mc.myMenu.size());
		mc.c.whatWouldYouLike(this, mc.myMenu);
		mc.s = CustomerState.asked;

	}

	private void giveCheck(MyCustomer mc){
		//DoGoToTable(mc.table);
		mc.c.hereIsCheck(mc.cost);
		mc.s = CustomerState.paying;
	}

	// The animation DoXYZ() routines

	private void DoGoToOrigin(){
		waiterGui.DoGoToCustomers();
		try {
			moving.acquire();
		}
		catch(InterruptedException e) {	
		}

	}
	private void DoSeatCustomer(Restaurant5CustomerGui customer, int table) {
		//Notice how we print "customer" directly. It's toString method will do it.
		//Same with "table"
		//print("Seating " + customer + " at " + table);
		DoGoToOrigin();
		waiterGui.DoBringToTable(customer, table); 
		try {
			//print("aquiring");
			moving.acquire();
		}
		catch(InterruptedException e) {	
		}

	}
	private void DoGoToTable(int table){
		waiterGui.DoGoToTable(table);
		try {
			moving.acquire();
		}
		catch(InterruptedException e) {	
		}

	}
	
	private void DoGoToStand() {
		waiterGui.DoGoToStand();
		try {
			moving.acquire();
		}
		catch(InterruptedException e) {	
		}
	}


	private void DoGoToCook(){
		waiterGui.DoGoToCook();
		try {
			moving.acquire();
		}
		catch(InterruptedException e) {	
		}
	}

	private void DoTakeOrder(int table, String choice){
		waiterGui.DoTakeOrder(table);
		try {
			moving.acquire();
		}
		catch(InterruptedException e) {	
		}
	}
	public void doneMoving(){
		moving.release();
	}

	//utilities
	public int getNumTables(){
		return NTABLES;
	}

	public void setGui(RoleGui gui) {
		super.setGui(gui);
		waiterGui = (WaiterGui)gui;
	}

	public RoleGui getGui() {
		return waiterGui;
	}

	public void askBreak(){//used by button
		h.iWantABreak(this);
	}

	public void offBreak(){//used by button
		h.offBreak(this);
	}

	public boolean isOnBreak(){
		return(work == WorkState.onBreak);
	}
	public boolean isOffBreak(){
		return(work == WorkState.working);
	}
	public int getNumCustomers(){
		return customers.size();
	}
	enum CustomerState{
		newCustomer, waiting, seated, readyToOrder, asked, givingOrder, waitingForFood, eating, doneEating, 
		leaving, needCheck, done, paying, left};
		enum OrderState{
			DNE,cooking, invalid, done,served};
			private class Order{
				String choice;
				int table;
				OrderState os;

				public Order(String c, int t){
					choice = c;
					table = t;
					os = OrderState.DNE;
				}
			}

			private class MyCustomer{
				Customer c;
				int table;
				String choice;
				CustomerState s;
				Order o;
				double cost;
				Menu myMenu;
				public MyCustomer(Customer cust, int t, CustomerState cs){
					myMenu = new Menu();
					c = cust;
					table = t;
					choice = "undecided";
					s = cs;

				}
			}


			private class Table {
				Customer occupiedBy;
				int tableNumber;

				Table(int tableNumber) {
					this.tableNumber = tableNumber;
				}

				void setOccupant(Customer cust) {
					occupiedBy = cust;
				}

				void setUnoccupied() {
					occupiedBy = null;
				}

				Customer getOccupant() {
					return occupiedBy;
				}

				boolean isOccupied() {
					return occupiedBy != null;
				}

				public String toString() {
					return "table " + tableNumber;
				}
			}


}

