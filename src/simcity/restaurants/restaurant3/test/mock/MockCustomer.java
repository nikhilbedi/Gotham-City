package simcity.restaurants.restaurant3.test.mock;


import simcity.restaurants.restaurant3.*;
import simcity.restaurants.restaurant3.interfaces.*;
import simcity.tests.mock.*;

/**
 *
 *
 * @author Evan Coutre
 *
 */
public class MockCustomer extends Mock implements Customer {

	/**
	 * Reference to the Cashier under test that can be set by the unit test.
	 */
	public Cashier cashier;
	public EventLog log = new EventLog();
	public double wallet;

	public MockCustomer(String name) {
		super(name);

	}
	@Override
	public void gotHungry() {
		log.add(new LoggedEvent("Received gotHungry from Gui"));
	}
	
	@Override
	public void msgFollowMeToTable(Menu menu, int tnum) {
		log.add(new LoggedEvent("Received msgFollowMeToTable from waiter"));
	}
	
	@Override
	public void msgWhatWouldYouLike() {
		log.add(new LoggedEvent("Received msgWhatWouldYouLike from waiter"));
	}
	
	@Override
	public void msgHereIsYourFood() {
		log.add(new LoggedEvent("Received msgHereIsYourFood from waiter"));
	}
	
	@Override
	public void msgHereIsYourCheck(Order o) {
		log.add(new LoggedEvent("Received msgHereIsYourCheck from waiter. Total = " + o.choice.getFoodPrice()));
	}
	
	@Override
	public void msgAnimationFinishedGoToSeat() {
		log.add(new LoggedEvent("Animation finished go to seat"));
	}
	
	@Override
	public void msgAnimationFinishedGoToCashier() {
		log.add(new LoggedEvent("Animation finished go to cashier"));
	}
	
	@Override
	public void msgAnimationFinishedLeaveRestaurant() {
		log.add(new LoggedEvent("Animation finished leave restaurant"));
	}
	
	@Override
	public void setDonePayingState() {
		log.add(new LoggedEvent("Done paying state"));
		
	}
	@Override
	public void setWaiter(WaiterRole waiter) {
		// TODO Auto-generated method stub
		
	}


}
