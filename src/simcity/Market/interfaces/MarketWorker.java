package simcity.Market.interfaces;

import java.util.List;
import java.util.Map;

import agent.Role;
import simcity.Market.Item;
import simcity.Market.Order;

public interface MarketWorker {

	abstract void Bring(List<Order> orders);
	
	abstract Map<String, Item> getInventory();

	abstract void Brought(MarketCustomer customer);

	abstract void SendFood(Map<String, Integer> temp, Role cookRole);

	abstract void Sent(Role role);

}
