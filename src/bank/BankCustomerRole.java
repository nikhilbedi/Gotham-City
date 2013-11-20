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
		setBankTeller(t);
	}
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}
	
	public int getWaitingNumber() {
		return waitingNumber;
	}
	
	//sets gui and initial positioning (Temp)
	public void setGui(bankCustomerGui gui, int x, int y) {
		bankCustomerGui = gui;
		bankCustomerGui.setX(x);
		bankCustomerGui.setY(y);
	}
	
	// Messages

	public void msgWaitHere(int i) {
		//customerGui.goToWaitingPosition(i); //gui call to go to a position
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
		
		System.out.println("CALLING SCHEDULER");
		
		if (state == CustomerState.waiting ){
			talkToGreeter();
			return true;
		}
		
		if (state == CustomerState.inLine){
			//state = AgentState.BeingSeated;
			System.out.println("Going to Teller");
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
}

