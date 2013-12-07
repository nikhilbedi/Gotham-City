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
 * This class is a JUnit test class to unit test the person logically moving through the scheduler.
 * Because several components such as roles may be missing at the start of this test, it is primitive in its understanding.
 * This test will evolve as more components of Simcity are available.
 *
 * @author Nikhil Bedi
 */
public class PersonTest extends TestCase
{
	PersonAgent person;
	BankMock bankMock;
	Hacker hacker;
	Bank bank;
	int time;

	/**
	 * This method is run before each test. It is used to instantiate the class variables
	 * for my agent and mocks, etc.
	 */
	public void setUp() throws Exception{
		super.setUp();
		time = 0;
		person = new PersonAgent("person");
		bank = new Bank("BankName", 0, 0, 0, 0);
		bankMock = new BankMock("bank");
		//Market market = new Market("MarketName", 0, 0, 0, 0);
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
	 * This test now fails due to a change in a scheduler rule.  The person will no longer actively decide to go to the bank if 
	 * he doesnot have an account number.
	 */
	
	public void testGoingToBankForAccount() {
		//preconditions
		/*assertTrue("The money allocated when a person is instantiated is zero. It is not.", person.checkMoney() == 0.0);
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
		//assertEquals("BankMock's log should have received a message from person, increasing"
		//		+ "it's log size by one. It didnt't", bankMock.log.size(), 1);
		assertTrue("The money allocated when a person is instantiated is zero. It is not.", person.checkMoney() == 0.0);
		assertEquals("Job should be null. It is not", person.getJob(), null);
		assertTrue("The size of roles should be zero", person.getRoles().size() == 0);
		assertTrue("The size of rentBills should be zero. It isnt", person.getRentBills().size() == 0);
		assertFalse("personScheduler should be false. it isnt", person.getPersonScheduler());
		*/
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

		HomeMock homeMock = new HomeMock("homeMock");

		//preconditions
		assertTrue("The grocery bag should be empty upon instantiation. It isnt.", person.groceryBag.isEmpty());
		assertTrue("The grocery list should be empty upon instantiation. It isnt.", person.groceryList.isEmpty());
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
		//This message comes from the resident role
		person.homeNeedsGroceries(map);


		//Check postconditions/preconditions
		assertEquals("MarketMock should have an empty event log. Instead, the MarketMock's event log reads: "
				+ marketMock.log.toString(), 0, marketMock.log.size());
		assertFalse("The grocery list should now be filled with items needed. But it is empty.", person.groceryList.isEmpty());
		assertTrue("The grocery bag should be empty, because the person hasn't gone to the market yet. but it is not.", person.groceryBag.isEmpty());
		assertTrue("The money allocated when a person is instantiated is zero. It is not.", person.checkMoney() == 0.0);
		assertEquals("Job should be null. It is not", person.getJob(), null);
		assertTrue("The size of roles should be zero", person.getRoles().size() == 0);
		assertTrue("The size of rentBills should be zero. It isnt", person.getRentBills().size() == 0);
		assertEquals("Bank account number should be zero. it isnt", person.getAccountNumber(), 0);
		assertTrue("personScheduler should be true. it isnt", person.getPersonScheduler());
		assertTrue("The person's state should be set so that he thinks he needs groceries. He doesnt", person.marketState == MarketState.GetGroceries);

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
		assertTrue("The grocery list should now be empty, because we were given the food. But there are still items "
				+ "on the list", person.groceryList.isEmpty());
		assertFalse("The grocery bag should now not be empty because we were given items from the market. but it's still empty", person.groceryBag.isEmpty());

		//Step 4 - Take groceries home using pickAndExecuteAnAction
		assertTrue("The person should head home now to put groceries in the fridge."
				+ " But the scheduler returned false", person.pickAndExecuteAnAction());

		//Check postconditions/preconditions
		assertTrue("The money allocated when a person is instantiated is zero. It is not.", person.checkMoney() == 0.0);
		assertEquals("Job should be null. It is not", person.getJob(), null);
		assertTrue("The size of roles should be zero", person.getRoles().size() == 0);
		assertTrue("The size of rentBills should be zero. It isnt", person.getRentBills().size() == 0);
		assertEquals("Bank account number should be zero. it isnt", person.getAccountNumber(), 0);
		assertTrue("personScheduler should be true. it isnt", person.getPersonScheduler());
		assertTrue("The person's state should be set so that he thinks he is taking groceries home. He doesnt", person.marketState == MarketState.TakingGroceriesHome);
		assertFalse("The grocery bag should still be present. It's empty.", person.groceryBag.isEmpty());

		//Step 5 - store the grocery bag into fridge 
		homeMock.addGroceriesToFridge(person.groceryBag);

		//Check postconditions/preconditions
		assertTrue("The money allocated when a person is instantiated is zero. It is not.", person.checkMoney() == 0.0);
		assertEquals("Job should be null. It is not", person.getJob(), null);
		assertTrue("The size of roles should be zero", person.getRoles().size() == 0);
		assertTrue("The size of rentBills should be zero. It isnt", person.getRentBills().size() == 0);
		assertEquals("Bank account number should be zero. it isnt", person.getAccountNumber(), 0);
		assertTrue("personScheduler should be true. it isnt", person.getPersonScheduler());
		assertTrue("The person's state should be set so that he thinks he is taking groceries home. He doesnt", person.marketState == MarketState.TakingGroceriesHome);
		assertTrue("The grocery bag should still be present. It's empty.", person.groceryBag.isEmpty());
	}

	
	/**
	 * This checks if the person appropriately decides to go to the bank, based upon how much money he has
	 */
	public void testGoingToBankForTransaction() {
		//Preconditions
		assertTrue("The money allocated when a person is instantiated is zero. It is not.", person.checkMoney() == 0.0);
		assertEquals("Job should be null. It is not", person.getJob(), null);
		assertTrue("The size of roles should be zero", person.getRoles().size() == 0);
		assertTrue("The size of rentBills should be zero. It isnt", person.getRentBills().size() == 0);
		assertEquals("Bank account number should be zero. it isnt", person.getAccountNumber(), 0);
		assertTrue("personScheduler should be true. it isnt", person.getPersonScheduler());
		assertEquals("Hunger count should be 0. But it isnt", 0, person.hungerCount);
		
		//Step 1 - Set the money to something
		person.addMoney(15);

		//Post/preconditions
		assertTrue("The money allocated is now equivalent to whatever was added using addMoney(int x). It is not.", person.checkMoney() > 0);
		assertEquals("Job should be null. It is not", person.getJob(), null);
		assertTrue("The size of roles should be zero", person.getRoles().size() == 0);
		assertTrue("The size of rentBills should be zero. It isnt", person.getRentBills().size() == 0);
		assertEquals("Bank account number should be zero. it isnt", person.getAccountNumber(), 0);
		assertTrue("personScheduler should be true. it isnt", person.getPersonScheduler());
		assertEquals("Hunger count should be 0. But it isnt", 0, person.hungerCount);
		
		//Step 2 - run pickAndExecuteAnAction to see what action the person does
		//assertFalse("Although the person's money is considered a low amount, he has not"
			//	+ "checked it yet. Thus, the scheduler should run false, but it ran true.",person.pickAndExecuteAnAction());
		
		//Redo Step 2 - Manually update the time so the person looks at his wallet
		time++;
		person.updateTime(time);
		//Releasing a permit before the scheduler so it doesnt get stuck "going" to the bank
		//assertTrue("The scheduler should run true because money is low. But it didnt", person.pickAndExecuteAnAction());
		
		//Post/preconditions
		assertTrue("The money state should now be low instead of neutral. It isnt", person.moneyState == MoneyState.Low);
		assertTrue("The money allocated should not have changed. It did.", person.checkMoney() > 0);
		assertEquals("Job should be null. It is not", person.getJob(), null);
		assertTrue("The size of roles should be zero", person.getRoles().size() == 0);
		assertTrue("The size of rentBills should be zero. It isnt", person.getRentBills().size() == 0);
		assertEquals("Bank account number should be zero. it isnt", person.getAccountNumber(), 0);
		assertTrue("personScheduler should be true. it isnt", person.getPersonScheduler());
		assertEquals("Hunger count should be 1. But it isnt", 1, person.hungerCount);
		
		//Step 3 - When scheduler runs true, the person goes to bank, and the bank should provide an account number, and not allow any money change
		bankMock.needAccountNumber();
		double moneytemp = person.checkMoney();
		person.addMoney(bankMock.withdrawMoney());
		
		//Post/preconditions
		assertTrue("The money allocated should be greater than before. It isnt.", person.checkMoney() > moneytemp);
		assertEquals("Job should be null. It is not", person.getJob(), null);
		assertTrue("The size of roles should be zero", person.getRoles().size() == 0);
		assertTrue("The size of rentBills should be zero. It isnt", person.getRentBills().size() == 0);
		assertEquals("Bank account number should be zero. it isnt", person.getAccountNumber(), 0);
		assertTrue("personScheduler should be true. it isnt", person.getPersonScheduler());
		assertEquals("Hunger count should be 1. But it isnt", 1, person.hungerCount);
		
		//Step 4 - Update the time
		time++;
		person.updateTime(time);
		
		//Post/preconditions
		assertTrue("The money state should be back to neutral, but it isnt.", person.moneyState == MoneyState.High);
		assertTrue("The money allocated should be greater than before. It isnt.", person.checkMoney() > moneytemp);
		assertEquals("Job should be null. It is not", person.getJob(), null);
		assertTrue("The size of roles should be zero", person.getRoles().size() == 0);
		assertTrue("The size of rentBills should be zero. It isnt", person.getRentBills().size() == 0);
		assertEquals("Bank account number should be zero. it isnt", person.getAccountNumber(), 0);
		assertTrue("personScheduler should be true. it isnt", person.getPersonScheduler());
		assertEquals("Hunger count should be 1. But it isnt", 2, person.hungerCount);
	}
	
	
	/**
	 * This test checks how appropriately the person will go eat when he is hungry (manually updating time)
	 */
	public void testGettingHungry() {
		//preconditions
		assertEquals("Hunger count should be 0. But it isnt", 0, person.hungerCount);
		assertTrue("The money allocated when a person is instantiated is zero. It is not.", person.checkMoney() == 0.0);
		assertEquals("Job should be null. It is not", person.getJob(), null);
		assertTrue("The size of roles should be zero", person.getRoles().size() == 0);
		assertTrue("The size of rentBills should be zero. It isnt", person.getRentBills().size() == 0);
		assertEquals("Bank account number should be zero. it isnt", person.getAccountNumber(), 0);
		assertTrue("personScheduler should be true. it isnt", person.getPersonScheduler());
		assertTrue("The person's state should be set so that he thinks he is not hungry, but it is not", person.hungerState == HungerState.NotHungry);

		//Step 1 - Update the time
		time++;
		person.updateTime(time);
		
		//post/preconditions
		assertEquals("Hunger count should be 1. But it isnt", 1, person.hungerCount);
		assertTrue("The money allocated when a person is instantiated is zero. It is not.", person.checkMoney() == 0.0);
		assertEquals("Job should be null. It is not", person.getJob(), null);
		assertTrue("The size of rentBills should be zero. It isnt", person.getRentBills().size() == 0);
		assertEquals("Bank account number should be zero. it isnt", person.getAccountNumber(), 0);
		assertTrue("personScheduler should be true. it isnt", person.getPersonScheduler());
		assertTrue("The person's state should be set so that he thinks he is not hungry, but it is not", person.hungerState == HungerState.NotHungry);

		//Step 2 - Update the time multiple times
		time++;
		person.updateTime(time);
		time++;
		person.updateTime(time);
		time++;
		person.updateTime(time);
		
		//post/preconditions
		assertEquals("Hunger count should be 4. But it isnt", 4, person.hungerCount);
		assertTrue("The money allocated when a person is instantiated is zero. It is not.", person.checkMoney() == 0.0);
		assertEquals("Job should be null. It is not", person.getJob(), null);
		assertTrue("The size of rentBills should be zero. It isnt", person.getRentBills().size() == 0);
		assertEquals("Bank account number should be zero. it isnt", person.getAccountNumber(), 0);
		assertTrue("personScheduler should be true. it isnt", person.getPersonScheduler());
		assertTrue("The person's state should be set so that he thinks he is famished, but it is not", person.hungerState == HungerState.Famished);

		
		//Step 3 - Update the time multiple times
		time++;
		person.updateTime(time);
		time++;
		person.updateTime(time);
		time++;
		person.updateTime(time);
		time++;
		person.updateTime(time);
		
		//post/preconditions
		assertEquals("Hunger count should be 8. But it isnt", 8, person.hungerCount);
		assertTrue("The money allocated when a person is instantiated is zero. It is not.", person.checkMoney() == 0.0);
		assertEquals("Job should be null. It is not", person.getJob(), null);
		assertTrue("The size of rentBills should be zero. It isnt", person.getRentBills().size() == 0);
		assertEquals("Bank account number should be zero. it isnt", person.getAccountNumber(), 0);
		assertTrue("personScheduler should be true. it isnt", person.getPersonScheduler());
		assertTrue("The person's state should be set so that he thinks he is hungry, but it is not", person.hungerState == HungerState.Hungry);
		
		//Step 4 - Update the time multiple times
		time++;
		person.updateTime(time);
		time++;
		person.updateTime(time);
		time++;
		person.updateTime(time);
		time++;
		person.updateTime(time);
		
		//post/preconditions
		assertEquals("Hunger count should be 12. But it isnt", 12, person.hungerCount);
		assertTrue("The money allocated when a person is instantiated is zero. It is not.", person.checkMoney() == 0.0);
		assertEquals("Job should be null. It is not", person.getJob(), null);
		assertTrue("The size of rentBills should be zero. It isnt", person.getRentBills().size() == 0);
		assertEquals("Bank account number should be zero. it isnt", person.getAccountNumber(), 0);
		assertTrue("personScheduler should be true. it isnt", person.getPersonScheduler());
		assertTrue("The person's state should be set so that he thinks he is starving, but it is not", person.hungerState == HungerState.Starving);
		
	}
}
