package Home;


//import restaurant.WaiterAgent.Menu;
//import Home.gui.ResidentGui;
//import Home.gui.HomeGui;
import Home.interfaces.Resident;
import agent.Agent;
import agent.Role;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

import simcity.PersonAgent;

/**
 * Restaurant customer agent.
 */
public class ResidentRole extends Role implements Resident{
	private String name;
	Timer timer = new Timer();
	private double wallet;
	public Random random = new Random();
	private List<String> groceryList;
	public List<Food> fridgeFoods;
	
	
	//public boolean leaveRestaurant = random.nextBoolean();
	//private ResidentGui residentGui;
	
	//private String choice;

	 //hack for gui
	public enum HomeState
	{DoingNothing, CheckingFoodSupply, Plating, Eating, Clearing}; 
	private HomeState state = HomeState.DoingNothing;//The start state

	public enum HomeEvent 
	{none, gotHungry, collectedIngredients, doneCooking, donePlating};
	HomeEvent event = HomeEvent.none;
 

	/**
	 * Constructor for CustomerAgent class
	 *
	 * @param name name of the customer
	 * @param gui  reference to the customergui so the customer can send it messages
	 */
	public ResidentRole(PersonAgent p){
		super(p);
		
	}
	

	public String getCustomerName() {
		return name;
	}
	
	public double getWallet() {  
		return wallet;
	}
	
	public void setWallet(double newWallet) {
		this.wallet = newWallet;
	}
	
	// Messages

	public void gotHungry() {//from animation
		System.out.println("I'm hungry");
		event = HomeEvent.gotHungry;
		stateChanged();
	}

	
	public void msgAnimationFinishedGoToSeat() {
		//from animation
		//print("msgFinishedGoToSeat");
		//event = AgentEvent.seated;
		//stateChanged();
	}
	public void msgAnimationFinishedGoToCashier() {
		//event = AgentEvent.donePaying;
		//stateChanged();
	}

	public void msgAnimationFinishedLeaveRestaurant() {
		//from animation
		System.out.println("Received msgFinishedLeaveRestaurant");
		//event = AgentEvent.doneLeaving;
		//stateChanged();
	}

	/**
	 * Scheduler.  Determine what action is called for, and do it.
	 */
	@Override
	public boolean pickAndExecuteAnAction() {
		//	ResidentRole is a finite state machine

		
		if (state == HomeState.DoingNothing && event == HomeEvent.gotHungry ){
			state = HomeState.WaitingInRestaurant;
			goToRestaurant();
			return true;
		}
		if (state == HomeState.WaitingInRestaurant && event == HomeEvent.followHost ){
			state = HomeState.Seated;
			SitDown();
			return true;
		}
		/*if(state == AgentState.BeingSeated && event == HomeEvent.seated){
			//state = HomeState.AskedToOrder;
			
			try {
				System.out.println("Customer is looking at the menu");
				Thread.sleep(3000);
				System.out.println("Customer is ready to order");
			
			}catch(Exception e) {
				System.out.print(e.getMessage());
			}
			return true;
		}
		if (state == HomeState.Seated && event == HomeEvent.AskedToOrder){
			state = HomeState.Waiting;
			SayChoice();
			return true;
		}
		
		if (state == HomeState.Waiting && event == HomeEvent.AskedToOrder){

			if (order.outOfStock && leaveIfOutOfStock) {
				state = HomeState.Leaving;
				leaveTable();
				return true;
			}
			else {
				state = HomeState.Waiting;
				SayChoice();
				return true;
			}
		}
		
	//	if (state == HomeState.Seated && event == HomeEvent.AskedToOrder){
	//		state = HomeState.Waiting;
		//	ReOrder();
		//	return true;
	//	}
		//if (state == HomeState.WaitingInRestaurant && event == HomeEvent.ordered){
		//	state = HomeState.Eating;

		//	return true;
		//

		if (state == HomeState.Waiting && event == HomeEvent.foodDelivered){
			state = HomeState.Eating;
			print("food delivered if");
			EatFood();
			return true;
		}
		if (state == HomeState.Eating && event == HomeEvent.doneEating){
			state = HomeState.Waiting;
			waiter.msgIWantTheCheck(this);
			//PayCheck();
			return true;
		}
		if (state == AgentState.Waiting && event == HomeEvent.checkDelivered){
			state = HomeState.Paying;
			PayCheck();
			return true;
		}
		if (state == HomeState.Paying && customerGui.getXPos() == 600 && customerGui.getYPos() == 200 && event != HomeEvent.donePaying){
			//print("*************inside if");
			cashier.msgCustomerPayingCheck(order);
			return true;
		}
		if (state == HomeState.Paying && event == HomeEvent.donePaying){
			state = HomeState.Leaving;
			leaveTable();
			return true;
		}
		if (state == HomeState.Leaving && event == HomeEvent.doneLeaving){
			state = HomeState.DoingNothing;
			//no action
			return true;
		}
		*/
		return false;
		
	}

	// Actions
/*
	private void goToRestaurant() {
		print(this.getName() + " is going to the restaurant");
//		Do("Going to restaurant");
		host.msgIWantToEat(this);//send our instance, so he can respond to us
	}
	/*private void tellHostIWantToEat() {
//		Do("Telling host they are hungry");
		print("Telling host they are hungry");
	}
	
	private void SitDown() {
//		Do("Being seated. Going to table");
		print(this.getName() + " is being seated and going to a table.");
		customerGui.DoGoToSeat(tableNum);//hack; only one table
		event = HomeEvent.seated;
		stateChanged();
	}

	private void SayChoice() {
//		Do("Customer orders food");
		print("Customer says what type of food he/she wants.");
		if (wallet < cheapestFood()) {
			//Random random = new Random();
			//boolean leaveRestaurant = random.nextBoolean();
			if (leaveRestaurant) {
				print("Customer " + getName() + " does not have enough money and chose to leave the restaurant.");
				leaveTable();
				//waiter.setCustomerStateLeaving(this);
				return;
			}
			else if(!leaveRestaurant){
				print("Customer " + getName() + " does not have enough money and chose to order anyway.");
				//waiter.setCustomerStateReadyToOrder(this);
			}
				
		}
		
		
		String choice = randomizeFoodChoice();
		print(this.getName() + " orders food.");
		customerGui.order=choice;
		customerGui.waitingForFood=true;
		waiter.msgHereIsMyChoice(this, choice);
		
		event = HomeEvent.ordered;
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
		customerGui.receivedFood=true;
		Do(this.getName() + " is eating his/her food");
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
		5000);//getHungerLevel() * 1000);//how long to wait before running task
		try {
			Thread.currentThread().sleep(5000);
		}catch(Exception e) {
			System.out.print(e.getMessage());
		}
		
		print("Done eating");
		event = HomeEvent.doneEating;
		customerGui.receivedFood=false;
		//isHungry = false;
		stateChanged();
	}
	
	private void PayCheck() {
		print(this.getName() + " is going to the cashier and paying the check");
		customerGui.DoGoToCashier();
		if (wallet >= order.totalPrice) {
			this.setWallet(wallet - order.totalPrice);
			cashier.setRestaurantRevenue(order.totalPrice);
		}
		else {
			print("Customer owes $" + order.totalPrice + " next time.");
			cashier.owed.put(this, order.totalPrice);
		}
			
		
		//event = HomeEvent.donePaying;
		
		stateChanged();
	}
	private void leaveTable() {
		print(this.getName() + " is leaving the restaurant");
		waiter.msgDoneEatingAndLeavingTable(this);
		//host.msgLeavingTable(this);
		customerGui.DoExitRestaurant();
		event = HomeEvent.doneLeaving;
		stateChanged();
	}

	// Accessors, etc.

	public String getName() {
		return name;
	}

	public int getHungerLevel() {
		return hungerLevel;
	}
	public void setDonePayingState() {
		event = HomeEvent.donePaying;
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
			print("choice = Salad");
		}
		if(x == 2) {
			choice = "Pizza";
			print("choice = pizza");
		}
		if(x == 3){
			choice = "Chicken";
			print("choice = Chicken");
		}
		if(x == 4){
			choice = "Steak";
			print("choice = Steak");
		}
		

		return choice;
	}
*/
	//public String toString() {
		//return "customer " + getName();
	//}

/*
	public void setGui(ResidentGui g) {
		residentGui = g;
	}

	public ResidentGui getGui() {
		return residentGui;
	}
	*/
	public double getWalletAmount() {
		return wallet;
	}
}
