package simcity.restaurants.restaurant1.interfaces;

import simcity.restaurants.restaurant1.CashierRole.Check;
/**
 * A sample Customer interface built to unit test a CashierAgent.
 *
 * @author Nikhil Bedi
 *
 */
public interface Cashier {

    public abstract void makeCheck(Waiter w, Customer c, String choice);
    /**
     * @param amount The price the customer either owes or is given back. If it is negative, it is a debt.
     *
     * Sent by the cashier to end the transaction between him and the customer. amount can be >=0 or <0.
     */
    public abstract void hereIsPayment(Check ch, double amount);
	//print("Cashier gave me change. I have " + money + " money left.");
	//print("Since I was in debt and God likes to speak to me, I was given 30 dollars to clear my payments.")
    
    public abstract void payMarketBill(Market market, double amount);
}