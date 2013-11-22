package simcity.Restaurant4.interfaces;

public interface Restaurant4Cook {

	boolean getPause();

	void Ready(String string);

	void setSalad();

	void HereIsOrder(Restaurant4Waiter restaurant4WaiterRole,
			String choice, int table);

}
