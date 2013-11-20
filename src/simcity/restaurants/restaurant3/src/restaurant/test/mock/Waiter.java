package simcity.restaurant_evan.src.restaurant.test.mock;

import simcity.restaurant_evan.src.restaurant.CustomerRole;
import simcity.restaurant_evan.src.restaurant.Order;

public interface Waiter {

	void msgWaiterOutOfFood(Order o);

	void msgSitAtTable(CustomerRole c, int table);

	void msgReadyToOrder(CustomerRole cust);

	void msgHereIsMyChoice(CustomerRole cust, String choice);

	void msgOrderIsReady(Order order);

	void msgIWantTheCheck(CustomerRole cust);

	void msgWaiterHereIsCheck(Order o);

	void msgDoneEatingAndLeavingTable(CustomerRole cust);

}
