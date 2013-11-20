package bank;

import agent.Agent;
import agent.Role;
import bank.BankTellerRole.MyCustomer;
import bank.interfaces.BankCustomer;
import bank.interfaces.BankGreeter;
import bank.interfaces.BankTeller;
import bank.BankReceipt;
import simcity.PersonAgent;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Random;

/**
 * Bank Customer Role
 * Programmer: Brice Roland
 */
public class BankCustomerRole extends Role implements BankCustomer{
	public BankCustomerRole(PersonAgent person) {
		super(person);
		// TODO Auto-generated constructor stub
	}

<<<<<<< HEAD
	public BankTeller getBankTeller() {
		return teller;
	}

	public void setBankTeller(BankTeller teller) {
		this.teller = teller;
	}

	private String name;
	private int  waitingNumber, tellerIndex;
	Timer timer = new Timer();
	//private CustomerGui customerGui;
	double cash, transactionAmount;
	String transactionType = "openingAccount";
	List<String> transactionList = new ArrayList<String>();
	
	public bankCustomerGui bankCustomerGui;
	
=======
	private String name, choice;
	private int hungerLevel = 5;        // determines length of meal
	private int seatNumber, waitingNumber;        // determines length of meal
	Timer timer = new Timer();
	//private CustomerGui customerGui;
	double cash, transactionAmount;
	String transactionType;
	boolean gotCheck = false, payForMeal = true;
	List<String> transactionList = new ArrayList<String>();
	
>>>>>>> 625dd9d9e00dcb14eaa686b434feee1c9294a813
	// agent correspondents
	private BankGreeter greeter;
	private BankTeller teller;
	
	//    private boolean isHungry = false; //hack for gui
	public enum CustomerState
	{waiting, inLine, goingToTeller, atTeller, receivedReceipt, done};
	private CustomerState state = CustomerState.waiting;//The start state
<<<<<<< HEAD
	
=======

>>>>>>> 625dd9d9e00dcb14eaa686b434feee1c9294a813
	/**
	 * hack to establish connection to Greeter Role.
	 */
	
	public void setGreeter(BankGreeter greeter) {
		this.greeter = greeter;
	}
	
	public void setTeller(BankTeller t) {
<<<<<<< HEAD
		setBankTeller(t);
=======
		teller = t;
>>>>>>> 625dd9d9e00dcb14eaa686b434feee1c9294a813
	}
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
<<<<<<< HEAD
		return name;
=======
		return null;
>>>>>>> 625dd9d9e00dcb14eaa686b434feee1c9294a813
	}
	
	public int getWaitingNumber() {
		return waitingNumber;
	}
	
<<<<<<< HEAD
	//sets gui and initial positioning (Temp)
	public void setGui(bankCustomerGui gui, int x, int y) {
		bankCustomerGui = gui;
		bankCustomerGui.setX(x);
		bankCustomerGui.setY(y);
	}
	
=======
>>>>>>> 625dd9d9e00dcb14eaa686b434feee1c9294a813
	// Messages

	public void msgWaitHere(int i) {
		//customerGui.goToWaitingPosition(i); //gui call to go to a position
<<<<<<< HEAD
		System.out.println(name + ": Told to wait in line");
		System.out.println("This is running twice.");
		waitingNumber = i;
		state = CustomerState.inLine;
		DoGoToLine();
	}

	public void msgGoToTeller(BankTeller teller) {
		this.setBankTeller(teller);
		tellerIndex = teller.getIndex();
		System.out.println(name + ": Told to go to teller");
		state = CustomerState.inLine;
=======
		waitingNumber = i;
	}
	
	public void msgGoToTeller(BankTeller teller) {
		this.teller = teller;
>>>>>>> 625dd9d9e00dcb14eaa686b434feee1c9294a813
		stateChanged();
	}
	
	@Override
	public void NotEligibleForLoan() {
		// Decide if making another transaction or leaving
		
	}
	
	@Override
	public void HereIsReceipt(BankReceipt receipt) {
		getPersonAgent().addMoney((float)receipt.transactionAmount);
	}

	public void HereIsReceiptAndAccountInfo(BankReceipt bankReceipt, int accountNumber) {
		getPersonAgent().addMoney((float)bankReceipt.transactionAmount);
		//add Account number info to PersonAgent
	}

	public void HereIsLoan(BankReceipt bankReceipt, double transactionAmount) {
		getPersonAgent().addMoney((float)bankReceipt.transactionAmount);
	}
	
	/**
	 * Scheduler.  Determine what action is called for, and do it.
	 */
	public boolean pickAndExecuteAnAction() {
		//	CustomerAgent is a finite state machine
		
<<<<<<< HEAD
		System.out.println("CALLING SCHEDULER");
		
=======
>>>>>>> 625dd9d9e00dcb14eaa686b434feee1c9294a813
		if (state == CustomerState.waiting ){
			talkToGreeter();
			return true;
		}
		
		if (state == CustomerState.inLine){
			//state = AgentState.BeingSeated;
<<<<<<< HEAD
			System.out.println("Going to Teller");
=======
>>>>>>> 625dd9d9e00dcb14eaa686b434feee1c9294a813
			DoGoToTeller();
			return true;
		}
		
		if (state == CustomerState.atTeller){
			makeATransaction();
			return true;
		}
		
		if (state == CustomerState.receivedReceipt){
			//add if statement for making another transaction or not
			//makeAnotherTransaction();
			DoLeaveBank();
			return true;
		}
<<<<<<< HEAD
		
=======
>>>>>>> 625dd9d9e00dcb14eaa686b434feee1c9294a813
		return false;
	}

	// Actions
	
	private void talkToGreeter() {
<<<<<<< HEAD
		greeter.msgNeedATeller(this);
	}
	
	private void DoGoToLine() {
		bankCustomerGui.GetInLine(waitingNumber);
	}
	
	private void DoGoToTeller() {
		bankCustomerGui.GoToTeller(tellerIndex);
		getBankTeller().msgNeedATransaction(this, transactionType, transactionAmount);
	}

	private void makeATransaction() {
		getBankTeller().msgNeedATransaction(this, transactionType, transactionAmount);
	}

	private void DoLeaveBank() {
		getBankTeller().msgDoneAndLeaving(this);
		bankCustomerGui.LeaveBank();
	}	
=======
		// TODO Auto-generated method stub
		greeter.msgNeedATeller(this);
	}
	
	private void DoGoToTeller() {
		// TODO Auto-generated method stub
		//Gui.GoToTeller();
		teller.msgNeedATransaction(this, transactionType, transactionAmount);
	}

	private void makeATransaction() {
		// TODO Auto-generated method stub
		teller.msgNeedATransaction(this, transactionType, transactionAmount);
	}

	private void DoLeaveBank() {
		// TODO Auto-generated method stub
		teller.msgDoneAndLeaving(this);
	}

	/*public String toString() {
		return "customer " + getName();
	}*/

	/*public void setGui(BankCustomerGui g) {
	bankCustomerGui = g;
}

public BankCustomerGui getGui() {
	return bankCustomerGui;
}*/

	

	
	

	
>>>>>>> 625dd9d9e00dcb14eaa686b434feee1c9294a813
}

