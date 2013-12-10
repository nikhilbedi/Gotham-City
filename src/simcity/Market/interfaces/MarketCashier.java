package simcity.Market.interfaces;

import java.util.List;
import java.util.Map;

import agent.Role;
import simcity.Market.Item;
import simcity.Market.MarketCashierRole.RestaurantOrder;
import simcity.Market.MarketCustomerRole;
import simcity.Market.Order;
//import simcity.Restaurant4.Restaurant4CashierRole;
//import simcity.Restaurant4.interfaces.Restaurant4Cashier;
import simcity.Market.MarketCashierRole.Check;
import simcity.restaurants.Restaurant;
import simcity.restaurants.restaurant4.Restaurant4CashierRole;

public interface MarketCashier {

	abstract void hereIsMoney(MarketCustomer marketCustomerRole, double payment);

	abstract void INeed(List<Order> orders);

	abstract Map<String, Item> getInventory();

	abstract void needFood(MarketCustomer marketCustomerRole);

	abstract void INeedFood(Map<String, Integer> food, Restaurant r);

	abstract void hereIsMoneyRestaurant(Restaurant r, double money);

	abstract List<Check> getChecks();
	
	abstract List<RestaurantOrder> getRestaurantOrders();
	
	abstract void setRest4Cashier(Restaurant4CashierRole r);

	abstract void foodIsDelivered(Restaurant rest);
}
