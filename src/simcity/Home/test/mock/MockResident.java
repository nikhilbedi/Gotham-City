package simcity.Home.test.mock;



import java.util.List;
import java.util.Map;

import simcity.PersonAgent.RentBill;
import simcity.Home.interfaces.Resident;
import simcity.tests.mock.Mock;
/**
 * A sample MockCustomer built to unit test a CashierAgent.
 *
 * @author Evan Coutre
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
	
	//messages
	@Override
	public void gotHungry() {
		log.add(new LoggedEvent("Received gotHungry from Gui"));
	}
	
	@Override
	public void gotSleepy() {
		log.add(new LoggedEvent("received got sleepy"));
		
	}
	@Override
	public void wakeUp() {
		log.add(new LoggedEvent("wake up"));
		
	}
	
	
	@Override
	public void atMailbox() {
		log.add(new LoggedEvent("animation at mailbox"));
		
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
	
	
	//actions
	@Override
	public void putGroceriesInFridge(Map<String, Integer> groceryBag) {
		log.add(new LoggedEvent("put groceries in fridge"));
		
	}
	@Override
	public void payRent(List<RentBill> rentBills) {
		log.add(new LoggedEvent("pay rent"));
		
	}
	@Override
	public void checkMail() {
		log.add(new LoggedEvent("check mailbox"));
		
	}
	
	@Override
	public void goToMailbox() {
		log.add(new LoggedEvent("go to mailbox"));
		
	}
	@Override
	public void goToBed() {
		log.add(new LoggedEvent("go to bed"));
		
	}
	@Override
	public void returnToHomePosition() {
		log.add(new LoggedEvent("return to home position"));
		
	}
	
	@Override
	public void clearFood() {
		log.add(new LoggedEvent("clear food"));
		
	}
	
	@Override
	public void exitHome() {
		log.add(new LoggedEvent("exit home"));
		
	}	
	@Override
	public void atHome() {
		log.add(new LoggedEvent("at home"));
	}

	@Override
	public void atRoomEntrance() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void atRoomExit() {
		// TODO Auto-generated method stub
		
	}

}
