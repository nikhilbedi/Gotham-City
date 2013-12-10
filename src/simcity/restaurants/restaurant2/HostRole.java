package simcity.restaurants.restaurant2;

import Gui.RoleGui;
import agent.Agent;
import agent.Role;
import simcity.restaurants.restaurant2.gui.HostGui;
import simcity.restaurants.restaurant2.interfaces.Customer;
import simcity.restaurants.restaurant2.interfaces.Host;
import simcity.restaurants.restaurant2.interfaces.Waiter;
import simcity.PersonAgent;

import java.util.*;
import java.util.concurrent.Semaphore;

/**
 * Restaurant Host Agent
 */
//We only have 2 types of agents in this prototype. A customer and an agent that
//does all the rest. Rather than calling the other agent a waiter, we called him
//the HostAgent. A Host is the manager of a restaurant who sees that all
//is proceeded as he wishes.
public class HostRole extends Role implements Host{
	static final int NTABLES = 3;//a global for the number of tables.
	//Notice that we implement waitingCustomers using ArrayList, but type it
	//with List semantics.
	public List<Customer> waitingCustomers
	= Collections.synchronizedList(new ArrayList<Customer>());
	public List<Waiter> waiters
	= Collections.synchronizedList(new ArrayList<Waiter>());
	public Collection<Table> tables;
	//private boolean seatingCustomer;
	//note that tables is typed with Collection semantics.
	//Later we will see how it is implemented

	private String name;
	private Semaphore atTable = new Semaphore(0,true);

	public HostGui hostGui = null;

	public HostRole(PersonAgent person) {
		super(person);

		//this.name = person.getName();
		// make some tables
		tables = new ArrayList<Table>(NTABLES);
		for (int ix = 1; ix <= NTABLES; ix++) {
			tables.add(new Table(ix));//how you add to a collections
		}
	}
	
	public HostRole() {
		super();
		
		tables = new ArrayList<Table>(NTABLES);
		for (int ix = 1; ix <= NTABLES; ix++) {
			tables.add(new Table(ix));//how you add to a collections
		}
	}

	public String getName() {
		return myPerson.getName();
	}

	public List getWaitingCustomers() {
		return waitingCustomers;
	}

	public Collection getTables() {
		return tables;
	}
	
	public void addWaiter(Waiter waiter) {
		waiters.add(waiter);
	}
	
	
	// Messages

	public void msgIWantFood(Customer cust) {
		waitingCustomers.add(cust);
		cust.msgWaitHere(waitingCustomers.size() - 1);
		stateChanged();
	}

	public void msgLeavingTable(Customer cust) {
		for (Table table : tables) {
			if (table.getOccupant() == cust) {
				System.out.println(getName() + ": " + cust + " leaving " + table);
				table.setUnoccupied();
				stateChanged();
			}
		}
	}
	
	public void msgTableEmpty(int tablenum) {
		for (Table table : tables) {
			if (table.tableNumber == tablenum) {
				System.out.println(getName() + ": " + table + " is empty");
				table.setUnoccupied();
				stateChanged();
			}
		}
	}
	
	public void msgWantToGoOnBreak(Waiter w) {
		if(waiters.size() == 1) {
			w.msgGoOnBreak(false);
			System.out.println(getName() + ": You can't go on break. There's only 1 waiter.");
		}
		else {
			w.msgGoOnBreak(true);
			System.out.println(getName() + ": You can go on break.");
		}
	}
	
	public void msgAtTable() {//from animation
		//print("msgAtTable() called");
		atTable.release();// = true;
		stateChanged();
	}
	
	
	//Scheduler 
	
	public boolean pickAndExecuteAnAction() {
		for (Table table : tables) {
			if (!table.isOccupied()) {
				if (!waitingCustomers.isEmpty()) {
					if(getLeastBusyWaiter() > -1) {
							waiters.get(getLeastBusyWaiter()).msgPleaseSeatCustomer(waitingCustomers.get(0), table.tableNumber);//the action
							table.setOccupant(waitingCustomers.get(0));
							waitingCustomers.remove(0);
					}
					return true;//return true to the abstract agent to reinvoke the scheduler.
				}
			}
		}

		return false;
	}

	//utilities

	public void setGui(RoleGui g) {
		super.setGui(g);
		hostGui = (HostGui)g;
	}

	public HostGui getGui() {
		return hostGui;
	}
	
	private int getLeastBusyWaiter() {
		int leastValue = 100, leastValueIndex = -1;
		
		for(int a = 0; a < waiters.size(); a++) {
			if(waiters.get(a).isAvailable())
			{
				if(leastValue > waiters.get(a).getNumCustomers())
				{
					leastValue = waiters.get(a).getNumCustomers();
					leastValueIndex = a;
				}
			}
		}
		return leastValueIndex;
	}
	
	private class Table {
		Customer occupiedBy;
		int tableNumber;

		Table(int tableNumber) {
			this.tableNumber = tableNumber;
		}

		void setOccupant(Customer customer) {
			occupiedBy = customer;
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

