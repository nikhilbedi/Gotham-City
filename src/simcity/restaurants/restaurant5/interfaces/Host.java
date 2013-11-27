package simcity.restaurants.restaurant5.interfaces;


public interface Host {
	public abstract String getName();

	// Messages

	//v2
	public abstract void iWantToEat(Customer cust);

	public abstract void tableIsFree(Waiter waiter, int t);
	//v2.1
	public abstract void iWantABreak(Waiter waiter);

	public abstract void offBreak(Waiter waiter);

	public abstract void noLongerHungry(Customer c);
}
