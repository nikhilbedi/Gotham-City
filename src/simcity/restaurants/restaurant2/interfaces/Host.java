package restaurant2.interfaces;

public interface Host {

	public abstract void msgIWantFood(Customer cust);

	public abstract void msgLeavingTable(Customer cust);

	public abstract void msgTableEmpty(int tablenum);

	public abstract void msgWantToGoOnBreak(Waiter w);

	public abstract void msgAtTable();

}