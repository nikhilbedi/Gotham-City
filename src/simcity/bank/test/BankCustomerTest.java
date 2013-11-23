package simcity.bank.test;

import simcity.PersonAgent;
import simcity.bank.BankCustomerRole;
import simcity.bank.test.mock.MockBankCustomer;
import simcity.bank.test.mock.MockBankGreeter;
import simcity.bank.test.mock.MockBankTeller;
import junit.framework.*;

/**
 * 
 * This class is a JUnit test class to unit test the CashierAgent's basic interaction
 * with waiters, customers, and the host.
 * It is provided as an example to students in CS201 for their unit testing lab.
 *
 * @author Brice Roland
 */
public class BankCustomerTest extends TestCase
{
	//these are instantiated for each test separately via the setUp() method.
	BankCustomerRole bankCustomer;
	MockBankGreeter mockGreeter;
	MockBankTeller mockTeller;
	
	public void setUp() throws Exception{
		super.setUp();
		bankCustomer = new BankCustomerRole(new PersonAgent("testCustomer"));		
		mockGreeter = new MockBankGreeter("mockGreeter");		
		mockTeller = new MockBankTeller("mockTeller");
		bankCustomer.setGreeter(mockGreeter);
		bankCustomer.setTeller(mockTeller);	
	}
	
	/**
	 * This tests the cashier under very simple terms: The cashier waiter list is checked and the mockWaiter is messaged
	 */
	public void testOneNormalWaiterScenario()
	{
		
		assertTrue("No messages were given but the customer should be waiting and send a message to the greeter, "
				+ "meaning pickAndExecute should be true. It is not.",bankCustomer.pickAndExecuteAnAction());
		
		bankCustomer.msgGoToTeller(mockTeller);
		
		assertTrue("", bankCustomer.getBankTeller() == mockTeller);
		
		//check preconditions
		
	}//end one normal customer scenario
}
