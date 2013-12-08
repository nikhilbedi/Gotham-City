package simcity.restaurants.restaurant5.interfaces;


public interface Market {
	public abstract String getName();
	public abstract void hereIsPayment(Cashier c, double payment);
	
	public abstract void iNeedFood(Cook ref, String s, int amount);
	
}
