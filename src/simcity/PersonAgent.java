package simcity;

import agent.Agent;
import agent.Role;
import Gui.RoleGui;
import Gui.Screen;
import Gui.ScreenFactory;
import simcity.interfaces.Person;
import simcity.RoleFactory;
import simcity.restaurants.*;
import simcity.restaurants.restaurant3.Restaurant3CustomerRole;
import simcity.restaurants.restaurant3.gui.Restaurant3CustomerGui;
import simcity.restaurants.restaurant4.Restaurant4;
import simcity.restaurants.restaurant4.Restaurant4CustomerRole;
import simcity.restaurants.restaurant4.Restaurant4Gui.Restaurant4CustomerGui;
import simcity.restaurants.restaurant5.Restaurant5CustomerRole;
import simcity.restaurants.restaurant5.gui.Restaurant5CustomerGui;
import simcity.restaurants.restaurant1.Restaurant1CustomerRole;
import simcity.restaurants.restaurant1.gui.Restaurant1CustomerGui;
import simcity.restaurants.restaurant2.Restaurant2CustomerRole;
import simcity.restaurants.restaurant2.gui.Restaurant2CustomerGui;
import simcity.Market.Market;
import simcity.Market.MarketCustomerRole;
import simcity.Market.MarketGui.MarketCustomerGui;
import simcity.Market.interfaces.MarketCustomer;
import simcity.Home.Home;
import simcity.Home.LandlordRole;
import simcity.Home.ResidentRole;
import simcity.Home.gui.ResidentGui;
import simcity.bank.*;
import trace.AlertLog;
import trace.AlertTag;

import java.util.*;
import java.util.concurrent.Semaphore;

public class PersonAgent extends Agent implements Person {

	// TODO REMOVE THIS
	Role bankRoleTemp;
	RoleGui bankGui;
	public Role marketRoleTemp;
	RoleGui marketGui;
	Role homeTemp;
	RoleGui homeGui;
	Role restTemp;
	RoleGui restGui;
	

	public String name;
	int currentTime; // (ranges from 1-24)
	public int accountNumber; // Not currently sure how we're using account
	// numbers, but the person should know it if
	// we're removing that role
	Semaphore busyWithTask = new Semaphore(0, false);
	double money = 0.0;
    int restaurantCounter = (int)(Math.random()*5);
	protected List<Role> roles = new ArrayList<Role>();
	public Map<String, Integer> groceryList = new HashMap<String, Integer>();
	public Map<String, Integer> groceryBag = new HashMap<String, Integer>();
	public List<RentBill> rentBills = new ArrayList<RentBill>();
	public boolean checkPersonScheduler = true;
	PersonGui gui;

	private LandlordRole landlord;


	// Locations
	// These buildings will be set when any person is added
	public List<Restaurant> restaurants;
	public Restaurant currentPreference;

	Role activeRole;

	public Bank bank;

	// These three are essential, but should be instantiated with the
	// "Homeless Shelter" spawnpoint
	private boolean shelter = false;
	private Building spawnPoint = new Building("bat cave");
	public Home myHome;
	private List<Market> markets;
	public Building currentBuilding = spawnPoint;
	public Building currentDestination = spawnPoint;

	// Need to implement going to bank to open account

	// States - Currently the states are private. If need be, we can change them
	// to public so our roles can see them

	// The day of the week
	public enum DayOfTheWeek {
		Sunday, Monday, Tuesday, Wednesday, Thursday, Friday, Saturday
	};

	int day = 0;
	public DayOfTheWeek dayState = DayOfTheWeek.values()[day];

	// Preferred Transportation
	public enum TransportationState {
		Walking, Bus, Car
	};

	public TransportationState transportationState = TransportationState.Walking;

	// Where to eat
	public enum EatingState {
		EatAtHome, HeadedToHome, EatingAtHome, Nowhere, EatAtRestaurant, HeadedtoRestaurant, EatingAtRestaurant
	};

	public EatingState eatingState = EatingState.Nowhere;

	// When to eat
	public enum HungerState {
		NotHungry, Famished, Hungry, Starving, FeedingHunger
	};

	public int hungerCount = 0;
	public HungerState hungerState = HungerState.NotHungry;

	// Going to the market states

	public enum MarketState {
		GetGroceries, GettingGroceries, HaveGroceries, TakeGroceriesHome, TakingGroceriesHome
	};

	public MarketState marketState = MarketState.HaveGroceries;

	// Keep track of money
	public enum MoneyState {
		Low, High, Neutral
	};
	public MoneyState moneyState = MoneyState.Neutral;

	// Job
	private Job myJob = null;
	public enum JobState {
		OffWork, GoToWorkSoon, HeadedToWork, AtWork, TimeToLeave, 
		PreparingToLeave, LeaveWork, LeavingWork, 
	};


	// Paying Rent
	// When to pay rent
	public enum RentState {
		Paid, NotPaid, PayingBill
	};

	public class RentBill {
		public RentState state = RentState.NotPaid;
		public PersonAgent accountHolder;
		public float amount;

		public RentBill(PersonAgent p, float a) {
			accountHolder = p;
			amount = a;
		}
	}

	// constructors

	public PersonAgent(String name) {
		super();
		this.name = name;
		// gui.setHomeScreen(s);
	}

	public PersonAgent(String name, PersonGui g, Screen s) {
		super();
		this.name = name;
		gui = g;
		gui.setHomeScreen(s);
	}

	public void setRestaurants(List<Restaurant> r) {
		restaurants = r;
		/*
		 * for(Restaurant rest : restaurants) {
		 * /*if(rest.getName().equals("Restaurant 4")) { currentPreference =
		 * rest; } }
		 */
		//currentPreference = r.get(restaurantCounter);
		restaurantCounter++;
	}

	// essential setters for GUI (When adding a person to SimCity)
	public void setGui(PersonGui g) {
		gui = g;
	}

	public void setMarkets(List<Market> m) {
		markets = m;
	}
	
	public List<Market> getMarkets(){
		return markets;
	}

	public void setBank(Bank b) {
		bank = b;
	}

	/**
	 * 
	 * @param h
	 *            The home (or shelter) the person will reside.
	 */
	public void setHome(Home h) {

/*		if (h.getName().contains("shelter")) {
			shelter = true;
		} else {
			setMyHome(h);
            // currentBuilding = h;
			currentDestination = h;
			// Should I make a new one, or just make it equal to this one? There
			// is only one resident for a home...
			// activeRole = myHome.resident;
		}*/
	}

	// functions so we can function
	public void setHomeOwnerRole() {
		// When Evan is done with homeowner role, I can add this
	}

	public double getMoney() {
		return money;
	}

	public void addMoney(double amount) {
		if (amount >= 0)
			money += amount;
	}

	public void removeMoney(double amount) {
		money -= amount;
	}

	public void setMoney(double amount) {
		money = amount;
	}

	public Location getLocation() {
		return currentBuilding.getEntranceLocation();
	}

	public void setJob(Role role, Building building, int shift) {
		myJob = new Job(role, building, shift);
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
		return money;
	}

	public String getName() {

		return name;
	}

	public void addRole(Role role) {
		roles.add(role);
		// stateChanged();
	}

	public void removeRole(Role role) {
		roles.remove(role);
	}

	public void setPreferredTransportation(String type) {
		if (type.equalsIgnoreCase("car"))
			transportationState = TransportationState.Car;
		else if (type.equalsIgnoreCase("bus"))
			transportationState = TransportationState.Bus;
		else
			transportationState = TransportationState.Walking;
	}

	// Messages from World Clock
	public void updateTime(int time) {
		// print("AVAILABLE PERMITS: " + busyWithTask.availablePermits());
		currentTime = time;
		// Another hour, another chance to eat ;)
		hungerCount++;
		// print("Checking my watch and it is " + time + " o' clock");
		// NEED TO CHECK IF THIS PERSON IS A HOMEOWNER. IF SO, MAKE THAT ROLE
		// ACTIVE IF NO OTHER ROLE IS ACTIVE
		if (landlord != null) {
			// landlord.updateCurrentTime(time);
			/*
			 * if(landord.timeIsUp()) { landlord.setActive(true); } else
			 * landlord.setActive(false);
			 */
		}

		// Next Day
		if (currentTime == 1) {
			//I would just get rid of the day variable, but I already use it in so many places, so I'll just set it.
			day = CityClock.getDay();
			dayState = DayOfTheWeek.values()[day];
			print("The day of the week is " + dayState.name());
		}

		if (hungerCount > 11 && hungerState != HungerState.Starving
				&& hungerState != HungerState.FeedingHunger) {
			hungerState = HungerState.Starving;
		} else if (hungerCount > 7 && hungerState != HungerState.Hungry
				&& hungerState != HungerState.FeedingHunger
				&& hungerState != HungerState.Starving) {
			hungerState = HungerState.Hungry;
		} else if (hungerCount > 3 && hungerState != HungerState.Famished
				&& hungerState != HungerState.FeedingHunger
				&& hungerState != HungerState.Starving
				&& hungerState != HungerState.Hungry) {
			hungerState = HungerState.Famished;
		}

		if (myJob != null) {
			if(day != 0 && day != 6) {
				if (currentTime == myJob.weekDayOnWork &&
						myJob.state == JobState.OffWork) {
					myJob.state = JobState.GoToWorkSoon;
				}
				// Maybe, also check if our current state is atWork
				else if (currentTime == myJob.weekDayOffWork &&
						myJob.state == JobState.AtWork) {
					myJob.state = JobState.TimeToLeave;
					// Need to now check the person scheduler so we leave work
					checkPersonScheduler = true;
				}
			}
			else {
				if (currentTime == myJob.weekEndOnWork) {
					myJob.state = JobState.GoToWorkSoon;
				}
				// Maybe, also check if our current state is atWork
				else if (currentTime == myJob.weekEndOffWork) {
					myJob.state = JobState.TimeToLeave;
					// Need to now check the person scheduler so we leave work
					checkPersonScheduler = true;
				}
			}
		}

		// every "hour", let's check how much money is in our wallet. (temporary
		// low and highs)

		double low = 25.0;
		double high = 150.0;
		if (money <= low && moneyState != MoneyState.Low) {
			moneyState = MoneyState.Low;
			// stateChanged();
		} else if (money >= high && moneyState != MoneyState.High) {
			moneyState = MoneyState.High;
			// stateChanged();
		} else {
			moneyState = MoneyState.Neutral;
		}
		stateChanged();
	}

	// Messages from User Interface or Animation or Roles
	public void doneWithWork() {
		myJob.state = JobState.LeaveWork;
		checkPersonScheduler = true;
	}
	
	/**
	 * 
	 */
	public void reachedBuilding() {
		currentBuilding = currentDestination;
		busyWithTask.release();
	}

	/**
	 * Notifies the person that the current role is done with all interactions
	 * in the restaurant
	 * 
	 * @param role
	 */
	public void leftBuilding(Role role) {
		checkPersonScheduler = true;
		role.setActive(false);
		role.getGui().getHomeScreen().removeGui(role.getGui());
		gui.getHomeScreen().addGui(gui);
		roles.remove(role);
		stateChanged();
	}

	public void enteringBuilding(Role role) {
		checkPersonScheduler = false;
		role.setPerson(this);
		roles.add(role);
		role.setActive(true);
		gui.getHomeScreen().removeGui(gui);
		role.getGui();//debug
		AlertLog.getInstance().logInfo(AlertTag.PERSON, "PersonAgent",
				"Role's Screen is " + role.getGui().getHomeScreen());
		role.getGui().getHomeScreen();//debug
		
		role.getGui().getHomeScreen().addGui(role.getGui());
		
		//TODO put this back in when ready to start messaging but for now leave out
		//role.startBuildingMessaging();
		stateChanged();
	}

	/**
	 * a message from the gui that instructs the person to go to a certain
	 * restaurant
	 * 
	 * @param r
	 *            Which restaurant the person should head to
	 */

	/*
	 * public void eatAtRestaurant(Restaurant r) { eatingState =
	 * EatingState.EatAtRestaurant; stateChanged(); }
	 */

	/**
	 * a message from the GUI to eat at home. But if he lives at the shelter, he
	 * can't eat at home.
	 */

	public void eatAtHome() {
		if (!shelter) {
			eatingState = EatingState.EatAtHome;
			stateChanged();
		}
	}

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

	// Scheduler

	@Override
	public boolean pickAndExecuteAnAction() {

		// Person Scheduler

		if (checkPersonScheduler) {
			if(true){
				//currentPreference = restaurants.get(0);
				goToWork();
				return true;
			}
			//Time to leave, yo.
			if (myJob != null) {
				if (myJob.state == JobState.TimeToLeave) {
					myJob.state = JobState.PreparingToLeave;
					tellRoleToLeaveWork();
					return true;
				}
			//Clockin' out, yo.
				if (myJob.state == JobState.LeaveWork) {
					myJob.state = JobState.LeavingWork;
					leaveWork();
					return true;
				}
			}
			
			// if the man has groceries in his hand, let him take them home!

			// print("person sched");
			

			if (marketState == MarketState.TakeGroceriesHome) {
				marketState = MarketState.TakingGroceriesHome;
				goToHome();
				return true;
			}

			// If he's CRRAAAZZY hungry, then eat something first. Then do
			// checks of eating at home versus the restaurant
			if (hungerState == HungerState.Starving
					&& marketState != MarketState.GetGroceries
					&& marketState != MarketState.GettingGroceries) {
				if (moneyState == MoneyState.Low) {
					hungerState = HungerState.FeedingHunger;
					goToHome();
					return true;
				} else {
					hungerState = HungerState.FeedingHunger;
					goEatAtRestaurant();
					return true;
				}
			}

			// Work comes first--his family probably doesn't like this :/
			if (myJob != null) {
				if (myJob.state == JobState.GoToWorkSoon) {
					myJob.state = JobState.HeadedToWork;
					goToWork();
					return true;
				}
			}

			// if he's REALLY hungry, then eat something before paying bills.
			// Then do checks of eating at home versus the restaurant
			if (hungerState == HungerState.Hungry) {
				if (moneyState == MoneyState.Low) {
					hungerState = HungerState.FeedingHunger;
					goToHome();
					return true;
				} else {
					hungerState = HungerState.FeedingHunger;
					goEatAtRestaurant();
					return true;
				}
			}

			// Gotta pay the bills!
			for (RentBill rb : rentBills) {
				if (rb.state == RentState.NotPaid) {
					goToBank();
					return true;
				}
			}

			// Gotta eat!- Says the GUI

			if (eatingState == EatingState.EatAtHome) {
				goToHome();
				return true;
			} else if (eatingState == EatingState.EatAtRestaurant) {
				goEatAtRestaurant();
				return true;
			}

			// If the person is famished, feed the man if he does not have much
			// to do.
			if (hungerState == HungerState.Famished) {
				if (moneyState == MoneyState.Low) {
					hungerState = HungerState.FeedingHunger;
					goToHome();
					return true;
				} else {
					hungerState = HungerState.FeedingHunger;
					goEatAtRestaurant();
					return true;
				}
			}

			// Might as well get groceries if I ain't got nothing to do
			if (marketState == MarketState.GetGroceries) {
				goGetGroceries();
				return true;
			}

			// Let me even see if I got money..
			if (moneyState == MoneyState.Low || moneyState == MoneyState.High) {
				if (currentBuilding != bank) {
					goToBank();
					return true;
				}
			}
			if(currentBuilding != getMyHome()) {
				goToHome();
				return true;
			}
		}

		// Role Scheduler

		for (Role r : roles) {
			// checkPersonScheduler should be made true anytime a role is done
			// at a building, outside this scheduler
			if (r.isActive()) {
				if (r.pickAndExecuteAnAction()) {
					// checkPersonScheduler = false;
					return true;
				}
			}
		}

		return false;
	}

	// Actions
	private void goToWork() {
		print("Going to work.");
		// animate to desired location
		gui.DoGoToLocation(myJob.workplace.getEntranceLocation());
		try {
			busyWithTask.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		myJob.state = JobState.AtWork;
		checkPersonScheduler = false;
		
		//I hacked this to get stuff to work unhack it plz
		/*if(myJob.role.checkWorkStatus()) {
			waitToStartWork();
		}*/
		enteringBuilding(myJob.role);
			myJob.role.setWorkStatus(true);
		
	}
	
	private void waitToStartWork() {
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			public void run() {
				if(myJob.role.checkWorkStatus()) {
					waitToStartWork();
				}
				else {
					enteringBuilding(myJob.role);
					myJob.role.setWorkStatus(true);
				}
			}
		},
		500);
	}
	
	private void tellRoleToLeaveWork() {
		myJob.role.setWorkStatus(false);
		myJob.role.getReadyToLeave(this);
		checkPersonScheduler = false;
	}

	private void leaveWork() {
		// Upon leaving work, person gains set amount of money in his wallet
		money += 100; //20 dollar wage * hours worked
		print("leaving work now.");

		// Going home is not a critical section
		gui.getHomeScreen().addGui(gui);
		gui.DoGoToLocation(myHome.getEntranceLocation());

		myJob.state = JobState.OffWork;

		checkPersonScheduler = true;
		if(myJob.role.getPersonAgent() == this){
			myJob.role.setActive(false);
			myJob.role.getGui().getHomeScreen().removeGui(myJob.role.getGui());
		}

		roles.remove(myJob.role);
	}

	private void goToHome() {

		// Since there are not enough homes, some people will be left with a
		// "null" home. Send them to a default location in the corner
		if (myHome != null) {
			// if inside building and not in home, animate there
			if (currentBuilding != myHome) {
				gui.DoGoToLocation(myHome.getEntranceLocation());
				try {
					// print("Available permits: " +
					// busyWithTask.availablePermits());
					busyWithTask.acquire();
					// busyWithTask.acquire();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
	
			homeTemp = RoleFactory.makeMeRole("residentRole");
			homeTemp = getMyHome().getResident();
			homeTemp.setActive(true);
			currentBuilding = getMyHome();
			homeGui = new ResidentGui((ResidentRole)homeTemp, ScreenFactory.getMeScreen(myHome.getName()));
			homeGui.setHomeScreen(ScreenFactory.getMeScreen(myHome.getName()));

//			//Add role
	
			homeTemp.setGui(homeGui);
			// Enter building
			homeTemp.setPerson(this);
			enteringBuilding(homeTemp);
			//hacking TODO
			enteringBuilding(myHome.getResident());
			checkPersonScheduler = false;
		} else {
			gui.DoGoToLocation(new Location(26, 580, "Default"));
			try {
				// print("Available permits: " +
				// busyWithTask.availablePermits());
				busyWithTask.acquire();
				// busyWithTask.acquire();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private void goEatAtRestaurant() {
		// if inside building and not in current restaurant preference
		// animate outside building
		gui.DoGoToLocation(currentPreference.getEntranceLocation());
		eatingState = EatingState.HeadedtoRestaurant;
		try {
			busyWithTask.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 5 if statements, to check which restaurant customer needs to be cast
		// :)
		if (currentPreference.getName().equalsIgnoreCase("Restaurant 1")) {
			restTemp = RoleFactory.makeMeRole(currentPreference
					.getCustomerName());
			restGui = new Restaurant1CustomerGui(
					(Restaurant1CustomerRole) restTemp,
					ScreenFactory.getMeScreen(currentPreference.getName()));
		} else if (currentPreference.getName().equalsIgnoreCase("Restaurant 3")) {
			print("currentPreference = restaurant 3");
			restTemp = RoleFactory.makeMeRole(currentPreference
					.getCustomerName());
			restGui = new Restaurant3CustomerGui(
					(Restaurant3CustomerRole) restTemp,
					ScreenFactory.getMeScreen(currentPreference.getName()));
		} else if (currentPreference.getName().equalsIgnoreCase("Restaurant 2")) {
			restTemp = RoleFactory.makeMeRole(currentPreference
					.getCustomerName());
			restGui = new Restaurant2CustomerGui(
					(Restaurant2CustomerRole) restTemp,
					ScreenFactory.getMeScreen(currentPreference.getName()));
			// restGui = new Restaurant2CustomerGui(restTemp,
			// ScreenFactory.getMeScreen(currentPreference.getName()));
		} else if (currentPreference.getName().equalsIgnoreCase("Restaurant 4")) {
			restTemp = RoleFactory.makeMeRole(currentPreference
					.getCustomerName());
			restGui = new Restaurant4CustomerGui(
					(Restaurant4CustomerRole) restTemp,
					ScreenFactory.getMeScreen(currentPreference.getName()));
		} else if (currentPreference.getName().equalsIgnoreCase("Restaurant 5")) {
			restTemp = RoleFactory.makeMeRole(currentPreference
					.getCustomerName());
			restGui = new Restaurant5CustomerGui(
					(Restaurant5CustomerRole) restTemp,
					ScreenFactory.getMeScreen(currentPreference.getName()));
		} else {

		}

		// rest1Temp = RoleFactory.makeMeRole("Restaurant4Customer");
		// //currentPreference.getCustomerName()

		//restTemp.setPerson(this);
		//restGui.setHomeScreen(ScreenFactory.getMeScreen(currentPreference
		//	.getName()));
		// print("here:"+ currentPreference.getName());

		checkPersonScheduler = false;
		restTemp.setGui(restGui);
		// Enter building
		currentBuilding = currentPreference;
		enteringBuilding(restTemp);

		restaurantCounter++;
		if (restaurantCounter > 4)
			restaurantCounter = 0;
		currentPreference = restaurants.get(restaurantCounter);
	}

	private void goGetGroceries() {
		marketState = MarketState.GettingGroceries;
		// if inside building and not in current restaurant preference
		// animate outside building
		for (Market m : markets) {
			gui.DoGoToLocation(m.getEntranceLocation());
			break;
		}
		try {
			busyWithTask.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		marketRoleTemp = RoleFactory.makeMeRole("marketCustomer");
		currentBuilding = markets.get(0);
		MarketCustomerRole tempMarketCustomerRole = (MarketCustomerRole) marketRoleTemp;
		marketGui = new MarketCustomerGui((MarketCustomerRole) marketRoleTemp,
				ScreenFactory.getMeScreen("Market"));
		marketRoleTemp.setPerson(this);
		marketGui.setHomeScreen(ScreenFactory.getMeScreen("Market"));
		activeRole = marketRoleTemp;

		checkPersonScheduler = false;
		// Add role
		roles.add(marketRoleTemp);
		marketRoleTemp.setGui(marketGui);
		// Enter building
		enteringBuilding(marketRoleTemp);

		// initial message to marketCashier
	}

	private void goToBank() {
		// if inside building and not in bank
		// animate outside building to the bank

		System.out.println("X: " + bank.getEntranceLocation().getX());
		System.out.println("Y: " + bank.getEntranceLocation().getY());
		// print("Current permits when going to bank are: " +
		// busyWithTask.availablePermits());
		currentDestination = bank;
		gui.DoGoToLocation(bank.getEntranceLocation());
		try {
			busyWithTask.acquire();
		} catch (InterruptedException e) {

		}
		currentBuilding = bank;

		bankRoleTemp = RoleFactory.makeMeRole("bankCustomer");
		// currentBuilding = bank;
		bankGui = new bankCustomerGui((BankCustomerRole) bankRoleTemp,
				ScreenFactory.getMeScreen("Bank"));
		bankRoleTemp.setPerson(this);
		bankGui.setHomeScreen(ScreenFactory.getMeScreen("Bank"));

		bankRoleTemp.setGui(bankGui);
		// Enter building
		enteringBuilding(bankRoleTemp);

		checkPersonScheduler = false;

	}

	public void restart() {
		// TODO Auto-generated method stub

	}

	public Map<String, Integer> getGroceryBag() {

		return groceryBag;
	}

	public Building getCurrentBuilding() {
		return currentBuilding;
	}

	public void setCurrentBuilding(Building currentBuilding) {
		this.currentBuilding = currentBuilding;
	}

	public Home getMyHome() {
		return myHome;
	}

	public void setMyHome(Home myHome) {
		this.myHome = myHome;
	}
}