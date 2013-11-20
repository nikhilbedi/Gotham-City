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

	private String name, choice;
	private int hungerLevel = 5;        // determines length of meal
	private int seatNumber, waitingNumber;        // determines length of meal
	Timer timer = new Timer();
	//private CustomerGui customerGui;
	double cash, transactionAmount;
	String transactionType;
	boolean gotCheck = false, payForMeal = true;
	List<String> transactionList = new ArrayList<String>();
	
	// agent correspondents
	private BankGreeter greeter;
	private BankTeller teller;
	
	//    private boolean isHungry = false; //hack for gui
	public enum CustomerState
	{waiting, inLine, goingToTeller, atTeller, receivedReceipt, done};
	private CustomerState state = CustomerState.waiting;//The start state

	/**
	 * hack to establish connection to Greeter Role.
	 */
	
	public void setGreeter(BankGreeter greeter) {
		this.greeter = greeter;
	}
	
	public void setTeller(BankTeller t) {
		teller = t;
	}
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public int getWaitingNumber() {
		return waitingNumber;
	}
	
	// Messages

	public void msgWaitHere(int i) {
		//customerGui.goToWaitingPosition(i); //gui call to go to a position
		waitingNumber = i;
	}
	
	public void msgGoToTeller(BankTeller teller) {
		this.teller = teller;
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
		
		if (state == CustomerState.waiting ){
			talkToGreeter();
			return true;
		}
		
		if (state == CustomerState.inLine){
			//state = AgentState.BeingSeated;
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
		return false;
	}

	// Actions
	
	private void talkToGreeter() {
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

	

	
	

	
}

