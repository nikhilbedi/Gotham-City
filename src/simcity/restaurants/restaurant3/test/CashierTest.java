package simcity.restaurants.restaurant3.test;

import junit.framework.TestCase;

import java.util.List;

import simcity.restaurants.restaurant3.*;
import simcity.restaurants.restaurant3.Order.OrderState;
import simcity.restaurants.restaurant3.test.mock.*;
import simcity.tests.mock.*;
import junit.framework.*;
/**
 * 
 * This class is a JUnit test class to unit test the CashierAgent's basic interaction
 * with waiters, customers, and the host.
 * It is provided as an example to students in CS201 for their unit testing lab.
 *
 * 
 */
public class CashierTest extends TestCase
{
	//these are instantiated for each test separately via the setUp() method.
	CashierRole cashier;
	MockWaiter waiter;
	MockCustomer customer;
	MockMarket market1;
	MockMarket market2;
	
	
	/**
	 * This method is run before each test. You can use it to instantiate the class variables
	 * for your agent and mocks, etc.
	 */
	public void setUp() throws Exception{
		super.setUp();		
		cashier = new CashierRole();		
		customer = new MockCustomer("mockcustomer");		
		waiter = new MockWaiter("mockwaiter");
		market1 = new MockMarket("mockmarket1");
		market2 = new MockMarket("mockmarket2");
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
		
		customer.cashier = cashier;//You can do almost anything in a unit test.
		
		assertEquals("Cashier should have 0 orders in it. It doesn't.", cashier.orders.size(), 0);
		
		//Order o = new Order(waiter, "Pizza", 1, OrderState.requestCheck, customer);
		//cashier.msgRequestCheck(customer, o);
		
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
		
		//customer.msgHereIsYourCheck(o);
		
		assertTrue("MockCustomer should have logged an event for receiving HereIsYourCheck with the correct balance, "
				+ "but his last event logged reads instead: " 
				+ customer.log.getLastLoggedEvent().toString(), customer.log.containsString("Received msgHereIsYourCheck from waiter. Total = 8.99"));
		
		//cashier.msgCustomerPayingCheck(o);
		
		assertTrue("Cashier's scheduler should have returned true, but didn't.", 
				cashier.pickAndExecuteAnAction());
		
		//assertTrue("Cashier's orders should contain a check with state == paying. It doesn't", 
			//	cashier.orders.get(0).os == OrderState.idle);
		
		assertFalse("Cashier's scheduler should have returned false (no actions left to do), but it didn't.",
				cashier.pickAndExecuteAnAction());
	}
	
	/**
	 * This tests the cashier under very simple terms: one customer cannot pay the exact bill.
	 */
	public void testTwo() {
		try {
			setUp();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		customer.cashier = cashier;//You can do almost anything in a unit test.	
		
		customer.wallet = 0;
		
		assertEquals("Cashier should have 0 orders in it. It doesn't.", cashier.orders.size(), 0);		
		
		//Order o = new Order(waiter, "Pizza", 1, OrderState.requestCheck, customer);
		
		//cashier.msgRequestCheck(customer, o);//send the message from a waiter
		
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
		
		//customer.msgHereIsYourCheck(o);
		
		assertTrue("MockCustomer should have logged an event for receiving HereIsYourCheck with the correct balance, "
				+ "but his last event logged reads instead: " 
				+ customer.log.getLastLoggedEvent().toString(), customer.log.containsString("Received msgHereIsYourCheck from waiter. Total = 8.99"));
		
		double restaurantRevenue = cashier.getRestaurantRevenue();
		
		assertTrue("Customer wallet is not less than the amount the customer owes",
				customer.wallet < cashier.orders.get(0).choice.getFoodPrice());
		
		//cashier.msgCustomerPayingCheck(o);
		
		assertTrue("Cashier's scheduler should have returned true, but didn't.", 
				cashier.pickAndExecuteAnAction());
		
		//assertEquals("Owed should contain this customer. It contains something else instead:"
			//	+ cashier.owed.get(customer), customer, cashier.owed.get(customer));
		
		assertEquals("Cashier should have the same amount of cash in the register before and after the customer tried to pay,"
				+ "but couldn't. Instead, they are different", restaurantRevenue, cashier.getRestaurantRevenue());
		
		//assertTrue("Cashier's orders should contain a check with state == paying. It doesn't", 
			//	cashier.orders.get(0).os == OrderState.idle);
		
		assertFalse("Cashier's scheduler should have returned false (no actions left to do), but it didn't.",
				cashier.pickAndExecuteAnAction());

	
	}
	/**
	 * This tests the cashier under very simple terms: one customer only has enough money to order the cheapest item so he/she does.
	 */
	public void testThree() {
		try {
			setUp();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		customer.cashier = cashier;//You can do almost anything in a unit test.	
		
		customer.wallet = 7;
		
		assertEquals("Cashier should have 0 orders in it. It doesn't.", cashier.orders.size(), 0);		
		
		//Order o = new Order(waiter, "Salad", 1, OrderState.requestCheck, customer);
		
		//cashier.msgRequestCheck(customer, o);//send the message from a waiter
		
		assertEquals("MockWaiter should have an empty event log before the cashier's scheduler is called. Instead, the MockWaiter's event log reads:"
				+ waiter.log.toString(), 0, waiter.log.size());
		
		assertEquals("Cashier should have one order in it. It doesn't.", cashier.orders.size(), 1);
		
		assertTrue("Orders should contain an order with state == requestCheck. It doesn't", 
				cashier.orders.get(0).os == OrderState.requestCheck);
		
		assertTrue("Orders should contain an order of price = $5.99. It contains something else instead: $"
				+ cashier.orders.get(0).choice.getFoodPrice(), cashier.orders.get(0).choice.getFoodPrice() == 5.99);
		
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
		
		//customer.msgHereIsYourCheck(o);
		
		assertTrue("MockCustomer should have logged an event for receiving HereIsYourCheck with the correct balance, "
				+ "but his last event logged reads instead: " 
				+ customer.log.getLastLoggedEvent().toString(), customer.log.containsString("Received msgHereIsYourCheck from waiter. Total = 5.99"));
		
		double restaurantRevenue = cashier.getRestaurantRevenue();
		
		assertTrue("Customer wallet is not less than the second most expensive food option",
				customer.wallet < 8.99);
		assertTrue("Customer wallet is not greater than the cheapest food option of which the customer ordered.",
				customer.wallet > cashier.orders.get(0).choice.getFoodPrice());
		
		//cashier.msgCustomerPayingCheck(o);
		
		assertTrue("Cashier's scheduler should have returned true, but didn't.", 
				cashier.pickAndExecuteAnAction());
		
		//assertEquals("Owed should contain this customer. It contains something else instead:"
			//	+ cashier.owed.get(customer), customer, cashier.owed.get(customer));
		
		assertFalse("Cashier should not have the same amount of cash in the register before and after the customer tried to pay,"
				+ "but couldn't. Instead, they are different", restaurantRevenue != cashier.getRestaurantRevenue());
		
		//assertTrue("Cashier's orders should contain a check with state == paying. It doesn't", 
			//	cashier.orders.get(0).os == OrderState.idle);
		
		assertFalse("Cashier's scheduler should have returned false (no actions left to do), but it didn't.",
				cashier.pickAndExecuteAnAction());
	}
	
	 /**
	 * Tests the cashier with interleaving  from customers and markets, with them paying the exact amount
	 */
	public void testFour(){
		try {
			setUp();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		customer.cashier = cashier;//You can do almost anything in a unit test.			
		market1.cashier = cashier;
		

		assertEquals("Cashier should have 0 orders in it. It doesn't.", cashier.orders.size(), 0);		
		
		//Order o = new Order(waiter, "Salad", 1, OrderState.requestCheck, customer);
		//cashier.msgRequestCheck(customer, o);
		double bill = 11.00;
		cashier.msgPayMarketBill(bill);
		
		assertEquals("MockWaiter should have an empty event log before the Cashier's scheduler is called. Instead, the MockWaiter's event log reads: "
				+ waiter.log.toString(), 0, waiter.log.size());
		
		assertEquals("Cashier should have 1 order in it. It doesn't.", cashier.orders.size(), 1);
		assertTrue("Cashier bill should be greater than 0. It is something different.", bill > 0);
		
		assertTrue("Cashier orders should contain an order with state == requestCheck. It doesn't.",
				cashier.orders.get(0).os == OrderState.requestCheck);
		
		double restaurantRevenue = cashier.getRestaurantRevenue();
		
		assertTrue("Cashier orders should contain an order of price = $5.99. It contains something else instead: $" 
				+ cashier.orders.get(0).choice.getFoodPrice(), cashier.orders.get(0).choice.getFoodPrice() == 5.99);
		
		assertEquals("Market bill should be = $11.00. It is something else instead.", bill, 11.00);
				
		assertTrue("Cashier orders should contain a check with the right customer in it. It doesn't.", 
				cashier.orders.get(0).customer == customer);
		
		assertTrue("Cashier's scheduler should have returned true, but didn't.", 
				cashier.pickAndExecuteAnAction());
		
		//assertTrue("Cashier orders should contain an order with state == paying. It doesn't.",
			//	cashier.orders.get(0).os == OrderState.paying);
		
		assertEquals("MockWaiter should not have an empty event log after the Cashier's scheduler is called for the first time. "
				+ "Instead, the MockWaiter's event log reads: "
						+ waiter.log.toString(), 1, waiter.log.size());
		assertEquals("MockCustomer should have an empty event log after the Cashier's scheduler is called for the first time. "
				+ "Instead, the MockCustomer's event log reads: "
						+ customer.log.toString(), 0, customer.log.size());
		//customer.msgHereIsYourCheck(o);
		
		assertNotSame("Cashier should not have the same amount of revenue before and after he pays the market. "
				+ "Instead, they are the same", 
				restaurantRevenue, cashier.getRestaurantRevenue());
		
		assertTrue("MockCustomer should have logged an event for receiving \"HereIsYourCheck\" with the correct balance, but his last event logged reads instead: " 
				+ customer.log.getLastLoggedEvent().toString(), customer.log.containsString("Received msgHereIsYourCheck from waiter. Total = 5.99"));
		
		restaurantRevenue = cashier.getRestaurantRevenue();
		
		assertNotSame("Cashier should not have the same amount of cash in the register before and after he receives the customer's payment. "
				+ "Instead, they are the same", 
				restaurantRevenue, cashier.getRestaurantRevenue());
	
		restaurantRevenue = cashier.getRestaurantRevenue();
		
		
		assertFalse("Cashier's scheduler should have returned false (no actions left to do), but didn't.", 
				cashier.pickAndExecuteAnAction());
		
	}

	 
	
	/**
	 * Tests the one order, fulfilled by the market, bill paid in full scenario.
	 */
	public void testFive(){
		try {
			setUp();
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		market1.cashier = cashier;
		
		
		assertEquals("Cashier should have 0 bills in it. It doesn't.", cashier.orders.size(), 0);		
		
		//step 1 of the test
		
		double bill = 11.00;
		cashier.msgPayMarketBill(bill);

		//check postconditions for step 1 and preconditions for step 2
		//assertEquals("CashierBills should have 1 bill in it. It doesn't.", cashier.bills.size(), 1);
		
		//assertTrue("CashierBill should contain a bill with state == distributed. It doesn't.",
			//	cashier.bills.get(0).mbs == MarketBillState.distributed);
		//MarketBill mb = new MarketBill(market1, cashier, 11.00, MarketBillState.produced);
		double restaurantRevenue = cashier.getRestaurantRevenue();
		
		//assertTrue("CashierBills should contain a bill of price = $11.00. It contains something else instead: $" 
			//	+ cashier.bill.get(0).bill, cashier.bills.get(0).bill == 11.00);
		
		//assertTrue("CashierBills should contain a bill with the right market in it. It doesn't.", 
			//		cashier.bills.get(0).m == market1);
		/*
		assertTrue("Cashier's scheduler should have returned true (needs to react to market's MarketBillDelivered), but didn't.", 
				cashier.pickAndExecuteAnAction());
		
		market1.msgHereIsPayment(mb, 11.00);//Not necessary, but helps with understanding the steps of the test
		
		*/
		assertNotSame("Cashier should not have the same amount of revenue before and after he pays the market. "
				+ "Instead, they are the same", 
				restaurantRevenue, cashier.getRestaurantRevenue());
		/*
		assertTrue("MockMarket should have logged an event for receiving \"HereIsPayment\" with the correct cash, but his last event logged reads instead: " 
				+ market1.log.getLastLoggedEvent().toString(), market1.log.containsString("Received HereIsPayment from cashier. Total = 11.0"));
		*/
		restaurantRevenue = cashier.getRestaurantRevenue();
		//cashiersCashBalance = cashier.getCashInRegister();
		/*
		cashier.msgHereIsChange(mb.change);
		
		assertEquals("Cashier should have the same amount of cash in the register before and after he receives his change "
				+ "because he paid the exact amount on the Market bill. Instead, they are different", 
				cashiersCashBalance, cashier.getCashInRegister());
		*/
		assertFalse("Cashier's scheduler should have returned false (no actions left to do), but didn't.", 
				cashier.pickAndExecuteAnAction());
		
	}
	
	
	
	/**
	 * Tests the one order, fulfilled by TWO markets, 2 bills paid in full scenario.
	 */
	public void testSix(){
		try {
			setUp();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		market1.cashier = cashier;
		market2.cashier = cashier;
		
		assertEquals("Cashier should have 0 bills in it. It doesn't.", cashier.orders.size(), 0);		
		
		//step 1 of the test
		
		MarketOrder m1 = new MarketOrder("Chicken", 5);
		MarketOrder m2 = new MarketOrder("Chicken", 1);
		
		MarketFood f1 = Food.get("Chicken");
		MarketFood f2 = Food.get("Chicken");
		
		cashier.msgPayMarketBill(m1.getAmountNeeded() * 7.99);
		
		/*
		cashier.msgMarketBillDelivered(mb1);//send the message from a waiter
	*/
		//assertEquals("CashierBills should have 1 bill in it. It doesn't.", cashier.bills.size(), 1);
		//assertEquals("Cashier")
		double restaurantRevenue = cashier.getRestaurantRevenue();
		
		assertNotSame("Cashier should not have the same amount of revenue before and after he pays the market. "
				+ "Instead, they are the same", 
				restaurantRevenue, cashier.getRestaurantRevenue());
		
		
		restaurantRevenue = cashier.getRestaurantRevenue();
		
		assertNotSame("Cashier should not have the same amount of cash in the register before and after he pays the market. "
				+ "Instead, they are the same", 
				restaurantRevenue, cashier.getRestaurantRevenue());
		
		assertFalse("Cashier's scheduler should have returned false (no actions left to do), but didn't.", 
				cashier.pickAndExecuteAnAction());
	}
	

	
	public void testOneNormalCustomerScenario()
	{
		//setUp() runs first before this test!
		
		customer.cashier = cashier;//You can do almost anything in a unit test.		
		
		//check preconditions
		 assertEquals("Cashier should have 0 bills in it. It doesn't.",cashier.orders.size(), 0);	
		//Order(WaiterAgent waiter, String foodChoice, int tableNumber, OrderState os, CustomerAgent customer){ 
		 //Order order = new Order(waiter, "Chicken", 1,  );
		 Food food = new Food("Chicken");
		// cashier.msgRequestCheck(customer, Order);
		 /*
		assertEquals("CashierAgent should have an empty event log before the Cashier's HereIsBill is called. Instead, the Cashier's event log reads: "
						+ cashier.log.toString(), 0, cashier.log.size());
		
		//step 1 of the test
		//public Bill(Cashier, Customer, int tableNum, double price) {
		Bill bill = new Bill(cashier, customer, 2, 7.98);
		cashier.HereIsBill(bill);//send the message from a waiter
		

		//check postconditions for step 1 and preconditions for step 2
		 * */
		//assertEquals("MockWaiter should have an empty event log before the Cashier's scheduler is called. Instead, the MockWaiter's event log reads: "
			//			+ waiter.log.toString(), 0, waiter.log.size());
		/*
		assertEquals("Cashier should have 1 bill in it. It doesn't.", cashier.bills.size(), 1);
		
		assertFalse("Cashier's scheduler should have returned false (no actions to do on a bill from a waiter), but didn't.", cashier.pickAndExecuteAnAction());
		
		assertEquals(
				"MockWaiter should have an empty event log after the Cashier's scheduler is called for the first time. Instead, the MockWaiter's event log reads: "
						+ waiter.log.toString(), 0, waiter.log.size());
		
		assertEquals(
				"MockCustomer should have an empty event log after the Cashier's scheduler is called for the first time. Instead, the MockCustomer's event log reads: "
						+ waiter.log.toString(), 0, waiter.log.size());
		
		//step 2 of the test
		cashier.ReadyToPay(customer, bill);
		
		//check postconditions for step 2 / preconditions for step 3
		assertTrue("CashierBill should contain a bill with state == customerApproached. It doesn't.",
				cashier.bills.get(0).state == cashierBillState.customerApproached);
		
		assertTrue("Cashier should have logged \"Received ReadyToPay\" but didn't. His log reads instead: " 
				+ cashier.log.getLastLoggedEvent().toString(), cashier.log.containsString("Received ReadyToPay"));

		assertTrue("CashierBill should contain a bill of price = $7.98. It contains something else instead: $" 
				+ cashier.bills.get(0).bill.netCost, cashier.bills.get(0).bill.netCost == 7.98);
		
		assertTrue("CashierBill should contain a bill with the right customer in it. It doesn't.", 
					cashier.bills.get(0).bill.customer == customer);
		
		
		//step 3
		//NOTE: I called the scheduler in the assertTrue statement below (to succintly check the return value at the same time)
		assertTrue("Cashier's scheduler should have returned true (needs to react to customer's ReadyToPay), but didn't.", 
					cashier.pickAndExecuteAnAction());
		
		//check postconditions for step 3 / preconditions for step 4
		assertTrue("MockCustomer should have logged an event for receiving \"HereIsYourTotal\" with the correct balance, but his last event logged reads instead: " 
				+ customer.log.getLastLoggedEvent().toString(), customer.log.containsString("Received HereIsYourTotal from cashier. Total = 7.98"));
	
			
		assertTrue("Cashier should have logged \"Received HereIsMyPayment\" but didn't. His log reads instead: " 
				+ cashier.log.getLastLoggedEvent().toString(), cashier.log.containsString("Received HereIsMyPayment"));
		
		
		assertTrue("CashierBill should contain changeDue == 0.0. It contains something else instead: $" 
				+ cashier.bills.get(0).changeDue, cashier.bills.get(0).changeDue == 0);
		
		
		
		//step 4
		assertTrue("Cashier's scheduler should have returned true (needs to react to customer's ReadyToPay), but didn't.", 
					cashier.pickAndExecuteAnAction());
		
		//check postconditions for step 4
		assertTrue("MockCustomer should have logged an event for receiving \"HereIsYourChange\" with the correct change, but his last event logged reads instead: " 
				+ customer.log.getLastLoggedEvent().toString(), customer.log.containsString("Received HereIsYourChange from cashier. Change = 0.0"));
	
		
		assertTrue("CashierBill should contain a bill with state == done. It doesn't.",
				cashier.bills.get(0).state == cashierBillState.done);
		
		assertFalse("Cashier's scheduler should have returned false (no actions left to do), but didn't.", 
				cashier.pickAndExecuteAnAction());
		*/
	
	}//end one normal customer scenario
	
	
}
