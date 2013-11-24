package simcity.bank.test;

import Gui.RoleGui;
import Gui.ScreenFactory;
import simcity.PersonAgent;
import simcity.bank.Bank;
import simcity.bank.BankCustomerRole;
import simcity.bank.bankCustomerGui;
import simcity.bank.BankCustomerRole.CustomerState;
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
		Bank b = new Bank("Bank", 0, 0, 0, 0);
		bankCustomer.getPersonAgent().setBank(b);
		bankCustomer.setGui(new bankCustomerGui((BankCustomerRole)bankCustomer, ScreenFactory.getMeScreen("Bank")));
		mockGreeter = new MockBankGreeter("mockGreeter");		
		mockTeller = new MockBankTeller("mockTeller");
		bankCustomer.setGreeter(mockGreeter);
		bankCustomer.setBankTeller(mockTeller);
		b.setGreeter(mockGreeter);
	}
	
	public void testOneNormalCustomerScenario()
	{
		//Check initial conditions
		assertTrue("The transactionList should be empty at the customer's creation. It is not.", 
				bankCustomer.transactionList.size() == 0);
		assertTrue("Customer state to begin with should be temp. It's currently not.", 
				bankCustomer.state == CustomerState.temp);
		assertTrue("Name should be testCustomer. It is instead " + bankCustomer.getName(), 
				bankCustomer.getName().equals("testCustomer"));
		
		//step 1
		
		bankCustomer.setTransactions();
		assertTrue("Transaction list should be increased after setTransactions. It is not. Instead the size is: " + 
				bankCustomer.transactionList.size(), bankCustomer.transactionList.size() > 0);
		bankCustomer.setGreeter(mockGreeter);
		assertTrue("Greeter should be set to mockGreeter and not null.", bankCustomer.getBankGreeter().equals(mockGreeter));
		bankCustomer.setBankTeller(mockTeller);
		assertTrue("Teller should be set to mockTeller and not null.", bankCustomer.getBankTeller().equals(mockTeller));
		assertTrue("", bankCustomer.getBankTeller() == mockTeller);
		
		//step 1
		assertFalse("No messages were given but the customer should be waiting and pickAndExecute should be "
				+ "false. It is not.", bankCustomer.pickAndExecuteAnAction());
		bankCustomer.msgEnteredBank();
		assertTrue("Bank Customer state should be updated, and the customer should msg the greeter concerning getting"
				+ "a teller. The state was not changed.", bankCustomer.pickAndExecuteAnAction());
		assertTrue("The state should be waiting after speaking to the greeter. It is instead something else.", bankCustomer.state == CustomerState.waiting);
		
		//step 2
		bankCustomer.msgWaitHere(0);
		assertTrue("The customer's waiting number should be 0. It is something else", bankCustomer.waitingNumber == 0);
		assertTrue("Customer state should be updated to inLine. It was not updated.", bankCustomer.state == CustomerState.goingToLine);
		
	}//end one normal customer scenario
}
