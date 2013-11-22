package simcity.restaurants.restaurant1.interfaces;

import simcity.restaurants.restaurant1.CashierRole.Check;

/**
 * A sample Waiter interface built to unit test a CashierAgent.
 *
 * @author Nikhil Bedi
 *
 */
public interface Waiter {
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