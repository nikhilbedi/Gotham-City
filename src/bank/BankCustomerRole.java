package bank;

import agent.Role;
import bank.interfaces.BankCustomer;
import bank.interfaces.BankGreeter;
import bank.interfaces.BankTeller;
import bank.BankReceipt;
import simcity.PersonAgent;

import java.util.List;
import java.util.ArrayList;
import java.util.Timer;

/**
 * Bank Customer Role
 * Programmer: Brice Roland
 */
public class BankCustomerRole extends Role implements BankCustomer{
	
	//constructor call to Role constructor
	public BankCustomerRole(PersonAgent person) {
		super(person);
	}
	
	
	//State Variables
	
	private int  waitingNumber, tellerIndex;
	Timer timer = new Timer();
	double cash, transactionAmount;
	String transactionType = "openingAccount";  //This must be changed to interact with the Person's intentions at the bank
	List<String> transactionList = new ArrayList<String>();
	
	
	//Gui
	
	public bankCustomerGui bankCustomerGui;
	
	//sets gui and initial positioning (Temp)
	public void setGui(bankCustomerGui gui, int x, int y) {
			bankCustomerGui = gui;
			bankCustomerGui.setX(x);
			bankCustomerGui.setY(y);
		}
	
	
	// agent correspondents
	
	private BankGreeter greeter;
	private BankTeller teller;
	
	
	//States for finite state machine
	
	public enum CustomerState
	{nothing, waiting, inLine, goingToTeller, atTeller, receivedReceipt, done};
	private CustomerState state = CustomerState.nothing;//The start state
	
	
	//Functions
	
	public void setGreeter(BankGreeter greeter) {
		this.greeter = greeter;
	}
	
	public void setTeller(BankTeller t) {
		setBankTeller(t);
	}
	
	@Override
	public String getName() {
		return super.getName();
	}
	
	public int getWaitingNumber() {
		return waitingNumber;
	}
	
	public BankTeller getBankTeller() {
		return teller;
	}

	public void setBankTeller(BankTeller teller) {
		this.teller = teller;
	}
	
	public void setTransactions() {
		transactionList.add("openingAccount");
		transactionList.add("depositing");
	}
	
	
	// Messages

	public void msgWaitHere(int i) {
		//customerGui.goToWaitingPosition(i); //gui call to go to a position
		System.out.println(getName() + ": Told to wait in line");
		waitingNumber = i;
		state = CustomerState.inLine;
		DoGoToLine();
	}

	public void msgGoToTeller(BankTeller teller) {
		this.setBankTeller(teller);
		tellerIndex = teller.getIndex();
		System.out.println(getName() + ": Told to go to teller");
		state = CustomerState.inLine;
		stateChanged();
	}
	
	public void msgAtTeller() {
		System.out.println("Customer: At Teller.");
		state = CustomerState.atTeller;
		stateChanged();
	}	
	
	public void msgOutOfBank() {
		System.out.println(getName() + ": left the bank.");
		//getPersonAgent().doLeaveBuilding("Bank");
	}
	
	public void msgEnteredBank() {
		state = CustomerState.waiting;
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
		System.out.println(getName() + ": Received receipt. Account number is: " + accountNumber);
		getPersonAgent().addMoney((float)bankReceipt.transactionAmount);
		//add Account number info to PersonAgent
		state = CustomerState.receivedReceipt;
		stateChanged();
	}

	public void HereIsLoan(BankReceipt bankReceipt, double transactionAmount) {
		getPersonAgent().addMoney((float)bankReceipt.transactionAmount);
	}
	
	
	//Scheduler
	
	public boolean pickAndExecuteAnAction() {
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
		//getBankTeller().msgNeedATransaction(this, transactionType, transactionAmount);
	}

	private void makeATransaction() {
		System.out.println(getName() + ": Making a transaction. Type: " + transactionType);
		getBankTeller().msgNeedATransaction(this, transactionType, transactionAmount);
	}

	private void DoLeaveBank() {
		getBankTeller().msgDoneAndLeaving(this);
		bankCustomerGui.LeaveBank();
	}
}

