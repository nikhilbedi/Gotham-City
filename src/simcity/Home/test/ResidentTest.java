package simcity.Home.test;

import static org.junit.Assert.*;
import junit.framework.TestCase;

import org.junit.Test;

import simcity.PersonAgent;
import simcity.Home.ResidentRole;

public class ResidentTest extends TestCase {

	public class CashierTest extends TestCase
	{
		//these are instantiated for each test separately via the setUp() method.
		ResidentRole resident;
		PersonAgent person;
	
		/**
		 * This method is run before each test. You can use it to instantiate the class variables
		 * for your agent and mocks, etc.
		 */
		public void setUp() throws Exception{
			super.setUp();		
			resident = new ResidentRole(person);		
		}
	}
		/**
		 * This tests the cashier under very simple terms: one customer is ready to pay the exact bill.
		 */
		public void testOne(){
			try { 
				setUp();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
}
			
		
			/*
			customer.cashier = cashier;//You can do almost anything in a unit test.
			
			assertEquals("Cashier should have 0 orders in it. It doesn't.", cashier.orders.size(), 0);
			
			Order o = new Order(waiter, "Pizza", 1, OrderState.requestCheck, customer);
			cashier.msgRequestCheck(customer, o);
			
			assertEquals("MockWaiter should have an empty event log before the cashier's scheduler is called. Instead, the MockWaiter's event log reads:"
					+ waiter.log.toString(), 0, waiter.log.size());
			
			assertEquals("Cashier should have one order in it. It doesn't.", cashier.orders.size(), 1);
			
			assertTrue("Orders should contain an order with state == requestCheck. It doesn't", 
					cashier.orders.get(0).os == OrderState.requestCheck);
			
			assertTrue("Orders should contain an order of price = $8.99. It contains something else instead: $"
					+ cashier.orders.get(0).choice.getFoodPrice(), cashier.orders.get(0).choice.getFoodPrice() == 8.99);
			
			assertTrue("Orders should contain a check with the right customer in it. It doesn't.",
					cashier.orders.get(0).customer == customer);
			
			assertTrue("Cashier's scheduler should have returned true (needs to react to Waiter's RequestCheck), but it didn't",
					cashier.pickAndExecuteAnAction());
			
			assertEquals("MockWaiter should not have an empty event log after the Cashier's scheduler is called for the first time. "
					+ "Instead, the MockWaiter's event log reads: "
					+ waiter.log.toString(), 1, waiter.log.size());
			
			assertEquals("MockCustomer should have an empty event log after the Cashier's scheduler is called for the first time."
					+ " Instead, the MockCustomer's event log reads: "
					+ customer.log.toString(), 0, customer.log.size());
			
			customer.msgHereIsYourCheck(o);
			
			assertTrue("MockCustomer should have logged an event for receiving HereIsYourCheck with the correct balance, "
					+ "but his last event logged reads instead: " 
					+ customer.log.getLastLoggedEvent().toString(), customer.log.containsString("Received msgHereIsYourCheck from waiter. Total = 8.99"));
			
			cashier.msgCustomerPayingCheck(o);
			
			assertTrue("Cashier's scheduler should have returned true, but didn't.", 
					cashier.pickAndExecuteAnAction());
			
			//assertTrue("Cashier's orders should contain a check with state == paying. It doesn't", 
				//	cashier.orders.get(0).os == OrderState.idle);
			
			assertFalse("Cashier's scheduler should have returned false (no actions left to do), but it didn't.",
					cashier.pickAndExecuteAnAction());
					*/
		//}
		
	//}
