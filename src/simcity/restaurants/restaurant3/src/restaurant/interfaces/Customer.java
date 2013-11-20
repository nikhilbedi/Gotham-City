package simcity.restaurant_evan.src.restaurant.interfaces;

import simcity.restaurant_evan.src.restaurant.Menu;
import simcity.restaurant_evan.src.restaurant.Order;

/**
 * A sample Customer interface built to unit test a CashierAgent.
 *
 * @author Monroe Ekilah
 *
 */
public interface Customer {
	
	public abstract void gotHungry();
	
	public abstract void msgFollowMeToTable(Menu menu, int tnum);
	
	public abstract void msgWhatWouldYouLike();
	
	public abstract void msgHereIsYourFood();
	
	public abstract void msgHereIsYourCheck(Order o);
	
	public abstract void msgAnimationFinishedGoToSeat();
	
	public abstract void msgAnimationFinishedGoToCashier();
	
	public abstract void msgAnimationFinishedLeaveRestaurant();
	
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

	public abstract void setDonePayingState();

}