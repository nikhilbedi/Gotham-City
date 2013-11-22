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
	public void AtTable() {
		log.add(new LoggedEvent("animation at table"));
		
	}
	@Override
	public void atSink() {
		log.add(new LoggedEvent("animation at sink"));
		
	}
	@Override
	public void atPlatingArea() {
		log.add(new LoggedEvent("animation at plating area"));
		
	}
	@Override
	public void atStove() {
		log.add(new LoggedEvent("animation at stove"));
		
	}
	@Override
	public void atBed() {
		log.add(new LoggedEvent("animation at bed"));
		
	}
	@Override
	public void atFridge() {
		log.add(new LoggedEvent("animation at fridge"));
		
	}
	@Override
	public void exited() {
		log.add(new LoggedEvent("animation exited home"));
		
	}
	@Override
	public void payRent() {
		log.add(new LoggedEvent("received pay rent"));
		
	}
	@Override
	public void checkMailbox() {
		log.add(new LoggedEvent("received check mailbox"));
		
	}

}
