package bank;

import agent.Agent;
import agent.Role;
import bank.interfaces.BankCustomer;
import bank.interfaces.BankGreeter;
import bank.interfaces.BankTeller;
import simcity.PersonAgent;

import java.util.*;
import java.util.concurrent.Semaphore;

/**
 * Bank Greeter Role
 * Programmer: Brice Roland
 */
//We only have 2 types of agents in this prototype. A customer and an agent that
//does all the rest. Rather than calling the other agent a waiter, we called him
//the HostAgent. A Host is the manager of a restaurant who sees that all
//is proceeded as he wishes.
public class BankGreeterRole extends Role implements BankGreeter{
	public List<MyCustomer> waitingCustomers
	= Collections.synchronizedList(new ArrayList<MyCustomer>());
	public List<BankTeller> tellers
	= Collections.synchronizedList(new ArrayList<BankTeller>());
	//private boolean seatingCustomer;
	//note that tables is typed with Collection semantics.
	//Later we will see how it is implemented

	private String name;
	
	public BankGreeterRole(PersonAgent person) {
		super(person);
		// TODO Auto-generated constructor stub
	}

	public String getName() {
		return name;
	}

	public List getWaitingCustomers() {
		return waitingCustomers;
	}
	
	public void addTeller(BankTeller teller) {
		tellers.add(teller);
		teller.setAvailable(true);
	}
	
	class MyCustomer {
		BankCustomer customer;
		customerState s;
		
		public MyCustomer(BankCustomer c) {
			customer = c;
			s = customerState.waiting;
		}
	}
	
	public enum customerState {waiting, inLine}; 
	// Messages

	public void msgNeedATeller(BankCustomer cust) {
		waitingCustomers.add(new MyCustomer(cust));
		cust.msgWaitHere(waitingCustomers.size() - 1);
		stateChanged();
	}
	
	public void msgReadyForCustomer(BankTeller teller) {
		teller.setAvailable(true);
		stateChanged();
	}
	
	/**
	 * Scheduler.  Determine what action is called for, and do it.
	 */
	//@Override
	public boolean pickAndExecuteAnAction() {
		/* Think of this next rule as:
            Does there exist a table and customer,
            so that table is unoccupied and customer is waiting.
            If so seat him at the table.
		 */
		for (BankTeller teller: tellers) {
			if (teller.isAvailable()) {
				if (!waitingCustomers.isEmpty()) 
					sendToTeller(waitingCustomers.get(0), teller);
					return true;//return true to the abstract agent to reinvoke the scheduler.
			}
		}
		
		for(int x = 0; x < waitingCustomers.size(); x++) {
			if(waitingCustomers.get(x).s == customerState.waiting)
				SendToLinePosition(waitingCustomers.get(x), x);
		}
		
		return false;
		//we have tried all our rules and found
		//nothing to do. So return false to main loop of abstract agent
		//and wait.
	}

	// Actions

	private void SendToLinePosition(MyCustomer cust, int x) {
		cust.customer.msgWaitHere(x); //Change to accommodate with gui
		//linePosition += //GUI IMPLEMENT
		cust.s = customerState.inLine;
	}

	private void sendToTeller(MyCustomer cust, BankTeller teller) {
		cust.customer.msgGoToTeller(teller);
		teller.setAvailable(false);
		waitingCustomers.remove(cust);
	}	
}

