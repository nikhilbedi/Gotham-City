package simcity.restaurants.restaurant1;

import simcity.PersonAgent;
import simcity.tests.mock.*;
import Gui.RoleGui;
import agent.Agent;
import simcity.restaurants.restaurant1.WaiterRole;
import simcity.restaurants.restaurant1.Restaurant1CustomerRole;
import simcity.restaurants.restaurant1.Menu;
import simcity.restaurants.restaurant1.gui.WaiterGui;
import simcity.restaurants.restaurant1.interfaces.*;
import simcity.restaurants.restaurant1.CashierRole.Check;
import agent.Role;

import java.util.*;
import java.util.concurrent.Semaphore;

/**
 * Restaurant Waiter Agent
 */
public class WaiterRole extends Role implements Waiter {
	private List<MyCustomer> myCustomers
	= Collections.synchronizedList(new ArrayList<MyCustomer>());
	Timer timer = new Timer();
	private String name;
	private Menu myMenu = new Menu();
	private Semaphore busyWithTask = new Semaphore(0, true);
	private Semaphore atTable = new Semaphore(0,true);
	private List<Check> bills = new ArrayList<Check>();
	//creating states outside the MyCustomer class
	private enum CustomerState {waiting, leavingEarly, seated, readyToOrder, askedToOrder, gaveOrder, gaveOrderAndWaiting, waitingForFood,
		waitingForCheck, orderNotAvailable, eating, doneEating};
	public enum WaiterState {available,askingForBreak, askedForBreak, readyToTakeBreak, onBreak, waitingToBePutOnBreak, busy};
	public WaiterState state = WaiterState.available;
	private HostRole host;
	private CookRole cook;
	private CashierRole cashier;

	private WaiterGui waiterGui = null;

	public WaiterRole(PersonAgent p) {
		super(p);
		this.name = name;
		print("I am a waiter. Sup.");
	}

	public void setHost(HostRole host) {
		this.host = host;
	}

	public void setCook(CookRole cook) {
		this.cook = cook;
	}

	public void setCashier(CashierRole cashier) {
		this.cashier= cashier;
	}

	public String getName() {
		return name;
	}

	public List getMyCustomers() {
		return myCustomers;
	}

	public boolean onBreak() {
		if(state == WaiterState.onBreak || state == WaiterState.readyToTakeBreak){
			return true;
		}
		return false;
	}

	public boolean askingForBreak() {
		if(state == WaiterState.askingForBreak){
			return true;
		}
		return false;
	}

	// Messages

	//from gui
	public void doneWithTask() {
		//launches a action that does a release
		available();
		//	state = WaiterState.busy;
		//stateChanged();
	}

	public void shouldTakeBreak() {
		print("I received message from God that I should take a break.");
		state = WaiterState.askingForBreak;
		stateChanged();
	}

	public void goOffBreak() {
		print("I am off break.");
		state = WaiterState.available;
		host.offBreak(this);
	}

	//Going on Break
	public void goOnBreak() {
		print("Thanks host! I'll go on break after any customers I have are finished.");
		state = WaiterState.readyToTakeBreak;
		stateChanged();
	}

	public void doNotGoOnBreak() {
		print("Man, the host won't let me have a break...");
		state = WaiterState.available;
		//commented this out because I assumed it was unnecessary
		//stateChanged();
	}

	//Correctly gets message from host to seat customer 
	public void pleaseSeatCustomer(Customer cust, int table, int x, int y) {
		myCustomers.add(new MyCustomer(cust, table, CustomerState.waiting, x, y));
		print("Received message to seat customer " + ((Role) cust).getPersonAgent().getName() + " at table number " + table + ".");
		stateChanged();
	}

	public void customerLeft(Customer cust) {
		for(MyCustomer mc : myCustomers) {
			if(mc.c == cust) {
				mc.state = CustomerState.leavingEarly;
				break;
			}
		}
		stateChanged();
	}

	//gui message
	public void ledCustomerToTableAnimation() {
		available();
	}

	public void readyToOrder(Customer cust) {
		for(MyCustomer mc : myCustomers) {
			if(mc.c == cust) {
				mc.state = CustomerState.readyToOrder;
				print("Received message that customer " + ((Role) cust).getPersonAgent().getName() + " is Ready to Order.");
				break;
			}
		}
		//This statechange was giving me a "maximum permit count exceeded" for the customer's thread
		stateChanged();
	}

	//Handles customer food order
	public void hereIsMyChoice(String choice, Customer cust) {
		for(MyCustomer mc : myCustomers) {
			if(mc.c == cust) {
				mc.choice = choice;
				print(": Received order " + choice + " from customer " + ((Role) cust).getPersonAgent().getName() + ".");
				mc.state = CustomerState.gaveOrder;
				break;
			}
		}
		stateChanged();
	}

	//gui message
	public void reachedCookAnimation() {
		available();
	}

	//Handles cook's order is ready message
	public void orderDone(int table, String choice) {
		print("Received message from cook that Order " + choice + " for table " + table + " is done.");
		//takeFoodToCustomer(table, choice);
		for(MyCustomer mc : myCustomers) {
			if(mc.tableNumber == table) {
				mc.state = CustomerState.waitingForFood;
				break;
			}
		}
		stateChanged();
	}

	//Handles cook's message that food is out
	public void outOfFood(int table, String choice) {
		myMenu.choices.remove(choice);
		//make new menu and then send this
		for(String c : myMenu.choices.keySet()) {
			print(c);
		}
		for(MyCustomer mc : myCustomers) {
			if(mc.tableNumber == table) {
				mc.state = CustomerState.orderNotAvailable;
				break;
			}
		}
		stateChanged();
	}

	public void readyForCheck(Restaurant1CustomerRole role) {
		print("Customer is ready for check. Will get it now.");
		for(MyCustomer mc : myCustomers) {
			if(mc.c == role) {
				mc.state = CustomerState.waitingForCheck;
				break;
			}
		}
		stateChanged();
	}
	
	//The cashier sends a check to the waiter
	@Override
	public void hereIsCheck(Check ch) {
		//Conduct an action to 
		print("Received check for " + ch.customer);
		bills.add(ch);
		stateChanged();
	}

	//Handles customer message when it's done eating
	//CHANGE THIS TO DONE AND PAYING
	public void doneAndLeaving(Customer cust) {
		for(MyCustomer mc : myCustomers) {
			if(mc.c == cust) {
				mc.state = CustomerState.doneEating;
				break;
			}
		}
		stateChanged();
	}

	/**
	 * Scheduler.  Determine what action is called for, and do it.
	 */
	public boolean pickAndExecuteAnAction() {
		/* Does there exist a customer in myCustomers such that its state is waiting. If so, seat the customer.
		   Does there exist a customer in myCustomers such that its state is readyToOrder. If so, then move over there to take the order.
		 */
		try{
			if(!bills.isEmpty() && bills.get(0) != null) {
				takeCheckToCustomer(bills.get(0));
				return true;
			}

			if(state == WaiterState.askingForBreak){
				askForBreak();
				return true;
			}

			for (MyCustomer mc : myCustomers) {
				if(mc.state == CustomerState.doneEating || mc.state == CustomerState.leavingEarly){
					tellHostTableFree(mc);		
					return true;
				}
			}
			for (MyCustomer mc : myCustomers) {
				if(mc.state == CustomerState.orderNotAvailable){
					doNotHaveOrder(mc);		
					return true;
				}
			}
			for (MyCustomer mc : myCustomers) {
				if(mc.state == CustomerState.readyToOrder) {
					takeOrder(mc);
					return true;
				}
			}
			for (MyCustomer mc : myCustomers) {
				if(mc.state == CustomerState.waiting){
					seatCustomer(mc);		
					return true;
				}
			}

			for (MyCustomer mc : myCustomers) {
				if(mc.state == CustomerState.waitingForFood) {
					takeFoodToCustomer(mc.tableNumber, mc.choice);
					//getCheck(mc);
					return true;
				}
			}
			
			for (MyCustomer mc : myCustomers) {
				if(mc.state == CustomerState.waitingForCheck) {
					getCheck(mc);
					return true;
				}
			}
			
			for (MyCustomer mc : myCustomers) {
				if(mc.state == CustomerState.gaveOrder) {
					takeOrderToCook(mc);
					return true;
				}
			}
			//  }

			/*  if(state == WaiterState.onBreak) {
			print("1");
			//Do timer and then notify host that you're off break
			takeBreak();
			return true;
			}*/
			//if the customer list is empty and the waiter's state is set to onBreak, start the timer.
			if(myCustomers.isEmpty() && state == WaiterState.readyToTakeBreak) {
				takeBreak();
				return true;
			}
		}

		catch (ConcurrentModificationException e) {
			return false;
		}

		return false;
		//we have tried all our rules and found
		//nothing to do. So return false to main loop of abstract agent
		//and wait.
	}

	// Actions

	/**
       Correctly seats customer and hands customer menu.
	 */
	private void seatCustomer(MyCustomer customer) {
		waiterGui.DoGetCustomer(customer.initialX+20, customer.initialY-20);
		try{
			busyWithTask.acquire();
		}
		catch(InterruptedException e) {
			e.printStackTrace();
		}
		host.waitingAreaAvailable(customer.c);
		waiterGui.DoSeatCustomer((Restaurant1CustomerRole) customer.c, customer.tableNumber);
		((Restaurant1CustomerRole) customer.c).followMe(this, myMenu);
		try{
			busyWithTask.acquire();
		}
		catch(InterruptedException e) {
			e.printStackTrace();
		}
		customer.state = CustomerState.seated;
		waiterGui.DoLeaveCustomer();

		//TESTING WAITER TAKING BREAK
		/*print(customer.c.getName());
		if(customer.c.getName().equals("break")) {
		    print("I want a break");
		    host.wantToGoOnBreak(this);
	    }*/
	}


	//Responds when customer is ready to order.

	private void takeOrder(MyCustomer customer) {
		waiterGui.DoGoToTable(customer.tableNumber);
		try {
			busyWithTask.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//Tells customer to give order
		((Restaurant1CustomerRole) customer.c).whatDoYouWant();
		customer.state = CustomerState.askedToOrder;
	}

	//Sends correct Customer order to cook
	private void takeOrderToCook(MyCustomer customer) {
		waiterGui.DoGoToCook();
		//freeze this thread until gui sends message back to release you from your current task when you reach the cook

		try {
			busyWithTask.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Here is the order");
		cook.hereIsOrder(this, customer.tableNumber, customer.choice);
		customer.state = CustomerState.gaveOrderAndWaiting;
		waiterGui.DoWaitingNearKitchen();
		//customer.state = CustomerState.waitingForFood;
	}

	//Let's customer know order is not available
	private void doNotHaveOrder(MyCustomer customer) {
		waiterGui.DoGoToTable(customer.tableNumber);
		try {
			busyWithTask.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		((Restaurant1CustomerRole) customer.c).orderNotAvailable(myMenu);
		customer.state = CustomerState.seated;
	}

	//Delivers finished order to correct Customer
	private void takeFoodToCustomer(int table, String choice) {
		//do animation to take food to customer, passing along the table
		waiterGui.DoGoToCook();
		try {
			busyWithTask.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		cook.pickedUpFood(this, table, choice);
		waiterGui.DoGoToTable(table);
		waiterGui.showFood(choice);
		try {
			busyWithTask.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//when reached the table, the gui will send a message to release you
		synchronized(myCustomers) {
			for(MyCustomer mc : myCustomers) {
				if(mc.tableNumber == table) {
					mc.state = CustomerState.eating;
					((Restaurant1CustomerRole) mc.c).hereIsYourFood(choice);
					waiterGui.setFood((Restaurant1CustomerRole) mc.c, choice);
					break;
				}
			}
		}
		waiterGui.DoLeaveCustomer();
	}

	private void getCheck(MyCustomer mc) {
		print("Getting check from cashier");
		//message cashier
		waiterGui.DoGoToCashier();
		try {
			busyWithTask.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		cashier.makeCheck(this, mc.c, mc.choice);
	}

	private void takeCheckToCustomer(Check currentCheck) {
		synchronized(myCustomers) {
			for(MyCustomer mc : myCustomers) {
				if(mc.c == currentCheck.customer) {
					waiterGui.DoGoToTable(mc.tableNumber);
				}
			}
		}
		try {
			busyWithTask.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		synchronized(myCustomers) {
			for(MyCustomer mc : myCustomers) {
				if(mc.c == currentCheck.customer) {
					((Restaurant1CustomerRole) mc.c).hereIsBill(currentCheck);
					break;
				}
			}
		}
		bills.remove(currentCheck);
		waiterGui.DoLeaveCustomer();
	}


	//Informs host that a table is free once the customer has finished eating
	private void tellHostTableFree(MyCustomer mc) {
		print("Customer is leaving. Notified the host.");
		host.tableFree(this, mc.tableNumber);	
		myCustomers.remove(mc);
		state = WaiterState.available;
	}

	private void askForBreak() {
		host.wantToGoOnBreak(this);
		state = WaiterState.askedForBreak;
	}

	private void takeBreak() {
		final Waiter temp = this;
		state = WaiterState.onBreak;
		//waiterGui.breakTime(true);
		/*	timer.schedule(new TimerTask() {
		public void run() {
		    print("Yo dawg, I'm off break");
		    state = WaiterState.available;
		    host.offBreak(temp);
		    //stateChanged();
		}
	    },
	    15000);*/
	}

	//utilities

	private void available() {
		busyWithTask.release();
	}

	public void setGui(RoleGui gui) {
		super.setGui(gui);
		waiterGui = (WaiterGui)gui;
	}

	public WaiterGui getGui() {
		return waiterGui;
	}


	private class MyCustomer {
		Customer c;
		int tableNumber;
		String choice;
		public CustomerState state = CustomerState.waiting;
		int initialX;
		int initialY;

		public MyCustomer(Customer customer,int table, CustomerState s) {
			c = customer;
			tableNumber = table;
			state = s;
		}

		public MyCustomer(Customer customer,int table, CustomerState s, int x, int y) {
			c = customer;
			tableNumber = table;
			state = s;
			initialX = x;
			initialY = y;
		}
	}
}
