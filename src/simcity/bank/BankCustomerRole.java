package simcity.bank;

import Gui.RoleGui;
import agent.Role;
import simcity.PersonAgent;
import simcity.PersonAgent.MoneyState;
import simcity.PersonAgent.RentBill;
import simcity.TheCity;
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
	
	public int  waitingNumber;
	private int tellerIndex;
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
	{nothing, entered, waiting, goingToLine, inLine, goingToTeller, atTeller, receivedReceipt, done};
	public CustomerState state = CustomerState.nothing;//The start state
	
	
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
		
		if(myPerson.moneyState == MoneyState.Low) {
			print("Withdrawing some money"); //Nikhil --
			transactionList.add(new BankTransaction("withdrawal", 50));
		}
		if(myPerson.moneyState == MoneyState.High) {
			print("Gonna deposit some money"); //Nikhil - debugging this code
			transactionList.add(new BankTransaction("deposit", myPerson.getMoney() - 130.0)); // Changed from set 50 to deposit proper amount - Nikhil
		//	myPerson.setMoney(130.0); //Above didnt work, so manually removing from person's wallet so he doesnt repeated go to bank - Nikhil
		}
		
	}
	
	
	// Messages
	
	@Override
	public void startBuildingMessaging(){
		setTransactions();
		//greeter = myPerson.bank.getGreeter();
		greeter = ((Bank)TheCity.getBuildingFromString("Bank")).getGreeter();
		msgEnteredBank();
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
	
	public void msgEnteredBank() {
		System.out.println("Entered Bank");
		state = CustomerState.entered;
		stateChanged();
	}
	
	@Override
	public void NotEligibleForLoan() {
		state = CustomerState.receivedReceipt; //For Scheduler - no actual receipt
		stateChanged();
	}
	
	@Override
	public void HereIsReceipt(BankReceipt receipt) {
		switch(receipt.transactionType) {
		case "withdrawal":
			if(receipt.transactionAmount < receipt.originalBalance)
				getPersonAgent().addMoney((float)receipt.transactionAmount); 
			else
				getPersonAgent().addMoney((float)receipt.originalBalance);
			getPersonAgent().moneyState = MoneyState.Neutral; break;
		case "deposit":
			getPersonAgent().removeMoney((float)receipt.transactionAmount); 
			getPersonAgent().moneyState = MoneyState.Neutral; break;
		}
		state = CustomerState.receivedReceipt;
		stateChanged();
	}

	public void HereIsReceiptAndAccountInfo(BankReceipt bankReceipt, int accountNumber) {
		System.out.println(getName() + ": Received receipt. Account number is: " + accountNumber);
		switch(bankReceipt.transactionType) {
		case "openingAccount":
			getPersonAgent().removeMoney((float)bankReceipt.transactionAmount); 
			getPersonAgent().setAccountNumber(accountNumber); break;
		case "closingAccount":
			getPersonAgent().addMoney((float)bankReceipt.transactionAmount); break;
		}
		state = CustomerState.receivedReceipt;
		stateChanged();
	}

	public void HereIsLoan(BankReceipt bankReceipt, double transactionAmount) {
		getPersonAgent().addMoney((float)bankReceipt.transactionAmount);
		state = CustomerState.receivedReceipt;
		stateChanged();
	}
	
	public void msgOutOfBank() {
		print("The person's money after leaving the bank: " + myPerson.getMoney()); //Nikhil: Trying to debug to see why the person keeps coming back to bank
		System.out.println(getName() + ": left the bank.");
		transactionList.clear();
		myPerson.leftBuilding(this);
	}
	
	
	//Scheduler
	
	public boolean pickAndExecuteAnAction() {
		
		if (state == CustomerState.entered ){
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
		System.out.println("Need a teller");
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
		if(!transactionList.isEmpty()) {
			System.out.println(getName() + ": Making a transaction. Type: " + transactionList.get(0).transactionType);
			getBankTeller().msgNeedATransaction(this, transactionList.get(0));
			transactionList.remove(0);
		}
		state = CustomerState.waiting;
	}
	
	private void DoLeaveBank() {
		getBankTeller().msgDoneAndLeaving(this);
		bankCustomerGui.LeaveBank();
		state = CustomerState.done;
	}
}

