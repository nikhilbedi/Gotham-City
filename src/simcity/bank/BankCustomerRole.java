package simcity.bank;

import Gui.RoleGui;
import agent.Role;
import simcity.PersonAgent;
import simcity.PersonAgent.MoneyState;
import simcity.PersonAgent.RentBill;
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
	// agent correspondents
	
	private BankGreeter greeter;
	private BankTeller teller;
	
	//constructor call to Role constructor
	public BankCustomerRole(PersonAgent person) {
		super(person);
		bankCustomerGui = (bankCustomerGui)super.gui;
		//setTransactions();
	}
	public BankCustomerRole(){
		super();
		bankCustomerGui = (bankCustomerGui)super.gui;
		//setTransactions();
	}
	
	//State Variables
	
	private int  waitingNumber, tellerIndex;
	Timer timer = new Timer();
	double cash, transactionAmount;
	String transactionType = "openingAccount";  //This must be changed to interact with the Person's intentions at the bank
	//public List<String> transactionList = new ArrayList<String>();
	public List<BankTransaction> transactionList = new ArrayList<BankTransaction>();
	
	
	//Gui
	
	public bankCustomerGui bankCustomerGui;
	
	//sets gui and initial positioning (Temp)
	
	public void setGui(RoleGui g){
		super.setGui(g);
		bankCustomerGui = (bankCustomerGui)g;
	}
	
	/*public void setGui(bankCustomerGui gui, int x, int y) {
			bankCustomerGui = gui;
			bankCustomerGui.setX(x);
			bankCustomerGui.setY(y);
		}*/
	
	
	
	
	
	//States for finite state machine
	
	public enum CustomerState
	{nothing, temp, waiting, goingToLine, inLine, goingToTeller, atTeller, receivedReceipt, done};
	public CustomerState state = CustomerState.temp;//The start state
	
	
	//Functions
	
	public void setGreeter(BankGreeter greeter) {
		this.greeter = greeter;
	}
	
	public BankGreeter getBankGreeter() {
		return greeter;
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
		/*transactionList.add("openingAccount");
		transactionList.add("deposit");
		transactionList.add("withdrawal");
		transactionList.add("needALoan");*/
		
		//myPerson.rentBills.add();
		
		/*transactionList.add(new BankTransaction("openingAccount", 50));
		transactionList.add(new BankTransaction("deposit", 50));
		transactionList.add(new BankTransaction("withdrawal", 40));
		transactionList.add(new BankTransaction("needALoan", 200));*/
		
		if(myPerson.getAccountNumber() == 0) {
			System.out.println(getName() + ": NEED TO OPEN ACCOUNT");
			transactionList.add(new BankTransaction("openingAccount", 50));
		}
		
		if(myPerson.getRentBills().size() > 0) {
			for(RentBill b : myPerson.getRentBills())
					transactionList.add(new BankTransaction("payingRentBill", b.amount, b.accountHolder.getName()));
		}
		
		if(myPerson.moneyState == MoneyState.Low)
			transactionList.add(new BankTransaction("withdrawal", 50));
		if(myPerson.moneyState == MoneyState.High)
			transactionList.add(new BankTransaction("deposit", 50));
		
		
	}
	
	
	// Messages
	@Override
	public void startBuildingMessaging(){
		msgEnteredBank();
		setTransactions();
	}

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
		myPerson.leftBuilding(this);
	}
	
	public void msgEnteredBank() {
		System.out.println("Entered Bank");
		greeter = myPerson.bank.getGreeter();
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
			System.out.println("talk to greeter");
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
		System.out.println("HI");
		greeter.msgNeedATeller(this);
		state = CustomerState.waiting;
	}
	
	private void DoGoToLine() {
		state = CustomerState.goingToLine;
		bankCustomerGui.GetInLine(waitingNumber);
	}
	
	private void DoGoToTeller() {
		state = CustomerState.goingToTeller;
		System.out.println("this is a message" + bankCustomerGui);
		bankCustomerGui.GoToTeller(tellerIndex);
	}
	
	private void makeATransaction() {
		System.out.println(getName() + ": Making a transaction. Type: " + transactionList.get(0).transactionType);
		getBankTeller().msgNeedATransaction(this, transactionList.get(0));
		transactionList.remove(0);
		state = CustomerState.waiting;
	}
	
	private void DoLeaveBank() {
		getBankTeller().msgDoneAndLeaving(this);
		bankCustomerGui.LeaveBank();
		state = CustomerState.done;
	}
}

