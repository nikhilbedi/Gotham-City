package simcity.tests;

import simcity.PersonAgent;
import java.util.List;

import junit.framework.*;

/**
 * 
 * This class is a JUnit test class to unit test the CashierAgent's basic interaction
 * with waiters, customers, and the host.
 *
 * @author Nikhil Bedi
 */
public class PersonTest extends TestCase
{

	/**
	 * This method is run before each test. It is used to instantiate the class variables
	 * for my agent and mocks, etc.
	 */
	public void setUp() throws Exception{
		super.setUp();
		PersonAgent person = new PersonAgent("person"); 
	}	
	
	/**
	 * This test is for checking if a person can be instantiated correctly from the GUI
	 */
	public void testOneNormalPersonScenario() {
		//person.setJob();
	}
}
