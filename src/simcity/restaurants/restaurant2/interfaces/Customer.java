package simcity.restaurants.restaurant2.interfaces;

import simcity.restaurants.restaurant2.Check;
import simcity.restaurants.restaurant2.Menu;
import simcity.restaurants.restaurant2.gui.Restaurant2CustomerGui;

public interface Customer {
	/**
	 * @param total The cost according to the cashier
	 *
	 * Sent by the cashier prompting the customer's money after the customer has approached the cashier.
	 */
	//public abstract void HereIsYourTotal(double total);

	/**
	 * @param total change (if any) due to the customer
	 *
	 * Sent by the cashier to end the transaction between him and the customer. total will be >= 0 .
	 */
	//public abstract void HereIsYourChange(double total);


	/**
	 * @param remaining_cost how much money is owed
	 * Sent by the cashier if the customer does not pay enough for the bill (in lieu of sending {@link #HereIsYourChange(double)}
	 */
	//public abstract void YouOweUs(double remaining_cost);
	
	
	public abstract void gotHungry();
	
	public abstract void msgFollowMe(Menu m, Waiter w);
	
	public abstract void msgSitAtTable(int tableNumber);

	public abstract void msgWhatWouldYouLike();
	
	public abstract void msgOutOfChoice(Menu m);
	
	public abstract void msgHereIsYourFood(String choice);
	
	public abstract void msgHereIsYourCheck(Check c);
	
	public abstract void msgAnimationFinishedGoToSeat();
	
	public abstract void msgAnimationFinishedLeaveRestaurant();
	
	public abstract void msgAnimationFinishedGoToCashier();
	
	
	//hacks
	public abstract String getName();

	public abstract Restaurant2CustomerGui getGui();

	public abstract double getCash();

	public abstract void setCash(double d);

	public abstract void addDebt(double abs);

	public abstract double getDebt();

	public abstract void msgWaitHere(int i);

	public abstract int getWaitingNumber();
}