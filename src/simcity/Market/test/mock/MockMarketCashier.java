package simcity.Market.test.mock;

import java.util.List;
import java.util.Map;

import simcity.Market.Item;
import simcity.Market.MarketCustomerRole;
import simcity.Market.Order;
import simcity.Market.interfaces.MarketCashier;
import simcity.Market.interfaces.MarketCustomer;

public class MockMarketCashier extends Mock implements MarketCashier {

	public MockMarketCashier(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void hereIsMoney(MarketCustomer marketCustomerRole, double payment) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void INeed(List<Order> orders) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Map<String, Item> getInventory() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void needFood(MarketCustomerRole marketCustomerRole) {
		// TODO Auto-generated method stub
		
	}

}
