package simcity.Home.test.mock;



import simcity.Home.Home;
import simcity.Home.interfaces.Landlord;
import simcity.Home.interfaces.Resident;
import simcity.PersonAgent.RentBill;
import simcity.tests.mock.Mock;

public class MockLandlord extends Mock implements Landlord {

	/**
	 * Reference to the Cashier under test that can be set by the unit test.
	 */
	public Resident resident;
	public EventLog log = new EventLog();
	//public double wallet;

	public MockLandlord(String name) {
		super(name);

	}
	@Override
	public void sendRentBills() {
		log.add(new LoggedEvent("send rent bills"));
	}
	@Override
	public void msgSendRentBills() {
		log.add(new LoggedEvent("received send rent bills"));
		
	}

}
