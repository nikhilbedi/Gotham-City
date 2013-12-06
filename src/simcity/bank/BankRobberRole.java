package simcity.bank;

import Gui.RoleGui;
import agent.Role;
import simcity.PersonAgent;
import simcity.PersonAgent.MoneyState;
import simcity.PersonAgent.RentBill;
import simcity.bank.BankReceipt;
import simcity.bank.interfaces.BankCustomer;
import simcity.bank.interfaces.BankGreeter;
import simcity.bank.interfaces.BankRobber;
import simcity.bank.interfaces.BankTeller;

import java.util.List;
import java.util.ArrayList;
import java.util.Timer;

/**
 * Bank Customer Role
 * Programmer: Brice Roland
 */
public class BankRobberRole extends Role implements BankRobber{
	
	// agent correspondents
	
	private BankGreeter greeter;
	private BankTeller teller;
	
	
	//constructor call to Role constructor
	public BankRobberRole(PersonAgent person) {
		super(person);
		bankRobberGui = (BankRobberGui)super.gui;
	}
	public BankRobberRole(){
		super();
		bankRobberGui = (BankRobberGui)super.gui;
	}
	
	
	//State Variables
	
	private int tellerIndex;
	Timer timer = new Timer();
	double cash, transactionAmount;
	//String transactionType = "openingAccount";  //This must be changed to interact with the Person's intentions at the bank
	//public List<String> transactionList = new ArrayList<String>();
	public List<BankTransaction> transactionList = new ArrayList<BankTransaction>();
	
	
	//Gui
	
	public BankRobberGui bankRobberGui;
	
	//sets gui and initial positioning (Temp)
	
	public void setGui(RoleGui g){
		super.setGui(g);
		bankRobberGui = (BankRobberGui)g;
	}
	
	/*public void setGui(bankCustomerGui gui, int x, int y) {
			bankCustomerGui = gui;
			bankCustomerGui.setX(x);
			bankCustomerGui.setY(y);
		}*/
	//States for finite state machine
	
	public enum RobberState
	{nothing, entered, waiting, goingToLine, inLine, goingToTeller, atTeller, receivedMoney, done};
	public RobberState state = RobberState.nothing;//The start state
	
	
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
	
	public BankTeller getBankTeller() {
		return teller;
	}

	public void setBankTeller(BankTeller teller) {
		this.teller = teller;
	}
		
	/*public void setTransactions() {
		
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
		
	}*/
	
	
	// Messages
	
	@Override
	public void startBuildingMessaging(){
		//setTransactions();
		msgEnteredBank();
	}
	
	/*public void msgWaitHere(int i) {
		System.out.println(getName() + ": Told to wait in line");
		waitingNumber = i;
		state = CustomerState.inLine;
		DoGoToLine();
	}*/
	
	public void msgGoToTeller(BankTeller teller) {
		this.setBankTeller(teller);
		tellerIndex = teller.getIndex();
		System.out.println(getName() + ": Told to go to teller");
		state = RobberState.inLine;
		stateChanged();
	}
	
	public void msgAtTeller() {
		System.out.println("Customer: At Teller.");
		state = RobberState.atTeller;
		stateChanged();
	}	
	
	public void msgEnteredBank() {
		System.out.println("Entered Bank");
		greeter = myPerson.bank.getGreeter();
		state = RobberState.entered;
		stateChanged();
	}
	
	public void msgHeresYourMoney(double amount) {
		System.out.println("Thank you. I'll see myself out.");
		state = RobberState.receivedMoney;
		stateChanged();
	}
	
	public void msgOutOfBank() {
		print("The person's money after leaving the bank: " + myPerson.getMoney()); //Nikhil: Trying to debug to see why the person keeps coming back to bank
		System.out.println(getName() + ": left the bank.");
		myPerson.leftBuilding(this);
	}
	
	/*@Override
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
	
	*/
	
	
	//Scheduler
	
	public boolean pickAndExecuteAnAction() {
		
		if (state == RobberState.entered ){
			System.out.println("talk to greeter");
			talkToGreeter();
			return true;
		}
		
		if (state == RobberState.inLine){
			System.out.println("Going to Teller");
			DoGoToTeller();
			return true;
		}
		
		if (state == RobberState.atTeller){
			demandMoney();
			return true;
		}
		
		if (state == RobberState.receivedMoney){
			DoLeaveBank();
		}
		return false;
	}
	
	
	// Actions
	
	private void talkToGreeter() {
		System.out.println("Need a teller");
		greeter.msgGiveMeATeller(this);
		state = RobberState.waiting;
	}
	
	private void DoGoToTeller() {
		state = RobberState.goingToTeller;
		//System.out.println("this is a message" + bankRobberGui);
		bankRobberGui.GoToTeller(tellerIndex);
	}
	
	private void demandMoney() {
		System.out.println(getName() + ": Gimme da money.");
		getBankTeller().msgGiveMeMoney(this);
		transactionList.remove(0);
	}
	
	private void DoLeaveBank() {
		getBankTeller().msgDoneAndLeaving(this);
		bankRobberGui.LeaveBank();
		state = RobberState.done;
	}
}

