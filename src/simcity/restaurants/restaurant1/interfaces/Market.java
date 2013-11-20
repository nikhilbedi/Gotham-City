package simcity.restaurants.restaurant1.interfaces;


/**
 * A sample Market interface built to unit test a CashierAgent.
 *
 * @author Nikhil Bedi
 *
 */
public interface Market {
    /**
     * @param amount The amount the cashier must pay
     * 
     * Sent by the market to the cashier to tell him to pay
     */
	public abstract void hereIsPayment(double amount);
}