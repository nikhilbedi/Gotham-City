package simcity.Market.test.mock;

import java.util.List;
import java.util.Map;

import simcity.Market.Order;
import simcity.Market.MarketGui.MarketCustomerGui;
import simcity.Market.interfaces.MarketCustomer;

public class MockMarketCustomer extends Mock implements MarketCustomer{

	public MockMarketCustomer(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void HereIsChange(double d) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void amountDue(double amountDue) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void outOf(Order order) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void AtCashier() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void NextCustomerPlease() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void ArrivedToGetItem() {
		// TODO Auto-generated method stub
		
	}

	public String getName(){
		return null;
		
		
	}

	@Override
	public void getGroceries() {
		
		
		// TODO Auto-generated method stub
		
	}

	@Override
	public void HereIsYourStuff(Map<String, Integer> m) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public MarketCustomerGui getCustomerGui() {
		// TODO Auto-generated method stub
		return null;
	}



}
