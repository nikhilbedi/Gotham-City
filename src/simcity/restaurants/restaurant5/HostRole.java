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
import simcity.restaurants.restaurant5.gui.HostGui;
import simcity.restaurants.restaurant5.interfaces.*;
import simcity.restaurants.restaurant5.gui.*;
/**
 * Restaurant Host Agent
 */

public class HostRole extends Role implements Host {
	static final int NTABLES = 3;//a global for the number of tables.
	public List<MyCustomer> customers = Collections.synchronizedList(new ArrayList<MyCustomer>());

	public Collection<Table> tables;

	public List<MyWaiter> waiters = Collections.synchronizedList(new ArrayList<MyWaiter>());

	private String name;

	public HostGui hostGui = null;

	int avaliableWaiters;
	int seatedCust;
	int roundRobinPointer;
	//int mostCustomers = 0;

	public HostRole(String name) {
		super();
		this.name = name;

		roundRobinPointer = 0;
		avaliableWaiters = 0;
		seatedCust = 0;

		// make some tables
		tables = new ArrayList<Table>(NTABLES);
		for (int ix = 0; ix < NTABLES; ix++) {
			tables.add(new Table(ix));//how you add to a collections
		}

	}

	public HostRole(PersonAgent hostPerson) {
		super(hostPerson);
		
		roundRobinPointer = 0;
		avaliableWaiters = 0;
		seatedCust = 0;

		// make some tables
		tables = new ArrayList<Table>(NTABLES);
		for (int ix = 0; ix < NTABLES; ix++) {
			tables.add(new Table(ix));//how you add to a collections
		}
	}

	public HostRole() {
		super();
		roundRobinPointer = 0;
		avaliableWaiters = 0;
		seatedCust = 0;

		// make some tables
		tables = new ArrayList<Table>(NTABLES);
		for (int ix = 0; ix < NTABLES; ix++) {
			tables.add(new Table(ix));//how you add to a collections
		}

	}

	public String getMaitreDName() {
		return name;
	}

	public String getName() {
		return name;
	}

	public List getWaitingCustomers() {
		return customers;
	}

	public Collection getTables() {
		return tables;
	}
	// Messages

	//v2
	public void iWantToEat(Customer cust){
		print("Received message that " + cust.getName() + " wants to eat.");
		customers.add(new MyCustomer(cust));
		stateChanged();

	}

	public void tableIsFree(Waiter waiter, int t){
		print("Received message that customer has left table " + t);
		seatedCust--;
		synchronized(waiters){
			for(MyWaiter mywaiter: waiters){
				if(mywaiter.w.equals(waiter)){
					mywaiter.numCust--;
				}
			}
		}
		for(Table table: tables){
			if(table.tableNumber == t){
				table.setUnoccupied();
			}
		}
		stateChanged();
	}
	//v2.1
	public void iWantABreak(Waiter waiter){
		print("Received message that waiter " + waiter.getName() + " wants a break");
		synchronized(waiters){
			for(MyWaiter mywaiter: waiters){
				if(mywaiter.w.equals(waiter)){
					mywaiter.ws = WaiterState.wantBreak;
				}
			}
		}
		stateChanged();
	}

	public void offBreak(Waiter waiter){
		print("Received message that waiter " + waiter.getName() + " is going off break");
		synchronized(waiters){
			for(MyWaiter mywaiter: waiters){
				if(mywaiter.w == waiter){
					mywaiter.ws = WaiterState.working;
					avaliableWaiters++;
				}
			}
		}
		stateChanged();
	}

	public void noLongerHungry(Customer c) {
		print("Received message that waiter " + c.getName() + " is no longer hungry.");
		synchronized(customers){
			for(MyCustomer mc: customers){
				if(mc.c.equals(c)){
					mc.cs = CustomerState.left;
				}
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

            ^note the above no longer applies
		 */
		
		/**THIS IS HACKED FIX IT
		
		
		/*if(seatedCust == NTABLES){
			synchronized(customers){
				for(MyCustomer mycustomer: customers){
					if(mycustomer.cs == CustomerState.newCust){
						informFull(mycustomer);
					}
				}
			}
		}
		
		synchronized(customers){
			for(MyCustomer mycustomer: customers){
				if(mycustomer.cs == CustomerState.left){
					handleLeft(mycustomer);
					return true;
				}
			}
		}
		synchronized(waiters){
			for(MyWaiter mywaiter: waiters){
				if(mywaiter.ws == WaiterState.wantBreak){
					tryGiveWaiterBreak(mywaiter);
					return true;
				}
			}
		}
		for (Table table : tables) {
			if (!table.isOccupied()) {
				if (!customers.isEmpty()){
					if(avaliableWaiters > 0) {
						while(waiters.get(roundRobinPointer).ws == WaiterState.onBreak){
							roundRobinPointer ++;
							if(roundRobinPointer > waiters.size()-1)
								roundRobinPointer = 0;
						}
					//print("Before! " + waiters.toString());
						giveWaiterCustomer(waiters.get(roundRobinPointer), customers.get(0), table);
						seatedCust++;
						roundRobinPointer++;
						if(roundRobinPointer > waiters.size()-1)
							roundRobinPointer = 0;
						//print("After! " + waiters.toString());
					}		
					return true;
				}
			}
		}
		if(!customers.isEmpty()){
			return true;
		}*/
		return false;
		//we have tried all our rules and found
		//nothing to do. So return false to main loop of abstract agent
		//and wait.
	}

	private void handleLeft(MyCustomer mycustomer) {
		//System.err.println("removing customer");
		customers.remove(mycustomer);

	}

	// Actions
	public void giveWaiterCustomer(MyWaiter mw, MyCustomer mc, Table t){
		print("Giving customer " + mc.c.getName() + " to Waiter " + mw.w.getName());
		mw.w.sitAtTable(this, mc.c, t.tableNumber);
		mw.numCust ++;
		t.setOccupant(mc.c);
		customers.remove(mc);

	}

	public void tryGiveWaiterBreak(MyWaiter mw){
		if(avaliableWaiters>1){
			mw.ws = WaiterState.onBreak;
			print("Break request from " + mw.w.getName() + " approved");
			mw.w.breakApproved();
			avaliableWaiters--;
		}
		else{
			mw.ws = WaiterState.working;
			print("Break request from " + mw.w.getName() + " denied");
			mw.w.cantBreak();
		}
	}

	public void informFull(MyCustomer mc){
		mc.cs = CustomerState.informed;
		mc.c.restIsFull(this);
	}

	//utilities
	public int getNumTables(){
		return NTABLES;
	}

	public void addWaiter(Waiter w){
		waiters.add(new MyWaiter(w, 0));
		avaliableWaiters++;
	}

	public void setGui(RoleGui g) {
		super.setGui(g);
		hostGui = (HostGui)g;
	}

	public HostGui getGui() {
		return hostGui;
	}

	public enum WaiterState{
		working,wantBreak,onBreak};

		private class MyWaiter{
			Waiter w;
			int numCust;
			WaiterState ws;
			public MyWaiter(Waiter waiter, int i){
				w = waiter;
				numCust = i;
				ws = WaiterState.working;
			}
		}

		public enum CustomerState {
			newCust,informed,left};

			private class MyCustomer{
				Customer c;
				CustomerState cs;
				public MyCustomer(Customer c){
					this.c = c;
					cs = CustomerState.newCust;
				}

			}

			public enum TableState
			{occupied, vacant};

			private class Table {
				Customer occupiedBy;
				int tableNumber;
				TableState s;
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

