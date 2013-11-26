package simcity.restaurants.restaurant3.src.restaurant;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Vector;
import java.util.concurrent.Semaphore;

import simcity.PersonAgent;
import simcity.agent.Role;
import simcity.restaurants.restaurant3.src.restaurant.gui.WaiterGui;
import agent.Agent;
//import restaurant.WaiterAgent.MyCustomer;

/**
 * Restaurant Host Agent
 */
//We only have 2 types of agents in this prototype. A customer and an agent that
//does all the rest. Rather than calling the other agent a waiter, we called him
//the HostAgent. A Host is the manager of a restaurant who sees that all
//is proceeded as he wishes.
public class HostRole extends Role {
	static final int NTABLES = 4;//a global for the number of tables.
	//Notice that we implement waitingCustomers using ArrayList, but type it
	//with List semantics.
	public List<MyCustomer> customers = Collections.synchronizedList(new ArrayList<MyCustomer>());
	private Vector<WaiterRole>  waiters;
	//public List<myCustomer> customers = new ArrayList<myCustomer>();
	public Collection<Table> tables;
	//note that tables is typed with Collection semantics.
	//Later we will see how it is implemented
	//private boolean waiterBusy= false;
	private String name;
	private Semaphore atTable = new Semaphore(0,true);

	public enum customerState {waitingToBeSeated, seated, leftRestaurant, restaurantFull}
	customerState cs;
	public WaiterGui waiterGui = null;

	public HostRole() {
		

		this.name = name;
		// make some tables
		tables = new ArrayList<Table>(NTABLES);
		for (int ix = 1; ix <= NTABLES; ix++) {
			tables.add(new Table(ix));//how you add to a collections
		}
	}

	public String getMaitreDName() {
		return name;
	}

	public String getName() {
		return name;
	}

	public List getCustomers() {
		return customers;
	}

	public Collection getTables() {
		return tables;
	}
	// Messages

	public void msgIWantToEat(CustomerRole cust) {
		System.out.println(cust.getName() + " tells the host that he/she is ready to eat.");
		int customerCapacity = tables.size();
		
		if (customerCapacity  <= customers.size()) 
			customers.add(new MyCustomer(cust, customerState.restaurantFull));
		else 
			customers.add(new MyCustomer(cust, customerState.waitingToBeSeated));
		
		stateChanged();
	}
	
	public void msgTableIsFree(CustomerRole cust) {
		System.out.println("Waiter tells the host that the table is free");
		for (MyCustomer mc:customers) {
			if (mc.cust == cust) {
				customers.remove(cust);
			}
		}
		for(Table t: tables){
			if (t.getOccupant() == cust) {
				System.out.println(cust.getName() + " is leaving " + t);
				System.out.println(t + " is now available.");
				t.setUnoccupied();
			}
		}
		stateChanged();
	}
	/* 
	public void msgLeavingTable(CustomerAgent cust) {
		System.out.println("msgLeavingTable() received");
		for (Table table : tables) {
			if (table.getOccupant() == cust) {
				System.out.println(cust + " leaving " + table);
				table.setUnoccupied();
				stateChanged();
			}
		}
	}
	*/

	public void msgAtTable() {//from animation
		//System.out.println("msgAtTable() called");
		atTable.release();// = true;
		stateChanged();
	}
	
	public boolean msgAskToGoOnBreak(WaiterRole w) {
		System.out.println(w.getName() + " asks the host if he/she can go on a break.");
		int availableWaiters = 0;
		// check the number of waiters
		for (int i = 0; i < waiters.size(); ++i) {
			if (!waiters.get(i).isOnBreak){
				++availableWaiters;
				System.out.println("Host tells " + w.getName() + " he/she cannot go on break at this time.");
			}
		}
		if (availableWaiters > 1){
			System.out.println("Host tells " + w.getName() + " he/she can go on break.");
			return true;
		}
		return false;
	}

	/**
	 * Scheduler.  Determine what action is called for, and do it.
	 */
	  //If there exists c in Customers such that c.cs = waitingToBeSeated and if there exists t in table
	public boolean pickAndExecuteAnAction() {
		
		/* Think of this next rule as:
            Does there exist a table and customer,
            so that table is unoccupied and customer is waiting.
            If so seat him at the table.
		 */
		
		if (waiters.size() == 0){
			return true;
		}
		synchronized(tables) {
		for (Table table : tables) {
			if (!table.isOccupied()) {
				synchronized(customers) {
				for (MyCustomer mc: customers ) {
					if(mc.cs == customerState.waitingToBeSeated) {
						WaiterRole waiter = hostChooseWaiter();
						tellWaiterToSeatCustomer(mc, table, waiter);//the action
					
					
					return true;//return true to the abstract agent to reinvoke the scheduler.
				
					}
				}
				}	
			}
		}
		}
		
		synchronized(customers) {
		for (MyCustomer mc: customers ) {
			if(mc.cs == customerState.restaurantFull) {
				Random r = new Random();
				boolean leaveRestaurantIfFull = r.nextBoolean();
				if (leaveRestaurantIfFull) {
					System.out.println("Restaurant is full. " + mc.cust.getName() + " chose to leave the restaurant");
					//mc.
					customers.remove(mc);
				}
				else {
					System.out.println("Restaurant is full. " + mc.cust.getName() + " chose to stay in the restaurant");
					mc.cs = customerState.waitingToBeSeated;
					stateChanged();
				}
				
				return true;
			}
		}
		}
		
		return false;
		//we have tried all our rules and found
		//nothing to do. So return false to main loop of abstract agent
		//and wait.
	}
		
	

	// Actions

	private void tellWaiterToSeatCustomer(MyCustomer mc, Table table, WaiterRole waiter) {
		//System.out.println("The host tells " + waiter.getName()+ " to seat " + mc.cust.getName() + " at " + table);
		mc.cust.setWaiter(waiter); //TO DO
		waiter.msgSitAtTable(mc.cust, table.tableNumber);
		
		table.setOccupant(mc.cust);
		customers.remove(mc);
		mc.cs = customerState.seated;
		//waiterGui.DoLeaveCustomer();
		/*try {
			//atTable.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.System.out.printlnStackTrace();
		}
		*/
	}

	
	//utilities

	public void setGui(WaiterGui gui) {
		waiterGui = gui;
	}

	public WaiterGui getGui() {
		return waiterGui;
	}
	
	public void setWaiter(Vector<WaiterRole>  waiters) {
		this.waiters = waiters;
	}
	private WaiterRole hostChooseWaiter() {
		//WaiterAgent result = null;
		if(customers.size() == 0 || waiters.size() == 0) {
			return null;
		}
		int min = Integer.MAX_VALUE;
		WaiterRole minWaiter = null;
		for(WaiterRole w: waiters) {
			if (w.isOnBreak)
				continue;
			if(w.getCustomersCount() < min) {
				minWaiter = w;
				min = w.getCustomersCount();
			}
		}
		return minWaiter;
	}

	private class Table {
		CustomerRole occupiedBy;
		int tableNumber;

		Table(int tableNumber) {
			this.tableNumber = tableNumber;
		}

		void setOccupant(CustomerRole cust) {
			occupiedBy = cust;
		}

		void setUnoccupied() {
			occupiedBy = null;
		}

		CustomerRole getOccupant() {
			return occupiedBy;
		}

		boolean isOccupied() {
			return occupiedBy != null;
		}

		public String toString() {
			return "table " + tableNumber;
		}
	}
	public class MyCustomer {
		CustomerRole cust;
		customerState cs;
		
		MyCustomer(CustomerRole cust, customerState cs) {
			this.cust = cust;
			this.cs = cs;
		}
	}
}

