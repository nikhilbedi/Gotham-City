package simcity.bank.test;

import Gui.RoleGui;
import Gui.ScreenFactory;
import simcity.PersonAgent;
import simcity.bank.Bank;
import simcity.bank.BankCustomerRole;
import simcity.bank.BankGreeterGui;
import simcity.bank.BankGreeterRole.customerState;
import simcity.bank.BankReceipt;
import simcity.bank.bankCustomerGui;
import simcity.bank.BankCustomerRole.CustomerState;
import simcity.bank.BankGreeterRole;
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
public class BankGreeterTest extends TestCase
{
	//these are instantiated for each test separately via the setUp() method.
	MockBankCustomer mockCustomer;
	BankGreeterRole bankGreeter;
	MockBankTeller mockTeller;
	
	
	public void setUp() throws Exception{
		super.setUp();
		bankGreeter = new BankGreeterRole(new PersonAgent("testGreeter"));	
		Bank b = new Bank("Bank", 0, 0, 0, 0);
		//bankGreeter.getPersonAgent().setBank(b);
		bankGreeter.setGui(new BankGreeterGui(bankGreeter));
		mockCustomer = new MockBankCustomer("mockCustomer");		
		mockTeller = new MockBankTeller("mockTeller");
		
		//bankCustomer.setGreeter(mockGreeter);
		//bankCustomer.setBankTeller(mockTeller);
		//b.setGreeter(mockGreeter);
	}
	
	
	public void testOneNormalGreeterScenario()
	{
		//Check initial conditions
		assertTrue("The waiting customer list should be empty at the greeter's creation. It is not.", 
				bankGreeter.waitingCustomers.size() == 0);
		assertTrue("The tellers list should be empty at the greeter's creation. It is not.", 
				bankGreeter.tellers.size() == 0);
		assertTrue("Name should be testCustomer. It is instead " + bankGreeter.getName(), 
				bankGreeter.getName().equals("testGreeter"));
		
		
		//step 1
		
		assertFalse("No messages were given but the greeter should be waiting and pickAndExecute should be "
				+ "false. It is not.", bankGreeter.pickAndExecuteAnAction());
		bankGreeter.msgNeedATeller(mockCustomer);
		assertTrue("Bank Customer state should be updated, and the customer should msg the greeter concerning getting"
				+ "a teller. The state was not changed.", bankGreeter.pickAndExecuteAnAction());
		assertTrue("The customer in index 0 should be sent into the line once the scheduler is called. His state was not chagned.", bankGreeter.waitingCustomers.get(0).s == customerState.inLine);
		
		
		// step 2
		bankGreeter.addTeller(mockTeller);
		assertTrue("Teller list should be increased after adding a teller. It is not. Instead the size is: " + 
						bankGreeter.tellers.size(), bankGreeter.tellers.size() > 0);
		bankGreeter.msgReadyForCustomer(mockTeller);
		assertTrue("The teller should be available before the scheduler runs. It is not.", mockTeller.isAvailable());
		assertTrue("The scheduler should seee the mockTeller as available and return true. It did not.", bankGreeter.pickAndExecuteAnAction());
		assertFalse("The teller should no longer be available after the scheduler runs. It is.", mockTeller.isAvailable());
		assertEquals("The waitingCustomer list should be empty after the customer is sent to the teller. It still has a customer in it.", bankGreeter.waitingCustomers.size(), 0);
		/*assertTrue("The customer's waiting number should be 0. It is something else", bankCustomer.waitingNumber == 0);
		assertTrue("Customer state should be updated to goingToLine after scheduler was run. It was not updated.", bankCustomer.state == CustomerState.goingToLine);
		
		
		//step 3
		
		bankCustomer.msgGoToTeller(mockTeller);
		assertTrue("The customer's state should be changed to inLine before the scheduler runs. This didn't occur.", bankCustomer.state == CustomerState.inLine);
		assertTrue("Scheduler should run DoGoToTeller() and return true. This didn't occur", bankCustomer.pickAndExecuteAnAction());
		assertTrue("The customer's state should be changed to atTeller after the scheduler runs. This didn't occur.", bankCustomer.state == CustomerState.goingToTeller);
		
		
		//step 4
		
		bankCustomer.msgAtTeller();
		assertTrue("Customer state should be atTeller. It is not.", bankCustomer.state == CustomerState.atTeller);
		assertTrue("Should ask teller for a transaction through scheduler. Something was missed.", bankCustomer.pickAndExecuteAnAction());
		assertTrue("Transaction List should be empty once the teller is messaged for a transaction. It does not occur", bankCustomer.transactionList.size() == 0);
	
		
		//step 5
		
		BankReceipt receipt = new BankReceipt(50, 50, "openingAccount");
		bankCustomer.HereIsReceiptAndAccountInfo(receipt, 101);
		assertEquals("The person's money amount should have been decremented by 50. It didn't.",bankCustomer.getPersonAgent().checkMoney(),-50.0);
		assertTrue("The customer's state should be receivedReceipt. It is not.", bankCustomer.state == CustomerState.receivedReceipt);
		assertTrue("The person's account number should be set to 101. It is not.", bankCustomer.getPersonAgent().getAccountNumber() == 101);
		assertTrue("Should begin to leave bank after finished through scheduler. Something was missed.", bankCustomer.pickAndExecuteAnAction());
		assertTrue("Customer state should be done after transaction is finished. It is not.", bankCustomer.state == CustomerState.done);*/
		
	}//end one normal customer opening Account scenario
	
}
