package simcity.restaurants.restaurant3.src.restaurant.test.mock;

import simcity.restaurants.restaurant3.src.restaurant.CustomerRole;
import simcity.restaurants.restaurant3.src.restaurant.Order;

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
