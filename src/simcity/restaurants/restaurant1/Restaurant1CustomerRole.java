package simcity.restaurants.restaurant1;

import simcity.PersonAgent;
import simcity.TheCity;
import simcity.restaurants.restaurant1.CashierRole.Check;
import simcity.restaurants.restaurant1.WaiterRole;
import simcity.restaurants.restaurant1.HostRole;
import simcity.restaurants.restaurant1.Menu;
import simcity.restaurants.restaurant1.gui.Restaurant1CustomerGui;
import simcity.restaurants.restaurant1.interfaces.*;
import Gui.RoleGui;
import agent.Role;

import java.util.Timer;
import java.util.TimerTask;
import java.util.Random;

/**
 * Restaurant customer agent.
 */
public class Restaurant1CustomerRole extends Role implements Customer {
	private String name;
	private int hungerLevel = 5;        // determines length of meal
	//which table to sit at
	private int openTable = 1;
	Timer timer = new Timer();
	private Restaurant1CustomerGui customerGui;
	private String myChoice;
	private Menu myMenu;
	//private double money;
	private Check myCheck;
	private int waitingPosX = 100;
	private int waitingPosY = 100;

	// agent correspondents
	private Waiter myWaiter;
	private HostRole host;
	private Cashier cashier;

	// private boolean isHungry = false; //hack for gui
	public enum AgentState
	{DoingNothing, WaitingInRestaurant, WaitingInArea, BeingSeated, Seated, calledWaiter, Ordering, WaitingForOrder, Eating, DoneEating, Paying, Leaving};
	private AgentState state = AgentState.DoingNothing;//The start state

	public enum AgentEvent 
	{none, gotHungry, mustWaitInArea, followWaiter, seated, thoughtOfOrder, askedForOrder, gaveOrder, receivedFood, doneEating, receivedBill, reachedCashier, 
		doneLeaving, leavingRestaurant, left};
	AgentEvent event = AgentEvent.none;

	/**
	 * Constructor for CustomerAgent class
	 *
	 * @param name name of the customer
	 * @param gui  reference to the customergui so the customer can send it messages
	 */
	public Restaurant1CustomerRole(PersonAgent p){
		super(p);
		//myChoice = name;
		//Set how much cash the customer has
		Random rnd = new Random();
		int max = 25;
		int min = 4;
	//	money = rnd.nextInt(max-min) + min;
		name = getPersonAgent().getName();
		/*	if(name.equals("flake")) {
		    money = 4.0;
		}*/
	}
	
	public Restaurant1CustomerRole() {
		Random rnd = new Random();
		int max = 25;
		int min = 4;
		//money = rnd.nextInt(max-min) + min;
	}

	/**
	 * hack to establish connection to Host agent.
	 */
	public void setHost(HostRole host) {
		this.host = host;
	}

	public void setCashier(CashierRole cashier) {
		this.cashier = cashier;
	}

	//Is this allowed?
	public boolean isWaiting() {
		if(state == AgentState.WaitingInRestaurant)
			return true;
		return false;
	}

	public String getCustomerName() {
		return name;
	}


	// Messages

	@Override
	public void startBuildingMessaging(){
		//Set host and cashier
		//host = (HostRole) myPerson.currentPreference.getHost();
		//cashier = (CashierRole) myPerson.currentPreference.getCashier();
		//host = (Role)((Restaurant1) TheCity.getBuildingFromString("Restaurant 1")).getHost());
		host = (HostRole) ((Restaurant1)TheCity.getBuildingFromString("Restaurant 1")).getHost();
		if(host.checkWorkStatus())
			gotHungry();
		else
			myPerson.leftBuilding(this);
	}
	
	/**
       handles the hungry message, in which the event will cause this customer to message the host
	 */
	public void gotHungry() {//from animation
		print("I'm hungry - received from GUI");
		event = AgentEvent.gotHungry;
		stateChanged();
	}

	public void msgAnimationFinishedGoToSeat() {
		//from animation
		event = AgentEvent.seated;
		stateChanged();
	}
	public void msgAnimationFinishedLeaveRestaurant() {
		//from animation
		event = AgentEvent.doneLeaving;
		stateChanged();
	}
	
	public void msgAnimationFinishedWaitingArea() {
		//from animation - Just manually send host message.
		host.inWaitingPosition();
	}

	public void waitInArea(int posX, int posY){
		print("got a message to wait at coordinates " + posX + " " + posY);
		event = AgentEvent.mustWaitInArea;
		waitingPosX = posX;
		waitingPosY = posY;
		stateChanged();
	}

	/**
       handles waiter's follow me message and eventually sits down at the correct table (the coordinates are passed from the waitergui to customer gui)
	 */
	public void followMe(Waiter w, Menu m) {
		print("I am following waiter");
		myWaiter = w;
		myMenu = m;
		event = AgentEvent.followWaiter;
		stateChanged();
	}

	/**
       handles waiter's message about what customer wants
	 */
	public void whatDoYouWant() {
		print("Waiter has asked me What I Want.");
		event = AgentEvent.askedForOrder;
		stateChanged();
	}

	public void hereIsYourFood(String order) {
		if(myChoice == order){
			print("Waiter has given me my food: " + order);
			event = AgentEvent.receivedFood;
		} 
		stateChanged();
	}

	public void orderNotAvailable(Menu m) {
		print("My order of " + myChoice + " isn't not available? What a lousy restaurant...");
		state = AgentState.BeingSeated;
		event = AgentEvent.seated;
		myMenu = m;
		stateChanged();
	}

	public void hereIsBill(Check ch) {
		myCheck = ch;
		event = AgentEvent.receivedBill;
		stateChanged();
		//Cause event to launch him to get up and go pay the cashier (since he's most likely done eating
	}

	//message from gui to tell when the customer has made it to the cashier
	public void madeItToCashier() {
		print("Made it to Cashier.");
		//	state = AgentState.Leaving;
		event = AgentEvent.reachedCashier;
		stateChanged();
	}

	public void hereIsChange(double amount) {
		//If the amount is greater than zero, then the customer receives how much money he has left
		myPerson.setMoney(amount);
		print("Cashier gave me change. I have " + myPerson.getMoney() + " money left.");
		//If he is in debt, then cash him up so he can pay next time
		if(myPerson.getMoney() < 0 ) {
			myPerson.addMoney(30);
			print("Since I was in debt and God likes to speak to me, I was given 30 dollars to clear my payments.");
		}
		event = AgentEvent.leavingRestaurant;
		stateChanged();
		//We can perhaps have a boolean for a flake and set the boolean true under the above condition
		//we will need to call an action to up this guys cash flow too, remember
	}

	/**
	 * Scheduler.  Determine what action is called for, and do it.
	 */
	//Make sure to implement the new states once you take away the host from the Gui
	public boolean pickAndExecuteAnAction() {
		//	CustomerAgent is a correctly implemented finite state machine
		if (state == AgentState.DoingNothing && event == AgentEvent.gotHungry ){
			state = AgentState.WaitingInRestaurant;
			goToRestaurant();
			return true;
		}

		if(state == AgentState.WaitingInRestaurant && event == AgentEvent.mustWaitInArea) {
			state = AgentState.WaitingInArea;
			goToArea();
			return true;
		}
		if(state == AgentState.WaitingInArea && event == AgentEvent.followWaiter) {
			state = AgentState.BeingSeated;
			goToTable();
			return true;
		}
		if(state == AgentState.BeingSeated && event == AgentEvent.seated) {
			state = AgentState.Seated;
			thinkingOfOrder();
			return true;
		}
		if(state == AgentState.Seated && event == AgentEvent.thoughtOfOrder) {
			callWaiter();
			return true;
		}
		if(state == AgentState.calledWaiter && event == AgentEvent.askedForOrder) {
			state = AgentState.Ordering;
			giveOrder();
			return true;
		}
		if(state == AgentState.Ordering && event == AgentEvent.gaveOrder) {
			state = AgentState.WaitingForOrder;
			return true; 
		}
		if(state == AgentState.WaitingForOrder && event == AgentEvent.receivedFood) {
			state = AgentState.Eating;
			eatFood();
			return true;
		}
		if (state == AgentState.Eating && event == AgentEvent.receivedBill){
			state = AgentState.Leaving;
			leaveTable();
			return true;
		}
		if(state == AgentState.Leaving && event == AgentEvent.reachedCashier) {
			state = AgentState.Paying;
			payCashier();
			return true;
		}
		if(state == AgentState.Paying && event == AgentEvent.leavingRestaurant) {
			state = AgentState.Leaving;
			leaveRestaurant();
			return true;
		}
		/*	if (state == AgentState.Leaving && event == AgentEvent.left) {
	    // state = AgentState.DoingNothing;
	    return true;
	    }*/

		return false;
	}

	// Actions

	private void goToRestaurant() {
		print("Going to restaurant");
		host.msgIWantFood(this);//send our instance, so he can respond to us
		print("Don't you know who I am? I'm " + name + " aka Professor Hata Structures. If I'm not served in fifteen seconds, I'm leaving.");
		//Do a timer to check if we've been waiting for 10 seconds or more
		//if the state is still "waitingInRestaurant", then message and leave
		//else, do nothing
		final Restaurant1CustomerRole temp = this;
		final Waiter w = myWaiter;
		//The requirements don't say that the customer should be allowed to become hungry again since he left the restaurant
		timer.schedule(new TimerTask() {
			public void run() {
				if(state == AgentState.WaitingInRestaurant) {
					print("Y'all be drinking that haterade. I'm out.");
					host.noServiceSoLeft(temp, w);
					state = AgentState.DoingNothing;
					event = AgentEvent.left;
					customerGui.DoExitRestaurant();
				}
			}
		},
		15000);
	}

	private void goToArea(){
		print("Going to waiting position");
		//animation
		customerGui.DoGoToArea(waitingPosX, waitingPosY);

	}

	private void SitDown() {
		print("Being seated. Going to table");
		//	customerGui.DoGoToSeat(openTable);//hack; only one table
	}

	private void goToTable() {
		//message host that waiting area is available
		host.waitingAreaAvailable(this);
		//customerGui.doGoToSeat();
		//state = AgentState.Seated;
		//Timer to wait and call the waiter back?/change state so waiter is called?
	}

	private void thinkingOfOrder() {
		print("thinking of what to order!");
		timer.schedule(new TimerTask() {
			public void run() {
				boolean stillChoosing = true;
				boolean haveEnoughMoney = false;
				//The customer's name might not be flake and might not have enough money for anything. Need to keep check first if he can order anything
			/*	if(!name.equals("flake")) {
					synchronized(myMenu.choices){
						for(String c : myMenu.choices.keySet()) {
							if(money >= myMenu.choices.get(c)) {
								haveEnoughMoney = true;
								break;
							}
						}
					}
					if(!haveEnoughMoney || myMenu.choices.size() == 0) {
						stillChoosing = false;
						state = AgentState.DoingNothing;
						print("Can't order anything! I'll just leave.");
						leaveTableAndRestaurant();
					}
				}*/
				while(stillChoosing) {
					// for(String c : myMenu.choices.keySet()) {
					//     print(c);
					// }
					Random rnd = new Random();
					int maxChoices = myMenu.choices.size();
					int minChoices = 1;
					int randomChoice = rnd.nextInt(maxChoices-minChoices) + minChoices;
					int counter = 0;
					print("Thinking of what to order..");
					synchronized(myMenu.choices){
						for(String c : myMenu.choices.keySet()) {
							++counter;
							if(randomChoice == counter) {
								//Check if the customer can afford it
								//if(money >= myMenu.choices.get(c) && !name.equals("flake")) {
								if(myPerson.getMoney() >= myMenu.choices.get(c)) {
									myChoice = c;
									stillChoosing = false;
									event = AgentEvent.thoughtOfOrder;
									stateChanged();

								}
								else if(getName().equals("flake")) {
									myChoice = c;
									stillChoosing = false;
									event = AgentEvent.thoughtOfOrder;
									stateChanged();

								}
								break;
							}
						}
					}
				}
			}
		},
		5000);
	}

	/**
	 * implements a mechanism to choose what to order.
	 * messages the waiter when ready to order
	 */    
	private void callWaiter() {
		state = AgentState.calledWaiter;
		myWaiter.readyToOrder(this);
	}


	/**
       messages the waiter with an order choice
	 */
	private void giveOrder() {
		print("I have this much money: " + myPerson.getMoney() + " so I'll get " + myChoice);
		myWaiter.hereIsMyChoice(myChoice, this);
		event = AgentEvent.gaveOrder;
	}

	/**
       event occurs to tell customer has food. This function times the eating of food.
       handles cooked food delivery and eventually eats food which is managed by a timer.
	 */
	private void eatFood() {
		print("Eating " + myChoice);
		//This next complicated line creates and starts a timer thread.
		//We schedule a deadline of getHungerLevel()*1000 milliseconds.
		//When that time elapses, it will call back to the run routine
		//located in the anonymous class created right there inline:
		//TimerTask is an interface that we implement right there inline.
		//Since Java does not all us to pass functions, only objects.
		//So, we use Java syntactic mechanism to create an
		//anonymous inner class that has the public method run() in it.
		final Restaurant1CustomerRole roleTemp = this;
		timer.schedule(new TimerTask() {
			Object cookie = 1;
			public void run() {
				print("Done eating "  + myChoice);
				myPerson.justAte();
				myWaiter.readyForCheck(roleTemp);
				//event = AgentEvent.doneEating;
				customerGui.doneWithFood();
				//  isHungry = false;
				stateChanged();
			}
		},
		5000);//getHungerLevel() * 1000);//how long to wait before running task
	}

	/**
       leaves the restaurant when done eating, informing the waiter, goes into the state able to be hungry again and start with the entire process over again.
	 */
	private void leaveTable() {
		print("Leaving.");
		myWaiter.doneAndLeaving(this);
		event = AgentEvent.left;
		//Instead of DoExitRestaurant, it will be DoGoToCashier()
		customerGui.DoGoToCashier();
		//When cashier is reached, pay cashier
		//	stateChanged();
	}

	private void payCashier() {
		print("Paying Cashier");
		cashier.hereIsPayment(myCheck, myPerson.getMoney());
		customerGui.DoExitRestaurant();
		myCheck = null;
	}

	private void leaveRestaurant() {
		event = AgentEvent.left;
		myPerson.leftBuilding(this);
	}



	// Accessors, etc.

	public String getName() {
		return myPerson.getName();
	}

	public int getHungerLevel() {
		return hungerLevel;
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
		customerGui = (Restaurant1CustomerGui)g;
	}

	public Restaurant1CustomerGui getGui() {
		return customerGui;
	}
}

