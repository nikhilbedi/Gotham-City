package restaurant2;

import restaurant2.Menu;
import restaurant2.gui.CustomerGui;
import restaurant2.gui.RestaurantGui;
import restaurant2.interfaces.Cashier;
import restaurant2.interfaces.Customer;
import restaurant2.interfaces.Host;
import restaurant2.interfaces.Waiter;
//import restaurant.WaiterAgent;
import agent.Agent;
import agent.Role;

import java.util.Timer;
import java.util.TimerTask;
import java.util.Random;

/**
 * Restaurant customer agent.
 */
public class CustomerAgent extends Role implements Customer{
	private String name, choice;
	private int hungerLevel = 5;        // determines length of meal
	private int seatNumber, waitingNumber;        // determines length of meal
	Timer timer = new Timer();
	private CustomerGui customerGui;
	double cash, debt;
	boolean gotCheck = false, payForMeal = true;
	Menu menu;
	Check check;

	// agent correspondents
	private Host host;
	private Cashier cashier;
	private Waiter waiter;
	
	//    private boolean isHungry = false; //hack for gui
	public enum AgentState
	{DoingNothing, WaitingInRestaurant, BeingSeated, Seated, Choosing, waitingToOrder, askedToOrder, Ordering, waitingForFood, Eating, DoneEating, Leaving, GoingToPay, Paying};
	private AgentState state = AgentState.DoingNothing;//The start state
	
	public enum AgentEvent 
	{none, gotHungry, followWaiter, seated, doneChoosing, aboutToOrder, doneOrdering, gotFood, doneEating, doneLeaving, donePaying, gotCheck, atCashier};
	AgentEvent event = AgentEvent.none;

	/**
	 * Constructor for CustomerAgent class
	 *
	 * @param name name of the customer
	 * @param gui  reference to the customergui so the customer can send it messages
	 */
	public CustomerAgent(String name){
		super();
		cash = 20;
		debt = 0;
		this.name = name;
	}

	/**
	 * hack to establish connection to Host agent.
	 */
	public void setHost(Host host) {
		this.host = host;
	}
	
	public void setCashier(Cashier c) {
		cashier = c;
	}
	
	public String getCustomerName() {
		return name;
	}
	
	public void setCash(double c) {
		cash = c;
	}
	
	public double getCash() {
		return cash;
	}
	
	public int getWaitingNumber() {
		return waitingNumber;
	}
	
	public void payForMeal(boolean b) {
		payForMeal = b;
	}
	
	// Messages

	public void gotHungry() {//from animation
		print("I'm hungry");
		event = AgentEvent.gotHungry;
		stateChanged();
	}
	
	public void msgWaitHere(int i) {
		customerGui.goToWaitingPosition(i);
		waitingNumber = i;
	}
	
	public void msgFollowMe(Menu m, Waiter w) {
		print("Received msgFollowMe");
		waiter = w;
		menu = m;
	}
	
	public void msgSitAtTable(int tableNumber) {
		print("Received msgSitAtTable");
		seatNumber = tableNumber;
		event = AgentEvent.followWaiter;
		stateChanged();
	}

	public void msgWhatWouldYouLike() {
		print("Received msgWhatWouldYouLike");
		event = AgentEvent.aboutToOrder;
		stateChanged();
	}
	
	public void msgOutOfChoice(Menu m) {
		print("Received msgOutOfChoice");
		menu = m;
		state = AgentState.Choosing;
		chooseFood();
	}
	
	public void msgHereIsYourFood(String choice) {
		print("Got my " + choice);
		event = AgentEvent.gotFood;
		stateChanged();
	}
	
	public void msgHereIsYourCheck(Check c) {
		// TODO Auto-generated method stub
		print("Got my check for $" + c.getTotal());
		check = c;
		print("I have $" + cash);
		//event = AgentEvent.gotCheck;
		gotCheck = true;
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
	
	public void msgAnimationFinishedGoToCashier() {
		// TODO Auto-generated method stub
		event = AgentEvent.atCashier;
		stateChanged();
	}
	
	/**
	 * Scheduler.  Determine what action is called for, and do it.
	 */
	protected boolean pickAndExecuteAnAction() {
		//	CustomerAgent is a finite state machine
		
		if (state == AgentState.DoingNothing && event == AgentEvent.gotHungry ){
			state = AgentState.WaitingInRestaurant;
			goToRestaurant();
			return true;
		}
		
		if (state == AgentState.WaitingInRestaurant && event == AgentEvent.followWaiter ){
			state = AgentState.BeingSeated;
			SitDown();
			return true;
		}
		
		if (state == AgentState.BeingSeated && event == AgentEvent.seated){
			state = AgentState.Choosing;
			timer.schedule(new TimerTask() {
				Object cookie = 1;
				public void run() {
					chooseFood();
				}
			},
			1000);
			return true;
		}
		
		if (state == AgentState.Choosing && event == AgentEvent.doneChoosing){
			state = AgentState.waitingToOrder;
			readyToOrder();
			customerGui.setString(choice.substring(0,2).toUpperCase() + "?");
			customerGui.showString(true);
			return true;
		}
		
		if (state == AgentState.waitingToOrder && event == AgentEvent.aboutToOrder){
			state = AgentState.Ordering;
			orderFood();
			
			return true;
		}
		
		if(state == AgentState.Ordering && event == AgentEvent.doneOrdering){
			waiter.msgHereIsMyOrder(choice, this);
			state = AgentState.waitingForFood;
			customerGui.showString(false);
			return true;
		}
		
		if (state == AgentState.waitingForFood && event == AgentEvent.gotFood) {
			state = AgentState.Eating;
			customerGui.setString(choice.substring(0,2).toUpperCase());
			customerGui.showString(true);
			EatFood();
			return true;
		}
		
		if (state == AgentState.Eating && event == AgentEvent.doneEating && gotCheck){
			//state = AgentState.Leaving;
			state = AgentState.GoingToPay;
			customerGui.showString(false);
			leaveTable();
			return true;
		}
		
		if(state == AgentState.GoingToPay && event == AgentEvent.atCashier) {
			state = AgentState.Paying;
			payForFood();
			return true;
		}
		
		if(state == AgentState.Paying && event == AgentEvent.donePaying) {
			state = AgentState.Leaving;
			leaveRestaurant();
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
		Do("Going to restaurant");
		host.msgIWantFood(this);//send our instance, so he can respond to us
	}

	private void SitDown() {
		Do("Being seated. Going to table");
		customerGui.DoGoToSeat(seatNumber);//hack; only one table
	}
	
	private void chooseFood() {
		Do("Choosing Food");
		if(menu.getNumFoods() == 4)
			switch(name) {
				case "Steak": choice = "Steak"; break;
				case "Chicken": choice = "Chicken"; break;
				case "Pizza": choice = "Pizza"; break;
				case "Salad": choice = "Salad"; break;
				default:
					while(true) {
						Random rand = new Random(System.currentTimeMillis());
						int  n = rand.nextInt(menu.getNumFoods());
						if(menu.getPrice(menu.getFood(n)) > cash && payForMeal) {
							print(menu.getFood(n) + " is too expensive. Checking menu again.");
							menu.removeFood(menu.getFood(n));
							if(menu.getNumFoods()==0) {
								print("Food too expensive. Leaving restaurant.");
								leaveRestaurant();
								state = AgentState.Leaving;
								waiter.msgDoneAndLeaving(this);
								break;
							}
							continue;
						}
						else {
							choice = menu.getFood(n);
							break;
						}
					}
		}
		else {
			Random rand = new Random(System.currentTimeMillis());
			int  n = rand.nextInt(menu.getNumFoods());
			choice = menu.getFood(n);
		}
		
		//This next complicated line creates and starts a timer thread.
		//We schedule a deadline of getHungerLevel()*1000 milliseconds.
		//When that time elapses, it will call back to the run routine
		//located in the anonymous class created right there inline:
		//TimerTask is an interface that we implement right there inline.
		//Since Java does not all us to pass functions, only objects.
		//So, we use Java syntactic mechanism to create an
		//anonymous inner class that has the public method run() in it.
		timer.schedule(new TimerTask() {
			Object cookie = 1;
			public void run() {
				print("Done choosing. Chose " + choice + ", cookie=" + cookie);
				event = AgentEvent.doneChoosing;
				//isHungry = false;
				stateChanged();
			}
		},
		5000);
	}
	
	private void readyToOrder() {
		Do("Ready to Order!");
		waiter.msgReadyToOrder(this);
	}
	
	private void orderFood() {
		Do("Ordering " + choice);
		timer.schedule(new TimerTask() {
			Object cookie = 1;
			public void run() {
				print("Done ordering, cookie=" + cookie);
				event = AgentEvent.doneOrdering;
				//isHungry = false;
				stateChanged();
			}
		},
		2000);
		//waiter.msgHereIsMyOrder(choice, this);
	}
	
	private void EatFood() {
		Do("Eating Food");
		//This next complicated line creates and starts a timer thread.
		//We schedule a deadline of getHungerLevel()*1000 milliseconds.
		//When that time elapses, it will call back to the run routine
		//located in the anonymous class created right there inline:
		//TimerTask is an interface that we implement right there inline.
		//Since Java does not all us to pass functions, only objects.
		//So, we use Java syntactic mechanism to create an
		//anonymous inner class that has the public method run() in it.
		timer.schedule(new TimerTask() {
			Object cookie = 1;
			public void run() {
				print("Done eating, cookie=" + cookie);
				event = AgentEvent.doneEating;
				//isHungry = false;
				stateChanged();
			}
		},
		10000);
	}

	private void leaveTable() {
		Do("Paying.");
		//host.msgLeavingTable(this);
		waiter.msgDoneAndLeaving(this);
		customerGui.DoGoToCashier();
		//customerGui.DoExitRestaurant();
	}
	
	private void payForFood() {
		Do("Paying for food.");
		Do("I owe $" + check.moneyOwed + " + a debt of $" + debt);
		cashier.msgHeresMyCheck(this, check, debt);
		timer.schedule(new TimerTask() {
			Object cookie = 1;
			public void run() {
				print("Done paying, cookie=" + cookie);
				event = AgentEvent.donePaying;
				//isHungry = false;
				stateChanged();
			}
		},
		5000);
	}
	
	private void leaveRestaurant() {
		Do("Leaving.");
		cash = 30;
		customerGui.DoExitRestaurant();
	}
	
	// Accessors, etc.

	public String getName() {
		return name;
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

	public void setGui(CustomerGui g) {
		customerGui = g;
	}

	public CustomerGui getGui() {
		return customerGui;
	}
	
	@Override
	public void addDebt(double d) {
		debt = d;
	}

	@Override
	public double getDebt() {
		// TODO Auto-generated method stub
		return debt;
	}

	
}

