package simcity.restaurants.restaurant4;


import Gui.RoleGui;
import agent.Agent;
import agent.Role;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Semaphore;
import java.lang.Math;

import simcity.PersonAgent;
import simcity.Market.MarketGui.MarketCustomerGui;
import simcity.restaurants.restaurant4.Restaurant4Gui.Restaurant4CustomerGui;
import simcity.restaurants.restaurant4.interfaces.Restaurant4Cashier;
import simcity.restaurants.restaurant4.interfaces.Restaurant4Customer;
import simcity.restaurants.restaurant4.interfaces.Restaurant4Host;
import simcity.restaurants.restaurant4.interfaces.Restaurant4Waiter;

/**
 * Restaurant customer agent.
 */
public class Restaurant4CustomerRole extends Role implements Restaurant4Customer{
	private String name;    
	Timer timer = new Timer();
	private Semaphore going = new Semaphore(0,true);
//	public CustomerGui customerGui;
	static final int timerTime = 5000;
	private Restaurant4Host host;
	private String choice;
	private double amountDue =0;
	private double money;
	private Restaurant4Waiter myWaiter;
	public Restaurant4Cashier cashier = null;
	private Menu menu;
	public enum AgentState {DoingNothing, WaitingInRestaurant, BeingSeated, ChoosingFood, WaitingWater, ReadyToOrder, WaitingForWaiter, OrderingFood, WaitingForFood, Eating, Leaving, Paying, GoingToPay, readyToPay};
	private AgentState state = AgentState.DoingNothing;//The start state
	public enum AgentEvent 
	{none, gotHungry, followWaiter, atCashier, seated, doneChoosing, waiterNotified, waiterAskedMe, inWaitingLine,  orderMade, foodDelivered, doneEating, doneLeaving, gotAmount, paid};
	AgentEvent event = AgentEvent.none;
	private int hungerLevel = 5; // determines length of meal
	public boolean seated = false;
	private PersonAgent person;
	private Restaurant4CustomerGui customerGui;
	/**
	 * Constructor for CustomerAgent class
	 *
	 * @param name name of the customer
	 * @param gui  reference to the customergui so the customer can send it messages
	 */
	public Restaurant4CustomerRole(PersonAgent p){
		super(p);
		customerGui  = (Restaurant4CustomerGui)super.gui;
	}

	public Restaurant4CustomerRole(){
		super();
		customerGui  = (Restaurant4CustomerGui)super.gui;
		
	}
	@Override
	public void startBuildingMessaging(){
		gotHungry();
	}
	
	public void msgOutOfRestaurant4(){
		myPerson.leftBuilding(this);
	}
	/**
	 * hack to establish connection to Host agent.
	 */
	public void setHost(Restaurant4Host host) {
		this.host = host;
	}
	
	
	public void setCashier(Restaurant4Cashier cashier){
		this.cashier = cashier;
	}
	
	public String getCustomerName() {
		return name;
	}
	
	public void setMoney(double m){
		money = money + m;
	}
	public double getMoney(){
		money = Math.round(money*100);
		money = money/100;
		return money;
	}
	
	public String getChoice(){
		return choice;
	}	
	
	public void setAmountDue(double i){
		
			amountDue = amountDue + i;
		
		}
	
	
	public double getAmountDue(){
		return amountDue ;
	}
	
	public Restaurant4CustomerGui getCustomerGui(){
		return customerGui;
	}
	// Messages

	public void gotHungry() {//from animation
		customerGui.ordered = false;
		customerGui.gotOrder = false;
		customerGui.doneEating = false;
		person.Do("I'm hungry");
		event = AgentEvent.gotHungry;
		stateChanged();
	}
	
	
	
	public void setGui(RoleGui g){
		super.setGui(g);
		customerGui = (Restaurant4CustomerGui)g;
	}
	
	public void followMe(Restaurant4Waiter w, Menu u){
		person.Do("My Waiter is "+ w.getName());
		event = AgentEvent.followWaiter;
		myWaiter = w;
		menu = u;
		stateChanged();
	}
	
	public void msgAnimationFinishedGoToSeat() {
		//from animation
		event = AgentEvent.seated;
		stateChanged();
	}
	
	public void CallWaiter(){
		event = AgentEvent.waiterNotified;
		stateChanged();
	}
	
	public void whatDoYouWant(){
		event = AgentEvent.waiterAskedMe;
		stateChanged();
	}
	
	public void OrderMade(){
		event = AgentEvent.orderMade;
		stateChanged();
	}
	
	public void outOfChoice(Menu menu){
		if (money>=5.99 && money<8.99 && choice.equals("Salad")){
			leaveTable();
			customerGui.ordered =false;
		}
		else{
			this.menu = menu;
			state = AgentState.BeingSeated;
			event = AgentEvent.seated;
			person.Do("reordering");
			stateChanged();
		}
	}
	
	public void HereIsFood(){
		person.Do("event food delievered");
		event = AgentEvent.foodDelivered;
		stateChanged();
	}
   
	public void AtRestaurant(){
		event = AgentEvent.inWaitingLine;
		stateChanged();
	}
	
	public void msgAnimationFinishedLeaveRestaurant() {
		//from animation
		event = AgentEvent.doneLeaving;
		stateChanged();
	}

	public void PayForFood(double check){
		person.Do("Going to cashier, I have " + money + " dollars");
		setAmountDue(check);
		event = AgentEvent.gotAmount; 
		stateChanged();
	}
	
	public void arrivedToCashier(){
		person.Do("Arrived to cashier");
		event = AgentEvent.atCashier;
		stateChanged();
	}
	
	public void HereIsYourChange(double m){
		if (m>=0){
			setMoney(m);
			amountDue = 0;
			person.Do("My change " + m);
		}
		else {
			setMoney(0);
			amountDue = Math.abs(m);
			person.Do("I will pay " + Math.abs(m) + " dolaars next time" );
		}
		event = AgentEvent.paid;
		stateChanged();
	}
	
	/**
	 * Scheduler.  Determine what action is called for, and do it.
	 */
	@Override
	public boolean pickAndExecuteAnAction() {
		if (state == AgentState.DoingNothing && event == AgentEvent.gotHungry ){
			state = AgentState.WaitingInRestaurant;
			goToRestaurant();
			return true;
		}
		
		if (state == AgentState.WaitingInRestaurant && event == AgentEvent.inWaitingLine){
			state = AgentState.WaitingWater;
			IWantFood();
			return true;
			
		}
		
		if (state == AgentState.WaitingWater && event == AgentEvent.followWaiter ){
			state = AgentState.BeingSeated;
			SitDown();
			return true;
		}
		if (state == AgentState.BeingSeated && event == AgentEvent.seated){
			state = AgentState.ChoosingFood;
			chooseFood();
			return true;
		}
		if (state == AgentState.ChoosingFood && event == AgentEvent.doneChoosing){
			state = AgentState.ReadyToOrder;
			notifyWaiter();
			return true;
		}
		
		if (state == AgentState.ReadyToOrder && event == AgentEvent.waiterNotified){
			state = AgentState.WaitingForWaiter;
			//no action
			return true;
		}
		
		if (state == AgentState.WaitingForWaiter && event == AgentEvent.waiterAskedMe){
			state = AgentState.OrderingFood;
			orderFood();
			return true;
		}
		
		if (state == AgentState.OrderingFood && event == AgentEvent.orderMade){
			state = AgentState.WaitingForFood;
			//no action
			return true;
		}
		if (state == AgentState.WaitingForFood && event == AgentEvent.foodDelivered){
			state = AgentState.Eating;
			EatFood();
			return true;
		}
		if (state == AgentState.Eating && event == AgentEvent.doneEating){
			state = AgentState.readyToPay;
			//leaveTable();
			return true;
		}
		if (state == AgentState.readyToPay && event == AgentEvent.gotAmount){
			state = AgentState.GoingToPay;
			goToCashier();
			return true;
		}
		
		if (state == AgentState.GoingToPay && event == AgentEvent.atCashier){
			state = AgentState.Paying;
			pay();
			return true;
		}
		
		if (state == AgentState.Paying && event == AgentEvent.paid){
			state = AgentState.Leaving;
			leaveTable();
			return true;
		}
		System.out.println("Nothing to do");
		return false;
	}

	// Actions
	
	
	

	private void goToRestaurant() {
		person.Do("Going to restaurant");
		customerGui.DoGoToRestaurant();
	}
	
	private void IWantFood(){
		person.Do("Waiting to be seated");
		host.msgIWantFood(this);
	} 
	
	private void SitDown() {
		person.Do("Being seated. Going to table");
		//customerGui.DoGoToSeat();
	}
	
	public void chooseFood(){
		person.Do("Choosing food");
		timer.schedule(new TimerTask() {
			public void run() {
				event = AgentEvent.doneChoosing;
				stateChanged();
			}
		},
		timerTime);
		person.Do("I have " + money+ " dollars");
		/*if (menu.choice.size() == 0){
			leaveTable();
		}
		if (money< 5.99 && name.equals("honest")){
			leaveTable();
		}
		else if (money>=5.99 && money<8.99){
			choice = menu.choice.get(2);
		}*/
		//else{
			Random rand = new Random();
			int value = rand.nextInt(menu.choice.size());	
			choice = menu.choice.get(value);
		//}
	}
	
	public void notifyWaiter(){
		person.Do("Ready to order");
		CallWaiter();
		myWaiter.ReadyToOrder(this);
	}
	
	public void orderFood(){
		person.Do("I want " + choice);
		myWaiter.HereIsMyChoice(this, choice);
		OrderMade();
		customerGui.ordered = true;
	}
	
	
	private void EatFood() {
		myWaiter.computeCheck(this);
		customerGui.gotOrder = true;
		customerGui.ordered = false;
		person.Do("Please give me a check");
		
		TimerTask task;
		
		person.Do("Eating " + choice);
		//This next complicated line creates and starts a timer thread.
		//We schedule a deadline of getHungerLevel()*1000 milliseconds.
		//When that time elapses, it will call back to the run routine
		//located in the anonymous class created right there inline:
		//TimerTask is an interface that we implement right there inline.
		//Since Java does not all us to pass functions, only objects.
		//So, we use Java syntactic mechanism to create an
		//anonymous inner class that has the public method run() in it.
		timer.schedule(new TimerTask() { public void run() {
			person.Do("Done eating " + choice );
				event = AgentEvent.doneEating;
				stateChanged();
				customerGui.doneEating = true;
			}
		},
		timerTime);//how long to wait before running task
	}

	public void goToCashier(){
		person.Do("Do go to cashier");
		customerGui.doGoToCashier();
	}
	
	public void pay(){
		cashier.hereIsPayment(this, money);
		person.Do("Here is money");
	}
	
	private void leaveTable() {
		//customerGui.doneEating = true;
		person.Do("Leaving.");
		myWaiter.DoneAndLeaving(this);
		customerGui.DoExitRestaurant();
		state = AgentState.DoingNothing;  
	}
	
	public void leaveIfYouWant(){
		if (name.equals("leave")){
			person.Do("Leaving.");
			state = AgentState.DoingNothing;
			customerGui.DoExitRestaurant();
			host.leaving(this);
		}
		else {
			person.Do("I'm staying");
			return;
		}
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

	public void setGui(Restaurant4CustomerGui g) {
		customerGui = g;
	}

	public Restaurant4CustomerGui getGui() {
		return customerGui;
	}
	
	public boolean DoneLeaving() {
		
		if (state == AgentState.Leaving){
			return true;
		}
		else {
			return false;
		}
	}

	
}

