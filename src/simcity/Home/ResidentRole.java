package simcity.Home;


//import restaurant.WaiterAgent.Menu;
//import Home.gui.ResidentGui;
//import Home.gui.HomeGui;
import agent.Agent;
import agent.Role;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import simcity.PersonAgent;

/**
 * Restaurant customer agent.
 */
public class ResidentRole extends Role {
	private String name;
	Timer timer = new Timer();
	
	//private ResidentGui residentGui;

	private double wallet;
	
	
	public Random random = new Random();
	public boolean leaveRestaurant = random.nextBoolean();
	
	//private String choice;

	 //hack for gui
	public enum HomeState
	{DoingNothing, CheckingFoodSupply,Cooking, Plating, Eating, Clearing}; //Seated
	private HomeState state = HomeState.DoingNothing;//The start state

	//other Homestates:Seated, askedToOrder, ordered, DoneEating

	public enum HomeEvent 
	{none, gotHungry, collectedIngredients, doneCooking, donePlating, doneEating, doneClearing};
	HomeEvent event = HomeEvent.none;
 

	/**
	 * Constructor for CustomerHome class
	 *
	 * @param name name of the customer
	 * @param gui  reference to the customergui so the customer can send it messages
	 */
	public ResidentRole(PersonAgent p){
		super(p);
	
	}
	
	/**
	 * hack to establish connection to Host agent.
	 */
	

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
		//event = HomeEvent.seated;
		//stateChanged();
	}
	public void msgAnimationFinishedGoToCashier() {
		//event = HomeEvent.donePaying;
		//stateChanged();
	}

	public void msgAnimationFinishedLeaveRestaurant() {
		//from animation
		System.out.println("Received msgFinishedLeaveRestaurant");
		//event = HomeEvent.doneLeaving;
		//stateChanged();
	}

	/**
	 * Scheduler.  Determine what action is called for, and do it.
	 */
	@Override
	public boolean pickAndExecuteAnAction() {
		//	CustomerHome is a finite state machine

		if (state == HomeState.DoingNothing && event == HomeEvent.gotHungry ){
			state = HomeState.CheckingFoodSupply;
			checkFoodSupply();
			return true;
		}
		if (state == HomeState.CheckingFoodSupply && event == HomeEvent.collectedIngredients){
			state = HomeState.Cooking;
			cookFood();
			return true;
		}
		if(state == HomeState.Cooking && event == HomeEvent.doneCooking){
			state = HomeState.Plating;
			plateFood();
			return true;
		}
		if (state == HomeState.Plating && event == HomeEvent.donePlating){
			state = HomeState.Eating;
			eatFood();
			return true;
		}
		if (state == HomeState.Eating && event == HomeEvent.doneEating){
			state = HomeState.Clearing;
			clearFood();
			return true;
		}
		if (state == HomeState.Clearing && event == HomeEvent.doneClearing){
			state = HomeState.DoingNothing;
			returnToHomePosition();
			return true;
		}
		
		return false;
	}

	// Actions

	private void returnToHomePosition() {
		// TODO Auto-generated method stub
		
	}

	private void clearFood() {
		// TODO Auto-generated method stub
		
	}

	private void eatFood() {
		// TODO Auto-generated method stub
		
	}

	private void plateFood() {
		// TODO Auto-generated method stub
		
	}

	private void cookFood() {
		// TODO Auto-generated method stub
		
	}

	private void checkFoodSupply() {
		// TODO Auto-generated method stub
		
	}

	// Accessors, etc.

	public String getName() {
		return name;
	}
/*
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
/*
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
