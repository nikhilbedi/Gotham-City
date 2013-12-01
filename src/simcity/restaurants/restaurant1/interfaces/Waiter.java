package simcity.restaurants.restaurant1.interfaces;

import agent.Role;
import simcity.restaurants.restaurant1.Restaurant1CustomerRole;
import simcity.restaurants.restaurant1.CashierRole.Check;

/**
 * A sample Waiter interface built to unit test a CashierAgent.
 *
 * @author Nikhil Bedi
 *
 */
public interface Waiter {
	
	//Host
	public abstract void goOffBreak();
	public abstract void goOnBreak();
	public abstract void doNotGoOnBreak();
	public abstract void pleaseSeatCustomer(Customer cust, int table, int x, int y);
	

	
	//gui message
	public abstract void shouldTakeBreak();
	public abstract void ledCustomerToTableAnimation();
	public abstract void reachedCookAnimation();

	
	//Customer
	public abstract void readyToOrder(Customer cust);
	public abstract void hereIsMyChoice(String choice, Customer cust);
	public abstract void readyForCheck(Restaurant1CustomerRole role);
	public abstract void doneAndLeaving(Customer cust);
	public abstract void customerLeft(Customer cust);
	
	//Cook
	public abstract void orderDone(int table, String choice);
	public abstract void outOfFood(int table, String choice);


    /**
     * @param ch The Check object containing the amount, customer, and waiter
     * 
     * Sent by the cashier to the waiter to cause waiter to give check to corresponding
     */
    public abstract void hereIsCheck(Check ch);
	//print("Received check for " + ch.customer);
    
    /**
     * @param amount The price the customer either owes or is given back. If it is negative, it is a debt.
     *
     * Sent by the cashier to end the transaction between him and the customer. amount can be >=0 or <0.
     */
}