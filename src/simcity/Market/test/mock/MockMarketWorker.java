package simcity.Market.test.mock;

import java.util.List;
import java.util.Map;

import agent.Role;
import simcity.Market.Item;
import simcity.Market.MarketWorkerRole.CustomerDelivery;
import simcity.Market.MarketWorkerRole.RestaurantDelivery;
import simcity.Market.Order;
import simcity.Market.interfaces.MarketCustomer;
import simcity.Market.interfaces.MarketWorker;
import simcity.restaurants.Restaurant;

public class MockMarketWorker extends Mock implements MarketWorker{

	public MockMarketWorker(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void Bring(List<Order> orders) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Map<String, Item> getInventory() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void Brought(MarketCustomer customer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void Sent(Role role) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<CustomerDelivery> getCustomerDeliveries() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<RestaurantDelivery> getRestaurantDeliveries() {
		// TODO Auto-generated method stub
		return null;
	}

	

	@Override
	public void SendFood(Map<String, Integer> temp, Restaurant r) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void Delivered(Restaurant r) {
		// TODO Auto-generated method stub
		
	}

}
