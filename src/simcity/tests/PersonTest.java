package simcity.tests;

import simcity.PersonAgent;
import simcity.Hacker;
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
	PersonAgent person;
	Hacker hacker;
	Object object;

	/**
	 * This method is run before each test. It is used to instantiate the class variables
	 * for my agent and mocks, etc.
	 */
	public void setUp() throws Exception{
		super.setUp();
		person = new PersonAgent("person"); 
		hacker = new Hacker();
		
	}	
	
	/**
	 * This test is for checking if a person can be instantiated correctly from the GUI
	 */
	public void testOneNormalPersonScenario() {
		//preconditions
		assertTrue("The money allocated when a person is instantiated is zero. It is not.", person.checkMoney() == 0.0);
		assertEquals("Job should be null. It is not", person.getJob(), null);
		assertTrue("The size of roles should be zero", person.getRoles().size() == 0);
		assertTrue("The size of rentBills should be zero. It isnt", person.getRentBills().size() == 0);
		assertEquals("Bank account number should be zero. it isnt", person.getAccountNumber(), 0);
		assertFalse("personScheduler should be false. it isnt", person.getPersonScheduler());
		
		
		//step 1
		assertTrue("The person should be initiated knowing he must create a bank account", person.pickAndExecuteAnAction());
		//assertTrue("The personScheduler should now be true. It isnt", person.getPersonScheduler());
		
		assertTrue("The money allocated when a person is instantiated is zero. It is not.", person.checkMoney() == 0.0);
		assertEquals("Job should be null. It is not", person.getJob(), null);
		assertTrue("The size of roles should be zero", person.getRoles().size() == 0);
		assertTrue("The size of rentBills should be zero. It isnt", person.getRentBills().size() == 0);
		assertEquals("Bank account number should be zero. it isnt", person.getAccountNumber(), 0);
		assertFalse("personScheduler should be false. it isnt", person.getPersonScheduler());
		
		//step 2
		assertFalse("The person should not have anything to do. But he does", person.pickAndExecuteAnAction());
		
		
		//person.setJob("marketCashier", workplace);
		
		assertEquals("Job should now be of type marketCashier. It is not", person.getJob(), "marketCashier");
		
		
	}
}
