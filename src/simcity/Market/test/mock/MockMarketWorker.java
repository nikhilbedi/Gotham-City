package simcity.Market.test.mock;

import java.util.List;
import java.util.Map;

import simcity.Market.Item;
import simcity.Market.Order;
import simcity.Market.interfaces.MarketCustomer;
import simcity.Market.interfaces.MarketWorker;

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

}
