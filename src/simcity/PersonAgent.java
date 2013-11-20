package simcity;

import agent.Agent; 
import agent.Role;
import simcity.interfaces.Person;
import simcity.RoleFactory;

import java.util.*;
import java.util.concurrent.Semaphore;



public class PersonAgent extends Agent implements Person {
	//Data
	public String name;
	int currentTime; //(ranges from 1-24)
	int accountNumber; //Not currently sure how we're using account numbers, but the person should know it.
	Semaphore busyWithTask;
	private double money;
	private List<Role> roles = new ArrayList<Role>();
	private List<String> groceryList;
	
	//Locations
	/*public List<Restaurant> restaurants;
	public List<Market> markets;
	public Bank bank;
	public Home myHome;
	Restaurant currentPreference;*/
	
	
	//Need to implement going to bank to open account
	
	//States - Currently the states are private. If need be, we can change them to public so our roles can see them
		//Preferred Transportation
		private enum TransportationState {Walking, Bus, Car};
		private TransportationState transportationState = TransportationState.Walking;
		
		//Where to eat
		private enum EatingState {EatAtHome, HeadedToHome, EatingAtHome, Nowhere, EatAtRestaurant, HeadedtoRestaurant, EatingAtRestaurant};
		private EatingState eatingState = EatingState.Nowhere;
		
		//When to eat
		private enum HungerState {NotHungry, Hungry, FeedingHunger};
		private HungerState hungerState =  HungerState.NotHungry;
		
		//Going to the market states
		private enum MarketState {GetGroceries, GettingGroceries, HaveGroceries};
		private MarketState marketState = MarketState.HaveGroceries;
		
		//Keep track of money
		private enum MoneyState{Low, High, Neutral};
		private MoneyState moneyState = MoneyState.Neutral;
		
	//Job
	private Job myJob;
	private enum JobState {OffWork, GoToWorkSoon, HeadedToWork, AtWork, LeaveWork, LeavingWork};
	protected class Job {
		public JobState state = JobState.OffWork;
		int onWork = 8; //8am
		int offWork = 17; //military hours - 17 == 5pm
		Role role;
		//How does he know where to work? Building base class?
		
		//Job Constructor
		public Job(Role r) {
			role = r;
		}
		
		public void setJob(Role r) {
			role = r;
		}
		
		public void setJob(String type) {
			role = RoleFactory.makeMeRole(type);
		}
	}
	
	//Paying Rent
	//When to pay rent
	private enum RentState {Paid, NotPaid, PayingBill};
	List<RentBill> rentBills = new ArrayList<RentBill>();
	public class RentBill {
		public RentState state = RentState.NotPaid;
		PersonAgent accountHolder;
		float amount;
		
		public RentBill(PersonAgent p, float a) {
			accountHolder = p;
			amount = a;
		}
	}
		
	
	
	//constructor
	public PersonAgent(String name) {
		super();
		this.name = name;
	}
	
	
	//functions so we can function
	public void addRole(Role role) {
		roles.add(role);
	}
	
	public void removeRole(Role role) {
		roles.remove(role);
	}
	
	public void addMoney(float amount) {
		money += amount;
	}
	
	public void setJob(Role role) {
		myJob = new Job(role);
	}
	
	public void setPreferredTransportation(String type) {
		if(type.contains("car"))
			transportationState = TransportationState.Car;
		else if(type.contains("bus"))
			transportationState = TransportationState.Bus;
		else
			transportationState = TransportationState.Walking;
	}
	
	
	//Messages from World Clock
	public void currentTime(int time) {
		currentTime = time;
		
		//We should change any states here, not constantly check the scheduler to change states
		if(myJob != null) {
			if(currentTime == myJob.onWork) {
				myJob.state = JobState.GoToWorkSoon;
			}
			else if(currentTime == myJob.offWork){
				myJob.state = JobState.LeaveWork;
			}
		}
		
		//every "hour", let's check how much money is in our wallet.
		double low = 25.0;
		double high = 150.0;
		if(money <= low) {
			moneyState = MoneyState.Low;
		}
		else if(money >= high) {
			moneyState = MoneyState.High;
		}
	}
	
	
	//Messages from GUI
	/**
	 * a message from the gui that instructs the person to go to a certain restaurant
	 * @param r Which restaurant the person should head to
	 */
	/*public void eatAtRestaurant(Restaurant r) {
		eatingState = EatingState.EatAtRestaurant;
		stateChanged();
	}*/
	
	public void eatAtHome() {
		eatingState = EatingState.EatAtHome;
		stateChanged();
	}
	
	
	//Messages from Roles
	/**
	 * a message from HomeResidentRole sends a grocery list of what foods are needed
	 * @param foods Currently, the list is of type String, but will be converted to a Food class list after Evan creates it
	 */
	public void homeNeedsGroceries(List<String> foods) {
		groceryList = foods;
		marketState = MarketState.GetGroceries;
		stateChanged();
	}
	
	/**
	 * a message from the HomeResidentRole sends a bill to be paid for his/her home
	 * @param rb The bill to be paid, in which the initial state is 'NotPaid'
	 */
	public void goPayBill(RentBill rb) {
		rentBills.add(rb);
		stateChanged();
	}
	
	/**
	 * to notify the person he/she has eaten is needed.  It is possible that the customer doesnt eat the restaurant, so it eating needs to be explicit
	 */
	public void justAte() {
		hungerState = HungerState.NotHungry;
	}
	
	/**
	 * Notifies the person that the current role is done with all interactions in the restaurant
	 * @param role
	 */
	public void leavingBuilding(Role role) {
		//if role is of type host or bankgreeter, don't remove. Still need them to be active 
		roles.remove(role);
	}
	
	
	
	//Scheduler
	@Override
	protected boolean pickAndExecuteAnAction() {
		// Person Scheduler 
		System.out.println("Calling person scheduler");
		
		//If he's CRRAAAZZY hungry, then eat something first. Then do checks of eating at home versus the restaurant
		
			//Work comes first--his family probably doesn't like this :/
			if(myJob != null) {
				if(myJob.state == JobState.GoToWorkSoon){
					goToWork();
					//return true; or boolean person = true;?
				}
				else if(myJob.state == JobState.LeaveWork) {
					leaveWork();
				}
			}
			
		//if he's REALLY hungry, then eat something before paying bills. Then do checks of eating at home versus the restaurant
			
			//Gotta pay the bills!
			for(RentBill rb : rentBills) {
				if(rb.state == RentState.NotPaid){
					payBills();
				}
			}
			
			//Gotta eat!
			if(eatingState == EatingState.EatAtHome) {
				goEatAtHome();
			}
			else if(eatingState == EatingState.EatAtRestaurant) {
				goEatAtRestaurant();
			}
			
			//Might as well get groceries if I ain't got nothing to do
			if(marketState == MarketState.GetGroceries) {
				goGetGroceries();
			}
			
			//Let me even see if I got money..
			if(accountNumber != 0 || moneyState == MoneyState.Low || moneyState == MoneyState.High) {
				goToBank();
			}
			
	
		//Role Scheduler
		for(Role r : roles) {
			System.out.println("Calling role schedulers");
			
			r.pickAndExecuteAnAction();
		}
		
		return false;
	}
	
	
	//Actions
	
	private void goToWork() {
		
	}
	
	private void leaveWork() {
		
	}
	
	private void payBills() {
		
	}

	private void goEatAtHome() {
		
	}
	
	private void goEatAtRestaurant() {
		
	}
	
	private void goGetGroceries() {
	
	}

	private void goToBank() {
		
	}


	public void restart() {
		// TODO Auto-generated method stub
		
	}
}