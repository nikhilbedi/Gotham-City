package simcity.restaurants.restaurant3;

import java.util.*;
import java.util.concurrent.Semaphore;

import simcity.PersonAgent;
import Gui.RoleGui;
import agent.Role;
import simcity.restaurants.restaurant3.gui.HostGui;
import simcity.restaurants.restaurant3.gui.WaiterGui;
import simcity.restaurants.restaurant3.interfaces.*;
import trace.AlertLog;
import trace.AlertTag;
import agent.Agent;
//import restaurant.WaiterAgent.MyCustomer;

/**
 * Restaurant Host Agent
 */
//We only have 2 types of agents in this prototype. A customer and an agent that
//does all the rest. Rather than calling the other agent a waiter, we called him
//the HostAgent. A Host is the manager of a restaurant who sees that all
//is proceeded as he wishes.
public class HostRole extends Role implements Host {
	static final int NTABLES = 4;//a global for the number of tables.
	//Notice that we implement waitingCustomers using ArrayList, but type it
	//with List semantics.
	public List<MyCustomer> customers = Collections.synchronizedList(new ArrayList<MyCustomer>());
	private Vector<Waiter>  waiters = new Vector<Waiter>();
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
	public HostGui hostGui = null;

	public HostRole(PersonAgent p) {
		super(p);
	
		// make some tables
		tables = new ArrayList<Table>(NTABLES);
		for (int ix = 1; ix <= NTABLES; ix++) {
			tables.add(new Table(ix));//how you add to a collections
		}
	}
	public HostRole() {
		super();
	
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

	public void msgIWantToEat(Restaurant3CustomerRole cust) {
		AlertLog.getInstance().logInfo(AlertTag.REST3, this.getName(),
				"tells the host that he/she is ready to eat. ");
		//System.out.println(cust.getName() + " tells the host that he/she is ready to eat.");
		int customerCapacity = tables.size();
		
		if (customerCapacity  <= customers.size()) 
			customers.add(new MyCustomer(cust, customerState.restaurantFull));
		else 
			customers.add(new MyCustomer(cust, customerState.waitingToBeSeated));
		
		stateChanged();
	}
	
	public void msgTableIsFree(Customer cust) {
		AlertLog.getInstance().logInfo(AlertTag.REST3, this.getName(),
				"Waiter tells the host that the table is free. ");
		//System.out.println("Waiter tells the host that the table is free");
		for (MyCustomer mc:customers) {
			if (mc.cust == cust) {
				customers.remove(cust);
			}
		}
		for(Table t: tables){
			if (t.getOccupant() == cust) {
				AlertLog.getInstance().logInfo(AlertTag.REST3,
						this.getName(), t+ " is now available.");
				//System.out.println(t + " is now available.");
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
	
	public boolean msgAskToGoOnBreak(Waiter w) {
		AlertLog.getInstance().logInfo(AlertTag.REST3, this.getName(),
				" asks the host if he/she can go on a break. ");
		//System.out.println(w.getName() + " asks the host if he/she can go on a break.");
		int availableWaiters = 0;
		// check the number of waiters
		for (int i = 0; i < waiters.size(); ++i) {
			if (!waiters.get(i).isOnBreak){
				++availableWaiters;
				AlertLog.getInstance().logInfo(AlertTag.REST3,
						this.getName(), "Host tells waiter he/she cannot go on break at this time. ");
				//System.out.println("Host tells " + w.getName() + " he/she cannot go on break at this time.");
			}
		}
		if (availableWaiters > 1){
			AlertLog.getInstance().logInfo(AlertTag.REST3,
					this.getName(), "Host tells he/she can go on break. ");
			//System.out.println("Host tells " + w.getName() + " he/she can go on break.");
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
		if(theManLeavingMe != null && customers.isEmpty()) {
			leaveWork();
			return true;
		}
		
		if (waiters.size() == 0){
			return true;
		}
		synchronized(tables) {
		for (Table table : tables) {
			if (!table.isOccupied()) {
				synchronized(customers) {
				for (MyCustomer mc: customers ) {
					if(mc.cs == customerState.waitingToBeSeated) {
						Waiter waiter = hostChooseWaiter();
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
					AlertLog.getInstance().logInfo(AlertTag.REST3,
							this.getName(), "Restaurant is full. Customer chose to leave the restaurant ");
					//System.out.println("Restaurant is full. Customer chose to leave the restaurant");
					//mc.
					customers.remove(mc);
				}
				else {
					AlertLog.getInstance().logInfo(AlertTag.REST3,
							this.getName(), "Restaurant is full. Customer chose to stay in the restaurant ");
					//System.out.println("Restaurant is full. Customer chose to stay in the restaurant");
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

	private void tellWaiterToSeatCustomer(MyCustomer mc, Table table, Waiter waiter) {
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
/*
	public void setGui(WaiterGui gui) {
		waiterGui = gui;
	}

	public WaiterGui getGui() {
		return waiterGui;
	}*/
	
	public void setGui(RoleGui g) {
		super.setGui(g);
		hostGui = (HostGui)g;
	}
	
	public HostGui getGui() {
		return hostGui;
	}
	
	public void setWaiter(Waiter  waiter) { //CHANGED TO SINGLE WAITER AND ADDED THAT WAITER TO WAITER VECTOR
		waiters.add(waiter);
		//this.waiters = waiters;
	}
	
	private Waiter hostChooseWaiter() {
		//WaiterAgent result = null;
		if(customers.size() == 0 || waiters.size() == 0) {
			return null;
		}
		int min = Integer.MAX_VALUE;
		Waiter minWaiter = null;
		for(Waiter w: waiters) {
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
	public class MyCustomer {
		Customer cust;
		customerState cs;
		
		MyCustomer(Customer cust, customerState cs) {
			this.cust = cust;
			this.cs = cs;
		}
	}
	
}

