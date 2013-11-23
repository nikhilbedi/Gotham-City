package simcity.restaurants.restaurant1.test;

import java.util.List;


import simcity.restaurants.restaurant1.CashierRole;
import simcity.restaurants.restaurant1.CashierRole.Check;
import simcity.restaurants.restaurant1.test.mock.*;
import simcity.tests.mock.*;

import junit.framework.*;

/**
 * 
 * This class is a JUnit test class to unit test the CashierAgent's basic interaction
 * with waiters, customers, and the host.
 *
 * @author Nikhil Bedi
 */
public class CashierTest extends TestCase
{
	//these are instantiated for each test separately via the setUp() method.
	CashierRole cashier;
	MockWaiter waiter;
	MockCustomer customer;
	MockMarket market;
	
	
	/**
	 * This method is run before each test. You can use it to instantiate the class variables
	 * for your agent and mocks, etc.
	 */
	public void setUp() throws Exception{
		super.setUp();		
		//cashier = new CashierRole("cashier");		
		customer = new MockCustomer("mockcustomer");		
		waiter = new MockWaiter("mockwaiter");
		market = new MockMarket("mockmarket");
	}	
	/**
	 * This tests the cashier under very simple terms: one waiter approaches cashier to make check.
	 * Waiter takes check to customer and customer is ready to pay the exact bill.
	 */
	public void testOneCustomerScenario()
	{
		//setUp() runs first before this test!
	    customer.cashier = cashier;//You can do almost anything in a unit test.			
		
		//check preconditions
		assertEquals("Cashier should have 0 bills in it. It doesn't.",cashier.checks.size(), 0);		
		assertEquals("CashierAgent should have an empty event log before the Cashier's HereIsBill is called. Instead, the Cashier's event log reads: "
						+ cashier.log.toString(), 0, cashier.log.size());
		
		//step 1 of the test
		cashier.makeCheck(waiter, customer, "Steak");
		Check check = cashier.checks.get(0);


		//check postconditions for step 1 and preconditions for step 2
		assertEquals("MockWaiter should have an empty event log before the Cashier's scheduler is called. Instead, the MockWaiter's event log reads: "
						+ waiter.log.toString(), 0, waiter.log.size());
		
		assertEquals("Cashier should have 1 bill in it. It doesn't.", 1, cashier.checks.size());
		
		//Note: this also runs pickAndExecuteAnAction()
		assertTrue("Cashier's scheduler should have returned true (an action to give a check to a waiter), but didn't.", cashier.pickAndExecuteAnAction());
		
		assertTrue("The check amount should be positive because the customer has yet to pay. It is not.", check.amount > 0.0);
		
		assertEquals(
				"MockCustomer should have an empty event log after the Cashier's scheduler is called for the first time. Instead, the MockCustomer's event log reads: "
						+ customer.log.toString(), 0, customer.log.size());
		
		//step 2
		assertFalse("Cashier's scheduler should have returned false (the check is in a state that doesn't need to be acted upon), but didn't.", cashier.pickAndExecuteAnAction());
		
		
		//post-conditions to step 2 and preconditions to step 3
		assertEquals("MockWaiter should have an event log of size one after the Cashier's scheduler is called. Instead, the MockWaiter's event log reads: "
						+ waiter.log.toString(), 1, waiter.log.size());
		
		assertEquals("MockCustomer should still have an event log of size zero after the Cashier's scheduler is called. Instead, the MockWaiter's event log reads: "
				+ waiter.log.toString(), 0, customer.log.size());
		
		assertEquals("Cashier should still have 1 bill in it. It doesn't.", 1, cashier.checks.size());
		
		assertTrue("The check should not contain a cashPaid value. It doesn't.", check.cashPaid == 0);
		
		
		//step 3 -- Now we need the waiter to give the check to the customer
		customer.hereIsBill(check);
		
		//post-conditions to step 3 and preconditions to step 4
		assertEquals("MockCustomer should have one log entry after the bill is given to this customer. It doesn't.", 1, customer.log.size());

		assertEquals("MockWaiter should have one log entry after the bill is given to the MockCustomer. It doesn't.", 1, waiter.log.size());

		assertEquals("Cashier should still have 1 bill in it. It doesn't.", 1, cashier.checks.size());

		assertFalse("The check should now contain a cashPaid value. The customer will never give 0 dollars. It doesn't.", check.cashPaid == 0);
		
		assertTrue("The check amount should still be positive because the cashier hasn't processed the customer's paid amount. It is not.", check.amount > 0.0);
		
		
		//step 4
		assertTrue("Cashier's scheduler should have returned true (an action to process amount paid and give change to customer), but didn't.", cashier.pickAndExecuteAnAction()); 
		
		assertTrue("Cashier's list should be empty because the customer was able to pay it in full. There was still something left in the list", cashier.checks.isEmpty());
		
		assertTrue("The amount of change the customer receives is zero or positive. This wasn't the case", check.cashPaid >= 0.0);
		
		assertTrue("Because the check was paid in full, the amount to be paid should be zero. It wasn't", check.amount == 0.0);
	}
	
	/**
	 * One waiter, one ordinary customer and one flake interleaved
	 */
	public void testSecondCustomerScenario() {
		customer.cashier = cashier;//You can do almost anything in a unit test.			
		MockCustomer customer2 = new MockCustomer("flake");	
		customer2.cashier = cashier;
		
		//check preconditions
		assertEquals("Cashier should have 0 bills in it. It doesn't.",cashier.checks.size(), 0);		
		assertEquals("CashierAgent should have an empty event log before the Cashier's HereIsBill is called. Instead, the Cashier's event log reads: "
						+ cashier.log.toString(), 0, cashier.log.size());
		
		//Everyone should have empty logs
		//Waiter messages about one customer
		//Waiter messages about second customer
		//step 1 of the test
		cashier.makeCheck(waiter, customer, "Steak");
		cashier.makeCheck(waiter, customer2, "Salad");
		//This is for the sake initializing so Java won't get mad. This will be overwritten if certain conditions in my test are satisfied 
		Check check1 = cashier.new Check(waiter, customer, "Steak");
		Check check2 = cashier.new Check(waiter, customer2, "Pizza");
		
		
		//check postconditions for step 1 and preconditions for step 2
		//Both Checks are created (there should be two)
		assertEquals("There should be two checks in the cashier's checks list. There isn't.", 2, cashier.checks.size());
		
				//signify which check belongs to whom (Lists sort them, so they may not be in the order we think they are)
		for(Check c : cashier.checks) {
			if(c.customer == customer)
				check1 = c;
			else if(c.customer == customer2) 
				check2 = c;
		}
		
		assertEquals("Waiter's log should be empty. It isn't.", 0, waiter.log.size());
		
		assertEquals("Customer 1's log should be empty. It isn't.", 0, customer.log.size());
		
		assertEquals("Customer 2's log should be empty. It isn't.", 0, customer2.log.size());
		
		//Step 2
		assertTrue("Cashier's scheduler should have returned true (an action to give a check to a waiter), but didn't.", cashier.pickAndExecuteAnAction());
		
		//Step 2 postconditions, step 3 preconditions
		assertTrue("The first check amount should be positive because the cashier hasn't processed the customer's paid amount. They are not.", check1.amount > 0.0);
		
		assertFalse("The first check amount should be zero because the cashier has only run pickAndExecuteAnAction once, thus never reaching the second check processing yet.. They are not.", cashier.checks.get(1).amount > 0.0);
		
		assertEquals("MockWaiter should have an event log of size one after the Cashier's scheduler is called. Instead, the MockWaiter's event log reads: "
				+ waiter.log.toString(), 1, waiter.log.size());
		
		assertEquals("Customer 1's log should be empty. It isn't.", 0, customer.log.size());
		
		assertEquals("Customer 2's log should be empty. It isn't.", 0, customer2.log.size());
		
		//Step 3
		assertTrue("Cashier's scheduler should have returned true (an action to give a check to a waiter), but didn't.", cashier.pickAndExecuteAnAction());
		
		
		//Step 3 postconditions, step 3 preconditions
		assertTrue("The first check amount should be positive because the cashier hasn't processed the customer's paid amount. They are not.", check1.amount > 0.0);
		
		assertTrue("The second check amount should be positive because the cashier hasn't processed the customer's paid amount. They are not.", check2.amount > 0.0);
		
		assertEquals("MockWaiter should have an event log of size one after the Cashier's scheduler is called. Instead, the MockWaiter's event log reads: "
				+ waiter.log.toString(), 2, waiter.log.size());
		
		assertEquals("Customer 1's log should be empty. It isn't.", 0, customer.log.size());
		
		assertEquals("Customer 2's log should be empty. It isn't.", 0, customer2.log.size());
		
		//Step 4
		assertFalse("Cashier's scheduler should have returned false (both checks have been handed to the waiter at this point), but didn't.", cashier.pickAndExecuteAnAction());
		
		
		//Step 4 postconditions, step 5 preconditions
		assertEquals("MockWaiter should have an event log of size one after the Cashier's scheduler is called. Instead, the MockWaiter's event log reads: "
				+ waiter.log.toString(), 2, waiter.log.size());
		
		assertEquals("Customer 1's log should be empty. It isn't.", 0, customer.log.size());
		
		assertEquals("Customer 2's log should be empty. It isn't.", 0, customer2.log.size());
		
		assertTrue("Both checks should not contain a cashPaid value. They do.", check1.cashPaid == 0 && check2.cashPaid == 0);
		
		
		//Step 5
		//Both customers message about their payment at the same time
		customer.hereIsBill(check1);
		customer2.hereIsBill(check2);
		
		
		//Step 5 postconditions, step 6 preconditions
		assertEquals("MockWaiter should have an event log of size one after the Cashier's scheduler is called. Instead, the MockWaiter's event log reads: "
				+ waiter.log.toString(), 2, waiter.log.size());
		
		assertEquals("Customer 1's log should have one entry. It doesnt.", 1, customer.log.size());
		
		assertEquals("Customer 2's log should have one entry. It doesnt.", 1, customer2.log.size());
		
		assertTrue("Both checks should contain a cashPaid value. They dont.", check1.cashPaid >= 0 && check2.cashPaid >= 0);
		
		//step 6
		assertTrue("Cashier's scheduler should have returned true (an action to give change to a customer), but didn't.", cashier.pickAndExecuteAnAction());
		
		
		//Step 6 postconditions, step 7 preconditions
		//Cashier gives change back to one first
			//There should actually be one OR two checks remaining. Because one of our customer's names is flake, one check will be held. We just don't know which one will be processed first
		assertTrue("Cashier's check size should be one or two at this point.", cashier.checks.size() ==1 || cashier.checks.size() == 2);
		
		
		//step 7
		assertTrue("Cashier's scheduler should have returned true (an action to give change to a customer), but didn't.", cashier.pickAndExecuteAnAction());
		
		
		//Step 7 postconditions, step 8 preconditions
		//Cashier gives change back to the next one
			//one remaining
		assertEquals("Cashier's checks size should be 1. It isnt.", 1, cashier.checks.size());
		
		assertTrue("The remaining check should belong to customer2. It doesnt.", cashier.checks.get(0) == check2);
		
		assertTrue("the remaining check's amount be greater than zero. It is not.", check2.amount > 0);
		
		//Check the other check's conditions
		assertEquals("The check1 should have an amount of zero because it was fully paid. It doesnt", 0.0, check1.amount);
		
		assertEquals("Customer 1's log should have two entries. It doesnt.", 2, customer.log.size());
		
		assertEquals("Customer 2's log should have two entries. It doesnt.", 2, customer2.log.size());
	}
	
	
	/**
	 * two waiters, two ordinary customers
	 */
	public void testThirdCustomerScenario() {
		customer.cashier = cashier;//You can do almost anything in a unit test.			
		MockCustomer customer2 = new MockCustomer("mockcustomer2");	
		customer2.cashier = cashier;
		
		MockWaiter waiter2 = new MockWaiter("mockwaiter2");
		
		//check preconditions
		assertEquals("Cashier should have 0 bills in it. It doesn't.",cashier.checks.size(), 0);		
		assertEquals("CashierAgent should have an empty event log before the Cashier's HereIsBill is called. Instead, the Cashier's event log reads: "
						+ cashier.log.toString(), 0, cashier.log.size());
		
		//Everyone should have empty logs
		//Waiter1 messages about one customer
		//Waiter2 messages about second customer
		//step 1 of the test
		cashier.makeCheck(waiter, customer, "Steak");
		cashier.makeCheck(waiter2, customer2, "Salad");
		//This is for the sake initializing so Java won't get mad. This will be overwritten if certain conditions in my test are satisfied 
		Check check1 = cashier.new Check(waiter, customer, "Steak");
		Check check2 = cashier.new Check(waiter, customer2, "Pizza");
		
		
		//check postconditions for step 1 and preconditions for step 2
		//Both Checks are created (there should be two)
		assertEquals("There should be two checks in the cashier's checks list. There isn't.", 2, cashier.checks.size());
		
		//signify which check belongs to whom (Lists sort them, so they may not be in the order we think they are)
		for(Check c : cashier.checks) {
			if(c.customer == customer)
				check1 = c;
			else if(c.customer == customer2) 
				check2 = c;
		}
		
		assertEquals("Waiter's log should be empty. It isn't.", 0, waiter.log.size());
		
		assertEquals("Customer 1's log should be empty. It isn't.", 0, customer.log.size());
		
		assertEquals("Customer 2's log should be empty. It isn't.", 0, customer2.log.size());
		
		//Step 2
		assertTrue("Cashier's scheduler should have returned true (an action to give a check to a waiter), but didn't.", cashier.pickAndExecuteAnAction());
		
		//Step 2 postconditions, step 3 preconditions
		assertTrue("The first check amount should be positive because the cashier hasn't processed the customer's paid amount. They are not.", check1.amount > 0.0);
		
		assertFalse("The first check amount should be zero because the cashier has only run pickAndExecuteAnAction once, thus never reaching the second check processing yet.. They are not.", cashier.checks.get(1).amount > 0.0);
		
		assertEquals("MockWaiter should have an event log of size one after the Cashier's scheduler is called. Instead, the MockWaiter's event log reads: "
				+ waiter.log.toString(), 1, waiter.log.size());
		
		assertEquals("MockWaiter2 should have an event log of size one after the Cashier's scheduler is called. Instead, the MockWaiter2's event log reads: "
				+ waiter2.log.toString(), 0, waiter2.log.size());
		
		assertEquals("Customer 1's log should be empty. It isn't.", 0, customer.log.size());
		
		assertEquals("Customer 2's log should be empty. It isn't.", 0, customer2.log.size());
		
		//Step 3
		assertTrue("Cashier's scheduler should have returned true (an action to give a check to a waiter), but didn't.", cashier.pickAndExecuteAnAction());
		
		
		//Step 3 postconditions, step 3 preconditions
		assertTrue("The first check amount should be positive because the cashier hasn't processed the customer's paid amount. They are not.", check1.amount > 0.0);
		
		assertTrue("The second check amount should be positive because the cashier hasn't processed the customer's paid amount. They are not.", check2.amount > 0.0);
		
		assertEquals("MockWaiter should have an event log of size one after the Cashier's scheduler is called. Instead, the MockWaiter's event log reads: "
				+ waiter.log.toString(), 1, waiter.log.size());
		
		assertEquals("MockWaiter2 should have an event log of size one after the Cashier's scheduler is called. Instead, the MockWaiter2's event log reads: "
				+ waiter2.log.toString(), 1, waiter2.log.size());
		
		assertEquals("Customer 1's log should be empty. It isn't.", 0, customer.log.size());
		
		assertEquals("Customer 2's log should be empty. It isn't.", 0, customer2.log.size());
		
		//Step 4
		assertFalse("Cashier's scheduler should have returned false (both checks have been handed to the waiter at this point), but didn't.", cashier.pickAndExecuteAnAction());
		
		
		//Step 4 postconditions, step 5 preconditions
		assertEquals("MockWaiter should have an event log of size one after the Cashier's scheduler is called. Instead, the MockWaiter's event log reads: "
				+ waiter.log.toString(), 1, waiter.log.size());
		
		assertEquals("MockWaiter2 should have an event log of size one after the Cashier's scheduler is called. Instead, the MockWaiter2's event log reads: "
				+ waiter2.log.toString(), 1, waiter2.log.size());
		
		assertEquals("Customer 1's log should be empty. It isn't.", 0, customer.log.size());
		
		assertEquals("Customer 2's log should be empty. It isn't.", 0, customer2.log.size());
		
		assertTrue("Both checks should not contain a cashPaid value. They do.", check1.cashPaid == 0 && check2.cashPaid == 0);
		
		
		//Step 5
		//Both customers message about their payment at the same time
		customer.hereIsBill(check1);
		customer2.hereIsBill(check2);
		
		
		//Step 5 postconditions, step 6 preconditions
		assertEquals("MockWaiter should have an event log of size one after the Cashier's scheduler is called. Instead, the MockWaiter's event log reads: "
				+ waiter.log.toString(), 1, waiter.log.size());
		
		assertEquals("MockWaiter2 should have an event log of size one after the Cashier's scheduler is called. Instead, the MockWaiter2's event log reads: "
				+ waiter2.log.toString(), 1, waiter2.log.size());
		
		assertEquals("Customer 1's log should have one entry. It doesnt.", 1, customer.log.size());
		
		assertEquals("Customer 2's log should have one entry. It doesnt.", 1, customer2.log.size());
		
		assertTrue("Both checks should contain a cashPaid value. They dont.", check1.cashPaid >= 0 && check2.cashPaid >= 0);
		
		//step 6
		assertTrue("Cashier's scheduler should have returned true (an action to give change to a customer), but didn't.", cashier.pickAndExecuteAnAction());
		
		
		//postconditions to step 6 and preconditions to step 7
		assertEquals("Cashier's check size should now be one, since the customer is paying in full. It's not.", 1, cashier.checks.size());
		
		assertTrue("The remaining check to be process should still have an amount to be paid. It doesn't", cashier.checks.get(0).amount > 0.0);
		
		
		//step 7
		assertTrue("Cashier's scheduler should have returned true (an action to give change to a customer), but didn't.", cashier.pickAndExecuteAnAction());
		
		assertEquals("Cashier's check size should now be empty, since the customer is paying in full. It's not.", 0, cashier.checks.size());
		
		assertTrue("The amounts of both checks should be zero since they were paid in full. They're not.", check1.amount == 0.0 && check2.amount == 0.0);

	}
	
	/**
	 * one market messages cashier and cashier pays exact bill
	 */
	public void testFourthCustomerScenario() {
		market.cashier = cashier;
		
		//check preconditions	
		assertEquals("Cashier should have an empty event log before the Cashier's HereIsBill is called. Instead, the Cashier's event log reads: "
						+ cashier.log.toString(), 0, cashier.log.size());
		
		assertEquals("Market should have an empty event log before the Cashier's HereIsBill is called. Instead, the Market's event log reads: "
				+ market.log.toString(), 0, market.log.size());
		
		assertEquals("Cashier's deliveryBills list should be at a size of zero. It isn't.", 0, cashier.billsToPay.size());
	
		
		//step 1
		//market messages cashier
		cashier.payMarketBill(market, 10.0);
		
		//Check step 1 post conditions and step 2 preconditions
		//Check if logs are correct
		assertEquals("Cashier should have an log size of one. it doesnt", 1, cashier.log.size());
		
		assertEquals("Market should have an empty event log before the Cashier's HereIsBill is called. Instead, the Market's event log reads: "
		+ market.log.toString(), 0, market.log.size());
		
		//cashier's deliverybills should be of size one
		assertEquals("Cashier's deliveryBills list should be at a size of one. It isn't.", 1, cashier.billsToPay.size());
		
		//Check if its amount is >0
		assertTrue("Bills should always have a greater than zero amount. This bill doesnt", cashier.billsToPay.get(0).amount >0.0);

		//step 2
		//Do pickAndExecuteAnAction - should be true
		assertTrue("Cashier's scheduler should have returned true (an action to give payment of market's bill), but didn't.", cashier.pickAndExecuteAnAction());
		
		//check postconditions to step 2 and preconditions to step 3
		//Check logs - cashier should have 1, market should have 1
		assertEquals("Cashier should have an log size of one. it doesnt", 1, cashier.log.size());
		
		assertEquals("Market should have an log size of one. it doesnt", 1, market.log.size());
		
		//size of BillsToPay is zero now
		assertEquals("Cashier's deliveryBills list should be at a size of zero. It isn't.", 0, cashier.billsToPay.size());
		
		//step 3
		//Run pickAndexectute, should return false
		assertFalse("Cashier's scheduler should have returned false, but didn't.", cashier.pickAndExecuteAnAction());
	}
	
	/**
	 * two markets message cashier at the same time and cashier pays both bills exactly
	 */
	public void testFifthCustomerScenario() {
		market.cashier = cashier;
		MockMarket market2 = new MockMarket("mockmarket2");
		market2.cashier = cashier;
		
		//check preconditions	
		assertEquals("Cashier should have an empty event log before the Cashier's HereIsBill is called. Instead, the Cashier's event log reads: "
						+ cashier.log.toString(), 0, cashier.log.size());
		
		assertEquals("Market1 should have an empty event log before the Cashier's HereIsBill is called. Instead, the Market1's event log reads: "
				+ market.log.toString(), 0, market.log.size());
		
		assertEquals("Market2 should have an empty event log before the Cashier's HereIsBill is called. Instead, the Market2's event log reads: "
				+ market2.log.toString(), 0, market2.log.size());
		
		assertEquals("Cashier's deliveryBills list should be at a size of zero. It isn't.", 0, cashier.billsToPay.size());
	
		
		//step 1
		//market messages cashier
		cashier.payMarketBill(market, 10.0);
		cashier.payMarketBill(market2, 10.0);
		
		//Check step 1 post conditions and step 2 preconditions
		//Check if logs are correct
		assertEquals("Cashier should have an log size of two. it doesnt", 2, cashier.log.size());
		
		assertEquals("Market1 should have an empty event log before the Cashier's HereIsBill is called. Instead, the Market1's event log reads: "
		+ market.log.toString(), 0, market.log.size());

		assertEquals("Market2 should have an empty event log before the Cashier's HereIsBill is called. Instead, the Market2's event log reads: "
		+ market2.log.toString(), 0, market2.log.size());
		
		//cashier's deliverybills should be of size one
		assertEquals("Cashier's deliveryBills list should be at a size of two. It isn't.", 2, cashier.billsToPay.size());
		
		//Check if its amount is >0
		assertTrue("Bills should always have a greater than zero amount. This bill doesnt", cashier.billsToPay.get(0).amount >0.0);
		
		assertTrue("Bills should always have a greater than zero amount. This bill doesnt", cashier.billsToPay.get(1).amount >0.0);

		//step 2
		//Do pickAndExecuteAnAction - should be true
		assertTrue("Cashier's scheduler should have returned true (an action to give payment of market's bill), but didn't.", cashier.pickAndExecuteAnAction());
		
		//check postconditions to step 2 and preconditions to step 3
		//Check logs - cashier should have 1, market1 should have 1, market2 should still be at 0
		assertEquals("Cashier should have an log size of two. it doesnt", 2, cashier.log.size());
		
		assertEquals("Market1 should have an log size of one. it doesnt", 1, market.log.size());
		
		assertEquals("Market2 should have an empty event log before the Cashier's HereIsBill is called. Instead, the Market2's event log reads: "
		+ market2.log.toString(), 0, market2.log.size());
		
		//size of BillsToPay is one now
		assertEquals("Cashier's deliveryBills list should be at a size of one. It isn't.", 1, cashier.billsToPay.size());
		
		//Do pickAndExecuteAnAction - should be true
		assertTrue("Cashier's scheduler should have returned true (an action to give payment of market's bill), but didn't.", cashier.pickAndExecuteAnAction());
		
		//check postconditions to step 2 and preconditions to step 3
		//Check logs - cashier should have 1, market1 should have 1, market2 should still be at 0
		assertEquals("Cashier should have an log size of two. it doesnt", 2, cashier.log.size());
		
		assertEquals("Market1 should have an log size of one. it doesnt", 1, market.log.size());

		assertEquals("Market2 should have an log size of one. it doesnt", 1, market2.log.size());
		
		//size of BillsToPay is one now
		assertEquals("Cashier's deliveryBills list should be at a size of zero. It isn't.", 0, cashier.billsToPay.size());
				
		
		//step 3
		//Run pickAndexectute, should return false
		assertFalse("Cashier's scheduler should have returned false, but didn't.", cashier.pickAndExecuteAnAction());
	}
	
	/**
	 * one market messages cashier interleaved with customer paying bill
	 */
	public void testSixthCustomerScenario() {
		market.cashier = cashier;
		customer.cashier = cashier;
		
		//check preconditions	
		assertEquals("Cashier should have an empty event log before the Cashier's messages come in. Instead, the Cashier's event log reads: "
						+ cashier.log.toString(), 0, cashier.log.size());
		
		assertEquals("Market should have an empty event log at the start. Instead, the Market's event log reads: "
				+ market.log.toString(), 0, market.log.size());
		
		assertEquals("Customer should have an empty event log at the start. Instead, the Customer event log reads: "
				+ customer.log.toString(), 0, customer.log.size());
		
		assertEquals("MockWaiter should have an empty event log before the Cashier's scheduler is called. Instead, the MockWaiter's event log reads: "
				+ waiter.log.toString(), 0, waiter.log.size());
		
		assertEquals("Cashier's deliveryBills list should be at a size of zero. It isn't.", 0, cashier.billsToPay.size());
		
		assertEquals("Cashier's checks list should be at a size of zero. It isn't.", 0, cashier.checks.size());
	
		
		//step 1
		//market messages cashier
		cashier.payMarketBill(market, 10.0);
		cashier.makeCheck(waiter, customer, "Steak");
		
		//Check step 1 post conditions and step 2 preconditions
		//Check if logs are correct
		assertEquals("Cashier should have an log size of two. it doesnt", 2, cashier.log.size());
		
		assertEquals("Market should have an empty event log before the Cashier's HereIsBill is called. Instead, the Market's event log reads: "
		+ market.log.toString(), 0, market.log.size());
		
		assertEquals("MockWaiter should have an empty event log before the Cashier's scheduler is called. Instead, the MockWaiter's event log reads: "
				+ waiter.log.toString(), 0, waiter.log.size());
		
		assertEquals("Customer should have an empty event log at the start. Instead, the Customer event log reads: "
				+ customer.log.toString(), 0, customer.log.size());
		
		//cashier's deliverybills and checks should be of size one
		assertEquals("Cashier's deliveryBills list should be at a size of one. It isn't.", 1, cashier.billsToPay.size());
		
		assertEquals("Cashier's checks list should be at a size of one. It isn't.", 1, cashier.checks.size());
		
		//Check if its amount is >0
		assertTrue("Bills should always have a greater than zero amount. This bill doesnt", cashier.billsToPay.get(0).amount >0.0);
		
		//the check should not have any amount yet because it has yet to be processed
		assertTrue("The check should not have any amount yet because it has yet to be processed. This check has an amount", cashier.checks.get(0).amount == 0.0);
		
		
		//step 2
		//Do pickAndExecuteAnAction - should be true
		assertTrue("Cashier's scheduler should have returned true (an action to give payment of market's bill), but didn't.", cashier.pickAndExecuteAnAction());
		
		//check postconditions to step 2 and preconditions to step 3
		//Check logs - cashier should have 1, market should have 1
		assertEquals("Cashier should have an log size of two. it doesnt", 2, cashier.log.size());
		
		assertEquals("Market should have an log size of one. it doesnt", 1, market.log.size());
		
		assertEquals("MockWaiter should have an empty event log before the Cashier's scheduler is called. Instead, the MockWaiter's event log reads: "
				+ waiter.log.toString(), 0, waiter.log.size());
		
		assertEquals("Customer should have an empty event log at the start. Instead, the Customer event log reads: "
				+ customer.log.toString(), 0, customer.log.size());
		
		//size of BillsToPay is zero now
		assertEquals("Cashier's deliveryBills list should be at a size of zero. It isn't.", 0, cashier.billsToPay.size());
		
		//checks should still be one
		assertEquals("Cashier's checks list should be at a size of one. It isn't.", 1, cashier.checks.size());
		
		assertTrue("The check should not have any amount yet because it has yet to be processed. This check has an amount", cashier.checks.get(0).amount == 0.0);
	
		
		//step 3
		//Do pickAndExecuteAnAction - should be true
		assertTrue("Cashier's scheduler should have returned true (an action to create the check), but didn't.", cashier.pickAndExecuteAnAction());
		
		
		//Step 3 postconditions, step 4 preconditions
		assertEquals("Market should have an log size of one. it doesnt", 1, market.log.size());
		
		assertEquals("Cashier's deliveryBills list should be at a size of zero. It isn't.", 0, cashier.billsToPay.size());
		
		assertTrue("The check amount should now be positive because the cashier has now created the check. it is not.", cashier.checks.get(0).amount > 0.0);
		
		assertEquals("MockWaiter should have an event log of size one after the Cashier's scheduler is called. Instead, the MockWaiter's event log reads: "
				+ waiter.log.toString(), 1, waiter.log.size());
		
		assertEquals("Customer's log should be empty. It isn't.", 0, customer.log.size());
		
		assertEquals("Cashier's checks list should be at a size of one. It isn't.", 1, cashier.checks.size());
		
		assertTrue("The check should not contain a cashPaid value. It doesnt.", cashier.checks.get(0).cashPaid == 0);
		
		//step 4
		customer.hereIsBill(cashier.checks.get(0));
		
		
		//Step 4 postconditions, step 5 preconditions
		assertEquals("MockWaiter should have an event log of size one after the Cashier's scheduler is called. Instead, the MockWaiter's event log reads: "
				+ waiter.log.toString(), 1, waiter.log.size());
		
		assertEquals("Customer's log should have one entry. It doesnt.", 1, customer.log.size());
		
		assertTrue("The check should contain a cashPaid value. It doesnt.", cashier.checks.get(0).cashPaid >= 0);
		
		assertEquals("Market should have an log size of one. it doesnt", 1, market.log.size());
		
		assertEquals("Cashier's deliveryBills list should be at a size of zero. It isn't.", 0, cashier.billsToPay.size());
		
		
		//step 5
		//Do pickAndExecuteAnAction - should be true
		assertTrue("Cashier's scheduler should have returned true (an action to create the check), but didn't.", cashier.pickAndExecuteAnAction());
		
		
		//step 5 postconditions
		assertEquals("Cashier's check size should now be empty, since the customer is paying in full. It's not.", 0, cashier.checks.size());
		
		assertEquals("Customer's log should have two entries. It doesnt.", 2, customer.log.size());
		
		assertEquals("Market should have an log size of one. it doesnt", 1, market.log.size());
		
		assertEquals("Cashier's deliveryBills list should be at a size of zero. It isn't.", 0, cashier.billsToPay.size());
		
		assertEquals("MockWaiter should have an event log of size one after the Cashier's scheduler is called. Instead, the MockWaiter's event log reads: "
				+ waiter.log.toString(), 1, waiter.log.size());
	}
}
