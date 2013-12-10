package simcity.restaurants.restaurant3;

import simcity.PersonAgent;
import simcity.TheCity;
import simcity.Home.ResidentRole.HomeEvent;
import simcity.bank.test.mock.LoggedEvent;
import simcity.restaurants.restaurant3.*;
import simcity.restaurants.restaurant3.gui.Restaurant3CustomerGui;
import simcity.restaurants.restaurant3.interfaces.*;
import trace.AlertLog;
import trace.AlertTag;
import Gui.RoleGui;
import agent.Role;

import java.util.Timer;
import java.util.TimerTask;
import java.util.Random;
/**
 * Restaurant customer agent.
 */
public class Restaurant3CustomerRole extends Role implements Customer {
	private String name;
	private int hungerLevel = 10;        // determines length of meal
	Timer timer = new Timer();
	private Restaurant3CustomerGui customerGui;
	//List<AgentEvent> events;
	// agent correspondents
	private HostRole host;
	private Waiter waiter;
	public Menu menu;
	private Restaurant3CashierRole cashier;
	public int tableNum;
	private double wallet;
	public Order order;
	public boolean leaveIfOutOfStock = false;
	public Random random = new Random();
	public boolean leaveRestaurant = random.nextBoolean();
	
	//private String choice;

	 //hack for gui
	public enum AgentState
	{DoingNothing, WaitingInRestaurant, Seated, Waiting, Eating, Paying, Leaving}; //Seated
	private AgentState state = AgentState.DoingNothing;//The start state

	//other agentstates:Seated, askedToOrder, ordered, DoneEating

	public enum AgentEvent 
	{none, gotHungry, followHost, seated, AskedToOrder, ordered, reordering, 
		foodDelivered, checkDelivered, doneEating, donePaying, doneLeaving};
	AgentEvent event = AgentEvent.none;
 

	/**
	 * Constructor for CustomerAgent class
	 *
	 * @param name name of the customer
	 * @param gui  reference to the customergui so the customer can send it messages
	 */
	public Restaurant3CustomerRole(PersonAgent p){
		super(p);
		customerGui = (Restaurant3CustomerGui)super.gui;
		//hello
		
		name = getPersonAgent().getName();
		
		//Random random = new Random();
		//this.wallet = random.nextInt(20); //random generated amount of money in wallet.
		//this.wallet = 7;
		//this.wallet = 0;
	}
	public Restaurant3CustomerRole(){
		super();
		customerGui = (Restaurant3CustomerGui)super.gui;
		//hello
		
		//Random random = new Random();
		//this.wallet = random.nextInt(20); //random generated amount of money in wallet.
		//this.wallet = 7;
		//this.wallet = 0;
	}

	
	/**
	 * hack to establish connection to Host agent.
	 */
	public void setHost(HostRole host) {
		this.host = host;
	}
	public void setCashier(Restaurant3CashierRole cashier) {
		this.cashier = cashier;
	}

	public String getCustomerName() {
		return name;
	}
	
	public double getWallet() {  
		return wallet;
	}
	
	public void setWallet(double newWallet) {
		//this.wallet = newWallet;
	}
	
	// Messages
	
	@Override
	
    public void startBuildingMessaging(){
            //Set host and cashier
        /*    host = (HostRole) myPerson.currentPreference.getHost();
            cashier = (Restaurant3CashierRole) myPerson.currentPreference.getCashier();
            gotHungry();*/
		host = (HostRole) ((Restaurant3)TheCity.getBuildingFromString("Restaurant 3")).getHost();
		cashier = (Restaurant3CashierRole) ((Restaurant3)TheCity.getBuildingFromString("Restaurant 3")).getCashier();
		if(((HostRole) host).checkWorkStatus() && ((Restaurant3CashierRole) cashier).checkWorkStatus()) {
			gotHungry();
		}
		else {
			myPerson.leftBuilding(this);
		}
    }

	public void gotHungry() {//from animation
		AlertLog.getInstance().logInfo(AlertTag.REST3, this.getName(),
				"I'm hungry");
		//System.out.println("I'm hungry");
		event = AgentEvent.gotHungry;
		stateChanged();
	}

	public void msgFollowMeToTable(Menu menu, int tnum) { //tnum
		AlertLog.getInstance().logInfo(AlertTag.REST3, this.getName(),
				"Follow me to the table");
		//System.out.println("Follow me to the table");
		this.menu = menu;
		event = AgentEvent.followHost;
		stateChanged();
		tableNum = tnum;
	}

	public void msgWhatWouldYouLike() {
		AlertLog.getInstance().logInfo(AlertTag.REST3, this.getName(),
				" the waiter asks the customer what they would like to eat");
		//System.out.println(this.getName() + " is asked by the waiter what he/she would like to eat.");
		event = AgentEvent.AskedToOrder;
		stateChanged();
	}

	public void msgHereIsYourFood() {
		AlertLog.getInstance().logInfo(AlertTag.REST3, this.getName(),
				"customer received his/her order from the waiter. ");
		//System.out.println(this.getName() + " received his/her order from the waiter.");
		//state = AgentState.Eating;
		event = AgentEvent.foodDelivered;
		stateChanged();
	}
	public void msgHereIsYourCheck(Order o) {
		AlertLog.getInstance().logInfo(AlertTag.REST3, this.getName(),
				"received the check from the waiter ");
		//System.out.println(this.getName() + " received the check from the waiter.");
		this.order = o;
		event = AgentEvent.checkDelivered;
		stateChanged();
	}
	
	public void msgAnimationFinishedGoToSeat() {
		//from animation
		//System.out.println("msgFinishedGoToSeat");
		event = AgentEvent.seated;
		stateChanged();
	}
	public void msgAnimationFinishedGoToCashier() {
		event = AgentEvent.donePaying;
		stateChanged();
	}

	public void msgAnimationFinishedLeaveRestaurant() {
		//from animation
		
		//System.out.println("Received msgFinishedLeaveRestaurant");
		myPerson.leftBuilding(this);
		event = AgentEvent.doneLeaving;
		stateChanged();
	}

	/**
	 * Scheduler.  Determine what action is called for, and do it.
	 */
	public boolean pickAndExecuteAnAction() {
		//	CustomerAgent is a finite state machine

		if (state == AgentState.DoingNothing && event == AgentEvent.gotHungry ){
			state = AgentState.WaitingInRestaurant;
			goToRestaurant();
			return true;
		}
		if (state == AgentState.WaitingInRestaurant && event == AgentEvent.followHost ){
			state = AgentState.Seated;
			SitDown();
			return true;
		}
		/*if(state == AgentState.BeingSeated && event == AgentEvent.seated){
			//state = AgentState.AskedToOrder;
			
			try {
				System.out.System.out.printlnln("Customer is looking at the menu");
				Thread.sleep(3000);
				System.out.System.out.printlnln("Customer is ready to order");
			
			}catch(Exception e) {
				System.out.System.out.println(e.getMessage());
			}
			return true;
		}*/
		if (state == AgentState.Seated && event == AgentEvent.AskedToOrder){
			state = AgentState.Waiting;
			SayChoice();
			return true;
		}
		
		if (state == AgentState.Waiting && event == AgentEvent.AskedToOrder){

			if (order.outOfStock && leaveIfOutOfStock) {
				state = AgentState.Leaving;
				leaveTable();
				return true;
			}
			else {
				state = AgentState.Waiting;
				SayChoice();
				return true;
			}
		}
		
	//	if (state == AgentState.Seated && event == AgentEvent.AskedToOrder){
	//		state = AgentState.Waiting;
		//	ReOrder();
		//	return true;
	//	}
		//if (state == AgentState.WaitingInRestaurant && event == AgentEvent.ordered){
		//	state = AgentState.Eating;

		//	return true;
		//

		if (state == AgentState.Waiting && event == AgentEvent.foodDelivered){
			state = AgentState.Eating;
			System.out.println("food delivered if");
			EatFood();
			return true;
		}
		if (state == AgentState.Eating && event == AgentEvent.doneEating){
			state = AgentState.Waiting;
			waiter.msgIWantTheCheck(this);
			//PayCheck();
			return true;
		}
		if (state == AgentState.Waiting && event == AgentEvent.checkDelivered){
			state = AgentState.Paying;
			PayCheck();
			return true;
		}
//		if (state == AgentState.Paying && customerGui.getXPos() == 600 && customerGui.getYPos() == 200 && event != AgentEvent.donePaying){
//			//System.out.println("*************inside if");

//			cashier.msgCustomerPayingCheck(order);
//			return true;
//		}
		if (state == AgentState.Paying && event == AgentEvent.donePaying){
			state = AgentState.Leaving;
			leaveTable();
			return true;
		}
		if (state == AgentState.Leaving && event == AgentEvent.doneLeaving){
			state = AgentState.DoingNothing;
			//no action
			return true;
		}
		return false;
	}

	// Actions

	private void goToRestaurant() {
		AlertLog.getInstance().logInfo(AlertTag.REST3, this.getName(),
				"going to restaurant");
		//System.out.println(this.getName() + " is going to the restaurant");

		host.msgIWantToEat(this);//send our instance, so he can respond to us
	}
	/*private void tellHostIWantToEat() {
//		
		System.out.println("Telling host they are hungry");
	}
	*/
	private void SitDown() {
//		Do("Being seated. Going to table");
		AlertLog.getInstance().logInfo(AlertTag.REST3, this.getName(),
				"being seated and going to a table");
		//System.out.println(this.getName() + " is being seated and going to a table.");
		customerGui.DoGoToSeat(tableNum);//hack; only one table
		event = AgentEvent.seated;
		stateChanged();
	}

	private void SayChoice() {
//		Do("Customer orders food");
		AlertLog.getInstance().logInfo(AlertTag.REST3, this.getName(),
				"Customer says what tpe of food he/she wants");
		//System.out.println("Customer says what type of food he/she wants.");
		if (myPerson.getMoney() < cheapestFood()) {
			//Random random = new Random();
			//boolean leaveRestaurant = random.nextBoolean();
			if (leaveRestaurant) {
				AlertLog.getInstance().logInfo(AlertTag.REST3,
						this.getName(), "Customer does not have enough money and chose to leave the restaurant.");
				//System.out.println("Customer " + getName() + " does not have enough money and chose to leave the restaurant.");
				leaveTable();
				//waiter.setCustomerStateLeaving(this);
				return;
			}
			else if(!leaveRestaurant){
				AlertLog.getInstance().logInfo(AlertTag.REST3,
						this.getName(), "Customer does not have enough money and chose to order anyway.");
				//System.out.println("Customer " + getName() + " does not have enough money and chose to order anyway.");
				//waiter.setCustomerStateReadyToOrder(this);
			}
				
		}
		
		
		String choice = randomizeFoodChoice();
		AlertLog.getInstance().logInfo(AlertTag.REST3, this.getName(),
				"customer orders food");
		//System.out.println(this.getName() + " orders food.");
		customerGui.order=choice;
		customerGui.waitingForFood=true;
		waiter.msgHereIsMyChoice(this, choice);
		
		event = AgentEvent.ordered;
		stateChanged();
	}
	
	private double cheapestFood() {
		double min = Double.MAX_VALUE;
		
		for (int i = 0; i < this.menu.foods.size(); ++i) {
			if (this.menu.foods.get(i).getFoodPrice() < min)
				min = this.menu.foods.get(i).getFoodPrice();
		}
		
		return min;
	}

	private void EatFood() {
		customerGui.waitingForFood=false;
		customerGui.receivedFood = true;
		AlertLog.getInstance().logInfo(AlertTag.REST3, this.getName(),
				"customer is eating his/her food");
		//System.out.println(this.getName() + " is eating his/her food");
		//This next complicated line creates and starts a timer thread.
		//We schedule a deadline of getHungerLevel()*1000 milliseconds.
		//When that time elapses, it will call back to the run routine
		//located in the anonymous class created right there inline:
		//TimerTask is an interface that we implement right there inline.
		//Since Java does not all us to pass functions, only objects.
		//So, we use Java syntactic mechanism to create an
		//anonymous inner class that has the public method run() in it.
		/*timer.schedule(new TimerTask() {
			//Object cookie = 1;
			public void run() {
				
			}
		},
		5000);//getHungerLevel() * 1000);//how long to wait before running task*/
		timer.schedule(new TimerTask() {
			public void run() {
				myPerson.justAte();
				//AlertLog.getInstance().logInfo(AlertTag.REST3,
					//	this.getName(), "Done Eating");
				//System.out.println("Done eating");
				event = AgentEvent.doneEating;
				stateChanged();
			}
		}, 3000);
		
		customerGui.receivedFood=false;
		//isHungry = false;
		
	}
	
	private void PayCheck() {
		AlertLog.getInstance().logInfo(AlertTag.REST3, this.getName(),
				"customer is going to the cashier and paying the check");
		//System.out.println(this.getName() + " is going to the cashier and paying the check");
		customerGui.DoGoToCashier();
		cashier.msgCustomerPayingCheck(order);
		if (myPerson.getMoney() >= order.totalPrice) {
			myPerson.setMoney(myPerson.getMoney() - order.totalPrice);
			cashier.setRestaurantRevenue(order.totalPrice);
		}
		else {
			AlertLog.getInstance().logInfo(AlertTag.REST3,
					this.getName(), " Customer owes $" + order.totalPrice + " next time.");
			//System.out.println("Customer owes $" + order.totalPrice + " next time.");
			cashier.owed.put(this, order.totalPrice);
		}
		cashier.msgCustomerPayingCheck(order);
			
		
		//event = AgentEvent.donePaying;
		
		//stateChanged();
	}
	private void leaveTable() {
		AlertLog.getInstance().logInfo(AlertTag.REST3, this.getName(),
				"is leaving the restaurant");
		//System.out.println(this.getName() + " is leaving the restaurant");
		waiter.msgDoneEatingAndLeavingTable(this);
		//host.msgLeavingTable(this);
		customerGui.DoExitRestaurant();
		event = AgentEvent.doneLeaving;
		stateChanged();
	}

	// Accessors, etc.

	public String getName() {
		return myPerson.getName();
	}

	public int getHungerLevel() {
		return hungerLevel;
	}
	public void setDonePayingState() {
		event = AgentEvent.donePaying;
		stateChanged();
	}

	private String randomizeFoodChoice() {
		String choice = null;
		
		Random r = new Random();
		int x;
		
		if (wallet < cheapestFood()) {
			x = r.nextInt(4) + 1;
		} 
		else {
		
			int i =0;
			for ( ; i < 4 && wallet > menu.foods.get(i).getFoodPrice(); ++i);
			
			if (i == 1) { //only can afford the cheapest food
				leaveIfOutOfStock = true;
			}
			x = r.nextInt(i) + 1;
		}
		
		if(x == 1) {
			choice = "Salad";
			System.out.println("choice = Salad");
		}
		if(x == 2) {
			choice = "Pizza";
			System.out.println("choice = pizza");
		}
		if(x == 3){
			choice = "Chicken";
			System.out.println("choice = Chicken");
		}
		if(x == 4){
			choice = "Steak";
			System.out.println("choice = Steak");
		}
		

		return choice;
	}

	public void setHungerLevel(int hungerLevel) {
		this.hungerLevel = hungerLevel;
		//could be a state change. Maybe you don't
		//need to eat until hunger lever is > 5?
	}

	public String toString() {
		return "customer " + getName();
	}

	public void setGui(RoleGui g) {
		super.setGui(g);
		customerGui = (Restaurant3CustomerGui)g;
	}

	public void setWaiter(Waiter waiter) {
		this.waiter = waiter;
	}

	public Restaurant3CustomerGui getGui() {
		return customerGui;
	}
	public double getWalletAmount() {
		return wallet;
	}
}
