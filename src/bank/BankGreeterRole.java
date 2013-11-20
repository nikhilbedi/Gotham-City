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
public class BankGreeterRole extends Role implements BankGreeter{
	public List<MyCustomer> waitingCustomers
	= Collections.synchronizedList(new ArrayList<MyCustomer>());
	public List<BankTeller> tellers
	= Collections.synchronizedList(new ArrayList<BankTeller>());
	public BankGreeterGui gui;
	
	public BankGreeterRole(PersonAgent person) {
		super(person);
	}
	
	/*public String getName() {
		return super.getName();
	}*/

	public List getWaitingCustomers() {
		return waitingCustomers;
	}
	
	public void addTeller(BankTeller teller) {
		tellers.add(teller);
		teller.setIndex(tellers.size()-1);
		teller.setAvailable(true);
	}
	
	//Class to hold BankCustomer and their information
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

	public void msgNeedATeller(BankCustomer cust) { //Message from BankCustomer for initial contact/entering the bank
		System.out.println("Greeter: Asked for a teller by customer " + cust.getName());
		waitingCustomers.add(new MyCustomer(cust));
		//cust.msgWaitHere(waitingCustomers.size() - 1);
		stateChanged();
	}
	
	public void msgReadyForCustomer(BankTeller teller) { //Message from BankTeller; send a customer to that teller
		teller.setAvailable(true);
		stateChanged();
	}
	
	/**
	 * Scheduler.  Determine what action is called for, and do it.
	 */
	public boolean pickAndExecuteAnAction() {
		for (BankTeller teller: tellers) { //Determines if a teller is available and if a customer can be sent to them
			if (teller.isAvailable()) {
				if (!waitingCustomers.isEmpty()) 
					sendToTeller(waitingCustomers.get(0), teller);
					return true;
			}
		}
		
		for(int x = 0; x < waitingCustomers.size(); x++) { //Sends a customer to a position in line if no teller is available
			if(waitingCustomers.get(x).s == customerState.waiting)
				SendToLinePosition(waitingCustomers.get(x), x);
		}
		
		return false;
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

	public void setGui(BankGreeterGui greeterGui) {
		gui = greeterGui;
		
	}	
}

