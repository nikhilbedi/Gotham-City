package simcity.bank.test;

import Gui.RoleGui;
import Gui.ScreenFactory;
import simcity.PersonAgent;
import simcity.bank.Bank;
import simcity.bank.BankCustomerRole;
import simcity.bank.BankDatabase;
import simcity.bank.BankReceipt;
import simcity.bank.BankTellerRole;
import simcity.bank.BankTellerRole.customerState;
import simcity.bank.BankTransaction;
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
public class BankTellerTest extends TestCase
{
	//these are instantiated for each test separately via the setUp() method.
	MockBankCustomer mockCustomer;
	MockBankGreeter mockGreeter;
	BankTellerRole bankTeller;
	
	
	public void setUp() throws Exception{
		super.setUp();
		bankTeller = new BankTellerRole(new PersonAgent("testTeller"));
		
		Bank b = new Bank("Bank", 0, 0, 0, 0);
		bankTeller.getPersonAgent().setBank(b);
		//bankCustomer.setGui(new bankCustomerGui((BankCustomerRole)bankCustomer, ScreenFactory.getMeScreen("Bank")));
		mockGreeter = new MockBankGreeter("mockGreeter");		
		mockCustomer = new MockBankCustomer("mockCustomer");
		bankTeller.setGreeter(mockGreeter);
		
		//bankCustomer.setBankTeller(mockTeller);
		//b.setGreeter(mockGreeter);
	}
	
	
	public void testOneNormalTellerOpeningAccountScenario()
	{
		//Check initial conditions
		assertTrue("The myCustomer list should be empty at the teller's creation. It is not.", 
				bankTeller.myCustomers.size() == 0);
		assertTrue("The teller's set greeter should be equivalent to mockGreeter. It is not.", 
				bankTeller.greeter == mockGreeter);
		assertTrue("Name should be testTeller. It is instead " + bankTeller.getName(), 
				bankTeller.getName().equals("testTeller"));
		assertEquals("Bank database should be null to begin with. It is not.", bankTeller.bankDatabase, null);
		
		
		//set up
		bankTeller.setBankDatabase(new BankDatabase());
		assertFalse("Bank Database should no longer be null. It is, however.", bankTeller.bankDatabase == null);
		assertTrue("Bank Teller index should be at 0 as a default. It is not.", bankTeller.getIndex() == 0);
		assertEquals("Number of customers should default to 0. It equals another number.", bankTeller.getNumCustomers(), 0);
		
		
		//step 1
		assertFalse("The scheduler at this point should return false. It returns true however.", bankTeller.pickAndExecuteAnAction());
		bankTeller.msgNeedATransaction(mockCustomer, new BankTransaction("openingAccount", 50));
		assertTrue("MyCustomer list should have been added to and greater than 0. It is not.", bankTeller.myCustomers.size() > 0);
		assertTrue("The state of myCustomer should be askedForTransaction. It is not.", bankTeller.myCustomers.get(0).s == customerState.askedForTransaction);
		assertFalse("Handled Transaction boolean should be false. It's true however", bankTeller.handledTransaction);
		assertTrue("The scheduler should now return true and run the open account method. It's false, however.", bankTeller.pickAndExecuteAnAction());
		assertTrue("Handled Transaction should now be true. It is false however.", bankTeller.handledTransaction);
		assertTrue("The customer's account should now have $50. It does not.", bankTeller.bankDatabase.getTotalAccountBalance("mockCustomer") == 50);
		assertFalse("The scheduler at this point should return false. It does not.", bankTeller.pickAndExecuteAnAction());
		
		//step 2
		bankTeller.msgDoneAndLeaving(mockCustomer);
		assertFalse("Handled Transaction should now be false. It is true, however.", bankTeller.handledTransaction);
		assertTrue("The state of myCustomer should be doneAndLeaving. It is not.", bankTeller.myCustomers.get(0).s == customerState.doneAndLeaving);
		assertTrue("The scheduler should now return true and run the handle Done State method. It's false, however.", bankTeller.pickAndExecuteAnAction());
		assertEquals("The My Customer list should now be empty. It is not.", bankTeller.myCustomers.size(), 0);
		
		
	}//end one normal teller opening Account scenario
	
	public void testTwoNormalDepositScenario()
	{
		//Check initial conditions
		assertTrue("The myCustomer list should be empty at the teller's creation. It is not.", 
				bankTeller.myCustomers.size() == 0);
		assertTrue("The teller's set greeter should be equivalent to mockGreeter. It is not.", 
				bankTeller.greeter == mockGreeter);
		assertTrue("Name should be testTeller. It is instead " + bankTeller.getName(), 
				bankTeller.getName().equals("testTeller"));
		assertEquals("Bank database should be null to begin with. It is not.", bankTeller.bankDatabase, null);
		
		
		//set up
		bankTeller.setBankDatabase(new BankDatabase());
		assertFalse("Bank Database should no longer be null. It is, however.", bankTeller.bankDatabase == null);
		assertTrue("Bank Teller index should be at 0 as a default. It is not.", bankTeller.getIndex() == 0);
		assertEquals("Number of customers should default to 0. It equals another number.", bankTeller.getNumCustomers(), 0);
		
		
		//step 1
		
		assertFalse("The scheduler at this point should return false. It returns true however.", bankTeller.pickAndExecuteAnAction());
		bankTeller.msgNeedATransaction(mockCustomer, new BankTransaction("openingAccount", 50));
		assertTrue("MyCustomer list should have been added to and greater than 0. It is not.", bankTeller.myCustomers.size() > 0);
		assertTrue("The state of myCustomer should be askedForTransaction. It is not.", bankTeller.myCustomers.get(0).s == customerState.askedForTransaction);
		assertFalse("Handled Transaction boolean should be false. It's true however", bankTeller.handledTransaction);
		assertTrue("The scheduler should now return true and run the open account method. It's false, however.", bankTeller.pickAndExecuteAnAction());
		assertTrue("Handled Transaction should now be true. It is false however.", bankTeller.handledTransaction);
		assertTrue("The customer's account should now have $50. It does not.", bankTeller.bankDatabase.getTotalAccountBalance("mockCustomer") == 50);
		assertFalse("The scheduler at this point should return false. It does not.", bankTeller.pickAndExecuteAnAction());
		
		
		//step 2
		
		assertFalse("The scheduler at this point should return false. It returns true however.", bankTeller.pickAndExecuteAnAction());
		bankTeller.msgNeedATransaction(mockCustomer, new BankTransaction("deposit", 50));
		assertTrue("MyCustomer list should have been added to and greater than 0. It is not.", bankTeller.myCustomers.size() > 0);
		assertTrue("The state of myCustomer should be askedForTransaction. It is not.", bankTeller.myCustomers.get(0).s == customerState.askedForTransaction);
		assertFalse("Handled Transaction boolean should be false. It's true however", bankTeller.handledTransaction);
		assertTrue("The scheduler should now return true and run the deposit method. It's false, however.", bankTeller.pickAndExecuteAnAction());
		assertTrue("Handled Transaction should now be true. It is false however.", bankTeller.handledTransaction);
		assertTrue("The customer's account should now have $100. It does not.", bankTeller.bankDatabase.getTotalAccountBalance("mockCustomer") == 100);
		assertFalse("The scheduler at this point should return false. It does not.", bankTeller.pickAndExecuteAnAction());
		
		
		//step 3
		
		bankTeller.msgDoneAndLeaving(mockCustomer);
		assertFalse("Handled Transaction should now be false. It is true, however.", bankTeller.handledTransaction);
		assertTrue("The state of myCustomer should be doneAndLeaving. It is not.", bankTeller.myCustomers.get(0).s == customerState.doneAndLeaving);
		assertTrue("The scheduler should now return true and run the handle Done State method. It's false, however.", bankTeller.pickAndExecuteAnAction());
		assertEquals("The My Customer list should now be empty. It is not.", bankTeller.myCustomers.size(), 0);
		
		
	}//end two normal teller Deposit scenario
	
	public void testThreeNormalWithdrawalScenario()
	{
		//Check initial conditions
		assertTrue("The myCustomer list should be empty at the teller's creation. It is not.", 
				bankTeller.myCustomers.size() == 0);
		assertTrue("The teller's set greeter should be equivalent to mockGreeter. It is not.", 
				bankTeller.greeter == mockGreeter);
		assertTrue("Name should be testTeller. It is instead " + bankTeller.getName(), 
				bankTeller.getName().equals("testTeller"));
		assertEquals("Bank database should be null to begin with. It is not.", bankTeller.bankDatabase, null);
		
		
		//set up
		bankTeller.setBankDatabase(new BankDatabase());
		assertFalse("Bank Database should no longer be null. It is, however.", bankTeller.bankDatabase == null);
		assertTrue("Bank Teller index should be at 0 as a default. It is not.", bankTeller.getIndex() == 0);
		assertEquals("Number of customers should default to 0. It equals another number.", bankTeller.getNumCustomers(), 0);
		
		
		//step 1
		
		assertFalse("The scheduler at this point should return false. It returns true however.", bankTeller.pickAndExecuteAnAction());
		bankTeller.msgNeedATransaction(mockCustomer, new BankTransaction("openingAccount", 50));
		assertTrue("MyCustomer list should have been added to and greater than 0. It is not.", bankTeller.myCustomers.size() > 0);
		assertTrue("The state of myCustomer should be askedForTransaction. It is not.", bankTeller.myCustomers.get(0).s == customerState.askedForTransaction);
		assertFalse("Handled Transaction boolean should be false. It's true however", bankTeller.handledTransaction);
		assertTrue("The scheduler should now return true and run the open account method. It's false, however.", bankTeller.pickAndExecuteAnAction());
		assertTrue("Handled Transaction should now be true. It is false however.", bankTeller.handledTransaction);
		assertTrue("The customer's account should now have $50. It does not.", bankTeller.bankDatabase.getTotalAccountBalance("mockCustomer") == 50);
		assertFalse("The scheduler at this point should return false. It does not.", bankTeller.pickAndExecuteAnAction());
		
		
		//step 2
		
		assertFalse("The scheduler at this point should return false. It returns true however.", bankTeller.pickAndExecuteAnAction());
		bankTeller.msgNeedATransaction(mockCustomer, new BankTransaction("withdrawal", 50));
		assertTrue("MyCustomer list should have been added to and greater than 0. It is not.", bankTeller.myCustomers.size() > 0);
		assertTrue("The state of myCustomer should be askedForTransaction. It is not.", bankTeller.myCustomers.get(0).s == customerState.askedForTransaction);
		assertFalse("Handled Transaction boolean should be false. It's true however", bankTeller.handledTransaction);
		assertTrue("The scheduler should now return true and run the withdrawal method. It's false, however.", bankTeller.pickAndExecuteAnAction());
		assertTrue("Handled Transaction should now be true. It is false however.", bankTeller.handledTransaction);
		assertTrue("The customer's account should now have $0. It does not.", bankTeller.bankDatabase.getTotalAccountBalance("mockCustomer") == 0);
		assertFalse("The scheduler at this point should return false. It does not.", bankTeller.pickAndExecuteAnAction());
		
		
		//step 3
		
		bankTeller.msgDoneAndLeaving(mockCustomer);
		assertFalse("Handled Transaction should now be false. It is true, however.", bankTeller.handledTransaction);
		assertTrue("The state of myCustomer should be doneAndLeaving. It is not.", bankTeller.myCustomers.get(0).s == customerState.doneAndLeaving);
		assertTrue("The scheduler should now return true and run the handle Done State method. It's false, however.", bankTeller.pickAndExecuteAnAction());
		assertEquals("The My Customer list should now be empty. It is not.", bankTeller.myCustomers.size(), 0);
		
		
	}//end three normal teller Withdrawal scenario
	
	public void testFourNormalLoanScenario()
	{
		//Check initial conditions
		assertTrue("The myCustomer list should be empty at the teller's creation. It is not.", 
				bankTeller.myCustomers.size() == 0);
		assertTrue("The teller's set greeter should be equivalent to mockGreeter. It is not.", 
				bankTeller.greeter == mockGreeter);
		assertTrue("Name should be testTeller. It is instead " + bankTeller.getName(), 
				bankTeller.getName().equals("testTeller"));
		assertEquals("Bank database should be null to begin with. It is not.", bankTeller.bankDatabase, null);
		
		
		//set up
		bankTeller.setBankDatabase(new BankDatabase());
		assertFalse("Bank Database should no longer be null. It is, however.", bankTeller.bankDatabase == null);
		assertTrue("Bank Teller index should be at 0 as a default. It is not.", bankTeller.getIndex() == 0);
		assertEquals("Number of customers should default to 0. It equals another number.", bankTeller.getNumCustomers(), 0);
		
		
		//step 1
		
		assertFalse("The scheduler at this point should return false. It returns true however.", bankTeller.pickAndExecuteAnAction());
		bankTeller.msgNeedATransaction(mockCustomer, new BankTransaction("openingAccount", 50000));
		assertTrue("MyCustomer list should have been added to and greater than 0. It is not.", bankTeller.myCustomers.size() > 0);
		assertTrue("The state of myCustomer should be askedForTransaction. It is not.", bankTeller.myCustomers.get(0).s == customerState.askedForTransaction);
		assertFalse("Handled Transaction boolean should be false. It's true however", bankTeller.handledTransaction);
		assertTrue("The scheduler should now return true and run the open account method. It's false, however.", bankTeller.pickAndExecuteAnAction());
		assertTrue("Handled Transaction should now be true. It is false however.", bankTeller.handledTransaction);
		assertTrue("The customer's account should now have $50. It does not.", bankTeller.bankDatabase.getTotalAccountBalance("mockCustomer") == 50000);
		assertFalse("The scheduler at this point should return false. It does not.", bankTeller.pickAndExecuteAnAction());
		
		
		//step 2
		
		assertFalse("The scheduler at this point should return false. It returns true however.", bankTeller.pickAndExecuteAnAction());
		bankTeller.msgNeedATransaction(mockCustomer, new BankTransaction("needALoan", 50));
		assertTrue("MyCustomer list should have been added to and greater than 0. It is not.", bankTeller.myCustomers.size() > 0);
		assertTrue("The state of myCustomer should be askedForTransaction. It is not.", bankTeller.myCustomers.get(0).s == customerState.askedForTransaction);
		assertFalse("Handled Transaction boolean should be false. It's true however", bankTeller.handledTransaction);
		assertTrue("The scheduler should now return true and run the withdrawal method. It's false, however.", bankTeller.pickAndExecuteAnAction());
		assertTrue("Handled Transaction should now be true. It is false however.", bankTeller.handledTransaction);
		assertFalse("The scheduler at this point should return false. It does not.", bankTeller.pickAndExecuteAnAction());
		assertTrue("The bank Database safe should have less money after the loan. It does not.", bankTeller.bankDatabase.safeBalance < 500000);
		
		//step 3
		
		bankTeller.msgDoneAndLeaving(mockCustomer);
		assertFalse("Handled Transaction should now be false. It is true, however.", bankTeller.handledTransaction);
		assertTrue("The state of myCustomer should be doneAndLeaving. It is not.", bankTeller.myCustomers.get(0).s == customerState.doneAndLeaving);
		assertTrue("The scheduler should now return true and run the handle Done State method. It's false, however.", bankTeller.pickAndExecuteAnAction());
		assertEquals("The My Customer list should now be empty. It is not.", bankTeller.myCustomers.size(), 0);
		
		
	}//end four normal teller Loan scenario
	
	public void testFiveNormalCloseAccountScenario()
	{
		//Check initial conditions
		assertTrue("The myCustomer list should be empty at the teller's creation. It is not.", 
				bankTeller.myCustomers.size() == 0);
		assertTrue("The teller's set greeter should be equivalent to mockGreeter. It is not.", 
				bankTeller.greeter == mockGreeter);
		assertTrue("Name should be testTeller. It is instead " + bankTeller.getName(), 
				bankTeller.getName().equals("testTeller"));
		assertEquals("Bank database should be null to begin with. It is not.", bankTeller.bankDatabase, null);
		
		
		//set up
		bankTeller.setBankDatabase(new BankDatabase());
		assertFalse("Bank Database should no longer be null. It is, however.", bankTeller.bankDatabase == null);
		assertTrue("Bank Teller index should be at 0 as a default. It is not.", bankTeller.getIndex() == 0);
		assertEquals("Number of customers should default to 0. It equals another number.", bankTeller.getNumCustomers(), 0);
		
		
		//step 1
		
		assertFalse("The scheduler at this point should return false. It returns true however.", bankTeller.pickAndExecuteAnAction());
		bankTeller.msgNeedATransaction(mockCustomer, new BankTransaction("openingAccount", 50000));
		assertTrue("MyCustomer list should have been added to and greater than 0. It is not.", bankTeller.myCustomers.size() > 0);
		assertTrue("The state of myCustomer should be askedForTransaction. It is not.", bankTeller.myCustomers.get(0).s == customerState.askedForTransaction);
		assertFalse("Handled Transaction boolean should be false. It's true however", bankTeller.handledTransaction);
		assertTrue("The scheduler should now return true and run the open account method. It's false, however.", bankTeller.pickAndExecuteAnAction());
		assertTrue("Handled Transaction should now be true. It is false however.", bankTeller.handledTransaction);
		assertTrue("The customer's account should now have $50. It does not.", bankTeller.bankDatabase.getTotalAccountBalance("mockCustomer") == 50000);
		assertFalse("The scheduler at this point should return false. It does not.", bankTeller.pickAndExecuteAnAction());
		
		
		//step 2
		
		assertFalse("The scheduler at this point should return false. It returns true however.", bankTeller.pickAndExecuteAnAction());
		bankTeller.msgNeedATransaction(mockCustomer, new BankTransaction("closingAccount", 50));
		assertTrue("MyCustomer list should have been added to and greater than 0. It is not.", bankTeller.myCustomers.size() > 0);
		assertTrue("The state of myCustomer should be askedForTransaction. It is not.", bankTeller.myCustomers.get(0).s == customerState.askedForTransaction);
		assertFalse("Handled Transaction boolean should be false. It's true however", bankTeller.handledTransaction);
		assertTrue("The scheduler should now return true and run the closeAccount method. It's false, however.", bankTeller.pickAndExecuteAnAction());
		assertTrue("Handled Transaction should now be true. It is false however.", bankTeller.handledTransaction);
		assertFalse("The scheduler at this point should return false. It does not.", bankTeller.pickAndExecuteAnAction());
		assertTrue("The account should have been removed, meaning there are no accounts in the bankDatabase. It did not get removed.", bankTeller.bankDatabase.accounts.size() == 0);
		assertTrue("The account should have been removed, meaning there are no account numbers in the bankDatabase. It did not get removed.", bankTeller.bankDatabase.accountNumbers.size() == 0);
		
		//step 3
		
		bankTeller.msgDoneAndLeaving(mockCustomer);
		assertFalse("Handled Transaction should now be false. It is true, however.", bankTeller.handledTransaction);
		assertTrue("The state of myCustomer should be doneAndLeaving. It is not.", bankTeller.myCustomers.get(0).s == customerState.doneAndLeaving);
		assertTrue("The scheduler should now return true and run the handle Done State method. It's false, however.", bankTeller.pickAndExecuteAnAction());
		assertEquals("The My Customer list should now be empty. It is not.", bankTeller.myCustomers.size(), 0);
		
		
	}//end four normal teller Loan scenario
}
