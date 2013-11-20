package simcity.Home.test.mock;



import simcity.Home.interfaces.Resident;
import simcity.tests.mock.Mock;
/**
 * A sample MockCustomer built to unit test a CashierAgent.
 *
 * @author Monroe Ekilah
 *
 */
public class MockResident extends Mock implements Resident {

	/**
	 * Reference to the Cashier under test that can be set by the unit test.
	 */
	public Resident resident;
	public EventLog log = new EventLog();
	//public double wallet;

	public MockResident(String name) {
		super(name);

	}
	@Override
	public void gotHungry() {
		log.add(new LoggedEvent("Received gotHungry from Gui"));
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

}
