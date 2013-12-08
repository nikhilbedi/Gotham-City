package simcity.bank;

import agent.Role;
import simcity.PersonAgent;
import simcity.bank.interfaces.BankCustomer;
import simcity.bank.interfaces.BankGreeter;
import simcity.bank.interfaces.BankTeller;

import java.util.*;

/**
 * Bank Teller Role
 * Programmer: Brice Roland
 */
public class BankTellerRole extends Role implements BankTeller{
	
	// Lists and state variables
	
	public List<MyCustomer> myCustomers
	= Collections.synchronizedList(new ArrayList<MyCustomer>());
	public BankGreeter greeter;
	private boolean isAvailable = false;
	public boolean handledTransaction = false;
	int tellerIndex;
	public BankDatabase bankDatabase;
	
	
	// Gui
	
	public BankTellerGui gui;
	
	public void setGui(BankTellerGui tellerGui) {
		gui = tellerGui;
		
	}
	
	
	// Constructor
	
	public BankTellerRole(PersonAgent person) {
		super(person);
	}
	
	
	//Functions
	
	public BankTellerRole() {
		super();
	}


	public PersonAgent getPersonAgent() { return super.getPersonAgent();}
	
	public void setBankDatabase(BankDatabase dB) {
		bankDatabase = dB;
	}
	
	public String getName() {
		return super.getName();
	}
	
	public void setGreeter(BankGreeter g) {
		greeter = g;
	}
	
	public void setIndex(int tIndex) { tellerIndex = tIndex;}
	
	public int getIndex() { return tellerIndex;}
	
	public boolean isAvailable() { return isAvailable; }
	
	@Override
	public void setAvailable(boolean b) {
		isAvailable = b;
	}
	
	public class MyCustomer { //MyCustomer Class to hold elements of transaction related to the customer
		public int accountNumber;
		BankCustomer c;
		public String transactionType;
		String landlordName;
		double transactionAmount;
		public customerState s;
		
		public MyCustomer(BankCustomer c2, String type, double tA) {
			c = c2;
			transactionType = type;
			transactionAmount = tA;
		}
	}
	
	public int find(BankCustomer c) { //find function to determine placement of current customer to deal with in MyCustomer
		for(int a = 0; a < myCustomers.size(); a++) {
			if(c.getName() == myCustomers.get(a).c.getName())
				return a;
		}
		return -1;
	}
	
	public int getNumCustomers() {
		return myCustomers.size();
	}
	
	public enum customerState {waiting, askedForTransaction, makingAnotherTransaction,
		doneAndLeaving;}
	
	
	// Messages
	
	@Override //Sets up connection
	public void msgNeedATransaction(BankCustomer cust, BankTransaction transaction) {
		System.out.println(getName() + ": Got needATransaction message from customer " + cust.getName());
		handledTransaction = false;
		if(find(cust) == -1) {
			MyCustomer c = new MyCustomer(cust, transaction.transactionType, transaction.transactionAmount);
			myCustomers.add(c);
			System.out.println(getName() + ": Added customer to customer list");
		}
		else {
			myCustomers.get(find(cust)).transactionType = transaction.transactionType;
			myCustomers.get(find(cust)).transactionAmount = transaction.transactionAmount;
		}
		myCustomers.get(find(cust)).s = customerState.askedForTransaction;
		if(transaction.transactionType.equals("payingRentBill"))
			myCustomers.get(find(cust)).landlordName = transaction.landlordName;
		else
			myCustomers.get(find(cust)).landlordName = "";
		
		stateChanged();
	}

	@Override
	public void msgDoneAndLeaving(BankCustomer bankCustomer) {
		System.out.println(getName() + ": Received message DoneAndLeaving.");
		myCustomers.get(find(bankCustomer)).s = customerState.doneAndLeaving;
		handledTransaction = false;
		stateChanged();
	}
	

	// Scheduler
	
	public boolean pickAndExecuteAnAction() {
		for(int a = 0; a < myCustomers.size(); a++) {
			if(myCustomers.get(a).s == customerState.doneAndLeaving) {
				handledTransaction = true;
				handleDoneState(myCustomers.get(a));
				return true;
			}
		}
		
		if(handledTransaction)
			return false;
		
		for(int a = 0; a < myCustomers.size(); a++) {
			if(myCustomers.get(a).transactionType.equalsIgnoreCase("deposit")) {
				handledTransaction = true;
				depositFunds(myCustomers.get(a));
				return true;
			}
		}
		
		for(int a = 0; a < myCustomers.size(); a++) {
			if(myCustomers.get(a).transactionType.equalsIgnoreCase("withdrawal")) {
				handledTransaction = true;
				withdrawFunds(myCustomers.get(a));
				return true;
			}
		}
		
		for(int a = 0; a < myCustomers.size(); a++) {
			if(myCustomers.get(a).transactionType.equalsIgnoreCase("openingAccount")) {
				handledTransaction = true;
				openAccount(myCustomers.get(a));
				return true;
			}
		}
		
		for(int a = 0; a < myCustomers.size(); a++) {
			if(myCustomers.get(a).transactionType.equalsIgnoreCase("closingAccount")) {
				handledTransaction = true;
				closeAccount(myCustomers.get(a));
				return true;
			}
		}
		
		for(int a = 0; a < myCustomers.size(); a++) {
			if(myCustomers.get(a).transactionType.equalsIgnoreCase("needALoan")) {
				handledTransaction = true;
				handleLoanCredentials(myCustomers.get(a));
				return true;
			}
		}
		
		for(int a = 0; a < myCustomers.size(); a++) {
			if(myCustomers.get(a).transactionType.equalsIgnoreCase("payingRentBill")) {
				handledTransaction = true;
				payBill(myCustomers.get(a));
				return true;
			}
		}
		
		for(int a = 0; a < myCustomers.size(); a++) {
			if(myCustomers.get(a).transactionType.equalsIgnoreCase("leaving")) {
				handledTransaction = true;
				handleDoneState(myCustomers.get(a));
				return true;
			}
		}

		return false;
	}

	
	// Actions
	
	public void depositFunds(MyCustomer c) {
		System.out.println(getName() + ": Depositing funds for customer " + c.c.getName());
		System.out.println(bankDatabase.accountNumbers.get(c.c.getName()));
		System.out.println(c.c.getName());
		System.out.println(bankDatabase.accounts.get(bankDatabase.accountNumbers.get(c.c.getName()).get(0)).accountHolderName + ": CUSTOMER NAME");
		BankAccount acc = bankDatabase.accounts.get(bankDatabase.accountNumbers.get(c.c.getName()).get(0));
		acc.depositMoney(c.transactionAmount);
		c.c.HereIsReceipt(new BankReceipt(acc.accountBalance, c.transactionAmount, c.transactionType));
	}
	
	public void withdrawFunds(MyCustomer c) {
		System.out.println(getName() + ": Withdrawing funds for customer " + c.c.getName());
		BankAccount acc = bankDatabase.accounts.get(bankDatabase.accountNumbers.get(c.c.getName()).get(0));
		acc.withdrawMoney(c.transactionAmount);
		c.c.HereIsReceipt(new BankReceipt(acc.accountBalance, c.transactionAmount, c.transactionType));
	}
	
	public void openAccount(MyCustomer c) {
		System.out.println(getName() + ": Opening account for customer " + c.c.getName());
		BankAccount acc = bankDatabase.addAccount(c.transactionAmount, c.c.getName());
		c.c.HereIsReceiptAndAccountInfo(new BankReceipt(acc.accountBalance, acc.accountNumber, c.transactionType), acc.accountNumber);
	}
	
	public void closeAccount(MyCustomer c) {
		BankAccount acc = bankDatabase.accounts.get(bankDatabase.accountNumbers.get(c.c.getName()).get(0));
		c.c.HereIsReceipt(new BankReceipt(acc.accountBalance, acc.accountNumber, c.transactionType));
		bankDatabase.removeAccount(c.c.getName(), bankDatabase.accountNumbers.get(c.c.getName()).get(0));
	}
	
	public void handleLoanCredentials(MyCustomer c) {
		double totalAccountBalance = bankDatabase.getTotalAccountBalance(c.c.getName());
		  if(totalAccountBalance > 5000) {
		        //check for enough money within bank  //database
		        bankDatabase.safeBalance -= c.transactionAmount;
		        c.c.HereIsLoan(new BankReceipt(c.transactionAmount, c.transactionAmount, c.transactionType), c.transactionAmount);
		        bankDatabase.loanHolders.put(c.c.getName(), c.transactionAmount);
		   }
		   else {
		         c.c.NotEligibleForLoan();
		    }
	}
	
	public void payBill(MyCustomer c) {
		System.out.println("Handling Bill for customer");
		BankAccount acc = bankDatabase.accounts.get(bankDatabase.accountNumbers.get(c.c.getName()).get(0));
		acc.withdrawMoney(c.transactionAmount);
		
		BankAccount landlordAcc = bankDatabase.accounts.get(bankDatabase.accountNumbers.get(c.landlordName).get(0));
		landlordAcc.depositMoney(c.transactionAmount);
		
		c.c.HereIsReceipt(new BankReceipt(acc.accountBalance, acc.accountNumber, c.transactionType));
	}
	
	public void handleDoneState(MyCustomer c) {
		System.out.println(getName() + ": Done with customer. Ready for next customer.");
		myCustomers.remove(c);
		greeter.msgReadyForCustomer(this);
	}
}

