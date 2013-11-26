package restaurant.test;

import junit.framework.TestCase;
import restaurant.CashierAgent;
import restaurant.CustomerAgent;
import restaurant.WaiterAgent;
import restaurant.test.mock.*;

//import junit.framework.*;

/**
 * 
 * This class is a JUnit test class to unit test the CashierAgent's basic interaction
 * with waiters, customers, and the host.
 * It is provided as an example to students in CS201 for their unit testing lab.
 *
 * @author Monroe Ekilah
 */
public class CashierTest extends TestCase
{
	//these are instantiated for each test separately via the setUp() method.
	CashierRole cashier;
	MockWaiter waiter1;
	MockCustomer customer1;
	MockWaiter waiter2;
	MockCustomer customer2;
	MockCustomer customer3;
	MockMarket market1;
	MockMarket market2;

	/**
	 * This method is run before each test. You can use it to instantiate the class variables
	 * for your agent and mocks, etc.
	 */
	public void setUp() throws Exception{
		super.setUp();		
		cashier = new CashierRole("Test Cashier");		
		customer1 = new MockCustomer("Customer 1");		
		waiter1 = new MockWaiter("Waiter 1");
		customer2 = new MockCustomer("Customer 2");		
		waiter2 = new MockWaiter("Waiter 2");
		customer3 = new MockCustomer("Customer 3");

		market1 = new MockMarket("Market1");
		market2 = new MockMarket("Market2");
		cashier.addMarket(market1);
		cashier.addMarket(market2);
	}	
	/**
	 * This tests the cashier under very simple terms: one customer is ready to pay the exact bill.
	 */
	public void testOneNormativeMarketPayment(){//normative 1 (one order, fulfilled by the market, bill paid in full.)
		assertEquals("Market list should have 2 members in it ", cashier.markets.size(), 2);

		double testBill = 20.0;

		double intiReg = cashier.register;

		cashier.hereIsMarketBill(market1, testBill);

		assertTrue("PickAndExecuteAnAction should return true and call payMarket ", cashier.pickAndExecuteAnAction() );

		assertEquals("Market1's log should now have 1 event in it the MockWaiter's event log reads: "
				+ market1.log.toString(), 1, market1.log.size());

		double afterReg = cashier.register;

		assertEquals("The register should now have 20 less bills in it ", intiReg - afterReg, 20.0);


	}
	public void testTwoMarketPaymentForTwoMarkets(){//normative 2 (one order, fulfilled by two markets, 2 bills paid in full.)
		assertEquals("Market list should have 2 members in it ", cashier.markets.size(), 2);

		double testBill1 = 20.0;

		double initReg = cashier.register;

		cashier.hereIsMarketBill(market1, testBill1);

		assertTrue("PickAndExecuteAnAction should return true and call payMarket ", cashier.pickAndExecuteAnAction() );

		assertEquals("Market1's log should now have 1 event in it the MockWaiter's event log reads: "
				+ market1.log.toString(), 1, market1.log.size());

		assertEquals("Marke2's log should have 0 events in it now the Market2's event log reads: "
				+ market2.log.toString(), 0, market2.log.size());

		double afterReg = cashier.register;

		assertEquals("The register should now have 20 less bills in it ", initReg - afterReg, 20.0);

		assertFalse("PickAndExecuteAnAction should return false now and the Cashier should sleep", cashier.pickAndExecuteAnAction());

		//Here the cook should have processed the fact that not all of the order was fufilled and another bill will be sent in

		double initReg2 = cashier.register;

		double testBill2 = 30.0;

		cashier.hereIsMarketBill(market2, testBill2);

		assertTrue("PickAndExecuteAnAction should return true and call payMarket ", cashier.pickAndExecuteAnAction() );

		assertEquals("Market1's log should still have 1 event in it Market1's event log reads: "
				+ market1.log.toString(), 1, market1.log.size());

		assertEquals("Marke2's log should have 1 event in it now the Market2's event log reads: "
				+ market2.log.toString(), 1, market2.log.size());

		double afterReg2 = cashier.register;

		assertEquals("The register should now have 30 less bills in it ", initReg2 - afterReg2, 30.0);

		assertFalse("PickAndExecuteAnAction should return false now and the Cashier should sleep", cashier.pickAndExecuteAnAction());

		assertEquals("Total register reduction should be 50 after the two transactions ", initReg - afterReg2, 50.0);

	}

	public void testThreeWaiterCashierNormative(){
		String choice1 = "Steak";
		assertFalse("PickAndExecuteAnAction should return false now and the Cashier should sleep", cashier.pickAndExecuteAnAction());

		assertEquals("Customers list should be 0", cashier.customers.size(), 0);

		//waiter requests a bill
		cashier.computeBill(waiter1, customer1, choice1);

		assertEquals("Customers list should be 1", cashier.customers.size(), 1);

		assertTrue("PickAndExecuteAnAction should return true and call generateBill ", cashier.pickAndExecuteAnAction() );

		assertEquals("Waiter's log should have 1 event in it Market1's event log reads: "
				+ waiter1.log.toString(), 1, waiter1.log.size());




	}
	public void testFourMultipleWaiters(){
		String choice1 = "Steak";
		String choice2 = "Salad";

		assertFalse("PickAndExecuteAnAction should return false now and the Cashier should sleep", cashier.pickAndExecuteAnAction());

		assertEquals("Customers list should be 0", cashier.customers.size(), 0);

		//waiter requests a bill
		cashier.computeBill(waiter1, customer1, choice1);

		//another waiter requests bill
		cashier.computeBill(waiter2, customer2, choice2);

		assertEquals("Customers list should be 2", cashier.customers.size(), 2);


		assertTrue("PickAndExecuteAnAction should return true and call generateBill ", cashier.pickAndExecuteAnAction() );

		assertEquals("Waiter 1's log should have 1 event in it Market1's event log reads: "
				+ waiter1.log.toString(), 1, waiter1.log.size());

		assertTrue("PickAndExecuteAnAction should return true and call generateBill ", cashier.pickAndExecuteAnAction() );

		assertEquals("Waiter 2's log should have 1 event in it Market1's event log reads: "
				+ waiter2.log.toString(), 1, waiter2.log.size());

	}

	public void testFiveNormativeCashierCustomerPayment(){
		assertFalse("PickAndExecuteAnAction should return false now and the Cashier should sleep", cashier.pickAndExecuteAnAction());
		String choice1 = "Steak";

		assertEquals("Customers list should be 0", cashier.customers.size(), 0);

		//waiter requests a bill
		cashier.computeBill(waiter1, customer1, choice1);

		assertEquals("Customers list should be 1", cashier.customers.size(), 1);

		assertTrue("PickAndExecuteAnAction should return true and call generateBill ", cashier.pickAndExecuteAnAction() );

		assertEquals("Waiter's log should have 1 event in it Market1's event log reads: "
				+ waiter1.log.toString(), 1, waiter1.log.size());

		//customer tries to make a payment
		cashier.payment(customer1, 16.00, 15.99);

		assertTrue("PickAndExecuteAnAction should return true and call processFinishedCustomer ", cashier.pickAndExecuteAnAction() );

		assertEquals("Customer1's log should now have 1 item in it ", customer1.log.size(), 1);

		assertEquals("Since customer paid his debt the customer list should now be empty", cashier.customers.size(), 0);




	}
	public void testSixNonNormativeMultipleCustomers(){
		assertFalse("PickAndExecuteAnAction should return false now and the Cashier should sleep", cashier.pickAndExecuteAnAction());
		String choice1 = "Steak";

		assertEquals("Customers list should be 0", cashier.customers.size(), 0);

		//waiter requests a bill for 3 of his customers
		cashier.computeBill(waiter1, customer1, choice1);
		cashier.computeBill(waiter1, customer2, choice1);
		cashier.computeBill(waiter1, customer3, choice1);

		assertEquals("Customers list should be 3", cashier.customers.size(), 3);

		assertTrue("PickAndExecuteAnAction should return true and call generateBill ", cashier.pickAndExecuteAnAction() );

		assertEquals("Waiter's log should have 1 event in it Market1's event log reads: "
				+ waiter1.log.toString(), 1, waiter1.log.size());
		assertTrue("PickAndExecuteAnAction should return true and call generateBill ", cashier.pickAndExecuteAnAction() );

		assertEquals("Waiter's log should have 2 events in it Market1's event log reads: "
				+ waiter1.log.toString(), 2, waiter1.log.size());
		assertTrue("PickAndExecuteAnAction should return true and call generateBill ", cashier.pickAndExecuteAnAction() );

		assertEquals("Waiter's log should have 3 events in it Market1's event log reads: "
				+ waiter1.log.toString(), 3, waiter1.log.size());

		//customers 1 try to make payments
		cashier.payment(customer1, 16.00, 15.99);
		cashier.payment(customer2, 100.0, 15.99);
		cashier.payment(customer3, 1.0, 15.99);


		assertTrue("PickAndExecuteAnAction should return true and call processFinishedCustomer ", cashier.pickAndExecuteAnAction() );

		assertEquals("Customer1's log should now have 1 item in it ", customer1.log.size(), 1);

		assertEquals("Since customer paid his debt the customer list should now have 2 customers", cashier.customers.size(), 2);

		assertTrue("PickAndExecuteAnAction should return true and call processFinishedCustomer ", cashier.pickAndExecuteAnAction() );

		assertEquals("Customer2's log should now have 1 item in it ", customer2.log.size(), 1);

		assertEquals("Since customer paid his debt the customer list should now have 1 customers", cashier.customers.size(), 1);

		assertTrue("PickAndExecuteAnAction should return true and call processFinishedCustomer ", cashier.pickAndExecuteAnAction() );

		assertEquals("Customer3's log should now have 1 item in it ", customer3.log.size(), 1);

		assertEquals("Since customer failed to pay his debt the customer list should still have 1 customers", cashier.customers.size(), 1);

		assertFalse("PickAndExecuteAnAction should return false now and the Cashier should sleep", cashier.pickAndExecuteAnAction());

	}

}
