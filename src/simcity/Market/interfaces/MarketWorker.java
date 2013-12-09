package simcity.Market.interfaces;

import java.util.List;
import java.util.Map;

import agent.Role;
import simcity.Market.Item;
import simcity.Market.Order;
import simcity.Market.MarketWorkerRole.CustomerDelivery;
import simcity.Market.MarketWorkerRole.RestaurantDelivery;
import simcity.restaurants.Restaurant;

public interface MarketWorker {

	abstract void Bring(List<Order> orders);
	
	abstract Map<String, Item> getInventory();

	abstract void Brought(MarketCustomer customer);

	abstract void SendFood(Map<String, Integer> temp, Role cookRole, Restaurant r);

	abstract void Sent(Role role);

	abstract List<CustomerDelivery> getCustomerDeliveries();
	
	abstract List<RestaurantDelivery> getRestaurantDeliveries();
}
