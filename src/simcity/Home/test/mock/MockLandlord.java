package simcity.Home.test.mock;



import simcity.Home.interfaces.Landlord;
import simcity.Home.interfaces.Resident;
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
	
	

}
