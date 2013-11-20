package simcity.Home;


//import restaurant.WaiterAgent.Menu;
import simcity.Home.gui.ResidentGui;
import simcity.Home.interfaces.Resident;
import agent.Agent;
import agent.Role;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import simcity.Home.Food;
import simcity.PersonAgent;


/**
 * Restaurant customer agent.
 */
public class ResidentRole extends Role implements Resident{
	private String name;
	Timer timer = new Timer();
	
	private ResidentGui residentGui;
	//public Food foodChoice;
	String type;
	public PersonAgent person;

	private double wallet;
	public List<Food> fridgeFoods= new ArrayList<Food>();
	public Map<String, Food> foods = new HashMap<String, Food>();
	public List<String> groceryList = new ArrayList<String>();
	
	//public List<FoodChoice> cookingList;
	
	public Random random = new Random();
	public boolean leaveRestaurant = random.nextBoolean();
	
	//private String choice;

	 //hack for gui
	public enum HomeState
	{DoingNothing, CheckingFoodSupply,Cooking, Plating, Eating, Clearing, LeavingHome, 
		GoingToBed, Sleeping}; 
	private HomeState state = HomeState.DoingNothing;//The start state

	//other Homestates:Seated, askedToOrder, ordered, DoneEating

	public enum HomeEvent 
	{none, gotHungry, collectedIngredients,checkedEmptyFridge, doneCooking, donePlating, 
		doneEating, doneClearing, gotSleepy, doneSleeping};
	HomeEvent event = HomeEvent.none;
 

	/**
	 * Constructor for CustomerHome class
	 *
	 * @param name name of the customer
	 * @param gui  reference to the customergui so the customer can send it messages
	 */
	public ResidentRole(PersonAgent p){
		super(p);
		
		person = p;
		name = person.name;
		
		Food f = new Food ("Chicken");
		foods.put("Chicken", f);
		
		f = new Food ("Steak");
		foods.put("Steak", f);
		
		f = new Food ("Pizza");
		foods.put("Pizza", f);
		
		f = new Food ("Salad");
		foods.put("Salad", f);
	
	}
	/*public ResidentRole(){
		Food f = new Food ("Chicken");
		foods.put("Chicken", f);
		
		f = new Food ("Steak");
		foods.put("Steak", f);
		
		f = new Food ("Pizza");
		foods.put("Pizza", f);
		
		f = new Food ("Salad");
		foods.put("Salad", f);
	
	}*/
	
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

	public void gotSleepy() {
		System.out.println("I'm sleepy");
		event = HomeEvent.gotSleepy;
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
		stateChanged();
	}

	/**
	 * Scheduler.  Determine what action is called for, and do it.
	 */
	@Override
	public boolean pickAndExecuteAnAction() {
		//	CustomerHome is a finite state machine
		System.out.println("Calling resident scheduler");
		

		if (state == HomeState.DoingNothing && event == HomeEvent.gotHungry ){
			System.out.println("CHECking food supply");
			
			state = HomeState.CheckingFoodSupply;
			checkFoodSupply();
			return true;
		}
		if (state == HomeState.DoingNothing && event == HomeEvent.gotSleepy ){
			state = HomeState.Sleeping;
			goToBed();
			return true;
		}
		if (state == HomeState.CheckingFoodSupply && event == HomeEvent.collectedIngredients){
			state = HomeState.Cooking;
			type = checkFoodSupply();
			cookFood(type);
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
		if (state == HomeState.CheckingFoodSupply && event == HomeEvent.checkedEmptyFridge){
			state = HomeState.LeavingHome;
			goToMarket();
			return true;
		}
		if (state == HomeState.LeavingHome && event == HomeEvent.none){
			state = HomeState.DoingNothing;
			return true;
		}
		
		return false;
	}

	

	// Actions

	private void goToBed() {
		residentGui.DoGoToBed();
		DoSleeping();
		event = HomeEvent.doneSleeping;
		stateChanged();
		
	}

	private void returnToHomePosition() {
		residentGui.DoGoToBed();
		event = HomeEvent.none;
		stateChanged();
		
	}

	private void clearFood() {
		System.out.println("resident cleaning plates");
		residentGui.DoClearFood();
		try {
			Thread.currentThread().sleep(5000);
		}catch(Exception e) {
			System.out.print(e.getMessage());
		}
		System.out.println("resident done cleaning");
		event = HomeEvent.doneClearing;
		stateChanged();
	}

	private void eatFood() {
		System.out.println("resident eating Food");
		//residentGui.waitingForFood=false;
		//residentGui.receivedFood=true;
		residentGui.DoGoToBed();
		try {
			Thread.currentThread().sleep(5000);
		}catch(Exception e) {
			System.out.print(e.getMessage());
		}
		System.out.println("resident done eating");
		event = HomeEvent.doneEating;
		//customerGui.receivedFood=false;
		//isHungry = false;
		stateChanged();
	}

	private void plateFood() {
		System.out.println("resident plating Food");
		residentGui.DoGoToPlatingArea();
		DoPlating();
		event = HomeEvent.donePlating;
		stateChanged();
		
	}

	private void cookFood(String type) {
		System.out.println("resident cooking Food");
		residentGui.DoGoToStove();
		Food myChoice = new Food(type);
		DoCooking(myChoice); //cooking timer
		event = HomeEvent.doneCooking;
		stateChanged();
	}

	private String checkFoodSupply() {
		residentGui.DoGoToFridge();
		System.out.println("checking food supply");
		
		String choice = randomizeFoodChoice();
		
		Food f = foods.get(choice);
			
		if (checkInventory(f)) {
			//print("cook is cooking the food");
			int amount = f.getAmount() - 1;
			f.setAmount (amount);			
			//DoCooking(o);
			//print(this.getName() + " is cooking the food");
			event = HomeEvent.collectedIngredients;
			stateChanged();
			//CookGui.order = o.choice.getType();
			//CookGui.tableNumber = o.tableNumber;
			//CookGui.cooking = true;
			//CookGui.plating = false;
			
			// check low threshold
			if (amount <= f.getLowThreshold()) { 
				addToGroceryList(f);
			}
		}
		else {
			event = HomeEvent.checkedEmptyFridge;
			//o.outOfStock = true;
			System.out.println(this.getName() + " has run out of " + choice);
			stateChanged();
		}
		
		return choice;
	}
	
	private void goToMarket() {
		residentGui.DoExitHome();
		event = HomeEvent.none;
		stateChanged();
		
	}


	// Accessors, etc.
	private void DoSleeping() {
		try {
			Thread.currentThread().sleep(10000);
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	private void DoCooking(Food f) {
		System.out.println("Do Cooking");

		int cookingTime = f.getCookingTime();
		try {
			Thread.currentThread().sleep(cookingTime*250);
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	private void DoPlating() {
		try {
			//CookGui.cooking = false;
			//CookGui.plating = true;
			Thread.currentThread().sleep(2000);
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	private boolean checkInventory(Food f) {
		System.out.println("checking Fridge Inventory");
		int amount = f.getAmount(); 
		if (amount > 0) {
			return true;
		}
		return false;
	}
	
	private void addToGroceryList(Food f) {
		 groceryList.add(f.getType());
	}

	public String getName() {
		return name;
	}
	
	private String randomizeFoodChoice() {
		String choice = null;
		
		Random r = new Random();
		int x;
		
		x = r.nextInt(4) + 1;
		
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
/*
	public int getHungerLevel() {
		return hungerLevel;
	}
	public void setDonePayingState() {
		event = HomeEvent.donePaying;
		stateChanged();
	}

/*
	//public String toString() {
		//return "customer " + getName();
	//}



	*/
	public void setGui(ResidentGui g) {
		residentGui = g;
	}

	public ResidentGui getGui() {
		return residentGui;
	}
	
	public double getWalletAmount() {
		return wallet;
	}
	
	public class FoodChoice {
		public Food choice;
		double totalPrice;
		boolean outOfStock = false;
		
		public FoodChoice(String foodChoice){
			
			this.choice = new Food(foodChoice);
			//this.totalPrice = totalPrice;
		}
		
		 public void setTotalPrice(double t) {
			totalPrice = t;
		}
			
	}
}
