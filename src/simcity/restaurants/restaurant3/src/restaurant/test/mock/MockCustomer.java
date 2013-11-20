package simcity.restaurants.restaurant3.src.restaurant.test.mock;


import simcity.restaurants.restaurant3.src.restaurant.Menu;
import simcity.restaurants.restaurant3.src.restaurant.Order;
import simcity.restaurants.restaurant3.src.restaurant.interfaces.Cashier;
import simcity.restaurants.restaurant3.src.restaurant.interfaces.Customer;

/**
 * A sample MockCustomer built to unit test a CashierAgent.
 *
 * @author Monroe Ekilah
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
	//@Override
	//public void HereIsYourTotal(double total) {
		//log.add(new LoggedEvent("Received HereIsYourTotal from cashier. Total = "+ total));
/*
		if(this.getName().toLowerCase().contains("thief")){
			//test the non-normative scenario where the customer has no money if their name contains the string "theif"
			cashier.IAmShort(this, 0);

		}else if (this.name.toLowerCase().contains("rich")){
			//test the non-normative scenario where the customer overpays if their name contains the string "rich"
			cashier.HereIsMyPayment(this, Math.ceil(total));

		}else{
			//test the normative scenario
			cashier.HereIsMyPayment(this, total);
			
		}
		*/
	//}
	@Override
	public void setDonePayingState() {
		log.add(new LoggedEvent("Done paying state"));
		
	}

	//@Override
	//public void HereIsYourChange(double total) {
		//log.add(new LoggedEvent("Received HereIsYourChange from cashier. Change = "+ total));
	//}

	//@Override
//	public void YouOweUs(double remaining_cost) {
		//log.add(new LoggedEvent("Received YouOweUs from cashier. Debt = "+ remaining_cost));
	//}

}
