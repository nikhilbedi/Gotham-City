package restaurant2;

import agent.Agent;
import agent.Role;
//import restaurant.HostAgent.Table;
import restaurant2.CustomerAgent.AgentEvent;
import restaurant2.gui.HostGui;
import restaurant2.gui.WaiterGui;
import restaurant2.interfaces.Cashier;
import restaurant2.interfaces.Cook;
import restaurant2.interfaces.Customer;
import restaurant2.interfaces.Host;
import restaurant2.interfaces.Waiter;

import java.util.*;
import java.util.concurrent.Semaphore;

/**
 * Restaurant Waiter Agent
 */
public class WaiterAgent extends Role implements Waiter{
	static final int NTABLES = 3;//a global for the number of tables.
	//Notice that we implement waitingCustomers using ArrayList, but type it
	//with List semantics.
	public List<Customer> waitingCustomers = Collections.synchronizedList(new ArrayList<Customer>());
	public List<MyCustomer> myCustomers
	= Collections.synchronizedList(new ArrayList<MyCustomer>());
	Host host;
	Cook cook;
	Cashier cashier;
	//public Collection<Table> tables;
	private boolean isAvailable = true, OnBreak = false;
	private Menu menu = new Menu();
	//note that tables is typed with Collection semantics.
	//Later we will see how it is implemented

	private String name;
	private Semaphore atTable = new Semaphore(0,true);
	private Semaphore atCook = new Semaphore(0,true);
	private Semaphore atCashier = new Semaphore(0,true);
	private Timer timer = new Timer();
	private Semaphore waitingForOrder = new Semaphore(0, true);
	private int currentOrderTableNumber = -1, currentCheckTableNumber = -1;
	
	public HostGui hostGui = null;
	public WaiterGui waiterGui;
	
	public WaiterAgent(String name) {
		super();

		this.name = name;
	}
	
	public String getName() {
		return name;
	}

	/*public Collection getTables() {
		return tables;
	}*/
	
	public void setCook(Cook c) {
		cook = c;
	}
	
	public void setHost(Host h) {
		host = h;
	}
	
	public void setCashier(Cashier c) {
		cashier = c;
	}
	
	public boolean isAvailable() { return isAvailable; }
	// Messages

	public void msgIWantFood(Customer cust) {
		waitingCustomers.add(cust);
		stateChanged();
	}

	/*public void msgLeavingTable(CustomerAgent cust) {
		for (Table table : tables) {
			if (table.getOccupant() == cust) {
				print(cust + " leaving " + table);
				table.setUnoccupied();
				stateChanged();
			}
		}
	}*/
	
	public void msgAtTable() {//from animation
		print("msgAtTable() called");
		atTable.release();// = true;
		stateChanged();
	}
	
	public void msgAtEntrance() {
		stateChanged();
	}
	
	public void msgAtCook() {
		print("msgAtCook() called");
		atCook.release();
		myCustomers.get(find(currentOrderTableNumber)).s = customerState.foodInTransit;
		DeliverOrder();
	}
	
	public void msgAtCashier() {
		print("msgAtCashier() called");
		atCashier.release();
		myCustomers.get(find(currentCheckTableNumber)).s = customerState.checkInTransit;
		DeliverCheck();
	}

	public void msgPleaseSeatCustomer (Customer c, int table) { 
		print("Received msg to seat customer.");
		myCustomers.add(new MyCustomer(c, table));
		myCustomers.get(find(c)).s = customerState.waiting;
		menu = new Menu();
		stateChanged();
	}
	
	public void msgReadyToOrder(Customer c) {
		MyCustomer mc = myCustomers.get(find(c));
		print("Received ready msg from table " + mc.table);
		mc.s = customerState.readyToOrder;
		stateChanged();
	}
	
	public void msgHereIsMyOrder( String choice, Customer c) {
		waitingForOrder.release();
		TakeOrder( myCustomers.get(find(c)), choice);
		stateChanged();
	}
	
	public void msgOutOfChoice( String choice, int table) {
		print("Received msgOutofChoice");
		print("Sorry, we're out of " + choice);
		menu.removeFood(choice);
		myCustomers.get(find(table)).c.msgOutOfChoice(menu);
		myCustomers.get(find(table)).s = customerState.seated;
		stateChanged();
	}
	
	public void msgOrderDone(String choice, int table) {
		//GoPickUpOrder(choice, table);
		myCustomers.get(find(table)).s = customerState.foodReady;
		stateChanged();
	}
	
	public void msgDoneAndLeaving( Customer c) {
		myCustomers.get(find(c)).s = customerState.leaving;
		stateChanged();
	}
	
	public void msgGoOnBreak(boolean canGoOnBreak) {
		if(canGoOnBreak) {
			isAvailable = false;
			print("Will go on break once done with customers.");
		}
		else {
			print("Will not go on break.");
		}
	}
	
	public void msgCheckReady(Check c) {
		myCustomers.get(find(c.getTable())).s = customerState.checkReady;
		myCustomers.get(find(c.getTable())).check = c;
		//currentCheckTableNumber = c.getTable();
		stateChanged();
	}
	
	/**
	 * Scheduler.  Determine what action is called for, and do it.
	 */
	protected boolean pickAndExecuteAnAction() {
		/* Think of this next rule as:
            Does there exist a table and customer,
            so that table is unoccupied and customer is waiting.
            If so seat him at the table
		 */
		
		try {
			for (int b = 0; b < myCustomers.size(); b++) { 
				if (myCustomers.get(b).s == customerState.waiting /*&& waiterGui.isAtEntrance()*/) {
					if(waiterGui.isAtCustomer())
					{
						seatCustomer(myCustomers.get(b).c, myCustomers.get(b).table);
					}
					else
						waiterGui.doPickUpCustomer(myCustomers.get(b).c.getWaitingNumber());
					return true;
				}
			}
			
			for (int b = 0; b < myCustomers.size(); b++) {
				if (myCustomers.get(b).s == customerState.readyToOrder/* && waiterGui.isAtEntrance()*/) {
						goToTableforOrder(myCustomers.get(b).c, myCustomers.get(b).table);
					return true;
				}
			}
			
			for (int b = 0; b < myCustomers.size(); b++) {
				if (myCustomers.get(b).s == customerState.orderTaken) {
					dropoffOrder(myCustomers.get(b).c, myCustomers.get(b).table);
					return true;
				}
			}
			
			for (int b = 0; b < myCustomers.size(); b++) {
				if(myCustomers.get(b).s == customerState.foodInTransit) {
					waiterGui.setString(myCustomers.get(b).choice.substring(0,2).toUpperCase());
					waiterGui.showString(true);
					//waiterGui.DoGoToTable(currentOrderTableNumber);
					try {
						atTable.acquire();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					DropOffFood(myCustomers.get(b).choice);
					waiterGui.showString(false);
					cashier.msgNeedCheck(this, myCustomers.get(b).price , currentOrderTableNumber);
					return true;
				}
			}
			
			for (int b = 0; b < myCustomers.size(); b++) {
				if(myCustomers.get(b).s == customerState.checkInTransit) {
					//waiterGui.setString(myCustomers.get(b).choice.substring(0,2).toUpperCase());
					//waiterGui.showString(true);
					try {
						atTable.acquire();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					DropOffCheck(myCustomers.get(b).check, myCustomers.get(b).table);
					//waiterGui.showString(false);
					return true;
				}
					
			}
			
			
			for (int b = 0; b < myCustomers.size(); b++) {
				if (myCustomers.get(b).s == customerState.foodReady) {
						GoPickUpOrder(myCustomers.get(b).choice, myCustomers.get(b).table);
					return true;
				}
			}
			
			for (int b = 0; b < myCustomers.size(); b++) {
				if (myCustomers.get(b).s == customerState.checkReady) {
					currentCheckTableNumber = myCustomers.get(b).table;
					pickUpCheck(myCustomers.get(b).table);
					return true;
				}
			}
			
			
			for (int b = 0; b < myCustomers.size(); b++) {
				if(myCustomers.get(b).s == customerState.leaving) {
					/*try {
						atTable.acquire();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}*/
					//print("HI");
					PrepareEmptyTable(myCustomers.get(b).c);
					return true;
				}
			}
		}
		
		catch(ConcurrentModificationException e) {
			return false;
		}
			
			waiterGui.DoGoToDefault();	
			
			if(!isAvailable && myCustomers.size() == 0 && !OnBreak)
				goOnBreak();

		return false;
		//we have tried all our rules and found
		//nothing to do. So return false to main loop of abstract agent
		//and wait.
	}

	// Actions

	private void seatCustomer(Customer c, int tableNumber) {
		c.msgSitAtTable(tableNumber);
		c.msgFollowMe(new Menu(), this);
		DoSeatCustomer(c, tableNumber);
		try {
			atTable.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		myCustomers.get(find(c)).s = customerState.seated;
		waiterGui.DoLeaveCustomer();
		
		if(name.equalsIgnoreCase("OnBreak")) {
			print("I would like to go on break.");
			host.msgWantToGoOnBreak(this);
		}
		
		//waitingCustomers.remove(customer);
		//hostGui.DoLeaveCustomer();
	}
	
	private void goToTableforOrder(Customer c, int tableNumber) {
		waiterGui.DoGoToTable(tableNumber);
		MyCustomer mc = myCustomers.get(find(c));
		
		try {
			atTable.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		mc.c.msgWhatWouldYouLike();
		mc.s = customerState.askedToOrder;
		
		try {
			waitingForOrder.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//waiterGui.DoLeaveCustomer();
	}
	
	public void TakeOrder(MyCustomer c, String choice) {
		print("Recorded order of " + choice);
		c.choice = choice;
		c.s = customerState.orderTaken;
		
		switch(choice) {
			case "Steak": c.price = 15.99; break;
			case "Pizza": c.price = 8.99; break;
			case "Chicken": c.price = 10.99; break;
			case "Salad": c.price = 5.99;
		}
		
		//stateChanged();
		//waiterGui.DoLeaveCustomer();
	}
	
	public void dropoffOrder( Customer c, int table) {
		print("Dropping off Order");
		MyCustomer mc = myCustomers.get(find(c));
		mc.s = customerState.orderBeingDroppedOff;
		dropOffOrders();
	}
	
	public void dropOffOrders() {
		for(int c = 0; c < myCustomers.size(); c++) {
			if(myCustomers.get(c).s == customerState.orderBeingDroppedOff) {
				print("Here the order for table " + myCustomers.get(c).table);
				cook.msgHereIsOrder(this, myCustomers.get(c).choice, myCustomers.get(c).table);
				myCustomers.get(c).s = customerState.waitingForFood;
			}
		}
	}
	
	public void GoPickUpOrder( String choice, int table) {
		print("Picking up order");
		//myCustomers.get(find(table)).s = customerState.foodInTransit;
		currentOrderTableNumber = table;
		waiterGui.DoGoToCook();
		
		//atCook.drainPermits();
		//atCook.release();
		atCook.release();
		//myCustomers.get(find(currentOrderTableNumber)).s = customerState.foodInTransit;
		try {
			atCook.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void DeliverOrder() {
		print("delivering order");
		cook.msgPickedUpOrder(currentOrderTableNumber);
		myCustomers.get(find(currentOrderTableNumber)).s = customerState.foodInTransit;
		//atTable.drainPermits();
		atTable.release();
		waiterGui.DoGoToTable(currentOrderTableNumber);
		try {
			atTable.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void DeliverCheck() {
		print("delivering check");
		myCustomers.get(find(currentCheckTableNumber)).s = customerState.checkInTransit;
		//DropOffCheck(myCustomers.get(find(currentCheckTableNumber)).check);
		//atTable.drainPermits();
		atTable.release();
		waiterGui.DoGoToTable(currentCheckTableNumber);
		try {
			atTable.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void DropOffFood(String choice) {
		print("Here is your " + choice);
		myCustomers.get(find(currentOrderTableNumber)).s = customerState.eating;
		myCustomers.get(find(currentOrderTableNumber)).c.msgHereIsYourFood(choice);
		stateChanged();
	}
	
	private void DropOffCheck(Check check, int table) {
		print("Here is your check of $" + check.getTotal());
		myCustomers.get(find(table)).s = customerState.receivedCheck;
		myCustomers.get(find(table)).c.msgHereIsYourCheck(check);
		stateChanged();
	}
	
	private void pickUpCheck(int table) {
		print("Picking up check.");
		waiterGui.DoGoToCashier();
		currentCheckTableNumber = table;
		//DropOffCheck(myCustomers.get(find(table)).check, table);
		//atCashier.release();
		try {
			atCashier.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void PrepareEmptyTable( Customer c) {
		MyCustomer mc = myCustomers.get(find(c));
		print("Clearing table " + mc.table);
		int tablenum = mc.table;
		myCustomers.remove(mc);
		waiterGui.DoGoToTable(tablenum);
		//atTable.release();
		try {
			atTable.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		host.msgTableEmpty(tablenum);
		//atTable.release();
		
	}
	
	public void askForBreak() {
		print("I would like to go on break.");
		host.msgWantToGoOnBreak(this);
	}
	
	private void goOnBreak() {
		print("Going on break.");
		OnBreak = true;
		
		timer.schedule(new TimerTask() {
			Object cookie = 1;
			public void run() {
				isAvailable = true;
				OnBreak = false;
				print("Back from break.");
				//isHungry = false;
				stateChanged();
			}
		},
		10000);
	}
	
	// The animation DoXYZ() routines
	private void DoSeatCustomer(Customer c, int tableNumber) {
		//Notice how we print "customer" directly. It's toString method will do it.
		//Same with "table"
		print("Seating " + c + " at table " + tableNumber);
		waiterGui.DoBringToTable(c, tableNumber); 

	}

	//utilities

	public void setGui(WaiterGui gui) {
		waiterGui = gui;
	}

	public WaiterGui getGui() {
		return waiterGui;
	}
	
	public class MyCustomer {
		Customer c;
		String choice;
		int table;
		double price;
		customerState s;
		Check check;
		
		public MyCustomer(Customer c2, int tableNumber) {
			c = c2;
			table = tableNumber;				
		}
		
	}
	
	public int find(Customer c) {
		for(int a = 0; a < myCustomers.size(); a++) {
			if(c.getName() == myCustomers.get(a).c.getName())
				return a;
		}
		return -1;
	}
	
	public int find(int tableNumber) {
		for(int a = 0; a < myCustomers.size(); a++) {
			if(tableNumber == myCustomers.get(a).table)
				return a;
		}
		return -1;
	}
	
	public int getNumCustomers() {
		return myCustomers.size();
	}
	
	public enum customerState {waiting, beingSeated, 
		seated, readyToOrder, askedToOrder, orderTaken, orderBeingDroppedOff,
		waitingForFood, foodReady, foodInTransit, eating, checkReady, checkInTransit,
		receivedCheck, leaving;}
	
}

