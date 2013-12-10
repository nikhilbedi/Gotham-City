package simcity.restaurants.restaurant4;

import Gui.RoleGui;
import agent.Agent;
import agent.Role;


import java.util.*;

import simcity.PersonAgent;
import simcity.restaurants.restaurant4.Restaurant4Gui.Restaurant4HostGui;
import simcity.restaurants.restaurant4.interfaces.Restaurant4Customer;
//import java.util.concurrent.Semaphore;
import simcity.restaurants.restaurant4.interfaces.Restaurant4Host;
import simcity.restaurants.restaurant4.interfaces.Restaurant4Waiter;

/**
 * Restaurant Host Agent
 */
//We only have 2 types of agents in this prototype. A customer and an agent that
//does all the rest. Rather than calling the other agent a waiter, we called him
//the HostAgent. A Host is the manager of a restaurant who sees that all
//is proceeded as he wishes.
public class Restaurant4HostRole extends Role implements Restaurant4Host{
	static final int NTABLES = 4;//a global for the number of tables.
	//Notice that we implement waitingCustomers using ArrayList, but type it
	//with List semantics.
	public List<Restaurant4Customer> waitingCustomers = new ArrayList<Restaurant4Customer>();
	public Collection<Table> tables;
	//note that tables is typed with Collection semantics.
	//Later we will see how it is implemented
	private String name;
//	private Semaphore atTable = new Semaphore(0,true);
	public List<Restaurant4Waiter> availableWaiters = new ArrayList<Restaurant4Waiter>();
	private Restaurant4HostGui gui;
	public Restaurant4HostRole(PersonAgent p) {
		super(p);
		// make some tables
		tables = new ArrayList<Table>(NTABLES);
		for (int ix = 1; ix <= NTABLES; ix++) {
			tables.add(new Table(ix));//how you add to a collection 
		}
	}
	public Restaurant4HostRole() {
		super();
		// make some tables
		tables = new ArrayList<Table>(NTABLES);
		for (int ix = 1; ix <= NTABLES; ix++) {
			tables.add(new Table(ix));//how you add to a collection 
		}
	}
	
	public void setGui(RoleGui g){
		super.setGui(g);
		gui = (Restaurant4HostGui)g;
	}
	
	public Restaurant4HostGui getGui() {
		return gui;
	}
	
	public String getMaitreDName() {
		return name;
	}

	public String getName() {
		return name;
	}


	public boolean isFull(){
		for (Table table : tables) {
			if (!table.isOccupied()){
				return false;
			}
		}
		return true;
	}
	
	public List<Restaurant4Customer> getWaitingCustomers() {
		return waitingCustomers;
	}

	public Collection<Table>getTables() {
		return tables;
	}
	// Messages

	public void msgIWantFood(Restaurant4CustomerRole cust) {
		waitingCustomers.add(cust);
		stateChanged();
	}

	public void msgLeavingTable(Restaurant4Customer cust) {
		for (Table table : tables) {
			if (table.getOccupant() == cust) {
				System.out.println(cust + " leaving " + table);
				table.setUnoccupied();
				stateChanged();
			}
		}
	}
	public void BreakIsOver(Restaurant4Waiter w){
		availableWaiters.add(w);
		stateChanged();
	}
	public void setWaiter(Restaurant4Waiter waiter){
		availableWaiters.add(waiter);
		System.out.println(availableWaiters.size());
	//	stateChanged();
	}
	
	public void wantABreak(Restaurant4Waiter w){
		if (availableWaiters.size()!=1){
			for(Restaurant4Waiter waiter: availableWaiters){
				if (waiter==w){
					myPerson.Do("You may go after you finish serving customers");
					waiter.youMayGoToABreak();
					availableWaiters.remove(waiter);
				}
			}
		}
		else {
			myPerson.Do("You cannot go to a break");
			return;}
	}
	
	/**
	 * Scheduler.  Determine what action is called for, and do it.
	 */
	public boolean pickAndExecuteAnAction() {
		/* Think of this next rule as:
            Does there exist a table and customer,
            so that table is unoccupied and customer is waiting.
            If so seat him at the table.
		 */
		if(theManLeavingMe != null && waitingCustomers.isEmpty()) {
			leaveWork();
			return true;
		}
		
		for (Table table : tables) {
			if (!table.isOccupied()) {
					if (!availableWaiters.isEmpty()){
					if (availableWaiters.size()==1){
						if (!waitingCustomers.isEmpty()){
							availableWaiters.get(0).PleaseSeatCustomer(waitingCustomers.get(0), table.getTable());
							table.setOccupant(waitingCustomers.get(0));
							waitingCustomers.remove(waitingCustomers.get(0));
							return true;
							}
						}
						else {
							while (!waitingCustomers.isEmpty()){
								int min = 0;
								for (int i=0; i<availableWaiters.size()-1; i++){
									if (availableWaiters.get(i).getSize() > availableWaiters.get(i+1).getSize()){
										min = i+1;
									}
									
								}
								availableWaiters.get(min).PleaseSeatCustomer(waitingCustomers.get(0), table.getTable());
								table.setOccupant(waitingCustomers.get(0));
								waitingCustomers.remove(waitingCustomers.get(0));
								return true;
								
								}
							}
		}
		}	
		}
		if (isFull() && waitingCustomers.size()>0){
			tellNoPlace();
		}
		return false;
		
		//we have tried all our rules and found
		//nothing to do. So return false to main loop of abstract agent
		//and wait.
	}

	// Actions
	public void tellNoPlace(){
		myPerson.Do("No available tables, you can leave if you want");
		for (Restaurant4Customer customer: waitingCustomers){
			customer.leaveIfYouWant();
		}
		
		}
	
	public void leaving(Restaurant4CustomerRole c){
		waitingCustomers.remove(c);
	}
	//utilities
	private class Table {
		Restaurant4Customer occupiedBy;
		int tableNumber;
	
		Table(int tableNumber) {
			this.tableNumber = tableNumber;
		}
		 
		int getTable(){
			return tableNumber;
		}
		
		void setOccupant(Restaurant4Customer cust) {
			occupiedBy = cust;
		}

		void setUnoccupied() {
			occupiedBy = null;
		}

		Restaurant4Customer getOccupant() {
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

