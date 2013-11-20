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
<<<<<<< HEAD
=======
//We only have 2 types of agents in this prototype. A customer and an agent that
//does all the rest. Rather than calling the other agent a waiter, we called him
//the HostAgent. A Host is the manager of a restaurant who sees that all
//is proceeded as he wishes.
>>>>>>> 625dd9d9e00dcb14eaa686b434feee1c9294a813
public class BankGreeterRole extends Role implements BankGreeter{
	public List<MyCustomer> waitingCustomers
	= Collections.synchronizedList(new ArrayList<MyCustomer>());
	public List<BankTeller> tellers
	= Collections.synchronizedList(new ArrayList<BankTeller>());
<<<<<<< HEAD
	public BankGreeterGui gui;
	
	public BankGreeterRole(PersonAgent person) {
		super(person);
	}
	
	/*public String getName() {
		return super.getName();
	}*/
=======
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
>>>>>>> 625dd9d9e00dcb14eaa686b434feee1c9294a813

	public List getWaitingCustomers() {
		return waitingCustomers;
	}
	
	public void addTeller(BankTeller teller) {
		tellers.add(teller);
<<<<<<< HEAD
		teller.setIndex(tellers.size()-1);
		teller.setAvailable(true);
	}
	
	//Class to hold BankCustomer and their information
=======
		teller.setAvailable(true);
	}
	
>>>>>>> 625dd9d9e00dcb14eaa686b434feee1c9294a813
	class MyCustomer {
		BankCustomer customer;
		customerState s;
		
		public MyCustomer(BankCustomer c) {
			customer = c;
			s = customerState.waiting;
		}
	}
	
	public enum customerState {waiting, inLine}; 
<<<<<<< HEAD
	
	// Messages

	public void msgNeedATeller(BankCustomer cust) { //Message from BankCustomer for initial contact/entering the bank
		System.out.println("Greeter: Asked for a teller by customer " + cust.getName());
		waitingCustomers.add(new MyCustomer(cust));
		//cust.msgWaitHere(waitingCustomers.size() - 1);
		stateChanged();
	}
	
	public void msgReadyForCustomer(BankTeller teller) { //Message from BankTeller; send a customer to that teller
=======
	// Messages

	public void msgNeedATeller(BankCustomer cust) {
		waitingCustomers.add(new MyCustomer(cust));
		cust.msgWaitHere(waitingCustomers.size() - 1);
		stateChanged();
	}
	
	public void msgReadyForCustomer(BankTeller teller) {
>>>>>>> 625dd9d9e00dcb14eaa686b434feee1c9294a813
		teller.setAvailable(true);
		stateChanged();
	}
	
	/**
	 * Scheduler.  Determine what action is called for, and do it.
	 */
<<<<<<< HEAD
	public boolean pickAndExecuteAnAction() {
		for (BankTeller teller: tellers) { //Determines if a teller is available and if a customer can be sent to them
			if (teller.isAvailable()) {
				if (!waitingCustomers.isEmpty()) 
					sendToTeller(waitingCustomers.get(0), teller);
					return true;
			}
		}
		
		for(int x = 0; x < waitingCustomers.size(); x++) { //Sends a customer to a position in line if no teller is available
=======
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
>>>>>>> 625dd9d9e00dcb14eaa686b434feee1c9294a813
			if(waitingCustomers.get(x).s == customerState.waiting)
				SendToLinePosition(waitingCustomers.get(x), x);
		}
		
		return false;
<<<<<<< HEAD
=======
		//we have tried all our rules and found
		//nothing to do. So return false to main loop of abstract agent
		//and wait.
>>>>>>> 625dd9d9e00dcb14eaa686b434feee1c9294a813
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
<<<<<<< HEAD
	}

	public void setGui(BankGreeterGui greeterGui) {
		gui = greeterGui;
		
=======
>>>>>>> 625dd9d9e00dcb14eaa686b434feee1c9294a813
	}	
}

