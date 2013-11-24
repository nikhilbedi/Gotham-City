package simcity.tests;

import simcity.PersonAgent;
import simcity.Hacker;
import simcity.PersonAgent.*;
import simcity.PersonGui;
import simcity.Home.Home;
import simcity.Market.Market;
import simcity.bank.Bank;
import simcity.tests.mock.mocks.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
		Bank bank = new Bank("BankName", 0, 0, 0, 0);
		Market market = new Market("MarketName", 0, 0, 0, 0);
		List<Market> markets = new ArrayList<Market>();
		Home home = new Home("HomeName", 0, 0, 0, 0);
		PersonGui pGui = new PersonGui(person);
		person.setGui(pGui);
		person.setBank(bank);
		person.setMarkets(markets);
		person.setHome(home);
		hacker = new Hacker();

	}	

	/**
	 * This test is for checking if a person performs a bank operation first.
	 */
	public void testGoingToBankForAccount() {
		BankMock bankMock = new BankMock("bank");

		//preconditions
		assertTrue("The money allocated when a person is instantiated is zero. It is not.", person.checkMoney() == 0.0);
		assertEquals("Job should be null. It is not", person.getJob(), null);
		assertTrue("The size of roles should be zero", person.getRoles().size() == 0);
		assertTrue("The size of rentBills should be zero. It isnt", person.getRentBills().size() == 0);
		assertEquals("Bank account number should be zero. it isnt", person.getAccountNumber(), 0);
		assertTrue("personScheduler should be true. it isnt", person.getPersonScheduler());



		//step 1
		//The semaphore in goToBank() needs to be commented out to unit test, otherwise it gets locked there without the main clock updating its position
		assertTrue("The person should be initiated knowing he must create a bank account", person.pickAndExecuteAnAction());

		//postconditions
		assertFalse("The personScheduler should now be false. It isnt", person.getPersonScheduler());
		assertTrue("The money allocated when a person is instantiated is zero. It is not.", person.checkMoney() == 0.0);
		assertEquals("Job should be null. It is not", person.getJob(), null);
		assertTrue("The size of roles should be zero", person.getRoles().size() == 0);
		assertTrue("The size of rentBills should be zero. It isnt", person.getRentBills().size() == 0);
		assertEquals("Bank account number should be zero. it isnt", person.getAccountNumber(), 0);
		assertFalse("personScheduler should be false. it isnt", person.getPersonScheduler());
		assertEquals("BankMock's log should be empty. It isn't", bankMock.log.size(), 0);


		//step 2 - set account number using BankMock
		person.accountNumber = bankMock.needAccountNumber();

		//postconditions
		assertTrue("Bank account number should not be zero. it is", person.getAccountNumber() != 0);
		assertFalse("The person should not have anything to do. But he does", person.pickAndExecuteAnAction());
		assertEquals("BankMock's log should have received a message from person, increasing"
				+ "it's log size by one. It didnt't", bankMock.log.size(), 1);
		assertTrue("The money allocated when a person is instantiated is zero. It is not.", person.checkMoney() == 0.0);
		assertEquals("Job should be null. It is not", person.getJob(), null);
		assertTrue("The size of roles should be zero", person.getRoles().size() == 0);
		assertTrue("The size of rentBills should be zero. It isnt", person.getRentBills().size() == 0);
		assertFalse("personScheduler should be false. it isnt", person.getPersonScheduler());
	}


	/**
	 * This test is to check if the person will go to the market based upon certain states, and receive groceries.
	 * Then, this person needs to immediately take those groceries home (can't walk around with it!)
	 */
	public void testGoingToMarketForGroceries() {
		MarketMock marketMock = new MarketMock("marketMock");
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("Pizza", 10);
		map.put("Steak", 5);

		//preconditions
		assertEquals("CashierAgent should have an empty event log before the Cashier's HereIsBill is called. Instead, the Cashier's event log reads: "
				+ marketMock.log.toString(), 0, marketMock.log.size());
		assertTrue("The money allocated when a person is instantiated is zero. It is not.", person.checkMoney() == 0.0);
		assertEquals("Job should be null. It is not", person.getJob(), null);
		assertTrue("The size of roles should be zero", person.getRoles().size() == 0);
		assertTrue("The size of rentBills should be zero. It isnt", person.getRentBills().size() == 0);
		assertEquals("Bank account number should be zero. it isnt", person.getAccountNumber(), 0);
		assertTrue("personScheduler should be true. it isnt", person.getPersonScheduler());
		assertTrue("The person's state should be set so that he thinks he has groceries. He doesnt", person.marketState == MarketState.HaveGroceries);

		//Step 1 - send the person a message that he needs groceries
		person.homeNeedsGroceries(map);

		//Check postconditions/preconditions
		assertEquals("CashierAgent should have an empty event log before the Cashier's HereIsBill is called. Instead, the Cashier's event log reads: "
				+ marketMock.log.toString(), 0, marketMock.log.size());
		assertTrue("The money allocated when a person is instantiated is zero. It is not.", person.checkMoney() == 0.0);
		assertEquals("Job should be null. It is not", person.getJob(), null);
		assertTrue("The size of roles should be zero", person.getRoles().size() == 0);
		assertTrue("The size of rentBills should be zero. It isnt", person.getRentBills().size() == 0);
		assertEquals("Bank account number should be zero. it isnt", person.getAccountNumber(), 0);
		assertTrue("personScheduler should be true. it isnt", person.getPersonScheduler());
		assertTrue("The person's state should be set so that he thinks he has groceries. He doesnt", person.marketState == MarketState.GetGroceries);

		//Step 2 - Check scheduler so he can get groceries
		assertTrue("The person should head to the market to get groceries. But the scheduler returned false", person.pickAndExecuteAnAction());

		//Check postconditions/preconditions
		assertEquals("CashierAgent should have an empty event log before the Cashier's HereIsBill is called. Instead, the Cashier's event log reads: "
				+ marketMock.log.toString(), 0, marketMock.log.size());
		assertTrue("The money allocated when a person is instantiated is zero. It is not.", person.checkMoney() == 0.0);
		assertEquals("Job should be null. It is not", person.getJob(), null);
		assertTrue("The size of roles should be zero", person.getRoles().size() == 0);
		assertTrue("The size of rentBills should be zero. It isnt", person.getRentBills().size() == 0);
		assertEquals("Bank account number should be zero. it isnt", person.getAccountNumber(), 0);
		assertFalse("personScheduler should be false. it isnt", person.getPersonScheduler());
		assertTrue("The person's state should be set so that he thinks he is getting groceries. He doesnt", person.marketState == MarketState.GettingGroceries);
		assertFalse("The grocery list should still be present. It's empty.", person.groceryList.isEmpty());

		//Step 3 - Give food to the person
		map = marketMock.giveMeGroceries(person);
		
		//Check postconditions/preconditions
		assertEquals("marketMock's log should have received a message from person, increasing"
				+ "it's log size by one. It didnt't", marketMock.log.size(), 1);
		assertTrue("The money allocated when a person is instantiated is zero. It is not.", person.checkMoney() == 0.0);
		assertEquals("Job should be null. It is not", person.getJob(), null);
		assertTrue("The size of roles should be zero", person.getRoles().size() == 0);
		assertTrue("The size of rentBills should be zero. It isnt", person.getRentBills().size() == 0);
		assertEquals("Bank account number should be zero. it isnt", person.getAccountNumber(), 0);
		assertTrue("personScheduler should be true. it isnt", person.getPersonScheduler());
		assertTrue("The person's state should be set so that he thinks he is getting groceries. He doesnt", person.marketState == MarketState.TakeGroceriesHome);
		assertFalse("The grocery list should still be present. It's empty.", person.groceryList.isEmpty());

		//Step 4 - Take groceries home using pickAndExecuteAnAction
		assertTrue("The person should head home now to put groceries in the fridge."
				+ " But the scheduler returned false", person.pickAndExecuteAnAction());
	}
}
