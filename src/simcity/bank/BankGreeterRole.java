package simcity.bank;

import agent.Role;
import simcity.PersonAgent;
import simcity.bank.interfaces.BankCustomer;
import simcity.bank.interfaces.BankGreeter;
import simcity.bank.interfaces.BankTeller;

import java.util.*;

/**
 * Bank Greeter Role
 * Programmer: Brice Roland
 */
public class BankGreeterRole extends Role implements BankGreeter{
	
	//Lists
	
	public List<MyCustomer> waitingCustomers
	= Collections.synchronizedList(new ArrayList<MyCustomer>());
	public List<BankTeller> tellers
	= Collections.synchronizedList(new ArrayList<BankTeller>());
	public BankGreeterGui gui;
	
	
	// Constructor
	
	public BankGreeterRole(PersonAgent person) {
		super(person);
	}
	
	
	// Functions
	
	public BankGreeterRole() {
		super();
	}


	public void addTeller(BankTeller teller) {
		tellers.add(teller);
		teller.setIndex(tellers.size()-1);
		teller.setAvailable(true);
	}
	
	public void setGui(BankGreeterGui greeterGui) {
		gui = greeterGui;
		
	}	
	
	//Class to hold BankCustomer and their information
	
	public class MyCustomer {
		BankCustomer customer;
		public customerState s;
		
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
	
	
	// Scheduler
	
	public boolean pickAndExecuteAnAction() {		
		for (BankTeller teller: tellers) { //Determines if a teller is available and if a customer can be sent to them
			if (teller.isAvailable()) {
				if (!waitingCustomers.isEmpty())  {
					sendToTeller(waitingCustomers.get(0), teller);
					return true;
				}
			}
		}
		
		for(int x = 0; x < waitingCustomers.size(); x++) { //Sends a customer to a position in line if no teller is available
			if(waitingCustomers.get(x).s == customerState.waiting) {
				SendToLinePosition(waitingCustomers.get(x), x);
				return true;
			}
		}
		
		return false;
	}

	
	// Actions

	private void SendToLinePosition(MyCustomer cust, int x) {
		System.out.println("Sending Customer into line");
		cust.customer.msgWaitHere(x); //Change to accommodate with gui
		//linePosition += //GUI IMPLEMENT
		cust.s = customerState.inLine;
	}

	private void sendToTeller(MyCustomer cust, BankTeller teller) {
		System.out.println("Sending Customer to Teller");
		cust.customer.msgGoToTeller(teller);
		teller.setAvailable(false);
		waitingCustomers.remove(cust);
	}
}
