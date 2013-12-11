package simcity.interfaces;

import java.util.List;
import java.util.Map;

import simcity.Building;
import simcity.Location;
import simcity.PersonGui;
import simcity.Home.Home;
import simcity.Home.LandlordRole;
import simcity.Market.Market;
import simcity.PersonAgent.RentBill;
import simcity.bank.Bank;
import simcity.restaurants.Restaurant;
import simcity.restaurants.restaurant4.Restaurant4;
import agent.Role;


public interface Person {
	
	public abstract void setGui(PersonGui g);

	public abstract void setRestaurants(List<Restaurant> r);
	public abstract void setMarkets(List<Market> m);

	public abstract void setBank(List<Bank> b);

	public abstract double checkMoney();
	/**
	 * 
	 * @param h The home (or shelter) the person will reside.
	 */
	public abstract void setHome(Home h);


	//functions so we can function
	public abstract void setHomeOwnerRole(LandlordRole role);
	
	public void addMoney(double amount);

	public abstract Location getLocation();

	public abstract void setJob(Role role, Building building, int shift);

	//public abstract void setJob(String type, Building building, int shift);

	public abstract String getJob();
	public abstract int getCurrentTime();

	public abstract void setCurrentTime(int currentTime);

	public abstract int getAccountNumber();
	
	public abstract void setAccountNumber(int accountNumber);
	

	public abstract boolean getPersonScheduler();

	public abstract List<Role> getRoles();

	public abstract List<RentBill> getRentBills();

	public abstract String getName();

	public abstract void addRole(Role role);

	public abstract void removeRole(Role role);

	public abstract void setPreferredTransportation(String type);


	//Messages from World Clock
	public abstract void updateTime(int time);


	//Messages from User Interface or Animation
	/**
	 * 
	 */
	public abstract void reachedBuilding();

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
	public abstract void leftBuilding(Role role);

	public abstract void enteringBuilding(Role role);
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
	public abstract void eatAtHome();
	
	//Messages from Roles


	/**
	 * a message from HomeResidentRole sends a grocery list of what foods are
	 * needed
	 * 
	 * @param foods
	 *            Currently, the list is of type String, but will be converted
	 *            to a Food class list after Evan creates it
	 */
	public abstract void homeNeedsGroceries(Map<String, Integer> foods);
	/**
	 * a message from the HomeResidentRole sends a bill to be paid for his/her
	 * home
	 * 
	 * @param rb
	 *            The bill to be paid, in which the initial state is 'NotPaid'
	 */
	public abstract void goPayBill(RentBill rb);

	/**
	 * to notify the person he/she has eaten is needed. It is possible that the
	 * customer doesnt eat the restaurant, so it eating needs to be explicit
	 */
	public abstract void justAte();
}
