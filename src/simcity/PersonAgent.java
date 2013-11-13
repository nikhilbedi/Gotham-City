package simcity;

import agent.Agent;
import agent.Role;

import java.util.*;
import java.util.concurrent.Semaphore;

public class PersonAgent extends Agent {
	//Data
	Semaphore busyWithTask;
	private float money;
	private List<Role> roles;
	/*public List<Restaurant> restaurants;
	public List<Market> markets;
	public Home myHome;*/
	
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
	}
	
	//Paying Rent
	//When to pay rent
	private enum RentState {EverythingIsPaid, PayBill, PayingBill};
	List<RentBill> rentBills;
	protected class RentBill {
		public RentState state = RentState.PayBill;
		PersonAgent accountHolder;
		float amount;
		
		public RentBill(PersonAgent p, float a) {
			accountHolder = p;
			amount = a;
		}
	}
		
	
	
	//constructor
	
	
	//Messages
	
	
	//Scheduler
	@Override
	protected boolean pickAndExecuteAnAction() {
		// TODO Auto-generated method stub
		
		return false;
	}
	
	
	//Actions

}