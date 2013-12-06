package simcity.restaurants.restaurant5.interfaces;



public interface Cashier {
	
	public abstract String getName();
	
	
	public abstract void computeBill(Waiter w, Customer c, String choice);



	public abstract void payment(Customer c, double cash, double check);


	public abstract void hereIsMarketBill(Market market, double bill);

}
