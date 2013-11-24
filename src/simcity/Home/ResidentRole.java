package simcity.Home;

//import restaurant.WaiterAgent.Menu;
import simcity.Home.gui.ResidentGui;
import simcity.Home.interfaces.Resident;
import Gui.RoleGui;
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
import simcity.PersonAgent.HungerState;
import simcity.PersonAgent.RentBill;
import simcity.PersonAgent.RentState;

/**
 * Home Resident Role
 */
public class ResidentRole extends Role implements Resident {
	private String name;
	Timer timer = new Timer();

	private ResidentGui residentGui;
	// public Food foodChoice;
	String type;
	public PersonAgent person;
	// public PersonAgent accountHolder;
	private Home home;
	private double wallet;
	public Map<String, Food> fridgeFoods = new HashMap<String, Food>();
	public Map<String, Food> foods = new HashMap<String, Food>();
	public Map<String, Integer> groceryList = new HashMap<String, Integer>();
	public Map<String, Integer> groceryBag = new HashMap<String, Integer>();
	private List<RentBill> rentBills = new ArrayList<RentBill>();

	// public List<FoodChoice> cookingList;

	public Random random = new Random();
	public boolean leaveRestaurant = random.nextBoolean();

	// private String choice;

	// hack for gui
	public enum HomeState {
		DoingNothing, CheckingFoodSupply, Cooking, Plating, Eating, Clearing, LeavingHome, GoingToBed, Sleeping, PayingRent, GoingToFridge, checkingMailbox, GoingToMailbox, checkingGroceryBag, PuttingGroceriesInFridge
	};

	private HomeState state = HomeState.DoingNothing;// The start state

	public enum HomeEvent {
		none, gotHungry, collectedIngredients, checkedEmptyFridge, doneCooking, donePlating, doneEating, doneClearing, gotSleepy, doneSleeping, payRent, atFridge, checkMailbox, atMailbox, checkGroceryBag, putGroceriesInFridge
	};

	HomeEvent event = HomeEvent.none;

	/**
	 * Constructor for CustomerHome class
	 * 
	 * @param name
	 *            name of the customer
	 * @param gui
	 *            reference to the customergui so the customer can send it
	 *            messages
	 */
	public ResidentRole(PersonAgent p) {
		super(p);

		//myPerson = p;
		name = myPerson.name;
		residentGui = (ResidentGui)super.gui;

		Food f = new Food("Chicken");
		foods.put("Chicken", f);

		f = new Food("Steak");
		foods.put("Steak", f);

		f = new Food("Pizza");
		foods.put("Pizza", f);

		f = new Food("Salad");
		foods.put("Salad", f);

	}

	public ResidentRole() {
		Food f = new Food("Chicken");
		foods.put("Chicken", f);

		f = new Food("Steak");
		foods.put("Steak", f);

		f = new Food("Pizza");
		foods.put("Pizza", f);

		f = new Food("Salad");
		foods.put("Salad", f);
		
		residentGui = (ResidentGui)super.gui;

	}
	@Override
	public void startBuildingMessaging(){
		msgCheckMailbox(); //message to start Home gui
		
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

	public void gotHungry() {// from animation
		System.out.println("I'm hungry");
		event = HomeEvent.gotHungry;
		stateChanged();
	}

	public void gotSleepy() {
		System.out.println("I'm sleepy");
		event = HomeEvent.gotSleepy;
		stateChanged();
	}
	public void wakeUp() {
		System.out.println("Good morning");
		//event = HomeEvent.;
		//stateChanged();
	}

	public void msgCheckMailbox() {
		System.out.println("check your mailbox for mail");
		event = HomeEvent.checkMailbox;
		stateChanged();
	}

	public void msgCheckGroceryBag() {
		event = HomeEvent.checkGroceryBag;
		stateChanged();
	}

	public void AtTable() {
		residentGui.eating = true;
		event = HomeEvent.doneEating;
		stateChanged();

	}

	public void atSink() {
		residentGui.clearing = true;
		event = HomeEvent.doneClearing;
		stateChanged();

	}

	public void atPlatingArea() {
		residentGui.plating = true;
		DoPlating();
		// event = HomeEvent.donePlating;
		// stateChanged();

	}

	public void atStove() {
		residentGui.cooking = true;
		Food myChoice = new Food(type);
		DoCooking(myChoice); // cooking timer

	}

	public void atBed() {
		residentGui.sleeping = true;
		event = HomeEvent.doneSleeping;
		stateChanged();

	}

	public void atFridge() {
		event = HomeEvent.atFridge;
		stateChanged();

	}

	public void atMailbox() {

		event = HomeEvent.atMailbox;
		stateChanged();

	}

	public void atHome() {
		event = HomeEvent.none;
		stateChanged();
	}

	public void exited() {
		// person.leftBuilding();

	}

	// @Override
	public void doLeaveBuilding() {

	}

	/**
	 * Scheduler. Determine what action is called for, and do it.
	 */
	@Override
	public boolean pickAndExecuteAnAction() {
		// CustomerHome is a finite state machine
		// System.out.println("Calling resident scheduler");

		// if (state == HomeState.DoingNothing && event == HomeEvent.none){
		// returnToHomePosition();
		// return true;
		// }
		if(myPerson.hungerState == HungerState.FeedingHunger) {
			gotHungry();
		}
		
		if (state == HomeState.DoingNothing && event == HomeEvent.checkMailbox) {
			state = HomeState.GoingToMailbox;
			goToMailbox();
			return true;
		}
		if (state == HomeState.GoingToMailbox && event == HomeEvent.atMailbox) {
			state = HomeState.checkingMailbox;
			checkMail();
			return true;
		}
		if (state == HomeState.checkingMailbox && event == HomeEvent.none) {
			state = HomeState.DoingNothing;
			returnToHomePosition();
			return true;
		}
		if (state == HomeState.checkingMailbox && event == HomeEvent.payRent) {
			state = HomeState.PayingRent;
			payRent(home.getRentBills());
			// payRent(myPerson.new RentBill(myPerson, 10));
			// payRent(new RentBill(myPerson, 10));
			// payRent(rb);
			return true;
		}
		if (state == HomeState.PayingRent && event == HomeEvent.none) {
			state = HomeState.DoingNothing;
			returnToHomePosition();
			return true;
		}
		
		//if (person.groceryBag.size() >0){
		//	msgCheckGroceryBag();
		//}
		
		if (state == HomeState.DoingNothing
				&& event == HomeEvent.checkGroceryBag) {
			state = HomeState.checkingGroceryBag;
			checkGroceryBag();
			return true;
		}
		if (state == HomeState.checkingGroceryBag && event == HomeEvent.none) {
			state = HomeState.DoingNothing;
			returnToHomePosition();
			return true;
		}
		if (state == HomeState.checkingGroceryBag
				&& event == HomeEvent.putGroceriesInFridge) {
			state = HomeState.PuttingGroceriesInFridge;
			// putGroceriesInFridge(person.getGroceryBag());
			return true;
		}
		if (state == HomeState.PuttingGroceriesInFridge
				&& event == HomeEvent.atFridge) {
			state = HomeState.DoingNothing;
			returnToHomePosition();
			return true;
		}
		
		if (state == HomeState.DoingNothing && event == HomeEvent.gotHungry) {
			// System.out.println("CHECking food supply");
			state = HomeState.GoingToFridge;
			checkFridge();
			return true;
		}
		if (state == HomeState.GoingToFridge && event == HomeEvent.atFridge) {
			// System.out.println("CHECking food supply");
			state = HomeState.CheckingFoodSupply;
			type = checkFoodSupply();
			return true;
		}
		if (state == HomeState.DoingNothing && event == HomeEvent.gotSleepy) {
			state = HomeState.Sleeping;
			goToBed();
			return true;
		}
		if (state == HomeState.CheckingFoodSupply
				&& event == HomeEvent.collectedIngredients) {
			state = HomeState.Cooking;
			cookFood(type);
			return true;
		}
		if (state == HomeState.Cooking && event == HomeEvent.doneCooking) {
			state = HomeState.Plating;
			plateFood();
			return true;
		}
		if (state == HomeState.Plating && event == HomeEvent.donePlating) {
			tryEatFood();
			return true;
		}
		if (state == HomeState.Eating && event == HomeEvent.doneEating) {
			// state = HomeState.Clearing;
			tryClearFood();
			return true;
		}
		if (state == HomeState.Clearing && event == HomeEvent.doneClearing) {
			state = HomeState.DoingNothing;
			returnToHomePosition();
			return true;
		}
		if (state == HomeState.CheckingFoodSupply
				&& event == HomeEvent.checkedEmptyFridge) {
			state = HomeState.DoingNothing; // LeavingRestaurant
			exitHome();
			return true;
		}
		/*
		 * if (state == HomeState.CheckingFoodSupply && event ==
		 * HomeEvent.checkedEmptyFridge){ state = HomeState.DoingNothing;
		 * //LeavingRestaurant exitHome(); return true; }
		 */
		if (state == HomeState.LeavingHome && event == HomeEvent.none) {
			state = HomeState.DoingNothing;
			returnToHomePosition();
			return true;
		}

		return false;
	}

	// Actions

	private void putGroceriesInFridge(Map<String, Integer> groceryBag) {
		residentGui.DoGoToFridge();
		fridgeFoods.get("Steak").setAmount(
				fridgeFoods.get("Steak").getAmount() + groceryBag.get("Steak"));
		groceryBag.put("Steak", 0);
		fridgeFoods.get("Chicken").setAmount(
				fridgeFoods.get("Chicken").getAmount()
						+ groceryBag.get("Chicken"));
		groceryBag.put("Chicken", 0);
		fridgeFoods.get("Pizza").setAmount(
				fridgeFoods.get("Pizza").getAmount() + groceryBag.get("Pizza"));
		groceryBag.put("Pizza", 0);
		fridgeFoods.get("Salad").setAmount(
				fridgeFoods.get("Salad").getAmount() + groceryBag.get("Salad"));
		groceryBag.put("Salad", 0);

	}

	private void checkGroceryBag() {
		if (groceryBag.size() > 0) {
			event = HomeEvent.putGroceriesInFridge;
			stateChanged();
		} else
			event = HomeEvent.none;
		stateChanged();

	}

	private void goToMailbox() {
		residentGui.DoGoToMailbox();

	}

	public void checkMail() {
		if (rentBills.size() > 0) {
			event = HomeEvent.payRent;
			stateChanged();
		}

		else {
			event = HomeEvent.none;
			stateChanged();
		}
	}

	public void payRent(List<RentBill> rentBills) {
		for (RentBill rb : rentBills) {
			if (rb.state == RentState.NotPaid) {
				myPerson.goPayBill(rb);
			}
		}
		event = HomeEvent.none;
		stateChanged();
	}

	private void goToBed() {
		residentGui.DoGoToBed();
		DoSleeping();
	}

	private void returnToHomePosition() {
		residentGui.DoReturnToHomePosition();
		event = HomeEvent.none;
		stateChanged();
	}

	private void tryClearFood() {
		System.out.println("resident cleaning plates");
		residentGui.DoClearFood();
		timer.schedule(new TimerTask() {
			public void run() {
				System.out.println("resident done cleaning");
				clearFood();
			}
		}, 5000);
		residentGui.clearing = false;
	}

	private void clearFood() {
		System.out.println("resident cleaning plates");
		state = HomeState.Clearing;
		stateChanged();

	}

	private void tryEatFood() {
		System.out.println("resident eating Food");
		// residentGui.waitingForFood=false;
		// residentGui.receivedFood=true;
		residentGui.DoGoToTable();
		timer.schedule(new TimerTask() {
			public void run() {
				// residentGui.eating = true;
				System.out.println("resident done eating");
				eatFood();
			}
		}, 5000);
		residentGui.eating = false;
		myPerson.justAte();
		// myPerson.hungerState = HungerState.NotHungry;
		// stateChanged();
		// customerGui.receivedFood=false;
		// isHungry = false;
	}

	private void eatFood() {
		state = HomeState.Eating;
		stateChanged();
	}

	private void plateFood() {
		System.out.println("resident plating Food");
		residentGui.DoGoToPlatingArea();
		// DoPlating();
	}

	private void cookFood(String type) {
		System.out.println("resident cooking Food");
		residentGui.DoGoToStove();
	}

	private void checkFridge() {
		residentGui.DoGoToFridge();
		System.out.println("checking food supply");
	}

	private String checkFoodSupply() {
		String choice = randomizeFoodChoice();
		// String choice = "food";
		Food f = fridgeFoods.get(choice);

		if (checkInventory(f)) {
			// print("cook is cooking the food");
			int amount = f.getAmount() - 1;
			f.setAmount(amount);
			// DoCooking(o);
			// print(this.getName() + " is cooking the food");
			event = HomeEvent.collectedIngredients;
			stateChanged();
			person.hungerState = HungerState.FeedingHunger;
			stateChanged();
			// CookGui.order = o.choice.getType();
			// CookGui.tableNumber = o.tableNumber;
			// CookGui.cooking = true;
			// CookGui.plating = false;

			// check low threshold
			if (amount <= f.getLowThreshold()) {
				addToGroceryList(f);
			}
		} else {
			event = HomeEvent.checkedEmptyFridge;
			// o.outOfStock = true;
			System.out.println(this.getName() + " has run out of " + choice);
			addToGroceryList(f);
			stateChanged();
		}

		return choice;

	}

	private void exitHome() {
		residentGui.DoExitHome();
		event = HomeEvent.none;
		stateChanged();

	}

	// TODO Fix this Evan
	// Accessors, etc.
	private void DoSleeping() {
		int bedTime = 6; // 8am
		int alarmTime = 22;
		// try {
		// Thread.currentThread().sleep(10000);
		// } catch(Exception e) {
		// System.out.println(e.getMessage());0
		// }
	}

	private void DoCooking(Food f) {
		System.out.println("Do Cooking");

		int cookingTime = f.getCookingTime();
		timer.schedule(new TimerTask() {
			public void run() {
				event = HomeEvent.doneCooking;
				stateChanged();
			}

		}, cookingTime * 500);
		residentGui.cooking = false;
	}

	private void DoPlating() {
		System.out.println("Do plating");
		timer.schedule(new TimerTask() {
			public void run() {
				event = HomeEvent.donePlating;
				stateChanged();
			}

		}, 5000);
		residentGui.plating = false;
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
		groceryList.put(f.getType(), f.getAmount());
		myPerson.homeNeedsGroceries(groceryList);
	}

	public String getName() {
		return name;
	}

	private String randomizeFoodChoice() {
		String choice = null;

		Random r = new Random();
		int x;

		x = r.nextInt(4) + 1;

		if (x == 1) {
			choice = "Salad";
			System.out.println("choice = Salad");
		}
		if (x == 2) {
			choice = "Pizza";
			System.out.println("choice = pizza");
		}
		if (x == 3) {
			choice = "Chicken";
			System.out.println("choice = Chicken");
		}
		if (x == 4) {
			choice = "Steak";
			System.out.println("choice = Steak");
		}

		return choice;
	}

	/*
	 * public int getHungerLevel() { return hungerLevel; } public void
	 * setDonePayingState() { event = HomeEvent.donePaying; stateChanged(); }
	 * 
	 * /* //public String toString() { //return "customer " + getName(); //}
	 */
	public void setGui(RoleGui g) {
		super.setGui(g);
		residentGui = (ResidentGui)g;
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

		public FoodChoice(String foodChoice) {

			this.choice = new Food(foodChoice);
			// this.totalPrice = totalPrice;
		}

		public void setTotalPrice(double t) {
			totalPrice = t;
		}

	}

}
