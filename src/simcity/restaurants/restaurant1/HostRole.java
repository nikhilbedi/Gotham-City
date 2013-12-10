package simcity.restaurants.restaurant1;

import simcity.PersonAgent;
import simcity.tests.mock.*;
import simcity.restaurants.restaurant1.WaiterRole;
import simcity.restaurants.restaurant1.Restaurant1CustomerRole;
import simcity.restaurants.restaurant1.Menu;
import simcity.restaurants.restaurant1.gui.HostGui;
import simcity.restaurants.restaurant1.interfaces.*;
import Gui.RoleGui;
import agent.Role;

import java.util.*;
import java.util.concurrent.Semaphore;

/**
 * Restaurant Host Agent
 */
//We only have 2 types of agents in this prototype. A customer and an agent that
//does all the rest. Rather than calling the other agent a waiter, we called him
//the HostAgent. A Host is the manager of a restaurant who sees that all
//is proceeded as he wishes.
public class HostRole extends Role implements Host {
	static final int NTABLES = 4;//a global for the number of tables.
	public List<Customer> waitingCustomers
	= new ArrayList<Customer>();
	public Collection<Table> tables;
	public List<MyWaiter> myWaiters = new ArrayList<MyWaiter>();
	//protected so Waiter can read the waiting area locations
	// protected List<WaitingArea> waitingAreas;
	WaitingArea[] waitingAreas = new WaitingArea[4];
	private String name;

	public HostGui hostGui = null;

	public HostRole(PersonAgent p) {
		super(p);
		// make some tables
		tables = new ArrayList<Table>(NTABLES);
		for (int ix = 1; ix <= NTABLES; ix++) {
			tables.add(new Table(ix));//how you add to a collections
		}

		//make some waiting areas
		for(int i =0; i < 4; i++) {
			waitingAreas[i] = new WaitingArea(i+1);
			waitingAreas[i].posX = 20;
			waitingAreas[i].posY = ((i+1)*40)-20;
		}	
	}

	public HostRole() {
		// make some tables
		super();
		tables = new ArrayList<Table>(NTABLES);
		for (int ix = 1; ix <= NTABLES; ix++) {
			tables.add(new Table(ix));//how you add to a collections
		}

		//make some waiting areas
		for(int i =0; i < 4; i++) {
			waitingAreas[i] = new WaitingArea(i+1);
			waitingAreas[i].posX = 20;
			waitingAreas[i].posY = ((i+1)*40)-20;
		}	
	}


	public void addWaiter(Waiter waiter) {
		//This check is meant resolve an issue where the host's thread is asleep despite adding his first waiter to handle all of the waiting customers
		if(myWaiters.isEmpty()) {
			myWaiters.add(new MyWaiter(waiter));
			if (myPerson != null)
				stateChanged();
		}
		else {
			myWaiters.add(new MyWaiter(waiter));
			if (myPerson != null)
				stateChanged();
		}

	}

	public String getMaitreDName() {
		return name;
	}

	public String getName() {
		return name;
	}

	public List getWaitingCustomers() {
		return waitingCustomers;
	}

	public Collection getTables() {
		return tables;
	}

	// Messages

	/**
       Handles new Customer and puts it onto waiting list
	 */
	public void msgIWantFood(Customer customer) {
		print("received message that customer is hungry.");
		waitingCustomers.add(customer);
		//Waiter
		print("persons name is " + myPerson.getName());
		stateChanged();
	}

	public void inWaitingPosition() {
		print("received message customer is waiting for service");
		stateChanged();
	}

	public void waitingAreaAvailable(Customer c) {
		for(WaitingArea w : waitingAreas) {
			if(w.getOccupant() == c){
				w.setUnoccupied();
			}
		}
		waitingCustomers.remove(c);
		stateChanged();
	}

	public void noServiceSoLeft(Customer cust, Waiter w) {
		print("No! My most valuable customer " + cust + " just left! These waiters should be working harder...");
		//it'd be interesting if the host didn't allow waiters to take breaks if this happened
		waitingCustomers.remove(cust);

		if(w != null) {
			((WaiterRole) w).customerLeft(cust);
		}

		stateChanged();

	}

	public void wantToGoOnBreak(Waiter waiter) {
		//call an action that checks if waiter can go on break
		print("Really? Forget about your paycheck. Lemme see if I can let you take a break...");
		for(MyWaiter mw : myWaiters) {
			if(mw.waiter == waiter) {
				mw.askedToGoOnBreak = true;
			}
		}
		stateChanged();
	}

	public void offBreak(Waiter waiter) {
		print("It took you a while to get off break " + ((PersonAgent) waiter).getName() + "...");
		for(MyWaiter mw : myWaiters) {
			if(mw.waiter == waiter) {
				mw.onBreak = false;
			}
		}
		stateChanged();
	}

	public void tableFree(Waiter waiter, int table) {
		for(Table t : tables) {
			if(t.tableNumber == table) {
				t.setUnoccupied();
				print("Freed the table in the tables list due to waiter's message.");
				break;
			}
		}
		//decrement the amount of customers a waiter has 
		for(MyWaiter w : myWaiters) {
			if(w.waiter == waiter) {
				w.amtOfCustomers--;
				break;
			}
		}
		stateChanged();
	}

	/**
	 * Scheduler.  Determine what action is called for, and do it.
	 */
	public boolean pickAndExecuteAnAction() {
		/* Think of this next rule as:
	   Does there exist a table and customer,
	   so that table is unoccupied and customer is waiting.
	   If so seat him at the table.

	  /*If there is a customer in the customer list who's state is waiting,
	  if there is a waiter in the waiters list who's state is available,
	  if there is a table in tables such that it's state is unoccupied,
	  Have the waiter seat that customer at an available table.
		 */
		/**
	a table becomes free and a waiter is known
		 */
		//leave cuz work is over
		if(theManLeavingMe != null && waitingCustomers.isEmpty()) {
			leaveWork();
			return true;
		}
		
		//Checks if a waiter wants to go on break
		for(MyWaiter w : myWaiters) {
			if(w.askedToGoOnBreak) {
				waiterWantsBreak(w);
				return true;
			}
		}

		//Assign customer to waitingArea
		for(Customer customer : waitingCustomers) {
			for(WaitingArea w: waitingAreas) {
				if(w.occupiedBy == customer){
					break;
				}
				if(!w.isOccupied()) {
					//w.setOccupant(customer);
					tellCustomerWait(customer, w);
					return true;
				}
			}
		}

		//Properly assigns customers 
		for (Table table : tables) {
			if (!table.isOccupied()) {
				//	print(table + " is free");
				for (Customer customer : waitingCustomers) {
					
					//We need to find the waiter with the least customers to hand this customer off to him/her
					/**
			       this is a mechanism to help load the balance of waiters
					 */
					if(!myWaiters.isEmpty()) {
						int i = 0;
						MyWaiter leastCustomersHeld;
						for(MyWaiter w : myWaiters) {
							//NOTICE. IN SIMCITY, IM NOW CHECKING THE WORK STATUS
							if(!w.onBreak && ((Role)w.waiter).checkWorkStatus()) {
								leastCustomersHeld = w;
								for(MyWaiter mw : myWaiters) {
									if(mw.amtOfCustomers < leastCustomersHeld.amtOfCustomers && !mw.onBreak) {
										leastCustomersHeld = mw;
									}
								}
								//increment the amount of customers this waiter has now
								leastCustomersHeld.amtOfCustomers++;
								seatCustomer(waitingCustomers.get(0), table, leastCustomersHeld.waiter);//the action
								return true;//return true to the abstract agent to reinvoke the scheduler
							}
						}
					}
				}
			}
		}



		return false;
		//we have tried all our rules and found
		//nothing to do. So return false to main loop of abstract agent
		//and wait.
	}

	// Actions
	
	private void waiterWantsBreak(MyWaiter mw) {
		//compare how many waiters to customers there are. 
		//if waiters >= customers, then let him go on break after he's finished with his customer
		int customerAmount = 0;
		/*	for(MyWaiter mw : myWaiters) {
		    customerAmount = customerAmount + mw.amtOfCustomers;
		}
		if(myWaiters.size() >= customerAmount)*/

		//Actually, make it simple. If waiter's amtOfCustomers is <2, let him go on break after he's finished.
		if(mw.amtOfCustomers < 2) {
			print("Okay, go on break " + ((PersonAgent) mw.waiter).getName() + "... BUT DON'T ASK ME FOR ANOTHER ONE!");   
			mw.waiter.goOnBreak();
			mw.askedToGoOnBreak = false;
			mw.onBreak = true;
			return;
		}
		print("You lousy mutt! You think I'll ever let you go on break with all these customers rolling on, " + ((PersonAgent) mw.waiter).getName() + "?! GET BACK TO WORK!!!");
		mw.waiter.doNotGoOnBreak();
		mw.askedToGoOnBreak = false;
	}

	private void tellCustomerWait(Customer customer, WaitingArea w){
		w.setOccupant(customer);
		((Restaurant1CustomerRole) customer).waitInArea(w.posX, w.posY);
	}

	private void seatCustomer(Customer customer, Table t, Waiter w) {
		waitingCustomers.remove(customer);
		Customer c = customer;
		for(WaitingArea wa : waitingAreas) {
			if(wa.getOccupant() == c){
				w.pleaseSeatCustomer(c, t.tableNumber, wa.posX, wa.posY);
				t.setOccupant(c);
				//wa.setUnoccupied();
				break;
			}
		}
	}


	//utilities
	public void setGui(RoleGui g) {
		super.setGui(g);
		hostGui = (HostGui)g;
	}

	public HostGui getGui() {
		return hostGui;
	}

	private class MyWaiter {
		Waiter waiter;
		int amtOfCustomers;
		boolean askedToGoOnBreak;
		boolean onBreak;

		MyWaiter(Waiter w){
			waiter = w;
			amtOfCustomers = 0;
			askedToGoOnBreak = false;
			onBreak = false;
		}
	}

	class WaitingArea {
		Customer occupiedBy;
		int posNumber;
		int posX;
		int posY;

		WaitingArea(int pos){
			posNumber = pos;
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
			return "waiting area " + posNumber;
		}
		public int getPosNumber() {
			return posNumber;
		}
	}

	private class Table {
		Customer occupiedBy;
		int tableNumber;
		int tableX;
		int tableY;

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
		public int getTableNumber() {
			return tableNumber;
		}
	}
}

