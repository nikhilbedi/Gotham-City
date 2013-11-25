package simcity;

import agent.Agent;
import agent.Role;
import Gui.RoleGui;
import Gui.Screen;
import Gui.ScreenFactory;
import simcity.interfaces.Person;
import simcity.RoleFactory;
import simcity.restaurants.*;
import simcity.Market.Market;
import simcity.Home.Home;
import simcity.Home.LandlordRole;
import simcity.bank.*;

import java.util.*;
import java.util.concurrent.Semaphore;

public class PersonAgent extends Agent implements Person {
	//TODO REMOVE THIS
	Role bankRoleTemp;
	RoleGui bankGui;


	public String name;
	int currentTime; //(ranges from 1-24)
	public int accountNumber; //Not currently sure how we're using account numbers, but the person should know it if we're removing that role
	Semaphore busyWithTask = new Semaphore(0, false);
	double money = 0.0;
	protected List<Role> roles = new ArrayList<Role>();
	public Map<String, Integer> groceryList = new HashMap<String, Integer>();
	public Map<String, Integer> groceryBag = new HashMap<String, Integer>();
	public List<RentBill> rentBills = new ArrayList<RentBill>();
	public boolean checkPersonScheduler = true;
	PersonGui gui;
	
	private LandlordRole landlord;

	//Saves time from having to loop all the time to find the active role
	Role activeRole;


	//Locations
	//These buildings will be set when any person is added 
	public List<Restaurant> restaurants;
	Restaurant currentPreference;
	public List<Market> markets;
	public Bank bank;

	//These three are essential, but should be instantiated with the "Homeless Shelter" spawnpoint
	private boolean shelter = false;
	private Building spawnPoint = new Building("spawnpoint");
	private Home myHome;
	public Building currentBuilding = spawnPoint; 
	public Building currentDestination = spawnPoint; 


	//Need to implement going to bank to open account

	//States - Currently the states are private. If need be, we can change them to public so our roles can see them

	//Preferred Transportation
	public enum TransportationState {Walking, Bus, Car};
	public TransportationState transportationState = TransportationState.Walking;

	//Where to eat
	public enum EatingState {EatAtHome, HeadedToHome, EatingAtHome, Nowhere, EatAtRestaurant, HeadedtoRestaurant, EatingAtRestaurant};
	public EatingState eatingState = EatingState.Nowhere;

	//When to eat
	public enum HungerState {NotHungry, Famished, Hungry, Starving, FeedingHunger};
	private int hungerCount = 0; 
	public HungerState hungerState =  HungerState.NotHungry;

	//Going to the market states
	public enum MarketState {GetGroceries, GettingGroceries, TakeGroceriesHome, TakingGroceriesHome, HaveGroceries};
	public MarketState marketState = MarketState.HaveGroceries;

	//Keep track of money
	public enum MoneyState{Low, High, Neutral};
	public MoneyState moneyState = MoneyState.Neutral;


	//Job

	private Job myJob;

	private enum JobState {
		OffWork, GoToWorkSoon, HeadedToWork, AtWork, LeaveWork, LeavingWork
	};

	protected class Job {
		public JobState state = JobState.OffWork;
		int onWork = 8; // 8am
		int offWork = 17; // military hours - 17 == 5pm
		Role role;
		String type;

		Building workplace;
		//How does he know where to work? Building base class?

		//Job Constructor
		public Job(Role r, Building w) {

			role = r;
			workplace = w;
		}


		public Job(Role r, String type, Building w) {

			role = r;
			this.type = type;
			workplace = w;
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
		public PersonAgent accountHolder;
		public float amount;


		public RentBill(PersonAgent p, float a) {
			accountHolder = p;
			amount = a;
		}
	}


	//constructors

	public PersonAgent(String name) {
		super();
		this.name = name;
		//	gui.setHomeScreen(s);
	}

	public PersonAgent(String name, PersonGui g, Screen s) {
		super();
		this.name = name;
		gui = g;
		gui.setHomeScreen(s);
	}

	//essential setters for GUI (When adding a person to SimCity)
	public void setGui(PersonGui g) {
		gui = g;
	}

	public void setRestaurants(List<Restaurant> r) {
		restaurants = r;
	}

	public void setMarkets(List<Market> m) {
		markets = m;
	}

	public void setBank(Bank b) {
		bank = b;
	}

	/**
	 * 
	 * @param h The home (or shelter) the person will reside.
	 */
	public void setHome(Home h) {
		if(h.getName().contains("shelter")) {
			shelter = true;
		}
		else {
			myHome = h;
			currentBuilding = h;
			currentDestination = h;
			//Should I make a new one, or just make it equal to this one? There is only one resident for a home...
			//activeRole = myHome.resident;
		}
	}



	//functions so we can function
	public void setHomeOwnerRole() {
		//When Evan is done with homeowner role, I can add this 
	}

	public Location getLocation() {
		return currentBuilding.getEntranceLocation();
	}

	public void setJob(Role role, Building building) {
		myJob = new Job(role, building);
	}

	public void setJob(String type, Building building) {
		myJob = new Job(RoleFactory.makeMeRole(type), type, building);
	}

	public String getJob() {
		if (myJob != null)
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
		return checkPersonScheduler;
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
		// stateChanged();
	}

	public void removeRole(Role role) {
		roles.remove(role);
	}

	public void addMoney(float amount) {
		money += amount;
	}

	public void removeMoney(float amount) {
		money -= amount;
	}
	
	public void setPreferredTransportation(String type) {
		if (type.contains("car"))
			transportationState = TransportationState.Car;
		else if (type.contains("bus"))
			transportationState = TransportationState.Bus;
		else
			transportationState = TransportationState.Walking;
	}



	//Messages from World Clock
	public void updateTime(int time) {
		currentTime = time;
		//Another hour, another chance to eat ;)
		hungerCount++;
		print("Checking my watch and it is " + time + " o' clock");
		//NEED TO CHECK IF THIS PERSON IS A HOMEOWNER. IF SO, MAKE THAT ROLE ACTIVE IF NO OTHER ROLE IS ACTIVE
		if(landlord != null) {
			//landlord.updateCurrentTime(time);
		/*	if(landord.timeIsUp()) {
				landlord.setActive(true);
			}
			else
				landlord.setActive(false);*/
		}

		if(hungerCount > 11) {
			hungerState = HungerState.Starving;
		}
		else if(hungerCount > 7) {
			hungerState = HungerState.Hungry;
		}
		else if(hungerCount > 3) {
			hungerState = HungerState.Famished;
		}

		//We should change any states here, not constantly check the scheduler to change states
		if(myJob != null) {
			if(currentTime == myJob.onWork) {
				myJob.state = JobState.GoToWorkSoon;	
			} 
			//Maybe, also check if our current state is atWork
			else if (currentTime == myJob.offWork) {
				myJob.state = JobState.LeaveWork;
				checkPersonScheduler = true;
			}
		}

		//every "hour", let's check how much money is in our wallet. (temporary low and highs)
		double low = 25.0;
		double high = 150.0;
		if (money <= low) {
			moneyState = MoneyState.Low;
		} else if (money >= high) {
			moneyState = MoneyState.High;
		}

		stateChanged();
	}



	//Messages from User Interface or Animation
	/**
	 * 
	 */
	public void reachedBuilding() {
		//	currentBuilding = currentDestination;
		busyWithTask.release();
	}


	/**
	 * Notifies the person that the current role is done with all interactions in the restaurant
	 * @param role
	 */
	/*public void leftBuilding(Role role) {
		//if role is of type host or bankgreeter, don't remove. Still need them to be active 
		roles.remove(role);
		//	checkPersonScheduler = true;
	}
	 *//**
	 * Notifies the person that the current role is done with all interactions
	 * in the restaurant
	 * 
	 * @param role
	 */

	/**
	 * Notifies the person that the current role is done with all interactions in the restaurant
	 * @param role
	 */
	public void leftBuilding(Role role) {
		role.setActive(false);
		role.getGui().getHomeScreen().removeGui(role.getGui());
		gui.getHomeScreen().addGui(gui);
		gui.DoGoToLocation(new Location(200,200));
		roles.remove(role);
		checkPersonScheduler = true;
		stateChanged();
	}

	public void enteringBuilding(Role role){
		roles.add(role);
		role.setActive(true);
		gui.getHomeScreen().removeGui(gui);
		role.getGui().getHomeScreen().addGui(role.getGui());
		role.startBuildingMessaging();
	}

	/**
	 * a message from the gui that instructs the person to go to a certain
	 * restaurant
	 * 
	 * @param r
	 *            Which restaurant the person should head to
	 */

	/*public void eatAtRestaurant(Restaurant r) {
		eatingState = EatingState.EatAtRestaurant;
		stateChanged();
	}*/

	/**
	 * a message from the GUI to eat at home.  But if he lives at the shelter, he can't eat at home.
	 */
	public void eatAtHome() {
		if(!shelter) {
			eatingState = EatingState.EatAtHome;
			stateChanged();
		}
	}

	//Messages from Roles


	/**
	 * a message from HomeResidentRole sends a grocery list of what foods are
	 * needed
	 * 
	 * @param foods
	 *            Currently, the list is of type String, but will be converted
	 *            to a Food class list after Evan creates it
	 */
	public void homeNeedsGroceries(Map<String, Integer> foods) {
		groceryList = foods;
		marketState = MarketState.GetGroceries;
		stateChanged();
	}

	/**
	 * a message from the HomeResidentRole sends a bill to be paid for his/her
	 * home
	 * 
	 * @param rb
	 *            The bill to be paid, in which the initial state is 'NotPaid'
	 */
	public void goPayBill(RentBill rb) {
		rentBills.add(rb);
		stateChanged();
	}

	/**
	 * to notify the person he/she has eaten is needed. It is possible that the
	 * customer doesnt eat the restaurant, so it eating needs to be explicit
	 */
	public void justAte() {
		hungerState = HungerState.NotHungry;
		hungerCount = 0;
		stateChanged();
	}



	//Scheduler
	@Override
	public boolean pickAndExecuteAnAction() {
		// Person Scheduler 

		if(checkPersonScheduler) {
			//if the man has groceries in his hand, let him take them home!
			if(marketState == MarketState.TakeGroceriesHome) {
				goToHome();
				return true;
			}

			//If he's CRRAAAZZY hungry, then eat something first. Then do checks of eating at home versus the restaurant
			if(hungerState == HungerState.Starving) {
				if(moneyState == MoneyState.Low) {
					hungerState = HungerState.FeedingHunger;
					goToHome();
					return true;
				}
				else {
					hungerState = HungerState.FeedingHunger;
					goEatAtRestaurant();
					return true;
				}
			}

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
			if(hungerState == HungerState.Hungry) {
				if(moneyState == MoneyState.Low) {
					hungerState = HungerState.FeedingHunger;
					goToHome();
					return true;
				}
				else {
					hungerState = HungerState.FeedingHunger;
					goEatAtRestaurant();
					return true;
				}
			}

			//Gotta pay the bills!
			for(RentBill rb : rentBills) {
				if(rb.state == RentState.NotPaid){
					goToBank();
					return true;
				}
			}

			//Gotta eat!- Says the GUI
			if(eatingState == EatingState.EatAtHome) {
				goToHome();
				return true;
			}
			else if(eatingState == EatingState.EatAtRestaurant) {
				goEatAtRestaurant();
				return true;
			}

			//If the person is famished, feed the man if he does not have much to do.
			if(hungerState == HungerState.Famished) {
				if(moneyState == MoneyState.Low) {
					hungerState = HungerState.FeedingHunger;
					goToHome();
					return true;
				}
				else {
					hungerState = HungerState.FeedingHunger;
					goEatAtRestaurant();
					return true;
				}
			}

			//Might as well get groceries if I ain't got nothing to do
			if(marketState == MarketState.GetGroceries) {
				goGetGroceries();
				return true;
			}

			//Let me even see if I got money..
			if(/*accountNumber == 0 ||*/ moneyState == MoneyState.Low || moneyState == MoneyState.High) {
				if(currentBuilding != bank){
					goToBank();
					return true;
				}
			}
		}

		//Role Scheduler
		for(Role r : roles) {
				//checkPersonScheduler should be made true anytime a role is done at a building, outside this scheduler
			if(r.isActive()) {
				if(r.pickAndExecuteAnAction()) {
					checkPersonScheduler = false;
					return true;
				}
			}

		}
		
		return false;
	}


	// Actions

	private void goToWork() {

		//animate out of building
		//activeRole.DoLeaveBuilding();

		//animate to desired location
		gui.DoGoToLocation(myJob.workplace.getEntranceLocation());

		try {
			busyWithTask.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		//enter building (thus deleting rect in city and adding rect to workplace)
		roles.add(myJob.role);

		//This loop should be changed to using ActiveRole
		myJob.role.setActive(true);

		checkPersonScheduler = false;

		//add role to building's list of workers
	}

	private void leaveWork() {
		//Use Screen to draw rect outside currentBuilding
		//Use Screen to delete rect inside currentBuilding
		//animate to desired location
		roles.remove(myJob.role);
		//Going home is not a critical section
		gui.DoGoToLocation(myHome.getEntranceLocation());
	}

	private void goToHome() {
		//if inside building and not in home, animate there
		if(currentBuilding != myHome) {
			gui.DoGoToLocation(myHome.getEntranceLocation());
			try {
				busyWithTask.acquire();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//enter building (removing rect from city screen if it is there, adding rect to home if not there)

			/*	Role residentRoleTemp = RoleFactory.makeMeRole(bank.residentRole);
			activeRole = residentRoleTemp;
			roles.add(residentRoleTemp);*/

		}
	}

	private void goEatAtRestaurant() {
		//if inside building and not in current restaurant preference
		//animate outside building
		gui.DoGoToLocation(currentPreference.getEntranceLocation());
		try {
			busyWithTask.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//enter building (removing rect from city screen if it is there, adding rect to home if not there)

		/*Role customerRoleTemp = roles.add(RoleFactory.makeMeRole(currentPreference.restaurantCustomerRole));
		activeRole = customerRoleTemp;
		roles.add(customerRoleTemp);*/

		checkPersonScheduler = false;

		//initial message to host
	}

	private void goGetGroceries() {
		marketState = MarketState.GettingGroceries;
		//if inside building and not in current restaurant preference
		//animate outside building
		for(Market m : markets){
			gui.DoGoToLocation(m.getEntranceLocation());
			break;
		}
		/*	try {
			busyWithTask.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/

		//enter building (removing rect from city screen if it is there, adding rect to home if not there)

		/*Role marketRoleTemp = roles.add(RoleFactory.makeMeRole(currentPreference.marketCustomerRole));
		activeRole = marketRoleTemp;
		roles.add(marketRoleTemp);*/

		checkPersonScheduler = false;

		//initial message to marketCashier
	}

	private void goToBank() {
		//if inside building and not in bank
		//animate outside building to the bank
		currentBuilding = bank;
		System.out.println("X: " + bank.getEntranceLocation().getX());
		System.out.println("Y: " + bank.getEntranceLocation().getY());
		gui.DoGoToLocation(bank.getEntranceLocation());
		try{
			busyWithTask.acquire();
		}
		catch(InterruptedException e){

		}
		
		bankRoleTemp = RoleFactory.makeMeRole("bankCustomer");
		//currentBuilding = bank;
		bankGui = new bankCustomerGui((BankCustomerRole)bankRoleTemp, ScreenFactory.getMeScreen("Bank"));
		bankRoleTemp.setPerson(this);
		bankGui.setHomeScreen(ScreenFactory.getMeScreen("Bank"));
		bankRoleTemp.setActive(true);


		bankRoleTemp.setGui(bankGui);
		//Enter building
		enteringBuilding(bankRoleTemp);
		
		checkPersonScheduler = false;
	}


	public void restart() {
		// TODO Auto-generated method stub

	}

}