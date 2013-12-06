package simcity.restaurants.restaurant4.interfaces;

import simcity.restaurants.restaurant4.Restaurant4CustomerRole;

public interface Restaurant4Waiter {

	void youMayGoToABreak();

	void PleaseSeatCustomer(Restaurant4Customer restaurant4Customer, int table);

	int getSize();

	String getName();

	void ReadyToOrder(Restaurant4Customer restaurant4CustomerRole);

	void HereIsMyChoice(Restaurant4Customer restaurant4CustomerRole,
			String choice);

	void computeCheck(Restaurant4Customer restaurant4CustomerRole);

	void DoneAndLeaving(Restaurant4Customer restaurant4CustomerRole);

	void HereIsCheck(Restaurant4Customer c, double amountDue);

	void OrderDone(String choice, int table);

	void outOf(int table, String choice);

	void msgAtTable();

	void arrivedAtTable(Restaurant4Customer customer);

	void arrivedToNotifyNoFood(Restaurant4Customer customer, String choice);

	void arrivedToCook(Restaurant4Customer customer);

	void GotFood(String food);

	void startBreak();

	void HereIsYourFood(int table);

	void arrivedAtCashier(Restaurant4Customer customer);

	void HereIsYourCheck(int table);

	void atDefaultPosition();

	void atRevolvingStand(Restaurant4Customer customer);


}
