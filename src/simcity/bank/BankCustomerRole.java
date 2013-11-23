package simcity.bank;

import agent.Role;
import simcity.PersonAgent;
import simcity.bank.BankReceipt;
import simcity.bank.interfaces.BankCustomer;
import simcity.bank.interfaces.BankGreeter;
import simcity.bank.interfaces.BankTeller;

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
	{nothing, waiting, goingToLine, inLine, goingToTeller, atTeller, receivedReceipt, done};
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
		transactionList.add("deposit");
		transactionList.add("withdrawal");
		transactionList.add("needALoan");
		
		/*if(myPerson.getMoneyState() == myPerson.moneyState.Low)
			transactionList.add("withdrawal");
		if(myPerson.getMoneyState() == myPerson.moneyState.High)
			transactionList.add("deposit");
		if(myPerson.getAccountNumber == 0)
			transactionList.add("openAccount");*/
		
	}
	
	
	// Messages

	public void msgWaitHere(int i) {
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
		myPerson.leavingBuilding(this);
	}
	
	public void msgEnteredBank() {
		System.out.println("Entered Bank");
		state = CustomerState.nothing;
		stateChanged();
	}
	
	@Override
	public void NotEligibleForLoan() {
		state = CustomerState.receivedReceipt; //For Scheduler - no actual receipt
		stateChanged();
	}
	
	@Override
	public void HereIsReceipt(BankReceipt receipt) {
		getPersonAgent().addMoney((float)receipt.transactionAmount);
		state = CustomerState.receivedReceipt;
		stateChanged();
	}

	public void HereIsReceiptAndAccountInfo(BankReceipt bankReceipt, int accountNumber) {
		System.out.println(getName() + ": Received receipt. Account number is: " + accountNumber);
		getPersonAgent().addMoney((float)bankReceipt.transactionAmount);
		//add Account number info to PersonAgent
		getPersonAgent().setAccountNumber(accountNumber);
		state = CustomerState.receivedReceipt;
		stateChanged();
	}

	public void HereIsLoan(BankReceipt bankReceipt, double transactionAmount) {
		getPersonAgent().addMoney((float)bankReceipt.transactionAmount);
		state = CustomerState.receivedReceipt;
		stateChanged();
	}
	
	
	//Scheduler
	
	public boolean pickAndExecuteAnAction() {
		
		if (state == CustomerState.nothing ){
			talkToGreeter();
			return true;
		}
		
		if (state == CustomerState.inLine){
			System.out.println("Going to Teller");
			DoGoToTeller();
			return true;
		}
		
		if (state == CustomerState.atTeller){
			makeATransaction();
			return true;
		}
		
		if (state == CustomerState.receivedReceipt){
			if(transactionList.size() > 0)
				makeATransaction();
			else
				DoLeaveBank();
			return true;
		}
		return false;
	}
	
	
	// Actions
	
	private void talkToGreeter() {
		greeter.msgNeedATeller(this);
		state = CustomerState.waiting;
	}
	
	private void DoGoToLine() {
		state = CustomerState.goingToLine;
		bankCustomerGui.GetInLine(waitingNumber);
	}
	
	private void DoGoToTeller() {
		state = CustomerState.goingToTeller;
		bankCustomerGui.GoToTeller(tellerIndex);
	}
	
	private void makeATransaction() {
		System.out.println(getName() + ": Making a transaction. Type: " + transactionList.get(0));
		getBankTeller().msgNeedATransaction(this, transactionList.get(0), transactionAmount);
		transactionList.remove(0);
		state = CustomerState.waiting;
	}
	
	private void DoLeaveBank() {
		getBankTeller().msgDoneAndLeaving(this);
		bankCustomerGui.LeaveBank();
		state = CustomerState.done;
	}
}

