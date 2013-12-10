package simcity.restaurants.restaurant5;

import Gui.RoleGui;
import agent.Agent;
import agent.Role;

import java.util.*;
import java.util.concurrent.Semaphore;
import java.util.Collections;
import java.util.Timer;
import java.util.Map;
import java.util.TimerTask;

import simcity.TheCity;
import simcity.restaurants.restaurant4.Restaurant4CashierRole;
import simcity.restaurants.restaurant4.Restaurant4HostRole;
import simcity.restaurants.restaurant4.Restaurant4Gui.Restaurant4CustomerGui;
import simcity.restaurants.restaurant5.interfaces.*;
import simcity.restaurants.restaurant5.gui.*;
import agent.Agent;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Semaphore;


/**
 * Restaurant customer agent.
 */
public class Restaurant5CustomerRole extends Role implements Customer {
	private int hungerLevel = 5;        // determines length of meal
	private int table;	//hack will be removed
	Timer timer = new Timer();
	private Restaurant5CustomerGui customerGui;

	Menu menu;

	String choice;

	//temporary map variable will likely be replaced by new class later

	private Semaphore moving = new Semaphore(0,true);

	// agent correspondents
	private Waiter waiter =  null;
	private HostRole host;
	private CashierRole cashier;


	double check = 0.0;
	boolean flake = false;

	//    private boolean isHungry = false; //hack for gui
	public enum CustomerState
	//{DoingNothing, WaitingInRestaurant, BeingSeated, Seated, Eating, DoneEating, Leaving};
	{NotHungry, Hungry, Waiting, ChoosingOrder, GoingToTable, GivingOrder, Eating, Done, Leaving, Left,
		//v2.1
		WaitOnCheck, Paying};

		public enum AgentEvent{
			followWaiter, seated, hasOrder, hasFood, doneEating, gotHungry, start, eating, waiting, ordering,
			//v2.1
			noLongerHungry, cantOrder, gotCheck, paid

		};
		private CustomerState s = CustomerState.NotHungry;//The start state
		private AgentEvent e = AgentEvent.start;

		/**
		 * Constructor for CustomerAgent class
		 *
		 * @param name name of the customer
		 * @param gui  reference to the customergui so the customer can send it messages
		 */
		public Restaurant5CustomerRole(String name){
			super();
		}

		public Restaurant5CustomerRole() {
			super();
			customerGui  = (Restaurant5CustomerGui)super.gui;
		}

		/**
		 * hack to establish connection to Host agent.
		 */
		

		public String getCustomerName() {
			return getName();
		}
		// Messages
		@Override
		public void startBuildingMessaging(){
/*			host =  (HostRole) myPerson.currentPreference.getHost();
			cashier = (CashierRole) myPerson.currentPreference.getCashier();
			becomesHungry();*/
			
			host = (HostRole) ((Restaurant5)TheCity.getBuildingFromString("Restaurant 5")).getHost();
			cashier = (CashierRole) ((Restaurant5)TheCity.getBuildingFromString("Restaurant 5")).getCashier();
			if(((HostRole) host).checkWorkStatus() && ((CashierRole) cashier).checkWorkStatus()) {
				becomesHungry();
			}
			else {
				myPerson.leftBuilding(this);
			}
		}
		public void becomesHungry() {//from animation
			print( getName() + " is hungry");
			e = AgentEvent.gotHungry;
			stateChanged();
		}

		public void msgSitAtTable(int table) {//will become archaic in v2
			print("Received msgSitAtTable");
			//s = CustomerState.followHost;
			this.table = table;
			stateChanged();
		}

		//Messages added for v2
		public void followMeToTable(Waiter w, Menu m){//(menu)
			print("Following waiter to table");
			menu = m;
			this.waiter = w;
			e = AgentEvent.followWaiter;
			stateChanged();
		}

		public void whatWouldYouLike(Waiter w){
			print("Waiter " + w.getName() + " has asked what I would like to eat");
			e = AgentEvent.ordering;
			stateChanged();
		}
		public void whatWouldYouLike(Waiter w, Menu m){
			print("Watier " + w.getName() + " has asked me to take a new order.");
			s = CustomerState.GoingToTable;
			e = AgentEvent.seated;
			menu = m;
			if(menu == null){
				s = CustomerState.ChoosingOrder;
				e = AgentEvent.cantOrder;
			}
			stateChanged();
		}
		public void hereIsYourFood(Waiter w){
			print("Received food from" + w.getName());
			e = AgentEvent.hasFood;
			stateChanged();
		}
		//v2.1
		public void giveChange(Cashier ca, double cash){
			print("Received change from cashier");
			e = AgentEvent.paid;
			myPerson.setMoney(cash);
			stateChanged();
		}
		public void giveDebt(Cashier ca, double cash){
			print("Recived debt from cashier");
			e = AgentEvent.paid;
			myPerson.setMoney(cash);
			stateChanged();
		}
		public void hereIsCheck(double check){
			print("Received check from waiter");
			this.check = check;
			e = AgentEvent.gotCheck;
			stateChanged();
		}
		public void restIsFull(Host h) {
			print("Received message that restaurant is full");
			double rand = Math.random();
			if(rand<0.5){
				s = CustomerState.Hungry;
				e = AgentEvent.noLongerHungry;
				print("No longer hungry");
				stateChanged();
			}
		}
		/**
		 * Scheduler.  Determine what action is called for, and do it.
		 */
		public boolean pickAndExecuteAnAction() {
			//	CustomerAgent is a finite state machine
			print("Checking the customer scheduler");
			if(s == CustomerState.Hungry){
				if( e == AgentEvent.followWaiter){
					s = CustomerState.GoingToTable;
					followWaiter();
					e = AgentEvent.seated;
					return true;
				}
				else if(e == AgentEvent.noLongerHungry){
					s = CustomerState.NotHungry;
					changedMind();
					return true;
				}
				else if(e == AgentEvent.gotHungry){
					goToRestaurant();
					e = AgentEvent.waiting;
					host.iWantToEat(this);
					return true;
				}
			}
			if(s == CustomerState.GoingToTable){
				if(e == AgentEvent.seated){
					s = CustomerState.ChoosingOrder;
					generateOrder();
					return true;
				}
			}
			if(s == CustomerState.ChoosingOrder){
				if(e == AgentEvent.hasOrder){
					s = CustomerState.GivingOrder;
					callWaiter();
					return true;
				}
				else if(e == AgentEvent.cantOrder){
					s = CustomerState.Left;
					handleEarlyLeave();
					return true;
				}
			}
			if(s == CustomerState.GivingOrder){
				if(e == AgentEvent.ordering){
					giveOrder();
					e = AgentEvent.waiting;
					return true;
				}
				if(e == AgentEvent.hasFood){
					eatFood();
					s = CustomerState.Eating;
					return true;
				}
			}

			if(s == CustomerState.Eating){
				if(e == AgentEvent.doneEating){
					s = CustomerState.WaitOnCheck;
					askForCheck();
					return true;
				}
				else if(e == AgentEvent.hasOrder){
					eatFood();
					e = AgentEvent.eating;
					return true;
				}
			}
			if(s == CustomerState.WaitOnCheck){
				if(e == AgentEvent.gotCheck){
					s = CustomerState.Paying;
					payCashier();
					return true;
				}
			}
			if(s == CustomerState.Paying){
				if(e==AgentEvent.paid){
					leaveRestaurant();
					s = CustomerState.NotHungry;
					return true;
				}
			}
			if(s == CustomerState.NotHungry){
				if(e == AgentEvent.gotHungry){
					s = CustomerState.Hungry;
					return true;
				}
			}
			return false;
		}

		// Actions

		private void goToRestaurant() {
			DoGoToRestaurant();
			stateChanged();
		}


		private void followWaiter(){
			//DoFollowWaiter(waiter);
			//s = CustomerState.ChoosingOrder;
			//stateChanged();
		}

		private void generateOrder(){//will need updates for prices
			int choiceNum = -1;
			if(menu.size()==0){//out of food scenario
				print("Given an empty menu! Leaving restauarant");
				e = AgentEvent.cantOrder;
				stateChanged();
				return;
			}
			if(getName().equalsIgnoreCase("Steak")&menu.get(0)!=null&myPerson.getMoney()>=15.99){
				choiceNum = 0;
			}
			if(getName().equalsIgnoreCase("Chicken")&menu.get(1)!=null&myPerson.getMoney()>=10.99){
				choiceNum = 1;
			}
			if(getName().equalsIgnoreCase("Salad")&menu.get(2)!=null&myPerson.getMoney()>=5.99){
				choiceNum = 2;
			}
			if(getName().equalsIgnoreCase("Pizza")&menu.get(3)!=null&myPerson.getMoney()>=8.99){
				choiceNum = 3;
			}

			if(choiceNum < 0){
				if(getName().equalsIgnoreCase("flake")){//flake will order the most expensive thing that is still available
					if(menu.get(0) != null){
						choiceNum = 0;
					}
					else if(menu.get(1) != null){
						choiceNum = 1;
					}
					else if(menu.get(3) != null){
						print("" + menu.get(3));
						choiceNum = 3;
					}
					else if(menu.get(2) != null){
						choiceNum = 2;
					}
				}
				else if(myPerson.getMoney() >= 5.99){
					choiceNum = generateRandom(myPerson.getMoney());
				}
			}
			if(choiceNum < 0){
				print("I could not find an order that I could afford. Leaving restaurant.");
				e = AgentEvent.cantOrder;
				stateChanged();
				return;
			}

			if(menu.get(choiceNum) == null){
				System.err.println("You've reached a weird error");
			}

			//if you make it this far! You've finally generated an order...phew!
			this.choice = menu.get(choiceNum);
			e = AgentEvent.hasOrder;
			stateChanged();
		}

		private void callWaiter(){
			waiter.imReadyToOrder(this);
			stateChanged();
		}

		private void changedMind(){
			host.noLongerHungry(this);
			DoLeaveRestaurant();
			stateChanged();
		}

		private void giveOrder(){
			//DoGiveOrder();
			waiter.hereIsMyChoice(this, choice);
			DoGiveOrder();
			stateChanged();

		}

		private void eatFood() {//may be modified slightly for v2
			//Do("Eating Food");
			//This next complicated line creates and starts a timer thread.
			//We schedule a deadline of getHungerLevel()*1000 milliseconds.
			//When that time elapses, it will call back to the run routine
			//located in the anonymous class created right there inline:
			//TimerTask is an interface that we implement right there inline.
			//Since Java does not all us to pass functions, only objects.
			//So, we use Java syntactic mechanism to create an
			//anonymous inner class that has the public method run() in it.
			timer.schedule(new TimerTask() {
				public void run() {
					doneEating();
				}
			},
			5000);//getHungerLevel() * 1000);//how long to wait before running task
		}

		private void doneEating(){
			print("Done eating, " + choice);
			e = AgentEvent.doneEating;
			stateChanged();

		}

		private void handleEarlyLeave(){
			waiter.cantOrderLeaving(this);
			DoLeaveRestaurant();
			stateChanged();
		}

		private void askForCheck(){
			waiter.doneEating(this);
		}

		private void payCashier(){
			DoGoToCashier();
			waiter.gotCheckAndLeaving(this);
			cashier.payment(this, myPerson.getMoney(), check);
			e = AgentEvent.paid;
			stateChanged();
		}
		//Animation stuff
		private void DoGoToRestaurant() {
			customerGui.DoGoToWaitingArea();
			try {
				moving.acquire();
			}
			catch(InterruptedException e) {	
			}

		}

		private void DoFollowWaiter(){
			customerGui.DoFollowWaiter();
			try {
				moving.acquire();
			}
			catch(InterruptedException e) {	
			}
		}
		private void DoGoToCashier(){
			customerGui.DoGoToCashier();
			try {
				moving.acquire();
			}
			catch(InterruptedException e) {	
			}
		}
		private void DoLeaveRestaurant(){
			customerGui.DoLeaveRestaurant();
			try {
				moving.acquire();
			}
			catch(InterruptedException e) {	
			}
		}
		private void DoGiveOrder(){
			waiter.doneMoving();
		}

		public void doneMoving(){
			moving.release();
		}

		private void leaveRestaurant() {//will be edited in v2
			//Do("Leaving.");
			DoLeaveRestaurant();
			myPerson.leftBuilding(this);
			//waiter.doneEatingAndLeaving(this);
			//customerGui.DoExitRestaurant();
		}

		// Accessors, etc.
		public int generateRandom(double money){
			int choiceNum = -1;
			int affordableSize = menu.size();
			if(money >= 15.99)
			{	
				choiceNum = (int)(Math.random()*(4));
				while(menu.get(choiceNum) == null)
					choiceNum = (int)(Math.random()*(4));
			}
			else if(money >= 10.99){
				if(menu.get(0) != null)
					affordableSize --;
				if(affordableSize <= 0){
					return -1;
				}
				choiceNum = (int)(Math.random()*(3)) + 1;
				while(menu.get(choiceNum) == null)
					choiceNum = (int)(Math.random()*(3)) + 1;
			}
			else if(money >= 8.99){
				if(menu.get(0) != null)
					affordableSize --;
				if(menu.get(1) != null)
					affordableSize --;
				if(affordableSize <= 0){
					return -1;
				}
				choiceNum  = (int)(Math.random()*(2)) + 2;	
				while(menu.get(choiceNum) == null)
					choiceNum  = (int)(Math.random()*(2)) + 2;	
			}
			else if(money >= 5.99){
				if(menu.get(0) != null)
					affordableSize --;
				if(menu.get(1) != null)
					affordableSize --;
				if(menu.get(3) != null)
					affordableSize --;
				if(affordableSize <= 0){
					return -1;
				}
				choiceNum = 2;
			}
			return choiceNum;
		}

		
		

		public int getHungerLevel() {
			return hungerLevel;
		}
		public String getWaiterName(){
			String result = "No Waiter";
			if(waiter != null){
				result = waiter.getName();
			}
			return result;
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
			customerGui = (Restaurant5CustomerGui)g;
		}

		public RoleGui getGui() {//Is this where the problem lies?
			return customerGui;
		}


}

