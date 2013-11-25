package simcity.interfaces;

import java.util.List;
import java.util.Map;

import simcity.Building;
import simcity.PersonAgent.RentBill;
import agent.Role;


public interface Person {

	public abstract void addRole(Role role);
	
	public abstract void removeRole(Role role);
	
	public abstract double checkMoney();
	
	public abstract void addMoney(float amount);
	
	public abstract void setJob(Role role, Building building);
	
	public abstract void setPreferredTransportation(String type);

	
	//Messages from World Clock
	public abstract void updateTime(int time);
	
	//Messages from GUI
	/**
	 * a message from the gui that instructs the person to go to a certain restaurant
	 * @param r Which restaurant the person should head to
	 */
	/*public void eatAtRestaurant(Restaurant r) {
		eatingState = EatingState.EatAtRestaurant;
		stateChanged();
	}*/
	
	public abstract void eatAtHome();
	
	
	//Messages from Roles
	/**
	 * a message from HomeResidentRole sends a grocery list of what foods are needed
	 * @param foods Currently, the list is of type String, but will be converted to a Food class list after Evan creates it
	 */
	public abstract void homeNeedsGroceries(Map<String, Integer> foods);
	
	/**
	 * a message from the HomeResidentRole sends a bill to be paid for his/her home
	 * @param rb The bill to be paid, in which the initial state is 'NotPaid'
	 */
	public void goPayBill(RentBill rb);
	
	/**
	 * to notify the person he/she has eaten is needed.  It is possible that the customer doesnt eat the restaurant, so it eating needs to be explicit
	 */
	public abstract void justAte();
	
	/**
	 * Notifies the person that the current role is done with all interactions in the restaurant
	 * @param role
	 */
	public abstract void leftBuilding(Role role);
}
