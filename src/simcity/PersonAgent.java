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
	private double money = 0.0;
	private List<Role> roles = new ArrayList<Role>();
	private List<String> groceryList = new ArrayList<String>();
	private List<RentBill> rentBills = new ArrayList<RentBill>();
	boolean personScheduler = false;

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
		String type;
		//How does he know where to work? Building base class?
		
		//Job Constructor
		public Job(Role r) {
			role = r;
		}
		
		public Job(Role r, String type) {
			role = r;
			this.type = type;
		}
		
		public void setJob(Role r) {
			role = r;
		}
		

	}
	
	//Paying Rent
	//When to pay rent
	private enum RentState {Paid, NotPaid, PayingBill};
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
	public String getJob() {
		if(myJob != null)
			return myJob.type;
		else
			return null;
	}
	public int getCurrentTime() {
		return currentTime;
	}


	public void setCurrentTime(int currentTime) {
		this.currentTime = currentTime;
	}


	public int getAccountNumber() {
		return accountNumber;
	}


	public void setAccountNumber(int accountNumber) {
		this.accountNumber = accountNumber;
	}

	public boolean getPersonScheduler() {
		return personScheduler;
	}
	
	public List<Role> getRoles() {
		return roles;
	}
	
	public List<RentBill> getRentBills() {
		return rentBills;
	}
	@Override
	public double checkMoney() {
		// TODO Auto-generated method stub
		return money;
	}
	
	public String getName(){
		return name;
	}
	
	public void addRole(Role role) {
		roles.add(role);
		//stateChanged();
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
	
	public void setJob(String type) {
		myJob = new Job(RoleFactory.makeMeRole(type), type);
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
	public boolean pickAndExecuteAnAction() {
		// Person Scheduler 
		//If he's CRRAAAZZY hungry, then eat something first. Then do checks of eating at home versus the restaurant
		
			//Work comes first--his family probably doesn't like this :/
			if(myJob != null) {
				if(myJob.state == JobState.GoToWorkSoon){
					goToWork();
					//return true; or boolean person = true;?
					return true;
				}
				else if(myJob.state == JobState.LeaveWork) {
					leaveWork();
					return true;
				}
			}
			
		//if he's REALLY hungry, then eat something before paying bills. Then do checks of eating at home versus the restaurant
			
			//Gotta pay the bills!
			for(RentBill rb : rentBills) {
				if(rb.state == RentState.NotPaid){
					payBills();
					return true;
				}
			}
			
			//Gotta eat!
			if(eatingState == EatingState.EatAtHome) {
				goEatAtHome();
				return true;
			}
			else if(eatingState == EatingState.EatAtRestaurant) {
				goEatAtRestaurant();
				return true;
			}
			
			//Might as well get groceries if I ain't got nothing to do
			if(marketState == MarketState.GetGroceries) {
				goGetGroceries();
				return true;
			}
			
			//Let me even see if I got money..
			//if(accountNumber == 0 || moneyState == MoneyState.Low || moneyState == MoneyState.High) {
			//	goToBank();
			//	return true;
			//}
		
	
		//Role Scheduler
		for(Role r : roles) {
			return r.pickAndExecuteAnAction();
		}
		
		return false;
	}

	
	//Actions




	private void goToWork() {
		//animate out of building
		//roles.get(0).DoLeaveBuilding();
		
		//animate to desired location
		//DoGoToDestination(myJob.role.myBuilding);
		roles.add(myJob.role);
		
	}
	
	private void leaveWork() {
		//animate out of building
		//roles.get(0).DoLeaveBuilding();
		
		//animate to desired location
		//DoGoToHome();
		roles.remove(myJob.role);
	}
	
	//May not need this function since we already have a goToBank()
	private void payBills() {
		//if inside building and not in bank
		//animate outside building
		
		//animate to bank
		//DoGoToBank();
		//roles.add(RoleFactory.makeMeRole(bank.bankCustomerRole));
	}

	private void goEatAtHome() {
		//if inside building and not in home
		//animate outside building
		
		//animate to home
		//DoGoToHome();
		//roles.add(RoleFactory.makeMeRole(home.homeCustomerRole));
	}
	
	private void goEatAtRestaurant() {
		//if inside building and not in current restaurant preference
		//animate outside building
		
		//animate to restaurant
		//DoGoToRestaurant(currentRestaurantPreference);
		//roles.add(RoleFactory.makeMeRole(currentRestaurantPreference.restaurantCustomerRole));
	}
	
	private void goGetGroceries() {
		//if inside building and not in market
		//animate outside building
		
		//animate to market
		//DoGoToMarket();
		//roles.add(RoleFactory.makeMeRole(market.marketCustomerRole));
	}

	private void goToBank() {
		//if inside building and not in bank
		//animate outside building
		
		//animate to bank
		//DoGoToBank();
		//roles.add(RoleFactory.makeMeRole(bank.bankCustomerRole));
	}


	public void restart() {
		// TODO Auto-generated method stub
		
	}
}