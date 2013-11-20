package bank;

import agent.Agent;
import agent.Role;
import bank.interfaces.BankCustomer;
import bank.interfaces.BankGreeter;
import bank.interfaces.BankTeller;
import simcity.PersonAgent;
import simcity.interfaces.Person;

import java.util.*;
import java.util.concurrent.Semaphore;

/**
 * Bank Teller Role
 * Programmer: Brice Roland
 */
public class BankTellerRole extends Role implements BankTeller{
	
	public List<MyCustomer> myCustomers
	= Collections.synchronizedList(new ArrayList<MyCustomer>());
	BankGreeter greeter;
	//public Collection<Table> tables;
	private boolean isAvailable = true;
	//note that tables is typed with Collection semantics.
	//Later we will see how it is implemented

	private String name;
	/*private Semaphore atTable = new Semaphore(0,true);
	private Semaphore atCook = new Semaphore(0,true);
	private Semaphore atCashier = new Semaphore(0,true);
	private Timer timer = new Timer();
	private Semaphore waitingForOrder = new Semaphore(0, true);
	private int currentOrderTableNumber = -1, currentCheckTableNumber = -1;*/
	
	BankDatabase bankDatabase;
	
	public BankTellerRole(PersonAgent person) {
		super(person);
		// TODO Auto-generated constructor stub
		
		//person.name = name;
	}
	
	public PersonAgent getPersonAgent() { return super.getPersonAgent();}
	
	public void setBankDatabase(BankDatabase dB) {
		bankDatabase = dB;
	}
	
	public String getName() {
		return name;
	}

	/*public Collection getTables() {
		return tables;
	}*/
	
	public void setGreeter(BankGreeter g) {
		greeter = g;
	}
	
	public boolean isAvailable() { return isAvailable; }
	
	@Override
	public void setAvailable(boolean b) {
		isAvailable = b;
	}
	
	// Messages
	
	

	@Override
	public void msgNeedATransaction(BankCustomer cust, String type, double amount) {
		MyCustomer c = new MyCustomer(cust, type, amount);
		myCustomers.add(c);
		stateChanged();
	}

	@Override
	public void msgDoneAndLeaving(BankCustomer bankCustomer) {
		// TODO Auto-generated method stub
		myCustomers.get(find(bankCustomer.getName()));
		stateChanged();
	}
	
	/**
	 * Scheduler.  Determine what action is called for, and do it.
	 */
	public boolean pickAndExecuteAnAction() {
		/* Think of this next rule as:
            Does there exist a table and customer,
            so that table is unoccupied and customer is waiting.
            If so seat him at the table
		 */
		
		for(int a = 0; a < myCustomers.size(); a++) {
			if(myCustomers.get(a).transactionType.equalsIgnoreCase("deposit")) {
				depositFunds(myCustomers.get(a));
			}
		}
		
		for(int a = 0; a < myCustomers.size(); a++) {
			if(myCustomers.get(a).transactionType.equalsIgnoreCase("withdrawal")) {
				withdrawFunds(myCustomers.get(a));
			}
		}
		
		for(int a = 0; a < myCustomers.size(); a++) {
			if(myCustomers.get(a).transactionType.equalsIgnoreCase("openingAccount")) {
				openAccount(myCustomers.get(a));
			}
		}
		
		for(int a = 0; a < myCustomers.size(); a++) {
			if(myCustomers.get(a).transactionType.equalsIgnoreCase("closingAccount")) {
				closeAccount(myCustomers.get(a));
			}
		}
		
		for(int a = 0; a < myCustomers.size(); a++) {
			if(myCustomers.get(a).transactionType.equalsIgnoreCase("needALoan")) {
				handleLoanCredentials(myCustomers.get(a));
			}
		}
		
		for(int a = 0; a < myCustomers.size(); a++) {
			if(myCustomers.get(a).transactionType.equalsIgnoreCase("payingBill")) {
				payBill(myCustomers.get(a));
			}
		}
		
		
		for(int a = 0; a < myCustomers.size(); a++) {
			if(myCustomers.get(a).transactionType.equalsIgnoreCase("leaving")) {
				handleDoneState(myCustomers.get(a));
			}
		}

		return false;
		//we have tried all our rules and found
		//nothing to do. So return false to main loop of abstract agent
		//and wait.
	}

	// Actions
	
	public void depositFunds(MyCustomer c) {
		BankAccount acc = bankDatabase.accounts.get(c.c.name);
		acc.depositMoney(c.transactionAmount);
		c.c.HereIsReceipt(new BankReceipt(acc.accountBalance, c.transactionAmount));
	}
	
	public void withdrawFunds(MyCustomer c) {
		BankAccount acc = bankDatabase.accounts.get(c.c.name);
		acc.withdrawMoney(c.transactionAmount);
		c.c.HereIsReceipt(new BankReceipt(acc.accountBalance, c.transactionAmount));
	}
	
	public void openAccount(MyCustomer c) {
		BankAccount acc = bankDatabase.addAccount(c.name, c.transactionAmount);
		c.c.HereIsReceiptAndAccountInfo(new BankReceipt(acc.accountBalance, acc.accountNumber), acc.accountNumber);
	}
	
	public void closeAccount(MyCustomer c) {
		BankAccount acc = bankDatabase.removeAccount(c.name, c.accountNumber);
		c.c.HereIsReceiptAndAccountInfo(new BankReceipt(acc.accountBalance, acc.accountNumber), acc.accountNumber);
	}
	
	public void handleLoanCredentials(MyCustomer c) {
		  if(c.c.getPersonAgent().stateOfWealth == rich ||  c.c.getPersonAgent().stateOfWealth == adequate) {
		        //check for enough money within bank  //database
		        bankDatabase.safeBalance -= c.transactionAmount;
		         c.c.HereIsLoan(new BankReceipt(c.transactionAmount, c.transactionAmount), c.transactionAmount);
		       bankDatabase.loanHolders.add(c, c.transactionAmount);
		       }
		   else {
		         c.c.NotEligibleForLoan();
		    }
	}
	
	public void payBill(MyCustomer c) {
		
	}
	
	public void handleDoneState(MyCustomer c) {
		myCustomers.remove(c);
		greeter.msgReadyForCustomer(this);
	}
	
	//utilities

	public void setGui(BankTellerGui gui) {
		bankTellerGui = gui;
	}

	public BankTellerGui getGui() {
		return bankTellerGui;
	}
	
	public class MyCustomer {
		BankCustomer c;
		String transactionType;
		double transactionAmount;
		customerState s;
		
		public MyCustomer(BankCustomer c2, String type, double tA) {
			c = c2;
			transactionType = type;
			transactionAmount = tA;
		}
	}
	
	public int find(BankCustomer c) {
		for(int a = 0; a < myCustomers.size(); a++) {
			if(c.getName() == myCustomers.get(a).c.getName())
				return a;
		}
		return -1;
	}
	
	/*public int find(int tableNumber) {
		for(int a = 0; a < myCustomers.size(); a++) {
			if(tableNumber == myCustomers.get(a).table)
				return a;
		}
		return -1;
	}*/
	
	public int getNumCustomers() {
		return myCustomers.size();
	}
	
	public enum customerState {waiting, askedForTransaction, makingAnotherTransaction,
		doneAndLeaving;}

	
	
}

