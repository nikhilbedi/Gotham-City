package restaurant2;

import agent.Agent;
import agent.Role;
import restaurant2.gui.HostGui;
import restaurant2.interfaces.Customer;
import restaurant2.interfaces.Host;
import restaurant2.interfaces.Waiter;

import java.util.*;
import java.util.concurrent.Semaphore;

/**
 * Restaurant Host Agent
 */
//We only have 2 types of agents in this prototype. A customer and an agent that
//does all the rest. Rather than calling the other agent a waiter, we called him
//the HostAgent. A Host is the manager of a restaurant who sees that all
//is proceeded as he wishes.
public class HostAgent extends Role implements Host{
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

	public HostAgent(String name) {
		super();

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

	public List getWaitingCustomers() {
		return waitingCustomers;
	}

	public Collection getTables() {
		return tables;
	}
	
	public void addWaiter(WaiterAgent waiter) {
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
				print(cust + " leaving " + table);
				table.setUnoccupied();
				stateChanged();
			}
		}
	}
	
	public void msgTableEmpty(int tablenum) {
		for (Table table : tables) {
			if (table.tableNumber == tablenum) {
				print(table + " is empty");
				table.setUnoccupied();
				stateChanged();
			}
		}
	}
	
	public void msgWantToGoOnBreak(Waiter w) {
		if(waiters.size() == 1) {
			w.msgGoOnBreak(false);
			print("You can't go on break. There's only 1 waiter.");
		}
		else {
			w.msgGoOnBreak(true);
			print("You can go on break.");
		}
	}
	
	public void msgAtTable() {//from animation
		//print("msgAtTable() called");
		atTable.release();// = true;
		stateChanged();
	}
	
	/**
	 * Scheduler.  Determine what action is called for, and do it.
	 */
	protected boolean pickAndExecuteAnAction() {
		/* Think of this next rule as:
            Does there exist a table and customer,
            so that table is unoccupied and customer is waiting.
            If so seat him at the table.
		 */
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
		//we have tried all our rules and found
		//nothing to do. So return false to main loop of abstract agent
		//and wait.
	}

	// Actions

	private void seatCustomer(CustomerAgent customer, Table table) {
		customer.msgSitAtTable(table.tableNumber);
		DoSeatCustomer(customer, table);
		try {
			atTable.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		table.setOccupant(customer);
		waitingCustomers.remove(customer);
		hostGui.DoLeaveCustomer();
	}

	// The animation DoXYZ() routines
	private void DoSeatCustomer(CustomerAgent customer, Table table) {
		//Notice how we print "customer" directly. It's toString method will do it.
		//Same with "table"
		print("Seating " + customer + " at " + table);
		hostGui.DoBringToTable(customer, table.tableNumber); 

	}

	//utilities

	public void setGui(HostGui gui) {
		hostGui = gui;
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

